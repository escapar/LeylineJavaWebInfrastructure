package net.masadora.mall.business.domain;

import moe.src.leyline.framework.domain.LeylineDO;

import javax.persistence.*;

/**
 * The persistent class for the order_parent database table.
 */
@Entity
@Table(name = "order_parent")
@NamedQuery(name = "OrderParent.findAll", query = "SELECT o FROM OrderParent o")
public class OrderParent implements LeylineDO {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public OrderParent() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}