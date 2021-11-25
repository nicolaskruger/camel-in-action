package my.split_aggregate;

import java.util.Arrays;
import java.util.List;

public class SpliterWord {
    public List<String> split(String word){
        return Arrays.asList(word.split(","));
    }
}
