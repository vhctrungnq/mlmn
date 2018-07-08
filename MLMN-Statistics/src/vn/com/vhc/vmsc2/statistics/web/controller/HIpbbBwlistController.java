package vn.com.vhc.vmsc2.statistics.web.controller;

/**
 * 
 * Edited by TrungNQ 31/5/2016
 * Them 4 cot diem dau, diem cuoi, don vi quan li, doi tac
 * Doi ten description thanh ghi chu (title hien thi tren file excel)
 * 2/6/2016 cap nhat khoa (direction, link), sua lai method filterByKey
 */

import java.io.IOException;
import java.util.ArrayList;
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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HIpbbBwlistDAO;
import vn.com.vhc.vmsc2.statistics.dao.SYS_PARAMETERDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.HIpbbBwlist;
import vn.com.vhc.vmsc2.statistics.domain.MDepartment;
import vn.com.vhc.vmsc2.statistics.domain.SYS_PARAMETER;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.filter.HIpbbBwlistFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;
import bsh.ParseException;

@Controller
@RequestMapping("/network/ipbb/*")
public class HIpbbBwlistController extends BaseController{
	private HIpbbBwlistDAO hipbbbwlistDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private SysUsersDAO sysUsersDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao; 
	@Autowired
	public void setHIpbbBwlistDao(HIpbbBwlistDAO hipbbbwlistDao) {
		this.hipbbbwlistDao = hipbbbwlistDao;
	}
	
	private static final int COLUMN_NUMBER = 19;

	@RequestMapping(value = "list")
	public ModelAndView list(@ModelAttribute("filter") HIpbbBwlistFilter filter, Model model) {
		List<HIpbbBwlist> IpbbBwlists = hipbbbwlistDao.filter(filter);
		model.addAttribute("IpbbBwList", IpbbBwlists);
		List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
		model.addAttribute("vendorForResourceList", vendorForResourceList);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);
		
