package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.CoreLog;
import vn.com.vhc.vmsc2.statistics.web.filter.TimeLog;


public interface CoreLogDAO {

	List<CoreLog> filter(TimeLog filter);

	Integer countRow(TimeLog filter);
}