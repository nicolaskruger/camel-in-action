package my.custom_loadbalance;

import org.apache.camel.Exchange;
import org.apache.camel.processor.loadbalancer.SimpleLoadBalancerSupport;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MyValorantCustonLoadBalance extends SimpleLoadBalancerSupport {

    public static final String WEAPON = "weapon";

    private static final Map<ValorantWeapons, Integer> swith = new HashMap(){{
        ValorantWeapons.toSortList()
                .forEach( w -> {
                    put(w, w.getNumber());
                });
    }
    };

    @Override
    public void process(Exchange exchange) throws Exception {
        getProcessors().get(
                swith.get(exchange.getIn().getHeader(WEAPON, ValorantWeapons.class))
        ).process(exchange);
    }
}
