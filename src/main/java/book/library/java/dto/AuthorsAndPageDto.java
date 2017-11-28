package book.library.java.dto;

import java.math.BigInteger;
import java.util.List;

public class AuthorsAndPageDto {
    private List<AuthorDto> authorDtoList;
    private Integer totalItems;

    public AuthorsAndPageDto(List<AuthorDto> authorDtoList, Integer totalItems) {
        this.authorDtoList = authorDtoList;
        this.totalItems = totalItems;
    }


    public List<AuthorDto> getAuthorDtoList() {
        return authorDtoList;
    }

    public void setAuthorDtoList(List<AuthorDto> authorDtoList) {
        this.authorDtoList = authorDtoList;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }
}
