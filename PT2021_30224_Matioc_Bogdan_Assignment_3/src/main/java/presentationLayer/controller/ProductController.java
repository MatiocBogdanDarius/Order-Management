package presentationLayer.controller;

import businessLogic.ProductLogic;
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
import model.dto.ProductDTO;
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
 *this class contains the initialization of the interface Product scene and adds event handle on buttons
 */
public class ProductController implements Initializable {
    private SwitchSceneInterface switchScene;
    private ProductLogic productLogic;
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
    private TableView<ProductDTO> table;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField stockTextField;

    public ProductController() {
        this.switchScene = new SwitchSceneClass();
        this.productLogic = new ProductLogic();
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

    private void addButtonHandle() {
        productLogic.add(new ProductDTO(
                nameTextField.getText(),
                Integer.parseInt(stockTextField.getText()),
                Double.parseDouble(priceTextField.getText()))
        );
        init();
    }

    private void deleteButtonHandle() {
        productLogic.delete(table.getSelectionModel().getSelectedItem());
        init();
    }

    private void setTable() {
        List<TableColumn<ProductDTO, ?>> tableColumns = new ArrayList<>();
        table.getColumns().clear();
        for (Field field : ProductDTO.class.getDeclaredFields()) {
            TableColumn<ProductDTO, ?> newTableColumn = createColumn(field.getType(), field.getName());
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

    private void editCellEvenHandle(TableColumn.CellEditEvent<ProductDTO, ?> editEvent) {
        try {
            ProductDTO product = editEvent.getRowValue();
            String columnName = editEvent.getTableColumn().getText();
            PropertyDescriptor propertyDescriptor =
                    new PropertyDescriptor(columnName, ProductDTO.class);

            invokeMethod(
                    propertyDescriptor.getWriteMethod(),
                    product,
                    editEvent.getNewValue().toString(),
                    product.getClass().getDeclaredField(columnName)
            );
            productLogic.update(product);
        } catch (IntrospectionException | IllegalAccessException |
                InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private TableColumn<ProductDTO, ?> createColumn(Class<?> type, String nameColumn) {
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

    private void invokeMethod(Method method, ProductDTO product, String newValue, Field field) throws InvocationTargetException, IllegalAccessException {
        if (field.getType() == SimpleIntegerProperty.class) {
            method.invoke(product, Integer.parseInt(newValue));
        } else if (field.getType() == SimpleDoubleProperty.class) {
            method.invoke(product, Double.parseDouble(newValue));
        } else {
            method.invoke(product, newValue);
        }
    }

    private void init() {
        ObservableList<ProductDTO> dataList =
                FXCollections.observableArrayList(productLogic.findAll());
        table.setItems(dataList);
    }
}
