package com.web.controller;


import com.web.entity.User;
import com.web.entity.Hesuan;
import com.web.service.IUserService;
import com.web.service.IHesuanService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import com.web.utils.Util;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

//核酸检测信息Controller层
@RestController
@RequestMapping("/hesuan")
public class HesuanController {

    @Autowired
    IHesuanService hesuanService;
    @Autowired
    private IUserService userService;

    //核酸检测信息信息列表
    @PostMapping("/searchList")
    public Result searchList(HttpServletRequest request, @RequestBody Map<String, Object> condition) {
        String token = request.getHeader("token");
        if(token.startsWith("3")){
            String userid = Util.parseToken(token);
            User user = userService.getById(userid);
            condition.put("sfz",user.getUsername());
        }
        PageUtils pageResult = hesuanService.getPage(condition);

        return Result.ok().put("pageResult",pageResult);

    }

    //添加核酸检测信息
    @PostMapping("/add")
    public Result add(@RequestBody Hesuan entity) {

        User user = userService.getUserByUsername(entity.getSfz());
        if(user==null){
            user = new User();
            user.setUsername(entity.getSfz());
            user.setName(entity.getXm());
            user.setPic("uploads/tx.png");
            user.setCtime(Util.parseTime(new Date()));
            user.setPassword(Util.getmd5("111111", "utf-8"));
            user.setStatus("正常");
            user.setRole(3);
            userService.save(user);
        }
        if("阳性".equals(entity.getJieguo())){
            entity.setStatus("未复核");
        }
        hesuanService.save(entity);
        return Result.ok("添加成功");
    }

    //通过id查核酸检测信息信息
    @GetMapping("/getById")
    public Result getById(@RequestParam("id") String id) {
        return Result.okAndData(hesuanService.getById(id));
    }

    //修改信息
    @PostMapping("/update")
    public Result update(@RequestBody Hesuan entity) {
        hesuanService.updateById(entity);
        return Result.ok("修改成功");
    }

