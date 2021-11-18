package bean;

public enum OperationType {
    ASK("ask"), BID("bid");

    private final String type;

    OperationType(String type) {
        this.type = type;
    }

}
