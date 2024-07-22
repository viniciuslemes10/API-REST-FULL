package br.com.erudio.integrationtests.vo.pagedmodels;

import br.com.erudio.integrationtests.vo.BooksVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class PagedModelBooksVO {
    @XmlElement(name = "content")
    private List<BooksVO> content;

    public PagedModelBooksVO() {
    }

    public List<BooksVO> getContent() {
        return content;
    }

    public void setContent(List<BooksVO> content) {
        this.content = content;
    }
}
