package vn.com.vhc.vmsc2.statistics.web.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.DyMsccploadDAO;
import vn.com.vhc.vmsc2.statistics.dao.HhlrDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.StpDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyHlrForFeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyHlrForGtmscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyHlrForMgwDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyHlrforbeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyMscMgwCPLoadDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyStpDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyMsccpload;
import vn.com.vhc.vmsc2.statistics.domain.Hhlr;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.Stp;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrForFe;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrForGtmsc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrForMgw;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrforbe;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscMgwCPLoad;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyStp;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/core/cpload-chart-summary/*")
public class BDGiamSatTaiController extends BaseController{
@Autowired
private MscDAO mscDAO;
@Autowired
private StpDAO stpDAO;
@Autowired
private DyMsccploadDAO dyMscCploadDAO;
@Autowired
private VRpDyMscMgwCPLoadDAO dyMgwCploadDAO;
@Autowired
private VRpDyStpDAO dyStpDAO;
@Autowired
private VRpDyHlrForFeDAO vRpDyHlrForFeDAO;
@Autowired
private VRpDyHlrforbeDAO vRpDyHlrForBeDAO;
@Autowired
private VRpDyHlrForGtmscDAO vRpDyHlrForGtmscDAO;
@Autowired
private VRpDyHlrForMgwDAO vRpDyHlrForMgwDAO;
@Autowired
private HhlrDAO hlrDao;
private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

@RequestMapping("dy")
public String showReport(
	 @RequestParam(required = false) Date endDate,
	 ModelMap model)
		{
	long currentTime = System.currentTimeMillis();		
	if (endDate == null) {
		endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
	}
			
			model.addAttribute("endDate", df.format(endDate));

			
			/* Chart Start */
			MscFilter h_msc = new MscFilter();
			h_msc.setType("MSC");
			List<Msc> mscList = mscDAO.filter(h_msc);
			model.addAttribute("mscList", mscList);
			
			
			
			long time = endDate.getTime();
			DateTime dt = new DateTime(time);
			Date destDay = new Date(time);
			Date startDay = dt.minusDays(30).toDate();
			
		
			Map<String, List<Float>> srcploadMSC = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> srcploadMGW = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> srcploadSTP = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> srcploadHLRFE = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> srcploadGTMSC = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> srcploadGTMGW = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> srcploadHLRBE = new LinkedHashMap<String, List<Float>>();
			
			List<String> cgMsc = new ArrayList<String>();
			List<String> cgMgw = new ArrayList<String>();
			List<String> cgStp = new ArrayList<String>();
			List<String> cgHlrFe= new ArrayList<String>();
			List<String> cgGTMSC= new ArrayList<String>();
			List<String> cgGTMGW= new ArrayList<String>();
			List<String> cgHlrBe= new ArrayList<String>();
			String  caMSC = "", caMGW = "", caSTP = "", caHlrFe="", caGTMSC="", caGTMGW="", caHlrBe = "";

			//  MSC Chart
			for(int i = 0; i < mscList.size(); i++)
			{
				
				List<DyMsccpload> dyList = dyMscCploadDAO.filter(mscList.get(i).getMscid(),df.format(startDay), df.format(destDay));
				List<Float> inMaxcpList = new ArrayList<Float>();
				for(DyMsccpload record : dyList){
					if(record.getMaxCpLoad()==null)
						record.setMaxCpLoad(Float.parseFloat("0"));
					inMaxcpList.add(record.getMaxCpLoad());
					
				
				}
				srcploadMSC.put(mscList.get(i).getMscid(), inMaxcpList);
				
			}
			List<DyMsccpload> mscDay = dyMscCploadDAO.filterDay(df.format(startDay), df.format(destDay));
			for(int j = 0; j < mscDay.size(); j++){
				cgMsc.add(df.format(mscDay.get(j).getDay()));
			}
			
			caMSC += Chart.basicLineNew("chartMSC", "Biểu đồ Tải CPLoad của MSC GSM","Đơn vị (%)", cgMsc, srcploadMSC);
			model.addAttribute("chartdivMSC", caMSC);
			
			
			
			
			// MGW Chart
			h_msc.setType("MGW");
			List<Msc> mgwList = mscDAO.filter(h_msc);
			for(int i = 0; i < mgwList.size(); i++)
			{
				
				List<VRpDyMscMgwCPLoad> dyList = dyMgwCploadDAO.filter(df.format(startDay), df.format(destDay),mgwList.get(i).getMscid());
				List<Float> mgwLoad = new ArrayList<Float>();
				for(VRpDyMscMgwCPLoad record : dyList){
					if(record.getProcessorload()==null)
						record.setProcessorload(Float.parseFloat("0"));
					mgwLoad.add(record.getProcessorload());
					
				
				}
				srcploadMGW.put(mgwList.get(i).getMscid(), mgwLoad);
				
			}
			List<VRpDyMscMgwCPLoad> mgwDay = dyMgwCploadDAO.filterDay(df.format(startDay), df.format(destDay));
			for(int j = 0; j < mgwDay.size(); j++){
				cgMgw.add(df.format(mgwDay.get(j).getDay()));
			}
			
			caMGW += Chart.basicLineNew("chartMGW", "Biểu đồ Tải CPLoad của MSC MGW","Đơn vị (%)", cgMgw, srcploadMGW);
			model.addAttribute("chartdivMGW", caMGW);
		
			// STP Chart
			List<Stp> stpList = stpDAO.getStpList();
			for(int i = 0; i < stpList.size(); i++)
			{
				
				List<VRpDyStp> dyList = dyStpDAO.filter(df.format(startDay), df.format(destDay),stpList.get(i).getStpid());
				List<Float> stpLoad = new ArrayList<Float>();
				for(VRpDyStp record : dyList){
					if(record.getMaxUtility()==null)
						record.setMaxUtility(Float.parseFloat("0"));
					stpLoad.add(record.getMaxUtility());
					
				
				}
				srcploadSTP.put(stpList.get(i).getStpid(), stpLoad);
				
			}
			List<VRpDyStp> stpDay = dyStpDAO.filterDay(df.format(startDay), df.format(destDay));
			for(int j = 0; j < stpDay.size(); j++){
				cgStp.add(df.format(mscDay.get(j).getDay()));
			}
			
			caSTP += Chart.basicLineNew("chartSTP", "Biểu đồ tải Utility của STP","Đơn vị (%)", cgStp, srcploadSTP);
			model.addAttribute("chartdivSTP", caSTP);
			//GT HLR FE
			List<Hhlr> hlrFeList = hlrDao.filterHlr("FE");
			for(int i = 0; i < hlrFeList.size(); i++)
			{
				
				List<VRpDyHlrForFe> dyList = vRpDyHlrForFeDAO.filter(df.format(startDay), df.format(destDay),hlrFeList.get(i).getHlrid());
				List<Float> hlrFeload = new ArrayList<Float>();
				for(VRpDyHlrForFe record : dyList){
					if(record.getAvgCpuUsage()==null)
						record.setAvgCpuUsage(Float.parseFloat("0"));
					hlrFeload.add(record.getAvgCpuUsage());
					
				
				}
				srcploadHLRFE.put(hlrFeList.get(i).getHlrid(), hlrFeload);
				
			}
			List<VRpDyHlrForFe> hrlfeDay = vRpDyHlrForFeDAO.filterDay(df.format(startDay), df.format(destDay));
			for(int j = 0; j < hrlfeDay.size(); j++){
				cgHlrFe.add(df.format(mscDay.get(j).getDay()));
			}
			
			caHlrFe += Chart.basicLineNew("chartHLRFE", "Biểu đồ tải CPLoad của HLR For FE","Đơn vị (%)", cgHlrFe, srcploadHLRFE);
			model.addAttribute("chartdivHLRFE", caHlrFe);
			
			//GT HLR BE
			List<Hhlr> hlrBeList = hlrDao.filterHlr("BE");
			for(int i = 0; i < hlrBeList.size(); i++)
			{
				
				List<VRpDyHlrforbe> dyList = vRpDyHlrForBeDAO.filter(df.format(startDay), df.format(destDay),hlrBeList.get(i).getHlrid());
				List<Float> hlrBeload = new ArrayList<Float>();
				for(VRpDyHlrforbe record : dyList){
					if(record.getSuccRateOfUpdateMes()==null)
						record.setSuccRateOfUpdateMes(Float.parseFloat("0"));
					hlrBeload.add(record.getSuccRateOfUpdateMes());
					
				
				}
				srcploadHLRBE.put(hlrBeList.get(i).getHlrid(), hlrBeload);
				
			}
			List<VRpDyHlrForFe> hrlbeDay = vRpDyHlrForFeDAO.filterDay(df.format(startDay), df.format(destDay));
			for(int j = 0; j < hrlbeDay.size(); j++){
				cgHlrBe.add(df.format(mscDay.get(j).getDay()));
			}
			
			caHlrBe += Chart.basicLineNew("chartHLRBE", "Biểu đồ Update Message Success của HLR For BE","Đơn vị (%)", cgHlrBe, srcploadHLRBE);
			model.addAttribute("chartdivHLRBE", caHlrBe);
			
			// GT MSC
			List<Hhlr> hlrGTMSCList = hlrDao.filterHlr("GTMSC");
			for(int i = 0; i < hlrGTMSCList.size(); i++)
			{
				
				List<VRpDyHlrForGtmsc> dyList = vRpDyHlrForGtmscDAO.filter(df.format(startDay), df.format(destDay),hlrGTMSCList.get(i).getHlrid());
				List<Float> hlrload = new ArrayList<Float>();
				for(VRpDyHlrForGtmsc record : dyList){
					if(record.getAverageSeizureRate()==null)
						record.setAverageSeizureRate(Float.parseFloat("0"));
					hlrload.add(record.getAverageSeizureRate());
					
				
				}
				srcploadGTMSC.put(hlrGTMSCList.get(i).getHlrid(), hlrload);
				
			}
			List<VRpDyHlrForGtmsc> gtMSCDay = vRpDyHlrForGtmscDAO.filterDay(df.format(startDay), df.format(destDay));
			for(int j = 0; j < gtMSCDay.size(); j++){
				cgGTMSC.add(df.format(mscDay.get(j).getDay()));
			}
			
			caGTMSC += Chart.basicLineNew("chartGTMSC", "Biểu đồ tải CPLoad của Huawei GT-MSC","Đơn vị (%)", cgGTMSC, srcploadGTMSC);
			model.addAttribute("chartdivGTMSC", caGTMSC);
			
			// GT MSC MGW
			List<Hhlr> nodeList = hlrDao.filterHlr("MGW");
			for(int i = 0; i < nodeList.size(); i++)
			{
				
				List<VRpDyHlrForMgw> dyList = vRpDyHlrForMgwDAO.filter(df.format(startDay), df.format(destDay),nodeList.get(i).getHlrid());
				List<Float> hlrload = new ArrayList<Float>();
				for(VRpDyHlrForMgw record : dyList){
					if(record.getMaxOccupiedRateOfCpu()==null)
						record.setMaxOccupiedRateOfCpu(Float.parseFloat("0"));
					hlrload.add(record.getMaxOccupiedRateOfCpu());
					
				
				}
				srcploadGTMGW.put(nodeList.get(i).getHlrid(), hlrload);
				
			}
			List<VRpDyHlrForGtmsc> gtMgwDay = vRpDyHlrForGtmscDAO.filterDay(df.format(startDay), df.format(destDay));
			for(int j = 0; j < gtMgwDay.size(); j++){
				cgGTMGW.add(df.format(mscDay.get(j).getDay()));
			}
			
			caGTMGW += Chart.basicLineNew("chartGTMGW", "Biểu đồ tải CPLoad của Huawei MSC MGW","Đơn vị (%)", cgGTMSC, srcploadGTMGW);
			model.addAttribute("chartdivGTMGW", caGTMGW);
						
			return "bieuDoGiamSatTai";
		}	
}
