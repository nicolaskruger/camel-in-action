package camelinaction.my;

import camelinaction.bindy.my.Cat;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MyPurchseOrderVelocityTest extends CamelTestSupport {

    public static final String NAME = "name";
    public static final String LAST_NAME = "last-name";
    public static final String VELOCITY_CAMELINACTION_NEW_MAIL_VM = "velocity:camelinaction/new-mail.vm";
    public static final String LOG_MAIL = "log:mail";
    public static final String MOCK_END = "mock:end";
    public static final String DIRECT_START = "direct:start";
    public static final String NICOLAS = "nicolas";
    public static final String KRUGER = "kruger";

    @Test
    public void test() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);
        mockEndpoint.message(0).header(NAME).isEqualTo(NICOLAS);
        mockEndpoint.message(0).header(LAST_NAME).isEqualTo(KRUGER);
        mockEndpoint.message(0).body().contains("");
        template.sendBody(DIRECT_START, Cat.builder()
                .color("black and white")
                .name("geleia")
                .age(12));
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {

        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .setHeader(NAME, constant(NICOLAS))
                        .setHeader(LAST_NAME, constant(KRUGER))
                        .to(VELOCITY_CAMELINACTION_NEW_MAIL_VM)
                        .to(LOG_MAIL)
                        .to(MOCK_END);

            }
        };
    }
}
