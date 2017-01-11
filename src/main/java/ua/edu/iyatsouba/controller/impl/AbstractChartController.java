package ua.edu.iyatsouba.controller.impl;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import ua.edu.iyatsouba.controller.AbstractFxmlController;
import ua.edu.iyatsouba.data.SoundData;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

public abstract class AbstractChartController extends AbstractFxmlController implements Initializable {

    final double SCALE_DELTA = 1.1;

    protected abstract XYChart<Number, Number> getChart();

    @FunctionalInterface
    public interface DataMapper {
        XYChart.Data<Number, Number> apply(int index, SoundData soundData);
    }

    protected abstract DataMapper getDataMapper();

    protected abstract Function<SoundData, Integer> getLengthSupplier();

    protected abstract Function<SoundData, List> getData();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Panning works via either secondary (right) mouse or primary with ctrl held down
        XYChart<Number, Number> chart = getChart();
        ChartPanManager panner = new ChartPanManager( chart );
        panner.setMouseFilter(mouseEvent -> {
            if ( mouseEvent.getButton() == MouseButton.SECONDARY ||
                    ( mouseEvent.getButton() == MouseButton.PRIMARY &&
                            mouseEvent.isShortcutDown() ) ) {
                //let it through
            } else {
                mouseEvent.consume();
            }
        });
        panner.start();

        //Zooming works only via primary mouse button without ctrl held down
        JFXChartUtil.setupZooming( chart, mouseEvent -> {
            if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
                    mouseEvent.isShortcutDown() )
                mouseEvent.consume();
        });

        JFXChartUtil.addDoublePrimaryClickAutoRangeHandler( chart );
    }

    public void drawSourceChart(SoundData soundData) {

        getChart().getData().retainAll();

        XYChart.Series<Number,Number> data= new XYChart.Series<>();
        List<XYChart.Data<Number,Number>> dataList = new LinkedList<>();

        DataMapper dataMapper = getDataMapper();
        Function<SoundData, Integer> lengthSupplier = getLengthSupplier();

        for(int i = 0; i < lengthSupplier.apply(soundData); i++) {
            dataList.add(dataMapper.apply(i, soundData));
        }
        Platform.runLater(() -> data.getData().addAll(dataList));

        List allData = getData().apply(soundData);

        ((NumberAxis)getChart().getXAxis()).setLowerBound(0);
        ((NumberAxis)getChart().getXAxis()).setUpperBound(lengthSupplier.apply(soundData));

        Object min = Collections.min(allData);
        if(min instanceof Short) {
            min = ((Short) min).doubleValue();
        }

        Object max = Collections.max(allData);
        if(max instanceof Short) {
            max = ((Short) max).doubleValue();
        }

        ((NumberAxis)getChart().getYAxis()).setLowerBound((Double) min);
        ((NumberAxis)getChart().getYAxis()).setUpperBound((Double) max);

        Rectangle rect = new Rectangle(0, 0);
        rect.setVisible(false);
        data.setNode(rect);

        if(getChart() instanceof AreaChart) {
            ((AreaChart)getChart()).setCreateSymbols(false); //hide dots
        }

        if(getChart() instanceof LineChart) {
            ((LineChart)getChart()).setCreateSymbols(false); //hide dots
        }

        getChart().getData().add(data);
    }
}
