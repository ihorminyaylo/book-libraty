package book.library.java.dto;

import java.math.BigInteger;
import java.util.List;

public class AuthorsAndPageDto {
    private List<AuthorDto> authorDtoList;
    private BigInteger pageCount;

    public AuthorsAndPageDto(List<AuthorDto> authorDtoList, BigInteger pageCount) {
        this.authorDtoList = authorDtoList;
        this.pageCount = pageCount;
    }

    public BigInteger getPageCount() {
        return pageCount;
    }

    public void setPageCount(BigInteger pageCount) {
        this.pageCount = pageCount;
    }

    public List<AuthorDto> getAuthorDtoList() {
        return authorDtoList;
    }

    public void setAuthorDtoList(List<AuthorDto> authorDtoList) {
        this.authorDtoList = authorDtoList;
    }
}
