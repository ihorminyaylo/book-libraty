package book.library.java.dto;

import java.util.List;

public class EntitiesAndPageDto<T> {
    private List<T> list;
    private Integer totalItems;

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

    public EntitiesAndPageDto(List<T> list, Integer totalItems) {
        this.list = list;
        this.totalItems = totalItems;
    }
}
