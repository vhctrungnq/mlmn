package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.BadcellConfig;


public interface BadcellConfigDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_BAD_CELL_CONFIG
     *
     * @ibatorgenerated Tue Nov 23 08:46:44 ICT 2010
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_BAD_CELL_CONFIG
     *
     * @ibatorgenerated Tue Nov 23 08:46:44 ICT 2010
     */
    void insert(BadcellConfig record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_BAD_CELL_CONFIG
     *
     * @ibatorgenerated Tue Nov 23 08:46:44 ICT 2010
     */
    void insertSelective(BadcellConfig record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_BAD_CELL_CONFIG
     *
     * @ibatorgenerated Tue Nov 23 08:46:44 ICT 2010
     */
    BadcellConfig selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_BAD_CELL_CONFIG
     *
     * @ibatorgenerated Tue Nov 23 08:46:44 ICT 2010
     */
    int updateByPrimaryKeySelective(BadcellConfig record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_BAD_CELL_CONFIG
     *
     * @ibatorgenerated Tue Nov 23 08:46:44 ICT 2010
     */
    int updateByPrimaryKey(BadcellConfig record);

	List<BadcellConfig> getAll();
}