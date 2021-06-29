package businessLogic;

import dataAcces.BuyListDAO;
import dataAcces.ClientDAO;
import dataAcces.OrderDAO;
import model.dto.BuyListDTO;
import model.dto.ClientDTO;
import model.dto.OrderDTO;
import model.dto.ProductDTO;
import model.entities.BuyList;
import model.entities.Client;
import model.entities.Product;
import model.entities._Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * this class contains the logic part of the shopping history
 */
public class ShoppingHistoryLogic{

    public List<OrderDTO> getOrders() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<OrderDTO> ordersDTO = new ArrayList<>();
        List<_Order> orders = (new OrderDAO()).findAll();
        orders.forEach(order -> ordersDTO.add(new OrderDTO(
                    order.getId(),
                    dateFormat.format(order.getDate()),
                    (new ClientDAO()).findById(order.getIdClient()).toString()
        )));
        return ordersDTO;
    }

    public List<BuyListDTO> getBuyListByOrder(OrderDTO orderDTO){
        int orderId = orderDTO.getId();
        List<BuyList> buyList = (new BuyListDAO()).findByField("idOrder", String.valueOf(orderId));
        List<BuyListDTO> buyListDTO = new ArrayList<>();
        buyList.forEach(buyListMember -> {
            ProductDTO product = (new ProductLogic()).findById(buyListMember.getIdProduct());
            buyListDTO.add(new BuyListDTO(
                product.toString(),
                    buyListMember.getQuantity(),
                    buyListMember.getPrice()
                ));
        });
        return buyListDTO;
    }
}