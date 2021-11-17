package camelinaction;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import javax.jms.ConnectionFactory;

public class MyOrderRouterWithWireTapTest extends CamelTestSupport {

    public static final String TCP_LOCALHOST_61616 = "tcp://localhost:61616";
    public static final String FILE_SRC_DATA_NOOP_TRUE = "file:src/data?noop=true";
    public static final String JMS_START = "jms:start";
    public static final String JMS_WIRE = "jms:wire";
    public static final String JMS_XML = "jms:xml";
    public static final String XML_$ = "^.*xml$";
    public static final String CAMEL_FILE_NAME = "CamelFileName";
    public static final String JSON_CSV_$ = "^.*(json|csv)$";
    public static final String JMS_JSON_CSV = "jms:json-csv";
    public static final String JMS_BAD = "jms:bad";
    public static final String MOCK_WIRE = "mock:wire";
    public static final String MOCK_XML = "mock:xml";

    @Override
    protected CamelContext createCamelContext() throws Exception {

        CamelContext camelContext = super.createCamelContext();

        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(TCP_LOCALHOST_61616);

        camelContext.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        return camelContext;
    }

    @Test
    public void test(){
        getMockEndpoint(MOCK_WIRE)
                .expectedMessageCount(1);
        getMockEndpoint(MOCK_XML)
                .expectedMessageCount(1);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            private Predicate fileNameRegex(String regex){
                return header(CAMEL_FILE_NAME).regex(regex);
            }
            @Override
            public void configure() throws Exception {
                from(FILE_SRC_DATA_NOOP_TRUE)
                        .to(JMS_START);
                from(JMS_START)
                        .wireTap(JMS_WIRE)
                        .choice()
                            .when(fileNameRegex(XML_$))
                                .to(JMS_XML)
                            .when(fileNameRegex(JSON_CSV_$))
                                .to(JMS_JSON_CSV)
                            .otherwise()
                                .to(JMS_BAD);
                from(JMS_WIRE)
                        .to(MOCK_WIRE);
                from(JMS_XML)
                        .to(MOCK_XML);
            }
        };
    }
}
