package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.MnCellToCellQos;


public interface MnCellToCellQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:45 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, String fromCell, Integer month, String toCell, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:45 ICT 2010
     */
    void insert(MnCellToCellQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:45 ICT 2010
     */
    void insertSelective(MnCellToCellQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:45 ICT 2010
     */
    MnCellToCellQos selectByPrimaryKey(String bscid, String fromCell, Integer month, String toCell, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:45 ICT 2010
     */
    int updateByPrimaryKeySelective(MnCellToCellQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:45 ICT 2010
     */
    int updateByPrimaryKey(MnCellToCellQos record);

	List<MnCellToCellQos> filterCellToCells(String bscid, String fromCell, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, int startRecord, int endRecord, String column, String order);

	List<MnCellToCellQos> filterCellsToCell(String bscid, String toCell, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, int startRecord, int endRecord, String column, String order);

	Integer countFromCell(String bscid, String fromCell, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear);

	Integer countToCell(String bscid, String toCell, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear);

	List<MnCellToCellQos> filterCellToCells(String bscid, String fromCell, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear);

	List<MnCellToCellQos> filterCellsToCell(String bscid, String toCell, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear);
}