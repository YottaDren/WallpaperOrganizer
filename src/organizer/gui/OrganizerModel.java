package organizer.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * A simple image organizer.
 * 
 * @author Ricardo Quintanilla - rpq5136@rit.edu
 * @author Josaphat Valdivia - josaphat.valdivia@gmail.com
 */
public class OrganizerModel {

	Vector<File> fileList = new Vector<File>();
	Vector<String> categoryList = new Vector<String>(); 
	Vector<Integer> startingNumbers = new Vector<Integer>();
	File directory;
	File directoryOut;
	String[] categoryPairs;
	int place = 0;
	
	/**
	 * Build the model and initialize it to the user's pictures directory
	 */
	public OrganizerModel() {
		System.out.println("Inside OrganizerModel()");
		directory = new File(System.getProperty("user.home")
				+ System.getProperty("file.separator")
				+ "Pictures"
			);
		
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

			try{
				FileInputStream fstream = new FileInputStream(cList);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				//Read line by line
				while((strLine = br.readLine()) != null){
					categoryList.add(strLine);
					startingNumbers.add(new Integer(0));
				}
				//close the output stream
				in.close();

			} catch(Exception e){
				System.err.println("Error: there was a problem reading the file");
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
	public void getFileList(String dir){
		File newDirectory = new File(dir);
		if(newDirectory.isDirectory()){
			File[] files_directories = newDirectory.listFiles();
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
}
