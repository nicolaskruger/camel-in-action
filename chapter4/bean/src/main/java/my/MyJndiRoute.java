package my;

import org.apache.camel.builder.RouteBuilder;

public class MyJndiRoute extends RouteBuilder {

    public static final String DIRECT_START = "direct:start";
    public static final String BUSS = "buss";
    public static final String METHOD_TU_EH = "tuEh";

    @Override
    public void configure() throws Exception {
        from(DIRECT_START)
                .bean(BUSS, METHOD_TU_EH);
    }
}
