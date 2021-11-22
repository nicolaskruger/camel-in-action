package my.regex;

import java.util.regex.Pattern;

public class MyRegexValidator {
    private MyRegexValidator(){}
    public static Boolean validate(String regex, String text){
        return Pattern.compile(regex).matcher(text).matches();
    }
}
