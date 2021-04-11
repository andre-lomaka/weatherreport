package ee.sda.weatherarchive.util;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.Properties;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public abstract class DataDownloader {

   protected final String addressPrefix;
   protected final Properties properties;
   protected String apiKey;
   private final HttpClient httpClient;

   protected DataDownloader(String addressPrefix) {
      this.addressPrefix = addressPrefix;
      this.httpClient = HttpClient.newHttpClient();
      this.properties = new Properties();
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
      if (inputStream != null)
      {
         try {
            this.properties.load(inputStream);
            inputStream.close();
         } catch (IOException ioe) {
            ioe.printStackTrace();
         }
      }
   }

   abstract Map<String, Object> getCurrentWeather(double latitude, double longitude) throws UnsuccessfulQueryException;

   protected String getDataFromURI(String uri) throws UnsuccessfulQueryException {
      StringBuilder sb;
      HttpRequest request;
      BufferedReader br;
      GZIPInputStream gis;
      InputStream is;
      HttpResponse<InputStream> response;
      try {
         request = HttpRequest.newBuilder(new URI(uri))
                            .version(HttpClient.Version.HTTP_2)
                            .headers("Accept-Encoding", "gzip, deflate")
                            .build();
      } catch (URISyntaxException use) {
         throw new UnsuccessfulQueryException("getDataFromURI: URISyntaxException");
      }
      try {
         response = httpClient.send(request, BodyHandlers.ofInputStream());
         is = response.body();
         gis = new GZIPInputStream(is);
         br = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
         sb = new StringBuilder();
         String line;
         while ((line = br.readLine()) != null) {
            sb.append(line);
         }
         br.close();
         gis.close();
         is.close();
      } catch (IOException ioe) {
         throw new UnsuccessfulQueryException("getDataFromURI: IOException");
      } catch (InterruptedException ie) {
         throw new UnsuccessfulQueryException("getDataFromURI: InterruptedException");
      }
      int statusCode = response.statusCode();
      if (statusCode != 200) throw new UnsuccessfulQueryException("getDataFromURI: wrong status code " + statusCode + " from http query");
      return sb.toString();
   }
}
