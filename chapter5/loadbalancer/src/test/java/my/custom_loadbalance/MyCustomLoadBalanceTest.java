package my.custom_loadbalance;

import lombok.var;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MyCustomLoadBalanceTest extends CamelTestSupport {

    public static final String DIRECT = "direct:";
    public static final String MOCK = "mock:";

    interface DebbugBean {
        public ValorantWeapons bean(ValorantWeapons valorantWeapons);
    }

    public static final String DIRECT_START = "direct:start";
    private final String[] directEndPoint = makeEndPoint(DIRECT);
    private final String[] mockEndPonint = makeEndPoint(MOCK);

    private String concatValue(String ...values){
      return Arrays.stream(values).collect(Collectors.joining());
    };

    private String[] makeEndPoint(String name){
        return ValorantWeapons.toSortList()
                .stream().map(v -> concatValue(name, v.getName()))
                .toArray(size -> new String[size]);
    }

    @Test
    public void test() throws InterruptedException {
        ValorantWeapons.toSortList()
                .forEach(w -> {
                    template.sendBody(DIRECT+w.getName(), w);
                    getMockEndpoint(MOCK+w.getName()).expectedMessageCount(1);
                    assertEquals(
                            w,
                            getMockEndpoint(MOCK+w.getName()).getExchanges().get(0).getIn()
                                    .getBody(ValorantWeapons.class)
                    );
                });
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .loadBalance(new MyValorantCustonLoadBalance())
                        .to(directEndPoint);
                for (int i = 0; i < directEndPoint.length; i++) {
                    from(directEndPoint[i])
                            .bean(new DebbugBean() {
                                @Override
                                public ValorantWeapons bean(ValorantWeapons valorantWeapons) {
                                    return valorantWeapons;
                                }
                            })
                            .to(mockEndPonint[i]);
                }
            }
        };
    }

}
