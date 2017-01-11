package ua.edu.iyatsouba.data;

public class SoundData {

    public int countOfLines;

    public short[] data;

    public short[] dataWithoutLatentPeriods;
    public double[] normalizationData;

    public double[][] laneRepresentationFourier;

    public double[][] lineRepresentationFourier;

    public double[][] lineRepresentationFourierSmoothing;

    public SoundData(short[] data) {
        this.data = data;
    }
}
