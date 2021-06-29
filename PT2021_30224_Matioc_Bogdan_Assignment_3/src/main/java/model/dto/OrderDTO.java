package model.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import model.entities.Client;
import model.entities._Order;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * this class is used to transfer a order's data between the presentation layer and the business Logic layer
 */
public class OrderDTO {
    private SimpleIntegerProperty id;
    private SimpleStringProperty date;
    private SimpleStringProperty client;

    public OrderDTO(){
        id = new SimpleIntegerProperty();
        date = new SimpleStringProperty();
        client = new SimpleStringProperty();
    }

    public OrderDTO(String date, String client){
        this();
        this.date.set(date);
        this.client.set(client);
    }

    public OrderDTO(int id, String date, String client){
        this(date, client);
        this.id.set(id);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(Timestamp date) {
        this.date.set(date.toString());
    }

    public String getClient() {
        return client.get();
    }

    public void setClient(String client) {
        this.client.set(client);
    }
}
