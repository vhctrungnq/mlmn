package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.DyBadCellBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBadCellDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBadCellDistrictDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBadCellProvinceDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBadCellRegionDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBadCellBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBadCellDistrictDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBadCellProvinceDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBadCellRegionDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnCellBlkBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnCellBlkDistrictDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnCellBlkProvinceDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnCellBlkRegionDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnCellTrafficBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnCellTrafficDistrictDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnCellTrafficProvinceDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnCellTrafficRegionDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellBlkBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellBlkDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellBlkDistrictDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellBlkProvinceDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellBlkRegionDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellTrafficBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellTrafficDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellTrafficDistrictDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellTrafficProvinceDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellTrafficRegionDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBadCellBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBadCellDistrictDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBadCellProvinceDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBadCellRegionDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkCellBlkBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkCellBlkDistrictDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkCellBlkProvinceDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkCellBlkRegionDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkCellTrafficBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkCellTrafficDistrictDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkCellTrafficProvinceDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkCellTrafficRegionDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyBadCell;
import vn.com.vhc.vmsc2.statistics.domain.DyBadCellBsc;
import vn.com.vhc.vmsc2.statistics.domain.DyBadCellDistrict;
import vn.com.vhc.vmsc2.statistics.domain.DyBadCellProvince;
import vn.com.vhc.vmsc2.statistics.domain.DyBadCellRegion;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.MnBadCellBsc;
import vn.com.vhc.vmsc2.statistics.domain.MnBadCellDistrict;
import vn.com.vhc.vmsc2.statistics.domain.MnBadCellProvince;
import vn.com.vhc.vmsc2.statistics.domain.MnBadCellRegion;
import vn.com.vhc.vmsc2.statistics.domain.MnCellBlkBsc;
import vn.com.vhc.vmsc2.statistics.domain.MnCellBlkDistrict;
import vn.com.vhc.vmsc2.statistics.domain.MnCellBlkProvince;
import vn.com.vhc.vmsc2.statistics.domain.MnCellBlkRegion;
import vn.com.vhc.vmsc2.statistics.domain.MnCellTrafficBsc;
import vn.com.vhc.vmsc2.statistics.domain.MnCellTrafficDistrict;
import vn.com.vhc.vmsc2.statistics.domain.MnCellTrafficProvince;
import vn.com.vhc.vmsc2.statistics.domain.MnCellTrafficRegion;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBlk;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBlkBsc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBlkDistrict;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBlkProvince;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBlkRegion;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTraffic;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTrafficBsc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTrafficDistrict;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTrafficProvince;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTrafficRegion;
import vn.com.vhc.vmsc2.statistics.domain.WkBadCellBsc;
import vn.com.vhc.vmsc2.statistics.domain.WkBadCellDistrict;
import vn.com.vhc.vmsc2.statistics.domain.WkBadCellProvince;
import vn.com.vhc.vmsc2.statistics.domain.WkBadCellRegion;
import vn.com.vhc.vmsc2.statistics.domain.WkCellBlkBsc;
import vn.com.vhc.vmsc2.statistics.domain.WkCellBlkDistrict;
import vn.com.vhc.vmsc2.statistics.domain.WkCellBlkProvince;
import vn.com.vhc.vmsc2.statistics.domain.WkCellBlkRegion;
import vn.com.vhc.vmsc2.statistics.domain.WkCellTrafficBsc;
import vn.com.vhc.vmsc2.statistics.domain.WkCellTrafficDistrict;
import vn.com.vhc.vmsc2.statistics.domain.WkCellTrafficProvince;
import vn.com.vhc.vmsc2.statistics.domain.WkCellTrafficRegion;



