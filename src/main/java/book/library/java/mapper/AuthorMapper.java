package book.library.java.mapper;

import book.library.java.dto.AbstractDto;
import book.library.java.dto.AuthorDto;
import book.library.java.model.Author;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper extends AbstractMapper<Author>{
    AuthorMapper MAPPER = Mappers.getMapper(AuthorMapper.class);

    AuthorDto toDto(Author author);

    @InheritInverseConfiguration
    Author fromDto(AbstractDto<Author> tDto);
}
