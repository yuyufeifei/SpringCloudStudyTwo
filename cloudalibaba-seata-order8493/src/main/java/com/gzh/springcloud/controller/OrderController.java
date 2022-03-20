package com.gzh.springcloud.controller;

import com.gzh.springcloud.entity.Order;
import com.gzh.springcloud.service.AccountService;
import io.seata.core.context.RootContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

/**
 * @author GZH
 * @date 2022-03-19
 */
@RestController
@Log4j2
public class OrderController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private static final String USER_ID = "U100001";
    private static final String COMMODITY_CODE = "C00321";

    private final AccountService accountService;
    private final JdbcTemplate jdbcTemplate;
    private final Random random;
    public OrderController(AccountService accountService, JdbcTemplate jdbcTemplate) {
        this.accountService = accountService;
        this.jdbcTemplate = jdbcTemplate;
        this.random = new Random();
    }

    @PostMapping(value = "/order", produces = "application/json")
    public String order(String userId, String commodityCode, int orderCount) {
        log.info("Order Service Begin ... xid: " + RootContext.getXID());

        int orderMoney = calculate(commodityCode, orderCount);

        invokerAccountService(orderMoney);

        final Order order = new Order();
        order.userId = userId;
        order.commodityCode = commodityCode;
        order.count = orderCount;
        order.money = orderMoney;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int result = jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement pst = con.prepareStatement(
                        "insert into order_tbl (user_id, commodity_code, count, money) values (?, ?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setObject(1, order.userId);
                pst.setObject(2, order.commodityCode);
                pst.setObject(3, order.count);
                pst.setObject(4, order.money);
                return pst;
            }
        }, keyHolder);

        order.id = keyHolder.getKey().longValue();

        if (random.nextBoolean()) {
            throw new RuntimeException("this is a mock Exception");
        }

        log.info("Order Service End ... Created " + order);

        if (result == 1) {
            return SUCCESS;
        }
        return FAIL;
    }

    private int calculate(String commodityId, int orderCount) {
        return 2 * orderCount;
    }

    private void invokerAccountService(int orderMoney) {
        String result = accountService.account(USER_ID, orderMoney);
        log.info("Account result:" + result);
    }

}
