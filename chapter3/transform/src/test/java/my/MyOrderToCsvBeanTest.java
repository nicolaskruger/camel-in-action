package my;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.io.File;

public class MyOrderToCsvBeanTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String FILE_NAME = "abacaxi";
    public static final String FILE_PATH = String.format("target/my/recived/file-%s.csv",FILE_NAME);
    public static final String HEADER = "myFile";
    public static final String FILE_TARGET_MY_RECIVED_FILE_NAME_FILE_$_HEADER_MY_FILE_CSV = String.format("file:target/my/recived?fileName=file-${header.%s}.csv", HEADER);
    public static final String REQUEST = "0000004444000001212320091208  1217@1478@2132";
    public static final String RESPONSE = "0000004444,0000012123,20091208,1217,1478,2132";
    public static final String FILE_SHOUD_EXISTS = "File shoud exists";

    private void assertFile(File file){
        assertTrue(FILE_SHOUD_EXISTS, file.exists());
        assertEquals(RESPONSE, context.getTypeConverter().convertTo(String.class, file));
    }

    @Test
    public void test(){
        template.sendBodyAndHeader(DIRECT_START, REQUEST, HEADER, FILE_NAME);
        assertFile( new File(FILE_PATH));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .bean(new MyOrderToCsvBean())
                        .to(FILE_TARGET_MY_RECIVED_FILE_NAME_FILE_$_HEADER_MY_FILE_CSV);
            }
        };
    }
}
