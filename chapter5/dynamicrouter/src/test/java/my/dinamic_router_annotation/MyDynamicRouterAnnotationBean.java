package my.dinamic_router_annotation;

import org.apache.camel.DynamicRouter;
import org.apache.camel.Exchange;
import org.apache.camel.Header;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyDynamicRouterAnnotationBean {

    public static final String FIND = "find";
    public static final String METHOD = "route";

    @DynamicRouter
    public String route(
            String body,
            @Header(Exchange.SLIP_ENDPOINT) String previos
    ){
            return proccess(body, previos);
    }
    private String mock(String val){
        return "mock://"+val;
    }
    private String getMockName(String previous){
        Matcher matcher = Pattern.compile("mock://(.+)").matcher(previous);
        matcher.find();
        return matcher.group(1);
    }
    private String proccess(String body, String previous){
        String[] split = body.split(",");
        if(previous == null){
            return split.length >= 1 ? mock(split[0]) : null;
        }
        String mockName = getMockName(previous);
        String next = Arrays.stream(split).reduce("", (acc, curr)->{
           if(curr.equals(mockName)){
               return FIND;
           }
           if(acc.equals(FIND)){
               return curr;
           }
           return acc;
        });
        return next.equals(FIND)?null:mock(next);
    }
}
