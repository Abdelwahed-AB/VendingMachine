import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Product, Integer> stock;

    public Inventory(){
        this.stock = new HashMap<>();
        restock();
    }

    public void restock(){
        for (Product prod :  Product.values()){
            stock.put(prod, 20);
        }
    }

    public void expend(Product prod){
        int prevAmount = stock.get(prod);
        stock.put(prod, prevAmount - 1);
    }

    public boolean isInStock(Product product){
        return stock.get(product) > 0;
    }

    public Map<Product, Integer> getStock() {
        return stock;
    }
}
