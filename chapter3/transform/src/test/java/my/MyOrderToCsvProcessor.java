package my;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyOrderToCsvProcessor extends MyOrderToCsv implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody(
                insertCommas(exchange.getIn().getBody(String.class)
                                .replaceAll(" ","")
                                .replaceAll("@",","))
        );
    }
}
