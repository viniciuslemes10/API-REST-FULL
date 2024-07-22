package br.com.erudio.integrationtests.wrappers;

import br.com.erudio.integrationtests.vo.BooksVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BooksEmbeddedVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("booksVOList")
    private List<BooksVO> booksVOS;

    public BooksEmbeddedVO() {
    }

    public List<BooksVO> getBooksVOS() {
        return booksVOS;
    }

    public void setBooksVOS(List<BooksVO> booksVOS) {
        this.booksVOS = booksVOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooksEmbeddedVO that)) return false;
        return Objects.equals(booksVOS, that.booksVOS);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(booksVOS);
    }
}
