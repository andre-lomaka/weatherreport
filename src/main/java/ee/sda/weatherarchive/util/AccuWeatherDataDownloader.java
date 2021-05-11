package ee.sda.weatherarchive.util;

import java.util.Map;
import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONArray;

public class AccuWeatherDataDownloader extends DataDownloader {

   private static final DataDownloader INSTANCE = new AccuWeatherDataDownloader();

   private AccuWeatherDataDownloader() {
      super("http://dataservice.accuweather.com/");
      apiKey = properties.getProperty("ACCUWEATHER_KEY");
   }

   public static DataDownloader getInstance() {
      return INSTANCE;
   }

   @Override
   public Map<String, Object> getCurrentWeather(double latitude, double longitude) throws UnsuccessfulQueryException {
      Map<String, Object> weatherDataMap = new HashMap<>();
      String data = getDataFromURI(addressPrefix + "locations/v1/cities/geoposition/search?apikey=" +
                                   apiKey + "&q=" + latitude + "%2C" + longitude);
      if (data != null && data.charAt(0) != '{') {
         throw new UnsuccessfulQueryException("getCurrentWeather: couldn't get data");
      }
      JSONObject jobj = new JSONObject(data);
      String locationKey = jobj.getString("Key");

      JSONArray jarr = new JSONArray(getDataFromURI(addressPrefix + "currentconditions/v1/" + locationKey + "?apikey=" + apiKey + "&details=true"));
      jobj = jarr.getJSONObject(0);

      JSONObject tobj = jobj.getJSONObject("Temperature").getJSONObject("Metric");
      JSONObject pobj = jobj.getJSONObject("Pressure").getJSONObject("Metric");
      JSONObject wdobj = jobj.getJSONObject("Wind").getJSONObject("Direction");
      JSONObject wsobj = jobj.getJSONObject("Wind").getJSONObject("Speed").getJSONObject("Metric");

      double temperature = tobj.getDouble("Value");
      double pressure = pobj.getDouble("Value");
      double humidity = jobj.getDouble("RelativeHumidity");
      double windDirection = wdobj.getDouble("Degrees");
      double windSpeed = wsobj.getDouble("Value");

      weatherDataMap.put("temperature", temperature);
      weatherDataMap.put("pressure", pressure);
      weatherDataMap.put("humidity", humidity);
      weatherDataMap.put("windDirection", windDirection);
      weatherDataMap.put("windSpeed", windSpeed);

      return weatherDataMap;
   }
}
