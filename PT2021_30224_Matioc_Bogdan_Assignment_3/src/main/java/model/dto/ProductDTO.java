package model.dto;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.entities.Product;

/**
 * this class is used to transfer a product's data between the presentation layer and the business Logic layer
 */
public class ProductDTO {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty stock;
    private SimpleDoubleProperty price;

    public ProductDTO(){
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        stock = new SimpleIntegerProperty();
        price = new SimpleDoubleProperty();
    }


    public ProductDTO(String name, int stock, double price) {
        this();
        setName(name);
        setStock(stock);
        setPrice(price);
    }

    public ProductDTO(int id, String name, int stock, double price) {
        this();
        setId(id);
        setName(name);
        setStock(stock);
        setPrice(price);
    }

    public ProductDTO(Product product){
        this(product.getId(), product.getName(), product.getStock(), product.getPrice());
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getStock() {
        return stock.get();
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }


    @Override
    public String toString() {
        return  id.get() + "\t" +
                " " + name.get();
    }
}
