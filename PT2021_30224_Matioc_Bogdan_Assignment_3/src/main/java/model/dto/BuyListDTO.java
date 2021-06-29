package model.dto;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.entities.BuyList;

/**
 * this class is used to transfer a buy list's data between the presentation layer and the business Logic layer
 */
public class BuyListDTO {
    private SimpleStringProperty product;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty price;

    public BuyListDTO() {
        product = new SimpleStringProperty();
        quantity = new SimpleIntegerProperty(0);
        this.price = new SimpleDoubleProperty(0);
    }

    public BuyListDTO(String product) {
        this();
        this.product.set(product);
    }

    public BuyListDTO(String product, double price) {
        this(product);
        this.price.set(price);
        this.quantity.set(1);
    }

    public BuyListDTO(String product, int quantity, double price){
        this(product, price);
        this.quantity.set(quantity);

    }

    //getters
    public String getProduct() {
        return product.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getPrice() {
        return price.get();
    }

    //setters
    public void setQuantity(int quantity, double pricePerProduct) {
        this.quantity.set(quantity);
        this.price.set(pricePerProduct * quantity);
    }
}
