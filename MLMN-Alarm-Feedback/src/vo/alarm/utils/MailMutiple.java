package vo.alarm.utils;
import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class MailMutiple {
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
	private List<File> fileImage;
	private List<String> file;
	
	public MailMutiple(String user, String pass, String to,String cc,String bcc,  String subject, String bodyText) {
		this.user 		= user;
		this.pass 		= pass;
		this.to 		= to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject 	= subject;
		this.bodyText 	= bodyText;
		//this.file 	= "C:/Users/Phoenix/Desktop/Send/";
		
		this.props.put("mail.smtp.starttls.enable", 	"true");
		this.props.put("mail.smtp.host", 				"smtp.gmail.com");
		this.props.put("mail.transport.protocol", 		"smtps");
		this.props.put("mail.debug", 					"true");
		this.props.put("mail.smtp.port", 				"587");
		this.props.put("mail.smtps.auth", 				"true");
		
		this.props.put("mail.smtp.user", 				this.user);
		this.props.put("mail.smtp.pass", 				this.pass);
	}
	
	public MailMutiple(Properties props, List<String> file,List<File> fileImage, String user, String pass, String to,
			String cc,String bcc,String subject, String bodyText) {
		this.props = props;
		
		this.user 		= user;
		this.pass 		= pass;
		this.to 		= to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject 	= subject;
		this.bodyText 	= bodyText;
		this.file 		= file;
		this.fileImage 	= fileImage;
		System.out.println(this.props.getProperty("mail.smtp.starttls.enable"));
		this.props.put("mail.smtp.user", 				this.user);
		this.props.put("mail.smtp.pass", 				this.pass);
	}

	public String send() {
		String host 		= props.getProperty("mail.smtp.host");
		final String user 	= props.getProperty("mail.smtp.user");
		final String pass 	= props.getProperty("mail.smtp.pass");
		
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
				transport = session.getTransport(props.getProperty("mail.transport.protocol"));
				
				transport.connect(host, user, pass);
				
			} catch (MessagingException e) {
				logger.warn("ERROR SendMail: " + e.toString());
			}
		}
		
		String result = send(user, to, cc, bcc, subject, bodyText);				// send email
		
		try {
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
			
			logger.warn("ERROR SendMail: " + e.toString());
		}
		
		return result;
	}
	
	public String send(String from, String to, String cc,String bcc, String subject, String text) {
		// send
		
		try {
			InternetAddress fromAddress = null;
			
			MimeMessage message = new MimeMessage(session);
			
			try {
				fromAddress = new InternetAddress(from);
			} catch (AddressException e) {
				logger.warn("ERROR SendMail: " + e.toString());
				
				return e.toString();
			}
			
			// set message
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			message.setFrom(fromAddress);
			if (to.indexOf(',') > 0) {
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        } else {
	            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
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
			
            //Set the email message text.
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setHeader("Content-Type", "text/plain; charset=UTF-8");
            messagePart.setContent(text, "text/html; charset=utf-8");
            
            // Set the email attachment file
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            
            try {
            	if (fileImage != null) {
		            for(File item: fileImage) {
		            	MimeBodyPart attachmentPart = new MimeBodyPart();
		            	FileDataSource fileDataSource = new FileDataSource(item) {
		                    @Override
		                    public String getContentType() {
		                        return "application/octet-stream";
		                    }
		                };
		                
		                attachmentPart.setDataHandler(new DataHandler(fileDataSource));
		                attachmentPart.setFileName(fileDataSource.getName());
		                
		                // Insert inline image to email
		               attachmentPart.setDisposition(Part.INLINE);
		                attachmentPart.setContentID("<"+item.getName()+">");
		                attachmentPart.addHeader("Content-Type", "image/png");
		                
		                multipart.addBodyPart(attachmentPart);
		            }
            	}
            } catch(Exception ex) {
            	logger.warn("Error file attach");
            	
            	return "Error file attach";
            }
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
			
			transport.close();
			
			logger.info("Send email success: " + to);
			//return true;
		} catch (MessagingException e) {
			logger.warn("Error MessagingException: " + e.toString());
			//return false;
			return e.toString();
		}
		
		return null;
	}
	
	public static void main(String arg[]) {
		
		Properties p = new Properties();
		p.put("mail.smtp.starttls.enable", 		"true");
		p.put("mail.smtp.host", 				"smtp.gmail.com");
		p.put("mail.transport.protocol", 		"smtps");
		p.put("mail.debug", 					"true");
		p.put("mail.smtp.port", 				"587");
		p.put("mail.smtps.auth", 				"true");
		
		String content = "<center>Nguyá»…n Há»¯u Ä?áº¡t</center><table style=\"border:1px solid #ddd\"><tr><td>a</td></tr></table>";
		
		//List<String> f = new ArrayList<String>();
		//f.add("C:/Users/Phoenix/Desktop/Send/Book.xls");
		//f.add("C:/Users/Phoenix/Desktop/Send/Book.xlsx");
		//List<String> f = FileTools.getFilename("C:/Users/Phoenix/Desktop/Send/");
		
		MailMutiple mail = new MailMutiple(p, null, null,"vhcjsc@gmail.com", "vhcsoft@123",
				"mr.datnh@gmail.com,datnh@vhc.com.vn",null,null,
				"Nguyá»…n Há»¯u Ä?áº¡t", content);
		
		mail.send();
		
	}
}
