package br.com.erudio.unitests.mapper.mocks;

import br.com.erudio.data.vo.v1.BooksVO;
import br.com.erudio.model.books.Books;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BooksMock {
    public Books mockEntity() {
        return mockEntity(0);
    }

    public BooksVO mockVO() {
        return mockVO(0);
    }

    public List<Books> mockListEntity() {
        List<Books> booksList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            booksList.add(mockEntity(i));
        }
        return booksList;
    }

    public List<BooksVO> mockListVO() {
        List<BooksVO> booksVOList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            booksVOList.add(mockVO(i));
        }
        return booksVOList;
    }

    public Books mockEntity(Integer number) {
        Books books = new Books();
        books.setId(number.longValue());
        books.setAuthor("Author: " + number);
        books.setTitle("Title: " + number);
        books.setLaunchDate(new Date());
        books.setPrice(number.doubleValue());
        return books;
    }

    public BooksVO mockVO(Integer number) {
        BooksVO booksVO = new BooksVO();

        booksVO.setKey(number.longValue());
        booksVO.setAuthor("Author: " + number);
        booksVO.setTitle("Title: " + number);
        booksVO.setLaunchDate(new Date());
        booksVO.setPrice(number.doubleValue());
        return booksVO;
    }
}
