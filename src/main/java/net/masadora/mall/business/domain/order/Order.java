package net.masadora.mall.business.domain.order;

import javax.persistence.*;
import java.io.Serializable;



/**
 * The persistent class for the mall_d_order database table.
 * 
 */
@Entity
@Table(name="mall_d_order")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="AMT_DISCOUNT")
	private float amtDiscount;

	@Column(name="AMT_PHASE_1")
	private float amtPhase1;

	@Column(name="AMT_PHASE_2")
	private float amtPhase2;

	@Column(name="AMT_SHIP")
	private float amtShip;

	@Column(name="CURRENT_PHASE")
	private int currentPhase;

	private String dest;

	@Column(name="PAYMENT_ID")
	private Long paymentId;

	@Column(name="SHIP_CODE")
	private String shipCode;

	@Column(name="SHIP_ETA")
	private Long shipEta;

	@Column(name="SHIPPED_AT_PHASE_1")
	private Long shippedAtPhase1;

	@Column(name="SHIPPED_AT_PHASE_2")
	private Long shippedAtPhase2;

	//uni-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="ROOT_ORDER_ID")
	private Order rootOrder;

	//uni-directional many-to-one association to OrderStatus
	@ManyToOne
	@JoinColumn(name="MALL_ORDER_STATUS_ID")
	private OrderStatus orderStatus;

	public Order() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getAmtDiscount() {
		return this.amtDiscount;
	}

	public void setAmtDiscount(float amtDiscount) {
		this.amtDiscount = amtDiscount;
	}

	public float getAmtPhase1() {
		return this.amtPhase1;
	}

	public void setAmtPhase1(float amtPhase1) {
		this.amtPhase1 = amtPhase1;
	}

	public float getAmtPhase2() {
		return this.amtPhase2;
	}

	public void setAmtPhase2(float amtPhase2) {
		this.amtPhase2 = amtPhase2;
	}

	public float getAmtShip() {
		return this.amtShip;
	}

	public void setAmtShip(float amtShip) {
		this.amtShip = amtShip;
	}

	public int getCurrentPhase() {
		return this.currentPhase;
	}

	public void setCurrentPhase(int currentPhase) {
		this.currentPhase = currentPhase;
	}

	public String getDest() {
		return this.dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public Long getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getShipCode() {
		return this.shipCode;
	}

	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

	public Long getShipEta() {
		return this.shipEta;
	}

	public void setShipEta(Long shipEta) {
		this.shipEta = shipEta;
	}

	public Long getShippedAtPhase1() {
		return this.shippedAtPhase1;
	}

	public void setShippedAtPhase1(Long shippedAtPhase1) {
		this.shippedAtPhase1 = shippedAtPhase1;
	}

	public Long getShippedAtPhase2() {
		return this.shippedAtPhase2;
	}

	public void setShippedAtPhase2(Long shippedAtPhase2) {
		this.shippedAtPhase2 = shippedAtPhase2;
	}

	public Order getRootOrder() {
		return this.rootOrder;
	}

	public void setRootOrder(Order rootOrder) {
		this.rootOrder = rootOrder;
	}

	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

}