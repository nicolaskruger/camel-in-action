package my.custom_loadbalance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum ValorantWeapons {
    CLASIC("classic", 0),
    PHANTON("phanton", 1),
    VANDAL("vandal", 2),
    OPERATOR("operator", 3);
    private String name;
    private Integer number;
    public static List<ValorantWeapons> toSortList(){
        List<ValorantWeapons> list = Arrays.asList(CLASIC,PHANTON,VANDAL,OPERATOR);
        list.sort((a,b)-> a.number - b.number);
        return list;
    }
    public static String toMockEndPoint(){
        return toSortList().stream().map(v -> v.getName())
                .collect(Collectors.joining(","));
    }
}
