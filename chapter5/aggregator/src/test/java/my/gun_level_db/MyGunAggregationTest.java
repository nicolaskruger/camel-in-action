package my.gun_level_db;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.leveldb.LevelDBAggregationRepository;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spi.AggregationRepository;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.toolbox.AggregationStrategies;
import org.junit.Test;

import java.util.Arrays;

public class MyGunAggregationTest extends CamelTestSupport {

    public static final String BEAN = "bean";
    public static final String MOCK_END = "mock:end";
    public static final String DIRECT_START = "direct:start";
    public static final String VANDAL = "vandal";
    public static final String PHANTON = "phanton";
    public static final String OPERATOR = "operator";
    public static final String DATA_GUN_DAT = "data/gun.dat";
    public static final String MY_GUN = "mygun";

    private <T> void sendWeapon(T weapon){
        template.sendBody(DIRECT_START, weapon);
    }

    @Test
    public void test() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);
        Arrays.asList(
                Gun.onlyName(VANDAL), new Bullet(VANDAL), new Bullet(VANDAL),
                Gun.onlyName(PHANTON), new Bullet(PHANTON), new Bullet(PHANTON),
                Gun.onlyName(OPERATOR), new Bullet(OPERATOR), new Bullet(OPERATOR)
                ).forEach(v -> {
                    sendWeapon(v);
        });
        mockEndpoint.expectedMessageCount(1);
        assertMockEndpointsSatisfied();
        assertNotNull(
                mockEndpoint.getExchanges().get(0).getIn().getBody()
        );
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                AggregationRepository aggregationRepository =
                        new LevelDBAggregationRepository(MY_GUN, DATA_GUN_DAT);
                from(DIRECT_START)
                        .aggregate(constant(true),AggregationStrategies.bean(new GunAggregation(), BEAN))
                        .aggregationRepository(aggregationRepository)
                        .completionSize(9)
                        .bean(DebbugGun.class)
                        .to(MOCK_END);
            }
        };
    }
}
