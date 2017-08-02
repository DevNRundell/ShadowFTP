package controller;

/*
 * 
 * Class handles all the actions the user can use to interact
 * with connecting to an FTP site, uploading and downloading
 * files and accessing bookmarks.
 */


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bookmark.ServerBookmark;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.Connect;
import server.DownloadServerFiles;
import server.ServerDirectory;
import server.ServerFileTableModel;
import server.ServerFileTableUtils;
import server.ServerFiles;
import task.TaskTableModel;
import task.TaskTableUtils;
import user.UploadUserFiles;
import user.UserDirectory;
import user.UserFileTableUtils;
import user.UserFileTableModel;
import user.UserFiles;

public class MainViewController implements Initializable{

	@FXML
	private TableView<UserFileTableModel> userFileTable;
	@FXML
	private TableColumn<UserFileTableModel, String> userFileNameCol;
	@FXML
	private TableColumn<UserFileTableModel, Long> userFileSizeCol;
	@FXML
	private TableColumn<UserFileTableModel, File> userFileTypeCol;
	@FXML
	private TableColumn<UserFileTableModel, String> userFileLocCol;
	@FXML
	private TableView<ServerFileTableModel> serverFileTable;
	@FXML
	private TableColumn<ServerFileTableModel, String> serverFileNameCol;
	@FXML
	private TableColumn<ServerFileTableModel, Long> serverFileSizeCol;
	@FXML
	private TableColumn<ServerFileTableModel, String> serverFileTypeCol;
	@FXML
	private TableColumn<ServerFileTableModel, String> serverFileLocCol;
	@FXML
    private TableView<TaskTableModel> taskTable;
    @FXML
    private TableColumn<TaskTableModel, String> taskFileNameCol;
    @FXML
    private TableColumn<TaskTableModel, Long> taskFileSizeCol;
    @FXML
    private TableColumn<TaskTableModel, Button> taskActionsCol;
    @FXML
    private TableColumn<TaskTableModel, ProgressBar> taskProgressCol;
    @FXML
    private TableView<UserFileTableModel> userUploadTable;
    @FXML 
    private TableColumn<UserFileTableModel, String> uploadFileNameCol;
    @FXML
    private TableColumn<UserFileTableModel, String> uploadFileLocCol;
    @FXML   
    private TableView<ServerFileTableModel> serverDownloadTable;
    @FXML 
    private TableColumn<ServerFileTableModel, String> downloadFileNameCol;
    @FXML
    private TableColumn<ServerFileTableModel, String> downloadFileLocCol;
	@FXML
	private ComboBox<String> userDirCB;
    @FXML
    private MenuItem disconnectMenuItem;
    @FXML
    private MenuItem bookmarkMenuItem;
    @FXML
    private TextField hostAddressTF;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private TextField portTF;
    @FXML
    private TreeView<Path> userDirectoryTreeView;
    @FXML
    private TreeView<String> serverDirectoryTreeView;
    @FXML
    private Label statusLabel;
    @FXML
    private TextArea logTA;
    @FXML
    private ProgressIndicator progIndicatorUserDir;
    @FXML
    private ProgressIndicator progIndicatorServerDir;
    @FXML
    private ImageView connectionImageView;
    @FXML
    private MenuButton connectButton;
    @FXML
    private Button uploadButton;
    @FXML
    private Button uploadAddButton;
    @FXML
    private Button downloadButton;
    @FXML
    private Button downloadAddButton;
    @FXML
    private Button downloadListButton;
    @FXML
    private Button downloadRemoveListButton;
    @FXML
    private Button downloadClearListButton;
    @FXML
    private Button uploadListButton;
    @FXML
    private Button uploadRemoveListButton;
    @FXML
    private Button uploadClearListButton;
    @FXML
    private Button clearTaskTableButton;
    private MenuItem bookmarkAction;
    private MenuItem connectAction;
    private MenuItem disconnectAction;
    private SeparatorMenuItem separatorMenuItem;
    private TextField[] textFields;
    private javafx.scene.image.Image connectedImage = new javafx.scene.image.Image("\\pictures\\connected_icon.png");
    private Connect connect = new Connect();
    private ServerFiles load = new ServerFiles();
    private UserFiles find = new UserFiles();
    private UserFileTableUtils initUserTable;
    private UserFileTableUtils initUploadTable;
    private ServerFileTableUtils initServerTable;
    private ServerFileTableUtils initDownloadTable;
    private TaskTableUtils initTaskTable;
    private String serverPath;
    private Path userDestination;
   
