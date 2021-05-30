package application.shoppingcart;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryOption2Controller implements Initializable {

    // Delivery Day Selection
    @FXML private GridPane daySelector;
    @FXML private ToggleButton tglButton00;
    @FXML private ToggleButton tglButton01;
    @FXML private ToggleButton tglButton02;
    @FXML private ToggleButton tglButton10;
    @FXML private ToggleButton tglButton11;
    @FXML private ToggleButton tglButton12;
    @FXML private ToggleButton tglButton20;
    //@FXML private ToggleButton tglButton21;
    //@FXML private ToggleButton tglButton22;

    // Time For Deliverance Selection
    @FXML private VBox timeGroup;
    @FXML private GridPane timeSelector;
    @FXML private ToggleButton tgl2Button00;
    @FXML private ToggleButton tgl2Button01;
    @FXML private ToggleButton tgl2Button02;
    @FXML private ToggleButton tgl2Button10;
    @FXML private ToggleButton tgl2Button11;
    //@FXML private ToggleButton tgl2Button12;
    //@FXML private ToggleButton tgl2Button20;
    //@FXML private ToggleButton tgl2Button21;
    //@FXML private ToggleButton tgl2Button22;


    private ShoppingCartController shoppingCartController = ShoppingCartController.getInstance();
    private List<ToggleButton> days = new ArrayList<>();
    private List<ToggleButton> times = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDays();
        initTimes();
    }

    private void initDays() {
        // Row 0
        days.add(tglButton00);
        days.add(tglButton01);
        days.add(tglButton02);
        // Row 1
        days.add(tglButton10);
        days.add(tglButton11);
        days.add(tglButton12);
        // Row 2
        days.add(tglButton20);
        //days.add(tglButton21);    Unused...
        //days.add(tglButton22);    Unused...
    }

    private void initTimes() {
        // Row 0
        times.add(tgl2Button00);
        times.add(tgl2Button01);
        times.add(tgl2Button02);
        // Row 1
        times.add(tgl2Button10);
        times.add(tgl2Button11);
        //times.add(tgl2Button12);  Unused...
        // Row 2
        //times.add(tgl2Button20);  Unused...
        //days.add(tgl2Button21);   Unused...
        //days.add(tgl2Button22);   Unused...
    }

    @FXML
    private void onSelection() {
        checkFirstPrecedence();
        checkSecondPrecedence();
    }

    private void checkFirstPrecedence() {
        int unSelections = 0;
        for (ToggleButton day : days) {
            if (day.isSelected()) {
                timeGroup.setDisable(false);
                break;
            } else {
                unSelections++;
                timeGroup.setDisable(true);
            }
        }
        if (unSelections == days.size()) {
            // No option is selected.
            clearButtonGroupSelection(times);
        }
    }



    private void checkSecondPrecedence() {
        for (ToggleButton time : times) {
            if (time.isSelected()) {
                shoppingCartController.disableNextButton(false);
                break;
            } else {
                shoppingCartController.disableNextButton(true);
            }
        }
    }

    private void clearButtonGroupSelection(List<ToggleButton> list) {
        for (ToggleButton time : list) {
            time.setSelected(false);
        }
    }
}
