package book.library.java.model;

/**
 * TypeSort is enum with two values: DESC and ASC.
 */
public enum TypeSort {
    DESC("desc"), ASC("asc");
    final String sql;
    TypeSort(String sql) {
        this.sql = sql;
    }
}
