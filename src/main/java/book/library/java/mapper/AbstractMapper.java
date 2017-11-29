package book.library.java.mapper;

import book.library.java.dto.AbstractDto;
import org.mapstruct.factory.Mappers;

public interface AbstractMapper<T> {
    AbstractMapper MAPPER = Mappers.getMapper(AbstractMapper.class);

    AbstractDto<T> toDto(T T);

    T fromDto(AbstractDto<T> tDto);
}
