package tomczak.product.backlog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Basic;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name",
		"product_id" }))
@NamedQueries({
		@NamedQuery(name = Item.GET_BY_PRODUCT_ID_AND_STATUS_ID, query = "SELECT i FROM Item i WHERE i.product.id = :productId AND i.status.id=:statusId"),
		@NamedQuery(name = Item.COUNT_BY_NAME_AND_PRODUCT_ID, query = "SELECT COUNT(i) FROM Item i WHERE i.product.id = :productId AND i.name = :name"),
		@NamedQuery(name = Item.GET_NEXT_ITEM_NUMBER_FOR_PRODUCT_ID, query = "SELECT (MAX(i.number)+1) FROM Item i WHERE i.product.id = :productId"),
})
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class LastChangeComparator implements Comparator<Item> {

		@Override
		public int compare(Item item1, Item item2) {
			if (item1.getEvents() != null && item2.getEvents() != null) {
				if (item1.getEvents().size() > 0) {
					if (item2.getEvents().size() > 0) {
						item1.getEvents()
								.get(item1.getEvents().size() - 1)
								.getDate()
								.compareTo(
										item2.getEvents()
												.get(item2.getEvents().size() - 1)
												.getDate());
					}
				}
			}
			return 0;
		}

	}

	public static final String GET_BY_PRODUCT_ID_AND_STATUS_ID = "Item.getByProductIdAndStatusId";
	public static final String COUNT_BY_NAME_AND_PRODUCT_ID = "Item.CountByNameAndProductId";
	public static final String GET_NEXT_ITEM_NUMBER_FOR_PRODUCT_ID = "Item.getNextItemNumberForProductId";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id = null;
	
	@Column
	private String name;

	@ManyToOne(optional = false)
	private Product product;

	@ManyToOne(optional = false)
	private Status status;

	@Basic(optional = false)
	private Integer number;
	
	private @OneToMany(mappedBy = "item")
	@OrderBy("date")
	List<ItemEvent> events = new ArrayList<ItemEvent>();

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		if (id != null) {
			return id.equals(((Item) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String toString() {
		String result = "";
		if (name != null && !name.trim().isEmpty())
			result += name;
		return result;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(final Product product) {
		this.product = product;
	}

	public List<ItemEvent> getEvents() {
		return this.events;
	}

	public void setEvents(final List<ItemEvent> events) {
		this.events = events;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	

}