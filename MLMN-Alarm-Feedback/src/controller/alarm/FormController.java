package controller.alarm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import dao.CSystemConfigsDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysDefineSmsEmailDAO;
import dao.SysMailParameterMasterDAO;
import dao.SysUsersDAO;

import vo.CSystemConfigs;
import vo.SYS_PARAMETER;
import vo.SysDefineSmsEmail;
import vo.SysMailParameterMaster;
import vo.SysUsers;
import vo.alarm.utils.FileTools;
import vo.alarm.utils.Mail;
import vo.alarm.utils.StringTools;
import vo.alarm.utils.URLCoderTools;

@Controller
@RequestMapping("/form/*")
public class FormController {

	@Autowired
	private SysUsersDAO sysUsersDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private CSystemConfigsDAO cSystemConfigsDAO;
	

	@Autowired
	private SysDefineSmsEmailDAO  sysDefineSmsEmailDAO ;
	
	
	@Autowired
	private SysMailParameterMasterDAO sysMailParamenterMasterDAO;
	
	
	
	private static Logger logger = Logger.getLogger(FormController.class.getName());
	
	@RequestMapping("email")
	public ModelAndView email(ModelMap model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers user = sysUsersDao.selectByPrimaryKey(username);
		String filePath="";
		String emailTo="";
		List<CSystemConfigs> configList= cSystemConfigsDAO.getSystemConfigMailDefault();
		for (CSystemConfigs cSystemConfigs : configList) {
			if (cSystemConfigs.getParamName().equalsIgnoreCase("sendmail.userTo"))
			{
				emailTo =  cSystemConfigs.getParamValue();
			}
			if (cSystemConfigs.getParamName().equalsIgnoreCase("sendmail.filePath"))
			{
				filePath =  cSystemConfigs.getParamValue();
			}
		}
		List<String> fileAtt = FileTools.getFilename(filePath);
		
		model.addAttribute("user", user);
		model.addAttribute("fileAtt", fileAtt);
		model.addAttribute("email", emailTo);
		
		return new ModelAndView("jsp/form/emailDaily");
	}
	
	/*@RequestMapping("send")
	@ResponseBody
	Map<String, Object> sendMail(String fileAtt, String content, String password, 
			String email, String report, HttpServletRequest request) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		password = URLCoderTools.decode(password);
		fileAtt = URLCoderTools.decode(fileAtt);
		content = URLCoderTools.decode(content);
		report = URLCoderTools.decode(report);
		email = URLCoderTools.decode(email);
		
		email = email.replaceAll(";", ",");
		//System.out.println("INDEX: " + report.indexOf("<table"));
		
		report = report.replaceAll("<table", "<table style=\"border:1px solid #ddd; border-collapse:collapse;width:100%\"");
		report = report.replaceAll("<th", "<th style=\"border:1px solid #ddd;padding:0px 3px;background-color: oldlace;\"");
		report = report.replaceAll("<td", "<td style=\"border:1px solid #ddd;padding:3px;\"");
		
		//System.out.println("REPORT 2: " + report);
		
		String contentReport = content + "<br/><center>=======================================</center><br/>" + report;
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
	
		String filePath="";
		String subject="";
		String userFrom = "vhcjsc@gmail.com";
		List<CSystemConfigs> configList= cSystemConfigsDAO.getSystemConfigMailDefault();
		for (CSystemConfigs cSystemConfigs : configList) {
			if (cSystemConfigs.getParamName().equalsIgnoreCase("sendmail.userFrom"))
			{
				userFrom =  cSystemConfigs.getParamValue();
			}
			if (cSystemConfigs.getParamName().equalsIgnoreCase("sendmail.subject"))
			{
				subject =  cSystemConfigs.getParamValue();
			}
			if (cSystemConfigs.getParamName().equalsIgnoreCase("sendmail.filePath"))
			{
				filePath =  cSystemConfigs.getParamValue();
			}
		}
		//SYS_PARAMETER email = sysParameterDao.getValueByCodeName("EMAIL_REPORT", "EMAIL_TO");
		//SYS_PARAMETER subject = sysParameterDao.getValueByCodeName("EMAIL_REPORT", "TITLE_EMAIL");
	//	List<C> p = CSystemConfigsDAO.getSysParameterByCode("SERVER_EMAIL");
		
		List<CSystemConfigs> p = cSystemConfigsDAO.getSystemConfigMail();
		
		if (filePath == null || email == null || subject == null || p == null || p.size() == 0) {
			data.put("status", "failed");
			data.put("cause", "Cấu hình tham số gửi email lỗi");
			
			return data;
		}
		
		Properties props = new Properties();
		for (CSystemConfigs item: p) {
			props.put(item.getParamName(), item.getParamValue());
			//System.out.println(item.getName() + "-" + item.getValue());
		}
		String[] file = StringTools.parseString(fileAtt, '|');
		List<String> fileName = new ArrayList<String>();
		
		for (String item: file) {
			if (item != null && !item.trim().equals("")) {
				fileName.add(filePath.concat("/").concat(item));
			}
		}
		
		SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		String title = subject.replace("#DATE#", formatter.format(new Date()));
		
		try {
			
			//Xu ly dia chi thu gui va gui mail den tung thanh vien trong nhom.
			String[] groupList = email.split(",");
			String errorSend="Succeccfull";
			String status="S";
			String mailTo="";
			String temp="";
			for (String group : groupList) {
				
				if (group!=null && !group.equals(""))
				{
					String regex = 
							"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
									+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				    Pattern pattern = Pattern.compile(regex);
				    Matcher matcher = pattern.matcher(group.trim());
				    //Neu la email ca nhan
				    if (matcher.matches()) {
				    	mailTo+=temp+group.trim();
				    	temp=",";
				    	
				    }
				    else   //neu la nhom thi can lay ra danh sach email can gui
				    {
				    	System.out.println("send nhom");
				    	List<SysDefineSmsEmail> userList = sysDefineSmsEmailDAO.getListAll("MAIL", group.trim(), null);
				    	for (SysDefineSmsEmail user : userList) {
				    		System.out.println("getEmail:"+user.getGroupUser());
				    		if (user.getGroupUser()!=null && !user.getGroupUser().equals(""))
				    		{
				    			mailTo+=temp+user.getGroupUser();
						    	temp=",";
				    		}
					    		
				    	}
					}
				  }
				}
			//gui mail
			try
			{
				Mail mail = new Mail(props, fileName, userFrom, password,
						mailTo, title, contentReport);
				
				String result = mail.send();
				if (result != null) {
					data.put("status", "failed");
					data.put("cause", result);
					errorSend="Error in sending mail";
					status="F";
					return data;
				}
				
			}
			catch(Exception ex)
			{
				logger.warn("Gui mail that bai");
				System.out.println("Gui mail that bai");
				errorSend="Error in sending mail";
				status="F";
			}
			cSystemConfigsDAO.saveMail(subject,mailTo,errorSend,status,null,"DY",null);
		
		} catch (Exception ex) {
			data.put("status", "failed");
			data.put("cause", "Quá trình gửi email lỗi");
			
			return data;
		}
		
		data.put("status", "success");
		
		return data;
	}*/
	
	

