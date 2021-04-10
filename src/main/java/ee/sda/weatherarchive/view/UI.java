package ee.sda.weatherarchive.view;

import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.stage.WindowEvent;

import ee.sda.weatherarchive.controller.QueryController;

public class UI {

   public final static double WINDOW_HEIGHT = 650.0;
   public final static double MAIN_BUTTON_AREA_WIDTH = 200.0;
   public final static double WINDOW_WIDTH = 850.0;

   private AnchorPane anchorPane;
   private VBox vBox;
   private StackPaneEx stackPane;

   private final QueryController queryController;

   public UI() {
      queryController = new QueryController();
   }

   public Parent asParent() {
      if (anchorPane != null) return anchorPane;
      vBox = new VBoxEx();
      stackPane = new StackPaneEx(queryController);
      anchorPane = new AnchorPane(vBox, stackPane);
      setAnchorPaneProperties(anchorPane);
      AnchorPane.setBottomAnchor(vBox, 0.0);
      AnchorPane.setTopAnchor(vBox, 0.0);
      return anchorPane;
   }

   private void setAnchorPaneProperties(AnchorPane ap) {
      ap.setMinWidth(Region.USE_PREF_SIZE);
      ap.setMaxWidth(Region.USE_PREF_SIZE);
      ap.setMinHeight(Region.USE_PREF_SIZE);
      ap.setMaxHeight(Region.USE_PREF_SIZE);
      ap.setPrefWidth(WINDOW_WIDTH);
      ap.setPrefHeight(WINDOW_HEIGHT);
      ap.setBackground(new Background(new BackgroundFill(Color.web("#02030A"), CornerRadii.EMPTY, Insets.EMPTY)));
   }

   public void postSetup() {
      for (Node n : stackPane.getPnLocations().getLocationsTable().lookupAll(".column-header > .label")) {
         if (n instanceof Label) {
            Label label = (Label) n;
            label.setFont(Font.font("System", FontWeight.BOLD, 15.0));
         }
      }
      anchorPane.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
   }

   private void closeWindowEvent(WindowEvent event) {
      QueryController.closeConnection();
   }

   public static void showAlert(AlertType alertType, String title, String headerText, String contentText) {
      Alert alert = new Alert(alertType);
      alert.setTitle(title);
      alert.setHeaderText(headerText);
      alert.setContentText(contentText);
      alert.showAndWait();
   }
}
