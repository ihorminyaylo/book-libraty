package book.library.java.mapper;

import book.library.java.dto.BookDto;
import book.library.java.model.Book;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper MAPPER = Mappers.getMapper(BookMapper.class);

    BookDto toDto(Book book);

    @InheritInverseConfiguration
    Book fromDto(BookDto bookDto);
}
