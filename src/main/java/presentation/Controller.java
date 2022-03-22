package presentation;

import model.Client;
import model.Model;
import model.Product;
import model.WarehouseOrder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class for creating the connection between the GUI application (the JFrame and all of its components) and the
 * "background" processes that will do the work its user expects.
 */

public class Controller {
    View view;
    Model model;
    BillGenerator billGenerator;

    public Controller(View newView, Model newModel){
        this.view = newView;
        this.model = newModel;

        view.addStartClientListener(new startClientListener());
        view.addStartProductListener(new startProductListener());
        view.addStartOrderListener(new startOrderListener());

    }

    /**
     * All of the classes bellow will create a separate ActionListener for every button of the GUI and will start
     * the corresponding processes.
     */

    //for Frames
    class startClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setClientFrame();
            view.addAddListener(new addClientListener(), 1);
            view.addEditListener(new editClientListener(), 1);
            view.addDeleteListener(new deleteClientListener(), 1);
            view.addViewListener(new viewClientsListener(), 1);
        }
    }
    class startProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setProductFrame();
            view.addAddListener(new addProductListener(), 2);
            view.addEditListener(new editProductListener(), 2);
            view.addDeleteListener(new deleteProductListener(), 2);
            view.addViewListener(new viewProductsListener(), 2);
        }
    }
    class startOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setOrderFrame();
            view.addAddListener(new addOrderListener(), 3);
            view.addViewListener(new viewOrdersListener(), 3);
        }
    }

    //for adding
    class addClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int id = view.getClientID();
                String name = view.getClientName();
                String address = view.getClientAddress();
                String email = view.getClientEmail();

                Client newClient = new Client(id, name, address, email);
                System.out.println(newClient.toString());

                boolean success = model.addClient(newClient);
                if(!success){
                    System.out.println("Client successfully added.");
                }
                else{
                    view.showError("Already existing Client.");
                }
            }
            catch(NumberFormatException nfex){
                view.showError("Enter valid data!");
            }
            catch (IllegalArgumentException ex){
                view.showError(ex.getMessage());
            }
        }
    }
    class addProductListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String name = view.getProductName();
                int quantity = view.getProductQuantity();
                int price = view.getProductPrice();
                Product newProduct = new Product(name, quantity, price);
                System.out.println(newProduct.toString());

                model.addProduct(newProduct);
                System.out.println("Product successfully added.");
            }
            catch(NumberFormatException nfex){
                view.showError("Enter valid data!");
            }
        }
    }
    class addOrderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int id = view.getOrderId();
                int quantity = view.getOrderQuantity();
                String client = view.getOrderClient();
                String product = view.getOrderProduct();

                WarehouseOrder newOrder = new WarehouseOrder(id, quantity, client, product);
                System.out.println(newOrder.toString());

                int success = model.addOrder(newOrder);
                if(success == 0){
                    billGenerator = new BillGenerator(id);
                    billGenerator.addToBill(newOrder);

                    System.out.println("Order successfully placed.");
                }
                else if(success == -1){
                    view.showError("Enter valid order ID!");
                }
                else{
                    view.showError("Cannot satisfy order's required quantity.");
                }
            }
            catch(NumberFormatException ex){
                view.showError("Enter Valid Data!");
            }
            catch(NoSuchElementException ex){
                view.showError(ex.getMessage());
            }
        }
    }

    //for editing
    class editClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int clientID = 0;
            try{
                clientID = view.getClientID();
            }
            catch (NumberFormatException ex){
                view.showError("Enter ID of client that you want to edit!");
                return;
            }
            try{
                Object [] newParams = new Object[3];
                newParams[0] = view.getClientName();
                newParams[1] = view.getClientAddress();
                newParams[2] = view.getClientEmail();

                model.editClient(clientID, newParams);
            }
            catch(NumberFormatException ex){
                view.showError("Enter valid data!");
            }
            catch(NoSuchElementException ex){
                view.showError(ex.getMessage());
            }
        }
    }
    class editProductListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String productName = "";
            try{
                productName = view.getProductName();
            }
            catch (NumberFormatException ex){
                view.showError("Enter name of product that you want to edit!");
                return;
            }
            try{
                Object [] newParams = new Object[2];
                newParams[0] = view.getProductQuantity();
                newParams[1] = view.getProductPrice();

                model.editProduct(productName, newParams);
            }
            catch(NumberFormatException ex){
                view.showError("Enter valid data!");
            }
            catch(NoSuchElementException ex){
                view.showError(ex.getMessage());
            }
        }
    }

    //for deleting
    class deleteClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getClientName();
            if(!name.isEmpty()){
                try{
                    model.deleteClient(name);
                }
                catch(NoSuchElementException ex){
                    view.showError(ex.getMessage());
                }
                System.out.println("Client successfully deleted!");
            }
            else{
                view.showError("Provide with client's name!");
            }
        }
    }
    class deleteProductListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getProductName();
            if(!name.isEmpty()){
                try{
                    model.deleteProduct(name);
                }
                catch (NoSuchElementException ex){
                    view.showError(ex.getMessage());
                }
                System.out.println("Product successfully deleted!");
            }
            else{
                view.showError("Provide with product's name!");
            }
        }
    }

    //for viewing
    class viewClientsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> clients = model.viewClients();

            view.setDataLimit(model.getSize(1));
            view.setClientResult(clients);
        }
    }
    class viewProductsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Product> products = model.viewProducts();

            view.setDataLimit(model.getSize(2));
            view.setProductResult(products);
        }
    }
    class viewOrdersListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            List<WarehouseOrder> orders = model.viewOrders();
            view.setDataLimit(model.getSize(3));
            view.setOrderResult(orders);
        }
    }

}
