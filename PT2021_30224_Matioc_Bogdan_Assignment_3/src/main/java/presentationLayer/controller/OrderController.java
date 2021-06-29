package presentationLayer.controller;

import businessLogic.ClientLogic;
import businessLogic.OrderLogic;
import businessLogic.ProductLogic;
import exceptions.UnavailableStockException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;
import model.dto.ClientDTO;
import model.dto.BuyListDTO;
import model.dto.ProductDTO;
import presentationLayer.controller.switchScene.SwitchSceneClass;
import presentationLayer.controller.switchScene.SwitchSceneInterface;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * this class contains the initialization of the interface Order scene and adds event handle on buttons
 */
public class OrderController implements Initializable {
    private OrderLogic orderLogic;
    private SwitchSceneInterface switchScene;
    ObservableList<BuyListDTO> shoppingCartTableContent;
    ObservableList<ProductDTO> productsAvailableTableContent;
    @FXML
    private Button homeButton;
    @FXML
    private Button clientButton;
    @FXML
    private Button productButton;
    @FXML
    private Button orderButton;
    @FXML
    private TableView<ProductDTO> productsAvailableTable;
    @FXML
    private TableView<BuyListDTO> shoppingCartTable;
    @FXML
    private Button placeOrderButton;
    @FXML
    private ChoiceBox<String> selectClientChoiceBox;
    @FXML
    private Button shoppingHistoryButton;
    @FXML
    private Label errorLabel;

    public OrderController() {
        this.switchScene = new SwitchSceneClass();
        this.orderLogic = new OrderLogic();
        this.homeButton = new Button();
        this.clientButton = new Button();
        this.productButton = new Button();
        this.orderButton = new Button();
        this.placeOrderButton = new Button();
        this.shoppingHistoryButton = new Button();
        this.errorLabel = new Label();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setOnAction(actionEvent -> switchScene.getHomeScene(actionEvent));
        clientButton.setOnAction(actionEvent -> switchScene.getClientScene(actionEvent));
        productButton.setOnAction(actionEvent -> switchScene.getProductScene(actionEvent));
        orderButton.setOnAction(actionEvent -> switchScene.getOrderScene(actionEvent));
        shoppingHistoryButton.setOnAction(actionEvent -> switchScene.getShoppingHistoryScene(actionEvent));
        placeOrderButton.setOnAction(this::placeOrderButtonHandle);
        placeOrderButton.setDisable(true);
        setProductsAvailableTable();
        setShoppingCartTable();
        shoppingCartTable.setEditable(true);
        initSelectClientChoiceBox();
        setErrorLabel("");
    }

    //initSelectClientChoiceBox
    private void initSelectClientChoiceBox() {
        List<ClientDTO> clients = (new ClientLogic()).findAll();
        if(!clients.isEmpty()) {
            ObservableList<String> clientsList = FXCollections.observableArrayList();
            clients.forEach(client -> clientsList.add(client.toString()));
            selectClientChoiceBox.setItems(clientsList);
            selectClientChoiceBox.setValue(clientsList.get(0));
        }
    }

    //placeOrderButtonHandle
    private void placeOrderButtonHandle(ActionEvent actionEvent) {
        try {
            orderLogic.placeOrder(shoppingCartTableContent, selectClientChoiceBox.getValue());
            switchScene.getBillScene(actionEvent);
            setErrorLabel("");
        } catch (UnavailableStockException e) {
            setErrorLabel(e.getMessage());
        }
    }

    //setProductsAvailableTable
    private void setProductsAvailableTable() {
        List<TableColumn<ProductDTO, ?>> tableColumns = new ArrayList<>();
        productsAvailableTable.getColumns().clear();
        for (Field field : ProductDTO.class.getDeclaredFields()) {
            TableColumn<ProductDTO, ?> newTableColumn =
                    createColumnForProductsAvailableTable(field.getType(), field.getName());
            tableColumns.add(newTableColumn);
        }
        productsAvailableTable.getColumns().addAll(tableColumns);
        initProductsAvailableTable();
        productsAvailableTable.setOnMouseClicked(mouseEvent -> mouseClickedHandle());

    }

    private TableColumn<ProductDTO, ?> createColumnForProductsAvailableTable(Class<?> type, String nameColumn) {
        if (type == SimpleIntegerProperty.class) {
            TableColumn<ProductDTO, Number> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            return newTableColumn;
        } else if (type == SimpleDoubleProperty.class) {
            TableColumn<ProductDTO, Number> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            return newTableColumn;
        } else {
            TableColumn<ProductDTO, String> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            return newTableColumn;
        }
    }

    private void initProductsAvailableTable() {
        productsAvailableTableContent = FXCollections.observableArrayList((new ProductLogic()).findAll());
        productsAvailableTable.setItems(productsAvailableTableContent);
    }

    private void mouseClickedHandle() {
        ProductDTO product = productsAvailableTable.getSelectionModel().getSelectedItem();
        productsAvailableTableContent.remove(product);
        shoppingCartTableContent.add(new BuyListDTO(product.toString(), product.getPrice()));
        placeOrderButton.setDisable(false);
    }


    //setShoppingCartTable
    private void setShoppingCartTable() {
        List<TableColumn<BuyListDTO, ?>> tableColumns = new ArrayList<>();
        shoppingCartTable.getColumns().clear();
        for (Field field : BuyListDTO.class.getDeclaredFields()) {
            TableColumn<BuyListDTO, ?> newTableColumn =
                    createColumnForShoppingCartTable(field.getType(), field.getName());
            tableColumns.add(newTableColumn);
            newTableColumn.setEditable(false);
            if (field.getName().equals("quantity")) {
                newTableColumn.setEditable(true);
                newTableColumn.setOnEditCommit(this::editQuantityHandle);
            }
        }
        shoppingCartTable.getColumns().addAll(tableColumns);
        initShoppingCartTableContent();
    }

    private TableColumn<BuyListDTO, ?> createColumnForShoppingCartTable(Class<?> type, String nameColumn) {
        if (type == SimpleIntegerProperty.class) {
            TableColumn<BuyListDTO, Number> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            return newTableColumn;
        } else if (type == SimpleDoubleProperty.class) {
            TableColumn<BuyListDTO, Number> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            return newTableColumn;
        } else {
            TableColumn<BuyListDTO, String> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            return newTableColumn;
        }
    }

    private void editQuantityHandle(TableColumn.CellEditEvent<BuyListDTO, ?> editEvent) {
        orderLogic.editQuantityHandleLogic(
                editEvent.getRowValue(),
                Integer.parseInt(editEvent.getNewValue().toString())
        );
        shoppingCartTable.refresh();
    }

    private void initShoppingCartTableContent() {
        shoppingCartTableContent = FXCollections.observableArrayList();
        shoppingCartTable.setItems(shoppingCartTableContent);
    }

    public void setErrorLabel(String message) {
        this.errorLabel.setText(message);
    }
}
