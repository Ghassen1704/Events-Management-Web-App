package org.munic.service;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.jvnet.hk2.annotations.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;





@Service
public class PhotoUpload {

	
	
	
 
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
			
			// Start Compression image
			if (filename.endsWith("jpg")) {
					File input = new File(uploadDirectory + "/" + dir + "/" +filename);
			        BufferedImage newimage = ImageIO.read(input);

			        File output = new File(uploadDirectory + "/" + dir + "/" +filename);
			        OutputStream out = new FileOutputStream(output);

			        ImageWriter writer =  ImageIO.getImageWritersByFormatName("jpg").next();
			        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
			        writer.setOutput(ios);

			        ImageWriteParam param = writer.getDefaultWriteParam();
			        if (param.canWriteCompressed()){
			            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			            param.setCompressionQuality(0.4f);
			        }

			        writer.write(null, new IIOImage(newimage, null, null), param);

			        out.close();
			        ios.close();
			        writer.dispose();
			}
			        // End Compression image 
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		// Return the new name of file
		return filename;
	}
}