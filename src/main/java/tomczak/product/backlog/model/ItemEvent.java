package tomczak.product.backlog.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "date", "item_id",
		"status_id" }))
@NamedQueries({ 
	@NamedQuery(name = ItemEvent.GET_BY_DATE_AND_ITEM_ID, query = "SELECT ie FROM ItemEvent ie WHERE ie.date = :date AND ie.item.id = :itemId") 
})
public class ItemEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String GET_BY_DATE_AND_ITEM_ID = "ItemEvent.getByDateAndItemId";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id = null;

	@Column(nullable = false)
	private @Temporal(TemporalType.DATE)
	Date date;

	@ManyToOne(optional = false)
	private Item item;

	@ManyToOne(optional = false)
	private Status status;

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
			return id.equals(((ItemEvent) that).id);
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

	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(final Item item) {
		this.item = item;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}
}