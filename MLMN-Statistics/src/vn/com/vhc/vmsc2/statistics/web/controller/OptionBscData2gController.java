package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscData2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.OptionBscData2g;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTools;

@Controller
@RequestMapping("/report/radio/bsc-data/option/*")
public class OptionBscData2gController {

	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HProvincesCodeDAO provinceDao;
	@Autowired
	private VRpDyBscData2gDAO vRpDyBscData2gDao;
	private DateFormat dff = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView showlist(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) String starttime, @RequestParam(required = false) String endtime, ModelMap model, HttpServletRequest request)
			throws ParseException {

		if (starttime == null) {
			starttime = df.format(new Date()) + " 00:00";
		}

		if (endtime == null) {
			endtime = dff.format(new Date());
		}
		String startDate, endDate;
		int startHour, endHour;
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		List<OptionBscData2g> dataList = new ArrayList<OptionBscData2g>();

		/*
		 * // begin pageSize int pageSize = 15; OptionBscData2g countRow = new
		 * OptionBscData2g(); Integer totalSize = 0;
		 * 
		 * try { pageSize = Integer.parseInt((String)
		 * RequestContextHolder.currentRequestAttributes
		 * ().getAttribute("pageSize", RequestAttributes.SCOPE_SESSION)); }
		 * catch (Exception ex) {}
		 * 
		 * int startRecord = 0; try { startRecord =
		 * (Integer.parseInt(request.getParameter((new
		 * ParamEncoder("item").encodeParameterName
		 * (TableTagParameters.PARAMETER_PAGE)))) - 1) * pageSize; } catch
		 * (NumberFormatException e) { } int endRecord = startRecord + pageSize;
		 */

		int order = 2;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}

		/*
		 * int export = -1; try { export =
		 * Integer.parseInt(request.getParameter((new
		 * ParamEncoder("item").encodeParameterName
		 * (TableTagParameters.PARAMETER_EXPORTTYPE)))); } catch
		 * (NumberFormatException e) { }
		 */

		if (DateTools.isValid("dd/MM/yyyy HH:mm", starttime) && DateTools.isValid("dd/MM/yyyy HH:mm", endtime)) {
			Date d = dff.parse(starttime);
			Date d2 = dff.parse(endtime);
			calendar.setTime(d);
			calendar2.setTime(d2);

			startDate = df.format(d);
			endDate = df.format(d2);
			startHour = calendar.get(Calendar.HOUR_OF_DAY);
			endHour = calendar2.get(Calendar.HOUR_OF_DAY);

			/*
			 * if (export == -1) { dataList =
			 * vRpDyBscData2gDao.bscData2gOption(startDate, endDate, startHour,
			 * endHour, bscid, region, startRecord, endRecord, column, order); }
			 * else {
			 */
			dataList = vRpDyBscData2gDao.bscData2gOption(startDate, endDate, startHour, endHour, bscid, region, -1, -1, column, order);
			/*
			 * }
			 * 
			 * if(dataList != null){ for(OptionBscData2g item: dataList) {
			 * item.setSdatetime(starttime); item.setEdatetime(endtime); } }
			 * 
			 * countRow = vRpDyBscData2gDao.countBscData2gOption(startDate,
			 * endDate, startHour, endHour, bscid, region); totalSize =
			 * countRow.getCountRow();
			 */
		} else {
			dataList = null;
		}

		List<Bsc> bscList = bscDao.getAll();
		List<HProvincesCode> regionList = provinceDao.getAllRegion();

		model.addAttribute("region", region);
		model.addAttribute("bscid", bscid);
		model.addAttribute("starttime", starttime);
		model.addAttribute("endtime", endtime);

/*		model.addAttribute("totalSize", totalSize);
		model.addAttribute("startRecord", startRecord);*/

		model.addAttribute("regionList", regionList);
		model.addAttribute("bscList", bscList);
		model.addAttribute("optionBscData2gList", dataList);

		// Lay ten file export
		String time = starttime + "_" + endtime;
		String exportFileName = "Bao_cao_Bsc_Data_2g_" + time;
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("baocaotuychon/optionBscData2gList");
	}
}
