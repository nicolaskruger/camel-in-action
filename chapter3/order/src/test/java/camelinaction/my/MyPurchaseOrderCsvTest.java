package camelinaction.my;

import camelinaction.PurchaseOrder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.List;

public class MyPurchaseOrderCsvTest extends CamelTestSupport {

    public static final String MOCK_END = "mock:end";
    public static final String FILE_SRC_TEST_RESOURCE_NOOP_TRUE_FILE_NAME_ORDER_CSV = "file:src/test/resources?noop=true&fileName=order.csv";
    protected class CsvBean{
        public PurchaseOrder bean(List<String> list){
            return new PurchaseOrder(
                    list.get(0),
                    Double.parseDouble(list.get(2)),
                    Double.parseDouble(list.get(1))
            );
        }
    }

    private PurchaseOrder getInfo(MockEndpoint mockEndpoint, Integer index){
        return mockEndpoint
                .getReceivedExchanges()
                .get(index)
                .getIn()
                .getBody(PurchaseOrder.class);
    }
    private void assertPurchaseOrder(PurchaseOrder purchaseOrder,
                                     String name,
                                     double amount,
                                     double price){
        assertEquals(purchaseOrder.getAmount(), amount, 0.1);
        assertEquals(purchaseOrder.getPrice(), price, 0.1);
        assertEquals(purchaseOrder.getName(), name);
    }

    private void mockEndPoin(MockEndpoint mockEndpoint) throws InterruptedException {
        mockEndpoint
                .expectedMessageCount(2);
        assertMockEndpointsSatisfied();
        assertPurchaseOrder(
                getInfo(mockEndpoint, 0),
                "Camel in Action",
                6999.0,
                1.0
        );
        assertPurchaseOrder(
                getInfo(mockEndpoint, 1),
                "Activemq in Action",
                4495.0,
                2.0
        );
    }

    @Test
    public void test() throws InterruptedException {
        mockEndPoin(getMockEndpoint(MOCK_END));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(FILE_SRC_TEST_RESOURCE_NOOP_TRUE_FILE_NAME_ORDER_CSV)
                        .unmarshal().csv()
                        .split(body())
                        .bean(new CsvBean())
                        .to(MOCK_END);
            }
        };
    }
}
