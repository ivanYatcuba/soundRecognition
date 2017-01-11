package ua.edu.iyatsouba.transform;


import ua.edu.iyatsouba.data.SoundData;

public class FourierTransform {

    private SoundData soundData;

    public FourierTransform(SoundData soundData) {
        this.soundData = soundData;
        this.soundData.laneRepresentationFourier = new double[soundData.countOfLines] [ 256 / 2];
    }

    private double calculateA(int k, int l) {
        double a = 0;
        int N = 256;
        for (int i = l * N; i < l * N + N + 1; i++) {
            a += soundData.normalizationData[i] * Math.cos(2 * Math.PI * k * i / N);
        }
        return a * (2.0 / (N));
    }

    private double calculateB(int k, int l) {
        double b = 0;
        int N = 256;
        for (int i = l * N; i < l * N + N + 1; i++) {
            b += soundData.normalizationData[i] * Math.sin(2 * Math.PI * k * i / N);
        }
        return b * (2.0 / (N));
    }

    private double calculateC(int k, int l) {
        double A = calculateA(k, l);
        double B = calculateB(k, l);
        return Math.pow(A, 2) + Math.pow(B, 2);
    }

    public void initFourierArray() {
        for (int i = 0; i < soundData.countOfLines; i++) {
            for (int j = 0; j < 256 / 2; j++) {
                soundData.laneRepresentationFourier[i][j] = Math.sqrt(calculateC(j, i));
            }
        }

    }


}
