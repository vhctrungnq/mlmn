package controller.alarm;

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

import vo.VRpHrBscCssr;
import vo.VRpHrCellCssr;
import vo.alarm.utils.Chart;
import dao.SYS_PARAMETERDAO;
import dao.VRpHrBscCssrDAO;
import dao.VRpHrCellCssrDAO;

@Controller
@RequestMapping("/alarm/bieu-do-giam-sat/*")
public class BDGiamSatCSSR {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private VRpHrBscCssrDAO vRpHrBscCssrDAO;
	@Autowired
	private VRpHrCellCssrDAO vRpHrCellCssrDAO;

	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String autoRefresh,
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		List <VRpHrBscCssr> listVRpHrBscCssr;
		List <VRpHrCellCssr> listVRpHrCellCssr;
		if(function.equals("cssr-2g"))
		{

			listVRpHrBscCssr= vRpHrBscCssrDAO.getListCssr("B");
			
			List<String> categories_VTD = new ArrayList<String>();
			List<String> categories_VTT = new ArrayList<String>();
			
			
			List<Float> cssrList_VTD = new ArrayList<Float>();
			List<Float> cssrList_VTT = new ArrayList<Float>();
			
			
			List<String> bscIdList_VTD = new ArrayList<String>();
			List<String> bscIdList_VTT = new ArrayList<String>();

			
			int hour = 0;
		
			for(int i=0; i<listVRpHrBscCssr.size();i++)
			{
				
					hour = listVRpHrBscCssr.get(i).getHour();
					if(listVRpHrBscCssr.get(i).getDept().contains("ĐÔNG"))
					{
						
						
						cssrList_VTD.add(listVRpHrBscCssr.get(i).getSpeechCssr());
						categories_VTD.add(listVRpHrBscCssr.get(i).getBscid());
						if(listVRpHrBscCssr.get(i).getSpeechCssr() < 95)
						{
							bscIdList_VTD.add(listVRpHrBscCssr.get(i).getBscid());
						}
					}
					else
					{
						
						cssrList_VTT.add(listVRpHrBscCssr.get(i).getSpeechCssr());
						categories_VTT.add(listVRpHrBscCssr.get(i).getBscid());
						if(listVRpHrBscCssr.get(i).getSpeechCssr() < 95)
						{
							bscIdList_VTT.add(listVRpHrBscCssr.get(i).getBscid());
						}
					}
					
					
				
		   }
			Map<String, List<Float>> series_VTD = new LinkedHashMap<String, List<Float>>();
			series_VTD.put("CSSR Tổ VTĐ(%)--4572a7", cssrList_VTD);
			Map<String, List<Float>> series_VTT = new LinkedHashMap<String, List<Float>>();
			series_VTT.put("CSSR Tổ VTT(%)--4572a7", cssrList_VTT);
			
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

			model.addAttribute("CssrChart_VTD", Chart.columnChart("CssrChart_VTD", "BSC CSSR VTD(%)", categories_VTD, series_VTD, "85", "100"));
			model.addAttribute("ChartCSSR_H_VTD", Chart.multiColumnWelcome("ChartCSSR_H_VTD", "Chart BSC VTD(CSSR (%) < 95(%)) ", hourlyCatergories, series_hourly_VTD));
			model.addAttribute("CssrChart_VTT", Chart.columnChart("CssrChart_VTT", "BSC CSSR VTT(%)", categories_VTT, series_VTT,"85", "100"));
			model.addAttribute("ChartCSSR_H_VTT", Chart.multiColumnWelcome("ChartCSSR_H_VTT", "Chart BSC VTT(CSSR (%) < 95(%)) ", hourlyCatergories, series_hourly_VTT));
			
		}
		else if(function.equals("cssr-3g"))
		{
			
			List<String> categories_3G = new ArrayList<String>();
			List<Float> cssrList_3G = new ArrayList<Float>();
			
			
			
			List<String> bscIdList_3G = new ArrayList<String>();
			listVRpHrBscCssr= vRpHrBscCssrDAO.getListCssr("R");
			int hour = 0;
			for(int i=0; i<listVRpHrBscCssr.size();i++)
			{
					
					hour = listVRpHrBscCssr.get(i).getHour();
					cssrList_3G.add(listVRpHrBscCssr.get(i).getSpeechCssr());
				
					categories_3G.add(listVRpHrBscCssr.get(i).getBscid());
					if(listVRpHrBscCssr.get(i).getSpeechCssr() < 95)
					{
						bscIdList_3G.add(listVRpHrBscCssr.get(i).getBscid());
					}
					
				
		   }
			Map<String, List<Float>> series_3G = new LinkedHashMap<String, List<Float>>();
			series_3G.put("CSSR 3G(%)--4572a7", cssrList_3G);
			
			
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
			model.addAttribute("cssrHourlyChart", Chart.multiColumnWelcome("cssrHourlyChart", "Chart RNC (Speech CSSR (%) < 95(%)) ", hourlyCatergories, series_hourly_3G));
			model.addAttribute("speechCssrChart", Chart.columnChart("speechCssrChart", "Speech CSSR (%)", categories_3G, series_3G, "85", "100"));
			//model.addAttribute("columnlinechar", Chart.columnLineChart("columnlinechar", "Speech CSSR (%)", categories_3G, series_3G,series_n));
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
				//cellIdList_2G.add(listVRpHrCellCssr.get(i).getCellid());
			}
			Map<String, List<Float>> seriesCell_2G = new LinkedHashMap<String, List<Float>>();
			seriesCell_2G.put("CSSR 2G(< 50 %)--FF0000", cssrListCell_2G);
			Map<String, List<Float>> seriesCell_3G = new LinkedHashMap<String, List<Float>>();
			seriesCell_3G.put("CSSR 3G(< 50 %)--FF0000", cssrListCell_3G);
			
			model.addAttribute("CssrChart_Cell_2G", Chart.columnChart("CssrChart_Cell_2G", "Cell CSSR 2G(< 50 %)", categories_2G, seriesCell_2G, "0", "50"));
			model.addAttribute("CssrChart_Cell_3G", Chart.columnChart("CssrChart_Cell_3G", "Cell CSSR 3G(< 50 %)", categories_3G, seriesCell_3G, "0", "50"));
			model.addAttribute("hour_2g", hour_2g);
			model.addAttribute("hour_3g", hour_3g);
		}
		
		if(autoRefresh == null)
			model.addAttribute("autoRefresh", "60");
		else
			model.addAttribute("autoRefresh", autoRefresh);
		
		model.addAttribute("function", function);
		return "jspalarm/bieuDoGiamSatCssr";
	}
}
