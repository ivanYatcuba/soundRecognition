package ua.edu.iyatsouba.util;

import org.springframework.stereotype.Component;
import ua.edu.iyatsouba.data.Sound;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

@Component
public class FileReader {

    public static  int HEADER_LENGTH = 44;

    public Sound read(File file) {
        byte[]  byteInput = new byte[(int)file.length() - HEADER_LENGTH];
        short[] input = new short[(int)(byteInput.length / 2f)];

        try{
            FileInputStream fis = new FileInputStream(file);
            fis.read(byteInput, HEADER_LENGTH, byteInput.length - HEADER_LENGTH);
            ByteBuffer.wrap(byteInput).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(input);

            short[] inputC = Arrays.copyOfRange(input, HEADER_LENGTH, input.length);

            return new Sound(inputC);
        }catch(Exception e  ){
            e.printStackTrace();
            return null;
        }
    }
}
