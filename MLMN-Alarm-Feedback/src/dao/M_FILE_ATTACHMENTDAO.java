package dao;

import java.util.List;

import vo.M_FILE_ATTACHMENT;

public interface M_FILE_ATTACHMENTDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    void insert(M_FILE_ATTACHMENT record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    void insertSelective(M_FILE_ATTACHMENT record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    M_FILE_ATTACHMENT selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    int updateByPrimaryKeySelective(M_FILE_ATTACHMENT record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_FILE_ATTACHMENT
     *
     * @ibatorgenerated Mon Jul 30 15:25:22 ICT 2012
     */
    int updateByPrimaryKey(M_FILE_ATTACHMENT record);
    
    List<M_FILE_ATTACHMENT> fileName_file(String idWorkMana);
    List<M_FILE_ATTACHMENT> getIdWorks(String idWorks);

	int insertAndResult(M_FILE_ATTACHMENT record);
}