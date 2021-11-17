package camelinaction;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;

@Converter
public final class CatConverter {
    @Converter
    public static Cat toCat(String cat, Exchange exchange){
        return Cat.builder()
                .name(cat)
                .build();
    }
}
