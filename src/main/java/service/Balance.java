package service;

import bean.Order;
import bean.Unit;

public class Balance {
    private double count;
    private Unit unit;

    public Balance(double count, Unit unit) {
        this.count = count;
        this.unit = unit;
    }

    public double getCount() {
        return count;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Balance{");
        sb.append("count=").append(count);
        sb.append(", unit=").append(unit);
        sb.append('}');
        return sb.toString();
    }
}
