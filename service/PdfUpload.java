package org.munic.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;



import org.jvnet.hk2.annotations.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;





@Service
public class PdfUpload {

	
	
	
 
	public static String uploadFile(MultipartFile file,String uploadDirectory, String dir ) {

		// Declare new name of file
		String dtf = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// Get extention of file
		String extension = "";
		int i = file.getOriginalFilename().lastIndexOf('.');
		if (i >= 0) {
			extension = file.getOriginalFilename().substring(i + 1);
		}
		// Old name of file
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		// Replace old by new name
		filename = filename.replace(filename, dtf +"."+ extension);
		// Get file path of image
		Path fileNameAndPath = Paths.get(uploadDirectory + "/" + dir, filename);

		try {
			Files.write(fileNameAndPath, file.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
		// Return the new name of file
		return filename;
	}
}