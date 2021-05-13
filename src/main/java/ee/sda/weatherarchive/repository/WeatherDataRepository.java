package ee.sda.weatherarchive.repository;

import java.util.Optional;
import java.util.List;
import javax.persistence.EntityManager;

import ee.sda.weatherarchive.jpamodel.JPAWeatherData;
import ee.sda.weatherarchive.jpamodel.JPAWeatherService;

public class WeatherDataRepository {

   private final EntityManager entityManager;

   public WeatherDataRepository(EntityManager entityManager) {
      this.entityManager = entityManager;
   }

   public Optional<JPAWeatherData> save(JPAWeatherData weatherData) {
      try {
         entityManager.getTransaction().begin();
         addService(weatherData);
         entityManager.persist(weatherData);
         entityManager.getTransaction().commit();
         return Optional.of(weatherData);
      } catch (Exception e) {
         System.err.println("Exception occured. " + e.getMessage());
      }
      return Optional.empty();
   }

   public List<JPAWeatherData> findById(String s) {
      return entityManager.createQuery("SELECT d FROM JPAWeatherData d WHERE location_id = (SELECT id FROM JPALocation WHERE locationName=?1)", JPAWeatherData.class).
                           setParameter(1, s).
                           getResultList();
   }

   public void delete(JPAWeatherData weatherData) {
      entityManager.getTransaction().begin();
      entityManager.remove(weatherData);
      entityManager.getTransaction().commit();
   }

   private void addService(JPAWeatherData weatherData) {
      String name = weatherData.getService().getName();
      JPAWeatherService service = entityManager.
            createQuery("SELECT c FROM JPAWeatherService c WHERE c.name = ?1", JPAWeatherService.class).
            setParameter(1, name).
            getSingleResult();
      weatherData.setService(service);
   }

   public void deleteAll() {
      try {
         entityManager.getTransaction().begin();
         entityManager.createNativeQuery("DELETE FROM weather_data").executeUpdate();
         entityManager.createNativeQuery("ALTER TABLE weather_data AUTO_INCREMENT=1").executeUpdate();
         entityManager.getTransaction().commit();
      } catch (Exception e) {
         System.err.println("Exception occured. " + e.getMessage());
      }
   }
}
