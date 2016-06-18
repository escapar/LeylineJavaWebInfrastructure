package moe.src.example.mall.business.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import moe.src.leyline.framework.domain.LeylineDO;

/**
 * The persistent class for the coupon database table.
 */
@Entity
@NamedQuery(name = "Coupon.findAll", query = "SELECT c FROM Coupon c")
public class Coupon implements LeylineDO {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "discount_rule_id")
    private long discountRuleId;

    private long id;

    private String name;

    //bi-directional one-to-one association to DiscountRule
    @OneToOne
    @JoinColumn(name = "discount_rule_id")
    private DiscountRule discountRule;

    public Coupon() {
    }

    public long getDiscountRuleId() {
        return this.discountRuleId;
    }

    public void setDiscountRuleId(long discountRuleId) {
        this.discountRuleId = discountRuleId;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscountRule getDiscountRule() {
        return this.discountRule;
    }

    public void setDiscountRule(DiscountRule discountRule) {
        this.discountRule = discountRule;
    }

}
