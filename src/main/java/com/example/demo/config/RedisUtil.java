package com.example.demo.config;

import com.alibaba.fastjson2.*;
import com.google.common.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public final class RedisUtil implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    //若当前class不在spring boot框架内（不在web项目中）所以无法使用autowired，使用此种方法进行注入
    //private static RedisTemplate<String, Object> template = (RedisTemplate<String, Object>) SpringUtils.getBean("redisTemplate");
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${guava.cache.maxCacheSize}")
    private Integer maxCacheSize;
    @Value("${guava.cache.initialCapacity}")
    private Integer initialCapacity;
    @Value("${guava.cache.concurrencyLevel}")
    private Integer concurrencyLevel;
    @Value("${guava.cache.expireAfterWrite}")
    private Integer expireAfterWrite;

    //读写锁,管理内存缓存并发读写
    private ReentrantReadWriteLock rrwLock = new ReentrantReadWriteLock();
    private final Lock readlock = rrwLock.readLock();
    private final Lock writeLock = rrwLock.writeLock();
    //内存池
    private Cache<String, Object> objectCache;
    /*
        ###  线上Redis禁止使用Keys正则匹配操作  ###
        1、redis是单线程的，其所有操作都是原子的，不会因并发产生数据异常
        2、使用高耗时的Redis命令是很危险的，会占用唯一的一个线程的大量处理时间，导致所有的请求都被拖慢。（例如时间复杂度为O(N)的KEYS命令，严格禁止在生产环境中使用）
        (1)运维/开发人员进行keys *操作，该操作比较耗时，又因为redis是单线程的，所以redis被锁住。
        (2)此时QPS比较高，又来了几万个对redis的读写请求，因为redis被锁住，所以全部Hang在那。
        (3)因为太多线程Hang在那，CPU严重飙升，造成redis所在的服务器宕机
        (4)所有的线程在redis那取不到数据，一瞬间全去数据库取数据，数据库就宕机了。
        需要注意的是，同样危险的命令不仅有keys *，还有以下几组：
            Flushdb 命令用于清空当前数据库中的所有 key
            Flushall 命令用于清空整个 Redis 服务器的数据(删除所有数据库的所有 key )
            CONFIG 客户端连接后可配置服务器
       参考简书：https://www.jianshu.com/p/2d0e11c551fc
    */

    // =============================common============================
    @Override
    public void afterPropertiesSet() throws Exception {
        objectCache = CacheBuilder.newBuilder()
                .initialCapacity(maxCacheSize)
                .concurrencyLevel(concurrencyLevel)//设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
                .maximumSize(maxCacheSize)  //最多存放50个数据
                .expireAfterWrite(expireAfterWrite, TimeUnit.SECONDS)  //缓存3600秒
                .recordStats()
                .build();
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void remove(String... key) {
        if (key != null && key.length > 0) {
            writeLock.lock();
            try {
                if (key.length == 1) {
                    if (key[0].endsWith("*")) {
                        objectCache.invalidateAll();
                        Set<String> keys = redisTemplate.keys(key[0]);
                        redisTemplate.delete(keys);
                    }
                    redisTemplate.delete(key[0]);
                    objectCache.invalidate(key[0]);
                } else {
                    redisTemplate.delete(CollectionUtils.arrayToList(key));
                    objectCache.invalidateAll(CollectionUtils.arrayToList(key));
                }
            } finally {
                writeLock.unlock();
            }
        }
    }
    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        if (key == null) {
            return null;
        }
        Object v = redisTemplate.opsForValue().get(key);
        if (v != null) {
            writeLock.lock();
            try {
                objectCache.put(key, v);
            } finally {
                writeLock.unlock();
            }
        }
        return v;
    }

    /**
     * 普通缓存获取,泛型
     *
     * @param key   键
     * @param clazz 类型
     * @return 值
     */
    public <T> T get(String key, Class<T> clazz) {
        Object v = null;
       /* try {
            v=objectCache.get(key,()->get(key));//优先从本地缓存取,没有值则直接从redis里取,注意valueLoader要么返回非null值，要么抛出异常，绝对不能返回null
        } catch (ExecutionException e) {
            log.error(e);
        }*/
        readlock.lock();
        try {
            v = objectCache.getIfPresent(key);//通过key获取本地缓存中的value，若不存在直接返回nul
        } catch (Exception e) {
            log.error("", e);
        } finally {
            readlock.unlock();
        }
        if (v == null) {
            v = get(key);//本地缓存不存在从redis中取
        }
        if (v == null) {
            return null;
        }

        String json = JSON.toJSONString(v, com.alibaba.fastjson2.JSONWriter.Feature.WriteClassName);//map转String
        T jsonObject = JSON.parseObject(json, clazz);//String转json
        return jsonObject;
    }

    public <T> T get(String key, TypeReference<T> type) {
        Object v = null;
        readlock.lock();
        try {
            v = objectCache.get(key, () -> get(key));//优先从本地缓存取,没有值则直接从redis里取
        } catch (ExecutionException e) {
            log.error("", e);
        } finally {
            readlock.unlock();
        }
        if (v == null) {
            return null;
        }
        T val = JSON.parseObject(v.toString(), type);
        return val;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        writeLock.lock();
        try {
            //加写锁
            objectCache.put(key, value);
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            //加写锁
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
                writeLock.lock();
                try {
                    objectCache.put(key, value);
                } finally {
                    writeLock.unlock();
                }
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */

    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("", e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */

    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("", e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("", e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("", e);
            return 0;
        }
    }
    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("", e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */

    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("", e);
            return 0;
        }
    }

    /**
     * 清除所有的本地换缓存
     */
    public void clearAllLocalCache() {
        objectCache.invalidateAll();
    }

    /**
     * 清除指定的本地换缓存
     */
    public void clearLocalCache(Object ob) {
        objectCache.invalidate(ob);
    }
}

