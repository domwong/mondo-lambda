package domwong.mondo.lambda.incoming;

public class TransactionEvent implements Event {
    private String type;

    private class Data {
        private int amount;
        private String created;
        private String currency;
        private String description;
        private String id;

    }

    private Data data;

    public String getType() {
        return type;
    }

    public String getMessage() {
        StringBuilder sb = new StringBuilder("Spent ");
        sb.append(-this.data.amount).append(this.data.currency).append(" at ").append(this.data.description);
        return sb.toString();
    }

}
