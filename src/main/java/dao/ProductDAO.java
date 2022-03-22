package dao;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO extends AbstractDAO<Product>{

    /**
     * Specific method for Product-type objects that updates the quantity of certain product in the database
     * once an order has been successfully placed.
     *
     * @param name is the name of the product that needs to be updated.
     * @param newQuantity is the new values of the product's quantity.
     */
    public void updateQuantity(String name, int newQuantity){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result;
        String query = "UPDATE Product SET quantity = ? where name = ?";

        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setString(2, name);

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
}
