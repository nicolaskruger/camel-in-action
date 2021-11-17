package my;

import junit.framework.TestCase;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.junit.Test;

import javax.xml.transform.Templates;

import static my.MyJndiRoute.*;

public class MySimpleRegistryTest extends TestCase {

    private CamelContext camelContext;
    private ProducerTemplate template;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        SimpleRegistry simpleRegistry = new SimpleRegistry();
        simpleRegistry.put(BUSS, new BussHelloBean());

        camelContext = new DefaultCamelContext(simpleRegistry);

        template  = camelContext.createProducerTemplate();

        camelContext.addRoutes(new MyJndiRoute());

        camelContext.start();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        template.stop();
        camelContext.stop();
    }

    @Test
    public void test(){
        String hello = template.requestBody(DIRECT_START, "nicolas", String.class);
        assertEquals("Tu eh nicolas ?", hello);
    }
}
