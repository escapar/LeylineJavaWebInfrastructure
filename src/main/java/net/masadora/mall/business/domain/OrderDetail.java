package net.masadora.mall.business.domain;

import moe.src.leyline.framework.domain.LeylineDO;
import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.domain.user.User;

import javax.persistence.*;

/**
 * The persistent class for the order_detail database table.
 */
@Entity
@Table(name = "order_detail")
@NamedQuery(name = "OrderDetail.findAll", query = "SELECT o FROM OrderDetail o")
public class OrderDetail implements LeylineDO {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "parent_order_id")
    private long parentOrderId;

    private String quantity;

    //bi-directional many-to-one association to Product
    @ManyToOne
    private Product product;

    //bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;

    public OrderDetail() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentOrderId() {
        return this.parentOrderId;
    }

    public void setParentOrderId(long parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
