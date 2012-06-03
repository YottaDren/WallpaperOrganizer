package organizer.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

/**
 * A simple image organizer.
 * 
 * @author Ricardo Quintanilla - rpq5136@rit.edu
 * @author Josaphat Valdivia - josaphat.valdivia@gmail.com
 */
public class OrganizerModel {

	public int FIRST;
	public int LAST;
	// List of files in the current directory
	private ArrayList<File> fileList = new ArrayList<File>();
	// List of categories (also folders)
	private ArrayList<String> categoryList = new ArrayList<String>();
	// Everything goes into a folder and gets the name in the folder plus a space
	// plus a number
	private ArrayList<Integer> startingNumbers = new ArrayList<Integer>();
	private File directory;
	// Where all the files will be renamed and copied to.
	private File directoryOut;
	// What category is chosen for that file
	private String[] categoryPairs;
	// which file in the file list
	int place = 0;
	
	// Keeping track of the image we're working with.
	int index;
	
	/**
	 * Build the model and initialize it to the user's pictures directory
	 */
	public OrganizerModel() {
		this.FIRST = 0;
		directory = new File(System.getProperty("user.home")
				+ System.getProperty("file.separator")
				+ "Pictures"
			);
		// Populate the file list
		updateFileList();
		// attempt to load up the default category file
		getCategoryList("cats.txt");
	}
	
	/**
	 * Updates the file list to the (relevant) contents of the directory
	 */
	private void updateFileList(){
		this.fileList = new ArrayList<File>(Arrays.asList(this.directory.listFiles(new FileFilter(){

			@Override
			public boolean accept(File f) {
				String s = f.getName();
				if(f.isFile()){
					s = s.toLowerCase();
					if(s.endsWith(".tiff") ||
							s.endsWith(".tif") ||
							s.endsWith(".jpeg") ||
							s.endsWith(".jpg") ||
							s.endsWith(".gif") ||
							s.endsWith(".png")){
						return true;
					}
				}
				return false;
			}
			
		})));
		index = 0;
		LAST = this.fileList.size() - 1;
	}
	
	/**
	 * Retrieves the list of categories in the organizer.
	 * @return the list of categories currently loaded
	 */
	public ArrayList<String> getCategoryList(){
		return this.categoryList;
	}

