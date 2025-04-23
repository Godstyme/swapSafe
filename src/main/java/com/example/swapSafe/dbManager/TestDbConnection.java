package com.example.swapSafe.dbManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDbConnection implements CommandLineRunner {

    @Autowired
    private DatabaseManagerConn databaseManagerConn;

    @Override
    public void run(String... args) throws Exception {
        databaseManagerConn.testConnection();
    }
}
