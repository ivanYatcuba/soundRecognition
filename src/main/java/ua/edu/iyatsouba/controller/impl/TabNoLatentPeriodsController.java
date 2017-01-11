package ua.edu.iyatsouba.controller.impl;


import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import org.springframework.stereotype.Controller;
import ua.edu.iyatsouba.data.SoundData;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Controller
public class TabNoLatentPeriodsController extends AbstractChartController {

    @FXML
    private AreaChart<Number,Number> noLatentChart;

    @Override
    protected AreaChart<Number, Number> getChart() {
        return noLatentChart;
    }

    @Override
    protected DataMapper getDataMapper() {
        return (index, sound) ->  new XYChart.Data<>(index, sound.dataWithoutLatentPeriods[index]);
    }

    @Override
    protected Function<SoundData, Integer> getLengthSupplier() {
        return sound -> sound.dataWithoutLatentPeriods.length;
    }

    @Override
    protected Function<SoundData, List> getData() {
        return sound1 -> {
            List<Short> shorts =  new LinkedList<>();
            for(int i = 0; i< sound1.dataWithoutLatentPeriods.length; i++) {
                shorts.add(sound1.dataWithoutLatentPeriods[i]);
            }
            return shorts;
        };
    }
}
