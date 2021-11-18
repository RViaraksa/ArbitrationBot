package bean;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Pair {
    BTC_ETH("BTC_ETH"), BTC_BCH("BTC_BCH"), USDT_ETH("USDT_ETH"),
    ETH_ETC("ETH_ETC"), USDT_BTC("USDT_BTC");

    private final String type;

    Pair(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }



    public static int getSize(){
        return Pair.values().length;
    }


    /**
    !!!method can return null
     */
    public Unit getFirstUnit(){
        return parsePairType(0);
    }


    /**
    method can return null
     */
    public Unit getSecondUnit(){
        return parsePairType(1);
    }

    /**
     * Проверяет содержит ли данная пара единицу
     * @param unit
     * @return
     */
    public boolean isContainUnit(Unit unit){
        boolean isContain = false;
        if ((this.getFirstUnit() == unit) || (this.getSecondUnit() == unit))
            isContain = true;
        return isContain;
    }

    /**
    @param  position : position 0 - first or second

    method can return null
     */
    private Unit parsePairType(int position){
        Unit unit = null;
        String first = type.split("_")[position].toUpperCase();
        switch (first){
            case "ETH":
                unit = Unit.ETH;
                break;
            case "ETC":
                unit = Unit.ETC;
                break;
            case "BTC":
                unit = Unit.BTC;
                break;
            case "BCH":
                unit = Unit.BCH;
                break;
            case "USDT":
                unit = Unit.USDT;
                break;
        }
        return unit;
    }

    public Optional<List<Pair>> findConnectedPair(){

        List<Pair> list = Stream.of(Pair.values())
                .filter(pair -> pair != this)
                .filter(((Predicate<Pair>) pair -> pair.getFirstUnit() == this.getFirstUnit())
                        .or(pair -> pair.getSecondUnit() == this.getFirstUnit()))
                .collect(Collectors.toList());
        list.addAll(Stream.of(Pair.values())
                .filter(pair -> pair != this)
                .filter(((Predicate<Pair>) pair -> pair.getFirstUnit() == this.getSecondUnit())
                        .or(pair -> pair.getSecondUnit() == this.getSecondUnit()))
                .collect(Collectors.toList()));
        return Optional.of(list);
    }

    public Optional<List<Unit>> findUnit(){
        List<Unit> l1 = Stream.of(Pair.values())
                .filter(pair -> pair != this)
                .filter(((Predicate<Pair>) pair -> pair.getFirstUnit() == this.getFirstUnit())
                        .or(pair -> pair.getSecondUnit() == this.getFirstUnit()))
                .map(pair -> pair.returnOppositeUnit(this.getFirstUnit()))
                .collect(Collectors.toList());
        l1.addAll(Stream.of(Pair.values())
                .filter(pair -> pair != this)
                .filter(((Predicate<Pair>) pair -> pair.getFirstUnit() == this.getSecondUnit())
                        .or(pair -> pair.getSecondUnit() == this.getSecondUnit()))
                .map(pair -> pair.returnOppositeUnit(this.getSecondUnit()))
                .collect(Collectors.toList()));

        return Optional.of(l1);
    }

    public Unit returnOppositeUnit(Unit u){
        Unit unit = null;
        if (this.getFirstUnit() == u){
            unit = this.getSecondUnit();
        } else if (this.getSecondUnit() == u){
            unit = this.getFirstUnit();
        }
        return unit;
    }
}
