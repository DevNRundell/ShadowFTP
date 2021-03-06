package user;
/*
 * 
 * Links the desired table with the model for the user table to access cell data
 * 
 */


import java.io.File;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserFileTableUtils {

	private TableView<UserFileTableModel> tableView;
	private TableColumn<UserFileTableModel, String> fileNameCol;
	private TableColumn<UserFileTableModel, Long> fileSizeCol;
	private TableColumn<UserFileTableModel, File> fileTypeCol;
	private TableColumn<UserFileTableModel, String> fileLocCol;

	//----------------------------------------------------------------------------------------
	public UserFileTableUtils(TableView<UserFileTableModel> tableView) {			
		this.tableView = tableView;
	}
	
	//----------------------------------------------------------------------------------------
	public void initFileTable() {
		
		fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
		fileSizeCol.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
		fileTypeCol.setCellValueFactory(new PropertyValueFactory<>("fileType"));
		fileLocCol.setCellValueFactory(new PropertyValueFactory<>("filePath"));
		
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	
	//----------------------------------------------------------------------------------------
	public void initUploadListTable() {
		
		fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
		fileLocCol.setCellValueFactory(new PropertyValueFactory<>("filePath"));
		
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	//----------------------------------------------------------------------------------------
	public void setFileNameCol(TableColumn<UserFileTableModel, String> fileNameCol) {
		this.fileNameCol = fileNameCol;
	}

	public void setFileSizeCol(TableColumn<UserFileTableModel, Long> fileSizeCol) {
		this.fileSizeCol = fileSizeCol;
	}

	public void setFileTypeCol(TableColumn<UserFileTableModel, File> fileTypeCol) {
		this.fileTypeCol = fileTypeCol;
	}

	public void setFileLocCol(TableColumn<UserFileTableModel, String> fileLocCol) {
		this.fileLocCol = fileLocCol;
	}
}
