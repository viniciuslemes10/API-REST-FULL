package br.com.erudio.mapper;

import br.com.erudio.data.vo.v1.BooksVO;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.model.books.Books;
import br.com.erudio.model.person.Person;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {
//    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    private static ModelMapper mapper = new ModelMapper();

    static {
        mapper.createTypeMap(
                Person.class,
                PersonVO.class)
                .addMapping(Person::getId, PersonVO::setKey);
        mapper.createTypeMap(
                PersonVO.class,
                Person.class
        ).addMapping(PersonVO::getKey, Person::setId);
        mapper.createTypeMap(
                Books.class,
                BooksVO.class)
                .addMapping(Books::getId, BooksVO::setKey);
        mapper.createTypeMap(
                BooksVO.class,
                Books.class)
                .addMapping(BooksVO::getKey, Books::setId);
    }

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObject(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<>();
        for (O o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
