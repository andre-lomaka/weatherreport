package ee.sda.weatherarchive.view;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class LocationFX {

   private final StringProperty id;
   private final StringProperty city;
   private final StringProperty country;
   private final StringProperty region;
   private final DoubleProperty latitude;
   private final DoubleProperty longitude;

   public LocationFX(String id, String city, String country, String region, double latitude, double longitude) {
      this.id = new SimpleStringProperty(id);
      this.city = new SimpleStringProperty(city);
      this.country = new SimpleStringProperty(country);
      this.region = new SimpleStringProperty(region);
      this.latitude = new SimpleDoubleProperty(latitude);
      this.longitude = new SimpleDoubleProperty(longitude);
   }

   public LocationFX(LocationFX location) {
      this.id = new SimpleStringProperty(location.getId());
      this.city = new SimpleStringProperty(location.getCity());
      this.country = new SimpleStringProperty(location.getCountry());
      this.region = new SimpleStringProperty(location.getRegion());
      this.latitude = new SimpleDoubleProperty(location.getLatitude());
      this.longitude = new SimpleDoubleProperty(location.getLongitude());
   }

   public String getId() {
      return id.get();
   }

   public StringProperty idProperty() {
      return id;
   }

   public void setId(String value) {
      id.set(value);
   }

   public String getCity() {
      return city.get();
   }

   public StringProperty cityProperty() {
      return city;
   }

   public void setCity(String value) {
      city.set(value);
   }

   public String getCountry() {
      return country.get();
   }

   public StringProperty countryProperty() {
      return country;
   }

   public void setCountry(String value) {
      country.set(value);
   }

   public String getRegion() {
      return region.get();
   }

   public StringProperty regionProperty() {
      return region;
   }

   public void setRegion(String value) {
      region.set(value);
   }

   public double getLatitude() {
      return latitude.get();
   }

   public DoubleProperty latitudeProperty() {
      return latitude;
   }

   public void setLatitude(double value) {
      latitude.set(value);
   }

   public double getLongitude() {
      return longitude.get();
   }

   public DoubleProperty longitudeProperty() {
      return longitude;
   }

   public void setLongitude(double value) {
      longitude.set(value);
   }

   public String getDisplayName() {
      return city.get() + ", " + country.get() + ", " + region.get();
   }
}
