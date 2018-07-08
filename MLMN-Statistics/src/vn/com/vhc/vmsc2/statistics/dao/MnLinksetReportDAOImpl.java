package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnLinksetReport;
import vn.com.vhc.vmsc2.statistics.web.filter.MnLinksetReportFilter;

public class MnLinksetReportDAOImpl extends SqlMapClientDaoSupport implements MnLinksetReportDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:58 ICT 2010
     */
    public MnLinksetReportDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:58 ICT 2010
     */
    public int deleteByPrimaryKey(String fromNode, Integer month, String toNode, Integer year) {
        MnLinksetReport key = new MnLinksetReport();
        key.setFromNode(fromNode);
        key.setMonth(month);
        key.setToNode(toNode);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_S7LINKSET_RP.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:58 ICT 2010
     */
    public void insert(MnLinksetReport record) {
        getSqlMapClientTemplate().insert("MN_S7LINKSET_RP.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:58 ICT 2010
     */
    public void insertSelective(MnLinksetReport record) {
        getSqlMapClientTemplate().insert("MN_S7LINKSET_RP.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:58 ICT 2010
     */
    public MnLinksetReport selectByPrimaryKey(String fromNode, Integer month, String toNode, Integer year) {
        MnLinksetReport key = new MnLinksetReport();
        key.setFromNode(fromNode);
        key.setMonth(month);
        key.setToNode(toNode);
        key.setYear(year);
        MnLinksetReport record = (MnLinksetReport) getSqlMapClientTemplate().queryForObject("MN_S7LINKSET_RP.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:58 ICT 2010
     */
    public int updateByPrimaryKeySelective(MnLinksetReport record) {
        int rows = getSqlMapClientTemplate().update("MN_S7LINKSET_RP.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_S7LINKSET_RP
     *
     * @ibatorgenerated Thu Oct 21 10:28:58 ICT 2010
     */
    public int updateByPrimaryKey(MnLinksetReport record) {
        int rows = getSqlMapClientTemplate().update("MN_S7LINKSET_RP.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	public List<MnLinksetReport> filter(MnLinksetReportFilter filter) {
        return getSqlMapClientTemplate().queryForList("MN_S7LINKSET_RP.filter", filter);
    }
}