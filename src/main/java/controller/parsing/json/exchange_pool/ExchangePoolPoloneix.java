package controller.parsing.json.exchange_pool;

import bean.OperationType;
import bean.Order;
import controller.parsing.json.bean.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExchangePoolPoloneix implements ExchangePool {

    public static List<Order> getListOrder(JsonObject object){
        List<Order> orders = new ArrayList<>();
        orders.addAll(object.getAsks().stream()
                                    .map((s) -> new Order(object.getPAIR(), OperationType.ASK, s.get(0), s.get(1)))
                                    .collect(Collectors.toList()));

        orders.addAll(object.getBids().stream()
                .map((s) -> new Order(object.getPAIR(), OperationType.BID, s.get(0), s.get(1)))
                .collect(Collectors.toList()));
        return orders;
    }


}
