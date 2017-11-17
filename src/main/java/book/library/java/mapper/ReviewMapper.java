package book.library.java.mapper;

import book.library.java.dto.ReviewDto;
import book.library.java.model.Review;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper MAPPER = Mappers.getMapper(ReviewMapper.class);

    @Mappings({
            @Mapping(source = "review.book.id", target = "bookId")
    })
    ReviewDto toDto(Review review);

    @InheritInverseConfiguration
    Review fromDto(ReviewDto reviewDto);
}
