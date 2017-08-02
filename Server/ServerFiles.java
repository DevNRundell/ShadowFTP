package server;

/*
 * 
 * Class handles file searching within a directory
 * 
 */

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServerFiles {
	
	private FTPClient ftpClient = Connect.getConnection();;
	
	//----------------------------------------------------------------------------------------
		public ObservableList<ServerFileTableModel> findFiles(String path) {
			
			ObservableList<ServerFileTableModel> serverFiles = FXCollections.observableArrayList();
			
			try {

				ftpClient.changeWorkingDirectory(path);		
				FTPFile[] ftpFiles = ftpClient.listFiles();	
        
				for(FTPFile file: ftpFiles) {
				
					if(file.isFile()) {
					
						String fileType = String .valueOf(file.getType());
						long fileSize = (file.getSize() / 1024);
					
						if(fileType != null) {
					
							serverFiles.add(new ServerFileTableModel(
									file.getName(),
									fileSize,
									fileType,
									path));
						} else {
						
							serverFiles.add(new ServerFileTableModel(
									file.getName(),
									fileSize,
									"Unknown",
									path));
						} 
					}
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(serverFiles != null) {
				return serverFiles;
			} else {
				return FXCollections.emptyObservableList();
			}
			
		}
		
}