@Controller
public class DyBadCellController extends BaseController {
        @Autowired
        private DyBadCellDAO dyBadCellDao;
        @Autowired
        private DyBadCellBscDAO dyBadCellBscDao;
        @Autowired
        private DyBadCellDistrictDAO dyBadCellDistrictDao;
        @Autowired
        private DyBadCellProvinceDAO dyBadCellProvinceDao;
        @Autowired
        private DyBadCellRegionDAO dyBadCellRegionDao;
        @Autowired
        private VRpDyCellBlkDAO dyBadCellBlkDao;
        @Autowired
        private VRpDyCellBlkBscDAO dyBadCellBlkBscDao;
        @Autowired
        private VRpDyCellBlkDistrictDAO dyBadCellBlkDistrictDao;
        @Autowired
        private VRpDyCellBlkProvinceDAO dyBadCellBlkProvinceDao;
        @Autowired
        private VRpDyCellBlkRegionDAO dyBadCellBlkRegionDao;
        @Autowired
        private VRpDyCellTrafficDAO dyBadCellTrafficDao;
        @Autowired
        private VRpDyCellTrafficBscDAO dyBadCellTrafficBscDao;
        @Autowired
        private VRpDyCellTrafficDistrictDAO dyBadCellTrafficDistrictDao;
        @Autowired
        private VRpDyCellTrafficProvinceDAO dyBadCellTrafficProvinceDao;
        @Autowired
        private VRpDyCellTrafficRegionDAO dyBadCellTrafficRegionDao;

        @Autowired
        private WkBadCellBscDAO wkBadCellBscDao;
        @Autowired
        private WkBadCellDistrictDAO wkBadCellDistrictDao;
        @Autowired
        private WkBadCellProvinceDAO wkBadCellProvinceDao;
        @Autowired
        private WkBadCellRegionDAO wkBadCellRegionDao;
        @Autowired
        private MnBadCellBscDAO mnBadCellBscDao;
        @Autowired
        private MnBadCellDistrictDAO mnBadCellDistrictDao;
        @Autowired
        private MnBadCellProvinceDAO mnBadCellProvinceDao;
        @Autowired
        private MnBadCellRegionDAO mnBadCellRegionDao;
        
        @Autowired
        private WkCellTrafficBscDAO wkBadCellTrafficBscDao;
        @Autowired
        private WkCellTrafficDistrictDAO wkBadCellTrafficDistrictDao;
        @Autowired
        private WkCellTrafficProvinceDAO wkBadCellTrafficProvinceDao;
        @Autowired
        private WkCellTrafficRegionDAO wkBadCellTrafficRegionDao;
        @Autowired
        private MnCellTrafficBscDAO mnBadCellTrafficBscDao;
        @Autowired
        private MnCellTrafficDistrictDAO mnBadCellTrafficDistrictDao;
        @Autowired
        private MnCellTrafficProvinceDAO mnBadCellTrafficProvinceDao;
        @Autowired
        private MnCellTrafficRegionDAO mnBadCellTrafficRegionDao;
        
        @Autowired
        private WkCellBlkBscDAO wkBadCellBlkBscDao;
        @Autowired
        private WkCellBlkDistrictDAO wkBadCellBlkDistrictDao;
        @Autowired
        private WkCellBlkProvinceDAO wkBadCellBlkProvinceDao;
        @Autowired
        private WkCellBlkRegionDAO wkBadCellBlkRegionDao;
        @Autowired
        private MnCellBlkBscDAO mnBadCellBlkBscDao;
        @Autowired
        private MnCellBlkDistrictDAO mnBadCellBlkDistrictDao;
        @Autowired
        private MnCellBlkProvinceDAO mnBadCellBlkProvinceDao;
        @Autowired
        private MnCellBlkRegionDAO mnBadCellBlkRegionDao;
        
        @Autowired
    	private HProvincesCodeDAO hProvincesCodeDao;
        
        private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        @RequestMapping("/report/radio/bad-cell/by-cell/list")
        public ModelAndView list(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

                List<DyBadCell> dyBadCellList = dyBadCellDao.filter(bscid, cellid, df.format(startDate), df.format(endDate));

                model.addAttribute("bscid", bscid);
                model.addAttribute("cellid", cellid);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellList");
        }

        @RequestMapping("/report/radio/bad-cell/by-bsc/list")
        public ModelAndView listByBsc(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

        		if (region == null)
        			region = getCenter("TT2");

                List<DyBadCellBsc> dyBadCellList = dyBadCellBscDao.filter(bscid, df.format(startDate), df.format(endDate), region);

                model.addAttribute("bscid", bscid);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);
                model.addAttribute("region", region);
        		
