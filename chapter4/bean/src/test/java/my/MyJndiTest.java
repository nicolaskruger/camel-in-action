package my;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import static my.MyJndiRoute.*;
public class MyJndiTest extends CamelTestSupport {
    @Override
    protected JndiRegistry createRegistry() throws Exception {

        JndiRegistry jndiRegistry = super.createRegistry();

        jndiRegistry.bind(BUSS, new BussHelloBean());

        return jndiRegistry;
    }
    @Test
    public void test(){
        String response = template.requestBody(DIRECT_START, "nicolas", String.class);

        assertEquals(response, "Tu eh nicolas ?");
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new MyJndiRoute();
    }
}
