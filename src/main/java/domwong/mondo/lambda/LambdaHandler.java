package domwong.mondo.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;

import domwong.mondo.lambda.incoming.Event;
import domwong.mondo.lambda.incoming.EventUtils;

public class LambdaHandler {
    public static final String ACCOUNT_SID = ""; // replace with your twilio account SID
    public static final String AUTH_TOKEN = ""; // replace with your twilio auth token

    public static void myHandler(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer);
        } catch (IOException e) {
            outputStream.write(e.getMessage().getBytes());
            return;
        }
        String incoming = writer.toString();
        logger.log("received : " + incoming);

        Event ev = null;
        try {
            ev = EventUtils.parse(incoming);
        } catch (Exception e) {
            String msg = "Exception parsing incoming " + incoming + " " + e.getMessage();
            logger.log(msg);
            outputStream.write(msg.getBytes());
            return;
        }

        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        Account account = client.getAccount();

        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", "")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", "")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("Body", ev.getMessage()));
        try {
            Message sms = messageFactory.create(params);
        } catch (TwilioRestException e) {
            outputStream.write(e.getErrorMessage().getBytes());
            return;
        }

        outputStream.write(ev.getMessage().getBytes());
    }
}
