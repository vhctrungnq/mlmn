package dao;

import java.util.List;

import vo.CostDetail;
import vo.CostExpenses;
import vo.CostSum;
import vo.SYS_PARAMETER;

public interface CostDetailDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_DETAIL
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_DETAIL
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    void insert(CostDetail record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_DETAIL
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    void insertSelective(CostDetail record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_DETAIL
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    CostDetail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_DETAIL
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    int updateByPrimaryKeySelective(CostDetail record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_DETAIL
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    int updateByPrimaryKey(CostDetail record);
    
    List<SYS_PARAMETER> titleForm(String typeForm );

	List<CostDetail> getCostDetailFilter(CostDetail costDetail, String column,
			int order);

	List<CostExpenses> getExpensesByYear(String departmentID, Integer costYear);

	List<CostDetail> getWorkSuperList(Integer idsup);

	Integer countDetailPlan(String departmentId, Integer costMonth,
			Integer costYear,String workCode);

	List<CostDetail> getCostDetailLastMonthNotDone(String departmentId,
			Integer costYear, Integer costMonth);

	CostDetail checkExits(String departmentId, Integer costYear,
			Integer costMonth, Integer workSuper,String expensesCode, String workName);

	Integer insertCost(CostDetail cost);

	Integer getIdSuper(String expensesCode, String departmentId,
			Integer costMonth, Integer costYear);

	String getTaskNoChildMax(Integer exSup, String departmentId, Integer costMonth,
			Integer costYear);

	List<String> getCostDetailSearch(String focus, String term);

	int countChilden(Integer id);

	List<CostDetail> getDetailSame(Integer superID);

	CostDetail getCostDetailMonth(String departmentId, Integer costYear,
			Integer costMonth, String expensesCode, String workId);

	/*Integer getIDSuperOfLastMonth(String departmentId, Integer costYear,
			Integer costMonth, String expensesCode, Integer id);*/

	int updateCostDetailSupper(Integer idSuper);

	List<CostSum> getSumCostByJobType(CostDetail costDetail, String column,
			int order);

	List<CostDetail> getCostNotDone(CostDetail costDetail, String column,
			int order);

	List<CostDetail> getCostAriseNew(CostDetail costDetail, String column,
			int order);

	int updateStatusPlan(Integer id);



}