package model.entities;


import java.sql.Timestamp;

/**
 * it is used to be able to manipulate and store the data resulting from the database query
 * corresponds to the _order table
 */
public class _Order {
    private int id;
    private int idClient;
    private Timestamp date;

    public _Order(){
        date = new java.sql.Timestamp(new java.util.Date().getTime());;
    }

    public _Order(int idClient){
        this();
        this.idClient = idClient;
    }

    //getters
    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public Timestamp getDate() {
        return date;
    }

    //setters

    public void setId(int id) {
        this.id = id;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
