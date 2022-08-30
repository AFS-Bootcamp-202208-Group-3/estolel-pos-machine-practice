package pos.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemDataLoader {
    public static List<Item> loadAllItems() {
        Item firstItem = new Item("ITEM000000", "Coca-Cola", 3);
        Item secondItem = new Item("ITEM000001", "Sprite", 3);
        Item thirdItem = new Item("ITEM000004", "Battery", 2);
        List<Item> items = new ArrayList<>();
        items.add(firstItem);
        items.add(secondItem);
        items.add(thirdItem);

        return items;
    }

    public static List<String> loadBarcodes() {
        return Arrays.asList("ITEM000000", "ITEM000000", "ITEM000000", "ITEM000000", "ITEM000001", "ITEM000001", "ITEM000004", "ITEM000004", "ITEM000004");
    }
}
