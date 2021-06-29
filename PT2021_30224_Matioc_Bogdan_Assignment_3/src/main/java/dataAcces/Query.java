package dataAcces;

import java.lang.reflect.Field;


/**
 * useful for accessing the buyLIst table in the database this class contains the creation of all queries
 */
public class Query<T> {
    private Class<T> type;

    public Query(Class<T> type) {
        this.type = type;
    }

    public String createInsertQuery(T object) {
        String[] aux = object.getClass().getName().split("\\.");
        String typeClassName = aux[aux.length - 1];
        StringBuilder newInsertQuery = new StringBuilder();
        newInsertQuery.append("INSERT INTO ")
                .append(typeClassName)
                .append(" VALUES (");
        int fieldsNumber = object.getClass().getDeclaredFields().length;
        newInsertQuery.append("?, ".repeat(Math.max(0, fieldsNumber - 1)));
        newInsertQuery.append("?);");
        return newInsertQuery.toString();
    }

    public String createUpdateQuery(T object) {
        String[] aux = object.getClass().getName().split("\\.");
        String typeClassName = aux[aux.length - 1];
        StringBuilder newUpdateQuery = new StringBuilder();
        newUpdateQuery.append("UPDATE ")
                .append(typeClassName)
                .append(" SET ");
        for(Field field:object.getClass().getDeclaredFields()){
            newUpdateQuery.append(field.getName()).append(" =  ?, ");
        }
        newUpdateQuery.replace(newUpdateQuery.length() - 2, newUpdateQuery.length() - 1, " ");
        newUpdateQuery.append("WHERE id = ?");
        return newUpdateQuery.toString();
    }

    public String createFindAllQuery() {
        StringBuilder newFindAllQuery = new StringBuilder();
        newFindAllQuery.append("SELECT * FROM ")
                .append(type.getSimpleName());
        return newFindAllQuery.toString();
    }

    public String createGetMaxIdQuery(){
        StringBuilder newFindAllQuery = new StringBuilder();
        newFindAllQuery.append("SELECT MAX(id) AS max FROM ")
                .append(type.getSimpleName());
        return newFindAllQuery.toString();
    }

    public String createSelectQuery(String field) {
        StringBuilder  newFindByIdQuery= new StringBuilder();
        newFindByIdQuery.append("SELECT * FROM ")
                .append(type.getSimpleName()).append(" WHERE ")
                .append(field)
                .append(" = ?");
        return newFindByIdQuery.toString();
    }

    public String createDeleteQuery() {
        StringBuilder  newFindByIdQuery= new StringBuilder();
        newFindByIdQuery.append("DELETE FROM ")
                .append(type.getSimpleName())
                .append(" WHERE id = ?;");
        return newFindByIdQuery.toString();
    }

    public String createFindByFieldQuery(String fieldName) {
        StringBuilder findByFieldQuery = new StringBuilder();
        findByFieldQuery.append(createFindAllQuery())
                .append(" WHERE ")
                .append(fieldName)
                .append(" = ?;");
        return findByFieldQuery.toString();
    }

    public String createSetForeignKeyChecksQuery(){
        return "SET FOREIGN_KEY_CHECKS=?";
    }
}
