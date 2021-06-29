package presentationLayer.controller.switchScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SwitchSceneClass implements SwitchSceneInterface {
    @Override
    public void getHomeScene(ActionEvent actionEvent){
        String fxmlPath = "src/main/java/presentationLayer/view/Welcome.fxml";
        changeScene(actionEvent, fxmlPath);
    }

    @Override
    public void getClientScene(ActionEvent actionEvent) {
        String fxmlPath = "src/main/java/presentationLayer/view/Client.fxml";
        changeScene(actionEvent, fxmlPath);
    }

    @Override
    public void getProductScene(ActionEvent actionEvent){
        String fxmlPath = "src/main/java/presentationLayer/view/Product.fxml";
        changeScene(actionEvent, fxmlPath);
    }

    @Override
    public void getOrderScene(ActionEvent actionEvent){
        String fxmlPath = "src/main/java/presentationLayer/view/Order.fxml";
        changeScene(actionEvent, fxmlPath);
    }

    @Override
    public void getShoppingHistoryScene(ActionEvent actionEvent) {
        String fxmlPath = "src/main/java/presentationLayer/view/ShoppingHistory.fxml";
        changeScene(actionEvent, fxmlPath);
    }

    @Override
    public void getBillScene(ActionEvent actionEvent) {
        String fxmlPath = "src/main/java/presentationLayer/view/Bill.fxml";
        changeScene(actionEvent, fxmlPath);
    }

    private void changeScene(ActionEvent actionEvent, String fxmlPath){
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void changeScene(ActionEvent actionEvent, String fxmlPath, int width, int height){
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setWidth(width);
            stage.setHeight(height);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
