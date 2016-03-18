package assignment1;

/**
 * @author Espen R&oslash;nning
 */
public class Main {

    public static void main(String[] args) {
        GroceryList groceryList = new GroceryList();
        GroceryItem sauce = new GroceryItem("SAUS", 4, 10.50);
        GroceryItem juice = new GroceryItem("JUICE", 2, 29.99);
        GroceryItem bread = new GroceryItem("BRØD", 3, 23.50);
        GroceryItem tomato = new GroceryItem("TOMAT", 4, 5.0);
        GroceryItem milk = new GroceryItem("MELK", 5, 11.75);
        GroceryItem banana = new GroceryItem("BANAN", 1, 7.99);
        GroceryItem cheese = new GroceryItem("OST", 2, 25.00);
        GroceryItem garlic = new GroceryItem("HVITLØK", 4, 12.00);
        GroceryItem cucumber = new GroceryItem("AGURK", 1, 15.50);
        GroceryItem butter = new GroceryItem("SMØR", 1, 15.50);
        GroceryItem chocolate = new GroceryItem("SJOKOLADE", 5, 30.00);
        printAddResult(bread, groceryList);
        printAddResult(milk, groceryList);
        printAddResult(cheese, groceryList);
        printAddResult(butter, groceryList);
        printAddResult(sauce, groceryList);
        printAddResult(juice, groceryList);
        printAddResult(tomato, groceryList);
        printAddResult(banana, groceryList);
        printAddResult(garlic, groceryList);
        printAddResult(cucumber, groceryList);
        printAddResult(chocolate, groceryList);
        printRemoveResult("SJOKOLADE", groceryList);
        printListStatus(groceryList);
        printRemoveResult("TOMAT", groceryList);
        printRemoveResult("AGURK", groceryList);
        printRemoveResult("BANAN", groceryList);
        printRemoveResult("SAUS", groceryList);
        printRemoveResult("HVITLØK", groceryList);
        printAddResult(juice, groceryList);
        printAddResult(juice, groceryList);
        printListStatus(groceryList);
        printRemoveResult("JUICE", groceryList);
        printListStatus(groceryList);
    }

    public static void printAddResult(GroceryItem item, GroceryList groceryList) {
        if (groceryList.addItem(item)) {
            System.out.println("Du la til " + item.getQuantity() + " " + item.getName() + " i handlelisten. " +
                    "Du har nå varer for " + String.format("%.2f", groceryList.getTotalCost()) + ".");
        } else {
            System.out.println("Handlelisten er full! " + item.getName() + " ble ikke lagt til handlelisten.");
        }
    }

    public static void printRemoveResult(String item, GroceryList groceryList) {
        if (groceryList.removeItem(item)) {
            System.out.println("Fjernet " + item + " fra handlelisten. " +
                    "Du har nå varer for " + String.format("%.2f", groceryList.getTotalCost()) + ".");
        } else {
            System.out.println("Kunne ikke fjerne " + item + " fra handlelisten.");
        }
    }

    public static void printListStatus(GroceryList list) {
        System.out.println("Varer i handlelisten: ");
        System.out.println(list.toString());
    }
}
