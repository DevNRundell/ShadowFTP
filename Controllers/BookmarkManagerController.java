package controller;

/*
 * 
 * Class handles operations for the Bookmark Manager
 * 
 */

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import bookmark.ServerBookmark;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import utils.Utilities;

public class BookmarkController implements Initializable  {

    @FXML
    private TextField hostAddressTF;
    @FXML
    private TextField portTF;
    @FXML
    private TextField userTF;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField bookmarkNameTF;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ListView<String> savedBookmarksList;
    private ServerBookmark bookmark = new ServerBookmark();
    private TextField[] textfields;

    //----------------------------------------------------------------------------------------
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		addButton.setOnAction(event -> addBookmark());
		editButton.setOnAction(event -> editBookmark());
		deleteButton.setOnAction(event -> deleteBookmark());
		closeButton.setOnAction(event -> {
			Stage stage = (Stage) closeButton.getScene().getWindow();
			stage.close();
		});
		savedBookmarksList.setItems(bookmark.getBookmarks());
		
		textfields = new TextField[]{hostAddressTF, portTF, bookmarkNameTF, userTF, passwordField};
		
	}
	
	//----------------------------------------------------------------------------------------
	private void addBookmark() {

		String host = hostAddressTF.getText();
	    String port = portTF.getText();
	    String name = bookmarkNameTF.getText();
	    String user = userTF.getText();
	    String pass = passwordField.getText();

	    bookmark.setHostAddress(host);
	    bookmark.setPort(port);
	    bookmark.setBookmarkName(name);
	    bookmark.setUsername(user);
	    bookmark.setPassword(pass);

	    if(name.isEmpty()) {
	        	
	    	Alert noName= new Alert(AlertType.ERROR, "Please enter a bookmark name...");
	    	noName.show();

	    } else if(bookmark.addBookmark()) {
	        	
	    	savedBookmarksList.setItems(bookmark.getBookmarks());
	    	refreshBookmarkMenuItems();
	    	
	    	Utilities.clearTextFields(textfields);
	    } else {

	        bookmarkNameTF.setText("");
	    }
	}

	//----------------------------------------------------------------------------------------
	private void deleteBookmark() {
		
		String deleteFile = savedBookmarksList.getSelectionModel().getSelectedItem();
		
		if(deleteFile != null) {
			
			Alert delete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete bookmark: " + deleteFile);
			delete.setTitle("Delete Bookmark");
			delete.setHeaderText("Delete?");
			Optional<ButtonType> result = delete.showAndWait();
			
			if (result.isPresent()) {
				
				if (result.get() == ButtonType.OK) {

                	bookmark.setBookmarkName(deleteFile);
                	
                	if (bookmark.deleteBookmark()) {
                		
                        savedBookmarksList.setItems(bookmark.getBookmarks());  
                        refreshBookmarkMenuItems();
                        
                        Utilities.clearTextFields(textfields);
                	}
                }
			}
		}
	}

	//----------------------------------------------------------------------------------------
	private void editBookmark() {

		String editFile = savedBookmarksList.getSelectionModel().getSelectedItem();

		if(editFile != null) {

        	bookmark.setBookmarkName(editFile);
        	bookmark.loadBookmark();        	
        	hostAddressTF.setText(bookmark.getHostAddress());
        	portTF.setText(bookmark.getPort());
        	bookmarkNameTF.setText(editFile);
        	userTF.setText(bookmark.getUsername());
        	passwordField.setText(bookmark.getPassword());

        	saveChangesButton.setVisible(true);
            cancelButton.setVisible(true);
            bookmarkNameTF.setDisable(true);
            editButton.setDisable(true);
            addButton.setDisable(true);
            deleteButton.setDisable(true);

            saveChangesButton.setOnAction(event -> {

            	String host = hostAddressTF.getText();
                String port = portTF.getText();
                String name = bookmarkNameTF.getText();
                String user = userTF.getText();
                String pass = passwordField.getText();
                
                bookmark.setHostAddress(host);
                bookmark.setPort(port);
                bookmark.setBookmarkName(name);
                bookmark.setUsername(user);
                bookmark.setPassword(pass);

	            if (bookmark.saveChanges()) {
	            	
	            	saveChangesButton.setVisible(false);
	            	cancelButton.setVisible(false);
	            	bookmarkNameTF.setDisable(false);
	            	
	            	editButton.setDisable(false);
	                addButton.setDisable(false);
	                deleteButton.setDisable(false);
	                
	                refreshBookmarkMenuItems();
	                Utilities.clearTextFields(textfields);
	            }
	            
            });
            
            cancelButton.setOnAction(event -> {
            	
            	saveChangesButton.setVisible(false);
            	cancelButton.setVisible(false);
            	bookmarkNameTF.setDisable(false);
            	
            	editButton.setDisable(false);
                addButton.setDisable(false);
                deleteButton.setDisable(false);
	        });          
		}	
	}
	
	//----------------------------------------------------------------------------------------
	//Updates Main Controller Menu Button Bookmarks if changes are made within the bookmark manager
	private void refreshBookmarkMenuItems() {
		
		if(Main.mainViewController != null) {
			Main.mainViewController.loadBookmarkMenuItems();
        }
	}
}
