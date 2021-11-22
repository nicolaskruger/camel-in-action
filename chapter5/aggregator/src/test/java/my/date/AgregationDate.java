package my.date;

import my.exchange.ExtendExchange;
import my.helper.AbstractAggregationStrategy;
import org.apache.camel.Exchange;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AgregationDate extends AbstractAggregationStrategy {


    @Override
    public Exchange extendAggregate(ExtendExchange oldExchange, ExtendExchange newExchange) {
        if(oldExchange.getExchange() == null){
            Date date = newExchange.getDate();
            return newExchange.setBody(Arrays.asList(date));
        }

        List<Date> dateList = oldExchange.getDateList().stream().map(v -> v).collect(Collectors.toList());

        Date date = newExchange.getDate();

        dateList.add(date);

        dateList.sort(Date::compareTo);

        return newExchange.setBody(dateList);
    }
}
