package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.Sgsn;
import vn.com.vhc.vmsc2.statistics.web.filter.SGSNFilter;

public interface SgsnDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    int deleteByPrimaryKey(String sgsnid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    void insert(Sgsn record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    void insertSelective(Sgsn record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    Sgsn selectByPrimaryKey(String sgsnid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    int updateByPrimaryKeySelective(Sgsn record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    int updateByPrimaryKey(Sgsn record);

	List<Sgsn> selectBySgsnName(String sgsnName);

	List<Sgsn> getAllSGSNName();

	List<Sgsn> getAllSGSN();

	List<Sgsn> getAllRegion();

	List<String> getSgsnids(String term);

	List<Sgsn> filter(SGSNFilter filter);

	List<Sgsn> getAllSGSNID();

	int deleteById(Integer id);

	Sgsn selectById(Integer id);

	List<Sgsn> checkUniqueSgsn(String sgsnid, String id);

	int updateById(Sgsn record);
}