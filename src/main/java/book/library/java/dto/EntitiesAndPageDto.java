package book.library.java.dto;

import java.util.List;

/**
 * Represent a EntitiesAndPageDto with generic of type(generic can be any model class of entity)
 * This class for convenient transfer of information between front-end and back-end.
 * EntitiesAndPageDto have list(model) and totalItems.
 */
public class EntitiesAndPageDto<T> { // todo: strange name. have to rename
    private List<T> list;
    private Integer totalItems;

    public EntitiesAndPageDto(List<T> list, Integer totalItems) {
        this.list = list;
        this.totalItems = totalItems;
    }

    public List<T> getList() {

        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }
}
