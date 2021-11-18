package controller.parsing.json.bean;

import bean.Pair;


public abstract class JsonObjectFactory {
    public static JsonObject getJsonObject(Pair pair){
        JsonObject jsonObject = null;
        switch (pair){
            case USDT_ETH:
                jsonObject = new JsonUSDT_ETH();
                break;
            case BTC_BCH:
                jsonObject = new JsonBTC_BCH();
                break;
            case BTC_ETH:
                jsonObject = new JsonBTC_ETH();
                break;
            case ETH_ETC:
                jsonObject = new JsonETH_ETC();
                break;
            case USDT_BTC:
                jsonObject = new JsonUSDT_BTC();
                break;
        }
        return jsonObject;
    }
}
