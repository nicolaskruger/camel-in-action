package my;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class MyInvokeWithProcessorRoute extends RouteBuilder {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_END = "mock:end";

    @Override
    public void configure() throws Exception {
        from(DIRECT_START)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String name = exchange.getIn().getBody(String.class);
                        String buss = new BussHelloBean().tuEh(name);
                        exchange.getIn().setBody(buss);
                    }
                })
                .to(MOCK_END);
    }
}
