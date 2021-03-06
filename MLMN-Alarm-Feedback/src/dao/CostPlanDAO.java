package dao;

import java.util.List;

import vo.CostPlan;
import vo.SYS_PARAMETER;

public interface CostPlanDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_PLAN
     *
     * @ibatorgenerated Wed Jun 12 09:10:36 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_PLAN
     *
     * @ibatorgenerated Wed Jun 12 09:10:36 ICT 2013
     */
    void insert(CostPlan record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_PLAN
     *
     * @ibatorgenerated Wed Jun 12 09:10:36 ICT 2013
     */
    void insertSelective(CostPlan record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_PLAN
     *
     * @ibatorgenerated Wed Jun 12 09:10:36 ICT 2013
     */
    CostPlan selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_PLAN
     *
     * @ibatorgenerated Wed Jun 12 09:10:36 ICT 2013
     */
    int updateByPrimaryKeySelective(CostPlan record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table COST_PLAN
     *
     * @ibatorgenerated Wed Jun 12 09:10:36 ICT 2013
     */
    int updateByPrimaryKey(CostPlan record);
    
    List<SYS_PARAMETER> titleForm(String typeForm);

	CostPlan checkExit(String departmentId, Integer costYear,
			String expensesSupper, String expensesCode);

	List<CostPlan> getCostPlanFilter(CostPlan costPlan, String column, int order);

	int updateCostSupper(String expensesSupper, String expensesSuper,
			String departmentId, Integer costYear);

	List<CostPlan> getPlanSameList(String departmentId, Integer costYear,
			String expensesCode);

	

	int deleteCostPlan(Integer id);

	

	List<CostPlan> getCostPlanByMonth(String departmentId, Integer costYear,
			Integer costMonth);

	CostPlan selectById(Integer id);

	CostPlan getPlanByCode_Year_Department(String expenCode,
			String departmentId, Integer costYear);
}