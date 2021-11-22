package my.date;


import my.regex.MyRegexValidator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateBuilder {

    private static final String DATE_VALIDATE = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    private DateBuilder(){}

    /**
     * expect date on format dd/mm/yyyy
     *
     * @param date
     * @return
     */
    public static Date build(String date){
        if(!MyRegexValidator.validate(DATE_VALIDATE, date)){
            throw new RuntimeException("invalida date");
        }
         List<Integer> integerList = Arrays.stream(date.split("[\\/\\-\\.]"))
                 .map(v ->Integer.parseInt(v)).collect(Collectors.toList());
        Calendar calendar = Calendar.getInstance();
        calendar.set(integerList.get(2),integerList.get(1), integerList.get(0));
        return calendar.getTime();
    }
}
