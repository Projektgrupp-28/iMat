package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class is responsible for the executable start method that runs the application.
 * @author Philip Winsnes
 */

public class iMat extends Application {

    public static void main(String[] args) {
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                Model.getInstance().shutDown();
            }
        }));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/application/MainView.fxml"));
        initStage(stage, root);
    }

    private void initStage(Stage stage, Parent root) {
        stage.setTitle("iMat");
        stage.setScene(new Scene(root));
        stage.setMinWidth(860);
        stage.setMinHeight(700);
        stage.show();
    }

}
