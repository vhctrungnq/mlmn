package vn.com.vhc.vmsc2.statistics.domain;

public class UserRole {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_USER_ROLE.ROLE_ID
     *
     * @ibatorgenerated Tue Jan 04 10:34:51 ICT 2011
     */
    private String roleId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_USER_ROLE.USER_ID
     *
     * @ibatorgenerated Tue Jan 04 10:34:51 ICT 2011
     */
    private Integer userId;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_USER_ROLE.ROLE_ID
     *
     * @return the value of A_USER_ROLE.ROLE_ID
     *
     * @ibatorgenerated Tue Jan 04 10:34:51 ICT 2011
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_USER_ROLE.ROLE_ID
     *
     * @param roleId the value for A_USER_ROLE.ROLE_ID
     *
     * @ibatorgenerated Tue Jan 04 10:34:51 ICT 2011
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_USER_ROLE.USER_ID
     *
     * @return the value of A_USER_ROLE.USER_ID
     *
     * @ibatorgenerated Tue Jan 04 10:34:51 ICT 2011
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_USER_ROLE.USER_ID
     *
     * @param userId the value for A_USER_ROLE.USER_ID
     *
     * @ibatorgenerated Tue Jan 04 10:34:51 ICT 2011
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}