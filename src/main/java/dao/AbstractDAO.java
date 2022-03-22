package dao;

import connection.ConnectionFactory;

import java.beans.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

/**
 *Abstract class implementing methods used in "communicating" with the database: create, insert, find / select,
 * delete, etc.. - dynamically generates the SQL queries, all that through reflection.
 *
 * @param <T> represents the chosen type of Model class - Client, Product, WarehouseOrder.
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;
    private int size;

    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Generates the SQL SELECT query to return the row that meets a certain condition that will be tested.
     * @param field is the column name to be tested.
     * @return the created SELECT query.
     */
    private String createSelectQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     * Generates the SQL DELETE query to delete all the rows that meet a certain condition.
     * @param fieldName is the column name to be tested.
     * @return the created DELETE query.
     */
    private String createDeleteQuery(String fieldName){
        return "DELETE FROM " + type.getSimpleName() + " WHERE " + fieldName + " =?";
    }
    /**
     * Generates the SQL INSERT query to insert into the table a new row.
     * @param fields contains all of the table's fields / columns.
     * @return the generated INSERT query.
     */
    private String createInsertQuery(Field[] fields){
        StringBuilder sb = new StringBuilder();

        int noStatics = 0;

        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" ( ");

        for(int i = 0; i < fields.length; i++){
            if (Modifier.isStatic(fields[i].getModifiers())) {
                noStatics++;
                continue;
            }
            sb.append(fields[i].getName());
            if(i != fields.length - 1){
                sb.append(", ");
            }
            else{
                sb.append(" ) ");
            }
        }

        sb.append(" VALUES (");
        sb.append("?,".repeat(fields.length - 1 - noStatics));
        sb.append("?)");
        return sb.toString();
    }

    /**
     * Finds the first row / object of type T that meets the condition of fieldName = field in the corresponding
     * table of the database.
     * @param field is the value for filtering tha data
     * @param fieldName is the name of the table column where we expect to find the occurrence.
     * @return the first row / object of type T that meets the condition / null if nothing of that nature was found.
     */
    public T findByField(Object field, String fieldName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(fieldName);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, field);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByField " + e.getMessage());
        }
        catch(IndexOutOfBoundsException e) {
            return null;
        }finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Inserts the item given as a parameter into its corresponding database table.
     * @param item is the item to be inserted.
     * @return an integer value to signal the kind of error / exception that might have occurred.
     */
    public int insert(T item){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result;
        String query = createInsertQuery(type.getDeclaredFields());

        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            int index = 1;

            for(Field field : type.getDeclaredFields()){
                field.setAccessible(true);
                preparedStatement.setObject(index, field.get(item));
                index++;
            }
            result = preparedStatement.executeUpdate();
        }
        catch (IllegalAccessException e){
            e.printStackTrace();
        }
        //pentru chei primare
        catch(SQLException e){
            return -1;
        }
        finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);

        }
        return 0;
    }

    /**
     * Returns all the objects of the type T in the corresponding table.
     * @return a list of T-type objects from the database.
     */
    public List<T> findAll(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM " + type.getSimpleName();

        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            List<T> res = createObjects(resultSet);
            size = res.size();

            return res;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Deletes all rows in the table where the condition of fieldName = field is met.
     *
     * @param field is the value for filtering tha data.
     * @param fieldName is the name of the table column where we expect the values of field to be.
     */
    public void delete(Object field, String fieldName){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result;
        String query = createDeleteQuery(fieldName);

        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, field);
            result = preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Converts the ResultSet that is returned by the SQL query into a List of objects corresponding to
     * the Table on which it was called (of T-type).
     *
     * @param resultSet is the ResultSet object to be converted.
     * @return a List of T-type objects.
     */
    private List<T> createObjects(ResultSet resultSet){
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
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        }
        catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getSize(){
        return this.size;
    }
}
