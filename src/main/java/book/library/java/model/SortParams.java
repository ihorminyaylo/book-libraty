package book.library.java.model;

/**
 * SortParams class for parameters for params to sort list of entity.
 * A ListParams have parameter for sort and have type field. Type field is enum.
 */
public class SortParams {
    private String parameter;
    private TypeSort type;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public TypeSort getType() {
        return type;
    }

    public void setType(TypeSort type) {
        this.type = type;
    }
}
