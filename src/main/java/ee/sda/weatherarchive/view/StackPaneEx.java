package ee.sda.weatherarchive.view;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.geometry.Insets;

import ee.sda.weatherarchive.controller.QueryController;

public class StackPaneEx extends StackPane implements NotificationReceiver {

   private LocationsPane pnLocations;
   private LocationViewPane pnLocationView;
   private WeatherPane pnWeather;
   private NotificationReceiver parent;

   private final QueryController queryController;

   public StackPaneEx(NotificationReceiver parent, QueryController queryController) {
      super();
      this.queryController = queryController;
      this.parent = parent;
      setProperties();
      prepareChildren();
   }

   public LocationsPane getPnLocations() {
      return pnLocations;
   }

   private void setProperties() {
      setLayoutX(UI.MAIN_BUTTON_AREA_WIDTH);
      setPrefWidth(UI.WINDOW_WIDTH - UI.MAIN_BUTTON_AREA_WIDTH);
      setPrefHeight(UI.WINDOW_HEIGHT);
      setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
   }

   private void prepareChildren() {
      pnLocations = new LocationsPane(this, queryController);
      pnLocationView = new LocationViewPane(this, queryController);
      pnWeather = new WeatherPane();
      getChildren().addAll(pnLocations, pnLocationView, pnWeather);
      setOnTop(0);
   }

   public void setOnTop(int paneNumber) {
      switch (paneNumber)
      {
         case 0:
            pnLocations.toFront();
            pnLocations.setVisible(true);
            pnLocationView.setVisible(false);
            pnWeather.setVisible(false);
            break;
         case 1:
            pnLocationView.toFront();
            pnLocationView.setVisible(true);
            pnLocations.setVisible(false);
            pnWeather.setVisible(false);
            break;
         case 2:
            pnWeather.toFront();
            pnWeather.setVisible(true);
            pnLocationView.setVisible(false);
            pnLocations.setVisible(false);
            break;
      }
   }

   @Override
   public void receiveNotification(Node child, Object msg) {
      if (child instanceof LocationsPane) {
         if (msg != null) {
            if (msg instanceof LocationFX) {
               pnLocationView.updateLocation((LocationFX)msg);
               pnLocationView.setTitle("Enter location data (edit)");
               setOnTop(1);
            } else {
               setOnTop(2);
               pnWeather.setAreaText(msg.toString());
               parent.receiveNotification(this, null);
            }
         } else {
            pnLocationView.setTitle("Enter location data (add)");
            setOnTop(1);
         }
      } else if (child instanceof LocationViewPane) {
         setOnTop(0);
      }
   }
}
