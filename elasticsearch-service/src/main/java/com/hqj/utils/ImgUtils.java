package com.hqj.utils;

import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.UUID;

/**
 * 图片工具类
 */
public class ImgUtils {
    private static final String EMPTY = "";
    private static final String ZX = "-";
    private static final String POINT = ".";
    private static final String XG = "/";

    /**
     * 链接url下载图片
     *
     * @param url
     * @param path
     * @return
     */
    public static String downloadPic(String url, String path) {
        try {
            if(StringUtils.isEmpty(url)) {
                return null;
            }
            DataInputStream dataInputStream = new DataInputStream(new URL(url).openStream());
            File file = new File(path);
            if(!file.exists()) {
                file.mkdirs();
            }
            // 文件名
            String imgName = UUID.randomUUID().toString().replace(ZX, EMPTY) + url.substring(url.lastIndexOf(POINT));
            imgName = imgName.replace("/", "");
            String imgPath = path + imgName;
            FileOutputStream fileOutputStream = new FileOutputStream(new File(imgPath));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            return imgName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
