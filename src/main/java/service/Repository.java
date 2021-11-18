package service;

import bean.ExchangePool;
import bean.Pair;
import controller.parsing.json.exchange_pool.ExchangePoolPoloneix;
import controller.parsing.json.request.PoloneixAPI;

import java.util.concurrent.CountDownLatch;

public class Repository extends Thread {

    static ExchangePool pool = new ExchangePool();

    public static CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void run(){
        for (Pair pair : Pair.values()) {
            pool.addAll(ExchangePoolPoloneix.getListOrder(PoloneixAPI.get(pair)));
        }
        latch.countDown();

    }



}
