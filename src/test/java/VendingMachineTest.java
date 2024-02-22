import exceptions.InvalidCoinException;
import exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

public class VendingMachineTest {

    public VendingMachine vendingMachine;

    @BeforeEach
    public void reset(){
        vendingMachine = new VendingMachine();
    }
    private void insertCoins(int coin, int amount){
        for (int i = 0; i < amount; i ++)
            vendingMachine.insertCoin(coin);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10})
    @DisplayName("Should accept coins of value 1, 2, 5 and 10 dirhams")
    public void should_accept_coins_of_value_1_2_5_10(int coin){
        Assertions.assertDoesNotThrow(()->
                vendingMachine.insertCoin(coin));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 6, 7})
    @DisplayName("Should throw when passed invalid coin value")
    public void should_throw_when_passed_invalid_coin_value(int coin){
        Assertions.assertThrows(InvalidCoinException.class, ()->vendingMachine.insertCoin(coin));
    }

    @Test
    @DisplayName("Should allow user to select products based on their id")
    public void should_allow_user_to_select_products_based_on_id(){
        insertCoins(10, 10);
        Assertions.assertDoesNotThrow(()->vendingMachine.selectProduct(Product.WATER));
    }

    @Test
    @DisplayName("Should throw when user selects a product and doesn't have enough balance")
    public void should_throw_when_user_selects_a_product_and_doesnt_have_enough_balance(){
        Assertions.assertThrows(InsufficientBalanceException.class, ()->vendingMachine.selectProduct(Product.WATER));
    }

    @Test
    @DisplayName("Should allow user to ask for a refund")
    public void should_allow_user_to_ask_for_refund(){
        Change expectedChange = new Change(10, 3, 1, 0);

        insertCoins(1, 10);
        insertCoins(2, 3);
        insertCoins(5, 1);

        Assertions.assertEquals(expectedChange, vendingMachine.refund());
    }

    @Test
    @DisplayName("Should throw when user hasn't inserted any coins and asks for a refund")
    public void should_throw_when_no_userbalance_and_asks_for_refund(){
        Assertions.assertThrows(InsufficientBalanceException.class, ()->vendingMachine.refund());
    }

    @Test
    @DisplayName("Should return the selected products and remaining change")
    public void should_return_selected_products_and_remaining_change(){
        vendingMachine.restock();
        insertCoins(5, 2);
        vendingMachine.selectProduct(Product.WATER);
        Change expectedChange = new Change(0, 0,1, 0);
        VendingMachineOutput output = vendingMachine.submitTransaction();

        Assertions.assertEquals(expectedChange, output.getChange());
        Assertions.assertEquals(1, output.getProducts().size());
        Assertions.assertTrue(output.getProducts().contains(Product.WATER));
    }

    @Test
    @DisplayName("Should return no products and refunds change")
    public void should_return_no_products_and_refunds_change(){
        insertCoins(5, 2);
        vendingMachine.selectProduct(Product.COCA);
        Change expectedChange = new Change(0, 0,2, 0);
        VendingMachineOutput output = vendingMachine.submitTransaction();

        Assertions.assertEquals(expectedChange, output.getChange());
    }

    @Test
    @DisplayName("Should restock vending machine")
    public void should_restock_vending_machine(){
        vendingMachine.restock();
        Map<Product, Integer> stock = vendingMachine.getInventory().getStock();
        Change change = vendingMachine.getMachineChange();

        Change expectedChange = new Change(100, 100, 100, 100);
        Assertions.assertEquals(expectedChange, change);

        Assertions.assertEquals(20, stock.get(Product.WATER));
        Assertions.assertEquals(20, stock.get(Product.BUENO));
        Assertions.assertEquals(20, stock.get(Product.COCA));
        Assertions.assertEquals(20, stock.get(Product.TWIX));
    }
}
