package com.mlnx.utils;

import java.io.File;
import java.util.List;

/**
 * @author fzh
 * @create 2018/4/3 8:47
 */
public class ReadFileUtils {


    /**
     * 读取目录下所有的文件
     * @param files 存放文件路径的list
     * @param filePath 文件名
     */
    public static void readAllFile(List<String> files,String filePath){
        File file = new File(filePath);
        if(!file.isDirectory()){
            files.add(filePath);
        }else {
            String[] filelist=file.list();
            for(int i = 0,len=filelist.length;i<len;i++){
                readAllFile(files,filePath + "\\" + filelist[i]);
            }
        }
    }

}
