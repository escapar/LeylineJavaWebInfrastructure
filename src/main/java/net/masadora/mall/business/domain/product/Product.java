package net.masadora.mall.business.domain.product;

import net.masadora.mall.business.domain.common.category.Category;
import net.masadora.mall.business.domain.common.property.Property;
import net.masadora.mall.business.domain.common.property.PropertyDetail;
import net.masadora.mall.business.domain.vendor.Vendor;
import net.masadora.mall.framework.domain.LeylineDO;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the mall_d_product database table.
 * 
 */
@Entity
@Table(name="mall_d_product")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements LeylineDO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="CREATED_AT")
	private Long createdAt;

	@Column(name="DELETED_AT")
	private Long deletedAt;

	private String description;

	@Column(name="MODIFIED_AT")
	private Long modifiedAt;

	private String name;

	private float price;

	private boolean reservable;

	@Column(name="SOLD_COUNT")
	private int soldCount;

	private int stock;

	private int weight;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="ROOT_PRODUCT_ID")
	private Product rootProduct;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="rootProduct", cascade={CascadeType.MERGE})
	private List<Product> subProducts;


	//uni-directional many-to-one association to Vendor
	@ManyToOne
	private Vendor vendor;

	//bi-directional many-to-one association to ProductImage
	@OneToMany(mappedBy="product")
	private List<ProductImage> images;

	//bi-directional many-to-one association to PropertyDetail
	@OneToMany(mappedBy="product")
	private List<PropertyDetail> properties;


	//uni-directional many-to-many association to Category
	@ManyToMany
	@JoinTable(
		name="mall_m2m_category_2_product"
		, joinColumns={
			@JoinColumn(name="PRODUCT_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="CATEGORY_ID")
			}
		)
	private List<Category> categories;

	public Product() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getDeletedAt() {
		return this.deletedAt;
	}

	public void setDeletedAt(Long deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getModifiedAt() {
		return this.modifiedAt;
	}

	public void setModifiedAt(Long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean getReservable() {
		return this.reservable;
	}

	public void setReservable(boolean reservable) {
		this.reservable = reservable;
	}

	public int getSoldCount() {
		return this.soldCount;
	}

	public void setSoldCount(int soldCount) {
		this.soldCount = soldCount;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Product getRootProduct() {
		return this.rootProduct;
	}

	public void setRootProduct(Product rootProduct) {
		this.rootProduct = rootProduct;
	}

	public List<Product> getSubProducts() {
		return this.subProducts;
	}

	public void setSubProducts(List<Product> subProducts) {
		this.subProducts = subProducts;
	}

	public Product addSubProduct(Product subProduct) {
		getSubProducts().add(subProduct);
		subProduct.setRootProduct(this);

		return subProduct;
	}

	public Product removeSubProduct(Product subProduct) {
		getSubProducts().remove(subProduct);
		subProduct.setRootProduct(null);

		return subProduct;
	}


	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<ProductImage> getImages() {
		return this.images;
	}

	public void setImages(List<ProductImage> images) {
		this.images = images;
	}

	public ProductImage addImage(ProductImage image) {
		getImages().add(image);
		image.setProduct(this);

		return image;
	}

	public ProductImage removeImage(ProductImage image) {
		getImages().remove(image);
		image.setProduct(null);

		return image;
	}

	public List<PropertyDetail> getProperties() {
		return this.properties;
	}

	public void setProperties(List<PropertyDetail> properties) {
		this.properties = properties;
	}

	public PropertyDetail addProperty(PropertyDetail property) {
		getProperties().add(property);
		property.setProduct(this);

		return property;
	}

	public PropertyDetail removeProperty(PropertyDetail property) {
		getProperties().remove(property);
		property.setProduct(null);

		return property;
	}

}