package ua.edu.iyatsouba.transform;

import ua.edu.iyatsouba.data.SoundData;

import java.util.ArrayList;
import java.util.List;

public class LineRepresentation {

    public static final int LINE_COUNT = 9;

    private SoundData sound;
    private double f;
    private int N;
    private List<Double> c;

    public LineRepresentation(SoundData sound, double f, int n) {
        this.sound = sound;
        this.N = n;
        this.sound.lineRepresentationFourier = new double[LINE_COUNT][sound.countOfLines];
        this.sound.lineRepresentationFourierSmoothing = new double[LINE_COUNT][sound.countOfLines];

        this.f = f;
        this.c = new ArrayList<>();
    }

    public void initRepresentationFourier() {
        for (int i = 0; i < sound.countOfLines; i++) {
            sound.lineRepresentationFourier[0][i] = calculateSum(0, 1, i, sound.laneRepresentationFourier);
            sound.lineRepresentationFourier[1][i] = calculateSum(2, 3, i, sound.laneRepresentationFourier);
            sound.lineRepresentationFourier[2][i] = calculateSum(4, 5, i, sound.laneRepresentationFourier);
            sound.lineRepresentationFourier[3][i] = calculateSum(6, 7, i, sound.laneRepresentationFourier);
            sound.lineRepresentationFourier[4][i] = calculateSum(8, 9, i, sound.laneRepresentationFourier);
            sound.lineRepresentationFourier[5][i] = calculateSum(10, 14, i, sound.laneRepresentationFourier);
            sound.lineRepresentationFourier[6][i] = calculateSum(15, 24, i, sound.laneRepresentationFourier);
            sound.lineRepresentationFourier[7][i] = calculateSum(25, 49, i, sound.laneRepresentationFourier);
            sound.lineRepresentationFourier[8][i] = calculateSum(50, 127, i, sound.laneRepresentationFourier);
        }
    }

    public void initRepresentationFourierSmoothing() {
        for (int i = 0; i < LINE_COUNT; i++) {
            for (int j = 0; j < sound.countOfLines; j++) {
                sound.lineRepresentationFourierSmoothing[i][j] = calculateYSmoothing(j, i, sound.lineRepresentationFourier, f);
            }
        }
    }

    private double calculateSum(int from, int to, int line, double[][] arr) {
        double sum = 0;
        for (int j = from; j <= to; j++) {
            sum += Math.pow(arr[line][j], 2);
        }
        return Math.sqrt(sum);
    }


    private double calculateYSmoothing(int l, int line, double[][] arr, double f) {
        initCArray(f);

        double y = 0;
        for (int i = -N; i <= N; i++) {
            int index = l - i;
            if (l - i >= sound.countOfLines) {
                index = -i - 1;
            }
            if (l - i < 0) {
                index = sound.countOfLines - i;
            }
            y += c.get(N + i) * arr[line][index];
        }
        return y;
    }


    private void initCArray(double f) {
        c.clear();
        for (int k = -N; k <= N; k++) {
            if (k == 0) {
                c.add(2 * f);
            } else {
                c.add(calculateC(k, f));
            }
        }
    }

    private double calculateC(int k, double f) {
        return (1.0 / (k * Math.PI)) * Math.sin(2.0 * Math.PI * k * f);
    }

}
