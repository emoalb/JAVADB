package alararestaurant.domain.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {
    private Order order;
    private Item item;
    private Integer quantity;

    public OrderItem() {
    }


    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id", referencedColumnName = "id",nullable = false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(targetEntity = Item.class)
    @JoinColumn(name = "item_id", referencedColumnName = "id",nullable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
