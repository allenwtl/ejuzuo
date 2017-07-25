package com.ejuzuo.common.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PicUtil {

	private static final transient Logger logger = LoggerFactory.getLogger(PicUtil.class);
	/**
	 * 按照图片大小进行压缩
	 * @param basePath 根路径，如 ：/mfs/ShareFile/upload/public
	 * @param relaPath 相对路径，如：img/member/20160521/63491463826887417.png
	 * @param width 
	 * @param height 
	 * @return
	 */
	public static String compressPic(String basePath,String relaPath,int width, int height) {
		String dist = relaPath.split("\\.")[0]+"-"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("mmssSSS"))+"."+relaPath.split("\\.")[1];
		logger.info("按照图片大小压缩，根路径:{},文件相对路径：{}，生成文件相对路径:{},宽度:{},高度:{}",basePath,relaPath,dist,width,height);
		try {
			Thumbnails.of(basePath + File.separator + relaPath).size(width, height)
			.keepAspectRatio(false)
			.toFile(basePath + File.separator + dist);
		} catch (IOException e) {
			logger.error("生成图片异常");
			e.printStackTrace();
			return null;
		}
		logger.info("生成图片成功，生成路径:{}",basePath + File.separator + dist);
		return dist;
	}
	
	/**
	 * 按照图片等比例进行压缩
	 * @param basePath 根路径，如 ：/mfs/ShareFile/upload/public
	 * @param relaPath 相对路径，如：img/member/20160521/63491463826887417.png
	 * @param width 
	 * @param height 
	 * @return
	 */
	public static String compressPicRate(String basePath,String relaPath,int width, int height) {
		String dist = relaPath.split("\\.")[0]+"-"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("mmssSSS"))+"."+relaPath.split("\\.")[1];
		logger.info("按照图片大小压缩，根路径:{},文件相对路径：{}，生成文件相对路径:{},宽度:{},高度:{}",basePath,relaPath,dist,width,height);
		try {
			Thumbnails.of(basePath + File.separator + relaPath).size(width, height)
			.toFile(basePath + File.separator + dist);
		} catch (IOException e) {
			logger.error("生成图片异常");
			e.printStackTrace();
			return null;
		}
		logger.info("生成图片成功，生成路径:{}",basePath + File.separator + dist);
		return dist;
	}
	
	public static void main(String[] args) {
		System.out.println(compressPic("d:/test/public", "img/default.jpg",43, 43));
		System.out.println(compressPic("d:/test/public", "img/default.jpg",76, 75));
	}
	
}
