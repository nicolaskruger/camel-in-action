package my.gun_level_db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Bullet {
    private String gunName;
    public Bullet copy(){
        return Bullet.builder()
                .gunName(getGunName())
                .build();
    }
}
