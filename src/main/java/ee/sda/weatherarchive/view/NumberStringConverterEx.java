package ee.sda.weatherarchive.view;

import javafx.util.converter.NumberStringConverter;
import java.text.NumberFormat;

public class NumberStringConverterEx extends NumberStringConverter {

   public NumberStringConverterEx(NumberFormat numberFormat) {
      super(numberFormat);
   }

   @Override
   public Number fromString(String value) {
      if (value.equals("-")) return 0.0;
      return super.fromString(value);
   }
}
