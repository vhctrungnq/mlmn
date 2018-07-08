package c2.security.authentication;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import vo.SysUsers;
import vo.alarm.utils.AlarmSetting;
/**
 * Function        : Kiem tra tai khoan va mat khau dang nhap vao he thong hoac LDAP
 * Created By      :
 * Create Date     :
 * Modified By     : BUIQUANG
 * Modify Date     : 26/11/2013
 * @author BUIQUANG
 * Description     :
 */
public class CustomAuthenticationManager implements AuthenticationManager {
	
	public static DataSource DATA_SOURCE = null;
	
	public static String dotEmail = "@mobifone.vn";

	private static Logger logger = Logger
			.getLogger(CustomAuthenticationManager.class);

	// Su dung ham ma hoa Md5 de ma hoa mat khau
	private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {

		//logger.info("Performing custom authentication");
		//logger.info("Username: " + (String) auth.getName() + ", Password: " + (String) auth.getCredentials());

		String currName = null;
		try {
			//Authentication authCurr = SecurityContextHolder.getContext().getAuthentication();
			currName = (String) auth.getName();
			
			System.out.println("currName: " + currName);
		} catch (Exception ex) {}
		
		
		String username = (String) auth.getName();
		String password = (String) auth.getCredentials();
		
		String module  = "";
		try {
			if (RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION) != null) {
				module = RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION).toString();
				
			}
			
		} catch(Exception ex) {}
		
		if (module.equals("") && AlarmSetting._MODULE != null) {
			module = AlarmSetting._MODULE;
		}
		
		if (module != null && !module.equals("")) {
			try {
				if(!getCountUserOfModule(username, module)) {
					if (currName != null && !currName.equals("anonymousUser")) {
						return new UsernamePasswordAuthenticationToken(currName,
								auth.getCredentials(), getAuthorities(1));
					}
					throw new BadCredentialsException("Bạn không có quyền truy cập module này.");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		boolean isDb = false;
		
		SysUsers user = null;
		
		try {
			user = getUserByUsername(auth.getName());
			
			if (user == null) {
				throw new BadCredentialsException("Tài khoản không tồn tại.");
			} else {
				
				if(user.getLoginBy() != null && !user.getLoginBy().equals("")){
					// 1. Neu LOGIN_BY = 1 la dang nhap bang email thi xac thuc qua LDAP, k check user, pass tren db cua minh
					if(user.getLoginBy().equals("1")){
						try {
							if(
									(LDAP.authentication(username + dotEmail, password) || LDAP.authentication(username, password))
									&& password != null && !password.equals("")
							){
								//logger.info("LOGIN LDAP SUCCESS");
								return new UsernamePasswordAuthenticationToken(user.getUsername(),
										auth.getCredentials(), getAuthorities(1));
							}
							else{
								logger.error("Wrong password!");
								throw new BadCredentialsException("Mật khẩu không đúng.");
							}
						} catch (Exception ex) {
							logger.error("Login failed!");
							throw new BadCredentialsException("Quá trình đăng nhập bị lỗi.");
						}
					}
					// 2. Neu LOGIN_BY = 2 la dang nhap bang username thi xac thuc qua password tren db cua minh, k check LDAP
					else if(user.getLoginBy().equals("2")){
						if (passwordEncoder.isPasswordValid(user.getPassword(), (String) auth.getCredentials(), null) == true 
								&& password != null && !password.equals("")) {
								isDb = true;
								//logger.info("LOGIN DB SUCCESS");
								return new UsernamePasswordAuthenticationToken(user.getUsername(),
										auth.getCredentials(), getAuthorities(1));
							}
					}
					// 2. Neu LOGIN_BY = 3 la dang nhap bang username & email thi nguoi dung co the nhap 1 trong 2 dieu kien tren deu duoc.
					else {
						if (passwordEncoder.isPasswordValid(user.getPassword(), (String) auth.getCredentials(), null) == true 
								&& password != null && !password.equals("")) {
								isDb = true;
							}
						if (!isDb) {
							try {
								if(
										(LDAP.authentication(username + dotEmail, password) || LDAP.authentication(username, password))
										&& password != null && !password.equals("")
								){
									//logger.info("LOGIN LDAP SUCCESS");
									return new UsernamePasswordAuthenticationToken(user.getUsername(),
											auth.getCredentials(), getAuthorities(1));
								}
								else{
									logger.error("Wrong password!");
									throw new BadCredentialsException("Mật khẩu không đúng.");
								}
							} catch (Exception ex) {
								logger.error("Login failed!");
								throw new BadCredentialsException("Quá trình đăng nhập bị lỗi.");
							}
						} else {
							//logger.info("LOGIN DB SUCCESS");
							return new UsernamePasswordAuthenticationToken(user.getUsername(),
									auth.getCredentials(), getAuthorities(1));
						}
					}
					
				}
				else{
					if (passwordEncoder.isPasswordValid(user.getPassword(), (String) auth.getCredentials(), null) == true 
							&& password != null && !password.equals("")) {
							isDb = true;
						}
					if (!isDb) {
						try {
							if(
									(LDAP.authentication(username + dotEmail, password) || LDAP.authentication(username, password))
									&& password != null && !password.equals("")
							){
								//logger.info("LOGIN LDAP SUCCESS");
								return new UsernamePasswordAuthenticationToken(user.getUsername(),
										auth.getCredentials(), getAuthorities(1));
							}
							else{
								logger.error("Wrong password!");
								throw new BadCredentialsException("Mật khẩu không đúng.");
							}
						} catch (Exception ex) {
							logger.error("Login failed!");
							throw new BadCredentialsException("Quá trình đăng nhập bị lỗi.");
						}
					} else {
						//logger.info("LOGIN DB SUCCESS");
						return new UsernamePasswordAuthenticationToken(user.getUsername(),
								auth.getCredentials(), getAuthorities(1));
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("Error: " + e.toString());
			throw new BadCredentialsException("Quá trình đăng nhập bị lỗi.");
		}
		
		/*if (!isDb) {
			try {
				if(
						(LDAP.authentication(username + dotEmail, password) || LDAP.authentication(username, password))
						&& password != null && !password.equals("")
				){
					//logger.info("LOGIN LDAP SUCCESS");
					return new UsernamePasswordAuthenticationToken(user.getUsername(),
							auth.getCredentials(), getAuthorities(1));
				}
				else{
					logger.error("Wrong password!");
					throw new BadCredentialsException("Mật khẩu không đúng.");
				}
			} catch (Exception ex) {
				logger.error("Login failed!");
				throw new BadCredentialsException("Quá trình đăng nhập bị lỗi.");
			}
		} else {
			//logger.info("LOGIN DB SUCCESS");
			return new UsernamePasswordAuthenticationToken(user.getUsername(),
					auth.getCredentials(), getAuthorities(1));
		}*/
		throw new BadCredentialsException("Quá trình đăng nhập bị lỗi.");
	}

	/**
	 * Xac dinh quyen truy cap
	 */
	public Collection<GrantedAuthority> getAuthorities(Integer access) {
		// Create a list of grants for this user
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);

		// All users are granted with ROLE_USER access
		// Therefore this user gets a ROLE_USER by default
		//logger.debug("Grant ROLE_USER to this user");
		authList.add(new GrantedAuthorityImpl("ROLE_USER"));

		// Check if this user has admin access
		// We interpret Integer(1) as an admin user
		if (access.compareTo(1) == 0) {
			// User has admin access
			//logger.debug("Grant ROLE_ADMIN to this user");
			authList.add(new GrantedAuthorityImpl("ROLE_USER"));
		}

		// Return list of granted authorities
		return authList;
	}
	
	static {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			DATA_SOURCE = (DataSource) ctx.lookup("java:/vmsc6_alarm");
			//logger.info("Init Datasource success.");
		} catch (NamingException e) {
			logger.error("Cannot init Datasource: " + e.getMessage());
		}
	}
	
	public SysUsers getUserByUsername(String username) throws SQLException {
		
		//logger.info("Loading user info");
		
        Connection con = null;
        
        SysUsers userDB = new SysUsers();

        try {
            con = DATA_SOURCE.getConnection();
            Statement st = con.createStatement();
            
            String sql = "SELECT USERNAME, PASSWORD, EMAIL, LOGIN_BY FROM SYS_USERS WHERE UPPER(USERNAME) = '" + username.toUpperCase() 
            		+ "' OR UPPER(EMAIL) = '" + username.toUpperCase() 
            		+ "' OR UPPER(EMAIL) = '" + username.toUpperCase() + dotEmail.toUpperCase() + "'";
            //logger.info("SQL: " + sql);
            
            ResultSet result = st.executeQuery(sql);
            
            String userName = null;
            String password = null;
            String email = null;
            String loginBy = null;
            
            while (result.next()) {
            	userName = result.getString(1);
            	password = result.getString(2);
            	email = result.getString(3);
            	loginBy = result.getString(4);
                userDB.setUsername(userName);
                userDB.setPassword(password);
                userDB.setEmail(email);
                userDB.setLoginBy(loginBy);
                
                //logger.info(" + <userName, email>: " + userName + ", " + email);
            }
            result.close();
            st.close();
        } catch(Exception ex) {
        	logger.info("Error: " + ex.toString());
        } finally {
            if (con != null) {
                con.close();
            }
            
           // logger.info("Load user info success.");
        }
        
        return userDB;
    }
	
	public boolean getCountUserOfModule(String username, String module) throws SQLException {
		CallableStatement cs 	= null;
		Integer output 			= null;
		
		Connection connection = null;
		
		try {
			connection = DATA_SOURCE.getConnection();
			
			cs = connection.prepareCall("{call pk_sys_users.GET_COUNT_USER_OF_MODULE(?, ?, ?)}");
			cs.setString(1, username);
			cs.setString(2, module);
			
			cs.registerOutParameter(3, Types.INTEGER);
			
			cs.executeQuery();
			
			output = cs.getInt(3);

			cs.close();
		} catch (SQLException e) {
			System.out.println("Connection Failed! " + e.toString());
		}
		finally {
            if (connection != null) {
            	connection.close();
            }
            
           // logger.info("Load user info success.");
        }
		return output>=1?true:false;
	}
}
