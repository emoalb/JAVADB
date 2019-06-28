package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "store_locations")
public class StoreLocation extends BaseEntity {
    private String locationName;
    private Set<Sale> sale;

    public StoreLocation() {

    }
@OneToMany(targetEntity = Sale.class,mappedBy = "storeLocation")
    public Set<Sale> getSale() {
        return sale;
    }

    public void setSale(Set<Sale> sale) {
        this.sale = sale;
    }


    @Column(name = "location_name")
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
