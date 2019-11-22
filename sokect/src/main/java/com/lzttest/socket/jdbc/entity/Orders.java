package com.lzttest.socket.jdbc.entity;

public class Orders {
    private Integer idO;

    private Integer idP;

    private String orderno;

    public Integer getIdO() {
        return idO;
    }

    public void setIdO(Integer idO) {
        this.idO = idO;
    }

    public Integer getIdP() {
        return idP;
    }

    public void setIdP(Integer idP) {
        this.idP = idP;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }
}