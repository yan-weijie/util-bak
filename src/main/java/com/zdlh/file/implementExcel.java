package com.zdlh.file;

import jxl.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/10.
 */
public class implementExcel {

    /**
     * wb=Workbook.getWorkbook(in);
     wwb=wb.createWorkbook(new File(path),wb);
     //0，第一个sheet
     WritableSheet ws=wwb.getSheet(0);
     或者wwb.getSheet("xxx");  xxx为sheet的名称。
     或者wwb.getSheets();得到一个sheet数组。
     * @throws Exception
     */
    @Test
    public static void writeExcel(String filePath, List<Map<String, String>> listMap) {
        try {
            Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\Administrator\\Desktop\\test.xls"));
            File file = new File("C:\\Users\\Administrator\\Desktop\\test.xls");


            WritableWorkbook wb = Workbook.createWorkbook(file,workbook);

            WritableSheet sheet = wb.getSheet(0);
            System.out.println("sheet:"+sheet.getName());
            System.out.println("Column:"+sheet.getColumns());
            System.out.println("Row:"+sheet.getRows());
            Cell b8 =  sheet.getCell(1, 7);

            for (int i=0; i < listMap.size(); i++) {
                listMap.remove(i);
                sheet.addCell(new Label(1, i, listMap.get(i).get("")));
            }

            sheet.addCell(new Label(1, 7, "爱仕达无"));

            wb.write();
            wb.close();
            workbook.close();
        }catch (Exception e)  {

        }

    }

    /**
     * 读取指定路径下的文件，具体要到某个文件(filePath)，指定sheet(sheet)中的excel表格中每一行，将放list中，返回
     * @param filePath
     * @param sheetname
     * @return
     * @throws IOException
     * @throws JXLException
     */
    public static List<Map<String, String>> readExcel(String filePath,String sheetname) throws IOException, JXLException {
        //声明一个数组，用于存放sheet中的值，长度根据sheet中的实际长度定义
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            //输入流，声明读取变量is 用户读取excel表格
            InputStream is=null;
            //构建Workbook对象
            Workbook workbook=null;
            //传入filepath中描述的路径，实例化is。
            is=new FileInputStream(filePath);
            //将is中读取到的excel表格赋值给workbook
            workbook=Workbook.getWorkbook(is);
            //声明变量sheet，将workbook中的sheet名为sheetname的表格读取到sheet中
            Sheet sheet=workbook.getSheet(sheetname);
            //通过两层循环，将sheet中的数据读取到数组中
            //遍数组的历行
            for(int i=0;i<sheet.getRows();i++){
                Map<String, String> map = new HashMap<String, String>();
                //遍数组的历列
                for(int j=0;j<sheet.getColumns();j++){
                    //声明一个单元格变量，通过getCell(j,i)得到单元格中的值，读取每列每行，getCell(j,i)前面的j是列，i是行
                    Cell cellA=sheet.getCell(j,0);//只读取第一行列名 设置成map键值
                    Cell cellB=sheet.getCell(j,i);
                    map.put(cellA.getContents(), cellB.getContents());
                    //System.out.println(cellA.getContents()+"|"+cellB.getContents());
                }
                list.add(map);
            }
            //将excel表关闭
            workbook.close();
            //将输入流关闭
            is.close();

        }catch (IOException e) {
            e.printStackTrace();
        }catch (JXLException e) {
            e.printStackTrace();
        }

        //返回已经读取到sheet数据的list
        return list;
    }

    /**
     * 定义字体和格式
     * @return
     */
    public static WritableCellFormat getHeader() {
        // 定义字体
        WritableFont font = new WritableFont(WritableFont.TIMES, 10,
                WritableFont.BOLD);
        try {
            // 黑色字体
            font.setColour(jxl.format.Colour.BLACK);
        } catch (WriteException e1) {
            e1.printStackTrace();
        }
        WritableCellFormat format = new WritableCellFormat(font);
        try {
            // 左右居中
            format.setAlignment(jxl.format.Alignment.CENTRE);
            // 上下居中
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            // 黑色边框
            format.setBorder(Border.ALL, BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            // 黄色背景
            format.setBackground(jxl.format.Colour.YELLOW);
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 读
     * @throws IOException
     * @throws JXLException
     */
    @Test
    public void read () throws IOException, JXLException {

        List<Map<String, String>> asd= readExcel("C:\\Users\\Administrator\\Desktop\\test.xls", "test");

        for (int i=0;i<asd.size();i++){
            System.out.println(asd.get(i).get("id"));
            System.out.println(asd.get(i).get("user"));
            System.out.println(asd.get(i).get("pw"));

        }
    }
}
