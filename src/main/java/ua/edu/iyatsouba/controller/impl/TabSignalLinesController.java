package ua.edu.iyatsouba.controller.impl;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.edu.iyatsouba.data.SoundData;
import ua.edu.iyatsouba.transform.LineRepresentation;
import ua.edu.iyatsouba.util.DataHolder;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

@Controller
public class TabSignalLinesController extends AbstractChartController {

    @Autowired
    private DataHolder dataHolder;
    @FXML
    private LineChart<Number,Number> spectrumChart;
    @FXML
    private Slider spectrumLine;
    @FXML
    private TextField fCut;
    @FXML
    private TextField n;

    private int lineCount() {
        return (int) spectrumLine.getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        spectrumLine.setBlockIncrement(1);

        spectrumLine.setMin(0);
        spectrumLine.setMax(8);

        spectrumLine.valueProperty().addListener((obs, oldVal, newVal) -> {
            int value = newVal.intValue();
            spectrumLine.setValue(value);

            drawSourceChart(dataHolder.getData());
        });

        fCut.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(!Objects.equals(oldValue, newValue)) {
                    double f = Double.parseDouble(newValue);
                    if(f != 0 && f > 0) {
                        LineRepresentation lineRepresentation = new LineRepresentation(dataHolder.getData(), f, getN());
                        lineRepresentation.initRepresentationFourier();
                        lineRepresentation.initRepresentationFourierSmoothing();
                        drawSourceChart(dataHolder.getData());
                    }

                }
            } catch (Exception e) {
                //do nothing
            }
        });

        n.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(!Objects.equals(oldValue, newValue)) {
                    int N = Integer.parseInt(newValue);
                    if(N != 0 && N > 0) {
                        LineRepresentation lineRepresentation = new LineRepresentation(dataHolder.getData(), getFCut(), N);
                        lineRepresentation.initRepresentationFourier();
                        lineRepresentation.initRepresentationFourierSmoothing();
                        drawSourceChart(dataHolder.getData());
                    }

                }
            } catch (Exception e) {
                //do nothing
            }
        });
    }

    @Override
    public void drawSourceChart(SoundData soundData) {
        super.drawSourceChart(soundData);

        XYChart.Series<Number,Number> data= new XYChart.Series<>();
        List<XYChart.Data<Number,Number>> dataList = new LinkedList<>();

        for(int i = 0; i < soundData.lineRepresentationFourierSmoothing[lineCount()].length; i++) {
            dataList.add(new XYChart.Data<>(i, soundData.lineRepresentationFourierSmoothing[lineCount()][i]));
        }
        Platform.runLater(() -> data.getData().addAll(dataList));
        getChart().getData().add(data);
    }

    @Override
    protected XYChart<Number, Number> getChart() {
        return spectrumChart;
    }

    @Override
    protected DataMapper getDataMapper() {
        return (index, sound) ->   new XYChart.Data<>(index, sound.lineRepresentationFourier[lineCount()][index]);
    }

    @Override
    protected Function<SoundData, Integer> getLengthSupplier() {
        return sound -> sound.lineRepresentationFourier[lineCount()].length;
    }

    @Override
    protected Function<SoundData, List> getData() {
        return sound1 -> {
            List<Double> shorts =  new LinkedList<>();
            for(int i = 0; i< sound1.lineRepresentationFourier[lineCount()].length; i++) {
                shorts.add(sound1.lineRepresentationFourier[lineCount()][i]);
            }
            return shorts;
        };
    }

    public double getFCut() {
        try {
            return Double.parseDouble(fCut.getText());
        } catch (Exception e) {
            return 0.1;
        }
    }

    public int getN() {
        try {
            return Integer.parseInt(n.getText());
        } catch (Exception e) {
            return 5;
        }
    }
}
