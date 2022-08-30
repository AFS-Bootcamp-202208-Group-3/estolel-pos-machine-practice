package pos.machine;

import java.util.List;

public class Receipt {
    public Receipt(List<ReceiptItem> receiptItemList, int totalPrice) {
        this.receiptItemList = receiptItemList;
        this.totalPrice = totalPrice;
    }

    List<ReceiptItem> receiptItemList;
    int totalPrice;


}
