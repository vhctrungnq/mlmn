package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.MDepartment;
import vn.com.vhc.vmsc2.statistics.web.filter.BscFilter;

public interface BscDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    int deleteByPrimaryKey(String bscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    void insert(Bsc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    void insertSelective(Bsc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    Bsc selectByPrimaryKey(String bscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    int updateByPrimaryKeySelective(Bsc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC
     *
     * @ibatorgenerated Wed Aug 25 00:33:14 ICT 2010
     */
    int updateByPrimaryKey(Bsc record);
    
    List<Bsc> getAll();
    List<Bsc> filter(BscFilter filter);

	List<String> getBscids(String term);

	List<Bsc> selectBsc2g();

	List<Bsc> getDeptHBsc();

	List<Bsc> getHBscByDept(String dept);
	
	List<String> getBsc2g3g(String term);
	
	//Load danh sach Dept
	List<MDepartment> getDept();
	//Load danh sach Team theo Dept
	List<MDepartment> getTeamByDept(String dept);
	//Load danh sach subteam theo Team
	List<MDepartment> getSubTeamByTeam(String dept, String team);
	
	//Lay gia tri dept, team , subteam theo cot deptCode
	List<MDepartment> getNamebyCode(String deptCode);
}