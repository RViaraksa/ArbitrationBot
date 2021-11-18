import bean.ExchangePool;
import bean.Pair;
import bean.Unit;
import controller.parsing.json.exchange_pool.ExchangePoolPoloneix;
import controller.parsing.json.request.PoloneixAPI;
import service.CalculateShell;

public class User {
    static ExchangePool pool = new ExchangePool();

    public static void main(String[] args)  {

        for (Pair pair: Pair.values()) {
            pool.addAll(ExchangePoolPoloneix.getListOrder(PoloneixAPI.get(pair)));
        }

        CalculateShell calculateShell1 = new CalculateShell(Unit.BTC, 3);
        System.out.println(calculateShell1.getAllChain());

        /*List<Pair> pairs;
        UnitCalculate unitCalculate = new UnitCalculate(Unit.USDT);
        pairs = unitCalculate.findPairs();
        System.out.println(pairs);
        System.out.println("<_________________>");
        for (Pair pair : pairs) {
            CalculateShell shell = new CalculateShell(Unit.USDT, pair,4);
            List<PairShell> pairShells = shell.getAllChain();
            System.out.println(pairShells);
            System.out.println("_____");
*/

    }



    }
