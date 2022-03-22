package presentation;

import model.WarehouseOrder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for creating a "bill" for each valid order.
 * It will create a new *.txt file and will add to it the corresponding values.
 */

public class BillGenerator {
    private File file;
    private String fileName;

    public BillGenerator(int orderID){
        this.fileName = "billOfOrder" + orderID + ".txt";

        try{
            file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void addToBill(WarehouseOrder order){
        try {
            FileWriter myWriter = new FileWriter(fileName);

            String textToAdd = "";
            textToAdd += "Order bill no. " + order.getId() + ".\n";
            textToAdd += "Client's name: " + order.getClient_name() + "\n";
            textToAdd += "Product purchased: " + order.getProduct_name() + "\n";
            textToAdd += "Quantity: " + order.getQuantity() + "\n";
            textToAdd += "Final price: " + order.getTotal() + "$";

            myWriter.write(textToAdd);

            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
