package com.raushan.blog.serviceImp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.raushan.blog.service.FileService;



@Service
public class FileServiceImp implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile multipartFile) throws IOException {
		
		// file Name
		String fileName = multipartFile.getOriginalFilename();
		
		//random name generator for File
		String randUUId =UUID.randomUUID().toString();
		String fileNameForImage = randUUId.concat(fileName).substring(fileName.lastIndexOf("."));
		
		//Full Path
		String filePath = path+File.separator+fileNameForImage;
		
		//create folder if not created
		File file = new File(path);
		if(!file.exists()) {
			file.mkdir();
		}
		
		//file copy
		Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
		
		return fileNameForImage;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws IOException {
		String fullPath = path+File.separator+fileName;
		InputStream inputStream = new FileInputStream(fullPath);
		//DB logic to return inputStream
		return inputStream;
	}

}