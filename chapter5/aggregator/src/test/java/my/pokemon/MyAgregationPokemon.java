package my.pokemon;

import my.date.AgregationDate;
import my.date.DateToTimeStampBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MyAgregationPokemon extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_END = "mock:end";
    public static final String PICACHU = "picachu";
    public static final String CHARMANDER = "charmander";

    private void sendList(String pokemonName, Power ...powers){
        for (int i = 0; i < powers.length; i++) {
            sendPokemon(pokemonName, powers[i]);
        }
    }

    private void sendPokemon(String pokemonName, Power power){
        template.sendBodyAndHeader(DIRECT_START, power, AgregationPokemon.NAME, pokemonName);
    }

    private Pokemon getPokemon(int i, MockEndpoint mockEndpoint){
        return mockEndpoint.getExchanges().get(i).getIn().getBody(Pokemon.class);
    }

    private void assertPokemon(Pokemon pokemon, String pokemonName){
        assertEquals(pokemon.getName(), pokemonName);
    }

    @Test
    public void test() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);
        sendList(PICACHU, new Power("thunder ligth", 10), new Power("thunder storm", 20));
        sendList(CHARMANDER, new Power("fire ball", 10), new Power("flame shot", 20));
        mockEndpoint.expectedMessageCount(2);
        assertMockEndpointsSatisfied();
        Pokemon picachu = getPokemon(0, mockEndpoint);
        Pokemon charmander = getPokemon(1, mockEndpoint);
        assertPokemon(picachu, PICACHU);
        assertPokemon(charmander, CHARMANDER);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .aggregate(header(AgregationPokemon.NAME), new AgregationPokemon())
                        .completionSize(3).completionTimeout(5000)
                        .bean(PokemonBean.class)
                        .to(MOCK_END);
            }
        };
    }
}
