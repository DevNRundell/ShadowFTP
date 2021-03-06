package user;

/*
 * 
 * Class is a model for the task table.
 * Allows access to data within each cell in a row
 * 
 */

public class UserFileTableModel {
	
	private String fileName;
	private String fileType;
	private long fileSize;
	private String filePath;
	
	//----------------------------------------------------------------------------------------
	public UserFileTableModel(String fileName, String fileType, long fileSize, String filePath) {
		
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileType = fileType;
		this.filePath = filePath;
	}

	//----------------------------------------------------------------------------------------
	public String getFileName() {	
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileType() {	
		return fileType;
	}
	
	public void setFileType(String fileType) {	
		this.fileType = fileType;
	}
	
	public long getFileSize() {	
		return fileSize;
	}
	
	public void setFileSize(long fileSize) {	
		this.fileSize = fileSize;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {	
		this.filePath = filePath;
	}
}
