package br.com.erudio.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {

    private static ModelMapper model = new ModelMapper();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return model.map(origin, destination);
    }

    public static <O, D> List<D> parseListObject(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<>();
        for (O o : origin) {
            destinationObjects.add(model.map(o, destination));
        }

        return destinationObjects;

    }

}
