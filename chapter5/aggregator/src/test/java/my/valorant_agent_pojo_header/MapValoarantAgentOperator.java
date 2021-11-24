package my.valorant_agent_pojo_header;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MapValoarantAgentOperator {
    private Map<ValorantClass, List<ValorantAgent>> map;

    public MapValoarantAgentOperator inserAgent(ValorantClass valorantClass, ValorantAgent valorantAgent ){
        List<ValorantAgent> list = map.getOrDefault(valorantClass, Arrays.asList()).stream().map(v -> v).collect(Collectors.toList());
        list.add(valorantAgent);
        map.put(valorantClass, list);
        return this;
    }

}
