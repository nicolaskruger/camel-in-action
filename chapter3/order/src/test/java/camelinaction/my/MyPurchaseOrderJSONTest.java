package camelinaction.my;

import camelinaction.bindy.my.Cat;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MyPurchaseOrderJSONTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_END = "mock:end";
    public static final String BIND_BEAN = "bindBean";
    public static final String CREATE_CAT = "createCat";
    public static final String NAME = "name";
    public static final String COLOR = "color";
    public static final String AGE = "age";
    public static final String GELEIA = "geleia";
    public static final int CAT_AGE = 12;
    public static final String BLACK_WHITE = "black white";

    private class MyBean {
        public Cat createCat(@Header(NAME) String name,
                             @Header(COLOR) String color,
                             @Header(AGE) Integer age){
            return Cat.builder()
                    .name(name)
                    .age(age)
                    .color(color)
                    .build();
        }
    }
    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndiRegistry = super.createRegistry();
        jndiRegistry.bind(BIND_BEAN, new MyBean());
        return jndiRegistry;
    }

    @Test
    public void test() throws InterruptedException {
        template.sendBody(DIRECT_START, null);
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);
        mockEndpoint.expectedMessageCount(1);
//        Cat cat = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Cat.class);
//        assertEquals(java.util.Optional.ofNullable(cat.getAge()), CAT_AGE);
//        assertEquals(cat.getColor(), BLACK_WHITE);
//        assertEquals(cat.getName(), GELEIA);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .setHeader(NAME, constant(GELEIA))
                        .setHeader(AGE, constant(CAT_AGE))
                        .setHeader(COLOR, constant(BLACK_WHITE))
                        .bean(BIND_BEAN, CREATE_CAT)
                        .marshal().json(JsonLibrary.Jackson)
                        .log("\n body: \n ${body}")
                        .to(MOCK_END);
            }
        };
    }
}
