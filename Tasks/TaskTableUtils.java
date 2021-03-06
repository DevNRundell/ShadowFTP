package task;

import javafx.scene.control.Button;

/*
 * 
 * Links the desired table with the model for the task table to access cell data
 * 
 */

import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TaskTableUtils {
	
    private TableView<TaskTableModel> taskTable;
    private TableColumn<TaskTableModel, String> taskFileNameCol;
    private TableColumn<TaskTableModel, Long> taskFileSizeCol;
    private TableColumn<TaskTableModel, Button> taskActionsCol;
	  private TableColumn<TaskTableModel, ProgressBar> taskProgressCol;

    //-----------------------------------------------------------------------------------------
	public TaskTableUtils(TableView<TaskTableModel> taskTable) {
		this.taskTable = taskTable;
	}

	//-----------------------------------------------------------------------------------------
	public void initTable() {
		
		taskFileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
		taskFileSizeCol.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
		taskActionsCol.setCellValueFactory(new PropertyValueFactory<>("cancelButton"));
		taskProgressCol.setCellValueFactory(new PropertyValueFactory<>("progressBar"));
	}
	
	//-----------------------------------------------------------------------------------------
	public void setTaskFileNameCol(TableColumn<TaskTableModel, String> taskFileNameCol) {
		this.taskFileNameCol = taskFileNameCol;
	}

	public void setTaskFileSizeCol(TableColumn<TaskTableModel, Long> taskFileSizeCol) {
		this.taskFileSizeCol = taskFileSizeCol;
	}

	public void setTaskActionsCol(TableColumn<TaskTableModel, Button> taskActionsCol) {
		this.taskActionsCol = taskActionsCol;
	}

	public void setTaskProgressCol(TableColumn<TaskTableModel, ProgressBar> taskProgressCol) {
		this.taskProgressCol = taskProgressCol;
	}
}
