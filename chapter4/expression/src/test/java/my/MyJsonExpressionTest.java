package my;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MyJsonExpressionTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String COUNTRY = "country";
    public static final String BREED = "breed";
    public static final String FROM_REQUEST_TO_DTO_$_BODY_$_PROPERTY_COUNTRY = "fromRequestToDto(${body}, ${property.country})";
    public static final String MOCK_END = "mock:end:${body.breed}";
    public static final String MOCK_ROUTE = "mock:end:%s";
    public static final String ASIAN = "asian";
    public static final String COREA = "corea";
    public static final String JETT = "jett";
    public static final String JSON = "{\"name\":\"jett\",\"country\":\"corea\"}";
    public static final String CLASS = "class";
    public static final String TO_OBJECT_$_BODY_$_PROPERTY_CLASS = "toObject(${body}, ${property.class})";

    private String makeRoute(String name){
        return String.format(MOCK_ROUTE, name);
    }

    private void mockEndPoint(String name, Integer times){
        getMockEndpoint(makeRoute(name)).expectedMessageCount(times);
    }

    @Test
    public void send() throws InterruptedException {
        mockEndPoint(Breed.ASIAN, 1);
        mockEndPoint(Breed.CAUCASIAN, 0);
        mockEndPoint(Breed.INDIAN, 0);
        mockEndPoint(Breed.AFICAN, 0);
        String json = JSON;
        PeopleDto peopleDto = template.requestBody(DIRECT_START, json, PeopleDto.class);
        assertMockEndpointsSatisfied();
        assertEquals(peopleDto.getBreed(), ASIAN);
        assertEquals(peopleDto.getCountry(), COREA);
        assertEquals(peopleDto.getName(), JETT);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .setProperty(COUNTRY, method(Breed.class, BREED))
                        .setProperty(CLASS, constant(PeopleRequest.class))
                        .bean(JsonUtils.class, TO_OBJECT_$_BODY_$_PROPERTY_CLASS)
                        .bean(PeopleMapper.class, FROM_REQUEST_TO_DTO_$_BODY_$_PROPERTY_COUNTRY)
                        .toD(MOCK_END);

            }
        };
    }
}
