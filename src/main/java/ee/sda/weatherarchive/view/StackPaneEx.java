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

   private final QueryController queryController;

   public StackPaneEx(QueryController queryController) {
      super();
      this.queryController = queryController;
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
      getChildren().addAll(pnLocations, pnLocationView);
      pnLocations.toFront();
      pnLocations.setVisible(true);
      pnLocationView.setVisible(false);
   }

   @Override
   public void receiveNotification(Node child, Object msg) {
      if (child instanceof LocationsPane) {
         if (msg != null) pnLocationView.updateLocation((LocationFX)msg);
         pnLocationView.toFront();
         pnLocationView.setVisible(true);
         pnLocations.setVisible(false);
      } else if (child instanceof LocationViewPane) {
         pnLocations.toFront();
         pnLocations.setVisible(true);
         pnLocationView.setVisible(false);
      }
   }
}
