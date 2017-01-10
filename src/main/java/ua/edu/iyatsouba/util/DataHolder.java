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

            double sqrtVarience = Math.sqrt(calcVariance(sound.data));
            double coef = 1.0 / 3.0;
            boolean isFinish = false;

            short[] noLatent = new short[sound.data.length];

            System.arraycopy(sound.data, 0, noLatent, 0, sound.data.length );

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
            sound.dataWithoutLatentPeriods = noLatent;
        }
    }

    public void makeNormalization() {
        if(data != null) {
            Sound sound = data;
            double varience = calcVariance(sound.dataWithoutLatentPeriods);
            double[] normalizedData = new double[sound.dataWithoutLatentPeriods.length];
            for (int i=0; i < sound.dataWithoutLatentPeriods.length; i++){
                normalizedData[i] = sound.dataWithoutLatentPeriods[i] / Math.sqrt(varience);
            }
            sound.normalizationData = normalizedData;
            sound.countOfLines = sound.normalizationData.length / 256;
        }
   }

    public  double calcVariance(short[] arr) {
        double varience = 0;
        for (Short anArr : arr) {
            varience += Math.pow(anArr, 2);
        }
        varience /= arr.length;
        return varience;
    }

}
