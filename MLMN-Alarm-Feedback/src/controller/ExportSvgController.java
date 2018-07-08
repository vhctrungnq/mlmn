/*package controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.apps.rasterizer.Main;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExportSvgController {
	@RequestMapping("/exportSvg")
	public ModelAndView export(@RequestParam(required = true) String type, @RequestParam(required = true) String svg,
			@RequestParam(required = false) boolean isSvgFileName, @RequestParam(required = false) String filename, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/data";

		if (filename == null)
			filename = "chart";

		String tempName = UUID.randomUUID().toString();

		String ext = null;
		if (type.equalsIgnoreCase("image/png")) {
			ext = "png";

		} else if (type.equalsIgnoreCase("image/jpeg")) {
			ext = "jpg";

		} else if (type.equalsIgnoreCase("application/pdf")) {
			ext = "pdf";

		} else if (type.equalsIgnoreCase("image/svg+xml")) {
			ext = "svg";
		}

		String outfile = tempName + "." + ext;

		if (isSvgFileName) {
			String[] params = { "-scriptSecurityOff", "-m", type, "-q", "0.99", "-d", dataDir + "/" + outfile, dataDir + "/" + svg };
			(new Main(params)).execute();
		} else {
			File svgTempFile = new File(dataDir + "/" + tempName + ".svg");
			FileUtils.writeStringToFile(svgTempFile, svg);
			String[] params = { "-scriptSecurityOff", "-m", type, "-q", "0.99", "-d", dataDir + "/" + outfile, dataDir + "/" + tempName + ".svg" };
			(new Main(params)).execute();
			svgTempFile.delete();
		}

		File imageTempFile = new File(dataDir + "/" + outfile);
		response.setContentType(type);
		response.setContentLength((int) imageTempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(imageTempFile), response.getOutputStream());

		imageTempFile.delete();

		return null;
	}
}
*/