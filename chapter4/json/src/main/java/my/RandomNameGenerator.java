package my;

import java.util.Arrays;
import java.util.List;

public class RandomNameGenerator {
    public static final String RANDOM_NAME_GENERATOR = "randomNameGenerator";
    public static final String RANDOM_NAME_GENERATOR_METHOD = "generate";
    private static final List<String> name = Arrays.asList("Mamimi","Naota","Haruko","Seki","Punpun", "Aiko");
    public String generate(){
        return name.get((int)(Math.random()*name.size()));
    }
}
