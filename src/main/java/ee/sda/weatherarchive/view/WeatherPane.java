package ee.sda.weatherarchive.view;

import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WeatherPane extends Pane {

   public WeatherPane() {
      super();
      setProperties();
      prepareChildren();
   }

   private void setProperties() {
      setPrefWidth(UI.WINDOW_WIDTH - UI.MAIN_BUTTON_AREA_WIDTH);
      setPrefHeight(UI.WINDOW_HEIGHT);
   }

   private void prepareChildren() {
      Label lbWeatherStat = new Label("Weather statistics");
      setLabelProperties(lbWeatherStat);
      getChildren().addAll(lbWeatherStat);
   }

   private void setLabelProperties(Label label) {
      label.setLayoutX(50.0);
      label.setLayoutY(25.0);
      label.setTextFill(Color.web("#E7E5E5"));
      label.setFont(Font.font("Arial", 30.0));
   }
}
