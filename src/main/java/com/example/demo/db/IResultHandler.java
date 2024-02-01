package com.example.demo.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IResultHandler {
    void handle(ResultSet resultSet) throws Exception;

    boolean isSuccess();
}

