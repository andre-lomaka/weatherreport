package ee.sda.weatherarchive.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import ee.sda.weatherarchive.controller.QueryController;

@SuppressWarnings("unchecked")
public class LocationsPane extends Pane {

   private Label lbLocations;
   private Node toggleNode;
   private ButtonEx btnAdd;
   private ButtonEx btnEdit;
   private ButtonEx btnDelete;
   private ButtonEx btnRetrieveAll;
   private ButtonEx btnDownload;
   private ButtonBar buttonBar;

   private TableView<LocationFX> locationsTable;
   private final NotificationReceiver parent;

   private int currentTableRowId = -1;

   private final ObservableList<LocationFX> locationsFX = FXCollections.observableArrayList();

   private final QueryController queryController;

   public LocationsPane(NotificationReceiver parent, QueryController queryController) {
      super();
      this.parent = parent;
      this.queryController = queryController;
      queryController.setLocations(locationsFX);
      setProperties();
      prepareChildren();
   }

   public TableView<?> getLocationsTable() {
      return locationsTable;
   }

   private void setProperties() {
      setPrefWidth(UI.WINDOW_WIDTH - UI.MAIN_BUTTON_AREA_WIDTH);
      setPrefHeight(UI.WINDOW_HEIGHT);
   }

   private void prepareChildren() {
      lbLocations = new Label("Modify");
      setLabelProperties(lbLocations);
      toggleNode = createToggleNode();
      locationsTable = createLocationsTable();
      buttonBar = new ButtonBar();
      btnRetrieveAll = createButton("Retrieve all");
      btnAdd = createButton("Add...");
      btnEdit = createButton("Edit...");
      btnDelete = createButton("Delete");
      buttonBar.getButtons().addAll(btnRetrieveAll, btnAdd, btnEdit, btnDelete);
      buttonBar.setLayoutX(50.0);
      buttonBar.setLayoutY(500.0);
      btnDownload = createButton("Download");
      btnDownload.setLayoutX(50.0);
      btnDownload.setLayoutY(500.0);
      btnDownload.setVisible(false);
      getChildren().addAll(lbLocations, toggleNode, locationsTable, buttonBar, btnDownload);
   }

   private void setLabelProperties(Label label) {
      label.setLayoutX(50.0);
      label.setLayoutY(25.0);
      label.setTextFill(Color.web("#E7E5E5"));
      label.setFont(Font.font("Arial", 30.0));
   }

