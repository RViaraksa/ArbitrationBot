package controller.parsing.json.request;

import bean.Pair;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.parsing.json.bean.JsonObject;
import controller.parsing.json.bean.JsonObjectFactory;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class PoloneixAPI {

    public static JsonObject get(Pair pair){
        JsonObject jsonObject = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonObject = JsonObjectFactory.getJsonObject(pair);
            URL url = requestAPI(pair);
            jsonObject = mapper.readValue(url, jsonObject.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject ;
    }

    private static URL requestAPI(Pair pair){
        StringBuffer address = new StringBuffer("https://poloniex.com/public?command=returnOrderBook&currencyPair=");
        address.append(pair.getType());
        address.append("&depth=10");
        URL url = null;
        try {
            url = new URL(address.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

}
