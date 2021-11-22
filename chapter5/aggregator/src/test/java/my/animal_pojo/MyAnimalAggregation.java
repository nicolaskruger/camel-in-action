package my.animal_pojo;

import lombok.var;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.toolbox.AggregationStrategies;
import org.junit.Test;

import java.util.List;

public class MyAnimalAggregation extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_END = "mock:end";
    public static final String $_BODY_TYPE = "${body.type}";

    public void sendAnimalRequest(Animal ...animals){
        for (int i = 0; i < animals.length; i++) {
            template.sendBody(DIRECT_START, animals[i]);
        }
    }

    public List<Animal> getAnimals(MockEndpoint mockEndpoint, Integer i){
        List<Exchange> e = mockEndpoint.getExchanges();
        return i >= e.size() ? null : e.get(i).getIn().getBody(List.class);
    }

    public void assertAnimals(List<Animal> ...animals){
        AnimalEnum[] animalEnums = { AnimalEnum.CAT, AnimalEnum.DOG };
        for (int i = 0; i < animals.length; i++) {
            final AnimalEnum anEnum = animalEnums[i];
            animals[i].forEach(a -> {
                assertEquals(a.getType(), anEnum);
            });
        }
    }

    @Test
    public void test() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);
        sendAnimalRequest(
                new Animal("geleia",13, AnimalEnum.CAT),
                new Animal("misa", 14, AnimalEnum.CAT),
                new Animal("gatsu", 3 , AnimalEnum.CAT),
                new Animal("chuchi", 11, AnimalEnum.DOG),
                new Animal("eliot", 1, AnimalEnum.DOG),
                new Animal("tink", 15, AnimalEnum.DOG)
        );
        assertMockEndpointsSatisfied();
        mockEndpoint.expectedMessageCount(2);
        assertAnimals(getAnimals(mockEndpoint, 0), getAnimals(mockEndpoint, 1));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .bean(DebbugAnimalBean.class)
                        .aggregate(simple($_BODY_TYPE), AggregationStrategies.bean(new AggregationAnimal(),"bean"))
                        .completionSize(3)
                        .bean(DebbugListAnimalBean.class)
                        .to(MOCK_END);
            }
        };
    }
}
