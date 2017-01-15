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
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.RelCategoryPrice;
import com.rhtop.buss.common.entity.ResultInfo;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.common.utils.Constant;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.utils.PropertyUtil;
import com.rhtop.buss.common.web.BaseController;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/category")
public class CategoryController  extends BaseController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private RelCategoryPriceService catPriSer;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveCategory(@Valid @RequestParam(value = "userId") String userId,@Valid Category category){
		if(category.getCategoryId() == null || "".equals(category.getCategoryId())){
			String categoryId = UUID.randomUUID().toString().replace("-", "");
			category.setCategoryId(categoryId);
			category.setCreateUser(userId);
			category.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			category.setUpdateUser(userId);
			category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			categoryService.insertCategory(category);
		}else{
			category.setUpdateUser(userId);
			category.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			categoryService.updateCategory(category);
		}
		return new HtmlMessage(category);
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
					category.setOfferPri(new BigDecimal(formatCell(hssfRow.getCell(8)).toString()));
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
	
	/**
	 * 国际人员 pc
	 * 回盘信息 
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/ctofPrice")
	@ResponseBody
	public List<Category> getCtofPrice(){
		System.out.println(1);
		Category category = new Category();
		List<Category> categorylist = categoryService.listCategorys(category);
		return categorylist;
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
			catPriSer.createOrUpdateOfferPriceAndTimeByCategoryId(catePri);
		} catch (Exception e) {
			log.error("[CategoryController.updateCategoryPrice]数据更新异常", e);
		}
		readResult.setCode("200");
		readResult.setMessage("更新成功！");
		return readResult;
	}
}
