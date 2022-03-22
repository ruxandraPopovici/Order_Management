package bll.validators;

import dao.AbstractDAO;
import dao.ProductDAO;
import model.Product;

import java.util.*;

/**
 * Class that implements the business logic for the Product table / class
 */

public class ProductBLL {
    private AbstractDAO<Product> dao;

    public ProductBLL() {
        this.dao = new ProductDAO();
    }

    public Product findProductByName(String name){
        Product product = dao.findByField(name, "name");
        if (product == null) {
            throw new NoSuchElementException("No product with the name " + name + " was found.");
        }
        return product;
    }

    /**
     * Inserts product into data base if it doesn't already exist and it updates its quantity on the contrary.
     * @param product the product to be inserted / updated.
     */
    public void insertProduct(Product product) {
        Product p = dao.findByField(product.getName(), "name");

        if (p != null) {
            ((ProductDAO)dao).updateQuantity(product.getName(), product.getQuantity() + p.getQuantity());
            product.setPrice(p.getPrice());
        }
        else {
            dao.insert(product);
        }
    }
    public void deleteProduct(String name){
        dao.delete(name, "name");
    }

    public void updateQuantity(String name, int quantity) {
        ((ProductDAO) dao).updateQuantity(name, quantity);
    }

    public List<Product> showProducts(){
        return dao.findAll();
    }

    public int getSize(){
        return dao.getSize();
    }

}
