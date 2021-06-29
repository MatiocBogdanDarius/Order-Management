package presentationLayer.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import presentationLayer.controller.switchScene.SwitchSceneClass;
import presentationLayer.controller.switchScene.SwitchSceneInterface;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *this class contains the initialization of the interface Product scene and adds event handle on buttons
 */
public class WelcomeController implements Initializable {
    private SwitchSceneInterface switchScene;
    @FXML
    private Button clientButton;
    @FXML
    private Button productButton;
    @FXML
    private Button orderButton;

    public WelcomeController(){
        switchScene = new SwitchSceneClass();
        clientButton = new Button();
        productButton = new Button();
        orderButton = new Button();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientButton.setOnAction(actionEvent -> switchScene.getClientScene(actionEvent));
        productButton.setOnAction(actionEvent -> switchScene.getProductScene(actionEvent));
        orderButton.setOnAction(actionEvent -> switchScene.getOrderScene(actionEvent));
    }
}
