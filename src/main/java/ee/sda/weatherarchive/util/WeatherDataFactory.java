package ee.sda.weatherarchive.util;

import java.time.LocalDateTime;

import ee.sda.weatherarchive.jpamodel.JPALocation;
import ee.sda.weatherarchive.jpamodel.JPAWeatherData;
import ee.sda.weatherarchive.jpamodel.JPAWeatherService;

public class WeatherDataFactory {
   public static JPAWeatherData createWeatherData(double temperature, double pressure, double humidity, double windSpeed, double windDirection, JPALocation location, String serviceName, LocalDateTime dateTime) {
      JPAWeatherData wd = new JPAWeatherData();
      wd.setTemperature(temperature);
      wd.setPressure(pressure);
      wd.setHumidity(humidity);
      wd.setWindSpeed(windSpeed);
      wd.setWindDirection(windDirection);
      wd.setLocation(location);
      JPAWeatherService service = new JPAWeatherService();
      service.setName(serviceName);
      wd.setService(service);
      wd.setDateTime(dateTime);
      return wd;
   }
}
