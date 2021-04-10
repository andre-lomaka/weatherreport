package ee.sda.weatherarchive.repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ee.sda.weatherarchive.util.HibernateUtil;
import ee.sda.weatherarchive.util.LocationFactory;
import ee.sda.weatherarchive.util.WeatherDataFactory;
import ee.sda.weatherarchive.jpamodel.JPALocation;
import ee.sda.weatherarchive.jpamodel.JPAWeatherData;

public class WeatherDataRepositoryTest {

   private static EntityManager entityManager;
   private static WeatherDataRepository weatherDataRepository;
   private static JPALocation location;
   private JPAWeatherData weatherData;

   public WeatherDataRepositoryTest() {
   }

   @BeforeAll
   public static void beforeTests() {
      entityManager = HibernateUtil.getEntityManager();
      weatherDataRepository = new WeatherDataRepository(entityManager);
      weatherDataRepository.deleteAll();
      LocationRepository locationRepository = new LocationRepository(entityManager);
      locationRepository.deleteAll();
      location = LocationFactory.createLocation("Favourite1", null, "Estonia", "Harju", -10.0, 20.0);
      locationRepository.save(location);
   }

   @AfterAll
   public static void afterTests() {
      HibernateUtil.closeEntityManager();
   }

   @BeforeEach
   public void setUp() {
      weatherData = WeatherDataFactory.createWeatherData(1.1, 2.2, 3.3, 4.4, 5.5, location, "AccuWeather", LocalDateTime.of(2021, 1, 1, 0, 0));
   }

   @Test
   public void givenWeatherDataEntity_whenInserted_thenWeatherDataIsPersisted() {
      weatherDataRepository.save(weatherData);
      assertWeatherDataPersisted(weatherData);
      weatherDataRepository.delete(weatherData);
   }

   private void assertWeatherDataPersisted(JPAWeatherData weatherDataExpected) {
      JPAWeatherData weatherData = entityManager.find(JPAWeatherData.class, weatherDataExpected.getId());
      assertNotNull(weatherData);
      assertEquals(weatherDataExpected.getTemperature(), weatherData.getTemperature(), 0.1);
      assertEquals(weatherDataExpected.getPressure(), weatherData.getPressure(), 0.1);
      assertEquals(weatherDataExpected.getHumidity(), weatherData.getHumidity(), 0.1);
      assertEquals(weatherDataExpected.getWindSpeed(), weatherData.getWindSpeed(), 0.1);
      assertEquals(weatherDataExpected.getWindDirection(), weatherData.getWindDirection(), 0.1);
      assertEquals(weatherDataExpected.getLocation().getLocationName(), weatherData.getLocation().getLocationName());
      assertEquals(weatherDataExpected.getService().getName(), weatherData.getService().getName());
      assertEquals(weatherDataExpected.getDateTime(), weatherData.getDateTime());
   }
}
