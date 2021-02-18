package com.dongdl.springboot1.util;

import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.MessageException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/4/9 10:33 UTC+8
 * @description
 **/
public class OfficeUtil {


    private static final int EXCEL2007_ROW_LIMIT = 1000000;
    private static final String EXCEL2007_FILE_SUFFIX = ".xlsx";

    private static final String CHARSET_GB2312 = "gb2312";
    private static final String CHARSET_ISO8859_1 = "ISO8859-1";

    private static CellStyle CELL_STYLE = null;

    /**
     * web excel下载
     *
     * @param list      数据库list数据
     * @param linkedMap put("数据库表字段"，"表头名称")
     * @param rowLimit  每张sheet表数据行数限制 默认1000000
     * @param fileName  excel文件名
     * @param resp
     */
    public static void exportExcel(List<Map<String, Object>> list, LinkedHashMap<String, String> linkedMap, int rowLimit, String fileName, HttpServletResponse resp) {
        valid(list, linkedMap);
        try {
            resp.addHeader("Content-Disposition", "attachment;fileName=" + new String(String.format("%s%s", fileName, EXCEL2007_FILE_SUFFIX).getBytes(CHARSET_GB2312), CHARSET_ISO8859_1));
            OutputStream os = resp.getOutputStream();
            writeWorkbook(list, linkedMap, rowLimit, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportExcel(List<Map<String, Object>> list, LinkedHashMap<String, String> linkedMap, int rowLimit, File outputFile) {
        valid(list, linkedMap);
        try {
            OutputStream os = new FileOutputStream(outputFile);
            writeWorkbook(list, linkedMap, rowLimit, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void valid(List<Map<String, Object>> list, LinkedHashMap<String, String> linkedMap) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("无查询数据");
        }
        if (linkedMap == null || linkedMap.isEmpty()) {
            throw new IllegalArgumentException("至少要有一项查询列");
        }
        if (linkedMap.size() > 16384) {
            throw new IllegalArgumentException(String.format("EXCEL 2007+ 最大列限制16384列：当前%d列", linkedMap.size()));
        }
    }

    private static void writeWorkbook(List<Map<String, Object>> list, LinkedHashMap<String, String> linkedMap, int rowLimit, OutputStream os) throws IOException {
        Workbook workbook = data2Workbook(list, linkedMap, rowLimit);
        workbook.write(os);
    }

    private static Workbook data2Workbook(List<Map<String, Object>> list, LinkedHashMap<String, String> linkedMap, int rowLimit) {
        // 初始化数据
        if (rowLimit == 0) {
            rowLimit = EXCEL2007_ROW_LIMIT;
        }
        List<String> fields = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (Map.Entry entry : linkedMap.entrySet()) {
            fields.add(String.valueOf(entry.getKey()));
            titles.add(String.valueOf(entry.getValue()));
        }


        // EXCEL 2007+ (.xlsx) 最大1048,576行数据、16,384列
        Workbook workBook = new SXSSFWorkbook();

        for (int j = 0; j <= list.size() / (rowLimit + 1); j++) {

            // new sheet
            String sheetName = "Sheet" + (j + 1);
            Sheet sheet = workBook.createSheet(sheetName);

            // create title
            createTitle(workBook, sheet, titles);

            // create content
            int rowMax = (j + 1) * rowLimit;
            if (list.size() < rowMax) {
                rowMax = list.size();
            }
            for (int i = j * rowLimit; i < rowMax; i++) {
                createRow(sheet, i % rowLimit + 1, list.get(i), fields);
            }

        }

        return workBook;

    }

    private static void createTitle(Workbook workBook, Sheet sheet, List<String> titles) {
        // new Row
        Row row = sheet.createRow(0);

        for (int i = 0; i < titles.size(); i++) {
            createCell(sheet, row, i, String.valueOf(titles.get(i)));
        }

        initTitleStyle(workBook, row);
        initCellStyle(workBook);
    }

    private static void createRow(Sheet sheet, int rowNum, Map<String, Object> cellLinkedMap, List<String> fields) {
        // new Row
        Row row = sheet.createRow(rowNum);

        for (int i = 0; i < fields.size(); i++) {
            createCell(sheet, row, i, String.valueOf(cellLinkedMap.get(String.valueOf(fields.get(i)))));
        }
    }

    /**
     * 目前存在问题：
     * cell 长度设置不精确
     * EXCEL单元格长度规则不明确 如字母'm'在cell单元格中实际占用两个字节
     *
     * @param sheet
     * @param row
     * @param cellNum
     * @param cellValue
     */
    private static void createCell(Sheet sheet, Row row, int cellNum, String cellValue) {
        // new Cell
        Cell cell = row.createCell(cellNum);
        cell.setCellValue(cellValue);
        // cell width
        int newWidth = getByteNum(cellValue) * 256;
        if (sheet.getColumnWidth(cellNum) < newWidth) {
            sheet.setColumnWidth(cellNum, newWidth);
        }
        // cell style
        cell.setCellStyle(CELL_STYLE);
    }

    /**
     * 返回等效字节数 = 单字节个数 + 双字节个数 * 2
     * 正则匹配 单字节/半角：[\\x00-\\xff]  双字节/全角：[^\\x00-\\xff]
     *
     * @param var
     * @return
     */
    private static int getByteNum(String var) {
        // 空闲5字节长度
        int len = 5;
        for (int i = 0; i < var.length(); i++) {
            char c = var.charAt(i);
            if (String.valueOf(c).matches("[\\x00-\\xff]")) {
                len++;
            } else {
                len += 2;
            }
        }
        return len;
    }

    /**
     * 单元格样式
     *
     * @param workbook
     */
    private static void initCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        // 水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        CELL_STYLE = style;
    }

    /**
     * 表头样式
     *
     * @param workbook
     * @param titleRow
     */
    private static void initTitleStyle(Workbook workbook, Row titleRow) {
        CellStyle style = workbook.createCellStyle();
        // 水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 背景色
//        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 字体样式
        Font font = workbook.createFont();
        // 字体
        font.setFontName("宋体");
        // 字体大小
        font.setFontHeightInPoints((short) 12);
        // 字体加粗
        font.setBold(true);

        style.setFont(font);

        for (int i = 0; i < titleRow.getLastCellNum(); i++) {
            titleRow.getCell(i).setCellStyle(style);
        }
    }


    /**
     * @author <a href="mailto:dongdongliang13@hotmail.com">zzt002</a>
     * @date 2020/6/29 16:23 GMT+8
     * @description
     * @param file
     * @param startRow
     * @return
     * @throws Exception
     */
    public static Map<Integer, List> readExcel2Map(MultipartFile file, Integer startRow) throws Exception {
        return readExcel2Map(file, startRow, Constants.INT_ONE);
    }

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/4/10 11:20 UTC+8
     * @description
     * @param file
     * @return
     * @throws Exception
     */
    public static Map<Integer, List> readExcel2Map(MultipartFile file, Integer startRow, Integer sheetNum) throws
            Exception {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new MessageException(e.getMessage());
        }

        Workbook workbook = getExcelWorkbook(file.getOriginalFilename(), inputStream);

        Sheet sheet = workbook.getSheetAt(sheetNum - 1);
        Row row = null;
        Cell cell = null;
        Map<Integer, List> map = Maps.newHashMap();
        startRow = startRow == null ? sheet.getFirstRowNum() : startRow - 1;
        for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            List<String> list = Lists.newArrayList();
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                list.add(cell.getStringCellValue());
            }
            map.put(i + 1, list);
        }
        return map;
    }

    /**
     * @author <a href="mailto:dongdongliang13@hotmail.com">zzt002</a>
     * @date 2020/6/29 9:58 GMT+8
     * @description
     * @param fileName
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static Workbook getExcelWorkbook(String fileName, InputStream inputStream) throws IOException {
        Workbook workbook = null;
        if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            throw new MessageException(String.format("Illegal excel file:%s", fileName));
        }
        return workbook;
    }
}
