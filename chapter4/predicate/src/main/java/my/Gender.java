package my;

import org.apache.camel.jsonpath.JsonPath;

public class Gender {
    public static final String HETERO = "hetero";
    public static final String $_GENDER = "$.gender";
    public static final String HOMO = "homo";
    public static final String BI = "bi";

    public boolean isHetero(@JsonPath($_GENDER) String gender){
        return gender.equals(HETERO);
    }
    public boolean isHomo(@JsonPath($_GENDER) String gender){
        return gender.equals(HOMO);
    }
    public boolean isBi(@JsonPath($_GENDER) String gender){
        return gender.equals(BI);
    }
}
