package ee.sda.weatherarchive.repository;

import java.util.Optional;
import java.util.List;
import javax.persistence.EntityManager;

import ee.sda.weatherarchive.jpamodel.JPALocation;
import ee.sda.weatherarchive.jpamodel.JPACity;
import ee.sda.weatherarchive.jpamodel.JPARegion;
import ee.sda.weatherarchive.jpamodel.JPACountry;

@SuppressWarnings("unchecked")
public class LocationRepository {

   private final EntityManager entityManager;

   public LocationRepository(EntityManager entityManager) {
      this.entityManager = entityManager;
   }

   public Optional<JPALocation> save(JPALocation location) {
      List<String> l = entityManager.
                       createQuery("SELECT c.locationName FROM JPALocation c WHERE c.locationName = ?1", String.class).
                       setParameter(1, location.getLocationName()).
                       getResultList();
      if (l.isEmpty()) {
         try {
            addCity(location);
            addCountry(location);
            addRegion(location);
            entityManager.getTransaction().begin();
            entityManager.persist(location);
            entityManager.getTransaction().commit();
            return Optional.of(location);
         } catch (Exception e) {
            System.err.println("Exception occured. " + e.getMessage());
         }
      }
      return Optional.empty();
   }

   public void delete(JPALocation location) {
      List<JPALocation> locations = entityManager.
                                    createQuery("SELECT c FROM JPALocation c WHERE c.locationName = ?1", JPALocation.class).
                                    setParameter(1, location.getLocationName()).
                                    getResultList();
      if (!locations.isEmpty()) {
         JPALocation loc = locations.get(0);
         entityManager.getTransaction().begin();
         entityManager.createQuery("DELETE FROM JPAWeatherData WHERE location_id = ?1").
                                  setParameter(1, loc.getId()).
                                  executeUpdate();
         entityManager.remove(loc);
         entityManager.getTransaction().commit();
      }
   }

   public JPALocation findByName(String name) {
      List<JPALocation> locations = entityManager.
                                    createQuery("SELECT c FROM JPALocation c WHERE c.locationName = ?1").
                                    setParameter(1, name).
                                    getResultList();
      if (!locations.isEmpty()) {
         return locations.get(0);
      }
      return null;
   }

   public void update(JPALocation location) {
      JPALocation loc = location;
      if (!entityManager.contains(location)) {
         List<JPALocation> locations = entityManager.
               createQuery("SELECT c FROM JPALocation c WHERE c.locationName = ?1", JPALocation.class).
               setParameter(1, location.getLocationName()).
               getResultList();
         if (!locations.isEmpty())
            loc = locations.get(0);
         else
            return;
      }
      addCity(location);
      addCountry(location);
      addRegion(location);
      entityManager.getTransaction().begin();
      copyData(location, loc);
      entityManager.getTransaction().commit();
   }

   public void deleteAll() {
      try {
         entityManager.getTransaction().begin();
         entityManager.createNativeQuery("DELETE FROM locations").executeUpdate();
         entityManager.createNativeQuery("DELETE FROM cities").executeUpdate();
         entityManager.createNativeQuery("DELETE FROM countries").executeUpdate();
         entityManager.createNativeQuery("DELETE FROM regions").executeUpdate();
         entityManager.createNativeQuery("ALTER TABLE locations AUTO_INCREMENT=1").executeUpdate();
         entityManager.createNativeQuery("ALTER TABLE cities AUTO_INCREMENT=1").executeUpdate();
         entityManager.createNativeQuery("ALTER TABLE countries AUTO_INCREMENT=1").executeUpdate();
         entityManager.createNativeQuery("ALTER TABLE regions AUTO_INCREMENT=1").executeUpdate();
         entityManager.getTransaction().commit();
      } catch (Exception e) {
         System.err.println("Exception occured. " + e.getMessage());
      }
   }

   public List<JPALocation> findAll() {
      return entityManager.createQuery("FROM JPALocation").getResultList();
   }

   private void addCity(JPALocation location) {
      if (location.getCity() == null) return;
      String name = location.getCity().getName();
      List<JPACity> l = entityManager.
                        createQuery("SELECT c FROM JPACity c WHERE c.name = ?1", JPACity.class).
                        setParameter(1, name).
                        getResultList();
      if (l.isEmpty()) {
         entityManager.getTransaction().begin();
         entityManager.persist(location.getCity());
         entityManager.getTransaction().commit();
      } else
         location.setCity(l.get(0));
   }

   private void addCountry(JPALocation location) {
      if (location.getCountry() == null) return;
      String name = location.getCountry().getName();
      List<JPACountry> l = entityManager.
                           createQuery("SELECT c FROM JPACountry c WHERE c.name = ?1", JPACountry.class).
                           setParameter(1, name).
                           getResultList();
      if (l.isEmpty()) {
         entityManager.getTransaction().begin();
         entityManager.persist(location.getCountry());
         entityManager.getTransaction().commit();
      } else
         location.setCountry(l.get(0));
   }

   private void addRegion(JPALocation location) {
      if (location.getRegion() == null) return;
      String name = location.getRegion().getName();
      List<JPARegion> l = entityManager.
                          createQuery("SELECT c FROM JPARegion c WHERE c.name = ?1", JPARegion.class).
                          setParameter(1, name).
                          getResultList();
      if (l.isEmpty()) {
         entityManager.getTransaction().begin();
         entityManager.persist(location.getRegion());
         entityManager.getTransaction().commit();
      } else
         location.setRegion(l.get(0));
   }

   private void copyData(JPALocation src, JPALocation dest) {
      if (src == dest) return;
      dest.setCity(src.getCity());
      dest.setCountry(src.getCountry());
      dest.setRegion(src.getRegion());
      dest.setLatitude(src.getLatitude());
      dest.setLongitude(src.getLongitude());
   }
}
