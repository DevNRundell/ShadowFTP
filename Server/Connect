package server;

/*
 * 
 * Class creates a new instance of FTPClient which is the API for FTP connection
 * Handles login, connection, disconnect, and returns the connection instance.
 * 
 */

import java.io.IOException;


import org.apache.commons.net.ftp.FTPClient;

public class Connect {
	
	private static FTPClient ftpClient;
	private String hostAddress;
	private String username;
	private String password;
	private int port;
	
	//----------------------------------------------------------------------------------------
	public Connect(String hostAddress, String username, String password, int port) {
		
		this.hostAddress = hostAddress;
		this.username = username;
		this.password = password;
		this.port = port;
		ftpClient = new FTPClient();
		
	}
	
	//----------------------------------------------------------------------------------------
	public boolean connectFtp() {
		
		try {
			
			ftpClient.connect(hostAddress, port);
			
			 if(ftpClient.isConnected()) {
	                return true;
	         }
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	//----------------------------------------------------------------------------------------
	public boolean loginFtp() {
		
		try {
			
			if(ftpClient.login(username, password)) {
				return true;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//-----------------------------------------------------------------------------------------
    public boolean disconnectFtp() {

        try {
        	
            ftpClient.disconnect();
            return true;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //-----------------------------------------------------------------------------------------
    public static FTPClient getConnection() {  	
    	return ftpClient;
    }
    
    //-----------------------------------------------------------------------------------------
    public boolean isFtpConnected() {
    	return ftpClient.isConnected();
    }
    
    public int getDefaultPort() {
    	return ftpClient.getDefaultPort();
    }
    
	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
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

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
