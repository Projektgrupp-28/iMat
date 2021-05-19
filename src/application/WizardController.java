package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WizardController extends iMatController {


    private Pane wizardStart = fxmlLoader.getPage("WizardStart");
    private Pane wizard1 = fxmlLoader.getPage("Wizard1");
    private Pane wizard2 = fxmlLoader.getPage("Wizard2");
    private Pane wizard3 = fxmlLoader.getPage("Wizard3");
    private Pane wizard4 = fxmlLoader.getPage("Wizard4");
    private Pane wizard5 = fxmlLoader.getPage("Wizard5");

    List<Pane> wizardPanes = new ArrayList<>();

    private void initWizardPanes() {
        wizardPanes.add(wizardStart);
        wizardPanes.add(wizard1);
        wizardPanes.add(wizard2);
        wizardPanes.add(wizard3);
        wizardPanes.add(wizard4);
        wizardPanes.add(wizard5);
    }

}
