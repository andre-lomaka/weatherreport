package ee.sda.weatherarchive.view;

import java.util.function.UnaryOperator;
import java.text.DecimalFormat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.beans.binding.Bindings;

import ee.sda.weatherarchive.controller.QueryController;

public class LocationViewPane extends Pane {

   private NotificationReceiver parent;

   private ButtonEx btnOk;
   private ButtonEx btnCancel;

   private final LocationFX locationFX = new LocationFX("", "", "", "", 0.0, 0.0);

   private final QueryController queryController;

   private boolean updateMode = false;

   public LocationViewPane(NotificationReceiver parent, QueryController queryController) {
      super();
      this.parent = parent;
      this.queryController = queryController;
      setProperties();
      prepareChildren();
   }

   private void setProperties() {
      setPrefWidth(UI.WINDOW_WIDTH - UI.MAIN_BUTTON_AREA_WIDTH);
      setPrefHeight(UI.WINDOW_HEIGHT);
   }

   private void prepareChildren() {
      Label lbLocation = new Label("Enter location data");
      setLabelProperties(lbLocation);
      GridPane gpData = createLocationDataGridPane();
      ButtonBar buttonBar = new ButtonBar();
      btnOk = createButton("OK");
      btnCancel = createButton("Cancel");
      buttonBar.getButtons().addAll(btnOk, btnCancel);
      buttonBar.setLayoutX(100.0);
      buttonBar.setLayoutY(430.0);
      getChildren().addAll(lbLocation, gpData, buttonBar);
   }

   private void setLabelProperties(Label label) {
      label.setLayoutX(50.0);
      label.setLayoutY(25.0);
      label.setTextFill(Color.web("#E7E5E5"));
      label.setFont(Font.font("Arial", 30.0));
   }

   private GridPane createLocationDataGridPane() {
      GridPane gp = new GridPane();
      setGridPaneProperties(gp);

      Label idLabel = new Label("ID:");
      Label cityLabel = new Label("City:");
      Label countryLabel = new Label("Country:");
      Label regionLabel = new Label("Region:");
      Label latitudeLabel = new Label("Latitude:");
      Label longitudeLabel = new Label("Longitude:");

      TextField idTextField = new TextField();
      TextField cityTextField = new TextField();
      TextField countryTextField = new TextField();
      TextField regionTextField = new TextField();
      TextField latitudeTextField = new TextField();
      TextField longitudeTextField = new TextField();

      locationFX.idProperty().bindBidirectional(idTextField.textProperty());
      locationFX.cityProperty().bindBidirectional(cityTextField.textProperty());
      locationFX.countryProperty().bindBidirectional(countryTextField.textProperty());
      locationFX.regionProperty().bindBidirectional(regionTextField.textProperty());
      DecimalFormat df = new DecimalFormat();
      df.setMaximumFractionDigits(10);
      StringConverter<Number> converter = new NumberStringConverterEx(df);
      Bindings.bindBidirectional(latitudeTextField.textProperty(), locationFX.latitudeProperty(), converter);
      Bindings.bindBidirectional(longitudeTextField.textProperty(), locationFX.longitudeProperty(), converter);
      UnaryOperator<TextFormatter.Change> filter = t -> {
         if (t.isAdded()) {
            if (!t.getControlNewText().matches("-?([0-9]+[.])?[0-9]*")) {
               t.setText("");
            }
         }
         return t;
      };
      latitudeTextField.setTextFormatter(new TextFormatter<>(filter));
      longitudeTextField.setTextFormatter(new TextFormatter<>(filter));

      setDataLabelProperties(idLabel);
      setDataLabelProperties(cityLabel);
      setDataLabelProperties(countryLabel);
      setDataLabelProperties(regionLabel);
      setDataLabelProperties(latitudeLabel);
      setDataLabelProperties(longitudeLabel);
      setTextFieldProperties(idTextField);
      setTextFieldProperties(cityTextField);
      setTextFieldProperties(countryTextField);
      setTextFieldProperties(regionTextField);
      setTextFieldProperties(latitudeTextField);
      setTextFieldProperties(longitudeTextField);

      GridPane.setHalignment(idLabel, HPos.RIGHT);
      GridPane.setHalignment(cityLabel, HPos.RIGHT);
      GridPane.setHalignment(countryLabel, HPos.RIGHT);
      GridPane.setHalignment(regionLabel, HPos.RIGHT);
      GridPane.setHalignment(latitudeLabel, HPos.RIGHT);
      GridPane.setHalignment(longitudeLabel, HPos.RIGHT);
      GridPane.setHalignment(idTextField, HPos.LEFT);
      GridPane.setHalignment(cityTextField, HPos.LEFT);
      GridPane.setHalignment(countryTextField, HPos.LEFT);
      GridPane.setHalignment(regionTextField, HPos.LEFT);
      GridPane.setHalignment(latitudeTextField, HPos.LEFT);
      GridPane.setHalignment(longitudeTextField, HPos.LEFT);

      gp.add(idLabel, 0, 0);
      gp.add(cityLabel, 0, 1);
      gp.add(countryLabel, 0, 2);
      gp.add(regionLabel, 0, 3);
      gp.add(latitudeLabel, 0, 4);
      gp.add(longitudeLabel, 0, 5);
      gp.add(idTextField, 1, 0);
      gp.add(cityTextField, 1, 1);
      gp.add(countryTextField, 1, 2);
      gp.add(regionTextField, 1, 3);
      gp.add(latitudeTextField, 1, 4);
      gp.add(longitudeTextField, 1, 5);
      for (int i = 0; i < 6; i++) gp.getRowConstraints().add(new RowConstraints(40.0));

      return gp;
   }

