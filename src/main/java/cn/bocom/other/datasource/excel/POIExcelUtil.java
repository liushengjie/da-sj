package cn.bocom.other.datasource.excel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class POIExcelUtil {
    /**
     * 标题样式
     */
    private final static String STYLE_HEADER = "header";
    /**
     * 表头样式
     */
    private final static String STYLE_TITLE = "title";
    /**
     * 数据样式
     */
    private final static String STYLE_DATA = "data";

    /**
     * 存储样式
     */
    private static final HashMap<String, CellStyle> cellStyleMap = new HashMap<>();

    /**
     * 读取excel文件里面的内容 支持日期，数字，字符，函数公式，布尔类型
     * 
     * 
     * @param file
     * @param rowCount
     * @param columnCount
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<ExcelSheetPO> readExcel(File file, Integer rowCount, Integer columnCount,
            String headerMode) throws FileNotFoundException, IOException {

        // 根据后缀名称判断excel的版本
        String extName = "." + cn.bocom.other.util.FileUtil.getFileType(file);

        Workbook wb = null;
        if (ExcelVersion.V2003.getSuffix().equals(extName)) {
            wb = new HSSFWorkbook(new FileInputStream(file));

        } else if (ExcelVersion.V2007.getSuffix().equals(extName)) {
            wb = new XSSFWorkbook(new FileInputStream(file));

        } else {
            // 无效后缀名称，这里之能保证excel的后缀名称，不能保证文件类型正确，不过没关系，在创建Workbook的时候会校验文件格式
            throw new IllegalArgumentException("Invalid excel version");
        }
        // 开始读取数据
        List<ExcelSheetPO> sheetPOs = new ArrayList<>();
        // 解析sheet
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            List<List<Object>> dataList = new ArrayList<>();
            List<List<String>> dataListCell = new ArrayList<>();
            ExcelSheetPO sheetPO = new ExcelSheetPO();
            sheetPO.setSheetName(sheet.getSheetName());
            sheetPO.setDataList(dataList);
            int readRowCount = 0;
            String[] headers = null;// 表头
            String[] headertypes = null;// 表头类型

            if (rowCount == null || rowCount > sheet.getPhysicalNumberOfRows()) {
                readRowCount = sheet.getPhysicalNumberOfRows();
            } else {
                readRowCount = rowCount;
            }
            // 解析sheet 的行
            for (int j = sheet.getFirstRowNum(); j < readRowCount; j++) {
                Row row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                if (row.getFirstCellNum() < 0) {
                    continue;
                }
                int readColumnCount = 0;
                if (columnCount == null || columnCount > row.getLastCellNum()) {
                    readColumnCount = (int) row.getLastCellNum();
                } else {
                    readColumnCount = columnCount;
                }
                List<Object> rowValue = new LinkedList<Object>();
                List<String> rowCell = new LinkedList<String>();
                // 解析sheet 的列
                for (int k = 0; k < readColumnCount; k++) {
                    Cell cell = row.getCell(k);

                    rowCell.add(getExcelCellType(cell));
                    rowValue.add(getCellValue(wb, cell));
                }

                dataList.add(rowValue);
                dataListCell.add(rowCell);
            }
            if (headerMode.equals("first")) {
                if (dataList.size() > 0) {
                    List<Object> rowVal = dataList.get(0);
                    List<String> rowCells = dataListCell.get(0);

                    headers = new String[rowVal.size()];

                    headertypes = new String[rowCells.size()];
                    for (int n = 0; n < rowVal.size(); n++) {
                        headers[n] = rowVal.get(n) == null ? "" : rowVal.get(n).toString();
                        headertypes[n] = rowCells.get(n);

                    }
                    sheetPO.setHeaders(headers);
                    sheetPO.setHeaderTypes(headertypes);
                    dataList.remove(0);
                    dataListCell.remove(0);
                }

            } else if (headerMode.equals("auto")) {
                if (dataList.size() > 0) {
                    List<Object> rowVal = dataList.get(0);
                    List<String> rowCells = dataListCell.get(1);
                    headertypes = new String[rowCells.size()];
                    headers = new String[rowVal.size()];
                    for (int m = 0; m < rowVal.size(); m++) {
                        headers[m] = "F" + (m);
                        headertypes[m] = rowCells.get(m);
                    }
                    sheetPO.setHeaders(headers);
                    sheetPO.setHeaderTypes(headertypes);
                }
            }
            sheetPO.setRowCount(dataList.size());
            sheetPOs.add(sheetPO);
        }
        return sheetPOs;
    }

    /**
     * 读取excel文件流里面的内容 支持日期，数字，字符，函数公式，布尔类型
     * 
     * 
     * @param file
     * @param rowCount
     * @param columnCount
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<ExcelSheetPO> readExcel(byte[] buf, Integer rowCount, Integer columnCount,
            String headerMode) throws FileNotFoundException, IOException, InvalidFormatException {
        InputStream is = new ByteArrayInputStream(buf);

        Workbook wb = null;


        if (POIFSFileSystem.hasPOIFSHeader(is)) {
            wb = new HSSFWorkbook(is);
        }
        /*if (POIXMLDocument.hasOOXMLHeader(is)) {
            wb = new XSSFWorkbook(OPCPackage.open(is));
        }*/

        // 开始读取数据
        List<ExcelSheetPO> sheetPOs = new ArrayList<>();
        // 解析sheet
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            List<List<Object>> dataList = new ArrayList<>();
            List<List<Cell>> dataListCell = new ArrayList<>();
            ExcelSheetPO sheetPO = new ExcelSheetPO();
            sheetPO.setSheetName(sheet.getSheetName());
            sheetPO.setDataList(dataList);
            int readRowCount = 0;
            String[] headers = null;// 表头
            String[] headertypes = null;// 表头类型

            if (rowCount == null || rowCount > sheet.getPhysicalNumberOfRows()) {
                readRowCount = sheet.getPhysicalNumberOfRows();
            } else {
                readRowCount = rowCount;
            }
            // 解析sheet 的行
            for (int j = sheet.getFirstRowNum(); j < readRowCount; j++) {
                Row row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                if (row.getFirstCellNum() < 0) {
                    continue;
                }
                int readColumnCount = 0;
                if (columnCount == null || columnCount > row.getLastCellNum()) {
                    readColumnCount = (int) row.getLastCellNum();
                } else {
                    readColumnCount = columnCount;
                }
                List<Object> rowValue = new LinkedList<Object>();
                List<Cell> rowCell = new LinkedList<Cell>();
                // 解析sheet 的列
                for (int k = 0; k < readColumnCount; k++) {
                    Cell cell = row.getCell(k);
                    rowValue.add(getCellValue(wb, cell));
                    rowCell.add(cell);

                }

                dataList.add(rowValue);
                dataListCell.add(rowCell);
            }
            if (headerMode.equals("first")) {
                List<Object> rowVal = dataList.get(0);
                List<Cell> rowCells = dataListCell.get(0);
                headers = new String[rowVal.size()];
                headertypes = new String[dataListCell.size()];
                for (int n = 0; n < rowVal.size(); n++) {
                    headers[n] = rowVal.get(n) == null ? "" : rowVal.get(n).toString();
                    headertypes[n] = getExcelCellType(rowCells.get(n));

                }
                sheetPO.setHeaders(headers);
                sheetPO.setHeaderTypes(headertypes);
                dataList.remove(0);
                dataListCell.remove(0);

            } else if (headerMode.equals("auto")) {
                List<Object> rowVal = dataList.get(0);
                List<Cell> rowCells = dataListCell.get(0);
                headertypes = new String[dataListCell.size()];
                headers = new String[rowVal.size()];
                for (int m = 0; m < rowVal.size(); m++) {
                    headers[m] = "F" + (m);
                    headertypes[m] = getExcelCellType(rowCells.get(m));
                }
                sheetPO.setHeaders(headers);
                sheetPO.setHeaderTypes(headertypes);
            }

            sheetPOs.add(sheetPO);

        }
        return sheetPOs;
    }

    private static String getExcelCellType(Cell cell) {

        String ret = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    ret = "STRING";
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                        ret = "NUMBER";
                    } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        ret = "NUMBER";
                    } else {
                        ret = "DATE";
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    ret = "BOOLEAN";
                    break;
                case Cell.CELL_TYPE_BLANK:
                    ret = "STRING";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    // 格式单元格
                    ret = "NUMBER";
                    break;
                default:
                    ret = "STRING";
            }
        }

        return ret;
    }

    /**
     * 转换
     * 
     * @param wb
     * @param cell
     * @return
     */
    private static Object getCellValue(Workbook wb, Cell cell) {
        Object columnValue = null;
        if (cell != null) {
            DecimalFormat df = new DecimalFormat("0.00");// 格式化 number
            // String
            // 字符
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
            DecimalFormat nf = new DecimalFormat("0");// 格式化数字
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    columnValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = df.format(cell.getNumericCellValue());
                    } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = nf.format(cell.getNumericCellValue());
                    } else {
                        columnValue =
                                sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    columnValue = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    columnValue = "";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    // 格式单元格
                    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                    evaluator.evaluateFormulaCell(cell);
                    CellValue cellValue = evaluator.evaluate(cell);
                    columnValue = cellValue.getNumberValue();
                    break;
                default:
                    columnValue = cell.toString() == null ? "" : cell.toString();
            }
        }
        return columnValue;
    }

    /**
     * 在硬盘上写入excel文件
     * 
     * 
     * @param version
     * @param excelSheets
     * @param filePath
     * @throws IOException
     */
    public static void createWorkbookAtDisk(ExcelVersion version, List<ExcelSheetPO> excelSheets,
            String filePath) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filePath);
        createWorkbookAtOutStream(version, excelSheets, fileOut, true);
    }

    /**
     * 把excel表格写入输出流中，输出流会被关闭
     * 
     * 
     * @param version
     * @param excelSheets
     * @param outStream
     * @param closeStream 是否关闭输出流
     * @throws IOException
     */
    public static void createWorkbookAtOutStream(ExcelVersion version,
            List<ExcelSheetPO> excelSheets, OutputStream outStream, boolean closeStream)
            throws IOException {
        if (CollectionUtils.isNotEmpty(excelSheets)) {
            Workbook wb = createWorkBook(version, excelSheets);
            wb.write(outStream);
            if (closeStream) {
                outStream.close();
            }
        }
    }

    private static Workbook createWorkBook(ExcelVersion version, List<ExcelSheetPO> excelSheets) {
        Workbook wb = createWorkbook(version);
        for (int i = 0; i < excelSheets.size(); i++) {
            ExcelSheetPO excelSheetPO = excelSheets.get(i);
            if (excelSheetPO.getSheetName() == null) {
                excelSheetPO.setSheetName("sheet" + i);
            }
            // 过滤特殊字符
            Sheet tempSheet =
                    wb.createSheet(WorkbookUtil.createSafeSheetName(excelSheetPO.getSheetName()));
            buildSheetData(wb, tempSheet, excelSheetPO, version);
        }
        return wb;
    }

    private static void buildSheetData(Workbook wb, Sheet sheet, ExcelSheetPO excelSheetPO,
            ExcelVersion version) {
        sheet.setDefaultRowHeight((short) 400);
        sheet.setDefaultColumnWidth((short) 10);
        // createTitle(sheet, excelSheetPO, wb, version);
        createHeader(sheet, excelSheetPO, wb, version);
        createBody(sheet, excelSheetPO, wb, version);
    }

    private static void createBody(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb,
            ExcelVersion version) {
        List<List<Object>> dataList = excelSheetPO.getDataList();
        for (int i = 0; i < dataList.size() && i < version.getMaxRow(); i++) {
            List<Object> values = dataList.get(i);
            Row row = sheet.createRow(1 + i);
            for (int j = 0; j < values.size() && j < version.getMaxColumn(); j++) {
                Cell cell = row.createCell(j);
                // cell.setCellStyle(getStyle(STYLE_DATA, wb));
                cell.setCellValue(values.get(j) == null ? "" : values.get(j).toString());
            }
        }

    }

    private static void createHeader(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb,
            ExcelVersion version) {
        String[] headers = excelSheetPO.getHeaders();
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length && i < version.getMaxColumn(); i++) {
            Cell cellHeader = row.createCell(i);
            // cellHeader.setCellStyle(getStyle(STYLE_HEADER, wb));
            cellHeader.setCellValue(headers[i]);
        }

    }

    private static void createTitle(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb,
            ExcelVersion version) {
        Row titleRow = sheet.createRow(0);
        Cell titleCel = titleRow.createCell(0);
        titleCel.setCellValue(excelSheetPO.getTitle());
        // titleCel.setCellStyle(getStyle(STYLE_TITLE, wb));
        // 限制最大列数
        int column = excelSheetPO.getDataList().size() > version.getMaxColumn()
                ? version.getMaxColumn()
                : excelSheetPO.getDataList().size();
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, column - 1));
    }

    private static CellStyle getStyle(String type, Workbook wb) {

        if (cellStyleMap.containsKey(type)) {
            return cellStyleMap.get(type);
        }
        // 生成一个样式
        CellStyle style = wb.createCellStyle();
        //style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setWrapText(true);

        if (STYLE_HEADER == type) {
            //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 16);
            //font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(font);
        } else if (STYLE_TITLE == type) {
            //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 18);
            //font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(font);
        } else if (STYLE_DATA == type) {
            //style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);
            style.setFont(font);
        }
        cellStyleMap.put(type, style);
        return style;
    }

    private static Workbook createWorkbook(ExcelVersion version) {
        switch (version) {
            case V2003:
                return new HSSFWorkbook();
            case V2007:
                return new XSSFWorkbook();
        }
        return null;
    }
}
