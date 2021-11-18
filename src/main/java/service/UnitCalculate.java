package service;

import bean.Pair;
import bean.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnitCalculate {
    private Unit unit;

    public UnitCalculate(Unit unit){
        this.unit = unit;
    }

    public List<Pair> findPairs(){
        List<Pair> pairs = new ArrayList<>();
        pairs.addAll(Arrays.asList(Pair.values())
                                            .stream()
                                            .filter(pair -> pair.getFirstUnit() == this.unit)
                                            .collect(Collectors.toList()));
        pairs.addAll(Arrays.asList(Pair.values())
                                            .stream()
                                            .filter(pair -> pair.getSecondUnit() == this.unit)
                                            .collect(Collectors.toList()));
        return pairs;
    }

}
