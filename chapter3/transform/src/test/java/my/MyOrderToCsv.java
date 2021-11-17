package my;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyOrderToCsv {
    protected String insertAll(String value, String separator, Integer ...inserts){
        return IntStream.range(0, value.length())
                .mapToObj(i -> Arrays.stream(inserts)
                        .filter(val -> val==i)
                        .findFirst()
                        .orElse(null) == null?value.substring(i, i+1):
                        separator+value.substring(i, i+1)).collect(Collectors.joining())
                .replaceAll(" ", "")
                .replaceAll("@", ",");
    }
    protected String insertCommas(String value){
        return insertAll(value, ",", 10, 20, 28);
    }
}
