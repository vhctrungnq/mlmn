package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.Cell;

public interface CellDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL
     *
     * @ibatorgenerated Thu Mar 31 10:27:15 ICT 2016
     */
    int deleteByPrimaryKey(String bscid, String cellid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL
     *
     * @ibatorgenerated Thu Mar 31 10:27:15 ICT 2016
     */
    void insert(Cell record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL
     *
     * @ibatorgenerated Thu Mar 31 10:27:15 ICT 2016
     */
    void insertSelective(Cell record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL
     *
     * @ibatorgenerated Thu Mar 31 10:27:15 ICT 2016
     */
    Cell selectByPrimaryKey(String bscid, String cellid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL
     *
     * @ibatorgenerated Thu Mar 31 10:27:15 ICT 2016
     */
    int updateByPrimaryKeySelective(Cell record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CELL
     *
     * @ibatorgenerated Thu Mar 31 10:27:15 ICT 2016
     */
    int updateByPrimaryKey(Cell record);
    
	List<String> getSiteids(String term);

	List<Cell> getAllBsc();

	List<String> getAllCellOfBsc(String bscid);

	List<Cell> getCellOfBsc(String bscid);

	List<String> getSiteOfBsc(String bscid);

	List<Cell> getHCellFilter(String bscid, String siteid, String cellid, String vendor, String province, String district, Integer startRecord,
			Integer endRecord, String column, String order);

	int countHCellFilter(String bscid, String siteid, String cellid, String vendor, String province, String district);

	List<Cell> checkPrimaryKeyBscCell(String bscid, String cellid, String id);

	List<Cell> selectBySiteOrCell(String site);

	List<String> getCellids(String term);

	int deleteById(Integer id);

	Cell selectById(String id);

	Cell getSiteInfoFilter(String siteid);

	int updateById(Cell record);
}