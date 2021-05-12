package ee.sda.weatherarchive.view;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;

public class VBoxEx extends VBox {

   private ButtonEx btnWeather;
   private ButtonEx btnLocations;

   private final NotificationReceiver parent;

   public VBoxEx(NotificationReceiver parent) {
      super();
      this.parent = parent;
      setProperties();
      prepareChildren();
   }

   private void setProperties() {
      setPadding(new Insets(20.0, 0.0, 0.0, 0.0));
      setPrefWidth(UI.MAIN_BUTTON_AREA_WIDTH);
      setPrefHeight(UI.WINDOW_HEIGHT);
      setAlignment(Pos.TOP_CENTER);
      setBackground(new Background(new BackgroundFill(Color.web("#05071F"), CornerRadii.EMPTY, Insets.EMPTY)));
   }

   private void prepareChildren() {
      btnWeather = createButton("Weather data");
      btnLocations = createButton("Locations");
      getChildren().addAll(btnWeather, btnLocations);
      activateButton(btnLocations);
   }

   private void activateButton(ButtonEx btn) {
      btn.setBackgroundEx("#1620A1");
      btn.setSelected(true);
   }

   private ButtonEx createButton(String text) {
      ButtonEx btn = new ButtonEx(text);
      btn.setAlignmentEx(Pos.BASELINE_LEFT)
         .setMnemonicParsingEx(false)
         .setPrefWidthEx(UI.MAIN_BUTTON_AREA_WIDTH)
         .setPrefHeightEx(50.0)
         .setTextFillEx("#E7E5E5")
         .setFontEx(Font.font("Arial", 22.0))
         .setPaddingEx(new Insets(0.0, 0.0, 0.0, 20.0))
         .setBackgroundEx("#05071F");
      btn.setOnMouseEntered(me -> { if (!btn.isSelected()) btn.setBackground(new Background(new BackgroundFill(Color.web("#10165F"), CornerRadii.EMPTY, Insets.EMPTY))); } );
      btn.setOnMouseExited(me -> { if (!btn.isSelected()) btn.restoreBackground(); } );
      btn.setOnAction(this::handleCommand);
      return btn;
   }

   private void handleCommand(ActionEvent actionEvent) {
      Object actionEventSource = actionEvent.getSource();
      if (actionEventSource == btnWeather) {
         activateButton(btnWeather);
         btnLocations.setSelected(false);
         btnLocations.setBackgroundEx("#05071F");
         parent.receiveNotification(null, "weather");
      } else {
         activateButton(btnLocations);
         btnWeather.setSelected(false);
         btnWeather.setBackgroundEx("#05071F");
         parent.receiveNotification(null, "locations");
      }
   }
}
