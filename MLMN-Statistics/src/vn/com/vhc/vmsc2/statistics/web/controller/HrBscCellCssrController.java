package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.RpHrKpiBadDAO;
import vn.com.vhc.vmsc2.statistics.dao.SYS_PARAMETERDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrBscCssrDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellCssrDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.RpHrKpiBad;
import vn.com.vhc.vmsc2.statistics.domain.SYS_PARAMETER;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscCssr;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellCssr;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/bieu-do-giam-sat/*")
public class HrBscCellCssrController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private VRpHrBscCssrDAO vRpHrBscCssrDAO;
	@Autowired
	private VRpHrCellCssrDAO vRpHrCellCssrDAO;
	@Autowired
	private RpHrKpiBadDAO rpHrIpiBadDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String autoRefresh,@RequestParam(required = false) String neType, @RequestParam(required = false) String alarmLevel,  
    		@RequestParam(required = false) String neParent,@RequestParam(required = false) String ne,
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		List <VRpHrBscCssr> listVRpHrRncCssr;
		List <VRpHrBscCssr> listVRpHrBscCssrVTD;
		List <VRpHrBscCssr> listVRpHrBscCssrVTT;
		List <VRpHrCellCssr> listVRpHrCellCssr;
		List<SYS_PARAMETER> sysPramList= sysParameterDao.getSysParameterByCode("TO_VT");
		String to_vtd = "Tổ VTĐ", to_vtt = "Tổ VTT";
		if(sysPramList.size() > 0)
		{
			to_vtd = sysPramList.get(0).getValue();
			to_vtt = sysPramList.get(1).getValue();
		}
		if(function.equals("cssr-2g"))
		{

			List<String> categories_VTD = new ArrayList<String>();
			List<String> categories_VTT = new ArrayList<String>();
			
			
			List<Float> cssrList_VTD = new ArrayList<Float>();
			List<Float> cssrList_VTT = new ArrayList<Float>();
			
			
			List<String> bscIdList_VTD = new ArrayList<String>();
			List<String> bscIdList_VTT = new ArrayList<String>();
			List<Float> ipbbBaseline = new ArrayList<Float>();
			List<Float> ipbbBaseline2 = new ArrayList<Float>();
			int hour = 0;
			listVRpHrBscCssrVTD = vRpHrBscCssrDAO.getListCssr("B", "VTD");
			for(int i=0; i<listVRpHrBscCssrVTD.size();i++)
			{
				
					hour = listVRpHrBscCssrVTD.get(i).getHour();
					cssrList_VTD.add(listVRpHrBscCssrVTD.get(i).getSpeechCssr());
					categories_VTD.add(listVRpHrBscCssrVTD.get(i).getBscid());
					ipbbBaseline.add((float) 95);
					if(listVRpHrBscCssrVTD.get(i).getSpeechCssr() < 95)
					{
						bscIdList_VTD.add(listVRpHrBscCssrVTD.get(i).getBscid());
					}
			 }
			listVRpHrBscCssrVTT = vRpHrBscCssrDAO.getListCssr("B", "VTT");
			for(int i=0; i<listVRpHrBscCssrVTT.size();i++)
			{
				cssrList_VTT.add(listVRpHrBscCssrVTT.get(i).getSpeechCssr());
				categories_VTT.add(listVRpHrBscCssrVTT.get(i).getBscid());
				ipbbBaseline2.add((float) 95);
				if(listVRpHrBscCssrVTT.get(i).getSpeechCssr() < 95)
				{
					bscIdList_VTT.add(listVRpHrBscCssrVTT.get(i).getBscid());
				}
			}
		   
			Map<String, List<Float>> series_VTD = new LinkedHashMap<String, List<Float>>();
			series_VTD.put("BSC " + to_vtd+"--2f7ed8", cssrList_VTD);
			Map<String, List<Float>> series_VTT = new LinkedHashMap<String, List<Float>>();
			series_VTT.put("BSC " + to_vtt+"--2f7ed8", cssrList_VTT);
			
			series_VTD.put("baseline-line-FF0000", ipbbBaseline);
			series_VTT.put("baseline-line-FF0000", ipbbBaseline2);
			// ve theo gio
			Map<String, List<Float>> series_hourly_VTD = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series_hourly_VTT = new LinkedHashMap<String, List<Float>>();
			
			
			List<String> hourlyCatergories = new ArrayList<String>();
			
			for(int i=0; i<bscIdList_VTD.size();i++)
			{
				List<VRpHrBscCssr> vRpHrBsc_VTD = vRpHrBscCssrDAO.filter(bscIdList_VTD.get(i));
				List<Float> hourly_cssrList_VTD = new ArrayList<Float>();
				for(int j=0;j<vRpHrBsc_VTD.size();j++){
					
					hourly_cssrList_VTD.add(vRpHrBsc_VTD.get(j).getSpeechCssr());
					
				}
				series_hourly_VTD.put(bscIdList_VTD.get(i)+ "-" + "column", hourly_cssrList_VTD)	;				
			}
			for(int i=0; i<bscIdList_VTT.size();i++)
			{
				List<VRpHrBscCssr> vRpHrBsc_VTT = vRpHrBscCssrDAO.filter(bscIdList_VTT.get(i));
				List<Float> hourly_cssrList_VTT = new ArrayList<Float>();
				for(int j=0;j<vRpHrBsc_VTT.size();j++){
					
					hourly_cssrList_VTT.add(vRpHrBsc_VTT.get(j).getSpeechCssr());
					
				}
				series_hourly_VTT.put(bscIdList_VTT.get(i)+ "-" + "column", hourly_cssrList_VTT)	;				
			}
			for(Integer i=0;i<hour;i++)
			{
				hourlyCatergories.add(i.toString());
			}
			model.addAttribute("hour", hour);

			model.addAttribute("CssrChart_VTD", Chart.columnChart("CssrChart_VTD", "Thống kê Cssr của "+to_vtd,"Chỉ số Cssr (%)", "85", "100", categories_VTD, series_VTD));
			model.addAttribute("ChartCSSR_H_VTD", Chart.multiColumnWelcome("ChartCSSR_H_VTD", "Bsc của "+to_vtd+" có (Cssr < 95) ", hourlyCatergories, series_hourly_VTD,"0","100"));
			model.addAttribute("CssrChart_VTT", Chart.columnChart("CssrChart_VTT", "Thống kê Cssr của "+to_vtt,"Chỉ số Cssr (%)", "85", "100", categories_VTT, series_VTT));
			model.addAttribute("ChartCSSR_H_VTT", Chart.multiColumnWelcome("ChartCSSR_H_VTT", "Bsc của "+to_vtt+"(Cssr < 95) ", hourlyCatergories, series_hourly_VTT,"0","100"));
			
			/*
			 * 
			 * Added by TrungNQ
			 * Bieu do giam sat bsc co cssr nho hon 70
			 */
			List<String> bscIdList_VTD2 = new ArrayList<String>();
			List<String> bscIdList_VTT2 = new ArrayList<String>();
			for(int i=0; i<listVRpHrBscCssrVTD.size();i++)
			{
				if(listVRpHrBscCssrVTD.get(i).getSpeechCssr() < 70)
				{
					bscIdList_VTD2.add(listVRpHrBscCssrVTD.get(i).getBscid());
				}
			}
			
			for(int i=0; i<listVRpHrBscCssrVTT.size();i++)
			{
				if(listVRpHrBscCssrVTT.get(i).getSpeechCssr() < 70)
				{
					bscIdList_VTT2.add(listVRpHrBscCssrVTT.get(i).getBscid());
				}
			}
			
			Map<String, List<Float>> series_hourly_VTD2 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series_hourly_VTT2 = new LinkedHashMap<String, List<Float>>();
			
			for(String bscid : bscIdList_VTD2)
			{
				List<VRpHrBscCssr> vRpHrBsc_VTD = vRpHrBscCssrDAO.filter(bscid);
				List<Float> hourly_cssrList_VTD2 = new ArrayList<Float>();		
				for(VRpHrBscCssr v : vRpHrBsc_VTD){
					
					hourly_cssrList_VTD2.add(v.getSpeechCssr());
					
				}
				series_hourly_VTD2.put(bscid+ "-" + "column", hourly_cssrList_VTD2)	;				
			}
			for(String bscid : bscIdList_VTT2)
			{
				List<VRpHrBscCssr> vRpHrBsc_VTT = vRpHrBscCssrDAO.filter(bscid);
				List<Float> hourly_cssrList_VTT2 = new ArrayList<Float>();
				for(VRpHrBscCssr v : vRpHrBsc_VTT){
					
					hourly_cssrList_VTT2.add(v.getSpeechCssr());
					
				}
				series_hourly_VTT2.put(bscid+ "-" + "column", hourly_cssrList_VTT2)	;				
			}
			String chart1 = Chart.multiColumnWelcome("ChartCSSR_H_VTD2", "Bsc của "+to_vtd+" có (Cssr < 70) ", hourlyCatergories, series_hourly_VTD2,"0","70");
			String chart2 = Chart.multiColumnWelcome("ChartCSSR_H_VTT2", "Bsc của "+to_vtt+" có (Cssr < 70) ", hourlyCatergories, series_hourly_VTT2,"0","70");
			model.addAttribute("ChartCSSR_H_VTD2", chart1);
			model.addAttribute("ChartCSSR_H_VTT2", chart2);

	
		}
		else if(function.equals("cssr-3g"))
		{
			
			List<String> categories_3G = new ArrayList<String>();
			List<Float> cssrList_3G = new ArrayList<Float>();
			List<Float> ipbbBaseline = new ArrayList<Float>();
			
			
			List<String> bscIdList_3G = new ArrayList<String>();
			listVRpHrRncCssr= vRpHrBscCssrDAO.getListCssr("R","");
			int hour = 0;
			for(int i=0; i<listVRpHrRncCssr.size();i++)
			{
					
					hour = listVRpHrRncCssr.get(i).getHour();
					cssrList_3G.add(listVRpHrRncCssr.get(i).getSpeechCssr());
					ipbbBaseline.add((float) 95);
					categories_3G.add(listVRpHrRncCssr.get(i).getBscid());
					if(listVRpHrRncCssr.get(i).getSpeechCssr() < 95)
					{
						bscIdList_3G.add(listVRpHrRncCssr.get(i).getBscid());
					}
			}
			Map<String, List<Float>> series_3G = new LinkedHashMap<String, List<Float>>();
			series_3G.put("RNC--2f7ed8", cssrList_3G);
			series_3G.put("baseline-line-FF0000", ipbbBaseline);
			// ve theo gio
			Map<String, List<Float>> series_hourly_3G = new LinkedHashMap<String, List<Float>>();
			List<String> hourlyCatergories = new ArrayList<String>();
			for(int i=0; i<bscIdList_3G.size();i++)
			{
				List<VRpHrBscCssr> vRpHrBsc_3G = vRpHrBscCssrDAO.filter(bscIdList_3G.get(i));
				List<Float> hourly_cssrList_3G = new ArrayList<Float>();
				for(int j=0;j<vRpHrBsc_3G.size();j++){
					
					hourly_cssrList_3G.add(vRpHrBsc_3G.get(j).getSpeechCssr());
					
				}
				series_hourly_3G.put(bscIdList_3G.get(i)+ "-" + "column", hourly_cssrList_3G)	;				
			}
			for(Integer i=0;i<hour;i++)
			{
				hourlyCatergories.add(i.toString());
			}
			model.addAttribute("cssrHourlyChart", Chart.multiColumnWelcome("cssrHourlyChart", "RNC có (Speech Cssr< 95) ", hourlyCatergories, series_hourly_3G,"0","100"));
			model.addAttribute("speechCssrChart", Chart.columnChart("speechCssrChart", "Thống kê Cssr của RNC","Chỉ số Cssr (%)", "85", "100", categories_3G, series_3G));
			model.addAttribute("hour", hour);
		}
		else if(function.equals("cssr-cell"))
		{
			listVRpHrCellCssr= vRpHrCellCssrDAO.getListCssrCell("");
			
			List<String> categories_2G = new ArrayList<String>();
			List<String> categories_3G = new ArrayList<String>();
			
			
			List<Float> cssrListCell_2G = new ArrayList<Float>();
			List<Float> cssrListCell_3G = new ArrayList<Float>();
			
			Calendar rightNow = Calendar.getInstance();
			int hour_2g, hour_3g;
			if(rightNow.get(Calendar.MINUTE) > 35)
			{
				 hour_2g = rightNow.get(Calendar.HOUR_OF_DAY) - 1;
				 hour_3g = rightNow.get(Calendar.HOUR_OF_DAY) - 1;
			}
			else
			{
				hour_2g = rightNow.get(Calendar.HOUR_OF_DAY) - 2;
				hour_3g = rightNow.get(Calendar.HOUR_OF_DAY) - 2;
			}
			for(int i=0; i<listVRpHrCellCssr.size();i++)
			{
				
				if(listVRpHrCellCssr.get(i).getBscid().substring(0,1).equals("B"))
				{
					
					hour_2g = listVRpHrCellCssr.get(i).getHour();
					cssrListCell_2G.add(listVRpHrCellCssr.get(i).getSpeechCssr());
					categories_2G.add(listVRpHrCellCssr.get(i).getCellid());
				
				}
				else
				{
					hour_3g = listVRpHrCellCssr.get(i).getHour();
					cssrListCell_3G.add(listVRpHrCellCssr.get(i).getSpeechCssr());
					categories_3G.add(listVRpHrCellCssr.get(i).getCellid());
				}
			}
			Map<String, List<Float>> seriesCell_2G = new LinkedHashMap<String, List<Float>>();
			seriesCell_2G.put("Cell--FF0000", cssrListCell_2G);
			Map<String, List<Float>> seriesCell_3G = new LinkedHashMap<String, List<Float>>();
			seriesCell_3G.put("NodeB--FF0000", cssrListCell_3G);
			
			model.addAttribute("CssrChart_Cell_2G", Chart.columnChart("CssrChart_Cell_2G", "Thống kê Cssr của Cell 2G","Chỉ số Cssr (%)", "0", "50", categories_2G, seriesCell_2G));
			model.addAttribute("CssrChart_Cell_3G", Chart.columnChart("CssrChart_Cell_3G", "Thống kê Cssr của Cell 3G","Chỉ số Cssr (%)", "0", "50" ,categories_3G, seriesCell_3G));
			model.addAttribute("hour_2g", hour_2g);
			model.addAttribute("hour_3g", hour_3g);
		}
		else if (function.equals("kpi-alarm")){
			List<SYS_PARAMETER> netypeList= sysParameterDao.getSysParameterByCode("kpi.alarm.netype");
			List<SYS_PARAMETER> levelList= sysParameterDao.getSysParameterByCode("kpi.alarm.level");
			
			List<RpHrKpiBad> kpiBadList = rpHrIpiBadDao.filterKpiBad(neType, alarmLevel, neParent, ne);
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("ALARM_LEVEL");
			model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "kpiBad"));
			
			model.addAttribute("neType", neType);
			model.addAttribute("neName", ne);
			model.addAttribute("neParent", neParent);
			model.addAttribute("alarmLevel", alarmLevel);
			model.addAttribute("kpiBadList", kpiBadList);
			model.addAttribute("netypeList", netypeList);
			model.addAttribute("levelList", levelList);
		}
		if(autoRefresh == null)
			model.addAttribute("autoRefresh", "60");
		else
			model.addAttribute("autoRefresh", autoRefresh);
		
		model.addAttribute("function", function);
		return "bieuDoGiamSatCssr";
	}
}
