package moe.src.leyline.business.domain.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import moe.src.leyline.framework.domain.user.LeylineUser;

/**
 * The persistent class for the user database table.
 */
@Entity
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements LeylineUser {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String password;

    private int role;

    //bi-directional many-to-one association to OrderDetail
    //@OneToMany(mappedBy="user", fetch=FetchType.EAGER)

    //private List<OrderDetail> orderDetails;

    public User() {
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	/*public List<OrderDetail> getOrderDetails() {
        return this.orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetail addOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().add(orderDetail);
		orderDetail.setUser(this);

		return orderDetail;
	}

	public OrderDetail removeOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().remove(orderDetail);
		orderDetail.setUser(null);

		return orderDetail;
	}*/

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
