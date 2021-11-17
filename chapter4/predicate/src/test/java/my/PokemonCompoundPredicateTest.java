package my;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Predicate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.jsonpath.JsonPath;
import org.apache.camel.processor.validation.PredicateValidationException;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class PokemonCompoundPredicateTest extends CamelTestSupport {

    public static final String $_TYPE = "$.type";
    public static final String FIRE = "fire";
    public static final String $_NAME_SHARMANDER = "$[?(@.name == 'sharmander')]";
    public static final String $_HEADER_TRAINER_NICOLAS = "${header.trainer} == 'nicolas'";
    public static final String IS_FIRE = "isFire";
    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_VALID = "mock:valid";
    public static final int EXPECTED_COUNT = 1;
    public static final String TRAINER = "trainer";
    public static final String NICOLAS = "nicolas";

    public static boolean isFire(@JsonPath($_TYPE) String type){
        return FIRE.equals(type);
    }

    @Test
    public void testPokemonOk() throws InterruptedException {
        getMockEndpoint(MOCK_VALID)
                .expectedMessageCount(EXPECTED_COUNT);

        String json = "{\"name\":\"sharmander\",\"type\":\"fire\"}";
        template.sendBodyAndHeader(DIRECT_START, json, TRAINER, NICOLAS);
        assertMockEndpointsSatisfied();
    }
    @Test(expected = PredicateValidationException.class)
    public void testPokemonNotOk() throws InterruptedException, PredicateValidationException {
        try {
            String json = "{\"name\":\"pikashu\",\"type\":\"thunder\"}";
            template.sendBodyAndHeader(DIRECT_START, json, TRAINER, NICOLAS);
            assertMockEndpointsSatisfied();
        }catch (CamelExecutionException e){
            PredicateValidationException pve = assertIsInstanceOf(PredicateValidationException.class, e.getCause());
            throw pve;
        }

    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                Predicate valid = PredicateBuilder.and(
                        jsonpath($_NAME_SHARMANDER),
                        simple($_HEADER_TRAINER_NICOLAS),
                        method(PokemonCompoundPredicateTest.class, IS_FIRE)
                );

                from(DIRECT_START)
                        .validate(valid)
                        .to(MOCK_VALID);

            }
        };
    }
}
