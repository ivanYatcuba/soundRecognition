package ua.edu.iyatsouba.controller.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.edu.iyatsouba.controller.AbstractFxmlController;
import ua.edu.iyatsouba.util.DataHolder;
import ua.edu.iyatsouba.util.FileReader;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MainController extends AbstractFxmlController implements Initializable {

    @Autowired
    private DataHolder dataHolder;
    @Autowired
    private FileReader fileReader;

    @Autowired
    TabSourceSignalController tabSourceSignalController;
    @Autowired
    TabNoLatentPeriodsController tabNoLatentPeriodsController;
    @Autowired
    TabNormalizedSignalController tabNormalizedSignalController;

    @FXML
    private MenuItem fileOpen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");

                File file = fileChooser.showOpenDialog(getView().getScene().getWindow());
                if (file != null) {
                    dataHolder.setData(fileReader.read(file));
                    dataHolder.removeLatentPeriods();
                    dataHolder.makeNormalization();
                    tabSourceSignalController.drawSourceChart(dataHolder.getData());
                    tabNoLatentPeriodsController.drawSourceChart(dataHolder.getData());
                    tabNormalizedSignalController.drawSourceChart(dataHolder.getData());
                }
            }
        });
    }
}
