package my.date;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MyAgregationDateTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:start";
    public static final String MOCK_END = "mock:end";
    public static final String DATE_LIST_TO_TIME_STAMP_LIST_BODY = "dateListToTimeStampList(${body})";
    public static final String DATE_03 = "27/05/1998";
    public static final String DATE_02 = "26/05/1998";
    public static final String DATE_01 = "25/05/1998";

    private Calendar fromTimestampToCallendar(Timestamp timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        return calendar;
    }

    private void assertTimeStamp(Timestamp actual, Timestamp expected){
        Calendar calendarActual = fromTimestampToCallendar(actual);
        Calendar calendarExpected = fromTimestampToCallendar(expected);
        assertEquals(calendarActual.YEAR, calendarExpected.YEAR);
        assertEquals(calendarActual.MONTH, calendarExpected.MONTH);
        assertEquals(calendarActual.DAY_OF_MONTH, calendarExpected.DAY_OF_MONTH);
    }

    @Test
    public void test(){

        MockEndpoint mockEndpoint = getMockEndpoint(MOCK_END);

        List<Date> dateList = Arrays.asList(DATE_03, DATE_02, DATE_01)
                        .stream().map((date) -> DateBuilder.build(date)).collect(Collectors.toList());
        dateList.forEach(date -> {
            template.sendBody(DIRECT_START, date);
        });

        List<Timestamp> list = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(List.class);

        List<Timestamp> expected = Arrays.asList(DATE_01, DATE_02, DATE_03)
                .stream().map(date -> new Timestamp(DateBuilder.build(date).getTime())).collect(Collectors.toList());

        for (int i = 0; i < expected.size(); i++) {
            assertTimeStamp(list.get(i), expected.get(i));
        }

    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(DIRECT_START)
                        .aggregate(new AgregationDate())
                        .constant(true)
                        .completionSize(3)
                        .bean(DateToTimeStampBean.class, DATE_LIST_TO_TIME_STAMP_LIST_BODY)
                        .to(MOCK_END);
            }
        };
    }
}
