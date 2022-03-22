package bll.validators;

import dao.AbstractDAO;
import dao.OrderDAO;
import model.Product;
import model.WarehouseOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that implements the business logic for the Warehouse Order table / class
 */

public class OrderBLL {

    private AbstractDAO<WarehouseOrder> dao;

    public OrderBLL(){
        this.dao = new OrderDAO();
    }

    public int insertOrder(WarehouseOrder order){
        ProductBLL productBLL = new ProductBLL();

        Product product = productBLL.findProductByName(order.getProduct_name());
        int dif = product.getQuantity() - order.getQuantity();

        if(dif < 0){
            return -2;
        }
        double newPrice = product.getPrice() * order.getQuantity();
        order.setTotal(newPrice);

        productBLL.updateQuantity(product.getName(), dif);

        return dao.insert(order);
    }

    public void deleteClientOrders(String clientName){
        dao.delete(clientName, "client_name");
    }
    public List<WarehouseOrder> showOrders(){
        return dao.findAll();
    }

    public int getSize(){
        return dao.getSize();
    }
}
