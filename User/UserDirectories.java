package user;

/*
 * 
 * Class handles loading root directories
 * For each root, all the sub directories are loaded.
 * 
 */


import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

public class UserDirectory {
	
	private final String SUBROOT_IMAGE ="/pictures/directory_icon.png";
	private TreeView<Path> treeView;
	
	//----------------------------------------------------------------------------------------
	public void initialize(TreeView<Path> treeView) throws IOException {
		this.treeView = treeView;
		loadRootDirectories();
	}
	
	//----------------------------------------------------------------------------------------
	private void loadRootDirectories() throws IOException {
		
		TreeItem<Path> parentRoot = new TreeItem<Path>();
		treeView.setRoot(parentRoot);
		treeView.setShowRoot(false);
				
		Iterable<Path> tempRootDirList = FileSystems.getDefault().getRootDirectories();
		
		if(tempRootDirList != null) {			
			for(Path directory: tempRootDirList) {		
				loadDirectories(directory.toAbsolutePath(), parentRoot);		
			}
		}
	}

	//----------------------------------------------------------------------------------------
	private void loadDirectories(Path path, TreeItem<Path> subRootParent) {
		
		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				
				File[] files = new File(path.toAbsolutePath().toString()).listFiles();
				
				TreeItem<Path> subRoot = new TreeItem<Path>(path.toAbsolutePath());
				
				for (File file : files) {		
					
					if(Files.isDirectory(file.toPath())) {	
						if(file.toPath().getNameCount() == 1) {
							subRoot.setGraphic(new ImageView(SUBROOT_IMAGE));
						}							
						loadDirectories(file.toPath(), subRoot);
					}
				}
		
				return subRootParent.getChildren().add(subRoot);
			}
			
		}; task.run();
	}
}
