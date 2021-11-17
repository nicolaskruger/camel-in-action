package my;

public class BussHelloBean {

    public static final String TU_EH_S = "Tu eh %s ?";

    public String tuEh(String name){
        return String.format(TU_EH_S, name);
    }
}
