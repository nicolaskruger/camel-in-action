package my.animal_pojo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AggregationAnimal {

    public List<Animal> bean(Object oldEx, Object newEx){

        final Animal newAnimal = (Animal) newEx;
        final Animal oldAnimal = oldEx instanceof Animal ? (Animal) oldEx: null;
        final List<Animal> oldListAnimal = oldEx instanceof List ? (List<Animal>) oldEx: null;

        if(oldEx instanceof Animal){
            return Arrays.asList(oldAnimal, newAnimal);
        }
        List<Animal> newList = oldListAnimal.stream().map(a -> a.copy()).collect(Collectors.toList());
        newList.add(newAnimal);
        return newList;
    }
}
