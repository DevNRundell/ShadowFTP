package server;

/*
 * 
 * Class handles loading root directories
 * For each root, all the sub directories are loaded.
 * 
 */

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ServerDirectory {
	
	private TreeView<String> treeView;
	private String ROOT_PATH = "\\";
	private FTPClient ftpClient = Connect.getConnection();;
	private String directoryPath;

	//----------------------------------------------------------------------------------------
	public void initialize(TreeView<String> treeView) throws IOException {
		this.treeView = treeView;
		loadRootDirectories();
	}
	
	//----------------------------------------------------------------------------------------
	public void loadRootDirectories() {
		
		TreeItem<String> parentRoot = new TreeItem<String>();
		treeView.setRoot(parentRoot);
		treeView.setShowRoot(false);

	    try {

	    		ftpClient.changeWorkingDirectory(ROOT_PATH);
	        FTPFile[] listDirectories = ftpClient.listDirectories();

	            if (listDirectories != null) {
	            	
	                for (FTPFile file : listDirectories) {
	                	
	                	directoryPath = "";           	
	                	directoryPath = ROOT_PATH + file.getName() + "\\";                	
	                	loadDirectories(directoryPath, parentRoot);
	                	
	                }	             
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	}

	//----------------------------------------------------------------------------------------
	private void loadDirectories(String path, TreeItem<String> subRootParent) throws IOException {
			
		ftpClient.changeWorkingDirectory(path);
		FTPFile[] listDirectories = ftpClient.listDirectories();
		
		TreeItem<String> subRoot = new TreeItem<String>(path);
		
		for (FTPFile file : listDirectories) {	
			path += file.getName() + "\\";
         	loadDirectories(path, subRoot);
        }
		
		subRootParent.getChildren().add(subRoot);
	}
}


