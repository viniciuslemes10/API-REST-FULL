package br.com.erudio.integrationtests.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class WrapperBooksVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private BooksEmbeddedVO embeddedBooks;

    public WrapperBooksVO() {
    }

    public BooksEmbeddedVO getEmbeddedBooks() {
        return embeddedBooks;
    }

    public void setEmbeddedBooks(BooksEmbeddedVO embeddedBooks) {
        this.embeddedBooks = embeddedBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WrapperBooksVO that)) return false;
        return Objects.equals(embeddedBooks, that.embeddedBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(embeddedBooks);
    }
}
