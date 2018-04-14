package uj.jwzp.w2.entity;

import java.math.BigDecimal;

public class Item {
    private String name;
    private int quantity;
    private BigDecimal price;

    public Item(String name, int quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (quantity != item.quantity) return false;
        if (!name.equals(item.name)) return false;
        return price.equals(item.price);
    }
}
