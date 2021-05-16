package ee.sda.weatherarchive.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;

class ButtonEx extends Button {

   private boolean selected;
   private String mainBackground;
   private String textFill;
   private String hoverColor;

   public ButtonEx(String text) {
      super(text);
   }

   public boolean isSelected() {
      return selected;
   }

   public void setSelected(boolean value) {
      selected = value;
   }

   public ButtonEx setAlignmentEx(Pos value) {
      this.setAlignment(value);
      return this;
   }

   public ButtonEx setMnemonicParsingEx(boolean value) {
      this.setMnemonicParsing(value);
      return this;
   }

   public ButtonEx setPrefWidthEx(double value) {
      this.setPrefWidth(value);
      return this;
   }

   public ButtonEx setPrefHeightEx(double value) {
      this.setPrefHeight(value);
      return this;
   }

   public ButtonEx setTextFillEx(String value) {
      this.setTextFill(Color.web(value));
      textFill = value;
      return this;
   }

   public ButtonEx setFontEx(Font value) {
      this.setFont(value);
      return this;
   }

   public ButtonEx setPaddingEx(Insets value) {
      this.setPadding(value);
      return this;
   }

   public ButtonEx setBackgroundEx(String value) {
      this.setBackground(new Background(new BackgroundFill(Color.web(value), CornerRadii.EMPTY, Insets.EMPTY)));
      mainBackground = value;
      return this;
   }

   public ButtonEx setBorderEx(String color, double width) {
      this.setBorder(new Border(new BorderStroke(Color.web(color), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(width))));
      return this;
   }

   public void restoreBackground() {
      if (mainBackground != null)
         this.setBackground(new Background(new BackgroundFill(Color.web(mainBackground), CornerRadii.EMPTY, Insets.EMPTY)));
   }

   public ButtonEx setHoverColor(String color) {
      setOnMouseEntered(me -> { if (!selected) setBackground(new Background(new BackgroundFill(Color.web(color), CornerRadii.EMPTY, Insets.EMPTY))); } );
      setOnMouseExited(me -> { if (!selected) restoreBackground(); } );
      hoverColor = color;
      return this;
   }

   public ButtonEx setPressedColor(String color) {
      setOnMousePressed(me -> { setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))); setTextFill(Color.web(color)); } );
      setOnMouseReleased(me -> { if (isHover()) setBackground(new Background(new BackgroundFill(Color.web(hoverColor), CornerRadii.EMPTY, Insets.EMPTY)));
                                     else restoreBackground();
                                     setTextFillEx(textFill); } );
      return this;
   }
}
