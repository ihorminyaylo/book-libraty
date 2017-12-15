package book.library.java.model;


/**
 * ListParams class for parameters for request from front-end.
 * A ListParams have offset and limit for pagination, pattern, where pattern is  object of class of each model.
 * ListParams also have sortParams, where sortParams is object of class.
 */
public class ListParams<P> {
    private Integer offset;
    private Integer limit;
    private P pattern;
    private SortParams sortParams;

    public ListParams() {
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

    public SortParams getSortParams() {
        return sortParams;
    }

    public void setSortParams(SortParams sortParams) {
        this.sortParams = sortParams;
    }
}
