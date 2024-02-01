package com.example.demo.websocket;

public class MsgerContext {
    public static final ThreadLocal<Msger> SMARTWORD_THREAD_LOCAL = new ThreadLocal<Msger>();

//    public static void createMsger(String key) {
//        THREAD_LOCAL.set(new Msger(key));
//    }

    public static Msger getSmartWord() {
        Msger msger = SMARTWORD_THREAD_LOCAL.get();
        if (msger == null) {
            //MsgerContext.createMsger("smartword");
            SMARTWORD_THREAD_LOCAL.set(new Msger("smartword"));
            msger = SMARTWORD_THREAD_LOCAL.get();
        }
        return msger;
    }

    public static void removeSmartWord() {
        SMARTWORD_THREAD_LOCAL.remove();
    }
}
