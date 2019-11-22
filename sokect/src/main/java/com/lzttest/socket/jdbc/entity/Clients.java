package com.lzttest.socket.jdbc.entity;

public class Clients {

    private String clientNo;

    private String address;

    public Clients() {
    }

    public Clients(String clientNo, String address) {
        this.clientNo = clientNo;
        this.address = address;
    }

    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo == null ? null : clientNo.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}