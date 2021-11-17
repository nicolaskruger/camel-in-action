package camelinaction.my;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import javax.jms.ConnectionFactory;
import java.util.Arrays;

public class MyOrderRouterOtherwiseTest extends CamelTestSupport {

    private static final String ACTIVE_MQ_URL = "tcp://localhost:61616";
    private static final String FILE = "file:src/data_my?noop=true";
    private static final String FILA_DE_ENTRADA = "jms:fila-de-entrada";
    private static final String FILA_JSON = "jms:fila-json";
    private static final String FILA_XML = "jms:fila-xml";
    private static final String FILA_CSV = "jms:fila-csv";
    private static final String FILA_BAD = "jms:fila-bad";
    private static final String CAMEL_FILE_NAME = "CamelFileName";
    private static final String MOCK_JSON = "mock:json";
    private static final String MOCK_CSV = "mock:csv";
    private static final String MOCK_xml = "mock:xml";
    private static final String MOCK_BAD = "mock:bad";

    @Override
    protected CamelContext createCamelContext() throws Exception {

        CamelContext camelContext = super.createCamelContext();

        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(ACTIVE_MQ_URL);
        camelContext.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory)
        );

        return camelContext;
    }

    @Test
    public void  test() throws InterruptedException {
        getMockEndpoint(MOCK_JSON)
                .expectedMessageCount(1);
        getMockEndpoint(MOCK_CSV)
                .expectedMessageCount(1);
        getMockEndpoint(MOCK_xml)
                .expectedMessageCount(1);
        getMockEndpoint(MOCK_BAD)
                .expectedMessageCount(2);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            private void routeMock(String fila, String mock){
                from(fila)
                        .log(String.format("recebe da fila %s {body}",fila))
                        .to(mock);
            }

            @Override
            public void configure() throws Exception {

                from(FILE)
                        .to(FILA_DE_ENTRADA);

                from(FILA_DE_ENTRADA)
                        .choice()
                        .when(header(CAMEL_FILE_NAME).endsWith(".json"))
                            .to(FILA_JSON)
                        .when(header(CAMEL_FILE_NAME).endsWith(".xml"))
                            .to(FILA_XML)
                        .when(header(CAMEL_FILE_NAME).endsWith(".csv"))
                            .to(FILA_CSV)
                        .otherwise()
                            .to(FILA_BAD);
                class Temp {
                    public Temp(String fila, String mock) {
                        this.fila = fila;
                        this.mock = mock;
                    }

                    String fila;
                    String mock;
                }
                Arrays.asList(
                                new Temp(FILA_CSV, MOCK_CSV),
                                new Temp(FILA_JSON, MOCK_JSON),
                                new Temp(FILA_XML, MOCK_xml),
                                new Temp(FILA_BAD, MOCK_BAD))
                        .forEach(temp -> {
                            routeMock(temp.fila, temp.mock);
                        });


            }
        };
    }
}
