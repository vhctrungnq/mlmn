package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import dao.SYS_PARAMETERDAO;
import dao.SysFilesDAO;
import vo.dictionary.FileTools;

import vo.SYS_PARAMETER;
import vo.SysFiles;

@Controller
@RequestMapping("/file/*")
public class FileController {
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private SysFilesDAO sysFilesDAO;
	private static Logger logger = Logger.getLogger(FileController.class.getName());
	
	@RequestMapping(value="upload/docpa", method = RequestMethod.POST)
	public @ResponseBody
	Integer uploadProccess(MultipartHttpServletRequest req, HttpServletRequest request, HttpServletResponse response) throws IOException {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<SYS_PARAMETER> directoryFolder = sysParameterDao.getSPByCodeAndName("SYSTEM_UPLOAD", "upload.directory");
		
		MultipartFile file = req.getFile("Filedata");
		
		InputStream is = null;
		
		String fileName;
		
		String fileExtend;
		String filePath;

		
		fileName = FileTools.getFileName(file.getOriginalFilename());

		fileExtend = FileTools.getExtendOfFile(file.getOriginalFilename());

		filePath = FileTools.getSubFolderByDate(new Date(), "YYYY/MM/");
		
		FileTools.mkDir(directoryFolder.get(0).getValue().concat(filePath));
		
		String fileFullPath = directoryFolder.get(0).getValue().concat(filePath).concat(fileName);
		
		logger.info("fileFullPath: " + fileFullPath);
		
		boolean error = false;
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
				logger.warn("Error while saving file");
				
				return null;
			}
            
