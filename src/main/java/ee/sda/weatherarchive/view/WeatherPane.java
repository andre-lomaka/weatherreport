package ee.sda.weatherarchive.view;

import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.TextArea;

public class WeatherPane extends Pane {

   private TextArea statisticsText;

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
      statisticsText = createStatisticsText();
      getChildren().addAll(lbWeatherStat, statisticsText);
   }

   private TextArea createStatisticsText() {
      TextArea ta = new TextArea();
      setTextAreaProperties(ta);
      return ta;
   }

   private void setTextAreaProperties(TextArea ta) {
      ta.setLayoutX(50.0);
      ta.setLayoutY(120.0);
      ta.setPrefWidth(550.0);
      ta.setPrefHeight(350.0);
      ta.setFont(Font.font("System Regular", 20.0));
      ta.setEditable(false);
   }

   private void setLabelProperties(Label label) {
      label.setLayoutX(50.0);
      label.setLayoutY(25.0);
      label.setTextFill(Color.web("#E7E5E5"));
      label.setFont(Font.font("Arial", 30.0));
   }

   public void setAreaText(String text) {
      statisticsText.setText(text);
   }
}
