package com.ejuzuo.admin.controller.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.FileInfo;
import com.ejuzuo.common.domain.type.FileInfoRelatedType;
import com.ejuzuo.common.option.FileInfoOption;
import com.ejuzuo.common.service.FileInfoService;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.PicUtil;

@Controller
@RequestMapping("/file")
public class FileInfoController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(FileInfoController.class);
	@Resource
	private FileInfoService fileInfoService;
	@Resource(name = "newsDomainRes")
	private  String NEWS_DOMAIN_RES;
	@Resource(name = "imagePath")
	private String PATH_IMAGE; // 图像物理根路径
	@Resource(name = "attachmentPath")
	private String PATH_ATTACHMENT; // 附件物理根路径
	
	private final static Pattern PATTERN_IMAGE_EXTENSION = Pattern.compile("^(jpe?g|png|gif|bmp|webp|bpg)$");
	private final static DateTimeFormatter DTF_FILE_PATH = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private final static DateTimeFormatter DTF_FILE_NAME = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	private final static String FORMAT_CKEDITOR_RESULT = "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(%s,'%s','%s');</script>";
	private final static String[] IMAGE_EXTENSIONS = new String[]{"jpg", "jpeg", "png", "gif", "bmp", "webp", "bpg"};
	private final static Pattern PATTERN_ARCHIVE_EXTENSION = Pattern.compile("^(zip|rar|7z|cab|iso)$");
	
	@RequestMapping(value = "upload-image", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadImage(@RequestPart MultipartFile image,String path, Integer relatedType, HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		logger.info("图片上传 [{}]", image.getOriginalFilename());
		// 校验文件类型
		String extension = FilenameUtils.getExtension(image.getOriginalFilename());
		extension = StringUtils.lowerCase(extension);
		Matcher matcher = PATTERN_IMAGE_EXTENSION.matcher(extension);
		if (!matcher.matches()) {
			logger.warn("图片上传 格式不正确 [{}]", image.getOriginalFilename());
			jo.put("success", false);
			jo.put("msg", "文件格式不正确");
			return jo;
		}
		String datePath = LocalDate.now().format(DTF_FILE_PATH);
		String fileName = LocalDateTime.now().format(DTF_FILE_NAME);
		String pathChild =  fileName + "." + extension;
		String pathParent;
		if (StringUtils.isBlank(path)) {
			pathParent = PATH_IMAGE + File.separator + datePath;
			path = "/" + datePath + "/" + pathChild;
		}else {
			pathParent = PATH_IMAGE + File.separator + path + File.separator + datePath;
			path = "/" +path + "/" + datePath + "/" + pathChild;
		}
		
		logger.info("图片上传 准备 [{}|{}]", image.getOriginalFilename(), path);
		try {
			File img = new File(pathParent, pathChild);
			if (!img.exists()) {
				img.mkdirs();
				img.createNewFile();
			}
			// 转存文件
			image.transferTo(img);
		} catch (IllegalStateException e) {
			logger.error("图片上传 转存图片 异常", e);
			jo.put("success", false);
			jo.put("msg", "转存图片异常");
			return jo;
		} catch (IOException e) {
			logger.error("图片上传 转存图片 异常", e);
			jo.put("success", false);
			jo.put("msg", "转存图片异常");
			return jo;
		}
		logger.info("图片上传 成功 [{}|{}]", image.getOriginalFilename(), path);
		
		int width = 200;
		int height = 200;
		if (relatedType != null) {
			if (relatedType == FileInfoRelatedType.xinwen.getIndex()) {
				width = 160;
				height = 120;
			}else if (relatedType == FileInfoRelatedType.huodong.getIndex()) {
				width = 383;
				height = 211;
			}else if (relatedType == FileInfoRelatedType.shuzijiaju.getIndex()) {
				if (Boolean.valueOf(request.getParameter("content"))) {
					logger.info("数字家居图示");
					width = 205;
					height = 100;
				}else {
					width = 288;
					height = 288;
				}
			}else if (relatedType == FileInfoRelatedType.pinpai.getIndex()) {
				width = 129;
				height = 49;
			}
		}
		String smallPath = PicUtil.compressPic(PATH_IMAGE, path, width, height);
		if (smallPath == null) {
			logger.info("生成缩略图失败");
			jo.put("success", false);
			jo.put("msg", "生成缩略图失败");
			return jo;
		}
		/**生成FileInfo*/
		FileInfo obj = new FileInfo(null,image.getOriginalFilename(), image.getSize(), extension, path, null, this.getUserAccount(), Calendar.getInstance(), relatedType==null?0:relatedType, 0, Status.STATUS.getIndex(), Calendar.getInstance(), Calendar.getInstance());
		fileInfoService.save(obj);
//		jo.put("fileId", obj.getId()); // 关联的fileId
		/** 如果是数字家居的内容，还要把缩略图 原路径和小图、大图路径返回给前端*/
		if (relatedType == FileInfoRelatedType.shuzijiaju.getIndex() && Boolean.valueOf(request.getParameter("content"))) {
			jo.put("path", path);
			jo.put("pic205100", smallPath);
			String pic850_478 = PicUtil.compressPic(PATH_IMAGE, path, 850, 478);
			jo.put("pic850478", pic850_478);
		}else {
			jo.put("path", smallPath);
		}
		jo.put("fileName", image.getOriginalFilename());
		jo.put("success", true);
		return jo;
	}
	
	@RequestMapping(value = "ckeditor/upload-image", method = RequestMethod.POST)
	public void uploadImageFromCKEditor(HttpServletResponse response, 
			@RequestPart MultipartFile upload, @RequestParam String CKEditorFuncNum, String path) throws IOException {
		logger.info("CKEditor 图片上传 [{}]", upload.getOriginalFilename());
		
		PrintWriter out = response.getWriter();
		
		// 校验文件类型
		String extension = FilenameUtils.getExtension(upload.getOriginalFilename());
		extension = StringUtils.lowerCase(extension);
		Matcher matcher = PATTERN_IMAGE_EXTENSION.matcher(extension);
		if (!matcher.matches()) {
			logger.warn("图片上传 格式不正确 [{}]", upload.getOriginalFilename());
			String result = String.format(FORMAT_CKEDITOR_RESULT, CKEditorFuncNum, "", "文件格式不正确");
			out.print(result);
			out.close();
			return;
		}
		
		String datePath = LocalDate.now().format(DTF_FILE_PATH);
		String fileName = LocalDateTime.now().format(DTF_FILE_NAME);
		
		String pathChild =  fileName + "." + extension;
		String pathParent;
		if (StringUtils.isBlank(path)) {
			pathParent = PATH_IMAGE + File.separator + datePath;
			path = "/" + datePath + "/" + pathChild;
		}else {
			pathParent = PATH_IMAGE + File.separator + path + File.separator + datePath;
			path = "/" +path + "/" + datePath + "/" + pathChild;
		}
		String url = NEWS_DOMAIN_RES + path;
		logger.info("CKEditor 图片上传 准备 [{}|{}|{}]", upload.getOriginalFilename(), path, url);
		try {
			File img = new File(pathParent, pathChild);
			if (!img.exists()) {
				img.mkdirs();
				img.createNewFile();
			}
			// 转存文件
			upload.transferTo(img);
		} catch (IllegalStateException e) {
			logger.error("CKEditor 图片上传 转存图片 异常", e);
			String result = String.format(FORMAT_CKEDITOR_RESULT, CKEditorFuncNum, "", "转存图片异常");
			out.print(result);
			out.close();
			return;
		} catch (IOException e) {
			logger.error("CKEditor 图片上传 转存图片 异常", e);
			String result = String.format(FORMAT_CKEDITOR_RESULT, CKEditorFuncNum, "", "转存图片异常");
			out.print(result);
			out.close();
			return;
		}
		
		logger.info("CKEditor 图片上传 成功 [{}|{}|{}]", upload.getOriginalFilename(), path, url);
		String result = String.format(FORMAT_CKEDITOR_RESULT, CKEditorFuncNum, url, "");
		out.print(result);
		out.close();
		
		/**生成FileInfo*/
		FileInfo obj = new FileInfo(null,upload.getOriginalFilename(), upload.getSize(), extension, path, null, this.getUserAccount(), Calendar.getInstance(), 1, 0, Status.STATUS.getIndex(), Calendar.getInstance(), Calendar.getInstance());
		ModelResult<FileInfo> modelResult = fileInfoService.save(obj);
		obj = modelResult.getModel();
		JSONObject remark = new JSONObject();
		remark.put("path", path);
		remark.put("fileName", upload.getOriginalFilename());
		return;
	}
	
	@RequestMapping(value = "ckeditor/image-viewer", method = RequestMethod.GET)
	public String ckeditorImageViewer(@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate date,
			@RequestParam String CKEditor, @RequestParam String CKEditorFuncNum, @RequestParam String langCode,
			String path,ModelMap modelMap) {
		modelMap.put("CKEditor", CKEditor);
		modelMap.put("CKEditorFuncNum", CKEditorFuncNum);
		modelMap.put("langCode", langCode);
		
		if (date == null) {
			date = LocalDate.now();
		}
		
		LocalDate datePrevious = date.minusDays(1);
		LocalDate dateNext = null;
		
		if (!date.isEqual(LocalDate.now())) {
			dateNext = date.plusDays(1);
			modelMap.put("dateNext", dateNext.toString());
		}
		
		modelMap.put("date", date.toString());
		modelMap.put("datePrevious", datePrevious.toString());
		
		String datePath = date.format(DTF_FILE_PATH);
		String folderPath = PATH_IMAGE + (StringUtils.isBlank(path)?"":(File.separator+path)) + File.separator + datePath;
		
		File directory = new File(folderPath);
		
		if (!directory.exists()) {
			return "news/new-ckeditor-image-viewer";
		}
		Collection<File> images = FileUtils.listFiles(directory, IMAGE_EXTENSIONS, false);
		
		List<String> imageUrls = new ArrayList<String>();
		if (images != null && images.size() > 0) {
			for (File image : images) {
				imageUrls.add(NEWS_DOMAIN_RES + (StringUtils.isBlank(path)?"":("/"+path)) + "/" + datePath + "/" + image.getName());
			}
		}
		modelMap.put("imageUrls", imageUrls);
		return "news/news-ckeditor-image-viewer";
	}
	
	@RequestMapping(value = "upload-archive", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadArchive(@RequestPart MultipartFile archive,String path, Integer relatedType) {
		JSONObject jo = new JSONObject();
		logger.info("压缩包上传 [{}]", archive.getOriginalFilename());
		// 校验文件类型
		String extension = FilenameUtils.getExtension(archive.getOriginalFilename());
		extension = StringUtils.lowerCase(extension);
		Matcher matcher = PATTERN_ARCHIVE_EXTENSION.matcher(extension);
		if (!matcher.matches()) {
			logger.warn("压缩包上传 格式不正确 [{}]", archive.getOriginalFilename());
			jo.put("success", false);
			jo.put("msg", "文件格式不正确");
			return jo;
		}
		String fileName = LocalDateTime.now().format(DTF_FILE_NAME);
		String pathChild =  fileName + "." + extension;
		String datePath = LocalDate.now().format(DTF_FILE_PATH);
		String pathParent;
		if (StringUtils.isBlank(path)) {
			pathParent = PATH_ATTACHMENT + File.separator + datePath;
			path = "/" + datePath + "/" + pathChild;
		}else {
			pathParent = PATH_ATTACHMENT + File.separator + path + File.separator + datePath;
			path = "/" +path + "/" + datePath + "/" + pathChild;
		}
		
		logger.info("压缩包上传 准备 [{}|{}]", archive.getOriginalFilename(), path);
		try {
			File file = new File(pathParent, pathChild);
			if (!file.exists()) {
				file.mkdirs();
				file.createNewFile();
			}
			// 转存文件
			archive.transferTo(file);
		} catch (IllegalStateException e) {
			logger.error("压缩包上传 转存压缩包 异常", e);
			jo.put("success", false);
			jo.put("msg", "转存压缩包异常");
			return jo;
		} catch (IOException e) {
			logger.error("压缩包上传 转存压缩包 异常", e);
			jo.put("success", false);
			jo.put("msg", "转存压缩包异常");
			return jo;
		}
		logger.info("压缩包上传 成功 [{}|{}]", archive.getOriginalFilename(), path);
		/**生成FileInfo*/
		FileInfo obj = new FileInfo(null,archive.getOriginalFilename(), archive.getSize(), extension, path, null, this.getUserAccount(), Calendar.getInstance(), relatedType==null?0:relatedType, 0, Status.STATUS.getIndex(), Calendar.getInstance(), Calendar.getInstance());
		ModelResult<FileInfo> modelResult = fileInfoService.save(obj);
		obj = modelResult.getModel();
		jo.put("fileId", obj.getId()); // 关联的fileId
//		jo.put("path", path);
		jo.put("fileName", archive.getOriginalFilename());
		jo.put("success", true);
		return jo;
	}
	
	@RequestMapping(value = "kindeditor/upload-image" , method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadImageFromKindEditor(@RequestPart MultipartFile imgFile,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		logger.info("kindeditor图片上传 [{}]", imgFile.getOriginalFilename());
		// 校验文件类型
		String extension = FilenameUtils.getExtension(imgFile.getOriginalFilename());
		extension = StringUtils.lowerCase(extension);
		Matcher matcher = PATTERN_IMAGE_EXTENSION.matcher(extension);
		if (!matcher.matches()) {
			logger.warn("图片上传 格式不正确 [{}]", imgFile.getOriginalFilename());
			jo.put("error", 1);
			jo.put("message", "文件格式不正确");
			return jo;
		}
		String fileName = LocalDateTime.now().format(DTF_FILE_NAME);
		String pathChild =  fileName + "." + extension;
		String pathParent;
		String path = "img/kindeditor";
		pathParent = PATH_IMAGE + File.separator + path;
		path = "/" +path + "/" + pathChild;
		logger.info("图片上传 准备 [{}|{}]", imgFile.getOriginalFilename(), path);
		try {
			File img = new File(pathParent, pathChild);
			if (!img.exists()) {
				img.mkdirs();
				img.createNewFile();
			}
			// 转存文件
			imgFile.transferTo(img);
		} catch (IllegalStateException e) {
			logger.error("图片上传 转存图片 异常", e);
			jo.put("error", 1);
			jo.put("message", "转存图片异常");
			return jo;
		} catch (IOException e) {
			logger.error("图片上传 转存图片 异常", e);
			jo.put("error", 1);
			jo.put("message", "转存图片异常");
			return jo;
		}
		logger.info("图片上传 成功 [{}|{}]", imgFile.getOriginalFilename(), path);
		
		/**生成FileInfo*/
		FileInfo obj = new FileInfo(null,imgFile.getOriginalFilename(), imgFile.getSize(), extension, path, null, this.getUserAccount(), Calendar.getInstance(), 0, 0, Status.STATUS.getIndex(), Calendar.getInstance(), Calendar.getInstance());
		fileInfoService.save(obj);
		jo.put("error", 0);
		jo.put("url", NEWS_DOMAIN_RES+path);
		return jo;
	}
	
	@RequestMapping(value = "kindeditor/image-viewer", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject kindeditorImageViewer() {
		String path = "img/kindeditor";
		File directory = new File(PATH_IMAGE + File.separator + "/img/kindeditor");
		if (!directory.exists()) {
			logger.info("kindeditor图片空间目录不存在,absolutePath:{}",directory.getAbsolutePath());
			return null;
		}
		Collection<File> images = FileUtils.listFiles(directory, IMAGE_EXTENSIONS, false);
		JSONArray jArray = new JSONArray();
		if (images != null && images.size() > 0) {
			for (File image : images) {
				JSONObject jo = new JSONObject();
				jo.put("is_dir", false);
				jo.put("has_file", false);
				jo.put("filesize", image.length());
				jo.put("dir_path", "");
				jo.put("is_photo", true);
				jo.put("filetype", image.getName().split("\\.")[1]);
				jo.put("filename", image.getName());
				Calendar cd = Calendar.getInstance();
		        cd.setTimeInMillis(image.lastModified());
				jo.put("datetime", DateUtil.toOracleDateStr(cd));
				jArray.add(jo);
			}
		}
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", "");
		result.put("current_dir_path", "");
		result.put("current_url", NEWS_DOMAIN_RES+"/"+path+"/");
		result.put("total_count", images.size());
		result.put("file_list", jArray);
		return result;
	}
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		
		return "file/file-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<FileInfo> page,
			@ModelAttribute FileInfoOption qVo) {
		DataPage<FileInfo> dataPage = page.toDataPage();
		PageResult<FileInfo> pageResult = null;
		try {
			pageResult = fileInfoService.queryPage(dataPage, qVo);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		if (pageResult == null) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		dataPage = pageResult.getPage();
		return Page.returnPage(dataPage);
	}
	
	@RequestMapping("/viewJson")
	@ResponseBody
	public Object viewJson(Integer id){
		ModelResult<FileInfo> modelResult = fileInfoService.queryById(id);
		return modelResult.getModel();
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/download", method = RequestMethod.GET)
	public void detailOrEdit(@PathVariable Integer id,HttpServletRequest request, HttpServletResponse response) {
		
		ModelResult<FileInfo> result = fileInfoService.queryById(id);
		FileInfo fileInfo = result.getModel();
		if (!result.isSuccess() || fileInfo == null) {
			logger.info("根据数据库找不到相关的ID，id:{}",id);
			return;
		}
		String filePath = fileInfo.getPath();
        File file = new File(PATH_ATTACHMENT + filePath);
        if (!file.exists()) {
        	logger.error("附近不存在，id:{},路径：{}",id,PATH_ATTACHMENT + filePath);
        	return;
		}
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes()));
        response.addHeader("Content-Length", "" + file.length());

        ServletContext context = request.getServletContext();
        String mimeType = context.getMimeType(filePath);

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, response.getOutputStream());
            fileInputStream.close();
            response.flushBuffer();
        } catch (IOException e) {
            logger.error("文件下载:{}", e);
            try {
                fileInputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
	}
}
