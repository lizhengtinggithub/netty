package com.lzttest.socket.jdbc.dao;

import com.lzttest.socket.jdbc.entity.Orders;

public interface OrdersMapper {
    int insert(Orders record);

    int insertSelective(Orders record);
}