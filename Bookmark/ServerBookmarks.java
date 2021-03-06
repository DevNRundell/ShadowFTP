package bookmark;

/*
 * 
 * Interacts with the users home drive
 * Creates a new folder named ShadowFTP_Bookmarks if necessary
 * Adds, Edits, Deletes, Obtains bookmarks
 * 
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.Properties;

public class ServerBookmark {

    private String bookmarkName;
    private String hostAddress;
    private String port;
    private String username;
    private String password;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;
    private final String homeDir = System.getProperty("user.home");

    //------------------------------------------------------------------------------------------------------------------
    public ServerBookmark(){}

    //------------------------------------------------------------------------------------------------------------------
    public ServerBookmark(String bookmarkName, String hostAddress, String port, String username, String password) {

        this.bookmarkName = bookmarkName;
        this.hostAddress = hostAddress;
        this.port = port;
        this.username = username;
        this.password = password;

    }

    //------------------------------------------------------------------------------------------------------------------
    public boolean addBookmark() {

        Properties properties = new Properties();

        File file = new File(homeDir + "\\ShadowFTP_Bookmarks\\" + bookmarkName);

        if(makeDirectory()) {

            try {

                if (!file.exists()) {

                    outputStream = new FileOutputStream(file);

                    properties.setProperty("host_address", hostAddress);
                    properties.setProperty("port", port);
                    properties.setProperty("username", username);
                    properties.setProperty("password", password);

                    properties.store(outputStream, null);

                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------
    public boolean loadBookmark() {

        Properties properties = new Properties();

        File file = new File(homeDir + "\\ShadowFTP_Bookmarks\\" + bookmarkName);

        try {

            if(file.exists()) {

                inputStream = new FileInputStream(file);

                properties.load(inputStream);

                setHostAddress(properties.getProperty("host_address"));
                setPort(properties.getProperty("port"));
                setUsername(properties.getProperty("username"));
                setPassword(properties.getProperty("password"));

                return true;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Gets all the bookmark files within the ShadowFTP_Bookmark folder
    public ObservableList<String> getBookmarks() {

        ObservableList<String> fileList = FXCollections.observableArrayList();

        File[] files = new File(homeDir + "\\ShadowFTP_Bookmarks").listFiles();

        if(files != null) {

            for (File file : files) {
                fileList.add(file.getName());
            }
        }

        return fileList;
    }

    //------------------------------------------------------------------------------------------------------------------
    public boolean deleteBookmark() {
        File file = new File(homeDir + "\\ShadowFTP_Bookmarks\\" + bookmarkName);
        return file.exists() && file.delete();
    }

    //------------------------------------------------------------------------------------------------------------------
    //Overwrites data within a file given a certain file name
    public boolean saveChanges() {

        Properties properties = new Properties();

        File file = new File(homeDir + "\\ShadowFTP_Bookmarks\\" + bookmarkName);

        try {

            if(file.exists()) {

                inputStream = new FileInputStream(file);
                properties.load(inputStream);
                inputStream.close();

                properties.setProperty("host_address", hostAddress);
                properties.setProperty("port", port);
                properties.setProperty("username", username);
                properties.setProperty("password", password);

                outputStream = new FileOutputStream(file);
                properties.store(outputStream, null);
                outputStream.close();

                return true;

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if(outputStream != null && inputStream != null) {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Makes new ShadowFTP_Bookmark directory if it doesnt exist
    private boolean makeDirectory() {

        File bookmarkFolder = new File(homeDir + "\\ShadowFTP_Bookmarks");

        if(!bookmarkFolder.exists()) {
            return bookmarkFolder.mkdir();
        }
        return true;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setBookmarkName(String bookmarkName) {
    	this.bookmarkName = bookmarkName;
    }
    
    public String getBookmarkName() {
    	return bookmarkName;
    }
    
    public String getHostAddress() {
    	return hostAddress;
    }
    
    public void setHostAddress(String hostAddress) {
    	this.hostAddress = hostAddress;
    }
    
    public String getPort() {
    	return port;
    }
    
    public void setPort(String port) {
    	this.port = port;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
}







