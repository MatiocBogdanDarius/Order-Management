package businessLogic;

import dataAcces.BuyListDAO;
import dataAcces.OrderDAO;
import dataAcces.ProductDAO;
import exceptions.UnavailableStockException;
import model.dto.ProductDTO;
import model.entities.BuyList;
import model.entities.Product;
import model.entities._Order;
import model.dto.BuyListDTO;

import java.util.List;

/**
 * this class contains the logic part on an order
 */
public class OrderLogic {
    private OrderDAO orderDAO;

    public OrderLogic(){
        orderDAO = new OrderDAO();
    }

    public void editQuantityHandleLogic(BuyListDTO orderTableContent, int newQuantity){
        int id = Integer.parseInt(orderTableContent.getProduct().split("\t")[0]);
        ProductDTO product = (new ProductLogic()).findById(id);
        double pricePerProduct = product.getPrice();
        orderTableContent.setQuantity(newQuantity, pricePerProduct);
    }

    public void placeOrder(List<BuyListDTO> shoppingList, String selectClient) throws UnavailableStockException {
        int idClient = Integer.parseInt(selectClient.split("\t")[0]);
        _Order order = new _Order(idClient);
        orderDAO.add(order);
        order = orderDAO.findLast();
        for(BuyListDTO fieldOfShoppingList:shoppingList){
            int idProduct = Integer.parseInt(fieldOfShoppingList.getProduct().split("\t")[0]);
            Product product = (new ProductDAO()).findById(idProduct);
            if(product.getStock() < fieldOfShoppingList.getQuantity()){
                throw new UnavailableStockException("Unavailable stock");
            }
        }

        for(BuyListDTO fieldOfShoppingList:shoppingList) {
            int idProduct = Integer.parseInt(fieldOfShoppingList.getProduct().split("\t")[0]);
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.findById(idProduct);
            (new BuyListDAO()).add(new BuyList(
                    order.getId(),
                    idProduct,
                    fieldOfShoppingList.getQuantity(),
                    fieldOfShoppingList.getPrice())
            );
            product.setStock(product.getStock() - fieldOfShoppingList.getQuantity());
            productDAO.update(product);
        }
    }
}
