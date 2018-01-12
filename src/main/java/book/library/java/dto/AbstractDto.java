package book.library.java.dto;

import book.library.java.model.AbstractEntity;

public class AbstractDto {
    private Integer id;
    private String createDate;

    AbstractDto(AbstractEntity abstractEntity) {
        id = abstractEntity.getId();
        createDate = abstractEntity.getCreateDate().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
