package service;

import bean.Pair;
import bean.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для моделироавания возможных потомков для
 * данной пары, т.е. если мы выбрали пару A_B, он среди
 * всех Pair найдет совпадения по первому и по второму
 * элементам и вернет их в виде листа:
 * [A_C, A_X, B_Y, B_W]
 */
public class PairShell {
    private Unit sellUnit;
    private int count;
    private boolean chainClose = false;
    private List<Pair> chain;
    private Pair currentPair;
    private boolean wrongChain = false;

    public PairShell(Unit selUnit, Pair current) {
        this.sellUnit = selUnit;
        this.count = 1;
        this.currentPair = current;
    }

    /**
     * Конструктор нового PairShel(класс хранящий информацию
     * о цепочке пар) на базе старого из которого переноситься
     * прошлая цепочка если она есть и текущая пара для
     * предидущего объекта
     * @param pairShell - предидущий объект
     * @param pair - пара
     */
    public PairShell(PairShell pairShell, Pair pair) {
        Unit prevSellUnit = pairShell.getSellUnit();                 //запись начальной единицы
        this.sellUnit = pairShell.getCurrentPair().returnOppositeUnit(prevSellUnit);
        /*
        если цепочка уже существует записываем в новый объект старую
        и добавляем текущий элемент из старого объекта
         */
        if (!pairShell.isChainEmpthy()) {
            chain = new ArrayList<>();
            chain = pairShell.getChain();
            chain.add(pairShell.getCurrentPair());
        }
        /*
         этап добавления второго элемента в цепочку
        тут важно ч-бы новая пара в цепочке не содержала
        ту единицу которую мы продаем
         */
        else if(!pair.isContainUnit(prevSellUnit)) {
            this.chain = new ArrayList<>();
            this.chain.add(pairShell.getCurrentPair());
        }
        /*
        иначе второй элемент содержит
         */
        else if(pair.isContainUnit(prevSellUnit)){
            this.wrongChain = true;
            this.chain = new ArrayList<>();
            this.chain.add(pairShell.getCurrentPair());
            this.chainClose = true;
        }
        /*
        новые пары не должны содержать предидущий элемент
         */
        if (!pair.isContainUnit(prevSellUnit)) {
            this.currentPair = pair;
            this.count = pairShell.getCount() + 1;
        } else {
            this.wrongChain = true;
            this.currentPair = pair;
            this.count = pairShell.getCount() + 1;
        }

        if(this.currentPair.isContainUnit(prevSellUnit)){
            this.chainClose = true;
        }


    }

    public int getCount() {
        return count;
    }

    public List<Pair> getChain() {
        List<Pair> copy = new ArrayList<>();
        if(chain != null)
            copy.addAll(chain);
        return copy;
    }

    public Unit getSellUnit() {
        return sellUnit;
    }

    public Pair getCurrentPair() {
        return currentPair;
    }

    public boolean isChainEmpthy(){
        boolean result = false;
        if(this.chain == null)
            result = true;
        return result;
    }

    public boolean isChainClose() {
        return chainClose;
    }

    public boolean isWrongChain() {
        return wrongChain;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PairShell{");
        sb.append("count=").append(count);
        sb.append(", chain=").append(chain);
        sb.append(", currentPair=").append(currentPair);
        sb.append('}').append("\n");
        return sb.toString();
    }
}

