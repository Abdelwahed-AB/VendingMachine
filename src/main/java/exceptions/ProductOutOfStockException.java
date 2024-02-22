package exceptions;

public class ProductOutOfStockException extends RuntimeException{

    public ProductOutOfStockException(String label) {
        super("Product : " + label + " is not in stock");
    }
}
