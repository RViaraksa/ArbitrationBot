package controller.parsing.json.exchange_pool;

public class SingletonGetPool {
    private static SingletonGetPool instance;
    public ExchangePool pool;

    private SingletonGetPool(ExchangePool pool){
        this.pool = pool;
    }

    public static SingletonGetPool getInstance(ExchangePool pool){
        if (instance == null)
            instance = new SingletonGetPool(pool);
        return instance;
    }


}
