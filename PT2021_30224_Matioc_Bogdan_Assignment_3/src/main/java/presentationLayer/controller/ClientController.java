package presentationLayer.controller;

import businessLogic.ClientLogic;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;
import model.dto.ClientDTO;
import presentationLayer.controller.switchScene.SwitchSceneClass;
import presentationLayer.controller.switchScene.SwitchSceneInterface;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 *this class contains the initialization of the interface Client scene and adds event hendle on buttons
 */
public class ClientController implements Initializable {
    private ClientLogic clientLogic;
    private SwitchSceneInterface switchScene;
    @FXML
    private Button homeButton;
    @FXML
    private Button clientButton;
    @FXML
    private Button productButton;
    @FXML
    private Button orderButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<ClientDTO> table;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;

    public ClientController() {
        this.switchScene = new SwitchSceneClass();
        this.clientLogic = new ClientLogic();
        this.homeButton = new Button();
        this.clientButton = new Button();
        this.productButton = new Button();
        this.orderButton = new Button();
        this.addButton = new Button();
        this.deleteButton = new Button();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setOnAction(actionEvent -> switchScene.getHomeScene(actionEvent));
        clientButton.setOnAction(actionEvent -> switchScene.getClientScene(actionEvent));
        productButton.setOnAction(actionEvent -> switchScene.getProductScene(actionEvent));
        orderButton.setOnAction(actionEvent -> switchScene.getOrderScene(actionEvent));
        addButton.setOnAction(actionEvent -> addButtonHandle());
        deleteButton.setOnAction(actionEvent -> deleteButtonHandle());
        table.setEditable(true);
        setTable();
    }

    public void addButtonHandle(){
        clientLogic.add( new ClientDTO(
                nameTextField.getText(),
                addressTextField.getText(),
                emailTextField.getText(),
                Integer.parseInt(ageTextField.getText()))
        );
        init();
    }

    public void deleteButtonHandle(){
        ClientDTO client = table.getSelectionModel().getSelectedItem();
        clientLogic.delete(client);
        init();
    }

    private void setTable() {
        List<TableColumn<ClientDTO, ?>> tableColumns = new ArrayList<>();
        table.getColumns().clear();
        for (Field field : ClientDTO.class.getDeclaredFields()) {
            TableColumn<ClientDTO, ?> newTableColumn = createColumn(field.getType(), field.getName());
            tableColumns.add(newTableColumn);
            if (field.getName().equals("id")) {
                newTableColumn.setEditable(false);
            } else {
                newTableColumn.setOnEditCommit(this::editCellEvenHandle);
            }
        }
        table.getColumns().addAll(tableColumns);
        init();
    }

    private void editCellEvenHandle(TableColumn.CellEditEvent<ClientDTO, ?> editEvent) {
        try {
            ClientDTO client = editEvent.getRowValue();
            String columnName = editEvent.getTableColumn().getText();
            PropertyDescriptor propertyDescriptor =
                    new PropertyDescriptor(columnName, ClientDTO.class);

            invokeMethod(
                    propertyDescriptor.getWriteMethod(),
                    client,
                    editEvent.getNewValue().toString(),
                    client.getClass().getDeclaredField(columnName)
            );
            clientLogic.update(client);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private TableColumn<ClientDTO, ?> createColumn(Class<?> type, String nameColumn) {
        if (type == SimpleIntegerProperty.class) {
            TableColumn<ClientDTO, Number> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            return newTableColumn;
        } else if (type == SimpleDoubleProperty.class) {
            TableColumn<ClientDTO, Number> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            return newTableColumn;
        } else {
            TableColumn<ClientDTO, String> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            return newTableColumn;
        }
    }

    private void invokeMethod(Method method, ClientDTO client, String newValue, Field field) throws InvocationTargetException, IllegalAccessException {
        if (field.getType() == SimpleIntegerProperty.class) {
            method.invoke(client, Integer.parseInt(newValue));
        } else if (field.getType() == SimpleDoubleProperty.class) {
            method.invoke(client, Double.parseDouble(newValue));
        } else {
            method.invoke(client, newValue);
        }
    }

    private void init() {
        ObservableList<ClientDTO> dataList =
                FXCollections.observableArrayList(clientLogic.findAll());
        table.setItems(dataList);
    }
}