    //删除信息
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") String id) {
        hesuanService.removeById(id);
        return Result.ok("删除成功");
    }

    //复核阳性
    @PostMapping("/update2")
    public Result update2(@RequestBody Hesuan entity) {
        entity.setStatus("复核阳性");
        entity.setFtime(Util.parseTime(new Date()));
        hesuanService.updateById(entity);
        return Result.ok("操作成功");
    }

    //复核阴性
    @PostMapping("/update3")
    public Result update3(@RequestBody Hesuan entity) {
        entity.setStatus("复核阴性");
        entity.setFtime(Util.parseTime(new Date()));
        hesuanService.updateById(entity);
        return Result.ok("操作成功");
    }

    @Value("${files.upload.path}")
    private String fileUploadPath;


    //导入核酸检测信息
    @PostMapping("/exceladd")
    public Result exceladd(@RequestBody Hesuan entity2) throws IOException {

        String excelpath = entity2.getExcelpath();

        String excelFile = fileUploadPath.replaceAll("uploads\\\\","")+excelpath;

        File file = new File(excelFile);
        InputStream is = new FileInputStream(file);
        //1、创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook(is);
        //2、获取表的数量并且遍历
        int totalSheet = 1;//获取共有多少个sheet

        XSSFSheet sheet = wb.getSheetAt(0);
        int totalRow = sheet.getPhysicalNumberOfRows();//总行数
        //3、遍历行

        int count = 0;
        for (int j = 1; j < totalRow; j++) {
            XSSFRow row = sheet.getRow(j);

            String sfz ="";
            //姓名
            String xm="";
            //采样地点
            String address="";
            //采样时间
            String ctime="";
            //采样机构
            String jigou="";
            //采样结果
            String jieguo="";
            //备注
            String beizhu="";

            Cell cell0 = row.getCell(0);
            if(cell0==null){
                continue;
            }
            cell0.setCellType(CellType.STRING);
            sfz = cell0.getStringCellValue();
            if(sfz.length()!=18){
                continue;
            }

            Cell cell1 = row.getCell(1);
            if(cell1==null){
                continue;
            }
            cell1.setCellType(CellType.STRING);
            xm = cell1.getStringCellValue();

            Cell cell2 = row.getCell(2);
            if(cell2==null){
                continue;
            }
            cell2.setCellType(CellType.STRING);
            address = cell2.getStringCellValue();

            Cell cell3 = row.getCell(3);
            if(cell3==null){
                continue;
            }
            cell3.setCellType(CellType.NUMERIC);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ctime = sdf.format(cell3.getDateCellValue());

            Cell cell4 = row.getCell(4);
            if(cell4==null){
                continue;
            }
            cell4.setCellType(CellType.STRING);
            jigou = cell4.getStringCellValue();

            Cell cell5 = row.getCell(5);
            if(cell5==null){
                continue;
            }
            cell5.setCellType(CellType.STRING);
            jieguo = cell5.getStringCellValue();
            if(!"阳性".equals(jieguo) &&!"阴性".equals(jieguo)){
                continue;
            }

            Cell cell6 = row.getCell(6);
            if(cell6==null){
                beizhu ="";
            }else{
                cell6.setCellType(CellType.STRING);
                beizhu = cell6.getStringCellValue();
            }


            Hesuan bean =  new Hesuan();

            bean.setSfz(sfz);
            bean.setXm(xm);
            bean.setAddress(address);
            bean.setCtime(ctime);
            bean.setJigou(jigou);
            bean.setJieguo(jieguo);
            bean.setBeizhu(beizhu);

            User user = userService.getUserByUsername(bean.getSfz());
            if(user==null){
                user = new User();
                user.setUsername(bean.getSfz());
                user.setName(bean.getXm());
                user.setPic("uploads/tx.png");
                user.setCtime(Util.parseTime(new Date()));
                user.setPassword(Util.getmd5("111111", "utf-8"));
                user.setStatus("正常");
                user.setRole(3);
                userService.save(user);
            }
            if("阳性".equals(bean.getJieguo())){
                bean.setStatus("未复核");
            }
            hesuanService.save(bean);
            count++;
        }

        return Result.ok("操作成功，导入"+count+"条记录");
    }


    @PostMapping("/excelout")
    public Result excelout(@RequestBody Map<String, Object> condition){
        //获取数据
        condition.put("limit",65000);
        PageUtils pageResult = hesuanService.getPage(condition);

        List<Hesuan> hesuanlist = (List<Hesuan>)pageResult.getList();

        //创建excel工作表
        Workbook workbook =new XSSFWorkbook();
        Sheet studentsheet = workbook.createSheet("核酸检测信息表");
        Row row = studentsheet.createRow(0);
        String[] title= {"身份证","姓名","采样地点","采样时间","采样机构","采样结果","备注"};
        for (int i = 0; i < 7; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }

        for (int i = 1; i < hesuanlist.size()+1; i++) {
            Hesuan bean = hesuanlist.get(i - 1);
            Row row1 = studentsheet.createRow(i);
            Cell cell0 = row1.createCell(0);
            cell0.setCellValue(bean.getSfz());
            Cell cell1 = row1.createCell(1);
            cell1.setCellValue(bean.getXm());
            Cell cell2 = row1.createCell(2);
            cell2.setCellValue(bean.getAddress());
            Cell cell3 = row1.createCell(3);
            cell3.setCellValue(bean.getCtime());
            Cell cell4 = row1.createCell(4);
            cell4.setCellValue(bean.getJigou());
            Cell cell5 = row1.createCell(5);
            cell5.setCellValue(bean.getJieguo());
            Cell cell6 = row1.createCell(6);
            cell6.setCellValue(bean.getBeizhu());
        }
        InputStream excelStream = null;

        String fileName = "核酸检测信息表("+Util.parseTime2(new Date())+").xlsx";

        String url = "uploads/"+fileName;

        String path=fileUploadPath+fileName;

        try {//这里就是io流的转换  WorkBook需要写入一个输出流 阿里云oss保存文件需要一个输入流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            out.close();
            excelStream= new ByteArrayInputStream(out.toByteArray());
            workbook.close();

            File file = new File(path);

            FileOutputStream fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            while ((excelStream.read(b)) != -1) {
                // 写入数据
                fos.write(b);
            }
            excelStream.close();
            // 保存数据
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Result result = new Result();
        result.put("url", url);
        return result;
    }




}
