package bean;

import java.util.Optional;
import java.util.stream.Stream;

public enum Unit {
    ETH("ETH"), BTC("BTC"), BCH("BCH"), ETC("ETC"), USDT("USDT");


    private final String type;

    Unit(String type) {
        this.type = type;
    }

    public static Optional<Unit> of(String type) {
        return Stream.of(Unit.values())
                .filter(e -> e.type.equalsIgnoreCase(type))
                .findFirst();
    }

    public String getType() {
        return type;
    }

}