	@RequestMapping("send")
	@ResponseBody
	Map<String, Object> sendMail( String content, String password, 
			String email, String report, HttpServletRequest request) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		password = URLCoderTools.decode(password);
		//fileAtt = URLCoderTools.decode(fileAtt);
		content = URLCoderTools.decode(content);
		report = URLCoderTools.decode(report);
		email = URLCoderTools.decode(email);
		
		email = email.replaceAll(";", ",");
		try
		{
			//Update thong tin gui mai vao trong sys_mail_parameter_master , xoa thong tin trong syssend
			sysMailParamenterMasterDAO.updateMailParameterMaster(email,content,null,"welcome");
			data.put("status", "success");
		}
		catch(Exception exp)
		{
			data.put("status", "failed");
		}
		return data;
	}
	
	
	@RequestMapping(value="upload/docpa", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> uploadProccess(MultipartHttpServletRequest req, HttpServletRequest request, HttpServletResponse response) throws IOException {

		Map<String, Object> data = new HashMap<String, Object>();
		
		CSystemConfigs directoryFolder = cSystemConfigsDAO.selectByPrimaryKey("sendmail.filePath");
		String filePath="/u01/data";
		if (directoryFolder!=null)
			{
					filePath= directoryFolder.getParamValue();
			}
		MultipartFile file = req.getFile("Filedata");
		
		InputStream is = null;
		
		String fileName;
		//Long fileSize;
		String fileExtend;
		//String filePath;
		//String fileStatus;
		
		fileName = FileTools.getFileName(file.getOriginalFilename());
		//fileSize = file.getSize();
		fileExtend = FileTools.getExtendOfFile(file.getOriginalFilename());
		//fileStatus = "Y";
		//filePath = FileTools.getSubFolderByDate(new Date(), "YYYY/MM/");
		
		FileTools.mkDir(filePath);
		
		String fileFullPath = filePath.concat("/").concat(fileName);
		
		//logger.info("fileFullPath: " + fileFullPath);
		
		//boolean error = false;
		try {
			is = request.getInputStream();
            
			OutputStream  os = null;
			
			try {
				File f = new File(fileFullPath.concat(fileExtend));
				int i = 0;
				String tmpFile = fileName;
				while (f.exists()) {
					i++;
					fileName = tmpFile.concat("(" + i + ")");
					String newFile = fileFullPath.concat("(" + i + ")").concat(fileExtend);
					f = new File(newFile);
				}
				
				os = new FileOutputStream(f);
			
				os.write(file.getBytes());
				
				os.close();
			} catch (Exception e) {
				//logger.warn("Error while saving file");
				
				data.put("status", "failed");
				
				return data;
			}
            
			data.put("status", "success");
			data.put("name", fileName.concat(fileExtend));
			
			return data;
			
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                is.close();
            } catch (IOException ignored) { }
        }
		
		data.put("status", "failed");
		
		return data;
	}
	
	
	@RequestMapping(value="file/download", method = RequestMethod.GET)
	public HttpEntity<byte[]> download(@RequestParam(required = false) String filename, Model model) {
		
		CSystemConfigs directoryFolder = cSystemConfigsDAO.selectByPrimaryKey("sendmail.filePath");
		String rootFolderImage="/u01/data";
		if (directoryFolder!=null)
			{
			rootFolderImage= directoryFolder.getParamValue();
			}
		
		File file = new File(rootFolderImage.concat("/").concat(filename));
		
		String fileExtend = FileTools.getExtendOfFile(filename);
		
		if (!file.exists()) {
			file = new File(rootFolderImage.concat("noprofile.png"));
		}
		
		byte documentBody[] = null;
		try {
			FileInputStream fin = new FileInputStream(file);
			documentBody = new byte[(int)file.length()];
			
			fin.read(documentBody);
			
			fin.close();
		} catch(Exception e) {
			//logger.warn("File not found: " + e);
			
			return null;
		}

	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(new MediaType("application", fileExtend));
	    header.set("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	    header.setContentLength(documentBody.length);

	    return new HttpEntity<byte[]>(documentBody, header);
	}

}