            if (!error) {
            	
            	SysFiles sysUpload = new SysFiles();
            	
            	sysUpload.setFileName(fileName);
            	sysUpload.setFileExtension(fileExtend);
            	sysUpload.setFilePath(filePath.concat(fileName).concat(fileExtend));
            	sysUpload.setFileType(fileExtend);
            	sysUpload.setCreatedBy(username);
            	
            	sysFilesDAO.insertSelectiveAndReturn(sysUpload);
            	
    			return sysUpload.getId();
            } else { 
            }
        } catch (FileNotFoundException ex) { 
        } catch (IOException ex) { 
        } finally {
            try {
                is.close();
            } catch (IOException ignored) { }
        }
		 

		return 0;
	}
	
	@RequestMapping(value="download", method = RequestMethod.GET)
	public HttpEntity<byte[]> download(@RequestParam(required = false) Integer id, @RequestParam(required = false) String newpass, Model model) {
		
		List<SYS_PARAMETER> directoryFolder = sysParameterDao.getSPByCodeAndName("SYSTEM_UPLOAD", "upload.directory");
		String rootFolderImage = directoryFolder.get(0).getValue();
		
		SysFiles sysUpload = sysFilesDAO.selectByPrimaryKey(id);
		
		String fileName = sysUpload.getFileName();
		String filePath = sysUpload.getFilePath();
		String fileExtend = sysUpload.getFileExtension();
		
		File file = new File(rootFolderImage.concat(filePath));
		
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
			logger.warn("File not found: " + e);
			
			return null;
		}

	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(new MediaType("application", fileExtend));
	    header.set("Content-Disposition", "attachment; filename=\"" + fileName.concat(fileExtend) + "\"");
	    header.setContentLength(documentBody.length);

	    return new HttpEntity<byte[]>(documentBody, header);
	}
	
	@RequestMapping("delete")
	public @ResponseBody 
	int delete(@RequestParam(required = false) Integer id) {
		
		logger.info("Delete: " + id);
		SysFiles sysFile = sysFilesDAO.selectByPrimaryKey(id);
		
		List<SYS_PARAMETER> directoryFolder = sysParameterDao.getSPByCodeAndName("SYSTEM_UPLOAD", "upload.directory");
		String rootFolderImage = directoryFolder.get(0).getValue();
		
		String filePath = sysFile.getFilePath();
		
		File file = new File(rootFolderImage.concat(filePath));
		
		if (file.delete()) {
			sysFilesDAO.deleteByPrimaryKey(id);
		} else {
			return 0;
		}
		
		return 1;
	}
	
	@RequestMapping(value="get")
    public @ResponseBody 
    SysFiles dsgui(@RequestParam(required = false) Integer fileId, HttpServletRequest request, HttpServletResponse response) {
		
		SysFiles file = sysFilesDAO.selectByPrimaryKey(fileId);
		
		return file;
	}
	
	// upload file alarm
	@RequestMapping(value="upload-alarm-syn/docpa", method = RequestMethod.POST)
	public @ResponseBody
	Integer uploadFileAlarm(MultipartHttpServletRequest req, HttpServletRequest request, HttpServletResponse response) throws IOException {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<SYS_PARAMETER> directoryFolder = sysParameterDao.getSPByCodeAndName("SYSTEM_UPLOAD", "upload.directory.alarm-syn");
		
		MultipartFile file = req.getFile("Filedata");
		
		InputStream is = null;
		
		String fileName;
		
		String fileExtend;
		String filePath;

		
		fileName = FileTools.getFileName(file.getOriginalFilename());

		fileExtend = FileTools.getExtendOfFile(file.getOriginalFilename());

		filePath = FileTools.getSubFolderByDate(new Date(), "yyyy/MM/");
		
		FileTools.mkDir(directoryFolder.get(0).getValue().concat(filePath));
		
		String fileFullPath = directoryFolder.get(0).getValue().concat(filePath).concat(fileName);
		
		
		logger.info("fileFullPath: " + fileFullPath);
		
		boolean error = false;
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
				logger.warn("Error while saving file");
				
				return null;
			}
            
            if (!error) {
            	
            	SysFiles sysUpload = new SysFiles();
            	
            	sysUpload.setFileName(fileName);
            	sysUpload.setFileExtension(fileExtend);
            	sysUpload.setFilePath(filePath.concat(fileName).concat(fileExtend));
            	sysUpload.setFileType(fileExtend);
            	sysUpload.setCreatedBy(username);
            	
            	sysFilesDAO.insertSelectiveAndReturn(sysUpload);
            	
    			return sysUpload.getId();
            } else { 
            }
        } catch (FileNotFoundException ex) { 
        } catch (IOException ex) { 
        } finally {
            try {
                is.close();
            } catch (IOException ignored) { }
        }
		 

		return 0;
	}
	
	@RequestMapping(value="download-alarm-syn", method = RequestMethod.GET)
	public HttpEntity<byte[]> downloadFileAlarm(@RequestParam(required = false) Integer id, @RequestParam(required = false) String newpass, Model model) {
		
		List<SYS_PARAMETER> directoryFolder = sysParameterDao.getSPByCodeAndName("SYSTEM_UPLOAD", "upload.directory.alarm-syn");
		String rootFolderImage = directoryFolder.get(0).getValue();
		
		SysFiles sysUpload = sysFilesDAO.selectByPrimaryKey(id);
		
		String fileName = sysUpload.getFileName();
		String filePath = sysUpload.getFilePath();
		String fileExtend = sysUpload.getFileExtension();
		
		File file = new File(rootFolderImage.concat(filePath));
		
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
			logger.warn("File not found: " + e);
			
			return null;
		}

	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(new MediaType("application", fileExtend));
	    header.set("Content-Disposition", "attachment; filename=\"" + fileName.concat(fileExtend) + "\"");
	    header.setContentLength(documentBody.length);

	    return new HttpEntity<byte[]>(documentBody, header);
	}
	
	@RequestMapping("delete-alarm-syn")
	public @ResponseBody 
	int deleteFileAlarm(@RequestParam(required = false) Integer id) {
		
		logger.info("Delete: " + id);
		SysFiles sysFile = sysFilesDAO.selectByPrimaryKey(id);
		
		List<SYS_PARAMETER> directoryFolder = sysParameterDao.getSPByCodeAndName("SYSTEM_UPLOAD", "upload.directory.alarm-syn");
		String rootFolderImage = directoryFolder.get(0).getValue();
		
		String filePath = sysFile.getFilePath();
		
		File file = new File(rootFolderImage.concat(filePath));
		
		if (file.delete()) {
			sysFilesDAO.deleteByPrimaryKey(id);
		} else {
			return 0;
		}
		
		return 1;
	}
}
