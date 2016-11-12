package ua.edu.iyatsouba.data;

public class Sound {

    private short[] data;

    private short[] dataWithoutLatentPeriods;
    private double[] normalizationData;

    public Sound(short[] data) {
        this.data = data;
    }

    public short[] getData() {
        return data;
    }

    public void setDataWithoutLatentPeriods(short[] dataWithoutLatentPeriods) {
        this.dataWithoutLatentPeriods = dataWithoutLatentPeriods;
    }

    public void setNormalizationData(double[] normalizationData) {
        this.normalizationData = normalizationData;
    }

    public short[] getDataWithoutLatentPeriods() {
        return dataWithoutLatentPeriods;
    }

    public double[] getNormalizationData() {
        return normalizationData;
    }
}
