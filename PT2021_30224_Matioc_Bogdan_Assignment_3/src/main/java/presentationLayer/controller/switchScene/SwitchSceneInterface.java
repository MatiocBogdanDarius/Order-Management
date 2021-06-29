package presentationLayer.controller.switchScene;


import javafx.event.ActionEvent;

public interface SwitchSceneInterface {
    void getHomeScene(ActionEvent actionEvent);
    void getClientScene(ActionEvent actionEvent);
    void getProductScene(ActionEvent actionEvent);
    void getOrderScene(ActionEvent actionEvent);
    void getShoppingHistoryScene(ActionEvent actionEvent);
    void getBillScene(ActionEvent actionEvent);
}
