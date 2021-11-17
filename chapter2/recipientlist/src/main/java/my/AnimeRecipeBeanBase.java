package my;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AnimeRecipeBeanBase {

    private List<String> flclCharacters() {
        return Arrays.asList(
                "naota",
                "mamimi",
                "haruko"
        );
    }

    private List<String> oyasumiCharacters(){
        return Arrays.asList(
                "punpun",
                "aiko",
                "seky"
        );
    }

    private Map<String, String[]> inithializeMap(){
        return new HashMap<String, String[]>(){{
            Function<String,Consumer<? super String>> consumer = (anime) -> (character) -> {
                put(character, new String[]{anime});
            };
            oyasumiCharacters().forEach(
                    consumer.apply(ROUTES.OYASUMI_PUNPUN)
            );
            flclCharacters().forEach(
                    consumer.apply(ROUTES.FLCL)
            );
        }};
    }

    protected String[] route(String name){
        return inithializeMap().getOrDefault(name, new String[]{ROUTES.OTHERS});
    }

    abstract public String[] recipe(String name);
}
