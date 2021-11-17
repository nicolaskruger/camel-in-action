package camelinaction;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

public class RouteBuilderExample {

    public static void main(String args[]) throws Exception {
        CamelContext context = new DefaultCamelContext();

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                // try auto complete in your IDE on the line below
                from("timer:timer")
                        .process((exchange)->{
                            System.out.println("We ar verry gay" + exchange.getIn().getHeader("CamelFileName"));
                        })
                        .to("jms:queue");

            }
        });
        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}
