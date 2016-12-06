package castagnos.agent.client.common;

import javafx.scene.control.Alert;
/**
 * Created by jerome on 06/12/2016.
 */

public class Popup {

    public static void popUpError(String titre, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}