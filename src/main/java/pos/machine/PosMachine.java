package pos.machine;

import java.io.Console;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ReceiptItem> receiptItems = decodeToItems(barcodes);
        return null;
    }

    List<ReceiptItem> decodeToItems(List<String> barcodes) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        List<Item> items = ItemDataLoader.loadAllItems();

        barcodes.forEach(barcode -> {
            ReceiptItem newReceiptItem;
            Item currentItem = items.stream().filter(item -> {return item.getBarcode().equals(barcode);}).findAny().get();
            System.out.println(barcode);
            if(receiptItems.size()>0){

                newReceiptItem = getExistingReceiptItem(receiptItems, currentItem);

                if(!Objects.isNull(newReceiptItem)) {
                    updateExistingReceiptItem(receiptItems, newReceiptItem);
                }else{
                    newReceiptItem = createNewReceiptItem(currentItem);
                    receiptItems.add(newReceiptItem);
                }
            }else{
                newReceiptItem = createNewReceiptItem(currentItem);
                receiptItems.add(newReceiptItem);
            }

        });
        return receiptItems;
    }

    private static void updateExistingReceiptItem(List<ReceiptItem> receiptItems, ReceiptItem newReceiptItem) {
        Integer indexOfExisting = receiptItems.indexOf(newReceiptItem);
        receiptItems.get(indexOfExisting)
                .setQuantity(newReceiptItem.quantity+1)
                .setSubTotal(newReceiptItem.quantity* newReceiptItem.unitPrice);
    }

    private static ReceiptItem getExistingReceiptItem(List<ReceiptItem> receiptItems, Item item) {
        return receiptItems.stream().filter(receiptItem -> {
            return receiptItem.name.equals(item.getName());
        }).findAny().orElse(null);
    }

    private static ReceiptItem createNewReceiptItem(Item item) {
        ReceiptItem newReceiptItem;
        newReceiptItem = new ReceiptItem(
                item.getName(),
                1,
                item.getPrice(),
                item.getPrice()
        );
        return newReceiptItem;
    }

}
