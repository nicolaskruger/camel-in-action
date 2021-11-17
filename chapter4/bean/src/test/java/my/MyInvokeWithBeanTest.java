package my;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import static my.MyInvokeWithBeanRoute.DIRECT_START;

public class MyInvokeWithBeanTest extends CamelTestSupport {

    @Test
    public void test(){
        String response = template.requestBody(DIRECT_START, "nicolas", String.class);

        assertEquals(response, "Tu eh nicolas ?");
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new MyInvokeWithBeanRoute();
    }
}
