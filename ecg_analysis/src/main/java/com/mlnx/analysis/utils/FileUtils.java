package com.mlnx.analysis.utils;

import com.mlnx.mptp.utils.MptpLogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by amanda.shan on 2018/2/6.
 */
public class FileUtils {

    public static void copyFile(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e){
            MptpLogUtils.e("拷贝文件失败:",e);
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
}
