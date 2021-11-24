package my.gun_level_db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Gun {
    private String name;
    private List<Bullet> bullets;
    public static Gun onlyName(String name){
        return Gun.builder()
                .name(name)
                .bullets(Arrays.asList())
                .build();
    }
    public Gun copy(){
        return Gun.builder()
                .bullets(getBullets().stream().map(b->b.copy()).collect(Collectors.toList()))
                .name(getName())
                .build();
    }
}
