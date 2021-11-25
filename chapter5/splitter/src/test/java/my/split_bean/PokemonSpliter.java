package my.split_bean;

import java.util.List;

public class PokemonSpliter {
    public List<PowerDto> split(PokemonDto pokemonDto){
        return pokemonDto.getPowerDtos();
    }
}
