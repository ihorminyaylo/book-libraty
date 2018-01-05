package book.library.java.dto;

import book.library.java.model.AbstractEntity;

public class AbstractDto {
    private Integer id;

    AbstractDto(AbstractEntity abstractEntity) {
        id = abstractEntity.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
