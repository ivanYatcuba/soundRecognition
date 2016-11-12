package ua.edu.iyatsouba.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import ua.edu.iyatsouba.data.Sound;

@Component
public class DataHolder {

    private Sound data;


    public Sound getData() {
        return data;
    }

    public void setData(Sound data) {
        this.data = data;
    }

    public void removeLatentPeriods() {

        if(data != null) {
            Sound sound = data;

            double sqrtVarience = Math.sqrt(calcVariance(sound.getData()));
            double coef = 1.0 / 3.0;
            boolean isFinish = false;

            short[] noLatent = new short[sound.getData().length];

            System.arraycopy(sound.getData(), 0, noLatent, 0, sound.getData().length );


            for(int i = 0; i < noLatent.length; i++) {
                if (!isFinish) {
                    if (noLatent[0] >= coef * sqrtVarience || noLatent[0] <= -coef * sqrtVarience) {
                        isFinish = true;
                    }
                    else {
                        noLatent = ArrayUtils.remove(noLatent, 0);
                    }
                }
            }
            isFinish = false;
            for(int i = noLatent.length - 1; i >= 0; i++) {
                if (!isFinish) {
                    if (noLatent[noLatent.length - 1] >= coef * sqrtVarience || noLatent[noLatent.length - 1] <= -coef * sqrtVarience) {
                        isFinish = true;
                    }
                    else {
                        noLatent = ArrayUtils.remove(noLatent, noLatent.length - 1);
                    }
                }
            }
            sound.setDataWithoutLatentPeriods(noLatent);
        }
    }

    public void makeNormalization() {

        if(data != null) {
            Sound sound = data;
            double varience = calcVariance(sound.getDataWithoutLatentPeriods());
            double[] normalizedData = new double[sound.getDataWithoutLatentPeriods().length];
            for (int i=0; i < sound.getDataWithoutLatentPeriods().length; i++){
                normalizedData[i] = sound.getDataWithoutLatentPeriods()[i] / Math.sqrt(varience);
            }
            sound.setNormalizationData(normalizedData);
        }
    }

    public double calcVariance(short[] arr) {
        double varience = 0;
        for (int i = 0; i < arr.length; i++) {
            varience += Math.pow(arr[i], 2)/arr.length;
        }
        return varience;
    }

}
