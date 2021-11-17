package my;

import org.modelmapper.ModelMapper;

public class PeopleMapper {
    private static ModelMapper modelMap;
    private static ModelMapper getInstance(){
        if(modelMap == null){
            modelMap = new ModelMapper();
        }
        return  modelMap;
    }

    public PeopleDto fromRequestToDto(PeopleRequest peopleRequest, String breed){
        PeopleDto people =  getInstance().map(peopleRequest, PeopleDto.class);
        people.setBreed(breed);
        return people;
    }
}