	/**
	 * Retrieves a list of categories. The cList can be either be the address
	 * of a text file or a directory. If a text file, categories will be read
	 * line by line. If a directory, the names of all folders inside will be
	 * accepted as the category list.
	 *  
	 * @param cList - The address of a text file or directory containing categories
	 */
	public void getCategoryList(String cList){
		File newCategoryList = new File(cList);
		if( newCategoryList.getName().endsWith(".txt") ){
			categoryList.clear();

			try(BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(cList))))){
				String strLine;
				//Read line by line
				while((strLine = br.readLine()) != null){
					categoryList.add(strLine);
					startingNumbers.add(new Integer(0));
				}
			} catch (FileNotFoundException e) {
				System.err.println("File not found");
			} catch (IOException e) {
				System.err.println("An exception occured");
			}
		} else if( newCategoryList.isDirectory() ){
			File[] files_directories = newCategoryList.listFiles();
			categoryList.clear();
			//filter by file name
			for(int i = 0; i < files_directories.length; i++){
				if( files_directories[i].isDirectory() ){
					categoryList.add(files_directories[i].getName());
					startingNumbers.add(new Integer(0));
				}
			}
		} else {
			System.out.print("not a text file");
		}
	}

	/**
	 * Prints the list of categories to standard out.
	 */
	public void printCategoryList(){
		int size = categoryList.size();
		for( int i = 0 ; i < size ; i++ ){
			System.out.println(i+" "+categoryList.get(i));
		}
	}

	/**
	 * Retrieves a list of image files from the given directory.
	 * 
	 * @param dir - the directory with images.
	 */
	public void getFileList(File dir){
		if(dir.isDirectory()){
			File[] files_directories = dir.listFiles();
			fileList.clear();
			//filter by file name
			for(int i = 0; i < files_directories.length; i++){
				if( files_directories[i].getName().endsWith(".jpg") ||
						files_directories[i].getName().endsWith(".jpeg") ||
						files_directories[i].getName().endsWith(".png") ||
						files_directories[i].getName().endsWith(".gif") ){
					fileList.add(files_directories[i]);
				}
			}
			categoryPairs = new String[fileList.size()];
		} else {
			System.out.print("not a directory");
		}
	}

	/**
	 * Prints the list of files to standard output.
	 */
	public void printFileList(){
		int size = fileList.size();
		for( int i = 0 ; i < size ; i++ ){
			System.out.println(i+" "+fileList.get(i).getName());
		}
	}

	/**
	 * Writes the category list to a file. Overwrites if the file already
	 * exists, creates a new file otherwise.
	 * 
	 * @param outputFile - the address of the file
	 */
	public void writeCategoryList(String outputFile){
		//http://www.roseindia.net/java/beginners/java-write-to-file.shtml
		try{
			int size = categoryList.size();

			// Create file 
			FileWriter fstream = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fstream);

			for( int i = 0 ; i < size ; i++ ){
				out.write(categoryList.get(i)+"\n");
				out.flush();
			}
			//Close the output stream
			out.close();
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Retrieves the number to start a categories numbering.
	 * 
	 * @param dir - The category directory 
	 * @return the number to start a categories numbering
	 */
	public int retrieveNumber(String dir){
		File innerDirectory = new File(directoryOut.getAbsolutePath()+"\\"+dir);
		int starting = 0;
		if(innerDirectory.isDirectory()){
			File[] files_directories = innerDirectory.listFiles();
			//filter by file name
			int temp = 0;
			for(int i = 0; i < files_directories.length; i++){
				if( files_directories[i].getName().endsWith(".jpg") ||
						files_directories[i].getName().endsWith(".png") ||
						files_directories[i].getName().endsWith(".gif") ){
					temp = (new Integer(files_directories[i].getName().substring(files_directories[i].getName().length()-8,
							files_directories[i].getName().length()-4))).intValue();
					if( starting < temp )
						starting = temp;
				} else if(files_directories[i].getName().endsWith(".jpeg")){
					temp = (new Integer(files_directories[i].getName().substring(files_directories[i].getName().length()-9,
							files_directories[i].getName().length()-5))).intValue();
					if( starting < temp )
						starting = temp;
				}
			}
			categoryPairs = new String[fileList.size()];
			return starting;
		} else {
			System.out.print("not a directory");
			return -1;
		}
	}

	/**
	 * Copies all the files with a selected category to their category folder. 
	 */
	public void applyChanges(){
		for( int i = 0 ; i < fileList.size() ; i++ ){
			if( categoryPairs[i] != null ){
				int temp = startingNumbers.get(i).intValue();
				String formattedNum = String.format("%04", temp);
				if( fileList.get(i).renameTo(new File(directoryOut,categoryPairs[i]
						+" "+formattedNum))){
					startingNumbers.set(i, new Integer(temp+1));
				}
			}
		}
	}

	/**
	 * Returns to the beginning of the file list and moves to the first place
	 * or returns null if empty.
	 * 
	 * @return File - the first file of the file list or null
	 */
	public File first(){
		place = 0;
		if(fileList.isEmpty()){
			return null;
		} else {
			return fileList.get(place);
		}
	}

	/**
	 * Returns the previous File in the file list and decrements the place.
	 * Returns null if the file list if empty.
	 * 
	 * @return File - the previous File in the file list or null
	 */
	public File left(){
		if((place-1) >= 0)
			place--;
		if(fileList.isEmpty()){
			return null;
		} else {
			return fileList.get(place);
		}
	}

	/**
	 * Returns the next File in the file list and decrements the place. Returns
	 * null if the list is empty.
	 * 
	 * @return File - the next file in the file list or null
	 */
	public File right(){
		if((place+1) < fileList.size())
			place++;
		if(fileList.isEmpty()){
			return null;
		} else {
			return fileList.get(place);
		}
	}

	/**
	 * Returns the last File in the file list and moves to the last place.
	 * 
	 * @return File - last file in the file list or null
	 */
	public File last(){
		place = fileList.size() - 1;
		if(fileList.isEmpty()){
			return null;
		} else {
			return fileList.get(place);
		}
	}

	public File getCurrentDir() {
		return this.directory;
	}

	public void setCurrentDir(File dir) {
		if(dir.isDirectory()){
			this.directory = dir;
			updateFileList();
		}
	}
	
	public ImageIcon getFirstImageIcon(){
		this.index = 0;
		return getImageIconAt(this.index);
	}
	
	public ImageIcon getPreviousImageIcon(){
		if(index < 1){
			index = 0;
		} else {
			--index;
		}
		return getImageIconAt(index);
	}

	public ImageIcon getNextImageIcon() {
		if(index > fileList.size() - 2){
			index = fileList.size() - 1;
		} else {
			++index;
		}
		return getImageIconAt(index);
	}
	
	public ImageIcon getLastImageIcon() {
		index = this.fileList.size()-1;
		return getImageIconAt(index);
	}
	
	public ImageIcon getImageIconAt(int i){
		if(i >= 0 && i < fileList.size()){
			return new ImageIcon(this.fileList.get(i).getAbsolutePath());
		} else {
			return null;
		}
	}
}
