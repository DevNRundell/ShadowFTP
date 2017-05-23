package user;

/*
 * 
 * Class handles file searching within a directory
 * 
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserFiles {
	
	//----------------------------------------------------------------------------------------
	public ObservableList<UserFileTableModel> findFiles(String path) {
		
		ObservableList<UserFileTableModel> userFiles = FXCollections.observableArrayList();
		
		File[] files = new File(path).listFiles();
		
		for(File file: files) {
			
			if(file.isFile() && !file.isHidden()) {
				
				try {
					
					String fileType = Files.probeContentType(file.toPath());
					long fileSize = (file.length() / 1024);
					
					if(fileType != null) {
					
						userFiles.add(new UserFileTableModel(
								file.getName(),
								fileType,
								fileSize,
								file.getAbsolutePath()));
					} else {
						
						userFiles.add(new UserFileTableModel(
								file.getName(),
								"Unknown",
								fileSize,
								file.getAbsolutePath()));
					}
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		
		if(userFiles != null) {
			return userFiles;
		} else {
			return FXCollections.emptyObservableList();
		}
		
	}
}
