package server;

/*
 * 
 * Class is a model for the server table
 * Allows access to data within each cell in a row
 * 
 */

public class ServerFileTableModel {
	
	private String fileName;
	private long fileSize;
	private String fileType;
	private String filePath;
	
	//----------------------------------------------------------------------------------------
	public ServerFileTableModel(String fileName, long fileSize, String fileType, String filePath) {
		
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

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
