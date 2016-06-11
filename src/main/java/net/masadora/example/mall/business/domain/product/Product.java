package net.masadora.example.mall.business.domain.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;
import moe.src.leyline.framework.domain.LeylineDO;

/**
 * The persistent class for the product database table.
 */
@Entity
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")

@EqualsAndHashCode
@Data
public class Product implements LeylineDO {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private double price;

    //bi-directional many-to-one association to OrderDetail
    //@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    //private List<OrderDetail> orderDetails;

    public Product() {
    }


}
