import exceptions.InvalidCoinException;
import exceptions.InsufficientBalanceException;
import exceptions.ProductOutOfStockException;

import java.util.*;

public class VendingMachine {
    private final int[] ALLOWED_COINS = {10, 5, 2, 1};
    private Inventory inventory;
    private Change machineChange;

    private int userBalance;
    private Change userChange;
    private Set<Product> userSelectedProducts;

    public VendingMachine(){
        inventory = new Inventory();
        userChange = new Change();
        userSelectedProducts = new HashSet<>();
        machineChange = new Change();
        //restock();
    }

    public int insertCoin(int coin) {
        if(Arrays.stream(ALLOWED_COINS).allMatch((item)-> item != coin))
            throw new InvalidCoinException();

        this.userBalance += coin;
        this.userChange.addCoin(coin);

        return userBalance;
    }

    public void selectProduct(Product product) {
        if(!inventory.isInStock(product)){
            throw new ProductOutOfStockException(product.label);
        }
        if(userBalance - product.price < 0){
            throw new InsufficientBalanceException();
        }

        userBalance -= product.price;
        this.userSelectedProducts.add(product);
    }

    public Change refund() {
        if(userChange.getTotal() == 0){
            throw new InsufficientBalanceException();
        }

        Change change = new Change(
                userChange.getOnesCount(),
                userChange.getTwosCount(),
                userChange.getFivesCount(),
                userChange.getTensCount()
        );

        this.userSelectedProducts.clear();
        userBalance = 0;
        this.userChange = new Change();

        return change;
    }

    public Change calculateChange(Change change, int returnAmount, Change returnChange){
        if(returnChange.getTotal() > returnAmount){
            return null;
        }
        if(returnAmount == returnChange.getTotal()){
            return returnChange;
        }

        for (int coin : ALLOWED_COINS){
            if(change.getCoinCount(coin) == 0)
                continue;
            change.removeCoin(coin);
            returnChange.addCoin(coin);

            Change res = calculateChange(change, returnAmount, returnChange);
            if(res != null)
                return res;

            change.addCoin(coin);
            returnChange.removeCoin(coin);
        }

        return null;
    }

    public VendingMachineOutput submitTransaction(){
        VendingMachineOutput vendingMachineOutput = new VendingMachineOutput();

        Change currentChange = new Change(
                machineChange.getOnesCount() + userChange.getOnesCount(),
                machineChange.getTwosCount() + userChange.getTwosCount(),
                machineChange.getFivesCount() + userChange.getFivesCount(),
                machineChange.getTensCount() + userChange.getTensCount()
        );

        Change returnChange = calculateChange(currentChange, userBalance, new Change());
        if (returnChange == null){
            //unable to return change, refund
            vendingMachineOutput.setChange(refund());
        }else {
            vendingMachineOutput.setChange(returnChange);
            vendingMachineOutput.setProducts(new HashSet<>(userSelectedProducts));

            for (Product product: userSelectedProducts){
                inventory.expend(product);
            }
        }

        userSelectedProducts.clear();
        return vendingMachineOutput;
    }

    public void restock(){
        this.machineChange = new Change(100, 100, 100, 100);
        this.inventory.restock();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Change getMachineChange() {
        return machineChange;
    }
}
