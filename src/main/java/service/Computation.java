package service;

import bean.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.function.Predicate;

public class Computation implements Runnable {
    private Unit unit;
    private ExchangePool pool;
    private CountDownLatch latch = Repository.latch;


    private double startSum;

    public Computation(Unit unit, ExchangePool pool){
        this.unit = unit;
        this.pool = pool;
    }

    @Override
    public void run() {
//          latch.await();

        CalculateShell shell = new CalculateShell(this.unit, 3);
        Set<PairShell> pairShells = shell.getAllChain();
        for ( PairShell pairShell : pairShells) {
            Pair first = pairShell.getChain().get(0);                                //получаем начальную пару, в-но выделить в отдельное поле

                    /* Возможно некоректное значение, т.к. мы вычисляем ту валюту которая была сразу на руках
                    чтобы провести ее сравнение в конце,
                    но и эту же величину передаем для новой единицы полученной
                    в результате первой сделки - а надо просто взять count!!!!
                     */

            Balance firstBalance = getBalanceStart(this.unit, first);                //не обязательно передовать в конструктор


            /*
             * получаем список Pair из PairShell все кроме последней
             * ???есть смысл выделить этот код в отдельный метод
             * в самом класск PairShell
             * */

            List<Pair> nextPairs = new ArrayList<>();
            int size = pairShell.getChain().size();

            if (size == 2 ) {
                nextPairs.add(pairShell.getChain().get(1));
            } else if (size > 2) {
                nextPairs = pairShell.getChain().subList(1, size - 1);           //отдельный класс / метод?
            }    
            nextPairs.add(pairShell.getCurrentPair());

            Balance buffer = firstBalance;
            for ( Pair nextPair: nextPairs) {
                buffer = getNextBalance(buffer, nextPair);
            }

            double differnce = buffer.getCount() - startSum;


            System.out.print(unit);
            System.out.println(" " + pairShell);
            System.out.println("Start summ: " + startSum + " - " + this.unit);
            System.out.println("End sum" + buffer.getCount() + " - " + buffer.getUnit());
            System.out.println("win = " + differnce);
            System.out.println();
            System.out.println("<------------------------>");
        }
    }


/**
     * Метод для определения колличество валюты которую мы продаем чтобы
     * вступить в арбитражную "гонку"
     * @param unit
     * @param pair
     * @return

*/

    private Balance getBalanceStart(Unit unit, Pair pair){
        Balance balance = null;
        if(pair.getFirstUnit() == unit) {
            Optional<Order> o = pool.getList()
                    .stream()
                    .filter(((Predicate<Order>) order -> order.getPair().getFirstUnit() == unit)
                            .and(order -> order.getType() == OperationType.ASK))
                    .max(Comparator.comparing(Order::getPrice));
            this.startSum = (o.get().calculateSumm());      // внешнюю переменную
            balance = new Balance(o.get().getCount(), o.get().getPair().getSecondUnit());
        } else if (pair.getSecondUnit() == unit) {
            Optional<Order> o = pool.getList()
                    .stream()
                    .filter(((Predicate<Order>) order -> order.getPair().getSecondUnit() == unit)
                            .and(order -> order.getType() == OperationType.BID))
                    .min(Comparator.comparing(Order::getPrice));
            this.startSum = (o.get().getCount());      //внешнюю переменную
            balance = new Balance(o.get().calculateSumm(), o.get().getPair().getFirstUnit());
        }
        return balance;
    }

/**
     * На основе предыдущей сделки и текущей пары, определяет какую валюту мы
     * продаем какую и в каком колличестве мы покупаем
     * @param balance
     * @param pair
     * @return

*/

    private Balance getNextBalance(Balance balance, Pair pair){
        Balance returnBalance = null;
        if(pair.getFirstUnit() == balance.getUnit()) {
            Optional<Order> o = pool.getList()
                    .stream()
                    /*.filter(((Predicate<Order>) order -> order.getPair().getFirstUnit() == balance.getUnit())
                            .and(order -> order.getType() == OperationType.ASK))*/
                    .filter(((Predicate<Order>) order -> order.getPair() == pair)
                            .and(order -> order.getType() == OperationType.ASK))
                    .max(Comparator.comparing(Order::getPrice));
            double newCount = balance.getCount() / o.get().getPrice();
            returnBalance = new Balance(newCount, o.get().getPair().getSecondUnit());
        } else if (pair.getSecondUnit() == balance.getUnit()) {
            Optional<Order> o = pool.getList()
                    .stream()
                    /*.filter(((Predicate<Order>) order -> order.getPair().getSecondUnit() == balance.getUnit())
                            .and(order -> order.getType() == OperationType.BID))*/
                    .filter(((Predicate<Order>) order -> order.getPair() == pair)
                            .and(order -> order.getType() == OperationType.BID))
                    .min(Comparator.comparing(Order::getPrice));
            double newCount = balance.getCount() * o.get().getPrice();
            returnBalance = new Balance(newCount, o.get().getPair().getFirstUnit());
        }
        return returnBalance;
    }

/**
     * метод для получения листа цепочек в виде листа цепочек из Pair вместо PairShell
     * @param pairShells
     * @return

*/

    private static List<List<Pair>> getShellsContents(List<PairShell> pairShells){
        List<List<Pair>> pairs = new ArrayList<>();
        if (pairShells != null) {
            for (PairShell pairShell : pairShells) {
                List<Pair> buffer = new ArrayList<>();
                buffer.addAll(pairShell.getChain());
                buffer.add(pairShell.getCurrentPair());
                pairs.add(buffer);
            }
        }
        return pairs;
    }

}

