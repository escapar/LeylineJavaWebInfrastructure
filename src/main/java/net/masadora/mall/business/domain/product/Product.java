package net.masadora.mall.business.domain.product;

import net.masadora.mall.business.domain.common.category.Category;
import net.masadora.mall.business.domain.common.property.Property;
import net.masadora.mall.business.domain.common.property.PropertyDetail;
import net.masadora.mall.business.domain.vendor.Vendor;
import net.masadora.mall.framework.domain.LeylineDO;
import org.joda.time.DateTime;
import org.springframework.data.annotation.*;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Transient;

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
	@OneToMany(mappedBy="rootProduct", cascade=CascadeType.MERGE)
	private List<Product> subProducts;


	//uni-directional many-to-one association to Vendor
	@ManyToOne
	private Vendor vendor;

	//bi-directional many-to-one association to ProductImage
	@OneToMany(cascade = CascadeType.MERGE)
	@JoinColumn(name = "PRODUCT_ID")
	private List<ProductImage> images;

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

	//uni-directional many-to-many association to Category
	@ManyToMany
	@JoinTable(
			name="mall_m2m_property_detail_2_product"
			, joinColumns={
			@JoinColumn(name="PRODUCT_ID")
	}
			, inverseJoinColumns={
			@JoinColumn(name="PROPERTY_DETAIL_ID")
	}
	)
	private List<PropertyDetail> properties;

	public Product() {
		if(createdAt == null){
			createdAt = new DateTime().getMillis();
		}
	}

	public Long getId() {
		return this.id;
	}

	public Product setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getCreatedAt() {
		return this.createdAt;
	}

	public Product setCreatedAt(Long createdAt) {
		this.createdAt = createdAt == null ? new DateTime().getMillis() : createdAt;
		return this;
	}

	public Long getDeletedAt() {
		return this.deletedAt;
	}

	public Product setDeletedAt(Long deletedAt) {
		this.deletedAt = deletedAt;
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public Product setDescription(String description) {
		this.description = description;
		return this;
	}

	public Long getModifiedAt() {
		return this.modifiedAt;
	}

	public Product setModifiedAt(Long modifiedAt) {
		this.modifiedAt = modifiedAt;
		return this;
	}

	public String getName() {
		return this.name;
	}

	public Product setName(String name) {
		this.name = name;
		return this;
	}

	public float getPrice() {
		return this.price;
	}

	public Product setPrice(float price) {
		this.price = price;
		return this;
	}

	public boolean getReservable() {
		return this.reservable;
	}

	public Product setReservable(boolean reservable) {
		this.reservable = reservable;
		return this;
	}

	public int getSoldCount() {
		return this.soldCount;
	}

	public Product setSoldCount(int soldCount) {
		this.soldCount = soldCount;
		return this;
	}

	public int getStock() {
		return this.stock;
	}

	public Product setStock(int stock) {
		this.stock = stock;
		return this;
	}

	public int getWeight() {
		return this.weight;
	}

	public Product setWeight(int weight) {
		this.weight = weight;
		return this;
	}

	public Product getRootProduct() {
		return this.rootProduct;
	}

	public Product setRootProduct(Product rootProduct) {
		this.rootProduct = rootProduct;
		return this;
	}

	public List<Product> getSubProducts() {
		return this.subProducts;
	}

	public Product setSubProducts(List<Product> subProducts) {
		this.subProducts = subProducts;
		return this;
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

	public Product setVendor(Vendor vendor) {
		this.vendor = vendor;
		return this;
	}

	public List<ProductImage> getImages() {
		return this.images;
	}

	public Product setImages(List<ProductImage> images) {
		this.images = images;
		return this;
	}

	public ProductImage addImage(ProductImage image) {
		getImages().add(image);
	//	image.setProduct(this);
		return image;
	}

	public ProductImage removeImage(ProductImage image) {
		getImages().remove(image);
	//	image.setProduct(null);
		return image;
	}

	public List<PropertyDetail> getProperties() {
		return this.properties;
	}

	public Product setProperties(List<PropertyDetail> properties) {
		this.properties = properties;
		return this;
	}

	public PropertyDetail addProperty(PropertyDetail property) {
		getProperties().add(property);
	//	property.setProduct(this);

		return property;
	}

	public PropertyDetail removeProperty(PropertyDetail property) {
		getProperties().remove(property);
	//	property.setProduct(null);

		return property;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public Product setCategories(List<Category> categories) {
		this.categories = categories;
		return this;
	}
}