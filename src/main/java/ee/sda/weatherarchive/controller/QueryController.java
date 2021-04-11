package ee.sda.weatherarchive.controller;

import java.util.Optional;
import java.util.List;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;

import ee.sda.weatherarchive.view.LocationFX;
import ee.sda.weatherarchive.repository.LocationRepository;
import ee.sda.weatherarchive.util.HibernateUtil;
import ee.sda.weatherarchive.util.LocationFactory;
import ee.sda.weatherarchive.jpamodel.JPALocation;

public class QueryController {

   private ObservableList<LocationFX> locationsFX;

   private static EntityManager entityManager;
   private static LocationRepository locationRepository;

   public QueryController() {
      if (entityManager == null) {
         entityManager = HibernateUtil.getEntityManager();
         locationRepository = new LocationRepository(entityManager);
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

   public void onAdd(LocationFX location) {
      Optional<JPALocation> opt = locationRepository.save(LocationFactory.createLocation(
                                                             location.getId(),
                                                             "".equals(location.getCity().trim()) ? null : location.getCity(),
                                                             "".equals(location.getCountry().trim()) ? null : location.getCountry(),
                                                             "".equals(location.getRegion().trim()) ? null : location.getRegion(),
                                                             location.getLatitude(),
                                                             location.getLongitude()
                                                         ));
      if (opt.isPresent()) locationsFX.add(new LocationFX(location));
   }

   public void onDownload(LocationFX location) {
      System.out.println("Downloading data for " + location.getId());
   }

   public static void closeConnection() {
      if (entityManager != null) {
         HibernateUtil.closeEntityManager();
         entityManager = null;
      }
   }
}
