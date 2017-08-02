package user;

/*
 * 
 * Handles uploading a single file to the FTP Server.
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import javafx.concurrent.Task;
import server.Connect;

public class UploadUserFiles {
	
	//-----------------------------------------------------------------------------------------
	public static Task<Void> uploadFile(String path, String fileName, String serverPath) {

		return new Task<Void>() {
			@Override
	        protected Void call() throws Exception {
				
				FTPClient ftpClient = Connect.getConnection();
				InputStream inputStream = null;
				
				try {
					
					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
					ftpClient.changeWorkingDirectory(serverPath);
					ftpClient.enterLocalPassiveMode();

					File filePath = new File(path);
					inputStream = new FileInputStream(filePath);
					
					for(int i = 0; i < filePath.length(); i++) {
						
						if(isCancelled()) {
							ftpClient.abort();
							break;
						}

						updateProgress(i + 1, filePath.length());
	                    
					}
					
					ftpClient.storeFile(fileName, inputStream);
					
				} catch (IOException e) {
					e.printStackTrace();
	            } finally {
	                try {
	                	inputStream.close();	             
	                } catch (IOException e) {
	                	e.printStackTrace();
	                }
	            }
	        return null;
			}
		};
	}
}
