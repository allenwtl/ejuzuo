package com.ejuzuo.common.util;

import com.ejuzuo.common.constants.ImageBusinessType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);


    /**
     * 删除目录下的所有目录和文件
     */
    public static String deleteDirectory(File dir) {
        if ((dir == null) || !dir.isDirectory()) {
            return "notDir";
        }

        File[] entries = dir.listFiles();
        for (int i = 0; i < entries.length; i++) {
            if (entries[i].isDirectory()) {
                deleteDirectory(entries[i]);
            } else {
                if (!entries[i].delete()) {
                    return "faile";
                }
            }
        }

        return "ok";
    }

    /**
     * 生成目录的规则
     * fileRoot 为根目录  nginx 映射的目录
     *
     * @return
     * @see ImageBusinessType
     * businessType 上传的图书所属的业务
     * fileRoot/businessType/yyyyMMdd/filename.formmat
     */
    public static String saveAdImg(String fileRoot, ImageBusinessType businessType, MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String format = StringUtils.substring(originalFilename, originalFilename.lastIndexOf("."));
        String destPath = FileUtils.getGenerateImagePath(businessType.getDescription());
        String destFile = "";

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            // D://www//ejuzuo
            File directory = new File(fileRoot + destPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            destFile = destPath + new Random().nextInt(6543) + Calendar.getInstance().getTimeInMillis() + format;
            logger.info(fileRoot + destFile);
            multipartFile.transferTo(new File(fileRoot + destFile));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return destFile;
    }


    /**
     * @param secondRoot 第二级目录
     * @return ad/20160202
     */
    private static String getGenerateImagePath(String secondRoot) {
        String datePrefix = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd");
        return "/img/"+ secondRoot + "/" + datePrefix + "/";
    }

    public static void main(String[] args) throws IOException {

        File file = new File("d:" + File.separator + "mldn.txt") ;  // 定义要压缩的文件
        File zipFile = new File("d:" + File.separator + "mldn.zip") ;   // 定义压缩文件名称
        InputStream input = new FileInputStream(file) ; // 定义文件的输入流
        ZipOutputStream zipOut = null ; // 声明压缩流对象
        zipOut = new ZipOutputStream(new FileOutputStream(zipFile)) ;
        zipOut.putNextEntry(new ZipEntry(file.getName())) ; // 设置ZipEntry对象
        zipOut.setComment("www.mldnjava.cn") ;  // 设置注释
        int temp = 0 ;
        while((temp=input.read())!=-1){ // 读取内容
            zipOut.write(temp) ;    // 压缩输出
        }



        input.close() ; // 关闭输入流
        zipOut.close() ;    // 关闭输出流
    }


}
