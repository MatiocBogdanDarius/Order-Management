package dataAcces;

import connection.ConnectionFactory;
import model.entities._Order;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *this interface contains the header of the methods for interacting with the database
 */
public abstract class DAO<T> implements RepositoryDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(DAO.class.getName());

    Class<T> type;
    Query<T> query;

    @SuppressWarnings("unchecked")
    public DAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        query = new Query<>(type);
    }

    @Override
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String findAllQuery = query.createFindAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findAllQuery);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
//            ConnectionFactory.close(resultSet);
//            ConnectionFactory.close(statement);
//            ConnectionFactory.close(connection);
        }

        return null;
    }

    @Override
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String selectByIdQuery = query.createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(selectByIdQuery);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
//            ConnectionFactory.close(resultSet);
//            ConnectionFactory.close(statement);
//            ConnectionFactory.close(connection);
        }
        return null;
    }

    @Override
    public List<T> findByField(String fieldName, String fieldValue){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String findAllQuery = query.createFindByFieldQuery(fieldName);
        System.out.println(query.createFindByFieldQuery(fieldName) + "\t" + fieldValue);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findAllQuery);
            statement.setString(1, fieldValue);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
//            ConnectionFactory.close(resultSet);
//            ConnectionFactory.close(statement);
//            ConnectionFactory.close(connection);
        }
        return  null;
    }

    public T findLast(){
        return findById(getMaxId());
    }

    @Override
    public void add(T object) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String insertQuery = query.createInsertQuery(object);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(insertQuery);
            int positionParam = 1;
            for (Field field : object.getClass().getDeclaredFields()) {
                if (positionParam > 1) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), object.getClass());
                    Method method = propertyDescriptor.getReadMethod();
                    String statementParam = method.invoke(object).toString();
                    System.out.println(positionParam + "    " + field.getName() + "     " + statementParam);
                    statement.setString(positionParam, statementParam);
                } else {
                    statement.setInt(positionParam, getMaxId() + 1);
                }
                positionParam++;
            }
            statement.executeUpdate();

        } catch (SQLException | IntrospectionException e) {
            LOGGER.log(Level.WARNING, object.getClass().getName() + "DAO:findById " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
//            ConnectionFactory.close(resultSet);
//            ConnectionFactory.close(statement);
//            ConnectionFactory.close(connection);
        }
    }

    @Override
    public void update(T object) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String updateQuery = query.createUpdateQuery(object);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(updateQuery);
            int positionParam = 1;
            for (Field field : object.getClass().getDeclaredFields()) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), object.getClass());
                Method method = propertyDescriptor.getReadMethod();
                String statementParam = method.invoke(object).toString();
                statement.setString(positionParam, statementParam);
                positionParam++;
            }
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor("id", object.getClass());
            Method method = propertyDescriptor.getReadMethod();
            int id = (int) method.invoke(object);
            statement.setInt(positionParam, id);
            statement.executeUpdate();

        } catch (SQLException | IntrospectionException e) {
            LOGGER.log(Level.WARNING, object.getClass().getName() + "DAO:update " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
//            ConnectionFactory.close(resultSet);
//            ConnectionFactory.close(statement);
//            ConnectionFactory.close(connection);
        }

    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();

            setForeignKeyChecks(connection, 0);

            String deleteQuery = query.createDeleteQuery();
            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();

            setForeignKeyChecks(connection, 1);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        }finally {
//            ConnectionFactory.close(resultSet);
//            ConnectionFactory.close(statement);
//            ConnectionFactory.close(connection);
        }
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    protected int getMaxId() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String findAllQuery = query.createGetMaxIdQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findAllQuery);
            resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("max");

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
//            ConnectionFactory.close(resultSet);
//            ConnectionFactory.close(statement);
//            ConnectionFactory.close(connection);
        }
        return 0;
    }

    private void setForeignKeyChecks(Connection connection, int value) throws SQLException {
        String setForeignKeyChecksQuery = query.createSetForeignKeyChecksQuery();
        PreparedStatement statement = connection.prepareStatement(setForeignKeyChecksQuery);
        statement.setInt(1, value);
        statement.execute();
    }
}
