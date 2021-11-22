package my.pokemon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Pokemon {
    private String name;
    private List<Power> powers;
    public Pokemon copy(){
        return Pokemon.builder()
                .name(name)
                .powers(powers.stream().map(p->p.copy()).collect(Collectors.toList()))
                .build();
    }
}
