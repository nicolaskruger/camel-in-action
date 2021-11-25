package my.routing_slip_bean;

import org.apache.camel.RoutingSlip;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MyRoutingSlipBean {
    @RoutingSlip
    public String slip(String route){
        return Arrays.stream(route.split(","))
                .map(r -> "mock:"+r)
                .collect(Collectors.joining(","));
    }
}
