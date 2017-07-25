package com.ejuzuo.web.controller;

import com.aicai.appmodel.domain.result.ModelResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.ImageBusinessType;
import com.ejuzuo.common.constants.PayStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.domain.FileInfo;
import com.ejuzuo.common.option.DownloadInfoOption;
import com.ejuzuo.common.service.*;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.FileUtils;
import com.ejuzuo.common.util.PicUtil;
import com.ejuzuo.common.util.UUIDUtils;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.util.ExcelUtil;
import com.ejuzuo.web.util.ResultTypeEnum;
import com.ejuzuo.web.vo.MemberVo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/3/31 0031.
 */

@Controller
@RequestMapping("file")
public class FileUploadDownLoadController extends BaseController {

    private static String [] headers = {"序号","套件名称","品牌","图片","单件名称","产品尺寸","材质","价格（元）"};

    @Resource
    private DownloadInfoService downloadInfoService;
    @Resource
    private FileInfoService fileInfoService;
    @Resource(name = "digitalFurnitureServiceClient")
    private DigitalFurnitureService digitalFurnitureService;
    @Resource
    private MemberService memberService;

    private static final Integer cellHeight = 80 ;


    @Resource
    private BrandService brandService ;

    @Login(ResultTypeEnum.json)
    @RequestMapping("/upload")
    @ResponseBody
    public JSONObject upload(MultipartFile file, Integer index, HttpServletRequest request) throws IOException {
        JSONObject reJson = new JSONObject();
        try {
            ImageBusinessType type = ImageBusinessType.getByIndex(index);
            if (type == null) {
                type = ImageBusinessType.others;
            }
            String path = FileUtils.saveAdImg(globalParam.getFileRootPublic(), type, file);

            reJson.put("code", "200");
            if (type.getIndex() == ImageBusinessType.memberImg.getIndex()) {
                JSONObject pathObject = new JSONObject();
                pathObject.put("picold", path);
                pathObject.put("pic4343", PicUtil.compressPic(globalParam.getFileRootPublic(), path, 43, 43));
                pathObject.put("pic7675", PicUtil.compressPic(globalParam.getFileRootPublic(), path, 76, 75));
                reJson.put("path", pathObject);
            } else if (type.getIndex() == ImageBusinessType.designerCoverImg.getIndex()) {
                JSONObject pathObject = new JSONObject();
                pathObject.put("picold", path);
                pathObject.put("pic500500", PicUtil.compressPic(globalParam.getFileRootPublic(), path, 500, 500));
                //pathObject.put("pic315315", PicUtil.compressPic(globalParam.getFileRootPublic(), path, 315, 315));
                //先验收演示，直接用height和width就行了，不要创建这么多图片。
                pathObject.put("pic280280", PicUtil.compressPic(globalParam.getFileRootPublic(), path, 280, 280));
                pathObject.put("pic4343", PicUtil.compressPic(globalParam.getFileRootPublic(), path, 43, 43));
                reJson.put("path", pathObject);
            } else if (type.getIndex() == ImageBusinessType.designerWorkCoverImg.getIndex()){
                JSONObject pathObject = new JSONObject();
                pathObject.put("picold", path);
                pathObject.put("pic280280", PicUtil.compressPic(globalParam.getFileRootPublic(), path, 280, 280));
                reJson.put("path", pathObject);
            } else {
                reJson.put("path", path);
            }

            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getOriginalFilename());
            fileInfo.setPath(path);
            fileInfo.setSize(file.getSize());
            fileInfo.setUploader(getAccount(request));
            fileInfo.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
            fileInfo.setRelatedId(0);
            fileInfo.setRelatedType(0);
            fileInfoService.save(fileInfo);
            return reJson;
        } catch (Exception e) {
            reJson.put("code", "400");
            reJson.put("msg", e.getMessage());
        }

