package my;

import org.apache.camel.jsonpath.JsonPath;

public class AnimeRecipiesBean extends AnimeRecipeBeanBase{


    @Override
    public String[] recipe(@JsonPath("$.name") String name) {
        return route(name);
    }
}
