package ee.sda.weatherarchive.controller;

import java.util.Optional;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;

import ee.sda.weatherarchive.view.LocationFX;
import ee.sda.weatherarchive.repository.LocationRepository;
import ee.sda.weatherarchive.repository.WeatherDataRepository;
import ee.sda.weatherarchive.util.HibernateUtil;
import ee.sda.weatherarchive.util.LocationFactory;
import ee.sda.weatherarchive.util.WeatherDataFactory;
import ee.sda.weatherarchive.util.DownloadUtil;
import ee.sda.weatherarchive.util.WeatherSource;
import ee.sda.weatherarchive.util.UnsuccessfulQueryException;
import ee.sda.weatherarchive.jpamodel.JPALocation;
import ee.sda.weatherarchive.jpamodel.JPAWeatherData;

public class QueryController {

   private ObservableList<LocationFX> locationsFX;

   private static EntityManager entityManager;
   private static LocationRepository locationRepository;
   private static WeatherDataRepository weatherDataRepository;

   public QueryController() {
      if (entityManager == null) {
         entityManager = HibernateUtil.getEntityManager();
         locationRepository = new LocationRepository(entityManager);
         weatherDataRepository = new WeatherDataRepository(entityManager);
      }
   }

   public void setLocations(ObservableList<LocationFX> locationsFX) {
      this.locationsFX = locationsFX;
   }

   public void onRetrieveAll() {
      locationsFX.clear();
      List<JPALocation> locs = locationRepository.findAll();
      for (JPALocation loc : locs) locationsFX.add(new LocationFX(
                                                     loc.getLocationName(),
                                                     loc.getCity() != null ? loc.getCity().getName() : "",
                                                     loc.getCountry() != null ? loc.getCountry().getName() : "",
                                                     loc.getRegion() != null ? loc.getRegion().getName() : "",
                                                     loc.getLatitude(),
                                                     loc.getLongitude()
                                                  ));
   }

   public void onUpdate(LocationFX location) {
      locationRepository.update(LocationFactory.createLocation(
                                   location.getId(),
                                   "".equals(location.getCity().trim()) ? null : location.getCity(),
                                   "".equals(location.getCountry().trim()) ? null : location.getCountry(),
                                   "".equals(location.getRegion().trim()) ? null : location.getRegion(),
                                   location.getLatitude(),
                                   location.getLongitude()
                               ));
   }

   public void onDelete(LocationFX location) {
      locationRepository.delete(LocationFactory.createLocation(
                                   location.getId(),
                                   "".equals(location.getCity().trim()) ? null : location.getCity(),
                                   "".equals(location.getCountry().trim()) ? null : location.getCountry(),
                                   "".equals(location.getRegion().trim()) ? null : location.getRegion(),
                                   location.getLatitude(),
                                   location.getLongitude()
                               ));
      locationsFX.remove(location);
   }

   public boolean onAdd(LocationFX location) {
      Optional<JPALocation> opt = locationRepository.save(LocationFactory.createLocation(
                                                             location.getId(),
                                                             "".equals(location.getCity().trim()) ? null : location.getCity(),
                                                             "".equals(location.getCountry().trim()) ? null : location.getCountry(),
                                                             "".equals(location.getRegion().trim()) ? null : location.getRegion(),
                                                             location.getLatitude(),
                                                             location.getLongitude()
                                                         ));
      if (opt.isPresent()) {
         locationsFX.add(new LocationFX(location));
         return true;
      } else
         return false;
   }

   public void onDownload(LocationFX location) throws UnsuccessfulQueryException {
      saveWeatherData(WeatherSource.ACCUWEATHER, "AccuWeather", location);
      saveWeatherData(WeatherSource.OPEN_WEATHER_MAP, "OpenWeatherMap", location);
   }

   public String onShowStatistics(LocationFX location) {
      List<JPAWeatherData> jwdList = weatherDataRepository.findById(location.getId());
      StringBuilder sb = new StringBuilder("Statistics for: " + location.getId() + "\n\n");
      sb.append("City: ").append(location.getCity()).append("\n")
        .append("Country: ").append(location.getCountry()).append("\n\n");
      for (JPAWeatherData jwd : jwdList) {
         sb.append("Service: ").append(jwd.getService().getName()).append("\n")
           .append("Time: ").append(jwd.getDateTime()).append("\n")
           .append("Temperature: ").append(jwd.getTemperature()).append("\n")
           .append("Pressure: ").append(jwd.getPressure()).append("\n\n");
      }
      return sb.toString();
   }

   private void saveWeatherData(WeatherSource ws, String sourceName, LocationFX location) throws UnsuccessfulQueryException {
      Map<String, Object> data = DownloadUtil.downloadWeatherData(ws, location.getLatitude(), location.getLongitude());
      double temperature = (double) data.get("temperature");
      double pressure = (double) data.get("pressure");
      double humidity = (double) data.get("humidity");
      double windSpeed = (double) data.get("windSpeed");
      double windDirection = (double) data.get("windDirection");
      weatherDataRepository.save(WeatherDataFactory.createWeatherData(temperature, pressure, humidity, windSpeed, windDirection, locationRepository.findByName(location.getId()), sourceName, LocalDateTime.now()));
   }

   public static void closeConnection() {
      if (entityManager != null) {
         HibernateUtil.closeEntityManager();
         entityManager = null;
      }
   }
}
