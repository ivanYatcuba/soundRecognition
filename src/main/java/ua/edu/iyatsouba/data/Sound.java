package ua.edu.iyatsouba.data;

public class Sound {

    public int countOfLines;

    public short[] data;

    public short[] dataWithoutLatentPeriods;
    public double[] normalizationData;

    public double[][] laneRepresentationFourier;
    public double[][] laneRepresentationWalsch;

    public double[][] lineRepresentationFourier;
    public double[][] lineRepresentationWalsch;

    public double[][] lineRepresentationFourierSmoothing;
    public double[][] lineRepresentationWalschSmoothing;

    public Sound(short[] data) {
        this.data = data;
    }
}