        return reJson;
    }

    @Login(ResultTypeEnum.json)
    @RequestMapping("/uploadP")
    @ResponseBody
    public JSONObject uploadPrivate(MultipartFile file, Integer index, HttpServletRequest request) throws IOException {
        JSONObject reJson = new JSONObject();
        try {
            ImageBusinessType type = ImageBusinessType.getByIndex(index);
            if (type == null) {
                type = ImageBusinessType.others;
            }
            String path = FileUtils.saveAdImg(globalParam.getFileRootPrivate(), type, file);
            reJson.put("code", "200");
            reJson.put("path", path);

            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getOriginalFilename());
            fileInfo.setPath(path);
            fileInfo.setSize(file.getSize());
            fileInfo.setUploader(getAccount(request));
            fileInfo.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
            fileInfo.setRelatedId(0);
            fileInfo.setRelatedType(0);
            fileInfoService.save(fileInfo);
            return reJson;
        } catch (Exception e) {
            reJson.put("code", "400");
            reJson.put("msg", e.getMessage());
        }

        return reJson;
    }

    /**
     * 用户中心-我的下载
     * 下载单个文件
     *
     * @param id
     */
    @Login(ResultTypeEnum.json)
    @RequestMapping("/downloadValid/{id}")
    @ResponseBody
    public JSONObject download(@PathVariable Integer id, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();        
    	MemberVo member = this.getMember(request);  
    	if (member == null) {
            logger.info("用户未登录, 不能下载！");
            jsonObject.put("code", 444);
            jsonObject.put("msg", "用户未登录, 不能下载！");
            return jsonObject;
    	}    	
        //走验证逻辑
        ModelResult<DownloadInfo> modelResult = downloadInfoService.judgeAvailableFile(id);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }
        DownloadInfo downloadInfo = modelResult.getModel();
        //判断是否当前用户购买的，谢明媚增加
        if(!member.getId().equals(downloadInfo.getMemberId())) {
            logger.info("用户:[{}]不是原始支付用户，不能下载数字家居模型[{}]", member.getId(), downloadInfo.getFileId() );
            jsonObject.put("code", 444);
            jsonObject.put("msg", "不是原始支付用户，不能下载！");
            return jsonObject;       	
        }
        

        FileInfo fileInfo = fileInfoService.queryById(downloadInfo.getFileId()).getModel();
        //File file = new File( fileInfo.getPath());
        String filePath = fileInfo.getPath();
        File file = new File(globalParam.getFileRootPrivate() + filePath);
        if (!file.exists()) {
            logger.info("文件id:[{}],绝对路径:[{}] 该文件不存在", fileInfo.getId(), file.getAbsolutePath());
            jsonObject.put("code", 444);
            jsonObject.put("msg", "文件不存在");
            return jsonObject;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("/file/downloadFile/" + id);
        jsonObject.put("code", 222);
        jsonObject.put("model", sb.toString());
        return jsonObject;
    }


    @Login(ResultTypeEnum.json)
    @RequestMapping("/downloadFile/{id}")
    public void downloadFile(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
    	MemberVo member = this.getMember(request);  
    	if (member == null) {
            logger.info("用户未登录, 不能下载！");
            return;
    	}
        //走验证逻辑
        ModelResult<DownloadInfo> modelResult = downloadInfoService.judgeAvailableFile(id);
        if (!modelResult.isSuccess()) {
            logger.info("用户:[{}], 获取逻辑异常", member.getAccount());
            return;
        }
        DownloadInfo downloadInfo = modelResult.getModel();
        //判断是否当前用户购买的，谢明媚增加
        if(!member.getId().equals(downloadInfo.getMemberId())) {
            logger.info("用户:[{}]不是原始支付用户，不能下载数字家居模型[{}]", member.getId(), downloadInfo.getFileId() );
            return;        	
        }

        FileInfo fileInfo = fileInfoService.queryById(downloadInfo.getFileId()).getModel();
        //String filePath = "D:\\res\\nimei.zip";
        String filePath = fileInfo.getPath();
        File file = new File(globalParam.getFileRootPrivate() + filePath);
        //File file = new File(filePath);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes()));
        response.addHeader("Content-Length", "" + file.length());

        ServletContext context = request.getServletContext();
        String mimeType = context.getMimeType(filePath);

        digitalFurnitureService.updateDownloadCount(downloadInfo.getGoodsId());

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


    /**
     * 商品详情页面 立即下载
     *
     * @return
     */
    @Login(ResultTypeEnum.json)
    @RequestMapping("/downloadNow/{goodsId}")
    @ResponseBody
    public JSONObject downloadNow(@PathVariable Integer goodsId, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        
    	MemberVo member = this.getMember(request);  
    	if (member == null) {
            logger.info("用户未登录, 不能下载！");
            jsonObject.put("code", 444);
            jsonObject.put("msg", "用户未登录, 不能下载！");
            return jsonObject;
    	}    	

        ModelResult<DigitalFurniture> modelResult = digitalFurnitureService.queryById(goodsId);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

        DigitalFurniture digitalFurniture = modelResult.getModel();

        //判断是否已经支付过并且在有效期内
        DownloadInfoOption downloadInfoOption = new DownloadInfoOption();
        downloadInfoOption.setGoodsId(digitalFurniture.getId());
        downloadInfoOption.setMemberId(Long.valueOf(member.getId()));
        downloadInfoOption.setPayStatus(PayStatus.PAY_SUCCESS.getIndex());
        downloadInfoOption.setStatus(Status.STATUS.getIndex());
        downloadInfoOption.setNowTime(Calendar.getInstance());
        ModelResult<DownloadInfo> downloadInfoModelResult = downloadInfoService.queryByOption(downloadInfoOption);
        if (!downloadInfoModelResult.isSuccess()) {
            logger.info("用户:[{}]查询数字家具[{}]的下载订单:异常[{}]", downloadInfoOption.getMemberId(), digitalFurniture.getId(), downloadInfoModelResult.getErrorMsg());
            jsonObject.put("code", 444);
            jsonObject.put("msg", downloadInfoModelResult.getErrorMsg());
            return jsonObject;
        }

        DownloadInfo downloadInfo = downloadInfoModelResult.getModel();

        if( downloadInfo != null){
            //判断是否当前用户购买的，谢明媚增加
            if(!member.getId().equals(downloadInfo.getMemberId())) {
                logger.info("用户:[{}]不是原始支付用户，不能下载数字家居模型[{}]", member.getId(), downloadInfo.getFileId() );
                jsonObject.put("code", 444);
                jsonObject.put("msg", "不是原始支付用户，不能下载！");
                return jsonObject;
            }

            if (downloadInfo !=null && downloadInfo.getExpire().compareTo(Calendar.getInstance()) > 0) {
                logger.info("用户:[{}],还有在有效期内的数字家具[{}]下载订单，不用支付", downloadInfo.getMemberId(), digitalFurniture.getId());
                jsonObject.put("code", 444);
                jsonObject.put("msg", "直接在用户中心去下载");
                return jsonObject;
            }

            FileInfo fileInfo = fileInfoService.queryById(digitalFurniture.getFileId()).getModel();
            File file = new File(fileInfo.getPath());
            if (!file.exists()) {
                logger.info("文件id:[{}],绝对路径:[{}] 该文件不存在", fileInfo.getId(), file.getAbsolutePath());
                jsonObject.put("code", 444);
                jsonObject.put("msg", "文件不存在");
                return jsonObject;
            }

            //普通用户5次/天，高级用户10次/天，vip20次/天。 begin
            DownloadInfoOption query = new DownloadInfoOption();
            query.setMemberId(Long.valueOf(member.getId()));
            query.setPayStatus(PayStatus.PAY_SUCCESS.getIndex());
            query.setStatus(Status.STATUS.getIndex());
            query.setPayBeginTime(DateUtil.getTheDayZero());
            query.setPayEndTime(DateUtil.getTheDayMidnight());
            Integer count = downloadInfoService.countByPage(query);            
            
            boolean canDownload = false;
            int limit = 5;
          	if(memberService.isDesingerByMemberId(member.getId())){
          		limit = 10;
        	}	
            if(memberService.isVipByMemberId(member.getId())) { 
            	limit = 20;
            }
            if (count < limit) {
               canDownload = true;
            }
            if (!canDownload) {                  
               logger.info("下载频次超限！");
               jsonObject.put("code", 444);
               //下载频次超限：普通用户5次/天，高级用户10次/天，vip用户20次/天。
               jsonObject.put("msg", "您好！您已超出今日的下载次数，普通用户5次/天，高级用户10次/天，vip用户20次/天！");
               return jsonObject; 
            }            
            //end
        }

      
        downloadInfo = new DownloadInfo();
        downloadInfo.setFileId(digitalFurniture.getFileId());
        downloadInfo.setGoodsId(digitalFurniture.getId());
        downloadInfo.setMemberId(getMemberId(request));
        downloadInfo.setPointPrice(new BigDecimal(digitalFurniture.getPointPrice()));
        downloadInfo.setActualPrice(new BigDecimal(digitalFurniture.getPointPrice()));
        ModelResult<DownloadInfo> result = downloadInfoService.createDownloadInfoAndPay(downloadInfo);
        if (!result.isSuccess()) {
            logger.info("用户:[{}] 生成订单并支付: 异常:[{}]", downloadInfo.getMemberId(), result.getErrorMsg());
            jsonObject.put("code", 444);
            jsonObject.put("msg", result.getErrorMsg());
            return jsonObject;
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "支付成功");
        jsonObject.put("model", result.getModel().getId());
        return jsonObject;
    }

    @Login(ResultTypeEnum.json)
    @RequestMapping("/downloadExcel/{ids}")
    @ResponseBody
    public void createExcelDownload(HttpServletRequest request, HttpServletResponse response, @PathVariable String ids) {
        JSONObject resultJSON = new JSONObject();
        try {
            if (StringUtils.isBlank(ids)){
                resultJSON.put("status", "fail");
                resultJSON.put("error", "参数为空");
                response.getWriter().print(resultJSON);
                return;
            }

            String [] idArray = ids.split(",");
            List<Integer> idList = new ArrayList<>();
            for (String id : idArray){
                idList.add(Integer.valueOf(id));
            }

            ModelResult<List<DigitalFurniture>> digitalFurnitureListResult = digitalFurnitureService.queryByIds(idList);
            if(!digitalFurnitureListResult.isSuccess()){
                resultJSON.put("status", "fail");
                resultJSON.put("error", digitalFurnitureListResult.getErrorMsg());
                response.getWriter().print(resultJSON);
                return;
            }

            List<DigitalFurniture> digitalFurnitureList = digitalFurnitureListResult.getModel();


            Workbook workbook = new XSSFWorkbook();   // OR XSSFWorkbook
            CellStyle style = workbook.createCellStyle();
            style.setVerticalAlignment(VerticalAlignment.CENTER.getCode());
            style.setAlignment(HorizontalAlignment.LEFT.getCode());

            Sheet sheet = workbook.createSheet("购物列表");

            Row headerRow = sheet.createRow(0);
            for (int i =0; i<headers.length; i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellStyle(style);
                sheet.setColumnWidth(i, 256*20);
                cell.setCellValue(headers[i]);
            }
            int totalRow = 1;
            for (int j =1; j<= digitalFurnitureList.size(); j++){
                DigitalFurniture digitalFurniture = digitalFurnitureList.get(j-1);

                JSONArray jsonArray = JSON.parseArray(digitalFurniture.getSpecification());

                Row row = null ;
                if ( jsonArray!=null && jsonArray.size() > 1 ){

                    CellRangeAddress cellRangeAddressA = new CellRangeAddress(totalRow, totalRow+ jsonArray.size() -1,0, 0);
                    CellRangeAddress cellRangeAddressB = new CellRangeAddress(totalRow, totalRow+ jsonArray.size() -1,1, 1);
                    CellRangeAddress cellRangeAddressC = new CellRangeAddress(totalRow, totalRow+ jsonArray.size() -1,2, 2);
                    CellRangeAddress cellRangeAddressD = new CellRangeAddress(totalRow, totalRow+ jsonArray.size() -1,3, 3);

                    for (int k=0; k< jsonArray.size(); k++){
                        Row tempRow = sheet.createRow(totalRow+k);
                        tempRow.setHeightInPoints(cellHeight);

                        JSONObject item = jsonArray.getJSONObject(k);
                        Cell cellF = tempRow.createCell(4);
                        cellF.setCellValue(item.getString("name"));
                        Cell cellG = tempRow.createCell(5);
                        cellG.setCellValue(item.getString("size"));
                        Cell cellH = tempRow.createCell(6);
                        cellH.setCellValue(item.getString("material"));
                        Cell cellI = tempRow.createCell(7);
                        cellI.setCellValue(item.getString("price"));
                    }

                    sheet.addMergedRegion(cellRangeAddressA);
                    sheet.addMergedRegion(cellRangeAddressB);
                    sheet.addMergedRegion(cellRangeAddressC);
                    sheet.addMergedRegion(cellRangeAddressD);

                    row = sheet.getRow(totalRow);
                } else  {
                    row = sheet.createRow(totalRow);
                    row.setHeightInPoints(cellHeight);
                    if(jsonArray != null && jsonArray.size() ==1){
                        JSONObject item = jsonArray.getJSONObject(0);
                        Cell cellF = row.createCell(4);
                        cellF.setCellValue(item.getString("name"));
                        Cell cellG = row.createCell(5);
                        cellG.setCellValue(item.getString("size"));
                        Cell cellH = row.createCell(6);
                        cellH.setCellValue(item.getString("material"));
                        Cell cellI = row.createCell(7);
                        cellI.setCellValue(item.getString("price"));
                    }
                }

                Cell A = row.createCell(0);
                A.setCellValue(j);
                A.setCellStyle(style);
                Cell B = row.createCell(1);
                B.setCellValue(digitalFurniture.getName());
                B.setCellStyle(style);
                Cell C = row.createCell(2);
                C.setCellStyle(style);
                C.setCellValue(brandService.selectById(digitalFurniture.getBrand()).getModel().getName());
                Cell D = row.createCell(3);
                D.setCellStyle(style);
                ExcelUtil.addImageToExcel(sheet, digitalFurniture.getThumbnail(), "D"+(row.getRowNum()+1));

                int tempRowNum =  jsonArray == null ? 1 : jsonArray.size() ;
                totalRow = totalRow + tempRowNum;
            }

            File excelFileDirectory = new File(globalParam.getDownLoadExcel());
            if(!excelFileDirectory.exists()){
                excelFileDirectory.mkdirs();
            }
            File excelFile = new File(globalParam.getDownLoadExcel()+UUIDUtils.getUuidStr()+".xlsx");
            FileOutputStream fileOutputStream = new FileOutputStream(excelFile);
            workbook.write(fileOutputStream);

            response.addHeader("Content-Disposition", "attachment;filename=" + UUIDUtils.getUuidStr()+".xlsx");
            response.addHeader("Content-Length", excelFile.length()+"");
            response.setContentType("application/vnd.ms-excel");
            workbook.write(response.getOutputStream());
            response.flushBuffer();
            workbook.close();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            File excelFileDirectory = new File(globalParam.getDownLoadExcel());
            File [] files = excelFileDirectory.listFiles();
            for (int i =0; i< files.length ; i++){
                files[i].delete();
            }
        }
    }


    public static void main(String[] args) throws IOException {

        Workbook workbook = new XSSFWorkbook();   // OR XSSFWorkbook
        Sheet sheet = workbook.createSheet("购物列表");
        Row headerRow = sheet.createRow(0);
        for (int i =0; i<headers.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int totalRow =1 ;

        for (int k =0; k<1; k++){
            Row tempRow = sheet.createRow(totalRow+k);
            Cell cellE = tempRow.createCell(4);
            cellE.setCellValue("规格1");
            Cell cellF = tempRow.createCell(5);
            cellF.setCellValue("单价规格");
            Cell cellG = tempRow.createCell(6);
            cellG.setCellValue("产品尺寸");
            Cell cellH = tempRow.createCell(7);
            cellH.setCellValue("材质");
            Cell cellI = tempRow.createCell(8);
            cellI.setCellValue("价格");
        }

        CellRangeAddress cellRangeAddressA = new CellRangeAddress(totalRow, totalRow+ 2,0, 0);
        CellRangeAddress cellRangeAddressB = new CellRangeAddress(totalRow, totalRow+ 2,1, 1);
        CellRangeAddress cellRangeAddressC = new CellRangeAddress(totalRow, totalRow+ 2,2, 2);
        CellRangeAddress cellRangeAddressD = new CellRangeAddress(totalRow, totalRow+ 2,3, 3);


        sheet.addMergedRegion(cellRangeAddressA);
        sheet.addMergedRegion(cellRangeAddressB);
        sheet.addMergedRegion(cellRangeAddressC);
        sheet.addMergedRegion(cellRangeAddressD);

        Row secondRow = sheet.getRow(1);
        Cell cellA = secondRow.createCell(0);
        cellA.setCellValue(1);
        Cell cellB = secondRow.createCell(1);
        cellB.setCellValue("套件名称");
        Cell cellC = secondRow.createCell(2);
        cellC.setCellValue("品牌");
        Cell cellD = secondRow.createCell(3);
        ExcelUtil.addImageToExcel(sheet, "/img/digital_furniture/2016/05/21/20160521163500046-small.jpg", "D"+(secondRow.getRowNum()+1));
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\"+UUIDUtils.getUuidStr()+".xlsx"));

        workbook.write(fileOutputStream);
    }

}
