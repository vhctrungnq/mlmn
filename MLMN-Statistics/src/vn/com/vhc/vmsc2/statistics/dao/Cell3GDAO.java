package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.Cell3G;

public interface Cell3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL_3G
     *
     * @ibatorgenerated Fri Apr 01 14:30:05 ICT 2016
     */
    int deleteByPrimaryKey(String bscid, String cellid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL_3G
     *
     * @ibatorgenerated Fri Apr 01 14:30:05 ICT 2016
     */
    void insert(Cell3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL_3G
     *
     * @ibatorgenerated Fri Apr 01 14:30:05 ICT 2016
     */
    void insertSelective(Cell3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL_3G
     *
     * @ibatorgenerated Fri Apr 01 14:30:05 ICT 2016
     */
    Cell3G selectByPrimaryKey(String bscid, String cellid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL_3G
     *
     * @ibatorgenerated Fri Apr 01 14:30:05 ICT 2016
     */
    int updateByPrimaryKeySelective(Cell3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL_3G
     *
     * @ibatorgenerated Fri Apr 01 14:30:05 ICT 2016
     */
    int updateByPrimaryKey(Cell3G record);
    
	Cell3G selectById(String id);

	List<Cell3G> getCellOfBsc3G(String bscid);

	List<String> getCellids3G(String term);

	List<Cell3G> getAll();

	List<Cell3G> selectBySiteOrCell(String site);

	List<Cell3G> getHCell3GFilter(String bscid, String siteid, String cellid, String vendor, String province, String district, Integer startRecord,
			Integer endRecord, String column, String order);

	int countHCell3GFilter(String bscid, String siteid, String cellid, String vendor, String province, String district);

	List<Cell3G> checkPrimaryKeyBscCell3g(String bscid, String cellid, String id);

	int deleteById(Integer id);

	int updateById(Cell3G record);
}