                return new ModelAndView("dyBadCellBscList");
        }
        
        @RequestMapping("/report/radio/bad-cell/by-district/list")
        public ModelAndView listByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }
                List<DyBadCellDistrict> dyBadCellList = dyBadCellDistrictDao.filter(province, district, df.format(startDate), df.format(endDate));

                model.addAttribute("province", province);
                model.addAttribute("district", district);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellDistrictList");
        }

        @RequestMapping("/report/radio/bad-cell/by-province/list")
        public ModelAndView listByProvince(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

        		if (region == null)
        			region = getCenter("TT2");

                List<DyBadCellProvince> dyBadCellList = dyBadCellProvinceDao.filter(province, df.format(startDate), df.format(endDate), region);

                model.addAttribute("province", province);
                model.addAttribute("region", region);
        		model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellProvinceList");
        }

        @RequestMapping("/report/radio/bad-cell/by-region/list")
        public ModelAndView listByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Date startDate,
                        @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

        		if (region == null)
        			region = getCenter("TT2");

                List<DyBadCellRegion> dyBadCellList = dyBadCellRegionDao.filter(region, df.format(startDate), df.format(endDate));
                List<HProvincesCode> regionList=hProvincesCodeDao.getAllRegion();
                model.addAttribute("regionList", regionList);
                model.addAttribute("region", region);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellRegionList");
        }
        
        /* Block */
        
        @RequestMapping("/report/radio/bad-cell-blk/by-cell/list")
        public ModelAndView listBlkByCell(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

                List<VRpDyCellBlk> dyBadCellList = dyBadCellBlkDao.filter(bscid, cellid, df.format(startDate), df.format(endDate));

                model.addAttribute("bscid", bscid);
                model.addAttribute("cellid", cellid);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellBlkList");
        }

        @RequestMapping("/report/radio/bad-cell-blk/by-bsc/list")
        public ModelAndView listBlkByBsc(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

        		if (region == null)
        			region = getCenter("TT2");

                List<VRpDyCellBlkBsc> dyBadCellList = dyBadCellBlkBscDao.filter(bscid, df.format(startDate), df.format(endDate), region);

                model.addAttribute("bscid", bscid);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);
                model.addAttribute("region", region);
        		
                return new ModelAndView("dyBadCellBlkBscList");
        }

        @RequestMapping("/report/radio/bad-cell-blk/by-district/list")
        public ModelAndView listBlkByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

                List<VRpDyCellBlkDistrict> dyBadCellList = dyBadCellBlkDistrictDao.filter(province, district, df.format(startDate), df.format(endDate));

                model.addAttribute("province", province);
                model.addAttribute("district", district);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellBlkDistrictList");
        }
        
        @RequestMapping("/report/radio/bad-cell-blk/by-province/list")
        public ModelAndView listBlkByProvince(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

        		if (region == null)
        			region = getCenter("TT2");

                List<VRpDyCellBlkProvince> dyBadCellList = dyBadCellBlkProvinceDao.filter(province, df.format(startDate), df.format(endDate), region);

                model.addAttribute("province", province);
                model.addAttribute("region", region);
        		model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellBlkProvinceList");
        }

        @RequestMapping("/report/radio/bad-cell-blk/by-region/list")
        public ModelAndView listBlkByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Date startDate,
                        @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

        		if (region == null)
        			region = getCenter("TT2");

                List<VRpDyCellBlkRegion> dyBadCellList = dyBadCellBlkRegionDao.filter(region, df.format(startDate), df.format(endDate));
                List<HProvincesCode> regionList=hProvincesCodeDao.getAllRegion();
    			model.addAttribute("regionList", regionList);
                model.addAttribute("region", region);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellBlkRegionList");
        }
        
        /* Block */
        
        @RequestMapping("/report/radio/bad-cell-traff/by-cell/list")
        public ModelAndView listTrafficByCell(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

                List<VRpDyCellTraffic> dyBadCellList = dyBadCellTrafficDao.filter(bscid, cellid, df.format(startDate), df.format(endDate));

                model.addAttribute("bscid", bscid);
                model.addAttribute("cellid", cellid);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellTrafficList");
        }

        @RequestMapping("/report/radio/bad-cell-traff/by-bsc/list")
        public ModelAndView listTrafficByBsc(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

        		if (region == null)
        			region = getCenter("TT2");

                List<VRpDyCellTrafficBsc> dyBadCellList = dyBadCellTrafficBscDao.filter(bscid, df.format(startDate), df.format(endDate), region);

                model.addAttribute("region", region);
        		model.addAttribute("bscid", bscid);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellTrafficBscList");
        }

        @RequestMapping("/report/radio/bad-cell-traff/by-district/list")
        public ModelAndView listTrafficByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

                List<VRpDyCellTrafficDistrict> dyBadCellList = dyBadCellTrafficDistrictDao.filter(province, district, df.format(startDate), df.format(endDate));

                model.addAttribute("province", province);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellTrafficDistrictList");
        }
        
        @RequestMapping("/report/radio/bad-cell-traff/by-province/list")
        public ModelAndView listTrafficByProvince(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
                        @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

        		if (region == null)
        			region = getCenter("TT2");

                List<VRpDyCellTrafficProvince> dyBadCellList = dyBadCellTrafficProvinceDao.filter(province, df.format(startDate), df.format(endDate), region);

                model.addAttribute("region", region);
        		model.addAttribute("province", province);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellTrafficProvinceList");
        }

        @RequestMapping("/report/radio/bad-cell-traff/by-region/list")
        public ModelAndView listTrafficByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Date startDate,
                        @RequestParam(required = false) Date endDate, ModelMap model) {
                long currentTime = System.currentTimeMillis();
                if (endDate == null) {
                        endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
                }
                if (startDate == null) {
                        startDate = endDate;
                }

        		if (region == null)
        			region = getCenter("TT2");

                List<VRpDyCellTrafficRegion> dyBadCellList = dyBadCellTrafficRegionDao.filter(region, df.format(startDate), df.format(endDate));
                List<HProvincesCode> regionList=hProvincesCodeDao.getAllRegion();
                model.addAttribute("regionList", regionList);
                model.addAttribute("region", region);
                model.addAttribute("startDate", df.format(startDate));
                model.addAttribute("endDate", df.format(endDate));
                model.addAttribute("dyBadCellList", dyBadCellList);

                return new ModelAndView("dyBadCellTrafficRegionList");
        }
        
        @RequestMapping("/report/radio/bad-cell-blk/by-region/mnList")
        public ModelAndView mnListBlkByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		if (region == null)
        			region = getCenter("TT2");
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("region", region);

                List<MnCellBlkRegion> mnBadCellList = mnBadCellBlkRegionDao.filter(region, startMonth, startYear, endMonth, endYear);
                List<HProvincesCode> regionList=hProvincesCodeDao.getAllRegion();
    			model.addAttribute("regionList", regionList);
                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellBlkRegionList");
        }
        
        @RequestMapping("/report/radio/bad-cell-blk/by-region/wkList")
        public ModelAndView wkListTrafficByBlk(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		if (region == null)
	    			region = getCenter("TT2");
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("region", region);

                List<WkCellBlkRegion> wkBadCellList = wkBadCellBlkRegionDao.filter(region, startWeek, startYear, endWeek, endYear);
                List<HProvincesCode> regionList=hProvincesCodeDao.getAllRegion();
    			model.addAttribute("regionList", regionList);
                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellBlkRegionList");
        }
        
        @RequestMapping("/report/radio/bad-cell-blk/by-district/mnList")
        public ModelAndView mnListBlkByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);
	    		model.addAttribute("district", district);

                List<MnCellBlkDistrict> mnBadCellList = mnBadCellBlkDistrictDao.filter(province, district, startMonth, startYear, endMonth, endYear);

                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellBlkDistrictList");
        }
        
        @RequestMapping("/report/radio/bad-cell-blk/by-district/wkList")
        public ModelAndView wkListBlkByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);
	    		model.addAttribute("district", district);

                List<WkCellBlkDistrict> wkBadCellList = wkBadCellBlkDistrictDao.filter(province, district, startWeek, startYear, endWeek, endYear);

                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellBlkDistrictList");
        }
        
        @RequestMapping("/report/radio/bad-cell-blk/by-province/mnList")
        public ModelAndView mnListBlkByProvince(@RequestParam(required = false) String province, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);

                List<MnCellBlkProvince> mnBadCellList = mnBadCellBlkProvinceDao.filter(province, startMonth, startYear, endMonth, endYear);

                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellBlkProvinceList");
        }
        
        @RequestMapping("/report/radio/bad-cell-blk/by-province/wkList")
        public ModelAndView wkListBlkByProvince(@RequestParam(required = false) String province, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);

                List<WkCellBlkProvince> wkBadCellList = wkBadCellBlkProvinceDao.filter(province, startWeek, startYear, endWeek, endYear);

                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellBlkProvinceList");
        }
        
        @RequestMapping("/report/radio/bad-cell-blk/by-bsc/mnList")
        public ModelAndView mnListBlkByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("bscid", bscid);

                List<MnCellBlkBsc> mnBadCellList = mnBadCellBlkBscDao.filter(bscid, startMonth, startYear, endMonth, endYear);

                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellBlkBscList");
        }
        
        @RequestMapping("/report/radio/bad-cell-blk/by-bsc/wkList")
        public ModelAndView wkListBlkByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("bscid", bscid);

                List<WkCellBlkBsc> wkBadCellList = wkBadCellBlkBscDao.filter(bscid, startWeek, startYear, endWeek, endYear);

                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellBlkBscList");
        }
        
        @RequestMapping("/report/radio/bad-cell/by-region/mnList")
        public ModelAndView mnListBadByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		if (region == null)
        			region = getCenter("TT2");
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("region", region);

                List<MnBadCellRegion> mnBadCellList = mnBadCellRegionDao.filter(region, startMonth, startYear, endMonth, endYear);
                List<HProvincesCode> regionList=hProvincesCodeDao.getAllRegion();
                model.addAttribute("regionList", regionList);
                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellRegionList");
        }
        
        @RequestMapping("/report/radio/bad-cell/by-region/wkList")
        public ModelAndView wkListBadByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		if (region == null)
	    			region = getCenter("TT2");
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("region", region);

                List<WkBadCellRegion> wkBadCellList = wkBadCellRegionDao.filter(region, startWeek, startYear, endWeek, endYear);
                List<HProvincesCode> regionList=hProvincesCodeDao.getAllRegion();
                model.addAttribute("regionList", regionList);
                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellRegionList");
        }
        
        @RequestMapping("/report/radio/bad-cell/by-district/mnList")
        public ModelAndView mnListBadByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);
	    		model.addAttribute("district", district);

                List<MnBadCellDistrict> mnBadCellList = mnBadCellDistrictDao.filter(province, district, startMonth, startYear, endMonth, endYear);

                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellDistrictList");
        }
        
        @RequestMapping("/report/radio/bad-cell/by-district/wkList")
        public ModelAndView wkListBadByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);
	    		model.addAttribute("district", district);

                List<WkBadCellDistrict> wkBadCellList = wkBadCellDistrictDao.filter(province, district, startWeek, startYear, endWeek, endYear);

                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellDistrictList");
        }
        
        @RequestMapping("/report/radio/bad-cell/by-province/mnList")
        public ModelAndView mnListByProvince(@RequestParam(required = false) String province, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);

                List<MnBadCellProvince> mnBadCellList = mnBadCellProvinceDao.filter(province, startMonth, startYear, endMonth, endYear);

                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellProvinceList");
        }
        
        @RequestMapping("/report/radio/bad-cell/by-province/wkList")
        public ModelAndView wkListBadByProvince(@RequestParam(required = false) String province, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);

                List<WkBadCellProvince> wkBadCellList = wkBadCellProvinceDao.filter(province, startWeek, startYear, endWeek, endYear);

                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellProvinceList");
        }
        
        @RequestMapping("/report/radio/bad-cell/by-bsc/mnList")
        public ModelAndView mnListBadByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("bscid", bscid);

                List<MnBadCellBsc> mnBadCellList = mnBadCellBscDao.filter(bscid, startMonth, startYear, endMonth, endYear);

                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellBscList");
        }
        
        @RequestMapping("/report/radio/bad-cell/by-bsc/wkList")
        public ModelAndView wkListBadByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("bscid", bscid);

                List<WkBadCellBsc> wkBadCellList = wkBadCellBscDao.filter(bscid, startWeek, startYear, endWeek, endYear);

                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellBscList");
        }
        
        @RequestMapping("/report/radio/bad-cell-traff/by-region/mnList")
        public ModelAndView mnListTrafficByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		if (region == null)
        			region = getCenter("TT2");
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("region", region);

                List<MnCellTrafficRegion> mnBadCellList = mnBadCellTrafficRegionDao.filter(region, startMonth, startYear, endMonth, endYear);
                List<HProvincesCode> regionList=hProvincesCodeDao.getAllRegion();
                model.addAttribute("regionList", regionList);
                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellTrafficRegionList");
        }
        
        @RequestMapping("/report/radio/bad-cell-traff/by-region/wkList")
        public ModelAndView wkListTrafficByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		if (region == null)
	    			region = getCenter("TT2");
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("region", region);

                List<WkCellTrafficRegion> wkBadCellList = wkBadCellTrafficRegionDao.filter(region, startWeek, startYear, endWeek, endYear);
                List<HProvincesCode> regionList=hProvincesCodeDao.getAllRegion();
                model.addAttribute("regionList", regionList);
                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellTrafficRegionList");
        }
        
        @RequestMapping("/report/radio/bad-cell-traff/by-district/mnList")
        public ModelAndView mnListTrafficByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);
	    		model.addAttribute("district", district);

                List<MnCellTrafficDistrict> mnBadCellList = mnBadCellTrafficDistrictDao.filter(province, district, startMonth, startYear, endMonth, endYear);

                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellTrafficDistrictList");
        }
        
        @RequestMapping("/report/radio/bad-cell-traff/by-district/wkList")
        public ModelAndView wkListTrafficByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);
	    		model.addAttribute("district", district);

                List<WkCellTrafficDistrict> wkBadCellList = wkBadCellTrafficDistrictDao.filter(province, district, startWeek, startYear, endWeek, endYear);

                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellTrafficDistrictList");
        }
        
        @RequestMapping("/report/radio/bad-cell-traff/by-province/mnList")
        public ModelAndView mnListTrafficByProvince(@RequestParam(required = false) String province, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);

                List<MnCellTrafficProvince> mnBadCellList = mnBadCellTrafficProvinceDao.filter(province, startMonth, startYear, endMonth, endYear);

                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellTrafficProvinceList");
        }
        
        @RequestMapping("/report/radio/bad-cell-traff/by-province/wkList")
        public ModelAndView wkListTrafficByProvince(@RequestParam(required = false) String province, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("province", province);

                List<WkCellTrafficProvince> wkBadCellList = wkBadCellTrafficProvinceDao.filter(province, startWeek, startYear, endWeek, endYear);

                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellTrafficProvinceList");
        }
        
        @RequestMapping("/report/radio/bad-cell-traff/by-bsc/mnList")
        public ModelAndView mnListTrafficByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	Calendar cal = Calendar.getInstance();
	    		if (endMonth == null) {
	    			cal.add(Calendar.MONTH, -1);
	    			endMonth = cal.get(Calendar.MONTH) + 1;
	    			endYear = cal.get(Calendar.YEAR);
	    		}
	
	    		if (startMonth == null) {
	    			startMonth = endMonth;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startMonth", startMonth);
	    		model.addAttribute("endMonth", endMonth);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("bscid", bscid);

                List<MnCellTrafficBsc> mnBadCellList = mnBadCellTrafficBscDao.filter(bscid, startMonth, startYear, endMonth, endYear);

                model.addAttribute("mnBadCellList", mnBadCellList);

                return new ModelAndView("mnBadCellTrafficBscList");
        }
        
        @RequestMapping("/report/radio/bad-cell-traff/by-bsc/wkList")
        public ModelAndView wkListTrafficByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
        		@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
	        	if (endWeek == null) {
	    			DateTime dt = new DateTime();
	    			dt = dt.minusWeeks(1);
	    			endWeek = dt.getWeekOfWeekyear();
	    			endYear = dt.getYear();
	    		}

	    		if (startWeek == null) {
	    			startWeek = endWeek;
	    			startYear = endYear;
	    		}
	    		
	    		model.addAttribute("startWeek", startWeek);
	    		model.addAttribute("endWeek", endWeek);
	    		model.addAttribute("startYear", startYear);
	    		model.addAttribute("endYear", endYear);
	    		model.addAttribute("bscid", bscid);

                List<WkCellTrafficBsc> wkBadCellList = wkBadCellTrafficBscDao.filter(bscid, startWeek, startYear, endWeek, endYear);

                model.addAttribute("wkBadCellList", wkBadCellList);

                return new ModelAndView("wkBadCellTrafficBscList");
        }
}