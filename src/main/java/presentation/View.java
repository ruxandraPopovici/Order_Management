package presentation;

import model.Client;
import model.Product;
import model.WarehouseOrder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Class for generating the GUI through which the user will interact with the application and indirectly
 * with the database.
 */
public class View extends JFrame{

    JFrame secondFrame = new JFrame();
    JFrame thirdFrame = new JFrame();

    JLabel selectTable = new JLabel("Select the table on which you would like to perform operations.");

    JButton clientButton = new JButton("Client");
    JButton productButton = new JButton("Product");
    JButton orderButton = new JButton("Order");

    JTable clientResult = new JTable();
    JTable productResult = new JTable();
    JTable orderResult = new JTable();

    JButton addClientButton = new JButton("ADD CLIENT");
    JButton editClientButton = new JButton("EDIT CLIENT");
    JButton deleteClientButton = new JButton("DELETE CLIENT");
    JButton viewClientButton = new JButton("VIEW CLIENTS");

    JButton addProductButton = new JButton("ADD PRODUCT");
    JButton editProductButton = new JButton("EDIT PRODUCT");
    JButton deleteProductButton = new JButton("DELETE PRODUCT");
    JButton viewProductButton = new JButton("VIEW PRODUCTS");

    JButton addOrderButton = new JButton("ADD ORDER");
    JButton viewOrderButton = new JButton("VIEW ALL ORDERS");

    Object [][] clientsData;
    Object [][] productsData;
    Object [][] ordersData;
    private int limit;

    /**
     * Constructor for generating the main panel.
     */
    public View(){
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel content1 = new JPanel();
        content1.setLayout(new FlowLayout(FlowLayout.CENTER));

        selectTable.setFont(new Font("Comic Sans MC", Font.PLAIN, 16));

        content1.add(selectTable);

        JPanel content2 = new JPanel();
        content2.setLayout(new FlowLayout(FlowLayout.CENTER));

        clientButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        productButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        content2.add(clientButton);
        content2.add(productButton);

        JPanel content3 = new JPanel();
        content3.setLayout(new FlowLayout(FlowLayout.CENTER));

        orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        content3.add(orderButton);

        content.add(content1);
        content.add(content2);
        content.add(content3);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setTitle("Order Management");
        this.setLayout(new BorderLayout());

        this.setContentPane(content);
    }

    private JTextField idText = new JTextField(10);
    private JTextField nameText = new JTextField(10);
    private JTextField addressText = new JTextField(10);
    private JTextField emailText = new JTextField(10);
    /**
     * Starts new frame for the application's corresponding database Client Table.
     */
    public void setClientFrame(){
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JPanel[] contents = setPanels(6);

        JLabel idLabel = new JLabel("Client's ID: ");
        contents[0].add(idLabel);
        contents[0].add(idText);

        JLabel nameLabel = new JLabel("Client's name: ");
        contents[1].add(nameLabel);
        contents[1].add(nameText);

        JLabel addressLabel = new JLabel("Client's address: ");
        contents[2].add(addressLabel);
        contents[2].add(addressText);

        JLabel emailLabel = new JLabel("Client's e-mail: ");
        contents[3].add(emailLabel);
        contents[3].add(emailText);

        contents[4].add(addClientButton);
        contents[4].add(editClientButton);
        contents[5].add(deleteClientButton);
        contents[5].add(viewClientButton);

        for(int i = 0; i < 6; i++){
            content.add(contents[i]);
        }

        secondFrame.setTitle("Client Frame");
        secondFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        secondFrame.setSize(500, 300);
        secondFrame.setLayout(new BorderLayout());

        secondFrame.setContentPane(content);

        secondFrame.setVisible(true);
    }

