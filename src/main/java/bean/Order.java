package bean;

public class Order{
    private Pair pair;
    private OperationType type;
    private double price;
    private double count;

    public Order(Pair pair, OperationType type, double price, double count) {
        this.pair = pair;
        this.type = type;
        this.price = price;
        this.count = count;
    }

    public Pair getPair() {
        return pair;
    }

    public void setPair(Pair pair) {
        this.pair = pair;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double calculateSumm() {
        return this.price * this.count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(pair);
        sb.append("{ type=").append(type);
        sb.append(", price=").append(price);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
