package my.split_bean;

import lombok.var;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MyPokemonSpliterTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String SPLIT = "split";
    public static final String MOCK_END = "mock:end";
    public static final String DEBBUG = "debbug";

    private PokemonDto pokemonBuilder(String name, Integer damage, String... powers){
        return PokemonDto.builder()
                .name(name)
                .powerDtos(Arrays.stream(powers).map(p ->
                        PowerDto.builder()
                                .damage(damage)
                                .name(p)
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

    @Test
    public void test() throws InterruptedException {


        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);

        Arrays.asList(
                pokemonBuilder("picachu", 20,"thunder atack", "eletric shock"),
                pokemonBuilder("charmander", 25, "fire flame", "fire ball")
        ).forEach( pokemon -> {
            template.sendBody(DIRECT_START, pokemon);
        });

        assertMockEndpointsSatisfied();

        mockEndpoint.expectedMessageCount(4);


    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .split().method(PokemonSpliter.class, SPLIT)
                        .bean(DebbugPokemon.class)
                        .to(MOCK_END);
            }
        };
    }
}
