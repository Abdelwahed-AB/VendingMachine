import java.util.Set;

public class VendingMachineOutput {
    private Change change;
    private Set<Product> products;

    public VendingMachineOutput() {}

    public VendingMachineOutput(Change change, Set<Product> products) {
        this.change = change;
        this.products = products;
    }

    public Change getChange() {
        return change;
    }
    public Set<Product> getProducts() {
        return products;
    }
    public void setChange(Change change) {
        this.change = change;
    }
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}
