package dao;

import java.util.List;

import vo.FbVip;

public interface FbVipDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_VIP
     *
     * @ibatorgenerated Thu Mar 07 13:59:16 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_VIP
     *
     * @ibatorgenerated Thu Mar 07 13:59:16 ICT 2013
     */
    void insert(FbVip record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_VIP
     *
     * @ibatorgenerated Thu Mar 07 13:59:16 ICT 2013
     */
    void insertSelective(FbVip record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_VIP
     *
     * @ibatorgenerated Thu Mar 07 13:59:16 ICT 2013
     */
    FbVip selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_VIP
     *
     * @ibatorgenerated Thu Mar 07 13:59:16 ICT 2013
     */
    int updateByPrimaryKeySelective(FbVip record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table FB_VIP
     *
     * @ibatorgenerated Thu Mar 07 13:59:16 ICT 2013
     */
    int updateByPrimaryKey(FbVip record);

	List<FbVip> getFbVipList();

	List<FbVip> getFbVipFilter(String vipCode, String vipName, String column,
			String order);

	List<FbVip> selectByVipCode(String vipCode);
}