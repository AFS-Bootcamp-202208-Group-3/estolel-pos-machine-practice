package pos.machine;

public class ReceiptItem {
    String name;

    int quantity;
    int unitPrice;
    int subTotal;



    public ReceiptItem(String name, int quantity, int unitPrice, int subTotal) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
    }



    public ReceiptItem setName(String name) {
        this.name = name;
        return this;
    }

    public ReceiptItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ReceiptItem setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public ReceiptItem setSubTotal(int subTotal) {
        this.subTotal = subTotal;
        return this;
    }
}
