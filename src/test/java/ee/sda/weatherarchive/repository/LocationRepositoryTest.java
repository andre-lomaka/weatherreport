package ee.sda.weatherarchive.repository;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ee.sda.weatherarchive.util.HibernateUtil;
import ee.sda.weatherarchive.util.LocationFactory;
import ee.sda.weatherarchive.jpamodel.JPALocation;

public class LocationRepositoryTest {

   private static EntityManager entityManager;
   private static LocationRepository locationRepository;
   private JPALocation location1;
   private JPALocation location2;
   private JPALocation location3;

   public LocationRepositoryTest() {
   }

   @BeforeAll
   public static void beforeTests() {
      entityManager = HibernateUtil.getEntityManager();
      WeatherDataRepository weatherDataRepository = new WeatherDataRepository(entityManager);
      weatherDataRepository.deleteAll();
      locationRepository = new LocationRepository(entityManager);
      locationRepository.deleteAll();
   }

   @AfterAll
   public static void afterTests() {
      HibernateUtil.closeEntityManager();
   }

   @BeforeEach
   public void setUp() {
      location1 = LocationFactory.createLocation("Favourite1", null, "Estonia", "Harju", -10.0, 20.0);
      location2 = LocationFactory.createLocation("Favourite1", "Tartu", "Estonia", "Tartumaa", 30.0, 40.0);
      location3 = LocationFactory.createLocation("Favourite2", "Tallinn", "Estonia", "Harju", 35.0, 45.0);
   }

   @Test
   public void givenLocationEntity_whenInserted_thenLocationIsPersisted() {
      locationRepository.save(location1);
      assertLocationPersisted(location1);
      locationRepository.delete(location1);
   }

   @Test
   public void givenLocationEntity_whenInsertedAndDeleted_thenLocationIsDeleted() {
      locationRepository.save(location1);
      locationRepository.delete(location1);
      assertNull(entityManager.find(JPALocation.class, location1.getId()));
   }

   @Test
   public void givenTwoLocationEntities_whenInsertedWithEqualNames_thenOnlyFirstIsPersisted() {
      locationRepository.save(location1);
      locationRepository.save(location2);
      assertEquals(1, ((Number) entityManager.createQuery("SELECT COUNT(o) FROM JPALocation o").getSingleResult()).intValue());
      assertLocationPersisted(location1);
      locationRepository.delete(location1);
   }

   @Test
   public void givenTwoLocationEntities_whenInsertedWithDifferentNames_thenBothArePersisted() {
      locationRepository.save(location1);
      locationRepository.save(location3);
      assertEquals(2, ((Number) entityManager.createQuery("SELECT COUNT(o) FROM JPALocation o").getSingleResult()).intValue());
      assertLocationPersisted(location1);
      assertLocationPersisted(location3);
      locationRepository.delete(location1);
      locationRepository.delete(location3);
   }

   private void assertLocationPersisted(JPALocation locationExpected) {
      JPALocation location = entityManager.find(JPALocation.class, locationExpected.getId());
      assertNotNull(location);
      assertEquals(locationExpected.getLocationName(), location.getLocationName());
      assertEquals(locationExpected.getCity() != null ? locationExpected.getCity().getName() : null, location.getCity() != null ? location.getCity().getName() : null);
      assertEquals(locationExpected.getCountry() != null ? locationExpected.getCountry().getName() : null, location.getCountry() != null ? location.getCountry().getName() : null);
      assertEquals(locationExpected.getRegion() != null ? locationExpected.getRegion().getName() : null, location.getRegion() != null ? location.getRegion().getName() : null);
      assertEquals(locationExpected.getLatitude(), location.getLatitude(), 0.1);
      assertEquals(locationExpected.getLongitude(), location.getLongitude(), 0.1);
   }
}