    private JTextField productNameText = new JTextField(10);
    private JTextField quantityText = new JTextField(10);
    private JTextField priceText = new JTextField(10);
    /**
     * Starts new frame for the application's corresponding database Product Table.
     */
    public void setProductFrame(){
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JPanel[] contents = setPanels(5);

        JLabel nameLabel = new JLabel("Product's name: ");
        contents[0].add(nameLabel);
        contents[0].add(productNameText);

        JLabel quantityLabel = new JLabel("Quantity: ");
        contents[1].add(quantityLabel);
        contents[1].add(quantityText);

        JLabel priceLabel = new JLabel("Price: ");
        contents[2].add(priceLabel);
        contents[2].add(priceText);

        contents[3].add(addProductButton);
        contents[3].add(editProductButton);
        contents[4].add(deleteProductButton);
        contents[4].add(viewProductButton);

        for(int i = 0; i < 5; i++){
            content.add(contents[i]);
        }

        secondFrame.setTitle("Product Frame");
        secondFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        secondFrame.setSize(500, 300);
        secondFrame.setLayout(new BorderLayout());

        secondFrame.setContentPane(content);

        secondFrame.setVisible(true);
    }

    private JTextField orderIdText = new JTextField(10);
    private JTextField orderQuantityText = new JTextField(10);
    private JTextField clientText = new JTextField(10);
    private JTextField productText = new JTextField(10);
    /**
     * Starts new frame for the application's corresponding database Order Table.
     */
    public void setOrderFrame(){
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JPanel[] contents = setPanels(6);

        JLabel idLabel = new JLabel("Order's ID: ");
        contents[0].add(idLabel);
        contents[0].add(orderIdText);

        JLabel quantityLabel = new JLabel("Quantity: ");
        contents[1].add(quantityLabel);
        contents[1].add(orderQuantityText);

        JLabel clientLabel = new JLabel("Client's Name: ");

        contents[2].add(clientLabel);
        contents[2].add(clientText);

        JLabel productLabel = new JLabel("Product's Name: ");
        contents[3].add(productLabel);
        contents[3].add(productText);

        contents[4].add(addOrderButton);
        contents[5].add(viewOrderButton);

        for(int i = 0; i < 6; i++){
            content.add(contents[i]);
        }

        secondFrame.setTitle("Order Frame");
        secondFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        secondFrame.setSize(500, 300);
        secondFrame.setLayout(new BorderLayout());

        secondFrame.setContentPane(content);

        secondFrame.setVisible(true);
    }

    /**
     * Method that will start a new Frame for displaying the rows in the Client Table
     * @param clients list of clients that will provide with the data to populate the table to be displayed.
     */
    public void setClientResult(List<Client> clients){

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        clientsData = new Object[limit][4];

        String [] columnNames = {"Client ID",
                "Name",
                "Address",
                "Email"};
        int i = 0;
        for(Client c : clients){
            clientsData[i][0] = c.getId();
            clientsData[i][1] = c.getName();
            clientsData[i][2] = c.getAddress();
            clientsData[i][3] = c.getEmail();
            i++;
        }
        System.out.println("hei");
        clientResult = new JTable(clientsData, columnNames);

        JScrollPane sp = new JScrollPane(clientResult);
        content.add(sp);

        thirdFrame.setTitle("Client Table");
        thirdFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        thirdFrame.setSize(500, 300);
        thirdFrame.setLayout(new BorderLayout());

        thirdFrame.setContentPane(content);
        thirdFrame.setVisible(true);
    }
    /**
     * Method that will start a new Frame for displaying the rows in the Product Table
     * @param products list of products that will provide with the data to populate the table to be displayed.
     */
    public void setProductResult(List<Product> products){

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        productsData = new Object[limit][3];

        String [] columnNames = {"Name",
                "Quantity",
                "Price"};
        int i = 0;
        for(Product p : products){
            productsData[i][0] = p.getName();
            productsData[i][1] = p.getQuantity();
            productsData[i][2] = p.getPrice();
            i++;
        }
        productResult = new JTable(productsData, columnNames);

        JScrollPane sp = new JScrollPane(productResult);
        content.add(sp);

        thirdFrame.setTitle("Product Table");
        thirdFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        thirdFrame.setSize(500, 300);
        thirdFrame.setLayout(new BorderLayout());

        thirdFrame.setContentPane(content);
        thirdFrame.setVisible(true);
    }
    /**
     * Method that will start a new Frame for displaying the rows in the WarehouseOrder Table
     * @param orders list of orders that will provide with the data to populate the table to be displayed.
     */
    public void setOrderResult(List<WarehouseOrder> orders){

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        ordersData = new Object[limit][5];

        String [] columnNames = {"Order ID",
                "Quantity",
                "Client's Name",
                "Product's Name",
                "Total"};
        int i = 0;
        for(WarehouseOrder c : orders){
            ordersData[i][0] = c.getId();
            ordersData[i][1] = c.getQuantity();
            ordersData[i][2] = c.getClient_name();
            ordersData[i][3] = c.getProduct_name();
            ordersData[i][4] = c.getTotal();
            i++;
        }
        orderResult = new JTable(ordersData, columnNames);

        JScrollPane sp = new JScrollPane(orderResult);
        content.add(sp);

        thirdFrame.setTitle("Order Table");
        thirdFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        thirdFrame.setSize(500, 300);
        thirdFrame.setLayout(new BorderLayout());

        thirdFrame.setContentPane(content);
        thirdFrame.setVisible(true);
    }

