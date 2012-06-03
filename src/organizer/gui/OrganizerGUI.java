package organizer.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import java.awt.*;

public class OrganizerGUI extends JFrame {
	
	final static int EAST_SIZE = 200;
	final static int SOUTH_SIZE = 300;
	
	public OrganizerGUI(){
		
		setTitle("WallpaperOrganizer");
		setExtendedState(JFrame.MAXIMIZED_BOTH); // full-screen
	    setDefaultCloseOperation(EXIT_ON_CLOSE);

	    JPanel main_p = new JPanel(new BorderLayout());

	    //////////////////////////////////////////////////////////////////////
	    // Category Panel - EAST
	    //////////////////////////////////////////////////////////////////////
	    
	    JPanel east_p = new JPanel(new BorderLayout());
	    
		    //////////////////////////////////////////////////////////////////////
		    // Category Panel - EAST: NORTH
		    //////////////////////////////////////////////////////////////////////
	    
	    east_p.add(new JLabel("Categories:"), BorderLayout.NORTH);
	    
		    //////////////////////////////////////////////////////////////////////
		    // Category Panel - EAST: CENTER
		    //////////////////////////////////////////////////////////////////////
	    
	    //create the JList
	    String[] initialList = {"This","Is","Where","Categories","Go"};
	    JList<String> listContainer = new JList<String>(initialList);
	    listContainer.setFixedCellWidth(EAST_SIZE);
	    listContainer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    east_p.add(listContainer, BorderLayout.CENTER);
	    
		    //////////////////////////////////////////////////////////////////////
		    // Category Panel - EAST: SOUTH
		    //////////////////////////////////////////////////////////////////////
	    
	    JButton new_button = new JButton("New");
	    JButton delete_button = new JButton("Delete");
	    
	    JPanel categoryOption_p = new JPanel(new GridLayout(1,2));
	    categoryOption_p.add(new_button);
	    categoryOption_p.add(delete_button);
	    
	    east_p.add(categoryOption_p, BorderLayout.SOUTH);
	    
	    main_p.add(east_p, BorderLayout.EAST);
	    
	    //////////////////////////////////////////////////////////////////////
	    // Button Panel - NORTH
	    //////////////////////////////////////////////////////////////////////
	    
	    //create buttons
	    JButton save_button = new JButton("Save Cat List");
	    JButton open_button = new JButton("Open Folder");
	    JButton category_button = new JButton("Open Cat List");
	    JButton apply_button = new JButton("Apply Changes");
	    JButton quit_button = new JButton("Quit");
	    JButton setFolder_button = new JButton("Set Folder");
	    
	    JPanel button_p = new JPanel(new GridLayout(1,6));
	    button_p.add(open_button);
	    button_p.add(setFolder_button);
	    button_p.add(category_button);
	    button_p.add(save_button);
	    button_p.add(apply_button);
	    button_p.add(quit_button);
	    
	    button_p.setSize(JFrame.MAXIMIZED_HORIZ, SOUTH_SIZE);
	    
	    main_p.add(button_p, BorderLayout.NORTH);
	    

	    //////////////////////////////////////////////////////////////////////
	    // Image Display - CENTER
	    //////////////////////////////////////////////////////////////////////
	    
	    JPanel center_p = new JPanel(new BorderLayout());
	    
		    //////////////////////////////////////////////////////////////////////
		    // Image Display - CENTER: TOP
		    //////////////////////////////////////////////////////////////////////	    

	    JPanel navigation_p = new JPanel(new GridLayout(1,4));
	    //navigation buttons
	    JButton first = new JButton("<<");
	    JButton left = new JButton("<");
	    JButton right = new JButton(">");
	    JButton last = new JButton(">>");
	    
	    navigation_p.add(first);
	    navigation_p.add(left);
	    navigation_p.add(right);
	    navigation_p.add(last);
	    
	    center_p.add(navigation_p, BorderLayout.NORTH);
	    
		    //////////////////////////////////////////////////////////////////////
		    // Image Display - CENTER: CENTER
		    //////////////////////////////////////////////////////////////////////
	    
	    JLabel image = new JLabel();
	    //image.setIcon(new ImageIcon("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0005-1290393897735.jpg"));
	    //image.setIcon(new ImageIcon("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0006-1290394504600.png"));
	    
	    //ImageIcon img = new ImageIcon(getResizedImage("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0005-1290393897735.jpg"));
	    ImageIcon img = new ImageIcon(getResizedImage("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0006-1290394504600.png"));
	    image.setIcon(img);
	    center_p.add(image, BorderLayout.CENTER);
	    
	    main_p.add(center_p, BorderLayout.CENTER);
	    
	    add(main_p);
	    setVisible(true);
	    
	}

	public static Image getResizedImage(String location){
		ImageIcon icon = new ImageIcon(location);
		Image img = icon.getImage();
		Image scaled_img = img.getScaledInstance(1066,
				468, Image.SCALE_SMOOTH);
		
		return scaled_img;
	}
	
	public static void main(String[] args){
		OrganizerGUI orgGUI = new OrganizerGUI();
	}
	
}
