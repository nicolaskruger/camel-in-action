package my.routing_slip_header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyAnimeHeader {

    public static final String MOCK_OYASUMI_PUNPUN = "mock:oyasumi_punpun";
    public static final String MOCK_FLCL = "mock:flcl";
    public static final String MOCK_SAMURAY_CHAMPLO = "mock:samuray_champlo";
    public static final String PUNPUN = "punpun";
    public static final String AYKO = "ayko";
    public static final String SEKY = "seky";
    public static final String NAOTA = "naota";
    public static final String MAMIMI = "mamimi";
    public static final String HARUKO = "haruko";
    public static final String MUGEN = "mugen";
    public static final String JIN = "jin";
    public static final String FU = "fu";

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class AnimeDto{
        private String name;
        private List<String> characters;
    }

    private  AnimeDto animeBuilder(String name, String... character ){
        return new AnimeDto(name, Arrays.asList(character));
    }

    private boolean containNames(String names, List<String> animes){
         return  !animes.stream().filter( anime -> names.contains(anime))
                .findFirst().orElse("").equals("");
    }

    public String bean(String names){
        return Arrays.asList(
                animeBuilder(MOCK_OYASUMI_PUNPUN, PUNPUN, AYKO, SEKY),
                animeBuilder(MOCK_FLCL, NAOTA, MAMIMI, HARUKO),
                animeBuilder(MOCK_SAMURAY_CHAMPLO, MUGEN, JIN, FU)
        ).stream().filter(anime -> containNames(names, anime.getCharacters()))
                .map(anime -> anime.getName())
                .collect(Collectors.joining(","));
    }
}
