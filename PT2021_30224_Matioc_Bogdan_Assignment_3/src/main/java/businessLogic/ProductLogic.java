package businessLogic;


import dataAcces.ProductDAO;
import model.dto.ProductDTO;
import model.entities.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * this class contains the logic part on a product
 */
public class ProductLogic {
    private ProductDAO productDAO;

    public ProductLogic(){
        productDAO = new ProductDAO();
    }

    public List<ProductDTO> findAll(){
        List<Product> products =  productDAO.findAll();
        List<ProductDTO> result = new ArrayList<>();
        products.forEach(product -> {
            if (product.getDeleted() == 0 && product.getStock() > 0) {
                result.add(new ProductDTO(product));
            }
        });
        return result;
    }

    public ProductDTO findById(int id){
        return new ProductDTO(productDAO.findById(id));
    }

    public void add(ProductDTO product) {
        productDAO.add(new Product(product));
    }

    public void update(ProductDTO product) {
        productDAO.update(new Product(product));
    }

    public void delete(ProductDTO product) {
        Product deletedProduct = new Product(product);
        deletedProduct.setDeleted(1);
        productDAO.update(deletedProduct);
    }

}
