package my;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import static my.JSonCatService.*;
import static my.RandomNameGenerator.*;
import static my.JSonRouter.*;

public class JsonCatTest extends CamelTestSupport {
    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndiRegistry = super.createRegistry();

        jndiRegistry.bind(RANDOM_NAME_GENERATOR, new RandomNameGenerator());

        jndiRegistry.bind(JSON_CAT_SERVICE, new JSonCatService());

        return jndiRegistry;
    }

    @Test
    public void test(){
        String json = "{\"name\":\"geleia\",\"age\":12}";
        String response = template.requestBody(DIRECT_START, json, String.class);
        assertNotNull(response);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new JSonRouter();
    }
}
