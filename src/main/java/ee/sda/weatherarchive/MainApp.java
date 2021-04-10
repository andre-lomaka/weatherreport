package ee.sda.weatherarchive;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import ee.sda.weatherarchive.view.UI;

public class MainApp extends Application {

   @Override
   public void start(Stage primaryStage) throws Exception {
      UI ui = new UI();
      primaryStage.setTitle("WeatherArchive");
      primaryStage.setScene(new Scene(ui.asParent()));
      primaryStage.show();
      ui.postSetup();
   }

   public static void main(String[] args) {
      launch(args);
   }
}
