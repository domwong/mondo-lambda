package domwong.mondo.lambda.incoming;

import static org.junit.Assert.fail;

import org.junit.Test;

import junit.framework.Assert;

public class TransactionEventTest {

    @Test
    public void test() {
        String testIncoming = "{`type`: `transaction.created`, `data`: {`amount`: -350,`created`: `2015-09-04T14:28:40Z`,`currency`: `GBP`,`description`: `Ozone Coffee Roasters`,`id`: `tx_00008zjky19HyFLAzlUk7t`}}".replace('`', '"');
        try {
            Event t = EventUtils.parse(testIncoming);
            Assert.assertEquals("transaction.created", t.getType());
            Assert.assertEquals(t.getMessage(), "Spent 3.50GBP at Ozone Coffee Roasters");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("Failed " + e.getMessage());
        }

    }

}
