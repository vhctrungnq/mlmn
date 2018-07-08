package dao;

import java.util.List;

import vo.SYS_PARAMETER;
import vo.dbafreespace;

public interface dbafreespaceDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DBA_FREE_SPACE
     *
     * @ibatorgenerated Tue Nov 06 15:35:16 ICT 2012
     */
    void insert(dbafreespace record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DBA_FREE_SPACE
     *
     * @ibatorgenerated Tue Nov 06 15:35:16 ICT 2012
     */
    void insertSelective(dbafreespace record);
   

	Integer countRow(String user);

	List<dbafreespace> filter(String user, int startRecord, int endRecord,
			String column, int order);

	List<SYS_PARAMETER> titleFormDetail();

	List<dbafreespace> getTablespaceName();
}