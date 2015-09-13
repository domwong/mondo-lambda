package domwong.mondo.lambda.incoming;

import java.util.Formatter;
import java.util.Locale;

public class TransactionEvent implements Event {
    private String type;

    private class Data {
        private int amount; // This is in minor currency
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
        Formatter fmt = new Formatter(Locale.UK);
        StringBuilder sb = new StringBuilder("Spent ");
        sb.append(fmt.format("%.2f", (float)-this.data.amount/100)).append(this.data.currency).append(" at ").append(this.data.description);
        return sb.toString();
    }

}
