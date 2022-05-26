package com.dreamss.dreamjourneycommon.utils;

import com.dreamss.dreamjourneycommon.enums.ResultEnum;
import com.dreamss.dreamjourneycommon.excepitons.DreamException;
import org.apache.poi.util.IOUtils;

import java.io.*;

/**
 * @author Created by DrEAmSs on 2022-05-26 13:58
 */
public class FileUtils {

    /**
     * 上传文件
     */
    public static void uploadFile(InputStream is, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                boolean mkdirs = parentFile.mkdirs();
                if (!mkdirs) {
                    throw new DreamException(ResultEnum.SERVER_ERROR.getValue(), ResultEnum.SERVER_ERROR.getLabel());
                }
            } else {
                try {
                    if (!file.createNewFile()) {
                        throw new DreamException(ResultEnum.SERVER_ERROR.getValue(), ResultEnum.SERVER_ERROR.getLabel());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            IOUtils.copy(is, fileOutputStream);
        } catch (Exception e) {
            throw new DreamException(ResultEnum.SERVER_ERROR.getValue(), ResultEnum.SERVER_ERROR.getLabel());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文件下载
     */
    public static void downloadFile(String remotePath, OutputStream outputStream) {
        try (InputStream inputStream = new FileInputStream(remotePath)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
