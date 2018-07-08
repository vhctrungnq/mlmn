package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrLinkset;

public class HrLinksetDAOImpl extends SqlMapClientDaoSupport implements HrLinksetDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_S7LINKSET
     *
     * @ibatorgenerated Fri Oct 22 10:29:11 ICT 2010
     */
    public HrLinksetDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_S7LINKSET
     *
     * @ibatorgenerated Fri Oct 22 10:29:11 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, String fromNode, Integer hour, String linksetid, String toNode) {
        HrLinkset key = new HrLinkset();
        key.setDay(day);
        key.setFromNode(fromNode);
        key.setHour(hour);
        key.setLinksetid(linksetid);
        key.setToNode(toNode);
        int rows = getSqlMapClientTemplate().delete("HR_S7LINKSET.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_S7LINKSET
     *
     * @ibatorgenerated Fri Oct 22 10:29:11 ICT 2010
     */
    public void insert(HrLinkset record) {
        getSqlMapClientTemplate().insert("HR_S7LINKSET.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_S7LINKSET
     *
     * @ibatorgenerated Fri Oct 22 10:29:11 ICT 2010
     */
    public void insertSelective(HrLinkset record) {
        getSqlMapClientTemplate().insert("HR_S7LINKSET.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_S7LINKSET
     *
     * @ibatorgenerated Fri Oct 22 10:29:11 ICT 2010
     */
    public HrLinkset selectByPrimaryKey(Date day, String fromNode, Integer hour, String linksetid, String toNode) {
        HrLinkset key = new HrLinkset();
        key.setDay(day);
        key.setFromNode(fromNode);
        key.setHour(hour);
        key.setLinksetid(linksetid);
        key.setToNode(toNode);
        HrLinkset record = (HrLinkset) getSqlMapClientTemplate().queryForObject("HR_S7LINKSET.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_S7LINKSET
     *
     * @ibatorgenerated Fri Oct 22 10:29:11 ICT 2010
     */
    public int updateByPrimaryKeySelective(HrLinkset record) {
        int rows = getSqlMapClientTemplate().update("HR_S7LINKSET.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_S7LINKSET
     *
     * @ibatorgenerated Fri Oct 22 10:29:11 ICT 2010
     */
    public int updateByPrimaryKey(HrLinkset record) {
        int rows = getSqlMapClientTemplate().update("HR_S7LINKSET.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	public List<HrLinkset> filter(Date day,Integer hour, String fromNode, String toNode){
    	 HrLinkset key = new HrLinkset();
    	 key.setHour(hour);
         key.setDay(day);
         key.setFromNode(fromNode);
         key.setToNode(toNode);
    	return getSqlMapClientTemplate().queryForList("HR_S7LINKSET.filter", key);
    }
}