package domwong.mondo.lambda.incoming;

public interface Event {
    String getType();
    String getMessage();
}
