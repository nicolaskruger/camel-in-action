package my;

import org.apache.camel.builder.RouteBuilder;
import static my.JSonCatService.JSON_CAT_SERVICE;
import static my.JSonCatService.JSON_CAT_SERVICE_METHOD;

public class JSonRouter extends RouteBuilder {

    public static final String DIRECT_START = "direct:start";

    @Override
    public void configure() throws Exception {
        from(DIRECT_START)
                .bean(JSON_CAT_SERVICE, JSON_CAT_SERVICE_METHOD);
    }
}
