package server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import javafx.concurrent.Task;

public class DownloadServerFiles {
	
	//-----------------------------------------------------------------------------------------
    public static Task<Void> downloadFile(String fileName, long fileSize, String destination, String serverPath) {

        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            	
            	FTPClient ftpClient = Connect.getConnection();
				      OutputStream outputStream = null;

                try {

                    ftpClient.changeWorkingDirectory(serverPath);
                    ftpClient.enterLocalPassiveMode();
                    outputStream = new FileOutputStream(destination + "\\" + fileName);

                    for(int i = 0; i < fileSize; i++) {

                        if(isCancelled()) {
                            ftpClient.abort();
                            break;
                        }

                        updateProgress(i + 1, fileSize);
          
                    }

                    ftpClient.retrieveFile(fileName, outputStream);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                    	outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };
    }
}
