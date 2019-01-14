package cn.bocom.other.datasource.excel;

import java.util.List;

/**
 * 定义表格的数据对象
 * 
 */
public class ExcelSheetPO {

    /**
     * sheet的名称
     */
    private String sheetName;


    /**
     * 表格标题
     */
    private String title;

    /**
     * 头部标题集合
     */
    private String[] headers;
    private String[] headerTypes;



    /**
     * 数据集合
     */
    private List<List<Object>> dataList;

    private int rowCount;

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public List<List<Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<Object>> dataList) {
        this.dataList = dataList;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getHeaderTypes() {
        return headerTypes;
    }

    public void setHeaderTypes(String[] headerTypes) {
        this.headerTypes = headerTypes;
    }
}
