package my.split_aggregate;

import java.util.HashMap;
import java.util.Map;

public class WordConverter {
    private final Map<String, String> map = new HashMap<String, String>(){{
        put("A", "bulbasaur");
        put("B", "squirdle");
        put("C", "charmander");
    }};
    public String converter(String word){
        if("E".equals(word))
            throw new RuntimeException("if 'E' throws excption");
        return map.getOrDefault(word, "pikachu");
    }
}
