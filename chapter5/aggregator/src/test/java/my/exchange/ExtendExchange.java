package my.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import my.pokemon.Pokemon;
import my.pokemon.Power;
import org.apache.camel.Exchange;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class ExtendExchange {
    private final Exchange exchange;
    public  <T> T getValue( Class<T> type){
        return exchange.getIn().getBody(type);
    }
    public <T> T getHeader(String name, Class<T> type){
        return exchange.getIn().getHeader(name, type);
    }
    public Date getDate(){
        return getValue(Date.class);
    }
    public List<Date> getDateList(){
        return  getValue(List.class);
    }
    public Power getPower(){return getValue(Power.class);}
    public Pokemon getPokemon() {return getValue(Pokemon.class);}
    public String getStringHeader(String name) {return  exchange.getIn().getHeader(name, String.class);}
    public  <T> Exchange setBody(T body){
        exchange.getIn().setBody(body);
        return exchange;
    }
}
