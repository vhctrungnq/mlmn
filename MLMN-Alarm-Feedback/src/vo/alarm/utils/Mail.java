package vo.alarm.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class Mail {

	private static Logger logger = Logger.getLogger(Mail.class.getName());

	private Properties props = new Properties();
	private Session session;
	private Transport transport;

	private String user;
	private String pass;
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String bodyText;
	private List<String> file;

	public Mail(String user, String pass, String to,String cc,String bcc, String subject,
			String bodyText) {
		this.user = user;
		this.pass = pass;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.bodyText = bodyText;
		// this.file = "C:/Users/Phoenix/Desktop/Send/";

		this.props.put("mail.smtp.starttls.enable", "true");
		this.props.put("mail.smtp.host", "smtp.gmail.com");
		this.props.put("mail.transport.protocol", "smtps");
		this.props.put("mail.debug", "true");
		this.props.put("mail.smtp.port", "587");
		this.props.put("mail.smtps.auth", "true");

		this.props.put("mail.smtp.user", this.user);
		this.props.put("mail.smtp.pass", this.pass);
	}

	public Mail(Properties props, List<String> file, String user, String pass,
			String to, String cc,String bcc, String subject, String bodyText) {
		this.props = props;

		this.user = user;
		this.pass = pass;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.bodyText = bodyText;
		this.file = file;
		//System.out.println(this.props.getProperty("mail.smtp.starttls.enable"));
		this.props.put("mail.smtp.user", this.user);
		this.props.put("mail.smtp.pass", this.pass);
	}

	public String send() {
		String host = props.getProperty("mail.smtp.host");
		final String user = props.getProperty("mail.smtp.user");
		final String pass = props.getProperty("mail.smtp.pass");

		if (session == null) {
			session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(user, pass);
						}
					});
		}

		if (transport == null || !transport.isConnected()) {
			try {
				transport = session.getTransport(props
						.getProperty("mail.transport.protocol"));

				transport.connect(host, user, pass);

			} catch (MessagingException e) {
				logger.warn("ERROR SendMail: " + e.toString());
			}
		}

		String result = send(user, to, cc, bcc, subject, bodyText); // send email

		try {
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();

			logger.warn("ERROR SendMail: " + e.toString());
		}

		return result;
	}

	public String send(String from, String to, String cc,String bcc, String subject, String text) {
		
		try {
			InternetAddress fromAddress = null;

			MimeMessage message = new MimeMessage(session);

			try {
				fromAddress = new InternetAddress(from);

				/*
				 * int i = 0; for (String m: mailCcList) { ccList[i] = new
				 * InternetAddress(m); i++; }
				 */
			} catch (AddressException e) {
				logger.warn("ERROR SendMail: " + e.toString());

				return e.toString();
			}

			// set message
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			message.setFrom(fromAddress);
			// message.setRecipients(RecipientType.TO, ccList);

			if (to.indexOf(',') > 0) {
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(to));
			} else {
				message.setRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
			}

			//03/03/2014 cc,bcc  mail
			if (cc!=null && !cc.equals(""))
			{
				if (cc.indexOf(',') > 0)   
		            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));   
		        else  
		            message.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
			}
			if (bcc!=null && !bcc.equals(""))
			{
				if (bcc.indexOf(',') > 0)   
		            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));   
		        else  
		            message.setRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
			}
			message.setSubject(subject, "UTF-8");

			// Set the email message text.
			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setHeader("Content-Type", "text/plain; charset=UTF-8");
			messagePart.setContent(text, "text/html; charset=utf-8");

			// Set the email attachment file
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart);

			try {
				if (file != null) {
					for (String item : file) {
						if (FileTools.isExistsFile(item)) {
							MimeBodyPart attachmentPart = new MimeBodyPart();
							FileDataSource fileDataSource = new FileDataSource(
									item) {
								@Override
								public String getContentType() {
									return "application/octet-stream";
								}
							};

							attachmentPart.setDataHandler(new DataHandler(
									fileDataSource));
							attachmentPart
									.setFileName(fileDataSource.getName());

							multipart.addBodyPart(attachmentPart);
						}
					}
				}
			} catch (Exception ex) {
				logger.warn("Error file attach");

				return "Error file attach";
			}

			message.setContent(multipart);

			// send message
			transport.sendMessage(message, message.getAllRecipients());

			logger.info("Send email success: " + to);
			// return true;
		} catch (MessagingException e) {
			logger.warn("Error MessagingException: " + e.toString());
			System.out.println("Error MessagingException: " + e.toString());
			// return false;
			return e.toString();
		}

		return null;
	}

	public static void main(String arg[]) {

		Properties p = new Properties();
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.transport.protocol", "smtps");
		p.put("mail.debug", "true");
		p.put("mail.smtp.port", "587");
		p.put("mail.smtps.auth", "true");
		
		/*p.put("mail.transport.protocol", "smtp");
		p.put("mail.smtp.host", "10.151.6.111");
		p.put("mail.smtp.port", "25");
		p.put("mail.smtps.auth", "true");
		p.put("mail.smtp.auth.mechanisms", "NTLM");
		p.put("mail.smtp.auth.ntlm.disable", "false");
		p.put("mail.smtp.auth.ntlm.domain", "mylm");
		p.put("mail.imap.host", "10.151.6.111");
		p.put("mail.store.protocol", "imap");
		p.put("mail.smtp.ssl.enable", "false");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.debug", "true");*/
		
		String content = ""
				+ "<span>Xin chào Anh/Chị!<br/>"
				+ "Kính gửi Anh/Chị danh sách giám sát KPI RNC theo giờ 16/01/2014 10:00:00 :<br/></span><br/><center><div><br><h2 align=\"left\" style=\"font-weight: bold;font-size: 12;\">1. RNC có CSSR < Ngưỡng quy định:</h2><table style=\" border-collapse:collapse;border:1px solid;width:100%;font-size: 9pt;\" cellpadding=\"5\" cellspacing=\"0\" name = \"V_RP_HR_BSC3G_MAIL\" ><thead><tr align=\"center\" style=\"font-weight: bold;background-color:#00B050;color:white;\"><th style=\"border:1px solid #000000;\" >STT</th><th style=\"border:1px solid #000000;\" >DATETIME </th><th style=\"border:1px solid #000000;\" >RNC ID</th><th style=\"border:1px solid #000000;\" >AMR (Erl)</th><th style=\"border:1px solid #000000;\" >HSDPA (GB)</th><th style=\"border:1px solid #000000;\" >SPEECH CSSR</th><th style=\"border:1px solid #000000;\" >HSDPA CSSR</th><th style=\"border:1px solid #000000;\" >FAIL BTS</th><th style=\"border:1px solid #000000;\" >FAIL IUB</th><th style=\"border:1px solid #000000;\" >FAIL RNC</th><th style=\"border:1px solid #000000;\" >CONTINUOUS HOURS</th></tr></thead><tbody></tbody></table><br><h2 align=\"left\" style=\"font-weight: bold;font-size: 12;\">2. Giám sát 5 cell kém theo tỉnh:</h2><table style=\" border-collapse:collapse;border:1px solid;width:100%;font-size: 9pt;\" cellpadding=\"5\" cellspacing=\"0\" name = \"V_RP_HR_CELL3G_MAIL\" ><thead><tr align=\"center\" style=\"font-weight: bold;background-color:#00B050;color:white;\"><th style=\"border:1px solid #000000;\" >DATE TIME</th><th style=\"border:1px solid #000000;\" >REASON </th><th style=\"border:1px solid #000000;\" >RNC ID</th><th style=\"border:1px solid #000000;\" >CELL</th><th style=\"border:1px solid #000000;\" >AMR (Erl)</th><th style=\"border:1px solid #000000;\" >HSDPA (MB) </th><th style=\"border:1px solid #000000;\" >SPEECH CSSR</th><th style=\"border:1px solid #000000;\" >HSDPA CSSR</th><th style=\"border:1px solid #000000;\" >FAIL BTS </th><th style=\"border:1px solid #000000;\" >FAIL IUB </th><th style=\"border:1px solid #000000;\" >CONTINUOUS-HOURS</th><th style=\"border:1px solid #000000;\" >HOURS/7DAYS </th><th style=\"border:1px solid #000000;\" >DAYS/7DAYS </th></tr></thead><tbody></tbody></table><br><br></div></center><br/><span><b>Ghi chú:</b>"
				+ "<br/>"
				+ "    * Fail BTS > 20<br/>"
				+ "    * Fail IUB > 20<br/>"
				+ "    * Dữ liệu trong database 8h có nghĩa là dữ liệu thực tế từ 8h-9h.<br/>"
				+ "<i>Đây là Email được gửi tự động, Anh/Chị vui lòng không trả lời Email này.</i>"
				+ "<br/>"
				+ "<i>Mọi chi tiết xin liên hệ:<br/>"
				+ "Cao Thị Vân Anh<br/>"
				+ "Công ty VHC Soft<br/>"
				+ "Email: vananhct@vhc.com.vn. Điện thoại: 01689980192</i></span>";

		List<String> file = new ArrayList<String>();
		file.add("THONG_KE_LOI_TRUYEN_DAN201403051035.xls");
		file.add("THONG_KE_LOI_TRUYEN_DAN201403051044.xls");
		String filePath="/u02/file_attach/kpi_daily";
		List<String> f = new ArrayList<String>();
		
		//zip file to send
		String fileZipName= filePath.concat("/").concat("TEST.zip");
		zipFiles(filePath,file,fileZipName);
		f.add(fileZipName);
		
		

		try {
			/*Mail mail = new Mail(p, f, "c6_ddh_cthtvhkt@vms.com.vn", "c6_ddh_cthtvhkt@123",
					"caoanhhus@gmail.com","caoanhhus@gmail.com","vananhct@vhc.com.vn",
					"Test mail cc, bcc", content);*/
			Mail mail = new Mail(p, f, "vananhct@vhc.com.vn", "gaconchaylonton",
			"caoanhhus@gmail.com","caoanhhus@gmail.com",null,
			"Test mail cc, bcc", content);

			mail.send();
		} catch (Exception ex) {
			logger.warn("SENDING_MAIL:", ex);
			System.out.println("Gui mail that bai");
		}
	}
	
	public static void zipFiles(String path,List<String> files, String fileZipname) {
		FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(fileZipname);
            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
            for(String fileName:files){
            	String filePath = path.concat("/").concat(fileName);
                File input = new File(filePath);
                fis = new FileInputStream(input);
                ZipEntry ze = new ZipEntry(input.getName());
                System.out.println("Zipping the file: "+input.getName());
                zipOut.putNextEntry(ze);
                byte[] tmp = new byte[4*1024];
                int size = 0;
                while((size = fis.read(tmp)) != -1){
                    zipOut.write(tmp, 0, size);
                }
                zipOut.flush();
                fis.close();
            }
            zipOut.close();
            System.out.println("Done... Zipped the files...");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try{
                if(fos != null) fos.close();
            } catch(Exception ex){
                 
            }
        }
    }
}
