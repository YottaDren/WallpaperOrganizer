package organizer.gui;


import static org.junit.Assert.*;

import java.io.File;
import java.util.Vector;

import org.junit.Test;


public class organizerTest {

	static Vector<File> fileList = new Vector<File>();
	static File directory;
	
	organizer test = new organizer();
	
	@Test
	public void getCategoryList() {
		test.getCategoryList("C:\\Users\\Dren Shcatten\\Pictures\\testf.txt");
		System.out.println("Category List Test:");
		test.printCategoryList();
		System.out.println();
	}
	
	@Test
	public void getFileListTest() {
		test.getFileList("C:\\Users\\Dren Shcatten\\Pictures");
		System.out.println("File List Test:");
		test.printFileList();
		System.out.println();
	}
	
	@Test
	public void writeCategoryListTest() {
		//read list
		test.getCategoryList("C:\\Users\\Dren Shcatten\\Pictures\\testf.txt");
		//add new line
		test.categoryList.add("aw snap its a new line");
		//overwrites a file
		test.writeCategoryList("C:\\Users\\Dren Shcatten\\Pictures\\testf_2.txt");
		//writes a new file
		test.writeCategoryList("C:\\Users\\Dren Shcatten\\Pictures\\testf_3.txt");
		
	}

}