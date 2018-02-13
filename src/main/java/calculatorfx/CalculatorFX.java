package calculatorfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author <a href="mailto:magomar@gmail.com">Mario Gómez</a>
 * Created on 30/03/2017.
 */
public class CalculatorFX extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

}
