package model.entities;

import model.dto.ProductDTO;

/**
 * it is used to be able to manipulate and store the data resulting from the database query
 * corresponds to the product table
 */
public class Product {
    private int id;
    private String name;
    private int stock;
    private double price;
    private int deleted;

    public Product(){
        name = "";
    }

    public Product(int id, String name, int stock, double price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public Product(String name, int stock, double price, int isDelete) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.deleted = isDelete;
    }

    public Product(int id, String name, int stock, double price, int isDelete) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.deleted = isDelete;
    }

    public Product(ProductDTO product){
        this(product.getId(), product.getName(), product.getStock(), product.getPrice());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return  id + "\t" + name;
    }
}
