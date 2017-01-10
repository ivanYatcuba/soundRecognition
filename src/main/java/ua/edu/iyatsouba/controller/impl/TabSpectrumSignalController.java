package ua.edu.iyatsouba.controller.impl;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.edu.iyatsouba.data.Sound;
import ua.edu.iyatsouba.util.DataHolder;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

@Controller
public class TabSpectrumSignalController extends AbstractChartController {

    @Autowired
    private DataHolder dataHolder;
    @FXML
    private Slider spectrumLine;
    @FXML
    private LineChart<Number,Number> spectrumChart;

    public void initLinesCount(int countOfLines) {
        spectrumLine.setMin(0);
        spectrumLine.setMax(countOfLines - 1);
    }

    private int lineCount() {
        return (int) spectrumLine.getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        spectrumLine.setBlockIncrement(1);

        spectrumLine.valueProperty().addListener((obs, oldVal, newVal) -> {
            int value = newVal.intValue();
            spectrumLine.setValue(value);

            drawSourceChart(dataHolder.getData());
           /* if (YolshSelected.isSelected()) {
                DrawWalschTransform((int) trackBar1.getValue());
            } else {
                DrawFourierTransform((int) trackBar1.getValue());
            }*/
        });
    }

    @Override
    protected LineChart<Number, Number> getChart() {
        return spectrumChart;
    }

    @Override
    protected DataMapper getDataMapper() {
        return (index, sound) ->  new XYChart.Data<>(index, sound.laneRepresentationFourier[lineCount()][index]);
    }

    @Override
    protected Function<Sound, Integer> getLengthSupplier() {
        return sound -> sound.laneRepresentationFourier[lineCount()].length;
    }

    @Override
    protected Function<Sound, List> getData() {
        return sound1 -> {
            List<Double> shorts =  new LinkedList<>();
            for(int i = 0; i< sound1.laneRepresentationFourier[lineCount()].length; i++) {
                shorts.add(sound1.laneRepresentationFourier[lineCount()][i]);
            }
            return shorts;
        };
    }
}
