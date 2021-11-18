package bean;

import java.util.ArrayList;
import java.util.List;

public class ExchangePool {
    private List<Order> list;

    public ExchangePool(){
        this.list = new ArrayList<>();
    }

    public ExchangePool(List<Order> list) {
        this.list = list;
    }

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }

    public void setOrder(Order order){
        this.list.add(order);
    }

    public void addAll(List<Order> list){
        this.list.addAll(list);
    }

    public Order get(int index){
        return list.get(index);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        list.stream()
                .forEach((s) -> sb.append(s).append("\n"));
        return sb.toString();
    }
}
