package com.chelun.soa;


public interface AsyncRPCCallback {

    void success(Object result);

    void fail(Exception e);

}
