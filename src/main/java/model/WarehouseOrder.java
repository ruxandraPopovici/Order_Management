package model;

public class WarehouseOrder {
    private int id;
    private int quantity;
    private String client_name;
    private String product_name;
    private double total;

    public WarehouseOrder(int id, int quantity, String client_name, String product_name) {
        this.id = id;
        this.quantity = quantity;
        this.client_name = client_name;
        this.product_name = product_name;
    }

    public WarehouseOrder(){}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getClient_name() {
        return client_name;
    }
    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    public String toString() {
        return id + " " + client_name + " " + product_name + " " + quantity + "  $" + total;
    }

}
