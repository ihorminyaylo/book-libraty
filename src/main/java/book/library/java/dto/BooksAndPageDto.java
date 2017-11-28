package book.library.java.dto;

import book.library.java.model.Book;

import java.math.BigInteger;
import java.util.List;

public class BooksAndPageDto {
    private List<BookDto> bookDtoList;
    private BigInteger pageCount;

    public BooksAndPageDto(List<BookDto> bookDtoList, BigInteger pageCount) {
        this.bookDtoList = bookDtoList;
        this.pageCount = pageCount;
    }

    public List<BookDto> getBookDtoList() {
        return bookDtoList;
    }

    public void setBookDtoList(List<BookDto> bookDtoList) {
        this.bookDtoList = bookDtoList;
    }

    public BigInteger getPageCount() {
        return pageCount;
    }

    public void setPageCount(BigInteger pageCount) {
        this.pageCount = pageCount;
    }
}
