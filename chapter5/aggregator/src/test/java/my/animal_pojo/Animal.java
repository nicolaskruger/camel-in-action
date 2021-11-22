package my.animal_pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Animal {
    private String name;
    private Integer age;
    private AnimalEnum type;
    public Animal copy(){
        return Animal.builder()
                .age(age)
                .name(name)
                .type(type)
                .build();
    }
}
