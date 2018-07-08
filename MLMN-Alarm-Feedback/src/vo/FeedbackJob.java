package vo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Controller;


import vn.com.vhc.alarm.core.AL_Global;
import vo.alarm.utils.Fb_Setting;
import vo.alarm.utils.SendMailSetting;
import vo.alarm.utils.feedback.EntityAction;
import vo.alarm.utils.feedback.EntityActionServiceLocator;


@Controller
public class FeedbackJob implements Job{

	
	public FeedbackJob(){}
	private static Logger logger = Logger.getLogger(FeedbackJob.class.getName());
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	private static DataSource dataSource = null;
	private static CallableStatement cs = null;
	private static Connection con = null;
	
	
	private void feedback() throws RemoteException, SQLException{
		
		try {
			FeedbackJob.loadSystemConfig();
		} catch (SQLException e) {
			logger.warn("Load system config failure: " + e.getMessage() + "; Service halt");
		}
		
		String serverNode = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_SERVER);
		String serverConfig = getServerNode();
		System.out.println("Feedback serverNode: "+serverNode);
		System.out.println("Feedback serverConfig: "+serverConfig);
		
		if (serverNode!=null && serverNode.equalsIgnoreCase(serverConfig)){
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String todate = df_full.format(cal.getTime());
			if (AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_DELAY_THREAD_KEY)!=null)
			{
				cal.add(Calendar.SECOND, -(Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_DELAY_THREAD_KEY))+15*60));
			}
			else
			{
				cal.add(Calendar.MINUTE, -15);
			}
			String fromdate = df_full.format(cal.getTime());
			
			String hour = todate.substring(11, 13);
			String hourData = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_TAKE_DATA_BY_HOUR_DATA);
			/*if(hour.equals(hourData)){
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(new Date());
				//cal1.add(Calendar.DAY_OF_MONTH, -1);
				cal1.add(Calendar.HOUR, -(Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_TAKE_DATA_BY_HOUR_DEFAULT))));
				fromdate = df.format(cal1.getTime()) + " 00:00:00";
			}*/
			logger.info("Feedback hour = " +hour+"- FEEDBACK_TAKE_DATA_BY_HOUR_DATA ="+hourData);
			if(hour.equals(hourData)){
				// xoa du lieu trong temp
				deleteFbProcessTemp();
				// Cat thoi gian de lay du lieu cho phan quan ly tien trinh
				int hourTakeData = Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_TAKE_DATA_BY_HOUR_DEFAULT));
				int hour_Nguyen ;
				int hour_Du;
				String todateOne = "";
				logger.info("Feedback hourTakeData = " +hourTakeData);
				if(hourTakeData > 48){
					hour_Nguyen = hourTakeData / 48;
					hour_Du = hourTakeData % 48;
					
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(new Date());
					todate = df_full.format(cal1.getTime());
					todateOne = todate;
					cal1.add(Calendar.HOUR, -48);
					fromdate = df.format(cal1.getTime()) + " 00:00:00";
					
					LayDuLieuTheoTrangThai(fromdate, todate);
					logger.info("Feedback(1) LayDuLieuTheoTrangThai: fromdate:  " +fromdate+ " - todate :"+todate);
					for(int j=1;j<hour_Nguyen;j++){
						todate = df.format(cal1.getTime()) + " 00:00:00";
						cal1.add(Calendar.HOUR, -48);
						fromdate = df.format(cal1.getTime()) + " 00:00:00";
						
						LayDuLieuTheoTrangThai(fromdate, todate);
						logger.info("Feedback("+(j+1)+") LayDuLieuTheoTrangThai: fromdate:  " +fromdate+ " - todate :"+todate);
					}
					
					todate = df.format(cal1.getTime()) + " 00:00:00";
					cal1.add(Calendar.HOUR, -hour_Du);
					fromdate = df.format(cal1.getTime()) + " 00:00:00";
					
					LayDuLieuTheoTrangThai(fromdate, todate);
					logger.info("Feedback(end) LayDuLieuTheoTrangThai: fromdate:  " +fromdate+ " - todate :"+todate);
					
					todate = todateOne;
				}
				else{
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(new Date());
					cal1.add(Calendar.HOUR, -(Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_TAKE_DATA_BY_HOUR_DEFAULT))));
					fromdate = df.format(cal1.getTime()) + " 00:00:00";
					
					LayDuLieuTheoTrangThai(fromdate, todate);
					logger.info("Feedback(hourTakeData<48) LayDuLieuTheoTrangThai: fromdate:  " +fromdate+ " - todate :"+todate);
				}
				deleteFbProcessExeption();
				
			}
			else{
				
				LayDuLieuTheoTrangThai(fromdate, todate);
				logger.info("Feedback("+hour+"h) LayDuLieuTheoTrangThai: fromdate:  " +fromdate+ " - todate :"+todate);
				
			}
			
			updateFromToDate(fromdate, todate);
		}
	}

	private void LayDuLieuTheoTrangThai(String fromdate, String todate){
		String status = "";	
		status = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_STATE_KEY);
		
		if(status == null || status == ""){
			String[] pDList = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_STATE_ALL_KEY).split(",");
			for(int j=0;j<pDList.length;j++){
				feedbacks(pDList[j], fromdate, todate);
			}
		}
		else{
			feedbacks(status, fromdate, todate);
		}
			
		
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("result: " + new Date());
		
		try {
			feedback();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String feedbacks(String status, String fromdate, String todate){
		
		try {
			FeedbackJob.loadSystemConfig();
		} catch (SQLException e) {
			logger.warn("Load system config failure: " + e.getMessage() + "; Service halt");
		}
		
		String user = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_USERNAME_KEY);
		String pass = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_PASSWORD_KEY);
		String dept_id = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_DEPARTMENT_ID_KEY);
		
		String[] pDeptList = dept_id.split(",");
		String record = "";
		for (String deptID : pDeptList) {
			logger.info("Lay FB phong ban: "+deptID);
			
			EntityActionServiceLocator locator = new EntityActionServiceLocator();
			
			try {
				con = dataSource.getConnection();
				
				EntityAction entity = locator.getEntityAction();
				String command = "command=fb_query;fromdate=" + fromdate + ";todate=" + todate + ";status=" + status + ";dept_id=" + deptID + ";";
				
				String result = entity.executeCommand(command, user, pass);
				record = result;
				
				result = result.replace(command + ";responce=", "");
				result = result.replace("||", "-;-");
				logger.info("result: " + result);
				if(!result.equals("null")){
					String[] pDList = result.split("-;-");
					for(int i=1;i<pDList.length;i++){
						detailsFeedbacks(pDList[i], user, pass, status,deptID);
					}
				}
			} catch (ServiceException e) {
				e.printStackTrace();
				logger.error("Error command=fb_query;fromdate=" + fromdate + ";todate=" + todate + ";status=" + status+": " + e.getMessage());
			} catch (RemoteException e) {
				e.printStackTrace();
				logger.error("Error command=fb_query;fromdate=" + fromdate + ";todate=" + todate + ";status=" + status+": " + e.getMessage());
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error("Error command=fb_query;fromdate=" + fromdate + ";todate=" + todate + ";status=" + status+": " + e.getMessage());
				
			}
			finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
			}
		}
		
		return record;
	}
	
	// Insert bản ghi vào bảng fb_process
	private static void detailsFeedbacks(String complain_id, String user, String pass, String status,String deptID){
		EntityActionServiceLocator locator = new EntityActionServiceLocator();
		try {
			EntityAction entity = locator.getEntityAction();
		
			// Thong tin chi tiet tung ban ghi
			String command = "command=fb_query_detail;complain_id=" + complain_id + ";";
			String result = entity.executeCommand(command, user, pass);
			result = result.replace(command + ";responce=", "");
			result = result.replace("||", "-;-");
			System.out.println("result: " + result);
			
			if(!result.equals("null")){
				String[] pDList = result.split("-;-");
				String tinhThanh = "";
				String quanHuyen = "";
				String phuongXa = "";
				if(!pDList[3].equals("")){
					tinhThanh = pDList[3].substring(0, 3);
					quanHuyen = pDList[3].substring(0, 6);
					phuongXa = pDList[3];
				}
				
				// Thong tin noi dung tra loi
				String responseContent = "";
				String processDate = "";
				
				String commandContent = "command=fb_query_detail_result;complain_id=" + complain_id + ";";
				String resultContent = entity.executeCommand(commandContent, user, pass);
				resultContent = resultContent.replaceAll("(.*)responce=", "");
				resultContent = resultContent.replace("||", "-;-");
				if(!resultContent.equals("null")){
					String[] pDListContent = resultContent.split("-;-");
					if(pDListContent.length == 3){
						processDate = pDListContent[1];
						responseContent = pDListContent[2];
					}
				}
				System.out.println("pDList.length: " + pDList.length);
				if(pDList.length == 14) {
					insertFbProcess(pDList[0], pDList[1], pDList[2], tinhThanh, quanHuyen, phuongXa, pDList[4], pDList[5], pDList[6], pDList[7], pDList[8], pDList[9], pDList[10], pDList[11], pDList[12], pDList[13], null, status, responseContent, processDate,deptID);
				}
				else if(pDList.length >= 15){
					insertFbProcess(pDList[0], pDList[1], pDList[2], tinhThanh, quanHuyen, phuongXa, pDList[4], pDList[5], pDList[6], pDList[7], pDList[8], pDList[9], pDList[10], pDList[11], pDList[12], pDList[13], pDList[14], status, responseContent, processDate,deptID);
				}
				
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("Error command=fb_query_detail;complain_id=" + complain_id+": " + e.getMessage());
			
		} catch (RemoteException e) {
			e.printStackTrace();
			logger.error("Error command=fb_query_detail;complain_id=" + complain_id+": " + e.getMessage());
		}
	}
	
	static {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:/vmsc6_alarm");
			logger.info("Init Datasource success.");
		} catch (NamingException e) {
			logger.error("Cannot init Datasource: " + e.getMessage());
		}
	}
	
	public static void loadSystemConfig() throws SQLException {
		Connection con = null;
		AL_Global.SYSTEM_CONFIG.clear();

		try {
			con = dataSource.getConnection();
			Statement st = con.createStatement();
			ResultSet result = st.executeQuery("Select PARAM_NAME, PARAM_VALUE from C_SYSTEM_CONFIGS_MLMN");
			String key = null;
			String value = null;
			while (result.next()) {
				key = result.getString(1);
				value = result.getString(2);
				if (key != null && value != null) {
					AL_Global.SYSTEM_CONFIG.setProperty(key, value);
				}
			}
			result.close();
			st.close();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
	
	public static void insertFbProcess(String idCentral, String soThueBao, String soLienLac, String tinhThanh, String quanHuyen, String phuongXa, String diaChi, String maLoaiPhanAnh, String loaiPhanAnh,
    		String htThanhToan, String noiDungPA, String thoiGianPA, String maDVXuLy, String tenDVXuLy, String loaiMang, String maVip, String tenVip, String trangThai, String responseContent, String processDate,String deptID) {

		
        try {
        	  
        	cs = con.prepareCall(
                "{call PK_FB_PROCESS_MLMN.INSERT_FB_PROCESS(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"
            );
            
        	cs.setString(1, idCentral);
        	cs.setString(2, soThueBao);
        	cs.setString(3, soLienLac);
        	cs.setString(4, tinhThanh);
        	cs.setString(5, quanHuyen);
        	cs.setString(6, phuongXa);
        	cs.setString(7, diaChi);
        	cs.setString(8, maLoaiPhanAnh);
        	cs.setString(9, loaiPhanAnh);
        	cs.setString(10, htThanhToan);
        	cs.setString(11, noiDungPA);
        	cs.setString(12, thoiGianPA);
        	cs.setString(13, maDVXuLy);
        	cs.setString(14, tenDVXuLy);
        	cs.setString(15, loaiMang);
        	cs.setString(16, maVip);
        	cs.setString(17, tenVip);
        	cs.setString(18, trangThai);
        	cs.setString(19, responseContent); // noi dung tra loi
        	cs.setString(20, processDate); // thời gian trả lời
        	cs.setString(21, "tòa nhà;cao ốc");
        	cs.setString(22, deptID);
        	cs.executeQuery();
         
            cs.close();
            
            logger.info("FB success:idCentral="+idCentral+",soThueBao="+soThueBao+",soLienLac="+soLienLac+",tinhThanh="+tinhThanh+",quanHuyen="+quanHuyen
        			+",phuongXa="+phuongXa+",diaChi="+diaChi+",maLoaiPhanAnh="+maLoaiPhanAnh+",loaiPhanAnh="+loaiPhanAnh+",htThanhToan="+htThanhToan+
        			",noiDungPA="+noiDungPA+",thoiGianPA="+thoiGianPA+",maDVXuLy="+maDVXuLy+",tenDVXuLy="+tenDVXuLy+",loaiMang="+loaiMang+
        			",maVip="+maVip+",tenVip="+tenVip+",trangThai="+trangThai+",responseContent="+responseContent+",processDate="+processDate+",deptID="+deptID);
    } catch (SQLException e) {

    	e.printStackTrace();
    	logger.error("Error insert FB: " + e.getMessage());
    }
        
	}
	
	public static void updateFromToDate(String fromDate, String toDate) {

		
        try {
        	con = dataSource.getConnection();  
        	cs = con.prepareCall(
                "{call PK_FB_PROCESS_MLMN.UPDATE_FROM_TO_DATE(?, ?)}"
            );
            
        	cs.setString(1, fromDate);
        	cs.setString(2, toDate);
        	cs.executeQuery();
         
            cs.close();
            
        } catch (SQLException e) {

        	e.printStackTrace();
        	logger.error("Error update FB: " + e.getMessage());
        }
        finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	
	public static int updateToCentralFeedback(String dept_id, String complain_id, String p_process_date, String p_content){
		EntityActionServiceLocator locator = new EntityActionServiceLocator();
		
		String result = "0";
		try {
			EntityAction entity = locator.getEntityAction();
			
			try {
				FeedbackJob.loadSystemConfig();
			} catch (SQLException e) {
				logger.warn("Load system config failure: " + e.getMessage() + "; Service halt");
			}
			//String dept_id = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_DEPARTMENT_ID_KEY);
			String user = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_USERNAME_KEY);
			String pass = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_PASSWORD_KEY);
			
			String command = "command=fb_update;complain_id="+complain_id+";process_date="+p_process_date+";content="+p_content+";dept_id="+dept_id+"";
			
			result = entity.executeCommand(command, user, pass);
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("Error command=fb_update;complain_id="+complain_id+";process_date="+p_process_date+";content="+p_content+": " + e.getMessage());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error command=fb_update;complain_id="+complain_id+";process_date="+p_process_date+";content="+p_content+": " + e.getMessage());
			
		}
		if(result.length() > 1){
			result = result.substring(result.length()-1, result.length());
		}
		return Integer.parseInt(result);
		
	}
	
	// XOA DU LIEU fb DIEU CHUYEN SANG CAC PHONG BAN KHAC KHONG THUOC MLMN
	private void deleteFbProcessExeption() {

        try {
        	con = dataSource.getConnection();  
        	cs = con.prepareCall(
                "{call PK_FB_PROCESS_MLMN.DELETE_FB_EXCEPTION()}"
            );
            cs.executeQuery();
         
            cs.close();
            
        } catch (SQLException e) {

        	e.printStackTrace();
        	logger.error("LOI DELETE_FB_EXCEPTION: " + e.getMessage());
        }
        finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
// XOA DU LIEU FB TEMP
	private void deleteFbProcessTemp() {

		
        try {
        	con = dataSource.getConnection();  
        	cs = con.prepareCall(
                "{call PK_FB_PROCESS_MLMN.DELETE_FB_TEMP()}"
            );
            
        	cs.executeQuery();
         
            cs.close();
            
        } catch (SQLException e) {

        	e.printStackTrace();
        	logger.error("Error DELETE_FB_TEMP: " + e.getMessage());
        }
        finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	
	/*public static String feedbacksCutByHours(String status, String fromdate, String todate){
		
	}*/
	public String getServerNode() {
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		//tt6 String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return props.getProperty("SERVER_NODE");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return null;
	}
		
}
