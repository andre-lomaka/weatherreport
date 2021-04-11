package ee.sda.weatherarchive.util;

import java.util.Map;

public class OpenWeatherMapDataDownloader extends DataDownloader {

   private static final DataDownloader INSTANCE = new OpenWeatherMapDataDownloader();

   private OpenWeatherMapDataDownloader() {
      super("http://api.openweathermap.org/data/2.5/");
      apiKey = properties.getProperty("OPEN_WEATHER_MAP_KEY");
   }

   public static DataDownloader getInstance() {
      return INSTANCE;
   }

   @Override
   public Map<String, Object> getCurrentWeather(double latitude, double longitude) {
      return null;
   }
}
