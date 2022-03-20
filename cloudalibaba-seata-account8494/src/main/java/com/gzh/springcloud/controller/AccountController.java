package com.gzh.springcloud.controller;

import io.seata.core.context.RootContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author GZH
 * @date 2022-03-19
 */
@RestController
@Log4j2
public class AccountController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    private final JdbcTemplate jdbcTemplate;
    private final Random random;

    public AccountController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.random = new Random();
    }

    @PostMapping(value = "/account", produces = "application/json")
    public String account(String userId, int money) {
        log.info("Account Service ... xid: " + RootContext.getXID());

        if (random.nextBoolean()) {
            throw new RuntimeException("this is a mock Exception");
        }

        int result = jdbcTemplate.update(
                "update account_tbl set money = money - ? where user_id = ?",
                new Object[] { money, userId });
        log.info("Account Service End ... ");
        if (result == 1) {
            return SUCCESS;
        }
        return FAIL;
    }

}
