package service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import eu.medsea.mimeutil.MimeUtil;

public class FileServiceImpl implements FileService{

	public String[] getAllFilesInfo() {
		File files = new File("/Users/nawidmujadidi/Documents/test");
		String[] fileInfo = new String[(int) files.length()];
		int index = 0;
		for( File f : files.listFiles()) {
			try {
				fileInfo[index] = f.getName()+" "+Files.probeContentType(f.toPath())+" "+f.length()+" "+FilenameUtils.getExtension(f.getAbsolutePath());
			} catch (IOException e) {
				fileInfo[index] = f.getName()+" No Mime type "+f.length()+" "+FilenameUtils.getExtension(f.getAbsolutePath());
			}
		}
		return fileInfo;
	}

	public ArrayList<File> getSupportedFiles(String mimetype) {
		ArrayList<File> filesToReturn = new ArrayList<File>();
		File files = new File("src/main/resources/testFiles/");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
		for( File f : files.listFiles()) {

			for(Object m : MimeUtil.getMimeTypes(f)) {
				String mimeType = m.toString();

				if(mimeType.equals(mimetype)) {
					filesToReturn.add(f);
				}
			}

		}
			
		return filesToReturn;
	}

}
