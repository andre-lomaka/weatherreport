package ee.sda.weatherarchive.util;

import ee.sda.weatherarchive.jpamodel.JPACity;
import ee.sda.weatherarchive.jpamodel.JPACountry;
import ee.sda.weatherarchive.jpamodel.JPARegion;
import ee.sda.weatherarchive.jpamodel.JPALocation;

public class LocationFactory {
   public static JPALocation createLocation(String name, String cityName, String countryName, String regionName, double latitude, double longitude) {
      JPALocation location = new JPALocation();
      location.setLocationName(name);
      if (cityName != null) {
         JPACity city = new JPACity();
         city.setName(cityName);
         location.setCity(city);
      }
      if (countryName != null) {
         JPACountry country = new JPACountry();
         country.setName(countryName);
         location.setCountry(country);
      }
      if (regionName != null) {
         JPARegion region = new JPARegion();
         region.setName(regionName);
         location.setRegion(region);
      }
      location.setLatitude(latitude);
      location.setLongitude(longitude);
      return location;
   }
}
