
package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellDAO;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCell;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;

@Controller
@RequestMapping("/report/*")
public class BadCell2gDropController extends BaseController{

	@Autowired
	private VRpDyCellDAO  vRpDyCellDAO ;
	long currentTime = System.currentTimeMillis();	
	DateTime  dt = new DateTime(currentTime- 24 * 60 * 60 * 1000);;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping("dropper-tch/cell/dy")
	public ModelAndView listtDrpr(
		 @ModelAttribute("filter") CellFilter filtercell,
		 @RequestParam(required = false) String bscid,
		 @RequestParam(required = false) String cellid,
		 @RequestParam(required = false) Date startDate, 
		 @RequestParam(required = false) Date endDate,
		 ModelMap model, HttpServletRequest request)
			{
		
				if (endDate == null) {
					endDate = new Date();
				   
				}
				if (startDate == null) {
					startDate = dt.minusDays(7).toDate();
				}
				if(bscid == null){
					bscid = "";
				}
				if(cellid == null){
					cellid = "";
				}
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("bscid",bscid);
				model.addAttribute("cellid",cellid);
				
				List<VRpDyCell> vRpDyCellList = new ArrayList<VRpDyCell>();
				vRpDyCellList.addAll(vRpDyCellDAO.filtertDrpr(df.format(startDate), df.format(endDate),bscid,cellid));	
				List<VRpDyCell> vRpDyCellList2 = new ArrayList<VRpDyCell>();
				int t=0,k =0;
				for(int i=0;i<vRpDyCellList.size();i++){
					if(vRpDyCellList.get(i).gettDrpr() > 3){
						t++;
						k=i-t+1;
					}
					else{
						t=0;
						k=0;
					}
					if(t==5){
						for(int j=k; j< k+t; j++){
							vRpDyCellList2.add(vRpDyCellList.get(j));
						}						
					}
					else if(t>5){
						vRpDyCellList2.add(vRpDyCellList.get(k+t-1));
					}
				}
				List<VRpDyCell> vRpDyCellList3 = new ArrayList<VRpDyCell>();
				int dd=0;//Lưu độ dài của chuỗi
				int j1=0;//Biến để trỏ đến các vị trí đầu tiên của các chuỗi con cần xét
				for(int i=j1;i<vRpDyCellList2.size();i++){
					String s1 = vRpDyCellList2.get(j1).getCellid();
					String s2 = vRpDyCellList2.get(i).getCellid();
					if(s1.equals(s2)==true){
						dd++;
					}else{
						dd=0;
						j1=i;
					}
					if(dd==5){
						for(int j=j1; j< j1+dd; j++){
							vRpDyCellList3.add(vRpDyCellList2.get(j));
						}
					}
					else if(dd>5){
						vRpDyCellList3.add(vRpDyCellList2.get(j1+dd-1));
					}
				}
				model.addAttribute("vRpDyCellList2", vRpDyCellList3);
				return new ModelAndView("dyBadCell2gTchDrpr");
			}	
	@RequestMapping("dropper-sdcch/cell/dy")
	public ModelAndView listsDrpr(
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@ModelAttribute("filter") CellFilter filtercell,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model, HttpServletRequest request)
		{
			{
				if (endDate == null) {
					endDate = new Date();
				}
				if (startDate == null) {
					startDate = dt.minusDays(7).toDate();
				}
				if(bscid == null){
					bscid = "";
				}
				if(cellid == null){
					cellid = "";
				}
				model.addAttribute("bscid",bscid);
				model.addAttribute("cellid",cellid);
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				
				List<VRpDyCell> vRpDyCellsDrpr = new ArrayList<VRpDyCell>();
				vRpDyCellsDrpr.addAll(vRpDyCellDAO.filtersDrpr(df.format(startDate), df.format(endDate),bscid,cellid));	
				List<VRpDyCell> vRpDyCellsDrpr2 = new ArrayList<VRpDyCell>();
				int t=0,k =0;
				for(int i=0;i<vRpDyCellsDrpr.size();i++){
					if(vRpDyCellsDrpr.get(i).getsDrpr() > 3){
						t++;
						k=i-t+1;//
					}
					else{
						t=0;
						k=0;
					}
					if(t==5){
						for(int j=k; j< k+t; j++){
							vRpDyCellsDrpr2.add(vRpDyCellsDrpr.get(j));
						}						
					}
				}
				List<VRpDyCell> vRpDyCellsDrpr3 = new ArrayList<VRpDyCell>();
				int dd=0;//Lưu độ dài của chuỗi
				int j1=0;//Biến để trỏ đến các vị trí đầu tiên của các chuỗi con cần xét
				for(int i=j1;i<vRpDyCellsDrpr2.size();i++){
					String s1 = vRpDyCellsDrpr2.get(j1).getCellid();
					String s2 = vRpDyCellsDrpr2.get(i).getCellid();
					if(s1.equals(s2)==true){
						dd++;
					}else{
						dd=0;
						j1=i;
					}
					if(dd==5){
						for(int j=j1; j< j1+dd; j++){
							vRpDyCellsDrpr3.add(vRpDyCellsDrpr2.get(j));
						}
					}
					else if(dd>5){
						vRpDyCellsDrpr3.add(vRpDyCellsDrpr2.get(j1+dd-1));
					}
				}
				model.addAttribute("vRpDyCellsDrpr2", vRpDyCellsDrpr3);
				return new ModelAndView("dyBadCell2gSDCCHDrpr");
			}
	}
	@RequestMapping("blocking-tch/cell/dy")
	public ModelAndView listtBlkr(
		 @RequestParam(required = false) String bscid,
		 @RequestParam(required = false) String cellid,
		 @RequestParam(required = false) Date startDate, 
		 @RequestParam(required = false) Date endDate,
		 @ModelAttribute("filter") CellFilter filtercell,
		 ModelMap model, HttpServletRequest request)
			{
				if (endDate == null) {
					endDate = new Date();
				}
				if (startDate == null) {
					startDate = dt.minusDays(7).toDate();
				}
				if(bscid == null){
					bscid = "";
				}
				if(cellid == null){
					cellid = "";
				}
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("bscid",bscid);
				model.addAttribute("cellid",cellid);
				
				List<VRpDyCell> vRpDyCelltBlkr = new ArrayList<VRpDyCell>();
				vRpDyCelltBlkr.addAll(vRpDyCellDAO.filtertBlkr(df.format(startDate), df.format(endDate),bscid,cellid));	
				List<VRpDyCell> vRpDyCelltBlkr2 = new ArrayList<VRpDyCell>();
				int t=0,k =0;
				for(int i=0;i<vRpDyCelltBlkr.size();i++){
					if(vRpDyCelltBlkr.get(i).gettBlkr() > 3){
						t++;
						k=i-t+1;
					}
					else{
						t=0;
						k=0;
					}
					if(t==5){
						for(int j=k; j< k+t; j++){
							vRpDyCelltBlkr2.add(vRpDyCelltBlkr.get(j));
						}						
					}
					else if(t>5){
						vRpDyCelltBlkr2.add(vRpDyCelltBlkr.get(k+t-1));
					}
				}
				List<VRpDyCell> vRpDyCelltBlkr3 = new ArrayList<VRpDyCell>();
				int dd=0;//Lưu độ dài của chuỗi
				int j1=0;//Biến để trỏ đến các vị trí đầu tiên của các chuỗi con cần xét
				for(int i=j1;i<vRpDyCelltBlkr2.size();i++){
					String s1 = vRpDyCelltBlkr2.get(j1).getCellid();
					String s2 = vRpDyCelltBlkr2.get(i).getCellid();
					if(s1.equals(s2)==true){
						dd++;
					}else{
						dd=0;
						j1=i;
					}
					if(dd==5){
						for(int j=j1; j< j1+dd; j++){
							vRpDyCelltBlkr3.add(vRpDyCelltBlkr2.get(j));
						}
					}
					else if(dd>5){
						vRpDyCelltBlkr3.add(vRpDyCelltBlkr2.get(j1+dd-1));
					}
				}
				model.addAttribute("vRpDyCelltBlkr2", vRpDyCelltBlkr3);
				return new ModelAndView("dyBadCell2gTchBlkr");
			}
	@RequestMapping("blocking-sdcch/cell/dy")
	public ModelAndView listsBlkr(
		 @RequestParam(required = false) String bscid,
		 @RequestParam(required = false) String cellid,
		 @RequestParam(required = false) Date startDate, 
		 @RequestParam(required = false) Date endDate,
		 @ModelAttribute("filter") CellFilter filtercell,
		 ModelMap model, HttpServletRequest request)
			{
				if (endDate == null) {
					endDate = new Date();
				}
				if (startDate == null) {
					startDate = dt.minusDays(7).toDate();
				}
				if(bscid == null){
					bscid = "";
				}
				if(cellid == null){
					cellid = "";
				}
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("bscid",bscid);
				model.addAttribute("cellid",cellid);
				
				List<VRpDyCell> vRpDyCellsBlkr = new ArrayList<VRpDyCell>();
				vRpDyCellsBlkr.addAll(vRpDyCellDAO.filtersBlkr(df.format(startDate), df.format(endDate),bscid,cellid));	
				List<VRpDyCell> vRpDyCellsBlkr2 = new ArrayList<VRpDyCell>();
				int t=0,k =0;
				for(int i=0;i<vRpDyCellsBlkr.size();i++){
					if(vRpDyCellsBlkr.get(i).getsBlkr() > 3){
						t++;
						k=i-t+1;
					}
					else{
						t=0;
						k=0;
					}
					if(t==5){
						for(int j=k; j< k+t; j++){
							vRpDyCellsBlkr2.add(vRpDyCellsBlkr.get(j));
						}						
					}
					else if(t>5){
						vRpDyCellsBlkr2.add(vRpDyCellsBlkr.get(k+t-1));
					}
				}

				List<VRpDyCell> vRpDyCellsBlkr3 = new ArrayList<VRpDyCell>();
				int dd=0;//Lưu độ dài của chuỗi
				int j1=0;//Biến để trỏ đến các vị trí đầu tiên của các chuỗi con cần xét
				for(int i=j1;i<vRpDyCellsBlkr2.size();i++){
					String s1 = vRpDyCellsBlkr2.get(j1).getCellid();
					String s2 = vRpDyCellsBlkr2.get(i).getCellid();
					if(s1.equals(s2)==true){
						dd++;
					}else{
						dd=0;
						j1=i;
					}
					if(dd==5){
						for(int j=j1; j< j1+dd; j++){
							vRpDyCellsBlkr3.add(vRpDyCellsBlkr2.get(j));
						}
					}
					else if(dd>5){
						vRpDyCellsBlkr3.add(vRpDyCellsBlkr2.get(j1+dd-1));
					}
				}
				model.addAttribute("vRpDyCellsBlkr2", vRpDyCellsBlkr3);
				return new ModelAndView("dyBadCell2gSDCCHBlkr");
			}
}
