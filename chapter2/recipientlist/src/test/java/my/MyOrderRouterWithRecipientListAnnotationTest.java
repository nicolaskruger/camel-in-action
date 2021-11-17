package my;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import javax.jms.ConnectionFactory;

public class MyOrderRouterWithRecipientListAnnotationTest extends CamelTestSupport {

    public static final String CAMEL_FILE_NAME = "CamelFileName";
    public static final String JSON = ".json";
    public static final String XML = ".xml";
    public static final String RECIPENTS = "recipents";
    public static final String METHOD = "recipe";
    public static final String FLCL_$_BODY = "FLCL : \n${body}";
    public static final String OYASUMI_PUNPUN_$_BODY = "Oyasumi Punpun : \n${body}";

    @Override
    protected CamelContext createCamelContext() throws Exception {

        CamelContext camelContext = super.createCamelContext();

        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");

        camelContext.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory)
        );

        return camelContext;
    }

    @Test
    public void test() throws InterruptedException {
        getMockEndpoint(ROUTES.MOCK_FLCL)
                .expectedMessageCount(3);
        getMockEndpoint(ROUTES.MOCK_OYASUMI_PUNPUN)
                .expectedMessageCount(3);
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            private Predicate fileNameEndWith(String end){
                return header(CAMEL_FILE_NAME).endsWith(end);
            }

            @Override
            public void configure() throws Exception {
                from(ROUTES.FILE)
                        .to(ROUTES.START);
                from(ROUTES.START)
                        .choice()
                        .when(fileNameEndWith(JSON))
                        .to(ROUTES.FILA_JSON)
                        .when(fileNameEndWith(XML))
                        .to(ROUTES.FILA_XML)
                        .otherwise()
                        .to(ROUTES.FILA_BAD);
                from(ROUTES.FILA_JSON)
                        .bean(AnimeRecipiesBeanAnotation.class);
                from(ROUTES.FLCL)
                        .log(FLCL_$_BODY)
                        .to(ROUTES.MOCK_FLCL);
                from(ROUTES.OYASUMI_PUNPUN)
                        .log(OYASUMI_PUNPUN_$_BODY)
                        .to(ROUTES.MOCK_OYASUMI_PUNPUN);
            }
        };
    }
}
