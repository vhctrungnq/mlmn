package controller.assets;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vo.AsProposedWarehouse;
import vo.alarm.utils.DateTools;

import controller.BaseController;
import dao.AsProposedWarehouseDAO;

@Controller
@RequestMapping("/assets/de-nghi-xuat-kho/*")
public class ProposedWarehouseController extends BaseController {
	@Autowired
	private AsProposedWarehouseDAO proposedWarehouseDao;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	// De nghi xuat kho
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = false) String ngayXuat,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		String user = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		Date day = new Date();

		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder(
					"proposedWarehouse")
					.encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request
					.getParameter((new ParamEncoder("proposedWarehouse")
							.encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}

		if (ngayXuat == null || ngayXuat.equals("")
				|| DateTools.isValid("dd/MM/yyyy", ngayXuat) == false) {

			Calendar cal = Calendar.getInstance();
			ngayXuat = dateFormat.format(cal.getTime());
		}

		List<AsProposedWarehouse> proposedWarehouse = proposedWarehouseDao
				.proposedWarehouseList(user, dateFormat.format(day), null,
						column, order);
		model.addAttribute("proposedWarehouse", proposedWarehouse);
		model.addAttribute("ngayXuat", ngayXuat);

		// get row_num, rowId
		int row_num = 0;
		String rowId = "";

		if (proposedWarehouse.size() != 0) {
			for (int i = 0; i < proposedWarehouse.size(); i++) {
				rowId += proposedWarehouse.get(i).getId() + ",";
			}
			row_num = proposedWarehouse.size();
			rowId = rowId.substring(0, rowId.lastIndexOf(","));
		}

		model.addAttribute("row_num", row_num);
		model.addAttribute("rowId", rowId);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "De-Nghi-Xuat-Kho_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView(
				"jspassets/asProposedWarehouse/asProposedWarehouseList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			proposedWarehouseDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		} catch (Exception e) {
			saveMessageKey(request, "message.confirm.deleteOther");
		}

		return "redirect:list.htm";
	}

	@RequestMapping(value = "save-de-nghi")
	public @ResponseBody
	Map<String, Object> saveData(String key_soLuong, String key_id,
			String key_boPhanSd, String key_donviSd, String key_nguoiSd,
			String key_ngayXuat, String key_lyDoXuat, String key_description,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> data = new HashMap<String, Object>();

		String[] value_soLuong = key_soLuong.split(",");
		String[] value_Id = key_id.split(",");
		AsProposedWarehouse record = new AsProposedWarehouse();

		if (DateTools.isValid("dd/MM/yyyy", key_ngayXuat) == true
				&& key_ngayXuat != null) {
			try {
				record.setNgayXuat(dateFormat.parse(key_ngayXuat));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			record.setBoPhanSd(key_boPhanSd);
			record.setDonViSd(key_donviSd);
			record.setNguoiSd(key_nguoiSd);

			record.setLyDoXuat(key_lyDoXuat);
			record.setDescription(key_description);
			record.setTrangThai("N");

			for (int i = 0; i < value_Id.length; i++) {
				if (value_soLuong[i] != null
						&& DateTools.isValidNumber(value_soLuong[i]) == true) {
					record.setSoLuong(Integer.parseInt(value_soLuong[i]));
				} else {
					record.setSoLuong(Integer.parseInt("0"));
				}

				if (value_Id[i] != null && value_Id[i] != "") {
					record.setId(Integer.parseInt(value_Id[i]));
					proposedWarehouseDao.update(record);
					data.put("status", "success");
				} else {
					data.put("status", "false");
				}
			}
		} else {
			data.put("status", "errordate");
		}

		return data;
	}
}