	//----------------------------------------------------------------------------------------
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initBookmarkActions();
		initUserTreeView();
		initFileSearch();
		initTables();
		initActions();
		
		textFields = new TextField[] {hostAddressTF, usernameTF, passwordTF, portTF};
		
	}

	//----------------------------------------------------------------------------------------
	//Handles connection, login and call for server treeview initialization
	private void connectToFtp() {
		
		String hostAddress = hostAddressTF.getText();
		String username = usernameTF.getText();
		String password = passwordTF.getText().toString();
		int port;
		
		if(portTF.getText().isEmpty()) {
			port = connect.getDefaultPort();
		} else {
			port = Integer.parseInt(portTF.getText());
		}
		
		connect.setHostAddress(hostAddress);
		connect.setUsername(username);
		connect.setPassword(password);
		connect.setPort(port);
	
				
		setLogText("Attempting to connect to: " + hostAddress);
		connectAction.setDisable(true);
				
		if(connect.connectFtp()) {
					
			setLogText("Attempting to login to: " + hostAddress);
					
			if(connect.loginFtp()) {
						
				try {
							
					connectionImageView.setImage(connectedImage);
					disconnectAction.setDisable(false);
					utils.Utilities.clearTextFields(textFields);
					setLogText("Connected to host address: " + hostAddress);		
							
					initializeServerTreeView();
							
				} catch (IOException e) {
					e.printStackTrace();
				}			
			} else {
				setLogText("Invalid User Credentials");
				connectAction.setDisable(false);
			}
		} else {
			setLogText("Failed to connect to host address: " + hostAddress);
			connectAction.setDisable(false);
		}
	}
	
	//----------------------------------------------------------------------------------------
	private void disconnectToFtp() {
		
		if(connect.isFtpConnected()) {
			
			connect.disconnectFtp();
			connectionImageView.setImage(null);
			connectAction.setDisable(false);
			disconnectAction.setDisable(true);
			serverPath = null;
			
			setLogText("Disconnected from server");
			
			serverDirectoryTreeView.getRoot().getChildren().clear();
			serverFileTable.getItems().clear();
			taskTable.getItems().clear();
			serverDownloadTable.getItems().clear();
			
		}
	}
	
	//----------------------------------------------------------------------------------------
	//Initializes table models with their settings
	private void initTables() {
		
		initUserTable = new UserFileTableUtils(userFileTable);
		initUserTable.setFileNameCol(userFileNameCol);
		initUserTable.setFileSizeCol(userFileSizeCol);
		initUserTable.setFileTypeCol(userFileTypeCol);
		initUserTable.setFileLocCol(userFileLocCol);
		initUserTable.initFileTable();
		
		initUploadTable = new UserFileTableUtils(userUploadTable);
		initUploadTable.setFileNameCol(uploadFileNameCol);
		initUploadTable.setFileLocCol(uploadFileLocCol);
		initUploadTable.initUploadListTable();
		
		initServerTable = new ServerFileTableUtils(serverFileTable);
		initServerTable.setFileLocTCol(serverFileLocCol);
		initServerTable.setFileNameTCol(serverFileNameCol);
		initServerTable.setFileSizeTCol(serverFileSizeCol);
		initServerTable.setFileTypeTCol(serverFileTypeCol);
		initServerTable.initFileTable();
		
		initDownloadTable = new ServerFileTableUtils(serverDownloadTable);
		initDownloadTable.setFileNameTCol(downloadFileNameCol);
		initDownloadTable.setFileLocTCol(downloadFileLocCol);
		initDownloadTable.initDownloadTable();
		
		
		initTaskTable = new TaskTableUtils(taskTable);
		initTaskTable.setTaskActionsCol(taskActionsCol);
		initTaskTable.setTaskFileNameCol(taskFileNameCol);
		initTaskTable.setTaskFileSizeCol(taskFileSizeCol);
		initTaskTable.setTaskProgressCol(taskProgressCol);
		initTaskTable.initTable();
		
	}

	
	//----------------------------------------------------------------------------------------
	//Initializes menu item actions & button actions
	private void initActions() {
		
		connectAction.setOnAction(event -> connectToFtp());
		disconnectAction.setOnAction(event -> disconnectToFtp());
		bookmarkAction.setOnAction(event -> loadBookmarkView());
		uploadAddButton.setOnAction(event -> addToUploadList());
		downloadAddButton.setOnAction(event -> addToDownloadList());
		uploadClearListButton.setOnAction(event -> userUploadTable.getItems().clear());
		clearTaskTableButton.setOnAction(event -> taskTable.getItems().clear());
		
		uploadButton.setOnAction(event -> {		
			ObservableList<UserFileTableModel> files = userFileTable.getSelectionModel().getSelectedItems();
			upload(files);
		});
		
		uploadListButton.setOnAction(event -> {
			ObservableList<UserFileTableModel> files = userUploadTable.getItems();
			upload(files);	
		});
		
		uploadRemoveListButton.setOnAction(event -> {
			
			ObservableList<UserFileTableModel> remove = userUploadTable.getSelectionModel().getSelectedItems();
			userUploadTable.getItems().removeAll(remove);
			
		});
		
		downloadButton.setOnAction(event-> {
		
			ObservableList<ServerFileTableModel> files = serverFileTable.getSelectionModel().getSelectedItems();
			download(files);
			
		});

	}
	
	//----------------------------------------------------------------------------------------
	//Presets menu items before loading in user bookmarks
	private void initBookmarkActions() {
		
		bookmarkAction = new MenuItem("Bookmark Manager");
	    connectAction = new MenuItem("Connect");
	    disconnectAction = new MenuItem("Disconnect");
	    disconnectAction.setDisable(true);
	    separatorMenuItem = new SeparatorMenuItem();
		
		loadBookmarkMenuItems();
	}
	
	//----------------------------------------------------------------------------------------
	//Task handles loading of the user directory tree view
	private void initUserTreeView() {
		
		progIndicatorUserDir.progressProperty().unbind();
		progIndicatorUserDir.setVisible(true);
		progIndicatorUserDir.setStyle("-fx-progress-color: red;");
		
		Task<Void> init = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				
				try {
					
					setLogText("Loading User Directories...");
					
					UserDirectory treeView = new UserDirectory();
					treeView.initialize(userDirectoryTreeView);
											
				} catch (IOException e) {
					e.printStackTrace();
				} 
				
				return null;
			}
		}; new Thread(init).start();
		
		progIndicatorUserDir.progressProperty().bind(init.progressProperty());
		
		init.setOnSucceeded(event -> {
			
			progIndicatorUserDir.progressProperty().unbind();
			progIndicatorUserDir.setProgress(0);
			progIndicatorUserDir.setVisible(false);
			
			setLogText("User Directories Loaded");
			
		});
		
		init.setOnFailed(event -> {
			
			progIndicatorUserDir.progressProperty().unbind();
			progIndicatorUserDir.setProgress(0);
			progIndicatorUserDir.setVisible(false);
			
			setLogText("User Directories Failed To Load");
		});
	}
	
	//----------------------------------------------------------------------------------------
	//Searches for files inside a chosen directry from the tree views for display in the respected tables
	private void initFileSearch() {
		
		userDirectoryTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			
			if(newValue != null) {

				userFileTable.setItems(find.findFiles(String.valueOf(newValue.getValue().toAbsolutePath())));
				userDestination = newValue.getValue();
			}
			
		});
		
		serverDirectoryTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			
			if(newValue != null) {
				
				serverFileTable.setItems(load.findFiles(newValue.getValue()));
				serverPath = newValue.getValue();
			}
		});
	}
	
	//----------------------------------------------------------------------------------------
	//Task handles loading of the server directory tree view
	private void initializeServerTreeView() throws IOException {
		
		progIndicatorServerDir.progressProperty().unbind();
		progIndicatorServerDir.setVisible(true);
		progIndicatorServerDir.setStyle("-fx-progress-color: red;");
		
		Task<Void> init = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				javafx.application.Platform.runLater(new Runnable() {
						
				@Override
				public void run() {
							
					try {
							
						setLogText("Loading Server Directories...");
							
						ServerDirectory serverTreeView = new ServerDirectory();
						serverTreeView.initialize(serverDirectoryTreeView);
							
					} catch (IOException e) {
						e.printStackTrace();
					} 
							
					}
				});
				return null;
			}
		}; new Thread(init).start();
		
		progIndicatorServerDir.progressProperty().bind(init.progressProperty());
		
		init.setOnSucceeded(event -> {
			
			progIndicatorServerDir.progressProperty().unbind();
			progIndicatorServerDir.setProgress(0);
			progIndicatorServerDir.setVisible(false);
			
			setLogText("Server Directories Loaded");
			
		});
		
		init.setOnFailed(event -> {
			
			progIndicatorServerDir.progressProperty().unbind();
			progIndicatorServerDir.setProgress(0);
			progIndicatorServerDir.setVisible(false);
			
			setLogText("Server Directories Failed To Load");
		});
	}
	
	//----------------------------------------------------------------------------------------
	public void setLogText(String message) {this.logTA.appendText("<" + LocalTime.now() + ">: " + message + "\n");}
	
	//----------------------------------------------------------------------------------------
	//Loads FXML window bookmark manager
	private void loadBookmarkView() {
		
		try {
		
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/BookmarkView.fxml"));
			Pane root = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Shadow FTP Bookmarks");
			stage.setOnCloseRequest(event -> stage.close());
			stage.setResizable(false);
			stage.show();
		
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	//-----------------------------------------------------------------------------------------
	//Adds bookmarks to connect menu button
	public void loadBookmarkMenuItems() {
		
		connectButton.getItems().clear();
		connectButton.getItems().addAll(connectAction, disconnectAction, bookmarkAction, separatorMenuItem);
		
		ServerBookmark bookmark = new ServerBookmark();
		
		for(String mark: bookmark.getBookmarks()) {
			
			MenuItem menuItem = new MenuItem(mark);
			menuItem.setOnAction(event -> {
			setFieldsFromBookmark(mark);
			});
				
		connectButton.getItems().add(menuItem);
		}		
		
	}
	
	//-----------------------------------------------------------------------------------------
	//Sets textfields from selected bookmark name from connect menu button
	private void setFieldsFromBookmark(String name) {
		
		ServerBookmark bookmark = new ServerBookmark();
		bookmark.setBookmarkName(name);
		bookmark.loadBookmark();
		
		hostAddressTF.setText(bookmark.getHostAddress());
		usernameTF.setText(bookmark.getUsername());
		passwordTF.setText(bookmark.getPassword());
		portTF.setText(bookmark.getPort());
	}
	
	//-----------------------------------------------------------------------------------------
	private void download(ObservableList<ServerFileTableModel> files) {
		
		if(connect.isFtpConnected()) {
			
			if(userDestination != null) {

				if(!files.isEmpty()) {
				
					downloadFiles(files);
					
				} else {				
					setLogText("You must select 1 or more files to download...");
				}
			
			} else {
				setLogText("You must select a user desitination to download to...");
			} 
			
		} else {
			setLogText("No Connection Detected...");
		}
		
	}
	
	//-----------------------------------------------------------------------------------------
	private void addToDownloadList() {
		
		ObservableList<ServerFileTableModel> files = serverFileTable.getSelectionModel().getSelectedItems();
		
		if(!files.isEmpty()) {
			
			for(ServerFileTableModel file: files) {
				
				serverDownloadTable.getItems().add(new ServerFileTableModel(file.getFileName(), file.getFileSize(), file.getFileType(), file.getFilePath()));
				
			}
		}
		
	}
	
	//-----------------------------------------------------------------------------------------
	private void downloadFiles(ObservableList<ServerFileTableModel> files) {
		
		CountDownLatch latch = new CountDownLatch(files.size());
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		while(latch.getCount() > 0) {
			
			for (ServerFileTableModel file: files) {
				
				Task<Void> download = DownloadServerFiles.downloadFile(file.getFileName(), file.getFileSize(), userDestination.toString(), file.getFilePath());
						
				ProgressBar progressBar = new ProgressBar();
				progressBar.setPrefWidth(300);
				
				Button cancelButton = new Button("Cancel");
				cancelButton.setStyle("-fx-text-fill: white");
				cancelButton.setOnAction(event -> download.cancel());
				
				taskTable.getItems().add((new TaskTableModel(file.getFileName(), file.getFileSize(), cancelButton, progressBar)));
						
				download.setOnSucceeded(event -> {

					userFileTable.setItems(find.findFiles(userDestination.toAbsolutePath().toString()));
					setLogText("File: " + file.getFileName() + " downloaded successfully...");

				});
				
				download.setOnCancelled(event -> {
					
					setLogText("File: " + file.getFileName() + " download canceled...");
					
				});
				
				progressBar.progressProperty().bind(download.progressProperty());
				
				try {
					latch.countDown();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				executorService.execute(download);
				
			}	
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------
	//Calls single or muliple upload based on certain met conditions
	private void upload(ObservableList<UserFileTableModel> files) {
		
		if(connect.isFtpConnected()) {
		
			if(serverPath != null) {

				if(!files.isEmpty()) {
			
					uploadFiles(files);	
					
				} else {				
					setLogText("You must select 1 or more files to upload...");
				}
			
			} else {
				setLogText("You must select a server path to upload to...");
			} 
			
		} else {
			setLogText("No Connection Detected...");
		}
		
	}
	
	//-----------------------------------------------------------------------------------------
	//Adds files to upload list for multiple file upload from different directories
	private void addToUploadList() {
		
		ObservableList<UserFileTableModel> files = userFileTable.getSelectionModel().getSelectedItems();
		
		if(!files.isEmpty()) {
			
			for(UserFileTableModel file: files) {
			
				userUploadTable.getItems().add(new UserFileTableModel(file.getFileName(), file.getFileType(), file.getFileSize(), file.getFilePath()));
			
			}
		}	
	}
	
	//-----------------------------------------------------------------------------------------
	private void uploadFiles(ObservableList<UserFileTableModel> files){
		
		CountDownLatch latch = new CountDownLatch(files.size());
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		while(latch.getCount() > 0) {
			
			for (UserFileTableModel file: files) {
				
				Task<Void> upload = UploadUserFiles.uploadFile(file.getFilePath(), file.getFileName(), serverPath);
						
				ProgressBar progressBar = new ProgressBar();
				progressBar.setPrefWidth(300);
				
				Button cancelButton = new Button("Cancel");
				cancelButton.setStyle("-fx-text-fill: white");
				cancelButton.setOnAction(event -> upload.cancel());
				
				taskTable.getItems().add(new TaskTableModel(file.getFileName(), file.getFileSize(), cancelButton, progressBar));
						
				upload.setOnSucceeded(event -> {
					
					serverFileTable.setItems(load.findFiles(serverPath));
					setLogText("File: " + file.getFileName() + " uploaded successfully...");

				});
				
				upload.setOnCancelled(event -> {
					
					setLogText("File: " + file.getFileName() + " upload canceled...");
					
				});
				
				progressBar.progressProperty().bind(upload.progressProperty());
				
				try {
					latch.countDown();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				executorService.execute(upload);
				
			}	
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