   private void setDataLabelProperties(Label label) {
      label.setTextFill(Color.WHITE);
      label.setFont(Font.font("Segoe UI Semibold", 22.0));
      label.setOpacity(0.6);
   }

   private void setTextFieldProperties(TextField textfield) {
      textfield.setFont(Font.font("Segoe UI Semibold", 22.0));
      textfield.setPadding(new Insets(2.0, 2.0, 2.0, 2.0));
      textfield.clear();
   }

   private void setGridPaneProperties(GridPane gridPane) {
      gridPane.setLayoutX(50.0);
      gridPane.setLayoutY(90.0);
      gridPane.setVgap(10.0);
      gridPane.setHgap(10.0);
   }

   private ButtonEx createButton(String text) {
      ButtonEx btn = new ButtonEx(text);
      btn.setPaddingEx(new Insets(5.0, 22.0, 5.0, 22.0))
         .setBackgroundEx("#1D1D1D")
         .setTextFillEx("#D8D8D8")
         .setFontEx(Font.font("Segoe UI", 15.0))
         .setBorderEx("#E2E2E2", 2.0);
      btn.setOnMouseEntered(me -> btn.setBackground(new Background(new BackgroundFill(Color.web("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY))) );
      btn.setOnMouseExited(me -> btn.restoreBackground() );
      btn.setOnMousePressed(me -> { btn.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))); btn.setTextFillEx("#1D1D1D"); } );
      btn.setOnMouseReleased(me -> { if (btn.isHover()) btn.setBackground(new Background(new BackgroundFill(Color.web("#3A3A3A"), CornerRadii.EMPTY, Insets.EMPTY)));
                                     else btn.restoreBackground();
                                     btn.setTextFillEx("#D8D8D8"); } );
      btn.setOnAction(this::handleCommand);
      return btn;
   }

   private void handleCommand(ActionEvent actionEvent) {
      Object actionEventSource = actionEvent.getSource();
      if (actionEventSource == btnOk) {
         if (!(locationFX.getLatitude() > -90.0 && locationFX.getLatitude() < 90.0 && locationFX.getLongitude() > -180.0 && locationFX.getLongitude() < 180.0)) {
            UI.showAlert(AlertType.WARNING, "Input error", "Coordinates out of range", "Latitude must be between -90 and 90.\nLongitude must be between -180 and 180.");
         } else if (locationFX.getId().isEmpty()) {
            UI.showAlert(AlertType.WARNING, "Input error", "Invalid ID", "ID cannot be empty string.");
         } else {
            if (updateMode) queryController.onUpdate(locationFX);
            else queryController.onAdd(locationFX);
            parent.receiveNotification(this, null);
            updateMode = false;
         }
      } else {
         parent.receiveNotification(this, null);
         updateMode = false;
      }
   }

   public void updateLocation(LocationFX location) {
      locationFX.setId(location.getId());
      locationFX.setCity(location.getCity());
      locationFX.setCountry(location.getCountry());
      locationFX.setRegion(location.getRegion());
      locationFX.setLatitude(location.getLatitude());
      locationFX.setLongitude(location.getLongitude());
      updateMode = true;
   }
}
