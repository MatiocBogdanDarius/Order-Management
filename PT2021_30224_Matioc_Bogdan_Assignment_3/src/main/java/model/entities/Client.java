package model.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.dto.ClientDTO;

/**
 * it is used to be able to manipulate and store the data resulting from the database query
 * corresponds to the client table
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;
    private int age;
    private int deleted;

    public Client(){
        name = "";
        address = "";
        email = "";
    }

    public Client( String name, String address, String email, int age) {
        this();
        setName(name);
        setAddress(address);
        setEmail(email);
        setAge(age);
    }

    public Client(int id, String name, String address, String email, int age) {
        this();
        setId(id);
        setName(name);
        setAddress(address);
        setEmail(email);
        setAge(age);
    }

    public Client(String name, String address, String email, int age, int isDelete) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
        this.deleted = isDelete;
    }

    public Client(int id, String name, String address, String email, int age, int isDelete) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
        this.deleted = isDelete;
    }

    public Client(ClientDTO client){
        this(
                client.getId(),
                client.getName(),
                client.getAddress(),
                client.getEmail(),
                client.getAge()
        );
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int delete) {
        deleted = delete;
    }


    @Override
    public String toString() {
        return  getId() + "\t"
                + getName() + "\t"
                + getAddress() + "\t"
                + getEmail() + "\t"
                + getAge();
    }
}
