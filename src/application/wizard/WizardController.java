package application.wizard;

import application.FxmlLoader;
import application.iMatController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class WizardController implements Initializable {

    private FxmlLoader fxmlLoader = new FxmlLoader();

    @FXML private BorderPane contentPane;
    private Rectangle rectangleReserved = new Rectangle();
    @FXML private Rectangle rectangle1;
    @FXML private Rectangle rectangle2;
    @FXML private Rectangle rectangle3;
    @FXML private Rectangle rectangle4;
    @FXML private Rectangle rectangle5;

    @FXML private Button backButton;

    private application.iMatController iMatController = application.iMatController.getInstance();
    private Pane wizardStart = fxmlLoader.getPage("wizard/WizardStart");
    private Pane wizard1 = fxmlLoader.getPage("wizard/Wizard1");
    private Pane wizard2 = fxmlLoader.getPage("wizard/Wizard2");
    private Pane wizard3 = fxmlLoader.getPage("wizard/Wizard3");
    private Pane wizard4 = fxmlLoader.getPage("wizard/Wizard4");
    private Pane wizard5 = fxmlLoader.getPage("wizard/Wizard5");

    List<Pane> wizardPanes = new ArrayList<>();

    List<Rectangle> wizardRectangles = new ArrayList<>();

    private void initWizardRectangles() {
        wizardRectangles.add(rectangleReserved);
        wizardRectangles.add(rectangle1);
        wizardRectangles.add(rectangle2);
        wizardRectangles.add(rectangle3);
        wizardRectangles.add(rectangle4);
        wizardRectangles.add(rectangle5);
    }

    private void initWizardPanes() {
        wizardPanes.add(wizardStart);
        wizardPanes.add(wizard1);
        wizardPanes.add(wizard2);
        wizardPanes.add(wizard3);
        wizardPanes.add(wizard4);
        wizardPanes.add(wizard5);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initWizardPanes();
        initWizardRectangles();
        contentPane.setCenter(wizardStart);
        contentPane.setOnMouseClicked(Event::consume);
        backButton.setVisible(false);
    }

    private int getCurrentWindowIndex() {
        Node thisPane = contentPane.getCenter();
        return wizardPanes.indexOf(thisPane);
    }

    @FXML
    private void next() {
        int currentWindowIndex = getCurrentWindowIndex();
        Rectangle nextRectangle;
        Rectangle currentRectangle;
        backButton.setVisible(true);
        if (currentWindowIndex < wizardPanes.size() - 1) {
            Pane nextPane = wizardPanes.get(getCurrentWindowIndex() + 1);
            currentRectangle = wizardRectangles.get(getCurrentWindowIndex());
            nextRectangle = wizardRectangles.get(getCurrentWindowIndex() + 1);
            currentRectangle.setVisible(false);
            nextRectangle.setVisible(true);
            contentPane.setCenter(nextPane);
        } else {
            // Next from last window closes the panel.
            close();
        }
    }

    @FXML
    private void previous() {
        int currentWindowIndex = getCurrentWindowIndex();
        Rectangle previousRectangle;
        Rectangle currentRectangle;
        if (currentWindowIndex > 0) {
            Pane previousPane = wizardPanes.get(currentWindowIndex - 1);
            currentRectangle = wizardRectangles.get(getCurrentWindowIndex());
            previousRectangle = wizardRectangles.get(getCurrentWindowIndex() - 1);
            currentRectangle.setVisible(false);
            previousRectangle.setVisible(true);
            contentPane.setCenter(previousPane);
        } else if (currentWindowIndex == 0) {
            backButton.setVisible(false);
        }else{
            backButton.setVisible(true);
        }
    }

    @FXML
    private void close() {
        iMatController.closeOverlayWindow();
    }

}
