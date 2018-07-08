package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bsh.ParseException;
import vn.com.vhc.vmsc2.statistics.dao.HIpbbNodeBDAO;
import vn.com.vhc.vmsc2.statistics.domain.HIpbbNodeB;
import vn.com.vhc.vmsc2.statistics.web.utils.NumberTools;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) by VHCSoft 2016
 * </p>
 * <p>
 * Company: VietNam High Technology Software JSC.
 * </p>
 * <p>
 * Create Date: Jun 6, 2016
 * </p>
 * 
 * @author VHC - Software
 * @version 1.0
 */
@Controller
@RequestMapping("/network/ipbb-nodeb/*")
public class HIpbbNodeBController extends BaseController{
	@Autowired
	private HIpbbNodeBDAO hIpbbNodeBDao;
	
	@RequestMapping(value = "list")
	public ModelAndView list(@RequestParam(required = false) String nodeId, @RequestParam(required = false) String type,
			@RequestParam(required = false) String tentram, Model model) {
		
		String title = "";
		if(type.equals("4G")){
			title = "Danh sách nodeB đối tác 4G";
		}else if(type.equals("3G")){
			title = "Danh sách nodeB đối tác 3G";
		}else{
			title = "Danh sách nodeB đối tác trạm mới";
		}
		
		List<HIpbbNodeB> ipbbNodeBList = hIpbbNodeBDao.getDataByFilter(nodeId, tentram, type);
		
		model.addAttribute("ipbbNodeBList", ipbbNodeBList);
		model.addAttribute("nodeId", nodeId);
		model.addAttribute("tentram", tentram);
		model.addAttribute("title", title);
		model.addAttribute("type", type);
		
		return new ModelAndView("/ipbbNodeB/ipbbNodeBList");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) int id, @RequestParam(required = false) String type,HttpServletRequest request) {
		
		hIpbbNodeBDao.deleteByPrimaryKey(id);
		saveMessage(request, "NodeB đối tác được xóa thành công.");
		
		return "redirect:list.htm?type="+type;
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id, @RequestParam(required = false) String type, ModelMap model) {
		String title = "";
		HIpbbNodeB hIpbbNodeB = (id == null) ? new HIpbbNodeB() : hIpbbNodeBDao.selectByPrimaryKey(id);
		
		if(id == null) title = "Thêm mới NodeB đối tác";
		else title = "Chỉnh sửa NodeB đối tác";
		
		model.addAttribute("hIpbbNodeB",hIpbbNodeB);
		model.addAttribute("title", title);
		model.addAttribute("type", type);
		
		return "/ipbbNodeB/ipbbNodeBForm"; 
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("hIpbbNodeB") @Valid HIpbbNodeB hIpbbNodeB, BindingResult result, 
			@RequestParam(required = false) String type, HttpServletRequest request,Model model, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		
		if (!result.hasErrors())
		{
			if(hIpbbNodeB.getId() == null){ 
				try {
					hIpbbNodeB.setCreateBy(username);
					hIpbbNodeB.setCreateDate(new Date());
					hIpbbNodeBDao.insert(hIpbbNodeB);
					saveMessage(request, "NodeB đối tác được tạo thành công");
				} catch (Exception e) {
					saveMessage(request, "NodeB ID đã tồn tại");
					model.addAttribute("title", "Thêm mới NodeB đối tác");
					model.addAttribute(hIpbbNodeB);
					model.addAttribute("type", type);
					
					return "/ipbbNodeB/ipbbNodeBForm";
				} 
			}else{
				try {
					hIpbbNodeB.setModifiedBy(username);
					hIpbbNodeB.setModifyDate(new Date());
					hIpbbNodeBDao.updateByPrimaryKey(hIpbbNodeB);
					saveMessage(request, "NodeB đối tác được sửa thành công");
				} catch (Exception e) {
					saveMessage(request, "NodeB ID đã tồn tại");
					model.addAttribute("title", "Chỉnh sửa NodeB đối tác");
					model.addAttribute(hIpbbNodeB);
					model.addAttribute("type", type);
					
					return "/ipbbNodeB/ipbbNodeBForm";
				}
			}
	
			return "redirect:list.htm?type="+type;
		}else{
				model.addAttribute("hIpbbNodeB", hIpbbNodeB);
				model.addAttribute("type", type);
				
				return "/ipbbNodeB/ipbbNodeBForm";
			}
	}
	
	// Upload File
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String type, HttpServletRequest request,Model model, HttpServletResponse response) {
		model.addAttribute("type", type);
		
		return "/ipbbNodeB/ipbbNodeBUpload";
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath, @RequestParam(required = false) String type,  Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<HIpbbNodeB> nodeBSuccessList = new ArrayList<HIpbbNodeB>();
		List<HIpbbNodeB> nodeBFailedList = new ArrayList<HIpbbNodeB>();
		HIpbbNodeB linkNodeB = null;
		HIpbbNodeB oldData = null;
		int total = 0;
		
		if (!filePath.isEmpty()) {

			String[] ten = filePath.getOriginalFilename().split("\\.");
			
			String fileExtn = ten[ten.length-1]; 
			
			if (fileExtn.equals("xls")) {
				/**Danh dau trang thai ban ghi: 
				true: ban ghi dung
				false: ban ghi loi*/
				boolean lineStatus = true;
				
				 
				List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
				
				if (sheetData.size() > 0) {
					// Kiem tra so cot cua file
		        	List heard= (List)sheetData.get(0);
		        	if (heard.size() != 6 ) {
		        		saveMessage(request, "Số lượng cột dữ liệu của file không đúng.");
		        		model.addAttribute("type", type);
		        		return "/ipbbNodeB/ipbbNodeBUpload";
		        	}
		        	
		        	// Xoa dong head trong file
		        	sheetData.remove(0);
		        	
		        	total = sheetData.size();
		        	
		        	// Doc du lieu trong file
		        	for (int i = 0; i < sheetData.size(); i++) { 
		        		List list = (List) sheetData.get(i);
		        		
		        		linkNodeB = new HIpbbNodeB();
		        		lineStatus = true;
		        		
		        		String nodebId =list.get(0).toString();
						String tendoitac = list.get(1).toString();
						String tentram = list.get(2).toString();
						String matram= list.get(3).toString();
						String bw= list.get(4).toString();
						String ghichu= list.get(5).toString();
		        		
						try {
							nodebId = nodebId.substring(0,nodebId.indexOf("."));
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						try {
							bw = bw.substring(0,bw.indexOf("."));
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						if(NumberTools.checkNumber(nodebId)){
							linkNodeB.setNodebId(Integer.valueOf(nodebId));
						}else{
							linkNodeB.setNodebIDStr(nodebId);
							lineStatus = false;
						}
						
						if(NumberTools.checkNumber(bw)){
							linkNodeB.setBw(Integer.valueOf(bw));
						}else{
							linkNodeB.setBwStr(bw);
							lineStatus = false;
						}
						
						linkNodeB.setTenTram(tentram);
						linkNodeB.setDescription(ghichu);
						linkNodeB.setStt(i+1);
						linkNodeB.setTenDoiTac(tendoitac);
						linkNodeB.setMaTram(matram);
						
						if(lineStatus){
							// Kiem tra du lieu da ton tai?
							oldData = hIpbbNodeBDao.selectByNodeBId(linkNodeB.getNodebId());
							
							try {
								if(oldData != null){
									linkNodeB.setId(oldData.getId());
									linkNodeB.setModifiedBy(username);
									linkNodeB.setModifyDate(new Date());
									
									hIpbbNodeBDao.updateByPrimaryKey(linkNodeB);
								}else{
									linkNodeB.setCreateBy(username);
									linkNodeB.setCreateDate(new Date());
									
									hIpbbNodeBDao.insert(linkNodeB);
								}
								
								nodeBSuccessList.add(linkNodeB);
							} catch (Exception e) {
								nodeBFailedList.add(linkNodeB);
							} 
						}else{
							nodeBFailedList.add(linkNodeB);
						}
		    		}
		        	
		        	saveMessage(request, "Import file thành công.");  
				}
				else
					saveMessage(request, "File không có dữ liệu.");		 
			}
			else
			{
				saveMessage(request, "Import file không thành công. File không đúng định dạng .xls");
				model.addAttribute("type", type);
				return "/ipbbNodeB/ipbbNodeBUpload";
			}
		}
		else
		{
			saveMessage(request, "Không tìm thấy file.");
		}
		
		model.addAttribute("nodeBSuccessList",nodeBSuccessList);
		model.addAttribute("nodeBFailedList",nodeBFailedList);
		model.addAttribute("importSuccess", nodeBSuccessList.size());
		model.addAttribute("importFailed",nodeBFailedList.size());
		model.addAttribute("total",total);
		model.addAttribute("type", type);
		
		return "/ipbbNodeB/ipbbNodeBUpload";
	}
}