   private Node createToggleNode() {
      ToggleGroup group = new ToggleGroup();

      RadioButton button1 = new RadioButton("Modify");
      button1.setToggleGroup(group);
      button1.setSelected(true);
      button1.setTextFill(Color.web("#E7E5E5"));
      button1.setFont(Font.font("Arial", 15.0));

      RadioButton button2 = new RadioButton("Download");
      button2.setToggleGroup(group);
      button2.setTextFill(Color.web("#E7E5E5"));
      button2.setFont(Font.font("Arial", 15.0));

      RadioButton button3 = new RadioButton("Statistics");
      button3.setToggleGroup(group);
      button3.setTextFill(Color.web("#E7E5E5"));
      button3.setFont(Font.font("Arial", 15.0));

      group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
         if (group.getSelectedToggle() != null) {
            RadioButton button = (RadioButton)group.getSelectedToggle();
            if (button == button1) {
               lbLocations.setText("Modify");
               setVisibilityStatus(true, false);
            } else if (button == button2) {
               lbLocations.setText("Download");
               setVisibilityStatus(false, true);
            } else {
               lbLocations.setText("Statistics");
               setVisibilityStatus(false, false);
            }
         }
      });

      HBox hBox = new HBox();
      hBox.getChildren().addAll(button1, button2, button3);
      hBox.setSpacing(12.0);
      hBox.setLayoutX(50.0);
      hBox.setLayoutY(80.0);
      return hBox;
   }

   private void setVisibilityStatus(boolean... status) {
      buttonBar.setVisible(status[0]);
      btnDownload.setVisible(status[1]);
   }

   private TableView<LocationFX> createLocationsTable() {
      TableView<LocationFX> tableView = new TableView<>();

      TableColumn<LocationFX, String> idCol = new TableColumn<>("ID");
      TableColumn<LocationFX, String> cityCol = new TableColumn<>("City");
      TableColumn<LocationFX, String> countryCol = new TableColumn<>("Country");
      TableColumn<LocationFX, String> regionCol = new TableColumn<>("Region");
      TableColumn<LocationFX, Double> latitudeCol = new TableColumn<>("Latitude");
      TableColumn<LocationFX, Double> longitudeCol = new TableColumn<>("Longitude");

      idCol.setCellFactory(l -> new CustomTableCellString());
      cityCol.setCellFactory(l -> new CustomTableCellString());
      countryCol.setCellFactory(l -> new CustomTableCellString());
      regionCol.setCellFactory(l -> new CustomTableCellString());
      latitudeCol.setCellFactory(l -> new CustomTableCellDouble());
      longitudeCol.setCellFactory(l -> new CustomTableCellDouble());

      tableView.setRowFactory(l -> new CustomTableRow());

      idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
      cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
      countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
      regionCol.setCellValueFactory(new PropertyValueFactory<>("region"));
      latitudeCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
      longitudeCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));

      idCol.setSortable(false);
      cityCol.setSortable(false);
      countryCol.setSortable(false);
      regionCol.setSortable(false);
      latitudeCol.setSortable(false);
      longitudeCol.setSortable(false);

      tableView.setItems(locationsFX);
      tableView.getColumns().addAll(idCol, cityCol, countryCol, regionCol, latitudeCol, longitudeCol);
      setTableViewProperties(tableView);
      tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { currentTableRowId = tableView.getSelectionModel().getSelectedIndex();
                                                                                                             tableView.refresh(); } );

      return tableView;
   }

   private void setTableViewProperties(TableView<?> tableView) {
      tableView.setLayoutX(50.0);
      tableView.setLayoutY(120.0);
      tableView.setPrefWidth(550.0);
      tableView.setPrefHeight(350.0);
      tableView.setBackground(new Background(new BackgroundFill(Color.web("#1D1D1D"), CornerRadii.EMPTY, Insets.EMPTY)));
      tableView.setFixedCellSize(30.0);
      tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
      if (actionEventSource == btnAdd) {
         parent.receiveNotification(this, null);
      } else if (actionEventSource == btnRetrieveAll) {
         queryController.onRetrieveAll();
      } else if (actionEventSource == btnEdit) {
         LocationFX lfx = locationsTable.getSelectionModel().getSelectedItem();
         if (lfx != null) {
            parent.receiveNotification(this, lfx);
         } else
         {
            UI.showAlert(AlertType.WARNING, "No selection", "No location selected", "Please select a location in the table.");
         }
      } else if (actionEventSource == btnDelete) {
         LocationFX lfx = locationsTable.getSelectionModel().getSelectedItem();
         if (lfx != null) {
            queryController.onDelete(lfx);
         } else {
            UI.showAlert(AlertType.WARNING, "No selection", "No location selected", "Please select a location in the table.");
         }
      } else if (actionEventSource == btnDownload) {
         LocationFX lfx = locationsTable.getSelectionModel().getSelectedItem();
         if (lfx != null) {
            queryController.onDownload(lfx);
            UI.showAlert(AlertType.INFORMATION, "Information", null, "Download successful!");
         } else {
            UI.showAlert(AlertType.WARNING, "No selection", "No location selected", "Please select a location in the table.");
         }
      }
   }

   private class CustomTableCellString extends TableCell<LocationFX, String> {
      @Override
      protected void updateItem(String item, boolean empty) {
         super.updateItem(item, empty);
         setBorder(Border.EMPTY);
         setAlignment(Pos.CENTER_LEFT);
         if (empty || item == null) {
            setBackground(new Background(new BackgroundFill(Color.web("#1D1D1D"), CornerRadii.EMPTY, Insets.EMPTY)));
            setText(null);
         } else {
            setBackground(getIndex() == currentTableRowId ? new Background(new BackgroundFill(Color.web("#039ED3"), CornerRadii.EMPTY, Insets.EMPTY)) :
                                         new Background(new BackgroundFill(Color.web("#1D1D1D"), CornerRadii.EMPTY, Insets.EMPTY)));
            setText(item);
            setFont(Font.font("Arial", 15.0));
            setTextFill(Color.web("#E7E5E5"));
         }
      }
   }

   private class CustomTableCellDouble extends TableCell<LocationFX, Double> {
      @Override
      protected void updateItem(Double item, boolean empty) {
         super.updateItem(item, empty);
         setBorder(Border.EMPTY);
         setAlignment(Pos.CENTER_LEFT);
         if (empty || item == null) {
            setBackground(new Background(new BackgroundFill(Color.web("#1D1D1D"), CornerRadii.EMPTY, Insets.EMPTY)));
            setText(null);
         } else {
            setBackground(getIndex() == currentTableRowId ? new Background(new BackgroundFill(Color.web("#039ED3"), CornerRadii.EMPTY, Insets.EMPTY)) :
                                         new Background(new BackgroundFill(Color.web("#1D1D1D"), CornerRadii.EMPTY, Insets.EMPTY)));
            setText(item.toString());
            setFont(Font.font("Arial", 15.0));
            setTextFill(Color.web("#E7E5E5"));
         }
      }
   }

   private static class CustomTableRow extends TableRow<LocationFX> {
      @Override
      protected void updateItem(LocationFX item, boolean empty) {
         super.updateItem(item, empty);
         setBackground(new Background(new BackgroundFill(Color.web("#1D1D1D"), CornerRadii.EMPTY, Insets.EMPTY)));
      }
   }
}