    public void addStartClientListener(ActionListener startClient){
        this.clientButton.addActionListener(startClient);
    }
    public void addStartProductListener(ActionListener startProduct){
        this.productButton.addActionListener(startProduct);
    }
    public void addStartOrderListener(ActionListener startOrder){
        this.orderButton.addActionListener(startOrder);
    }

    public void addAddListener(ActionListener add, int whichFrame){
        switch (whichFrame) {
            case 1 -> this.addClientButton.addActionListener(add);
            case 2 -> this.addProductButton.addActionListener(add);
            default -> this.addOrderButton.addActionListener(add);
        }
    }
    public void addEditListener(ActionListener edit, int whichFrame){
        if(whichFrame == 1){
            this.editClientButton.addActionListener(edit);
        }
        else{
            this.editProductButton.addActionListener(edit);
        }
    }
    public void addDeleteListener(ActionListener delete, int whichFrame){
        if(whichFrame == 1){
            this.deleteClientButton.addActionListener(delete);
        }
        else{
            this.deleteProductButton.addActionListener(delete);
        }
    }
    public void addViewListener(ActionListener view, int whichFrame){
        switch (whichFrame) {
            case 1 -> this.viewClientButton.addActionListener(view);
            case 2 -> this.viewProductButton.addActionListener(view);
            default -> this.viewOrderButton.addActionListener(view);
        }
    }

    JPanel[] setPanels(int noPanels){
        JPanel [] contents = new JPanel[noPanels];
        for(int i = 0; i < noPanels; i++){
            contents[i] = new JPanel();
            contents[i].setLayout(new FlowLayout(FlowLayout.CENTER));
        }
        return contents;
    }

    public int getClientID(){
        return Integer.parseInt(idText.getText());
    }
    public String getClientName(){
        return nameText.getText();
    }
    public String getClientAddress(){
        return addressText.getText();
    }
    public String getClientEmail(){
        return emailText.getText();
    }

    public String getProductName(){
        return productNameText.getText();
    }
    public int getProductQuantity(){
        return Integer.parseInt(quantityText.getText());
    }
    public int getProductPrice(){
        return Integer.parseInt(priceText.getText());
    }

    public int getOrderId(){
        return Integer.parseInt(orderIdText.getText());
    }
    public int getOrderQuantity(){
        return Integer.parseInt(orderQuantityText.getText());
    }
    public String getOrderClient(){
        return clientText.getText();
    }
    public String getOrderProduct(){
        return productText.getText();
    }

    public void setDataLimit(int limit){
        this.limit = limit;
    }
    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
}
