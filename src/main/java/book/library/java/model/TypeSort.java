package book.library.java.model;

/**
 * TypeSort is enum with two values: DESC and ASC.
 */
public enum TypeSort {
    DESC("desc"), ASC("asc");
    final String sql; // todo: why package visible? is this variable really need?
	TypeSort(String sql) {
        this.sql = sql;
    }
}
