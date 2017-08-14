package com.chelun.soa.test;

import com.chelun.soa.ResponseData;
import com.chelun.soa.client.NettyClient;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2017/4/25.
 */
public class TestAddIncome {

    private static final Logger log = LoggerFactory.getLogger(TestAddIncome.class);


    public static void main(String[] args) throws Exception {

        int len = (null == args? 10:Integer.valueOf(args[0]));

        test(1);
        for(int i=0;i<len;i++){
            ResponseData result = (ResponseData) NettyClient.getInstance("kaojiazhao").callCustom("Supercoach\\Order::applyWithdraw",
                    2222,
                    "2",//提现类型1是微信2是银行卡
                    12563658,
                    100,//单位是分
                    StringEscapeUtils.escapeJava("哈哈从超级教练app提现1元，流水Id：" + 12563658),
                    "");
            if(null == result){
                System.out.println("result is null");
            }else{
                System.out.println("result code = " + result.getCode());
                System.out.println("result data = " + result.getData());
            }
        }
    }








    public static void test(int len) {
        int failCount = 0 ;
        Random random = new Random();
        String daybookId ;
        for(int i =0;i<len;i++){
            System.out.println("当前调用次数:"+i);
            //入账流水
            daybookId = "12"+String.valueOf(random.nextInt(1000)) + String.valueOf(i);
            ResponseData order = null;
            try {
                order = (ResponseData) NettyClient.getInstance("kaojiazhao").callCustom("Supercoach\\Order::addIncomeRecord",
                        "200",//source
                        daybookId ,//serial_number
                        2,//收入来源
                        2222,//collection_id
                        12345,//payment_id
                        1,//money
                        "0", //cost
                        1, //coach_money
                        "0", //cl_money
                        "2", //payment_type,1支付宝 2微信 3银行卡 4百度 5-360 6分期
                        String.valueOf(new Date().getTime()), //pay_success_time
                        ""//extra
                );
                if (null == order || 0 == order.getCode()) {
                    failCount ++ ;
                    log.error("入账出现异常, daybookId=" + daybookId);
                    if (null == order) {
                        log.error("articleTipSuccessCallback:addIncomeRecord fail, result is null");
                    } else if (0 == order.getCode()) {
                        log.error(String.format("articleTipSuccessCallback:addIncomeRecord fail, result=%s,resultCode=%s", order, order.getCode()));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("连接失败,e=",e);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("总调用次数:"+len+",失败次数:"+failCount);
    }
}
