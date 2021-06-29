package presentationLayer.controller;

import businessLogic.ProductLogic;
import businessLogic.ShoppingHistoryLogic;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;
import model.dto.BuyListDTO;
import model.dto.ClientDTO;
import model.dto.OrderDTO;
import presentationLayer.controller.switchScene.SwitchSceneClass;
import presentationLayer.controller.switchScene.SwitchSceneInterface;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *this class contains the initialization of the interface ShoppingHistoryController scene and adds event handle on buttons
 */
public class ShoppingHistoryController implements Initializable {
    private SwitchSceneInterface switchScene;
    private ObservableList<BuyListDTO> purchasedProductsTableContent;
    private ObservableList<OrderDTO> ordersTableContent;
    private ShoppingHistoryLogic shoppingHistoryLogic;
    @FXML
    private TableView<OrderDTO> ordersTable;
    @FXML
    private TableView<BuyListDTO> purchasedProductsTable;
    @FXML
    private Button backToShoppingButton;

    public ShoppingHistoryController() {
        switchScene = new SwitchSceneClass();
        shoppingHistoryLogic = new ShoppingHistoryLogic();
        backToShoppingButton = new Button();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backToShoppingButton.setOnAction(actionEvent -> {
            switchScene.getOrderScene(actionEvent);
        });
        setPurchasedProductsTable();
        setOrdersTable();
    }

    //setOrdersTable
    private void setOrdersTable() {
        List<TableColumn<OrderDTO, ?>> tableColumns = new ArrayList<>();
        ordersTable.getColumns().clear();
        for (Field field : OrderDTO.class.getDeclaredFields()) {
            TableColumn<OrderDTO, ?> newTableColumn =
                    createColumnForOrdersTable(field.getType(), field.getName());
            tableColumns.add(newTableColumn);
        }
        ordersTable.getColumns().addAll(tableColumns);
        initOrdersTable();
        ordersTable.setOnMouseClicked(mouseEvent -> mouseClickedHandle());
    }

    private TableColumn<OrderDTO, ?> createColumnForOrdersTable(Class<?> type, String nameColumn) {
        if (type == SimpleIntegerProperty.class) {
            TableColumn<OrderDTO, Number> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            return newTableColumn;
        } else if (type == SimpleDoubleProperty.class) {
            TableColumn<OrderDTO, Number> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
            return newTableColumn;
        } else {
            TableColumn<OrderDTO, String> newTableColumn = new TableColumn<>(nameColumn);
            newTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameColumn));
            newTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            return newTableColumn;
        }
    }

    private void initOrdersTable() {
        ordersTableContent = FXCollections.observableArrayList(shoppingHistoryLogic.getOrders());
        ordersTable.setItems(ordersTableContent);
    }

    private void mouseClickedHandle() {
        OrderDTO orderDTO = ordersTable.getSelectionModel().getSelectedItem();
        purchasedProductsTableContent = FXCollections.observableArrayList(
                shoppingHistoryLogic.getBuyListByOrder(orderDTO));
        purchasedProductsTable.setItems(purchasedProductsTableContent);
        purchasedProductsTable.refresh();
    }

    //setPurchasedProductsTable
    private void setPurchasedProductsTable() {
        List<TableColumn<BuyListDTO, ?>> tableColumns = new ArrayList<>();
        purchasedProductsTable.getColumns().clear();
        for (Field field : BuyListDTO.class.getDeclaredFields()) {
            TableColumn<BuyListDTO, ?> newTableColumn =
                    createColumnForPurchasedProductsTable(field.getType(), field.getName());
            tableColumns.add(newTableColumn);
        }
        purchasedProductsTable.getColumns().addAll(tableColumns);
        initPurchasedProductsTableContent();
    }

    private TableColumn<BuyListDTO, ?> createColumnForPurchasedProductsTable(Class<?> type, String nameColumn) {
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

    private void initPurchasedProductsTableContent() {
        purchasedProductsTableContent = FXCollections.observableArrayList();
        purchasedProductsTable.setItems(purchasedProductsTableContent);
    }
}
