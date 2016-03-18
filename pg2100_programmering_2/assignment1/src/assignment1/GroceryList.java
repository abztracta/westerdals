package assignment1;

/**
 * @author Espen R&oslash;nning
 */
public class GroceryList {

    private static final int DEFAULT_CAPACITY = 10;

    private GroceryItem[] groceryList;
    private int size;

    public GroceryList() {
        this(DEFAULT_CAPACITY);
    }

    public GroceryList(int capacity) {
        groceryList = new GroceryItem[capacity];
    }

    /**
     * This method adds a <code>GroceryItem</code> to the <code>GroceryList</code>.
     * If the item already exists, the quantity will be updated.
     * The item will be inserted after the last existing item.
     *
     * @param item The item to be added.
     * @return <code>true</code> if item is added, <code>false</code> if the list is full.
     */
    public boolean addItem(GroceryItem item) {
        for (int i = 0; i < size; i++) {
            if (groceryList[i].getName().equals(item.getName())) {
                groceryList[i].setQuantity(groceryList[i].getQuantity() + item.getQuantity());
                return true;
            }
        }
        if (size < groceryList.length) {
            groceryList[size++] = item;
            return true;
        }
        return false;
    }

    public double getTotalCost() {
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += groceryList[i].getCost();
        }
        return sum;
    }

    /**
     *
     * @param name the item to be removed
     * @return <code>true</code> if item was removed, <code>false</code> if not.
     */
    public boolean removeItem(String name) {
        for (int i = 0; i < size; i++) {
            if (groceryList[i].getName().equals(name)) {
                removeItem(i); // remove item and rearrange array
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Rearranges array so that all null-references are kept at the back the list.
     *
     * @param index
     */
    private void removeItem(int index) {
        for (int i = index; i < groceryList.length - 1; i++) {
            groceryList[i] = groceryList[i + 1];
        }
        groceryList[groceryList.length - 1] = null;
    }

    /**
     * Goes through all items in the <code>GroceryList</code> and prints their quantity, name and price per unit
     * before printing the total cost of all items in the list.
     */
    @Override
    public String toString() {
        String list = "";
        double sum = 0;
        for (int i = 0; i < size; i++) {
            list += groceryList[i] + "\n";
            sum += groceryList[i].getCost();
        }
        list += "Samlet kostnad: " + String.format("%.2f", sum);
        return list;
    }
}
