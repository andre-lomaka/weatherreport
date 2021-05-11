package ee.sda.weatherarchive.util;

import java.util.Map;
import java.util.HashMap;
import org.json.JSONObject;

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
   public Map<String, Object> getCurrentWeather(double latitude, double longitude) throws UnsuccessfulQueryException {
      Map<String, Object> weatherDataMap = new HashMap<>();
      String data = getDataFromURI(addressPrefix + "onecall?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=" + apiKey);
      if (data != null && data.charAt(0) != '{') {
         throw new UnsuccessfulQueryException("getCurrentWeather: couldn't get data");
      }

      JSONObject jobj = new JSONObject(data);
      JSONObject jcur = jobj.getJSONObject("current");

      double temperature = jcur.getDouble("temp");
      double pressure = jcur.getDouble("pressure");
      double humidity = jcur.getDouble("humidity");
      double windDirection = jcur.getDouble("wind_deg");
      double windSpeed = 3.6*jcur.getDouble("wind_speed");

      weatherDataMap.put("temperature", temperature);
      weatherDataMap.put("pressure", pressure);
      weatherDataMap.put("humidity", humidity);
      weatherDataMap.put("windDirection", windDirection);
      weatherDataMap.put("windSpeed", windSpeed);

      return weatherDataMap;
   }
}
