package com.lzttest.socket.jdbc.dao;

import com.lzttest.socket.jdbc.entity.Clients;

public interface ClientsMapper {
    int insert(Clients record);

    int insertSelective(Clients record);

    Clients selByNo(String clientNo);
}