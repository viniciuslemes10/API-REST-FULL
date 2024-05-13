package br.com.erudio.unitests.mapper.mocks;

import br.com.erudio.data.vo.v1.BooksVO;
import br.com.erudio.model.books.Books;

import java.time.LocalDate;
import java.time.ZoneOffset;
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
        LocalDate dateFixed = LocalDate.of(2000, 10, 11);
        Date date = Date.from(dateFixed.atStartOfDay().toInstant(ZoneOffset.UTC));
        books.setLaunchDate(date);
        books.setPrice(number.doubleValue());
        return books;
    }

    public BooksVO mockVO(Integer number) {
        BooksVO booksVO = new BooksVO();

        booksVO.setKey(number.longValue());
        booksVO.setAuthor("Author: " + number);
        booksVO.setTitle("Title: " + number);
        LocalDate dateFixed = LocalDate.of(2000, 10, 11);
        Date date = Date.from(dateFixed.atStartOfDay().toInstant(ZoneOffset.UTC));
        booksVO.setLaunchDate(date);
        booksVO.setPrice(number.doubleValue());
        return booksVO;
    }
}
