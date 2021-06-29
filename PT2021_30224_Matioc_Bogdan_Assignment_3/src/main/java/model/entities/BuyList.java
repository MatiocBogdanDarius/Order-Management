package model.entities;

/**
 * it is used to be able to manipulate and store the data resulting from the database query
 * corresponds to the buyList table
 */
public class BuyList {
    private int id;
    private int idOrder;
    private int idProduct;
    private int quantity;
    private double price;

    public BuyList(){}

    public BuyList(int idOrder, int idProduct, int quantity, double price) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
    }

    public BuyList(int id, int idOrder, int idProduct, int quantity, double price) {
        this(idOrder, idProduct, quantity, price);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
