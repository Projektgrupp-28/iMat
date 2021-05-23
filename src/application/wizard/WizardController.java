package application.wizard;

import application.FxmlLoader;
import application.iMatController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class WizardController implements Initializable {

    private FxmlLoader fxmlLoader = new FxmlLoader();

    @FXML private BorderPane contentPane;

    private application.iMatController iMatController = application.iMatController.getInstance();
    private Pane wizardStart = fxmlLoader.getPage("wizard/WizardStart");
    private Pane wizard1 = fxmlLoader.getPage("wizard/Wizard1");
    private Pane wizard2 = fxmlLoader.getPage("wizard/Wizard2");
    private Pane wizard3 = fxmlLoader.getPage("wizard/Wizard3");
    private Pane wizard4 = fxmlLoader.getPage("wizard/Wizard4");
    private Pane wizard5 = fxmlLoader.getPage("wizard/Wizard5");

    List<Pane> wizardPanes = new ArrayList<>();

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
        contentPane.setCenter(wizardStart);
        contentPane.setOnMouseClicked(Event::consume);
    }

    private int getCurrentWindowIndex() {
        Node thisPane = contentPane.getCenter();
        return wizardPanes.indexOf(thisPane);
    }

    @FXML
    private void next() {
        int currentWindowIndex = getCurrentWindowIndex();
        if (currentWindowIndex < wizardPanes.size() - 1) {
            Pane nextPane = wizardPanes.get(getCurrentWindowIndex() + 1);
            contentPane.setCenter(nextPane);
        } else {
            // Next from last window closes the panel.
            close();
        }
    }

    @FXML
    private void previous() {
        int currentWindowIndex = getCurrentWindowIndex();
        if (currentWindowIndex > 0) {
            Pane previousPane = wizardPanes.get(currentWindowIndex - 1);
            contentPane.setCenter(previousPane);
        }
    }

    @FXML
    private void close() {
        iMatController.closeOverlayWindow();
    }

}
