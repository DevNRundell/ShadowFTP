package server;

/*
 * 
 * Links the desired table with the model for the server table to access cell data
 * 
 */

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ServerFileTableUtils {
	
	private TableView<ServerFileTableModel> tableView;
	private TableColumn<ServerFileTableModel, String> fileNameTCol;
	private TableColumn<ServerFileTableModel, Long> fileSizeTCol;
	private TableColumn<ServerFileTableModel, String> fileTypeTCol;
	private TableColumn<ServerFileTableModel, String> fileLocTCol;

	//----------------------------------------------------------------------------------------
	public ServerFileTableUtils(TableView<ServerFileTableModel> tableView) {
		this.tableView = tableView;
	}
	
	//----------------------------------------------------------------------------------------
	//Initialization for Server File Table
	public void initFileTable() {
		
		fileNameTCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
		fileSizeTCol.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
		fileTypeTCol.setCellValueFactory(new PropertyValueFactory<>("fileType"));
		fileLocTCol.setCellValueFactory(new PropertyValueFactory<>("filePath"));
		
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	
	//----------------------------------------------------------------------------------------
	//Initialization for Download List Table
	public void initDownloadTable() {
			
	fileNameTCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
	fileLocTCol.setCellValueFactory(new PropertyValueFactory<>("filePath"));
		
	tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
	}

	//----------------------------------------------------------------------------------------
	public void setFileNameTCol(TableColumn<ServerFileTableModel, String> fileNameTCol) {
		this.fileNameTCol = fileNameTCol;
	}

	public void setFileSizeTCol(TableColumn<ServerFileTableModel, Long> fileSizeTCol) {
		this.fileSizeTCol = fileSizeTCol;
	}

	public void setFileTypeTCol(TableColumn<ServerFileTableModel, String> fileTypeTCol) {
		this.fileTypeTCol = fileTypeTCol;
	}

	public void setFileLocTCol(TableColumn<ServerFileTableModel, String> fileLocTCol) {
		this.fileLocTCol = fileLocTCol;
	}

}
