package my;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class GenderPredicate extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_HETERO = "mock:hetero";
    public static final String IS_HETERO = "isHetero";
    public static final String IS_HOMO = "isHomo";
    public static final String MOCK_HOMO = "mock:homo";
    public static final String IS_BI = "isBi";
    public static final String MOCK_BI = "mock:bi";
    public static final String MOCK_OTHER = "mock:other";

    @Test
    public void test() throws InterruptedException {
        getMockEndpoint(MOCK_BI)
                .expectedMessageCount(0);
        getMockEndpoint(MOCK_HOMO)
                .expectedMessageCount(0);
        getMockEndpoint(MOCK_OTHER)
                .expectedMessageCount(0);
        getMockEndpoint(MOCK_HETERO)
                .expectedMessageCount(1);

        String json = "{\"gender\":\"hetero\"}";

        template.sendBody(DIRECT_START, json);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .choice()
                        .when(method(Gender.class, IS_HETERO)).to(MOCK_HETERO)
                        .when(method(Gender.class, IS_HOMO)).to(MOCK_HOMO)
                        .when(method(Gender.class, IS_BI)).to(MOCK_BI)
                        .otherwise().to(MOCK_OTHER);
            }
        };
    }
}
