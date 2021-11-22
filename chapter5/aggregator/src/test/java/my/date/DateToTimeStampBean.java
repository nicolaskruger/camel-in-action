package my.date;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateToTimeStampBean {
    public Timestamp dateToTimeStamp(Date date){
        return new Timestamp(date.getTime());
    }
    public List<Timestamp> dateListToTimeStampList(List<Date> date){
        return date.stream().map(v->dateToTimeStamp(v)).collect(Collectors.toList());
    }
}
