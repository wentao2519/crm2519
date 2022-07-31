package com.waves.crm.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * @author huangWenTao
 * @desc 使用apache-poi生成excel文件
 * @date 2022/6/30 20:34
 */

public class CreateExcelTest {
    public static void main(String[] args) throws Exception{
        //创建HSSFWorkbook对象，对应一个excel文件
        HSSFWorkbook wb=new HSSFWorkbook();

        //生成HSSFCellStyle对象
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //使用wb创建HSSFSheet对象，对应wb文件中的一页
        HSSFSheet sheet=wb.createSheet("学生列表");
        //使用sheet创建HSSFRow对象，对应sheet中的一行
        HSSFRow row=sheet.createRow(0);//行号：从0开始,依次增加
        row.setRowStyle(style);
        //使用row创建HSSFCell对象，对应row中的列
        HSSFCell cell=row.createCell(0);//列的编号：从0开始，依次增加
        cell.setCellValue("学号");
        cell.setCellStyle(style);
        cell=row.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell=row.createCell(2);
        cell.setCellValue("年龄");
        cell.setCellStyle(style);


//生成HSSFCellStyle对象
//        HSSFCellStyle style=wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
        //使用sheet创建10个HSSFRow对象，对应sheet中的10行
        for(int i=1;i<=10;i++){
            row=sheet.createRow(i);
            row.setRowStyle(style);
            cell=row.createCell(0);//列的编号：从0开始，依次增加
            cell.setCellValue(100+i);
            cell.setCellStyle(style);
            cell=row.createCell(1);
            cell.setCellValue("NAME"+i);
            cell.setCellStyle(style);
            cell=row.createCell(2);
            cell.setCellValue(20+i);
            cell.setCellStyle(style);
        }

        //调用工具函数生成excel文件
        OutputStream os=new FileOutputStream("D:\\studentList.xls");//目录必须手动创建，文件如果不存在，poi会自动创建
        wb.write(os);

        //关闭资源
        os.close();
        wb.close();

        System.out.println("===========create ok==========");
    }
}
