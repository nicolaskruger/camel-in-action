package my.pokemon;

import my.exchange.ExtendExchange;
import my.helper.AbstractAggregationStrategy;
import org.apache.camel.Exchange;

import java.util.Arrays;

public class AgregationPokemon extends AbstractAggregationStrategy {

    public static final String NAME = "name";

    @Override
    public Exchange extendAggregate(ExtendExchange oldExchange, ExtendExchange newExchange) {

        if(oldExchange.getExchange() == null){
            Power power = newExchange.getPower();

            String name = newExchange.getStringHeader(NAME);

            newExchange.setBody(Pokemon.builder().name(name).powers(Arrays.asList(power)).build());

            return newExchange.getExchange();
        }

        Pokemon pokemon = oldExchange.getPokemon().copy();

        pokemon.getPowers().add(newExchange.getPower());

        newExchange.setBody(pokemon);

        return newExchange.getExchange();
    }
}
