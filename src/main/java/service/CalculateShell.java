package service;

import bean.Pair;
import bean.Unit;

import java.util.*;
import java.util.stream.Collectors;

public class CalculateShell {
    private Unit sellUnit;
    private List<Pair> pairs;
    private List<PairShell> shells;
    private Set<PairShell> returnShells;
    private int maxDepth;


    /**
    Коструктор принимает единицу измерения которую мы собираемся продавать
     а также максмальную глубину проводимых операций
     */
    public CalculateShell(Unit sellUnit, int maxDepth){
        this.sellUnit = sellUnit;
        UnitCalculate unitCalculate = new UnitCalculate(sellUnit);   //берем unit
        this.pairs = unitCalculate.findPairs();                 //получаем список пар содержащих их
        /*
        из списка пар получаем список объектов цепочек пар
        которые в дальнейшем станут стартовыми для генерации
        полноценно цепочки
         */
        this.shells = pairs
                        .stream()
                        .map(pair -> new PairShell(sellUnit, pair))
                        .collect(Collectors.toList());
        this.maxDepth = maxDepth;
        this.returnShells = new HashSet<>();
    }


    /**
     * Метод для получения листов всех возможных цепочек
     * @return List<PairShell>
     */
    public Set<PairShell> getAllChain(){
        int count = maxDepth;
        /*
        Поскольку при вызове конструктора уже создался лист PairShell в котором
        содержиться 1 элемент то начинаем обход с 2-ой позиции пока не достигнем
        предела - заданной глубины
         */
        for (int i=2 ; i <= count; i++){
            List<PairShell> buffer = new ArrayList<>();
            /*
            тут используется перезапись нашего поля: добавили для каждого
            PairShell новую пару в цепочку - получили новый список PairShell -
            перезалили значение
             */
            for ( PairShell pairShell1 : shells) {
                buffer.addAll(generate(pairShell1));
            }
            this.shells = buffer;
        }

        /*
        возращаем только те PairShell конечный элемент Pair содержит начальную единицу Unit, чтобы
        мы могли ее получить в конечном итоге, а также фильтрует на неверные цепочки, где вместо
        того чтобы продать нужный Unit продается другой
        пример: sellUnit = USDT полученная цепочка пар: USDT_BTC -> ETC-USDT , т.е. вместо того
        чтобы продать USDT мы продали BTC и сломали алгоритм)))? может в алгоритм это сунуть??
         */
        this.returnShells = shells
                .stream()
                .filter(s -> !s.isWrongChain())      //потом поставить !
                .filter(s -> s.getCurrentPair().isContainUnit(this.sellUnit))
                .filter(s -> s.getSellUnit() != this.sellUnit)
                .collect(Collectors.toSet());
        return returnShells;

    }

    /**
     *Получаем PairShell , для его текущей Pair находим возможные последующие Pair
     * при этом продим проверку или не пытаемся мы добавить пару котороая является
     * последней на текущий момент в цепочке, а также исключаем повторяющихся пар
     * в цепочке
     *
     * В резльтате если появилось несколько новых цепочек возращаем их в виде листа
     * @param shell
     * @return
     */
    private List<PairShell> generate(PairShell shell) {
        List<PairShell> list = new ArrayList<>();
        Optional<List<Pair>> optionalPairs = shell.getCurrentPair().findConnectedPair();    //ищем свзанные пары
        List<Pair> pairs = optionalPairs.get();
        /**
         * добавить логику которая проверяет, есть ли среди полученных пар
         * первая пара - если есть удаляем так как она должна быть завершающей
         */
        for (Pair pair : pairs) {

            if ((shell.getCurrentPair() != pair) & (!isRepeat(shell, pair))) {
                if (shell.isChainClose()) {
                    list.add(shell);
                } else {
                    PairShell p1 = new PairShell(shell, pair);

                    list.add(p1);
                }

            }
        }

        return list;
    }


    /**
     * Проверяет добавляем ли мы в цепочку такую пару, какая уже есть
     * @param shell
     * @param pair
     * @return
     */
    private boolean isRepeat(PairShell shell, Pair pair){
        boolean excludeRepetition = false;
        if (shell.getChain() != null) {
            int length = shell.getChain().size();
            if (length > 2) {
                for (Pair p : shell.getChain().subList(1, length - 1)) {
                    if (p == pair)
                        excludeRepetition = true;
                }
            } else if (length == 2){
                if (shell.getChain().get(1) == pair){
                    excludeRepetition = true;
                }
            }
        }
        return excludeRepetition;
    }


}