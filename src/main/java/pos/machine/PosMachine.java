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
        Receipt receipt = calculateCost(receiptItems);
        String  receiptString = renderReceipt(receipt);
        return receiptString;
    }

    private String renderReceipt(Receipt receipt) {
        String itemsReceipt = generateItemsReceipt(receipt);
        String receiptString = generateReceipt(itemsReceipt, receipt.totalPrice);
        return receiptString;
    }

    private String generateReceipt(String itemsReceipt, int totalPrice) {
        return "***<store earning no money>Receipt***\n" +itemsReceipt+
                "----------------------\n" +
                "Total: "+totalPrice+" (yuan)\n" +
                "**********************";
    }

    private String generateItemsReceipt(Receipt receipt) {
        String itemsReceipt ="";
        String finalItemsReceipt = "";
//        "Name: Coca-Cola, Quantity: 4, Unit price: 3 (yuan), Subtotal: 12 (yuan)\n"
        finalItemsReceipt = receipt.receiptItemList.stream().map(receiptItem -> {
           return itemsReceipt.concat("Name: "+receiptItem.name+", Quantity: "+receiptItem.quantity+", Unit price: "+
                    receiptItem.unitPrice+" (yuan), Subtotal: "+receiptItem.subTotal+" (yuan)\n");
        }).collect(Collectors.joining());
        return finalItemsReceipt;
    }

    private Receipt calculateCost(List<ReceiptItem> receiptItems) {
        //I omitted "calculateItemsCost" because it was already done during decodeToItems() method
        Receipt receipt = new Receipt(receiptItems, calculateTotalPrice(receiptItems));
        return receipt;
    }

    private static int calculateTotalPrice(List<ReceiptItem> receiptItems) {
        return receiptItems.stream().mapToInt(receiptItem -> {
            return receiptItem.subTotal;
        }).sum();
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
