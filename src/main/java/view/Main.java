package view;

import bean.ExchangePool;
import bean.Order;
import bean.Pair;
import bean.Unit;
import controller.parsing.json.exchange_pool.ExchangePoolPoloneix;
import controller.parsing.json.request.PoloneixAPI;
import service.Computation;
import service.UnitCalculate;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    static ExchangePool pool = new ExchangePool();

    public static void main(String[] args) {
        int count = 4;

        for (Pair pair : Pair.values()) {
            pool.addAll(ExchangePoolPoloneix.getListOrder(PoloneixAPI.get(pair)));
        }

        Computation c = new Computation(Unit.BTC, pool);
         new Thread(c).start();

    }
}
