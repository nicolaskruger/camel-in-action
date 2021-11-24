package my.valorant_agent_pojo_header;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HeaderOperator {
    public static final String CLASS = "class";
    Map<String, ValorantClass> header;
    ValorantClass getValorantClass(){
        return header.get(CLASS);
    }
}
