package ee.sda.weatherarchive.jpamodel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "locations")
public class JPALocation {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;

   @Column(name = "location_name")
   private String locationName;

   @ManyToOne
   @JoinColumn(name = "city_id")
   private JPACity city;

   @ManyToOne
   @JoinColumn(name = "country_id")
   private JPACountry country;

   @ManyToOne
   @JoinColumn(name = "region_id")
   private JPARegion region;

   @Column(name = "latitude")
   private double latitude;

   @Column(name = "longitude")
   private double longitude;

   public JPALocation() {
   }

   public JPALocation(int id, String locationName, JPACity city, JPACountry country, JPARegion region, double latitude, double longitude) {
      this.id = id;
      this.locationName = locationName;
      this.city = city;
      this.country = country;
      this.region = region;
      this.latitude = latitude;
      this.longitude = longitude;
   }

   public void setId(int value) {
      id = value;
   }

   public int getId() {
      return id;
   }

   public void setLocationName(String value) {
      locationName = value;
   }

   public String getLocationName() {
      return locationName;
   }

   public void setCity(JPACity value) {
      city = value;
   }

   public JPACity getCity() {
      return city;
   }

   public void setCountry(JPACountry value) {
      country = value;
   }

   public JPACountry getCountry() {
      return country;
   }

   public void setRegion(JPARegion value) {
      region = value;
   }

   public JPARegion getRegion() {
      return region;
   }

   public void setLatitude(double value) {
      latitude = value;
   }

   public double getLatitude() {
      return latitude;
   }

   public void setLongitude(double value) {
      longitude = value;
   }

   public double getLongitude() {
      return longitude;
   }
}
