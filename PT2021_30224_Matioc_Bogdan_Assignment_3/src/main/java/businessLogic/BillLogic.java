package businessLogic;

import dataAcces.BuyListDAO;
import dataAcces.ClientDAO;
import dataAcces.OrderDAO;
import dataAcces.ProductDAO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import model.entities.BuyList;
import model.entities.Client;
import model.entities.Product;
import model.entities._Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * this class contains the logic part of an invoice
 */
public class BillLogic {

    public void download(Window window) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        File chosenDir = dirChooser.showDialog(window);
        try {
            File myObj = new File(chosenDir.getPath() + "Bill.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        String downloadFileStringContent = createBill();

        try {
            FileWriter myWriter = new FileWriter(chosenDir.getPath() + "/Bill.txt");
            myWriter.write(downloadFileStringContent);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String createBill(){
        StringBuilder bill = new StringBuilder();
        _Order lastOrder = (new OrderDAO()).findLast();
        List<BuyList> lastBuyList =
                (new BuyListDAO()).findByField("idOrder", String.valueOf(lastOrder.getId()));
        Client lastClient = (new ClientDAO()).findById(lastOrder.getIdClient());
        bill.append("BILL \n")
                .append("NO. ORDER: ")
                .append(lastOrder.getId())
                .append('\n')
                .append("DATE ORDER: ")
                .append(lastOrder.getDate())
                .append("\n\n")
                .append("CLIENT: ")
                .append(getClientBillStringFormat(lastClient))
                .append("\n\n")
                .append(createBuyListBillFormat(lastBuyList));
        return bill.toString();
    }

    private String getClientBillStringFormat(Client client) {
        StringBuilder clientBillStringFormat = new StringBuilder();
        clientBillStringFormat.append("ID CLIENT: ")
                .append(client.getId())
                .append("\nNAME: ")
                .append(client.getName())
                .append("\nADDRESS: ")
                .append(client.getAddress())
                .append("\nEMAIL: ")
                .append(client.getEmail())
                .append("\nAGE: ")
                .append(client.getAddress());
        return clientBillStringFormat.toString();
    }

    private String createBuyListBillFormat(List<BuyList> buyList) {
        StringBuilder buyListBillStringFormat = new StringBuilder();
        buyListBillStringFormat.append("ID PRODUCT")
                .append("\t")
                .append("NAME PRODUCT")
                .append("\t")
                .append("QUANTITY")
                .append("\t")
                .append("PRICE")
                .append("\n");

        buyList.forEach(buyListMember ->{
            Product product = (new ProductDAO()).findById(buyListMember.getIdProduct());
            buyListBillStringFormat.append(product.toString())
                    .append("\t")
                    .append(buyListMember.getQuantity())
                    .append("\t")
                    .append(buyListMember.getPrice())
                    .append("\n");
        });
        return buyListBillStringFormat.toString();
    }


}
