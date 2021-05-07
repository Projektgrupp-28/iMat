package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;


public class iMat extends Application {

    public static void main(String[] args) {
        launch(args);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                IMatDataHandler.getInstance().shutDown();
            }
        }));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/MainView.fxml"));
        stage.setTitle("iMat");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /*
    List<Product> productList;
    @FXML
    ListView<application.ProductCard> listView;

    public static void main(String[] args) {
        launch(args);

        IMatDataHandler dataHandler = IMatDataHandler.getInstance();
        Product product = dataHandler.getProduct(2);
        System.out.println(product.getName() + " kostar " + (int)product.getPrice() + "kr");

        System.out.println(dataHandler.getCustomer().getFirstName());
        System.out.println("Test William");
    }


     */

}
