package ee.sda.weatherarchive.view;

import javafx.scene.Node;

public interface NotificationReceiver {
   void receiveNotification(Node child, Object msg);
}
