package ua.edu.iyatsouba.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import ua.edu.iyatsouba.data.SoundData;

@Component
public class DataHolder {

    private SoundData data;


    public SoundData getData() {
        return data;
    }

    public void setData(SoundData data) {
        this.data = data;
    }

    public void removeLatentPeriods() {

        if(data != null) {
            SoundData soundData = data;

            double sqrtVarience = Math.sqrt(calcVariance(soundData.data));
            double coef = 1.0 / 3.0;
            boolean isFinish = false;

            short[] noLatent = new short[soundData.data.length];

            System.arraycopy(soundData.data, 0, noLatent, 0, soundData.data.length );

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
            soundData.dataWithoutLatentPeriods = noLatent;
        }
    }

    public void makeNormalization() {
        if(data != null) {
            SoundData soundData = data;
            double varience = calcVariance(soundData.dataWithoutLatentPeriods);
            double[] normalizedData = new double[soundData.dataWithoutLatentPeriods.length];
            for (int i = 0; i < soundData.dataWithoutLatentPeriods.length; i++){
                normalizedData[i] = soundData.dataWithoutLatentPeriods[i] / Math.sqrt(varience);
            }
            soundData.normalizationData = normalizedData;
            soundData.countOfLines = soundData.normalizationData.length / 256;
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
