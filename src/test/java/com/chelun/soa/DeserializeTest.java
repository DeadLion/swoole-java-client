package com.chelun.soa;

import org.junit.Test;

/**
 * @author zhongjian
 * @create 2017-06-06
 */

public class DeserializeTest {

@Test
    public void testde(){
    Request<ResponseData> request = new Request<ResponseData>("a",null);
    request.parseResponse("{\"errno\":0,\"data\":{\"code\":1,\"msg\":\"success\",\"data\":{\"order_id\":\"231255556635394048\"}}}");
    System.out.println(request.getResponse().getErrno());
}
}
