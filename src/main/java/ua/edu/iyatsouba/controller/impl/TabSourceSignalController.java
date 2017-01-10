package ua.edu.iyatsouba.controller.impl;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import org.springframework.stereotype.Controller;
import ua.edu.iyatsouba.data.Sound;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Controller
public class TabSourceSignalController extends AbstractChartController {

    @FXML
    private AreaChart<Number,Number> sourceChart;

    @Override
    protected AreaChart<Number, Number> getChart() {
        return sourceChart;
    }

    @Override
    protected DataMapper getDataMapper() {
        return (index, sound) -> new XYChart.Data<>(index, sound.data[index]);
    }

    @Override
    protected Function<Sound, Integer> getLengthSupplier() {
        return sound -> sound.data.length;
    }

    @Override
    protected Function<Sound, List> getData() {
        return sound1 -> {
            List<Short> shorts =  new LinkedList<>();
            for(int i = 0; i< sound1.data.length; i++) {
                shorts.add(sound1.data[i]);
            }
            return shorts;
        };
    }
}
