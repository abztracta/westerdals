package assignment1;

/**
 * @author Espen R&oslash;nning
 */
public class GroceryItem {

    private String name;
    private int quantity;
    private double pricePerUnit;

    public GroceryItem() {
        this("-", 0, 0);
    }

    public GroceryItem(String name, int quantity, double pricePerUnit) {
        setName(name);
        setQuantity(quantity);
        setPricePerUnit(pricePerUnit);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public double getCost() {
        return pricePerUnit * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-4d%-16s" + "(%.2f /stk)", quantity, name, pricePerUnit);
    }
}
