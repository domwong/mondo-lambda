package domwong.mondo.lambda.incoming;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class EventUtils {

    public static Event parse(String json) throws Exception {
        Gson gson = new Gson();
        try {
            TypeDummy ev = gson.fromJson(json, TypeDummy.class);
            switch (ev.type) {
            case "transaction.created":
                return gson.fromJson(json, TransactionEvent.class);
            default:
                throw new Exception("Event type " + ev.type + " not recognised");
            }
        } catch (JsonSyntaxException e) {
            throw e;
        }
    }

    private static class TypeDummy {
        private String type;
    }
}