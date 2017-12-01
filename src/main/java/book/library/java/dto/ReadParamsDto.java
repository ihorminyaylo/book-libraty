package book.library.java.dto;

public class ReadParamsDto<P> {
    private Integer offset;
    private Integer limit;
    private P pattern;

    public ReadParamsDto() {
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public P getPattern() {
        return pattern;
    }

    public void setPattern(P pattern) {
        this.pattern = pattern;
    }
}
