package store;

import javafx.util.Pair;

public class MemberImpl implements Member {
    Inventory inventory;
    ShoppingBasket shoppingBasket;

    public MemberImpl(Inventory inventory, ShoppingBasket shoppingBasket) {
        this.inventory = inventory;
        this.shoppingBasket = shoppingBasket;
    }

    @Override
    public void selectProduct(Product product, int quantity) throws IllegalArgumentException, IllegalStateException {
        try {
            //if inventory can't take product, will throw IllegalState and not call shoppingBasket
            //if product or quantity are illegal arguments, IllegalArgument will be thrown and not call shoppingBasket
            inventory.takeProduct(product,quantity);
            shoppingBasket.addItem(product, quantity);
        } catch(IllegalStateException | IllegalArgumentException e) {
            throw e;
        }
    }

    @Override
    public void returnProduct(Product product, int quantity) throws IllegalArgumentException, IllegalStateException {

        try {
            //if removeItem returns false, then basket can't remove product
            //so member throws IllegalState
            if(!shoppingBasket.removeItem(product, quantity))
                throw new IllegalStateException("Member's basket does not contain the required amount of product");
            //if code reaches here then product and quantity are valid
            inventory.addProduct(product, quantity);
        } catch(IllegalArgumentException e) {
            throw e;
        }
    }

    @Override
    public void finalisePurchases() {
        String purchase = "Purchase Completed!\n Here is your receipt:\n";
        for(int i=0; i < shoppingBasket.getItems().size(); i++) {
            Pair<Product, Integer> pair = shoppingBasket.getItems().get(i);
            purchase += "  (" +(i+1) + ") " + pair.getKey().getName() + "\t" + pair.getKey().getPrice() * pair.getValue() + "\n";
        }
        purchase += "TOTAL:  " + shoppingBasket.getValue();
        System.out.println(purchase);

        shoppingBasket.clear();
    }
}
