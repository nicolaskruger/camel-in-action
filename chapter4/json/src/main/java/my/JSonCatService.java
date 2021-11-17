package my;

import org.apache.camel.jsonpath.JsonPath;
import org.apache.camel.language.Bean;

import static my.RandomNameGenerator.RANDOM_NAME_GENERATOR;
import static my.RandomNameGenerator.RANDOM_NAME_GENERATOR_METHOD;

public class JSonCatService {

    public static final String JSON_CAT_SERVICE = "JsonCatService";
    public static final String JSON_CAT_SERVICE_METHOD = "toCsv";

    public String toCsv(@JsonPath("$.name") String name,
                        @JsonPath("$.age") Integer age,
                        @Bean(ref=RANDOM_NAME_GENERATOR, method = RANDOM_NAME_GENERATOR_METHOD) String owner){
        return String.format("%s,%d,%s",name, age, owner);
    }
}
