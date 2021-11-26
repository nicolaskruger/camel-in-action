package my.random_load_balancer;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyRandomLoadBalanceTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String ROUTES = "laranja,maca,banna";
    public static final String DIRECT_S = "direct:%s";
    public static final String MOCK_S = "mock:%s";

    private String toDirectRoutes(String route){
        return String.format(DIRECT_S, route);
    }

    private String toMapDirect(String route){
        return String.format(MOCK_S, route);
    }

    private String[] splitRoute(){
        return ROUTES.split(",");
    }

    private List<String> builder(Function<String, String> oper){
        return Arrays.stream(splitRoute())
                .map(v -> oper.apply(v))
                .collect(Collectors.toList());
    }

    private List<String> directRoutes = builder((v)->toDirectRoutes(v));
    private List<String> mockRoute = builder((v) -> toMapDirect(v));

    @Test
    public void test(){
        Arrays.stream("punpun;aiko;sashi;seky".split(";"))
                .collect(Collectors.toList())
                .forEach(v -> {
                    template.sendBody(DIRECT_START, v);
                });
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .loadBalance().random()
                            .to(directRoutes.toArray(new String[directRoutes.size()]))
                        .end();
                for (int i = 0; i < mockRoute.size(); i++) {
                    from(directRoutes.get(i))
                            .log("body ${body}")
                            .to(mockRoute.get(i));
                }
            }
        };
    }
}
