package uj.jwzp.w2.entity;

import uj.jwzp.w2.entity.Item;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Transaction implements Serializable{
    int id;
    String timestamp;
    int customer_id;
    List<Item> items;
    BigDecimal sum;

    public Transaction(int id, String timestamp, int customer_id, List<Item> items) {
        this.id = id;
        this.timestamp = timestamp;
        this.customer_id = customer_id;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", timestamp='" + timestamp + '\'' +
                ", customer_id=" + customer_id +
                '}';
    }
}
