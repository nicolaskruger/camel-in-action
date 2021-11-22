package my.pokemon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Power {
    private String name;
    private Integer damage;
    public Power copy(){
        return Power.builder()
                .damage(damage)
                .name(name)
                .build();
    }
}
