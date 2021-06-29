package model.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.entities.Client;

/**
 * this class is used to transfer a client's data between the presentation layer and the business Logic layer
 */
public class ClientDTO {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty address;
    private SimpleStringProperty email;
    private SimpleIntegerProperty age;

    public ClientDTO(){
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.age = new SimpleIntegerProperty();
    }

    public ClientDTO(String name, String address, String email, int age) {
        this();
        setName(name);
        setAddress(address);
        setEmail(email);
        setAge(age);
    }

    public ClientDTO(int id, String name, String address, String email, int age) {
        this();
        setId(id);
        setName(name);
        setAddress(address);
        setEmail(email);
        setAge(age);
    }

    public ClientDTO(Client client){
        this(
                client.getId(),
                client.getName(),
                client.getAddress(),
                client.getEmail(),
                client.getAge()
        );
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

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    @Override
    public String toString() {
        return id.get() + "\t" + name.get();
    }
}
