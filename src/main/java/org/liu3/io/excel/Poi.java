package org.liu3.io.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

/**
 * @Author: liutianshuo
 * @Date: 2025/5/8
 */
public class Poi {

    public static void test() throws Exception {


        Workbook wb = new SXSSFWorkbook();//new SXSSFWorkbook();
        Sheet sheet = wb.createSheet();

        DataFormat format = wb.createDataFormat();


        IndexedColors[] colors = IndexedColors.values();
        Row row = sheet.createRow(0);
        for (int i = 0; i < colors.length; i++) {
            IndexedColors color = colors[i];
            CellStyle style = wb.createCellStyle();
            style.setDataFormat(format.getFormat("@"));
            style.setFillForegroundColor(color.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            String textStr = color.name();
            XSSFRichTextString text = new XSSFRichTextString(textStr);
            cell.setCellValue(text);
        }
        wb.write(new FileOutputStream(new File("./aaa.xlsx")));
    }

    /**
     * description: 依据excel模板，导出数据excel
     *
     * @param sheetName sheet 页名称
     * @param headers   数据header部分
     * @param dataList  table 数据集合
     * @param destFile  excel模板文件路径
     */
    public static void export(String sheetName, String[] headers, List<List<Object>> dataList, File destFile) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        createSheet(sheetName, headers, dataList, workbook);
        workbook.write(new FileOutputStream(destFile));
    }


    /**
     * 根据header与data的集合，动态地创建并生成excel数据流进行下载操作
     * description: 导出excel --- 支持web
     *
     * @param sheetName sheet表名字
     * @param headers   表头
     * @param dataList  表数据
     * @param fileName  导出文件名
     * @param response
     */
    public static void export(String sheetName, String[] headers, List<List<Object>> dataList, String fileName
            , HttpServletResponse response) throws Exception {
        Workbook workbook = new SXSSFWorkbook();
        createSheet(sheetName, headers, dataList, workbook);
        response.reset();
        //response.setContentType("application/vnd.ms-excel; charset=utf-8");
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        workbook.write(response.getOutputStream());
        // 删除临时文件,SXSSFWorkbook 需要
        //workbook.dispose();
    }

    /**
     * description: 创建sheet表格
     *
     * @param sheetName 表sheet 名字
     * @param headers   表头
     * @param dataList  表数据
     * @param wb
     */
    public static void createSheet(String sheetName, String[] headers, List<List<Object>> dataList, Workbook wb) {
        Sheet sheet = wb.createSheet(sheetName);
        // 设置表头和单元格格式
        CellStyle headStyle = setHeaderStyle(wb);
        CellStyle bodyStyle = setBodyStyle(wb);
        // 创建表头和单元格数据
        createHeader(headers, sheet, headStyle);
        createBody(dataList, sheet, bodyStyle, wb);
    }

    /**
     * description: 创建表头
     *
     * @param headers
     * @param sheet
     * @param headStyle
     */
    private static void createHeader(String[] headers, Sheet sheet, CellStyle headStyle) {
        Row row = sheet.createRow(0);
        row.setHeightInPoints(16F);
        for (int i = 0; i < headers.length; i++) {
            // 创建单元格
            Cell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            //sheet.trackAllColumnsForAutoSizing();
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * description: 表格中填充数据
     *
     * @param dataList
     * @param sheet
     * @param bodyStyle
     * @param wb        主要是更改文本格式
     */
    private static void createBody(List<List<Object>> dataList, Sheet sheet, CellStyle bodyStyle, Workbook wb) {
        if (dataList == null) {
            return;
        }
        for (int i = 0; i < dataList.size(); i++) {
            // 从第二行开始，第一行做表头
            Row row = sheet.createRow(i + 1);
            List<Object> rowList = dataList.get(i);
            if (rowList == null) {
                continue;
            }
            for (int j = 0; j < rowList.size(); j++) {
                Cell cell = row.createCell(j);
                Object data = rowList.get(j);
                // 如果数据是  Double 类型，则需要保证金额数转换，否则导出数据显示为科学计数法  如： 8E7
                if (data instanceof Double) {
                    DataFormat format = wb.createDataFormat();
                    bodyStyle.setDataFormat(format.getFormat("#,##0.00")); // 千位符
                    // bodyStyle.setDataFormat(format.getFormat("#0.00")); // 小数
                    // bodyStyle.setDataFormat(format.getFormat("0.00%")); // 百分比  数据必须是小数，如：0.59 -> 59%
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(Double.parseDouble(String.valueOf(data)));
                } else {
                    String textStr = Optional.ofNullable(rowList.get(j)).orElse("").toString();
                    XSSFRichTextString text = new XSSFRichTextString(textStr);
                    cell.setCellValue(text);
                }

                //sheet.trackAllColumnsForAutoSizing();
                //设置内容列为列的最大值
                sheet.autoSizeColumn(j);
            }
        }
    }

    /**
     * description: 设置单元格内容样式
     *
     * @param wb
     * @return HSSFCellStyle
     */
    private static CellStyle setBodyStyle(Workbook wb) {
        // 设置表格单元格格式
        CellStyle style = wb.createCellStyle();
//        style.setFillForegroundColor(HSSFColor.WHITE.index);
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        // 设置字体格式
        Font font = wb.createFont();
        font.setFontName("宋体");
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style.setFont(font);
        return style;
    }

    /**
     * description: 设置表头样式
     *
     * @param wb
     * @return
     * @return HSSFCellStyle
     */
    private static CellStyle setHeaderStyle(Workbook wb) {
        // 设置表格单元格格式
        CellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//		style.setFillBackgroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置字体格式
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        return style;
    }
}
