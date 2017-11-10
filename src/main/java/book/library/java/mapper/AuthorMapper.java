package book.library.java.mapper;

import book.library.java.dto.AuthorDto;
import book.library.java.model.Author;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper MAPPER = Mappers.getMapper(AuthorMapper.class);

    AuthorDto toDto(Author author);

    @InheritInverseConfiguration
    Author fromDto(AuthorDto authorDto);
}
