package ee.sda.weatherarchive.util;

import java.util.Map;

public final class DownloadUtil {

   private static final DataDownloader accuWeatherDataDownloader = AccuWeatherDataDownloader.getInstance();
   private static final DataDownloader openWeatherMapDataDownloader = OpenWeatherMapDataDownloader.getInstance();

   private DownloadUtil() {
   }

   public static Map<String, Object> downloadWeatherData(WeatherSource source, double latitude, double longitude) throws UnsuccessfulQueryException {
      return switch (source) {
         case ACCUWEATHER -> accuWeatherDataDownloader.getCurrentWeather(latitude, longitude);
         case OPEN_WEATHER_MAP -> openWeatherMapDataDownloader.getCurrentWeather(latitude, longitude);
      };
   }
}
