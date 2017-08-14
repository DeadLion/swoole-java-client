package com.chelun.soa.test;

import com.chelun.soa.ResponseData;
import com.chelun.soa.client.NettyClient;

import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2017/5/2.
 */
public class CheckWallet {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ResponseData<LinkedHashMap> result = (ResponseData<LinkedHashMap>) NettyClient.getInstance("kaojiazhao").callCustom("Supercoach\\Order::getCoachCashInList",
                "200",//source
                218782
        );
    }
}
