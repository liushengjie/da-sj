package cn.bocom.other.datasource.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;

/**
 * @author: lishipeng
 * @date: 2019/1/29
 * @Description: TODO
 */
public class EasyExcelUtil {

	/**
	 * StringList 解析监听器
	 */
	@SuppressWarnings("rawtypes")
	private static class StringExcelListener extends AnalysisEventListener {
		/**
		 * 自定义用于暂时存储data
		 * 可以通过实例获取该值
		 */
		private List<List<String>> datas = new ArrayList<>();
		
		/**
		 * 每解析一行都会回调invoke()方法
		 * @param object
		 * @param context
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void invoke(Object object, AnalysisContext context) {
			List<String> stringList= (List<String>) object;
			//数据存储到list，供批量处理，或后续自己业务逻辑处理。
			datas.add(stringList);
			//根据自己业务做处理
		}
			
		@Override
		public void doAfterAllAnalysed(AnalysisContext context) {
			//解析结束销毁不用的资源
			//注意不要调用datas.clear(),否则getDatas为null
		}
			
		public List<List<String>> getDatas() {
			return datas;
		}
		@SuppressWarnings("unused")
		public void setDatas(List<List<String>> datas) {
			this.datas = datas;
		}
	}
	
	/**
	 * 来读取Excel
	 * @param file Excel File
	 * @param excelTypeEnum Excel的格式(XLS或XLSX)
	 * @return 返回 StringList 的列表
	 * @throws FileNotFoundException 
	 */
	public static List<List<String>> readExcelWithStringList(File file, int sheetIndex) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(file);
		StringExcelListener listener = new StringExcelListener();
		ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
		List<Sheet> sheetList = excelReader.getSheets();
		if(sheetList.size()<sheetIndex) {
			return null;
		}
		excelReader.read(sheetList.get(sheetIndex));
		List<List<String>> list = listener.getDatas();
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 读取Sheet
	 * @param file Excel File
	 * @param excelTypeEnum Excel的格式(XLS或XLSX)
	 * @return 返回 Sheet的列表
	 * @throws FileNotFoundException 
	 */
	public static List<Map<String, Object>> getExcelSheets(File file) throws FileNotFoundException{
		InputStream inputStream = new FileInputStream(file);
		StringExcelListener listener = new StringExcelListener();
		ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
		List<Sheet> sheetList =excelReader.getSheets();
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		if(sheetList!=null&&sheetList.size()>0) {
			for(int i=0;i<sheetList.size();i++) {
				Sheet s = sheetList.get(i);
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("index", i);
				m.put("sheetName", s.getSheetName());
				ret.add(m);
			}
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 来读取Excel表头
	 * @param file Excel File
	 * @param sheetIndex 要读取的sheet索引
	 * @param headRowNum 表头所在行
	 * @param excelTypeEnum Excel的格式(XLS或XLSX)
	 * @return 返回表头列表
	 * @throws FileNotFoundException 
	 */
	public static List<String> getExcelSheetHead(File file, int sheetIndex, int headRowNum) throws FileNotFoundException {
		if(headRowNum<1) {
			return null;
		}
		List<List<String>> list = readExcelWithStringList(file, sheetIndex);
		if(list==null||list.size()==0) {
			return null;
		}
		if(headRowNum>0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	/**
	 * 来读取Excel数据，不含表头
	 * @param file Excel File
	 * @param sheetIndex 要读取的sheet索引
	 * @param headRowNum 表头所在行
	 * @param limit 要读取的数据行数
	 * @param excelTypeEnum Excel的格式(XLS或XLSX)
	 * @return 返回 StringList 的列表
	 * @throws FileNotFoundException 
	 */
	public static List<List<String>> getExcelSheetData(File file, int sheetIndex, int headRowNum, int limit) throws FileNotFoundException {
		List<List<String>> list = readExcelWithStringList(file, sheetIndex);
		if(list==null||list.size()==0) {
			return null;
		}
		if(headRowNum>0) {
			list.remove(0);
		}
		if(limit>0) {
			return list.subList(0, limit);
		} else {
			return list;
		}
	}
	
}
