package my;

public class MyOrderToCsvBean extends MyOrderToCsv{
    public String map(String custom){
        return insertCommas(custom);
    }
}