		return new ModelAndView("ipbbBwList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) int id, HttpServletRequest request) {
		
		hipbbbwlistDao.deleteByPrimaryKey(id);
		saveMessage(request, "Direction/link được xóa thành công.");
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {

		HIpbbBwlist hipbbBw  = new HIpbbBwlist();
		List<MDepartment> deptList = new ArrayList<MDepartment>();
		List<MDepartment> teamList = new ArrayList<MDepartment>();
		List<MDepartment> subteamList = new ArrayList<MDepartment>();
		
		if(id == null){
			//Add new
			hipbbBw = new HIpbbBwlist();
			deptList = bscDao.getDept();
			teamList = bscDao.getTeamByDept(null);
			bscDao.getSubTeamByTeam(null, null);
		}else{
			//Edit
			hipbbBw = hipbbbwlistDao.selectByPrimaryKey(id);
			deptList = bscDao.getDept();
			teamList = bscDao.getTeamByDept(hipbbBw.getDept());
			subteamList = bscDao.getSubTeamByTeam(hipbbBw.getDept(), hipbbBw.getTeam());
		}
		List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
		model.addAttribute("vendorForResourceList", vendorForResourceList);
		model.addAttribute("hipbbBw", hipbbBw);
		model.addAttribute("deptList", deptList);
		model.addAttribute("teamList", teamList);
		model.addAttribute("subteamList", subteamList);
		
		return "hipbbBwForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("hipbbBw") @Valid HIpbbBwlist hipbbBw, BindingResult result,
			HttpServletRequest request,Model model) {

		List<MDepartment> deptList = bscDao.getDept();
		List<MDepartment> teamList = bscDao.getTeamByDept(hipbbBw.getDept()); 
		List<MDepartment> subteamList = bscDao.getSubTeamByTeam(hipbbBw.getDept(), hipbbBw.getTeam());
		List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
		model.addAttribute("vendorForResourceList", vendorForResourceList);
		if (!result.hasErrors())
		{
			if(hipbbBw.getId() == null)
			{
				try {
					hipbbbwlistDao.insert(hipbbBw);
					saveMessage(request, "Direction/link đã được tạo thành công.");
				} catch (Exception e) {
					// TODO: handle exception
					saveMessage(request, "Direction/link đã tồn tại");
					model.addAttribute("hipbbBw", hipbbBw);
					model.addAttribute("deptList", deptList);
					model.addAttribute("teamList", teamList);
					model.addAttribute("subteamList", subteamList);
					return "hipbbBwForm";
				}
			}
			else
			{
				try {
					hipbbbwlistDao.updateByPrimaryKey(hipbbBw);
					saveMessage(request, "Direction/link đã được cập nhật thành công.");	
				} catch (Exception e) {
					// TODO: handle exception
					saveMessage(request, "Direction/link đã tồn tại");
					model.addAttribute("hipbbBw", hipbbBw);
					model.addAttribute("deptList", deptList);
					model.addAttribute("teamList", teamList);
					model.addAttribute("subteamList", subteamList);
					return "hipbbBwForm";
				}
			}
			
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute("hipbbBw", hipbbBw);
			return "hipbbBwForm";
		}
	}
	
	// Upload File
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "IpbbUpload";
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten = filePath.getOriginalFilename().split("\\.");
			
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				try
				{ 
					List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
					
					
					if (sheetData.size() > 0) {
						// Kiem tra số cột của file
			        	List heard= (List)sheetData.get(0);
			        	if (heard.size() != COLUMN_NUMBER ) {
			        		saveMessage(request, "Số lượng cột dữ liệu ipbb của file không đúng");
			        		return "IpbbUpload";
			        	}
			        	sheetData.remove(0);
			        	
			        	for (int i = 0; i < sheetData.size(); i++) {
			        		
			        		List list = (List) sheetData.get(i);
			    			
			        		if(list.size() != COLUMN_NUMBER )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("IpbbBwList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Bạn nhập thiếu thông tin");
			            		return "IpbbUpload";
			        		}
			        		 
			        		String direction =list.get(2).toString();
			        		
			        		if(direction.length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("IpbbBwList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu Direction không có");
			            		return "IpbbUpload";
			        		}
			    		} 
			        		importXlsContent(sheetData, model,  request);
				        	saveMessage(request, "Import file thành công.");
							 
					}
					else
						saveMessage(request, "Không có dữ liệu để insert");		
				}
				catch(IOException e)
				{
					saveMessage(request, "Import lỗi.");
				}
			}
			else
			{
				saveMessage(request, "Import file không thành công");
				return "IpbbUpload";
			}
		}
		else
		{
			saveMessage(request, "Import lỗi.");
		}
		return "IpbbUpload";
	}
	
	@SuppressWarnings({ "rawtypes" })
	private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{	
        excecuteImport(sheetData, model, request);
        
	}
	
	@SuppressWarnings("rawtypes")
	private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{
		List<HIpbbBwlist> IpbbBwList = new ArrayList<HIpbbBwlist>();
		for (int i = 0; i < sheetData.size(); i++) {
			
			/*try
			{*/
				HIpbbBwlist record = new HIpbbBwlist();
				
				List list = (List) sheetData.get(i);
				String vendor = list.get(1).toString(); 
				String direction =list.get(2).toString();
				String link = list.get(3).toString();
				String interfaceName = list.get(4).toString();
				String interfaceService = list.get(5).toString();
				String BandWidth= list.get(6).toString();
				String IP= list.get(7).toString();
				String LocalId= list.get(8).toString(); 
				String Dept= list.get(9).toString();
				String Team= list.get(10).toString(); 
				String Subteam= list.get(11).toString();
				String LocationName= list.get(12).toString();
				String Pha= list.get(13).toString();
				String diemDau = list.get(14).toString();
				String diemCuoi = list.get(15).toString();
				String donVi = list.get(16).toString();
				String doiTac = list.get(17).toString();
				String description= list.get(18).toString();
				
				record.setDirection(direction);
				
				record.setLink(link);
				
				record.setBw(Float.parseFloat(BandWidth));
				
				if(Pha.isEmpty()){
					record.setPha(Float.parseFloat("0"));
				}
				else
					record.setPha(Float.parseFloat(Pha));
				
				record.setIp(IP);
				if(LocalId.isEmpty()){
					record.setLocalId(Float.parseFloat("0"));
				} else {
					record.setLocalId(Float.parseFloat(LocalId));
				}
				
				record.setLocationName(LocationName);
				record.setDept(Dept);
				record.setTeam(Team);
				record.setSubTeam(Subteam);
				record.setVendor(vendor);
				record.setInterfaceName(interfaceName);
				record.setInterfaceService(interfaceService);
				record.setDescription(description);
				record.setDiemDau(diemDau);
				record.setDiemCuoi(diemCuoi);
				record.setDonViQuanLy(donVi);
				record.setDoiTacTruyenDan(doiTac);
				
				HIpbbBwlist hipbb = getDuplicate(record);
				if(hipbb != null){
					record.setId(hipbb.getId());
					hipbbbwlistDao.updateByPrimaryKey(record);
					
				} else {
					hipbbbwlistDao.insertSelective(record);
					
				}
				
			/*}
			catch(Exception exp)
			{	
				System.out.println(exp.toString()); 
			}*/
		}
		
		model.addAttribute("IpbbBwList",IpbbBwList);
	}
	private HIpbbBwlist getDuplicate(HIpbbBwlist record){
		HIpbbBwlistFilter filter = new HIpbbBwlistFilter();
		filter.setDirection(record.getDirection());
		filter.setLink(record.getLink());;
		HIpbbBwlist hipbb = hipbbbwlistDao.filterBykey(filter);
		return hipbb;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<HIpbbBwlist> errorlist(List sheet)
	{
		List<HIpbbBwlist> IpbbBwList = new ArrayList<HIpbbBwlist>();
		HIpbbBwlist record = new HIpbbBwlist();
		int size= sheet.size();
    	for (int i=size;i<=COLUMN_NUMBER - 1;i++)
    		sheet.add("");
		record.setDirection(sheet.get(1).toString());
		record.setLink(sheet.get(2).toString());
		try
		{
			if(sheet.get(4).toString().isEmpty()){
				record.setPha(Float.parseFloat("0"));
			}
			else
				record.setPha(Float.parseFloat(sheet.get(4).toString()));
			
			if(sheet.get(3).toString().isEmpty()){
				record.setBw(Float.parseFloat("0"));
			}
			else
				record.setBw(Float.parseFloat(sheet.get(3).toString()));
			
			
		}
		catch(Exception exp){}	
		record.setDescription(sheet.get(5).toString());
		IpbbBwList.add(record);
		return IpbbBwList;
	}
}
