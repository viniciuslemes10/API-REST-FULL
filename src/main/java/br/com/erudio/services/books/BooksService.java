package br.com.erudio.services.books;

import br.com.erudio.controllers.BooksController;
import br.com.erudio.data.vo.v1.BooksVO;
import br.com.erudio.exceptions.RequiredObjectIsNotNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.books.Books;
import br.com.erudio.repositories.books.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BooksService {
    @Autowired
    private BooksRepository repository;

    public BooksVO created(BooksVO vo) {
        var entity = DozerMapper.parseObject(vo, Books.class);
        var booksVO = DozerMapper.parseObject(repository.save(entity), BooksVO.class);
        booksVO.add(linkTo(methodOn(BooksController.class).findById(vo.getKey())).withSelfRel());
        return booksVO;

    }

    public BooksVO findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, BooksVO.class);
        vo.add(linkTo(methodOn(BooksController.class).findById(id)).withSelfRel());
        return vo;
    }

    public List<BooksVO> findAll() {
        var listVO = DozerMapper.parseListObject(repository.findAll(), BooksVO.class);
        listVO.stream().forEach(b -> b.add(linkTo(methodOn(BooksController.class).findById(b.getKey())).withSelfRel()));
        return listVO;
    }

    public BooksVO update(BooksVO vo) {
        if(vo == null) throw new RequiredObjectIsNotNullException();

        var entity = repository.findById(vo.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(vo.getAuthor());
        entity.setLaunchDate(vo.getLaunchDate());
        entity.setPrice(vo.getPrice());
        entity.setTitle(vo.getTitle());
        repository.save(entity);
        var bookVO =  DozerMapper.parseObject(entity, BooksVO.class);
        bookVO.add(linkTo(methodOn(BooksController.class).update(vo)).withSelfRel());
        return bookVO;
    }

    public void delete(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
