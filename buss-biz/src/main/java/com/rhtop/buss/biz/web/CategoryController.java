package com.rhtop.buss.biz.web;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

import net.sf.json.JSONObject;

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

import com.fasterxml.jackson.databind.ObjectMapper;
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
				BigDecimal offerPri = UnitUtils.unitConver(category.getCurrency(), new BigDecimal(category.getOfferPri()), category.getUnit(), rate);
				//换算好的报盘价添加到品类表中
				category.setOfferPri(offerPri.floatValue());
				category.setOfferAging(category.getOfferAging());
				categoryService.insertCategory(category);
				//将供应商，货币单位，计量单位，报价，时效,品类主键加入到品类与价格关系表中
				RelCategoryPrice relCategoryPrice = new RelCategoryPrice();
				relCategoryPrice.setRelCategoryPriceId(UUID.randomUUID().toString().replace("-", ""));
				relCategoryPrice.setCategoryId(categoryId);
				relCategoryPrice.setCateSup(category.getCateSup());
				relCategoryPrice.setCurrency(category.getCurrency());
				relCategoryPrice.setOfferPri(category.getOfferPri());
				relCategoryPrice.setOfferAging(category.getOfferAging());
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
				relCategoryPrice.setOfferPri(category.getOfferPri());
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
			//遍历excel,从第三行开始 即 rowNum=2,逐个获取单元格的内容,然后进行格式处理,最后插入数据库
			for(int rowNum=2;rowNum<=hssfSheet.getLastRowNum();rowNum++){
				HSSFRow hssfRow=hssfSheet.getRow(rowNum);
				if(hssfRow==null||hssfRow.getCell(0) == null||"".equals(formatCell(hssfRow.getCell(0)))){
					continue;
				}
				Category category = new Category();
				category.setCategoryId(UUID.randomUUID().toString().replace("-", ""));
				category.setCateName(formatCell(hssfRow.getCell(0)));
				category.setCateStan(formatCell(hssfRow.getCell(1)));
				category.setPkgQuan(formatCell(hssfRow.getCell(2)));
				category.setManuNum(formatCell(hssfRow.getCell(3)));
				category.setProdPla(formatCell(hssfRow.getCell(4)));
				category.setComm(formatCell(hssfRow.getCell(5)));
				category.setCusCha(formatCell(hssfRow.getCell(6)));
				category.setCusLoc(formatCell(hssfRow.getCell(7)));
				if(hssfRow.getCell(8) == null || "".equals(formatCell(hssfRow.getCell(8)))){
				}else{
					category.setOfferPri(Float.valueOf(formatCell(hssfRow.getCell(8)).toString()));
				}
				category.setOfferAging(formatCell(hssfRow.getCell(9)));
				category.setCreateUser(userId);
				category.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
				category.setUpdateUser(userId);
				category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
				categorys.add(category);
			}
		}
		categoryService.insertExcelCategory(categorys);
		return new HtmlMessage(new Category());
	}
	@RequestMapping("/{categoryId}")
	@ResponseBody
	public Category getByCategoryId(@PathVariable("categoryId") String categoryId){
		Category category = categoryService.selectByPrimaryKey(categoryId);
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
