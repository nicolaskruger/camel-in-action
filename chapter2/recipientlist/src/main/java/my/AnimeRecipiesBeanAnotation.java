package my;

import org.apache.camel.RecipientList;
import org.apache.camel.jsonpath.JsonPath;

public class AnimeRecipiesBeanAnotation extends AnimeRecipeBeanBase{
    @Override
    @RecipientList
    public String[] recipe(@JsonPath("$.name") String name) {
        return route(name);
    }
}
