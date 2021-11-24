package my.valorant_agent_pojo_header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValorantAggregation {


    public Map<ValorantClass, List<ValorantAgent>> bean(Object oldAgent,
                                                 Map oldHeader,
                                                 Object newAgent,
                                                 Map newHeader ){
        HeaderOperator oldHeaderOperator = new HeaderOperator(oldHeader);
        HeaderOperator newHeaderOperator = new HeaderOperator(newHeader);

        if(oldAgent instanceof ValorantAgent){
            MapValoarantAgentOperator map =  new MapValoarantAgentOperator(new HashMap<>());
            map.inserAgent(oldHeaderOperator.getValorantClass(), (ValorantAgent) oldAgent)
                    .inserAgent(newHeaderOperator.getValorantClass(), (ValorantAgent) newAgent);
            return map.getMap();
        }
        MapValoarantAgentOperator map = new MapValoarantAgentOperator((Map<ValorantClass, List<ValorantAgent>>)oldAgent);
        map.inserAgent(newHeaderOperator.getValorantClass(),(ValorantAgent) newAgent);

        return map.getMap();
    }
}
