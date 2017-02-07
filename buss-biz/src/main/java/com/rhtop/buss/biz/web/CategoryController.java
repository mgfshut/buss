package com.rhtop.buss.biz.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.rhtop.buss.common.entity.Category;
import com.rhtop.buss.common.entity.CodeValue;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.CodeValueService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.common.utils.Constant;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.utils.PropertyUtil;
import com.rhtop.buss.common.utils.UnitUtils;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/category")
public class CategoryController  extends BaseController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private RelCategoryPriceService catPriSer;
	@Autowired
	private CodeValueService codeValueService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveCategory(@Valid @RequestParam(value = "userId") String userId,@Valid Category category){
		HtmlMessage htmlMessage = new HtmlMessage(category);
		if(category.getCategoryId() == null || "".equals(category.getCategoryId())){
			//对品类进行唯一性判断
			 Category cate = categoryService.checkCategoryExist(category);
			 if(cate==null){//不存在
				//加入品类表中
				String categoryId = UUID.randomUUID().toString().replace("-", "");
				category.setCategoryId(categoryId);
				category.setCreateUser(userId);
				
				category.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
				category.setUpdateUser(userId);
				category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
				//从字典表中拿汇率数据
				String rate = null;
				List<CodeValue> rates = codeValueService.listCodeValuesByCode("rate");
				if (rates == null || rates.size() == 0){
					htmlMessage.setMessage("暂无汇率字典信息，无法进行美元转换！");
					
					return htmlMessage;
				}
				
				for(CodeValue val : rates){
					if(val.getCodeValue().trim().equals("us")){
						rate = val.getCodeValueDescribe();
					}
				}
				//换算价格
				BigDecimal offerPri = UnitUtils.unitConver(category.getCurrency(), new BigDecimal(category.getCatePri()), category.getUnit(), rate);
				//换算好的报盘价添加到品类表中
				category.setUniOfferPri(offerPri.floatValue());
				category.setUniOfferAging(category.getUniOfferAging());
				categoryService.insertCategory(category);
				//将供应商，货币单位，计量单位，报价，时效,品类主键加入到品类与价格关系表中
				RelCategoryPrice relCategoryPrice = new RelCategoryPrice();
				relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
				relCategoryPrice.setCategoryId(categoryId);
				relCategoryPrice.setCateSup(category.getCateSup());
				relCategoryPrice.setCurrency(category.getCurrency());
//				relCategoryPrice.setOfferPri(category.getOfferPri());
				relCategoryPrice.setUniOfferAging(category.getUniOfferAging());
				relCategoryPrice.setUnit(category.getUnit());
				relCategoryPrice.setOfferUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
				catPriSer.insertRelCategoryPrice(relCategoryPrice);	 
				htmlMessage.setMessage("添加成功！");
			 }else{//存在
				//如果存在
				cate.setOfferPri(category.getOfferPri());
				cate.setOfferAging(category.getOfferAging());
				cate.setUpdateUser(userId);
				cate.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
				categoryService.updateCategory(cate);
				//将供应商，货币单位，计量单位，报价，时效,品类主键加入到品类与价格关系表中
				RelCategoryPrice relCategoryPrice = new RelCategoryPrice();
				relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
				relCategoryPrice.setCategoryId(cate.getCategoryId());
				relCategoryPrice.setCateSup(category.getCateSup());
				relCategoryPrice.setCurrency(category.getCurrency());
//				relCategoryPrice.setOfferPri(category.getOfferPri());
				relCategoryPrice.setOfferAging(category.getOfferAging());
				relCategoryPrice.setUnit(category.getUnit());
				relCategoryPrice.setOfferUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
				catPriSer.insertRelCategoryPrice(relCategoryPrice);	 
				htmlMessage.setMessage("添加成功！");
			 }
		}else{
			category.setUpdateUser(userId);
			category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			categoryService.updateCategory(category);
		}
		return htmlMessage;
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteCategory")
	public InfoResult<Category> deleteCategory(@RequestParam("categoryId") String categoryId){
		InfoResult<Category> infoResult = new InfoResult<Category>();
		int num = categoryService.deleteCategory(categoryId);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("删除成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("删除失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 修改
     */
	@ResponseBody
	@RequestMapping("/updateCategory")
	public InfoResult<Category> updateCategory(Category category){
		InfoResult<Category> infoResult = new InfoResult<Category>();
		category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = categoryService.updateCategory(category);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("修改成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("修改失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
	 * 根据条件分页查询信息列表
	 */
	@ResponseBody
	@RequestMapping(value="/pager")
	public InfoResult<Category> listPageCategory(Page page,Category category){
		InfoResult<Category> infoResult = new InfoResult<Category>();
		category.setPage(page);
		List<Category> categoryList = categoryService.listPageCategory(category);
		infoResult.setCode("200");
		infoResult.setResList(categoryList);
		infoResult.setPage(category.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<Category> selectByPrimaryKey(@RequestParam("categoryId") String categoryId) {
		InfoResult<Category> infoResult = new InfoResult<Category>();
		infoResult.setCode("200");
		Category category = categoryService.selectByPrimaryKey(categoryId);
		infoResult.setResObject(category);
		return infoResult;
	}
	
	/**
	 * 根据条件查询品类信息列表
	 * @author mgf
	 * @date 2017年1月12日 下午2:43:25 
	 * @param body
	 * @return
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value="/listCategorys")
	public ResultInfo listCategorys(@RequestParam("body") String body) {
		ObjectMapper mapper = new ObjectMapper();
		Category category = null;
		try{
			category = mapper.readValue(body, Category.class);
		}catch(Exception e){
			log.error("[CategoryController.listCategorys]数据解析异常", e);
		}
		
		JSONObject jsonObject=JSONObject.fromObject(body);
		ResultInfo readResult = new ResultInfo();
		List<Category> categorys = categoryService.listCategorys(category);
		readResult.setCode("200");
		readResult.setRecords(categorys);
		return readResult;
	}
	
	public String formatCell(HSSFCell hssfCell){  
        if(hssfCell==null){  
            return "";  
        }else{  
            if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){  
                return String.valueOf(hssfCell.getBooleanCellValue());  
            }else if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){  
                return String.valueOf(hssfCell.getNumericCellValue());  
            }else{  
                return String.valueOf(hssfCell.getStringCellValue());  
            }  
        }  
    } 
	
	@RequestMapping("/excelImport")
	@ResponseBody
	public HtmlMessage excelImport(@Valid @RequestParam(value = "userId") String userId,
			@Valid @RequestParam(value = "filePath") String filePath){
		String rootPath = "/filestore/";
		try {
			PropertyUtil propertyUtil = new PropertyUtil("properties/common.properties");
			//从配置文件中读取上传文件的存放根路径
			rootPath = propertyUtil.readValue("file.root.path");
		} catch (IOException e1) {
			e1.printStackTrace();
			log.error("[CategoryController.excelImport]IO异常", e1);
		}
		
		filePath = rootPath+filePath;
		POIFSFileSystem fs = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("[CategoryController.excelImport]文件未找到异常", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("[CategoryController.excelImport]IO异常", e);
		}
		
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("[CategoryController.excelImport]IO异常", e);
		}
		HSSFSheet hssfSheet=wb.getSheetAt(0);
		List<Category> categorys = new ArrayList<Category>();
		if(hssfSheet!=null){
			int[] cellIndex = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
			//遍历excel表头,从第二行开始 即 rowNum=1,逐个获取单元格的内容,然后进行格式处理,最后插入数据库
			HSSFRow hssfNameRow = hssfSheet.getRow(1);
			if(hssfNameRow==null || hssfNameRow.getCell(0) == null || "".equals(formatCell(hssfNameRow.getCell(0)))){
				HtmlMessage html = new HtmlMessage(new Category());
				html.setStatusCode("400");
				html.setMessage("第二列未发现表头信息");
				return html;
			}
			
			for(int cellNum=0; cellNum<=hssfNameRow.getLastCellNum(); cellNum++){
				HSSFCell cell = hssfNameRow.getCell(cellNum);
				try{
					if (cell != null && StringUtils.isNotEmpty(cell.getStringCellValue())){
						String cellName = cell.getStringCellValue().replaceAll(" ", "");
						if ("品类名称".equals(cellName)){
							cellIndex[0] = cellNum;
						}else if ("规格".equals(cellName)){
							cellIndex[1] = cellNum;
						}else if ("包装数量".equals(cellName)){
							cellIndex[2] = cellNum;
						}else if ("厂号".equals(cellName)){
							cellIndex[3] = cellNum;
						}else if ("产地".equals(cellName)){
							cellIndex[4] = cellNum;
						}else if ("备注".equals(cellName)){
							cellIndex[5] = cellNum;
						}else if ("地区".equals(cellName)){
							cellIndex[6] = cellNum;
						}else if ("报盘价".equals(cellName)){
							cellIndex[7] = cellNum;
						}else if ("价格时效".equals(cellName)){
							cellIndex[8] = cellNum;
						}
					}
				}catch(Exception e){
					
				}
			}
			
			for(int cellNum=0; cellNum<cellIndex.length; cellNum++){
				if (cellIndex[cellNum] == -1){
					String name = "";
					if (cellNum == 0){
						name = "品类名称";
					}else if (cellNum == 1){
						name = "规格";
					}else if (cellNum == 2){
						name = "包装数量";
					}else if (cellNum == 3){
						name = "厂号";
					}else if (cellNum == 4){
						name = "产地";
					}else if (cellNum == 5){
						name = "备注";
					}else if (cellNum == 6){
						name = "地区";
					}else if (cellNum == 7){
						name = "报盘价";
					}else if (cellNum == 8){
						name = "价格时效";
					}
					
					HtmlMessage html = new HtmlMessage(new Category());
					html.setStatusCode("400");
					html.setMessage("未发现["+name+"]列头信息，请确认表头名称是否正确！");
					return html;
				}
			}
			//遍历excel,从第三行开始 即 rowNum=2,逐个获取单元格的内容,然后进行格式处理,最后插入数据库
			for(int rowNum=2;rowNum<=hssfSheet.getLastRowNum();rowNum++){
				try{
					HSSFRow hssfRow=hssfSheet.getRow(rowNum);
					if(hssfRow==null||hssfRow.getCell(0) == null||"".equals(formatCell(hssfRow.getCell(0)))){
						continue;
					}
					Category category = new Category();
					category.setCategoryId(UUID.randomUUID().toString().replace("-", ""));
					String cateName = formatCell(hssfRow.getCell(cellIndex[0]));
					if (StringUtils.isNotEmpty(cateName)){
						
						CodeValue codeCateName = codeValueService.queryCodeValueAndCodeName("cateName", cateName);
						if (codeCateName == null || StringUtils.isEmpty(codeCateName.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[品类名称]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							
							return html;
						}else{
							cateName = codeCateName.getCodeValueId();
						}
						category.setCateName(cateName);
						
						//规格需要从字段表提取
						String cateStan = formatCell(hssfRow.getCell(cellIndex[1]));
						CodeValue code = codeValueService.queryCodeValueAndCodeName("cateStan", cateStan);
						if (code == null || StringUtils.isEmpty(code.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[规格]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							
							return html;
						}else{
							cateStan = code.getCodeValueId();
						}
						category.setCateStan(cateStan);
						category.setPkgQuan(formatCell(hssfRow.getCell(cellIndex[2])));
						
						String manuNum = formatCell(hssfRow.getCell(cellIndex[3]));
						CodeValue codeManuNum = codeValueService.queryCodeValueAndCodeName("manuNum", manuNum);
						if (codeManuNum == null || StringUtils.isEmpty(codeManuNum.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[厂号]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							
							return html;
						}else{
							manuNum = codeManuNum.getCodeValueId();
						}
						category.setManuNum(manuNum);
						
						//产地需要从字段表提取
						String prodPla = formatCell(hssfRow.getCell(cellIndex[4]));
						CodeValue codeProd = codeValueService.queryCodeValueAndCodeName("prodPla", prodPla);
						if (codeProd == null || StringUtils.isEmpty(codeProd.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[产地]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							
							return html;
						}else{
							prodPla = codeProd.getCodeValueId();
						}
						category.setProdPla(prodPla);
						category.setComm(formatCell(hssfRow.getCell(cellIndex[5])));
						//category.setCusCha(formatCell(hssfRow.getCell(cellIndex[6])));
						category.setCusLoc(formatCell(hssfRow.getCell(cellIndex[6])));
						if(hssfRow.getCell(cellIndex[7]) == null || "".equals(formatCell(hssfRow.getCell(cellIndex[7])))){
						}else{
							category.setOfferPri(Float.valueOf(formatCell(hssfRow.getCell(cellIndex[7])).toString()));
						}
						category.setOfferAging(formatCell(hssfRow.getCell(cellIndex[8])));
						category.setCreateUser(userId);
						category.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
						category.setUpdateUser(userId);
						category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
						categorys.add(category);
						ObjectMapper json = new ObjectMapper();
						System.out.println(json.writeValueAsString(category));
					}
				}catch(Exception e){
					HtmlMessage html = new HtmlMessage(new Category());
					html.setStatusCode("400");
					html.setMessage("第"+(rowNum+1)+"行数据有误，请检查！");
					
					return html;
				}
			}
		}
		
		//categoryService.insertExcelCategory(categorys);
		return new HtmlMessage(new Category());
	}
	
	@RequestMapping("/excelImportFile")
	@ResponseBody
	public HtmlMessage excelImportFile(@Valid @RequestParam(value = "userId") String userId, @RequestParam(value = "file") MultipartFile file){
		File localFile = null;
		try {
			localFile = new File(FileUtils.getTempDirectory() + "/" + file.getOriginalFilename());
			Files.write(file.getBytes(), localFile);
			
			if (localFile == null || !localFile.exists()){
				HtmlMessage html = new HtmlMessage(new Category());
				html.setStatusCode("400");
				html.setMessage("文件上传失败");
				return html;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			log.error("[CategoryController.excelImport]IO异常", e1);
		}
		
		
		POIFSFileSystem fs = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(localFile.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("[CategoryController.excelImport]文件未找到异常", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("[CategoryController.excelImport]IO异常", e);
		}
		
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("[CategoryController.excelImport]IO异常", e);
			HtmlMessage html = new HtmlMessage(new Category());
			html.setStatusCode("400");
			html.setMessage("Excel读取失败，请确认文件是否可用！");
			return html;
		}
		HSSFSheet hssfSheet=wb.getSheetAt(0);
		List<Category> categorys = new ArrayList<Category>();
		if(hssfSheet!=null){
			int[] cellIndex = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
			//遍历excel表头,从第二行开始 即 rowNum=1,逐个获取单元格的内容,然后进行格式处理,最后插入数据库
			HSSFRow hssfNameRow = hssfSheet.getRow(1);
			if(hssfNameRow==null || hssfNameRow.getCell(0) == null || "".equals(formatCell(hssfNameRow.getCell(0)))){
				HtmlMessage html = new HtmlMessage(new Category());
				html.setStatusCode("400");
				html.setMessage("第二列未发现表头信息");
				return html;
			}
			
			for(int cellNum=0; cellNum<=hssfNameRow.getLastCellNum(); cellNum++){
				HSSFCell cell = hssfNameRow.getCell(cellNum);
				try{
					if (cell != null && StringUtils.isNotEmpty(cell.getStringCellValue())){
						String cellName = cell.getStringCellValue().replaceAll(" ", "");
						if ("品类名称".equals(cellName)){
							cellIndex[0] = cellNum;
						}else if ("规格".equals(cellName)){
							cellIndex[1] = cellNum;
						}else if ("包装数量".equals(cellName)){
							cellIndex[2] = cellNum;
						}else if ("厂号".equals(cellName)){
							cellIndex[3] = cellNum;
						}else if ("产地".equals(cellName)){
							cellIndex[4] = cellNum;
						}else if (cellName.indexOf("备注") != -1){
							cellIndex[5] = cellNum;
						}else if ("地区".equals(cellName)){
							cellIndex[6] = cellNum;
						}else if (cellName.indexOf("报盘价") != -1){
							cellIndex[7] = cellNum;
						}else if ("价格时效".equals(cellName)){
							cellIndex[8] = cellNum;
						}
					}
				}catch(Exception e){
					
				}
			}
			
			for(int cellNum=0; cellNum<cellIndex.length; cellNum++){
				if (cellIndex[cellNum] == -1){
					String name = "";
					if (cellNum == 0){
						name = "品类名称";
					}else if (cellNum == 1){
						name = "规格";
					}else if (cellNum == 2){
						name = "包装数量";
					}else if (cellNum == 3){
						name = "厂号";
					}else if (cellNum == 4){
						name = "产地";
					}else if (cellNum == 5){
						name = "备注";
					}else if (cellNum == 6){
						name = "地区";
					}else if (cellNum == 7){
						name = "报盘价";
					}else if (cellNum == 8){
						name = "价格时效";
					}
					
					HtmlMessage html = new HtmlMessage(new Category());
					html.setStatusCode("400");
					html.setMessage("未发现["+name+"]列头信息，请确认表头名称是否正确！");
					return html;
				}
			}
			//遍历excel,从第三行开始 即 rowNum=2,逐个获取单元格的内容,然后进行格式处理,最后插入数据库
			for(int rowNum=2;rowNum<=hssfSheet.getLastRowNum();rowNum++){
				try{
					HSSFRow hssfRow=hssfSheet.getRow(rowNum);
					if(hssfRow==null||hssfRow.getCell(0) == null||"".equals(formatCell(hssfRow.getCell(0)))){
						continue;
					}
					Category category = new Category();
					category.setCategoryId(UUID.randomUUID().toString().replace("-", ""));
					//用固定列来读取数据
					String cateName = formatCell(hssfRow.getCell(0));
					if (StringUtils.isNotEmpty(cateName)){
						
						CodeValue codeCateName = codeValueService.queryCodeValueAndCodeName("cateName", cateName);
						if (codeCateName == null || StringUtils.isEmpty(codeCateName.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[品类名称]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							
							return html;
						}else{
							cateName = codeCateName.getCodeValueId();
						}
						category.setCateName(cateName);
						
						//规格需要从字段表提取
						String cateStan = formatCell(hssfRow.getCell(1));
						if (StringUtils.isEmpty(cateStan)){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[规格]数据不能为空，数据导入终止！");
							return html;
						}
						CodeValue code = codeValueService.queryCodeValueAndCodeName("cateStan", cateStan);
						if (code == null || StringUtils.isEmpty(code.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[规格]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							
							return html;
						}else{
							cateStan = code.getCodeValue();
						}
						category.setCateStan(cateStan);
						//包装数量
						String pkgQuan = formatCell(hssfRow.getCell(2));
//						if (StringUtils.isEmpty(pkgQuan)){
//							HtmlMessage html = new HtmlMessage(new Category());
//							html.setStatusCode("400");
//							html.setMessage("第"+(rowNum+1)+"行[包装]数据不能为空，数据导入终止！");
//							return html;
//						}
						category.setPkgQuan(pkgQuan);
						//厂号
						String manuNum = formatCell(hssfRow.getCell(3));
						if (StringUtils.isEmpty(manuNum)){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[厂号]数据不能为空，数据导入终止！");
							return html;
						}
						
						CodeValue codeManuNum = codeValueService.queryCodeValueAndCodeName("manuNum", manuNum);
						if (codeManuNum == null || StringUtils.isEmpty(codeManuNum.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[厂号]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							return html;
						}else{
							manuNum = codeManuNum.getCodeValueId();
						}
						category.setManuNum(manuNum);
						
						//产地需要从字段表提取
						String prodPla = formatCell(hssfRow.getCell(4));
						if (StringUtils.isEmpty(prodPla)){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[产地]数据不能为空，数据导入终止！");
							return html;
						}
						CodeValue codeProd = codeValueService.queryCodeValueAndCodeName("prodPla", prodPla);
						if (codeProd == null || StringUtils.isEmpty(codeProd.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[产地]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							
							return html;
						}else{
							prodPla = codeProd.getCodeValue();
						}
						category.setProdPla(prodPla);
						category.setComm(formatCell(hssfRow.getCell(5)));
						category.setCusLoc(formatCell(hssfRow.getCell(cellIndex[6])));
						//渠道
						String cusCha = formatCell(hssfRow.getCell(7));
						if (StringUtils.isEmpty(cusCha)){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[渠道]数据不能为空，数据导入终止！");
							return html;
						}
						CodeValue codeCusCha = codeValueService.queryCodeValueAndCodeName("cusCha", cusCha);
						if (codeProd == null || StringUtils.isEmpty(codeCusCha.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[渠道]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							
							return html;
						}else{
							cusCha = codeCusCha.getCodeValue();
						}
						category.setCusCha(cusCha);
						
						String offerPri = formatCell(hssfRow.getCell(8));
						if(StringUtils.isEmpty(offerPri)){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[报盘价]数据不能为空，数据导入终止！");
							
							return html;
						}else{
							category.setOfferPri(Float.valueOf(offerPri));
						}
						
						category.setCreateUser(userId);
						category.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
						category.setUpdateUser(userId);
						category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
						//提取价格相关信息
						RelCategoryPrice price = new RelCategoryPrice();
						
						//批发价
						String pifj = formatCell(hssfRow.getCell(9));
						if (StringUtils.isNotEmpty(pifj)){
							price.setWholesalePri(Float.parseFloat(pifj));
						}
						//接盘价
						String jpj = formatCell(hssfRow.getCell(10));
						if (StringUtils.isNotEmpty(jpj)){
							price.setAcptPri(Float.parseFloat(jpj));
						}
						//现货价
						String xhj = formatCell(hssfRow.getCell(11));
						if (StringUtils.isNotEmpty(xhj) && StringUtils.isNumeric(xhj)){
							price.setSpotMin(Float.parseFloat(xhj));
							price.setSpotMax(Float.parseFloat(xhj));
						}else if (StringUtils.isNotEmpty(jpj)){
							String[] xhjArray = xhj.split("-");
							if (xhjArray.length == 2){
								price.setSpotMin(Float.parseFloat(xhjArray[0]));
								price.setSpotMax(Float.parseFloat(xhjArray[1]));
							}
						}
						//半期货价
						String bqhj = formatCell(hssfRow.getCell(12));
						if (StringUtils.isNotEmpty(bqhj) && StringUtils.isNumeric(bqhj)){
							price.setInterFutMin(Float.parseFloat(bqhj));
							price.setInterFutMax(Float.parseFloat(bqhj));
						}else if (StringUtils.isNotEmpty(bqhj)){
							String[] xhjArray = bqhj.split("-");
							if (xhjArray.length == 2){
								price.setInterFutMin(Float.parseFloat(xhjArray[0]));
								price.setInterFutMax(Float.parseFloat(xhjArray[1]));
							}
						}
						//期货价
						String qhj = formatCell(hssfRow.getCell(13));
						if (StringUtils.isNotEmpty(qhj) && StringUtils.isNumeric(qhj)){
							price.setFutMin(Float.parseFloat(qhj));
							price.setFutMax(Float.parseFloat(qhj));
						}else if (StringUtils.isNotEmpty(qhj)){
							String[] xhjArray = qhj.split("-");
							if (xhjArray.length == 2){
								price.setFutMin(Float.parseFloat(xhjArray[0]));
								price.setFutMax(Float.parseFloat(xhjArray[1]));
							}
						}
						
						//供应商
						String gys = formatCell(hssfRow.getCell(14));
						if (StringUtils.isNotEmpty(gys)){
							price.setCateSup(gys);
						}
						category.setRelCategoryPrice(price);
						
						String uniOfferPri = formatCell(hssfRow.getCell(15));
						if(StringUtils.isEmpty(uniOfferPri)){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[国际报盘价]数据不能为空，数据导入终止！");
							return html;
						}else{
							category.setUniOfferPri(Float.valueOf(uniOfferPri));
						}
						//价格时效
						category.setOfferAging(formatCell(hssfRow.getCell(16)));
						
						categorys.add(category);
						
					/*
					 * 2017年1月22日。备份代码，从表头读取数据数据列所在位置
					 * String cateName = formatCell(hssfRow.getCell(cellIndex[0]));
					if (StringUtils.isNotEmpty(cateName)){
						category.setCateName(cateName);
						//规格需要从字段表提取
						String cateStan = formatCell(hssfRow.getCell(cellIndex[1]));
						if (StringUtils.isEmpty(cateStan)){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[规格]数据不能为空，数据导入终止！");
							return html;
						}
						CodeValue code = codeValueService.queryCodeValueAndCodeName("cateStan", cateStan);
						if (code == null || StringUtils.isEmpty(code.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[规格]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							
							return html;
						}else{
							cateStan = code.getCodeValue();
						}
						category.setCateStan(cateStan);
						//包装数量
						String pkgQuan = formatCell(hssfRow.getCell(cellIndex[2]));
						if (StringUtils.isEmpty(pkgQuan)){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[包装]数据不能为空，数据导入终止！");
							return html;
						}
						category.setPkgQuan(pkgQuan);
						//厂号
						String manuNum = formatCell(hssfRow.getCell(cellIndex[3]));
						if (StringUtils.isEmpty(manuNum)){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[厂号]数据不能为空，数据导入终止！");
							return html;
						}
						category.setManuNum(manuNum);
						//产地需要从字段表提取
						String prodPla = formatCell(hssfRow.getCell(cellIndex[4]));
						if (StringUtils.isEmpty(prodPla)){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[产地]数据不能为空，数据导入终止！");
							return html;
						}
						CodeValue codeProd = codeValueService.queryCodeValueAndCodeName("prodPla", prodPla);
						if (codeProd == null || StringUtils.isEmpty(codeProd.getCodeValueId())){
							HtmlMessage html = new HtmlMessage(new Category());
							html.setStatusCode("400");
							html.setMessage("第"+(rowNum+1)+"行[产地]数据在系统字典中不存在，无法继续提取，请先向字典添加对应的数据！");
							
							return html;
						}else{
							prodPla = codeProd.getCodeValue();
						}
						category.setProdPla(prodPla);
						category.setComm(formatCell(hssfRow.getCell(cellIndex[5])));
						category.setCusCha(formatCell(hssfRow.getCell(cellIndex[6])));
						category.setCusLoc(formatCell(hssfRow.getCell(cellIndex[6])));
						if(hssfRow.getCell(cellIndex[7]) == null || "".equals(formatCell(hssfRow.getCell(cellIndex[7])))){
						}else{
							category.setOfferPri(Float.valueOf(formatCell(hssfRow.getCell(cellIndex[7])).toString()));
						}
						category.setOfferAging(formatCell(hssfRow.getCell(cellIndex[8])));
						category.setCreateUser(userId);
						category.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
						category.setUpdateUser(userId);
						category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
						categorys.add(category);*/
					}else{
						HtmlMessage html = new HtmlMessage(new Category());
						html.setStatusCode("400");
						html.setMessage("第"+(rowNum+1)+"行[品类名称]不能为空，数据导入终止！");
						
						return html;
					}
				}catch(Exception e){
					e.printStackTrace();
					HtmlMessage html = new HtmlMessage(new Category());
					html.setStatusCode("400");
					html.setMessage("第"+(rowNum+1)+"行数据有误，请检查！");
					
					return html;
				}
			}
		}
		
		try {
			categoryService.insertExcelCategory(categorys);
		} catch (Exception e) {
			log.error("[CategoryController.excelImportFile]数据写入异常"+e.getMessage());
			return new HtmlMessage("更新失败，请检查后重试。");
		}
		return new HtmlMessage(new Category());
	}
	
	@RequestMapping("/{categoryId}")
	@ResponseBody
	public Category getByCategoryId(@PathVariable("categoryId") String categoryId){
		Category category = categoryService.selectByPrimaryKey(categoryId);
		RelCategoryPrice catePri = catPriSer.selectByCategoryId(categoryId);
		try {
			category.setCateSup(catePri.getCateSup());
		} catch (Exception e) {
			//TODO:出现异常，品类关系对象没查出来(就是这个品类还没有填写报盘价或者供应商信息)，那么这里会抛空指针，页面上的供应商一栏为空。
		}
		return category;
	}
	
	
	@RequestMapping("/updateCategoryPrice")
	@ResponseBody
	public ResultInfo updateCategoryPrice(@Valid @RequestParam(value = "userId") String userId,@Valid RelCategoryPrice CategoryPrice){
		RelCategoryPrice catePri = CategoryPrice;
		ResultInfo readResult = new ResultInfo();
		String now = DateUtils.getNowTime();
		try {
			catePri.setUpdateTime(now);
			catePri.setUpdateUser(userId);
			catePri.setUniMgrId(userId);
			catePri.setOfferUpdateTime(now);
			catPriSer.createOrUpdateOfferPriceAndTimeByCategoryId(readResult, catePri);
			readResult.setCode("200");
			readResult.setMessage("更新成功！");
		} catch (Exception e) {
			readResult.setCode("500");
			readResult.setMessage(e.getMessage());
			log.error("[CategoryController.updateCategoryPrice]数据更新异常", e);
		}
		
		return readResult;
	}
}
