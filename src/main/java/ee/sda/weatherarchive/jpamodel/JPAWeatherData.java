package ee.sda.weatherarchive.jpamodel;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "weather_data")
public class JPAWeatherData {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;

   @Column(name = "temperature")
   private double temperature;

   @Column(name = "pressure")
   private double pressure;

   @Column(name = "humidity")
   private double humidity;

   @Column(name = "wind_speed")
   private double windSpeed;

   @Column(name = "wind_direction")
   private double windDirection;

   @ManyToOne
   @JoinColumn(name = "location_id")
   private JPALocation location;

   @ManyToOne
   @JoinColumn(name = "service_id")
   private JPAWeatherService service;

   @Column(name = "date_time")
   private LocalDateTime dateTime; 

   public JPAWeatherData() {
   }

   public JPAWeatherData(int id, double temperature, double pressure, double humidity, double windSpeed, double windDirection, JPALocation location, JPAWeatherService service, LocalDateTime dateTime) {
      this.id = id;
      this.temperature = temperature;
      this.pressure = pressure;
      this.humidity = humidity;
      this.windSpeed = windSpeed;
      this.windDirection = windDirection;
      this.location = location;
      this.service = service;
      this.dateTime = dateTime;
   }

   public void setId(int value) {
      id = value;
   }

   public int getId() {
      return id;
   }

   public void setTemperature(double value) {
      temperature = value;
   }

   public double getTemperature() {
      return temperature;
   }

   public void setPressure(double value) {
      pressure = value;
   }

   public double getPressure() {
      return pressure;
   }

   public void setHumidity(double value) {
      humidity = value;
   }

   public double getHumidity() {
      return humidity;
   }

   public void setWindSpeed(double value) {
      windSpeed = value;
   }

   public double getWindSpeed() {
      return windSpeed;
   }

   public void setWindDirection(double value) {
      windDirection = value;
   }

   public double getWindDirection() {
      return windDirection;
   }

   public void setLocation(JPALocation value) {
      location = value;
   }

   public JPALocation getLocation() {
      return location;
   }

   public void setService(JPAWeatherService value) {
      service = value;
   }

   public JPAWeatherService getService() {
      return service;
   }

   public void setDateTime(LocalDateTime value) {
      dateTime = value;
   }

   public LocalDateTime getDateTime() {
      return dateTime;
   }
}
