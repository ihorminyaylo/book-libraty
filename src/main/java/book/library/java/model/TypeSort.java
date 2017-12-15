package book.library.java.model;

public enum TypeSort {
    DESC("desc"), ASC("asc");
    final String sql;
    TypeSort(String sql) {
        this.sql = sql;
    }
}
