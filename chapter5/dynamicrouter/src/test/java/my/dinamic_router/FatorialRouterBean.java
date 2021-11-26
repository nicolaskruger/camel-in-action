package my.dinamic_router;

import org.apache.camel.Exchange;
import org.apache.camel.Header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FatorialRouterBean {
    public String route(String body, @Header(Exchange.SLIP_ENDPOINT) String previous){
        return fatorial(body, previous);
    }

    private Matcher buildMatcher(String regex, String text){
        return Pattern.compile(regex).matcher(text);
    }

    private Integer getNumber(String previos){
        Matcher matcher = buildMatcher("mock://(\\d+)!",previos);
        matcher.find();
        return Integer.parseInt(matcher.group(1));
    }

    private Integer getFatorial(String preivios){
        Matcher matcher = buildMatcher("\\d+$", preivios);
        matcher.find();
        return Integer.parseInt(
            matcher.group(0)
        );
    }

    private String fatorial(String body,String previos){
        if(body.contains("!"))
            return null;
        Integer number02 = Integer.parseInt(body);
        if(number02 == 0){
            return "language://0!_1";
        }
        if(previos == null){
            return "mock:0!_1";
        }
        Integer number01 = getNumber(previos);
        Integer fatorial = getFatorial(previos);
        Integer nextFatorial = fatorial*(number01+1);
        if(number01 == number02){
            return "language://simple:"+number01+"!_"+fatorial;
        }
        return "mock://"+(number01+1)+"!_"+nextFatorial;
    }

}
