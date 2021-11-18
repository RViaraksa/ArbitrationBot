package controller.parsing.json.bean;

import bean.Pair;

import java.util.List;

public abstract class JsonObject {
    private Pair pair ;
    private List<List<Double>> asks ;
    private List<List<Double>> bids ;
    private int isFrozen ;
    private int postOnly ;
    private int seq ;

    public JsonObject(Pair pair) {
        this.pair = pair;
    }


    public List<List<Double>> getAsks() {
        return asks;
    }

    public void setAsks(List<List<Double>> asks) {
        this.asks = asks;
    }

    public List<List<Double>> getBids() {
        return bids;
    }

    public void setBids(List<List<Double>> bids) {
        this.bids = bids;
    }

    public int getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(int isFrozen) {
        this.isFrozen = isFrozen;
    }

    public int getPostOnly() {
        return postOnly;
    }

    public void setPostOnly(int postOnly) {
        this.postOnly = postOnly;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Pair getPAIR() {
        return pair;
    }


}
