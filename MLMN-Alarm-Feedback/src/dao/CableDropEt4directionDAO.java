package dao;

import java.util.List;

import vo.CableDropEt4direction;
import vo.SYS_PARAMETER;
import vo.alarm.utils.CableDropEt4directionFilter;

public interface CableDropEt4directionDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_DROP_ET4_DIRECTIONS
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    void insert(CableDropEt4direction record);
    

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_DROP_ET4_DIRECTIONS
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    void insertSelective(CableDropEt4direction record);
    
    int updateByPrimaryKey(CableDropEt4direction record);
    
    CableDropEt4direction selectById(Integer id);
    
    CableDropEt4direction selectByPrimaryKey(String circuit, String ddfHead, String ddfFinish);
    
    int deleteByPrimaryKey(Integer id);
    
    List<CableDropEt4direction> getList(CableDropEt4directionFilter filter,String column, int order, Integer delData);
    
    List<SYS_PARAMETER> titleForm(String typeForm);
    
    List<String> getAllCiruits(String term);

}