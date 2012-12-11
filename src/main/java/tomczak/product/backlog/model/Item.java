package tomczak.product.backlog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.Version;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name","product_id"}))
@NamedQueries({
	@NamedQuery(name=Item.GET_BY_PRODUCT_ID, 
			query="SELECT i FROM Item i WHERE i.product.id = :productId"),
	@NamedQuery(name=Item.COUNT_BY_NAME_AND_PRODUCT_ID,
			query="SELECT COUNT(i) FROM Item i WHERE i.product.id = :productId AND i.name = :name")
})
public class Item implements Serializable
{

   public static final String GET_BY_PRODUCT_ID = "Item.getByProductId";
public static final String COUNT_BY_NAME_AND_PRODUCT_ID = "Item.CountByNameAndProductId";
@Id
   private @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;
   @Version
   private @Column(name = "version")
   int version = 0;

   @Column
   private String name;

   @ManyToOne
   private Product product;

   private @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
   @OrderBy("date")
   List<Event> events = new ArrayList<Event>();

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Item) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String toString()
   {
      String result = "";
      if (name != null && !name.trim().isEmpty())
         result += name;
      return result;
   }

   public Product getProduct()
   {
      return this.product;
   }

   public void setProduct(final Product product)
   {
      this.product = product;
   }

   public List<Event> getEvents()
   {
      return this.events;
   }

   public void setEvents(final List<Event> events)
   {
      this.events = events;
   }
}