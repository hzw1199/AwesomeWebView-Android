package com.wuadam.awesomewebview.helpers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FormatHelper {

    private FormatHelper() {
    }

    public static String getExtension(File file) {
        try {
//            long fileLength = file.length();
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedInputStream.mark(Integer.MAX_VALUE);
            byte[] start;
            byte[] end;

            // https://en.wikipedia.org/wiki/JPEG
            start = new byte[2];
//            end = new byte[2];
            bufferedInputStream.read(start);
//            bufferedInputStream.skip(fileLength - start.length - end.length);
//            bufferedInputStream.read(end);
            if (start[0] == (byte) 0xFF &&
                    start[1] == (byte) 0xD8
//                    &&
//                    end[0] == (byte) 0xFF &&
//                    end[1] == (byte) 0xD9
            ) {
                return "jpg";
            }
            bufferedInputStream.reset();

            // https://en.wikipedia.org/wiki/Portable_Network_Graphics
            start = new byte[8];
            bufferedInputStream.read(start);
            if (start[0] == (byte) 0x89 &&
                    start[1] == (byte) 0x50 &&
                    start[2] == (byte) 0x4E &&
                    start[3] == (byte) 0x47 &&
                    start[4] == (byte) 0x0D &&
                    start[5] == (byte) 0x0A &&
                    start[6] == (byte) 0x1A &&
                    start[7] == (byte) 0x0A) {
                return "png";
            }
            bufferedInputStream.reset();

            // https://developers.google.com/speed/webp/docs/riff_container
            // https://en.wikipedia.org/wiki/WebP
            start = new byte[4];
            end = new byte[4];
            bufferedInputStream.read(start);
            bufferedInputStream.skip(4);
            bufferedInputStream.read(end);
            if (start[0] == (byte) 0x52 &&
                    start[1] == (byte) 0x49 &&
                    start[2] == (byte) 0x46 &&
                    start[3] == (byte) 0x46 &&
                    end[0] == (byte) 0x57 &&
                    end[1] == (byte) 0x45 &&
                    end[2] == (byte) 0x42 &&
                    end[3] == (byte) 0x50) {
                return "webp";
            }
            bufferedInputStream.reset();

            // https://en.wikipedia.org/wiki/File:Empty_GIF_hex.png
            start = new byte[6];
            bufferedInputStream.read(start);
            if (start[0] == (byte) 0x47 &&
                    start[1] == (byte) 0x49 &&
                    start[2] == (byte) 0x46 &&
                    start[3] == (byte) 0x38 &&
                    start[4] == (byte) 0x39 &&
                    start[5] == (byte) 0x61) {
                return "gif";
            }

            bufferedInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
