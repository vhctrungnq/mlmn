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

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCell3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.OptionCell3g;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTools;

@Controller
@RequestMapping("/report/radio3g/cell/option/*")
public class OptionCell3gController {

	@Autowired
	private HProvincesCodeDAO provinceDao;
	@Autowired
	private VRpDyCell3GDAO vRpDyCellDao;
	@Autowired
	private Bsc3GDAO bsc3GDao;

	private DateFormat dff = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView showlist(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
			@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid, @RequestParam(required = false) String starttime,
			@RequestParam(required = false) String endtime, ModelMap model, HttpServletRequest request) throws ParseException {

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
		List<OptionCell3g> dataList = new ArrayList<OptionCell3g>();

		/*
		 * // begin pageSize int pageSize = 15; OptionCell3g countRow = new
		 * OptionCell3g(); Integer totalSize = 0;
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
			 * vRpDyCellDao.Cell3gOption(startDate, endDate, startHour, endHour,
			 * region, province, bscid, cellid, startRecord, endRecord, column,
			 * order); } else {
			 */
			dataList = vRpDyCellDao.Cell3gOption(startDate, endDate, startHour, endHour,
					region, province, bscid, cellid, -1, -1, column, order);
			/*
			 * }
			 * 
			 * if(dataList != null){ for(OptionCell3g item: dataList) {
			 * item.setSdatetime(starttime); item.setEdatetime(endtime); } }
			 * 
			 * countRow = vRpDyCellDao.countCell3gOption(startDate, endDate,
			 * startHour, endHour, region, province, bscid, cellid); totalSize =
			 * countRow.getCountRow();
			 */
		} else {
			dataList = null;
		}

		List<HProvincesCode> regionList = provinceDao.getAllRegion();
		List<Bsc3G> bscList = bsc3GDao.getAllBsc();
		model.addAttribute("regionList", regionList);
		model.addAttribute("bscList", bscList);
		model.addAttribute("region", region);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);
		model.addAttribute("starttime", starttime);
		model.addAttribute("endtime", endtime);

/*		model.addAttribute("totalSize", totalSize);
		model.addAttribute("startRecord", startRecord);*/
		model.addAttribute("optionCell3gList", dataList);

		// Lay ten file export
		String time = starttime + "_" + endtime;
		String exportFileName = "Bao_cao_Cell_3g_" + time;
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("baocaotuychon/optionCell3gList");
	}
}
