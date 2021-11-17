package my;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.component.jackson.JacksonDataFormat;

public class JsonUtils {
    public static <T> T toObject(String json, Class<T> tClass){
        try {
            return new ObjectMapper().readValue(json, tClass);
        }
        catch (Exception e){
            return  null;
        }

    }
}
