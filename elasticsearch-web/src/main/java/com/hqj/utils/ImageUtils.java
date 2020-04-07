package com.hqj.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片工具类
 */
public class ImageUtils {
    /**
     * base64转图片
     *
     * @param base64
     * @param filePath
     */
    public static void base64ToImage(String base64, String filePath) throws IOException{
        try (OutputStream out = new FileOutputStream(filePath)) {
            out.write(Base64.decodeBase64(base64));
            out.flush();
        }
    }
}
