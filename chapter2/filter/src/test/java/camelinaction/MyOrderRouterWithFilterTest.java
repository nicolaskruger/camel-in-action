package camelinaction;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import javax.jms.ConnectionFactory;

public class MyOrderRouterWithFilterTest extends CamelTestSupport {

    public static final String FILE_SRC_DATA_MY_NOOP_TRUE = "file:src/data_my?noop=true";
    public static final String JMS_FILA_ENTRADA = "jms:fila-entrada";
    public static final String CAMEL_FILE_NAME = "CamelFileName";
    public static final String JMS_FILA_JSON = "jms:fila-json";
    public static final String JMS_BAD = "jms:bad";
    public static final String MOCK_JSON = "mock:json";
    public static final String MOCK_BAD = "mock:bad";

    @Override
    protected CamelContext createCamelContext() throws Exception {

        CamelContext camelContext = super.createCamelContext();

        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");

        camelContext.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        return camelContext;
    }

    @Test
    public void test() throws InterruptedException {
        getMockEndpoint(MOCK_BAD)
                .expectedMessageCount(0);
        getMockEndpoint(MOCK_JSON)
                .expectedMessageCount(1);
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(FILE_SRC_DATA_MY_NOOP_TRUE)
                        .to(JMS_FILA_ENTRADA);

                from(JMS_FILA_ENTRADA)
                        .choice()
                            .when(header(CAMEL_FILE_NAME).endsWith(".json"))
                                .to(JMS_FILA_JSON)
                            .otherwise()
                                .to(JMS_BAD);

                from(JMS_FILA_JSON)
                        .filter(jsonpath("[?(@.name != 'fio maravilha')]"))
                        .log("entrou na fila de json ${body}")
                        .log("${body} entrou na fila")
                        .to(MOCK_JSON);

                from(JMS_BAD)
                        .log("entrou na fila bad ${body}")
                        .to(MOCK_BAD);


            }
        };
    }
}
