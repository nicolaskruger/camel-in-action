package camelinaction.my;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import javax.jms.ConnectionFactory;

public class MyOrderRouterWithParallelMulticastTest extends CamelTestSupport {

    public static final String FILE_SRC_DATA_MY_NOOP_TRUE = "file:src/data-my?noop=true";
    public static final String JMS_FILA_ENTRADA = "jms:fila-entrada";
    public static final String CAMEL_FILE_NAME = "CamelFileName";
    public static final String JMS_FILA_JSON = "jms:fila-json";
    public static final String JMS_FILA_XML = "jms:fila-xml";
    public static final String JMS_FILA_BAD = "jms:fila-bad";
    public static final String JMS_UAT = "jms:uat";
    public static final String JMS_PRD = "jms:prd";
    public static final String MOCK_UAT = "mock:uat";
    public static final String MOCK_PRD = "mock:prd";

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
        getMockEndpoint(MOCK_PRD)
                .expectedMessageCount(1);
        getMockEndpoint(MOCK_UAT)
                .expectedMessageCount(1);
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            private Predicate fileNameEndsWith(String end){
                return header(CAMEL_FILE_NAME).endsWith(end);
            }

            @Override
            public void configure() throws Exception {
                from(FILE_SRC_DATA_MY_NOOP_TRUE)
                        .to(JMS_FILA_ENTRADA);

                from(JMS_FILA_ENTRADA)
                        .choice()
                        .when(fileNameEndsWith(".json"))
                        .to(JMS_FILA_JSON)
                        .when(fileNameEndsWith(".xml"))
                        .to(JMS_FILA_XML)
                        .otherwise()
                        .to(JMS_FILA_BAD);
                from(JMS_FILA_JSON)
                        .multicast()
                        .parallelProcessing()
                        .to(JMS_UAT, JMS_PRD);

                from(JMS_UAT)
                        .to(MOCK_UAT);
                from(JMS_PRD)
                        .to(MOCK_PRD);
            }
        };
    }
}


