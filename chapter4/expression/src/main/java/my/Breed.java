package my;

import org.apache.camel.jsonpath.JsonPath;

import java.util.HashMap;
import java.util.Map;

public class Breed {
    public static final String ASIAN = "asian";
    public static final String CAUCASIAN = "caucasian";
    public static final String AFICAN = "afican";
    public static final String INDIAN = "indian";
    public static final String JAPAN = "japan";
    public static final String BRASIL = "brasil";
    public static final String US = "us";
    public static final String SOUTH_AFRICA = "south africa";
    public static final String INDIA = "india";
    public static final String COREA = "corea";
    public static final Map<String, String> race = new HashMap<String, String>(){{
        put(JAPAN, ASIAN);
        put(BRASIL, CAUCASIAN);
        put(US, CAUCASIAN);
        put(SOUTH_AFRICA, AFICAN);
        put(INDIA, INDIAN);
        put(COREA, ASIAN);
    }};
    public static final String UNKNOU = "unknou";

    public String breed(@JsonPath("$.country") String country){
        return race.getOrDefault(country, UNKNOU);
    }
}
