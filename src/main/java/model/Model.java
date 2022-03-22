package model;

import bll.validators.ClientBLL;
import bll.validators.OrderBLL;
import bll.validators.ProductBLL;

import java.util.List;

/**
 * Class that establishes the connection with the business logic of the project - contains objects of the
 * corresponding database Tables name and will do the background work of the application (given a T type object,
 * or one of its specific fields, it will be able to use its methods and manage and manipulate the database).
 */

public class Model {
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;

    public Model(){
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        orderBLL = new OrderBLL();
    }

    public boolean addClient(Client client){
        try{
            if(clientBLL.insertClient(client) == -1){
                return false;
            }
        }
        catch(IllegalArgumentException ex){
            throw ex;
        }
        return true;
    }
    public void deleteClient(String clientName){
        clientBLL.findClientByName(clientName);
        clientBLL.deleteClient(clientName);
    }
    public void editClient(int id, Object [] newParams){
        Client client = clientBLL.findClientByID(id);
        clientBLL.deleteClient(client);

        client.setName((String)newParams[0]);
        client.setAddress((String)newParams[1]);
        client.setEmail((String)newParams[2]);
        clientBLL.insertClient(client);
    }
    public List<Client> viewClients(){
        return clientBLL.showClients();
    }

    public void addProduct(Product product){
        productBLL.insertProduct(product);
    }
    public void deleteProduct(String productName){
        productBLL.findProductByName(productName);
        productBLL.deleteProduct(productName);
    }
    public void editProduct(String name, Object [] newParams){
        Product product = productBLL.findProductByName(name);
        productBLL.deleteProduct(name);

        product.setQuantity((int)newParams[0]);
        product.setPrice((int)newParams[1]);

        productBLL.insertProduct(product);
    }
    public List<Product> viewProducts(){
        return productBLL.showProducts();
    }

    public int addOrder(WarehouseOrder order){
        clientBLL.findClientByName(order.getClient_name());
        productBLL.findProductByName(order.getProduct_name());

        return orderBLL.insertOrder(order);
    }
    public List<WarehouseOrder> viewOrders(){
        return orderBLL.showOrders();
    }
    public Client findClient(int id){
            return clientBLL.findClientByID(id);
    }
    public int getSize(int whichFrame){
        if(whichFrame == 1){
            return clientBLL.getSize();
        }
        if(whichFrame == 2){
            return productBLL.getSize();
        }
        return orderBLL.getSize();
    }
}
