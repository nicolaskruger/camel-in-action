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
        return map.getOrDefault(word, "pikachu");
    }
}
