package serviceTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import eu.medsea.mimeutil.MimeUtil;
import service.FileServiceImpl;

public class FileServiceImplTest {

	FileServiceImpl fileServiceImpl;
	
	@Before
	public void initialise() {
		fileServiceImpl = new FileServiceImpl();
	}
	
	@Test
	public void testGetSupportedFiles() {
		ArrayList<File> listFiles = fileServiceImpl.getSupportedFiles("application/vndms-excel");
		System.out.println(listFiles.size());
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
		
		for(File f: listFiles) {
			boolean doesFileHaveMimeType = false;
			for(Object m : MimeUtil.getMimeTypes(f)) {
				String mimeType = m.toString();

				System.out.println(mimeType);
				if(mimeType.equals("application/vndms-excel")) {
					doesFileHaveMimeType = true;
				}
			}
			if(!doesFileHaveMimeType) {
				fail();
			}
		}
		assertTrue(true);
	}

}
