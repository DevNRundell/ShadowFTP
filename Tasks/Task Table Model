package task;

import javafx.scene.control.Button;

/*
 * 
 * Class is a model for the task table.
 * Displays file information and download or upload progress.
 * 
 */

import javafx.scene.control.ProgressBar;

public class TaskTableModel {
	
	private String fileName;
	private long fileSize;
	private Button cancelButton;
	private ProgressBar progressBar;

	//-----------------------------------------------------------------------------------------
	public TaskTableModel(String fileName, long fileSize, Button cancelButton, ProgressBar progressBar) {
	
		this.fileName = fileName;
		this.fileSize = fileSize; 
		this.cancelButton = cancelButton;
		this.progressBar = progressBar;
		
	}

	//-----------------------------------------------------------------------------------------
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
	
	public Button getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

}
