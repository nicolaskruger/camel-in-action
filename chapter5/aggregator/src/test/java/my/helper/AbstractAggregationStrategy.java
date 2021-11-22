package my.helper;

import my.exchange.ExtendExchange;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public abstract class AbstractAggregationStrategy implements AggregationStrategy {

    public abstract Exchange extendAggregate(ExtendExchange oldExchange, ExtendExchange newExchange);

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        return extendAggregate(new ExtendExchange(oldExchange), new ExtendExchange(newExchange));
    }
}
