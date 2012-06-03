package organizer.gui;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The GUI for the organizer.
 * 
 * @author YottaGuy
 * @author Josaphat Valdivia - josaphat.valdivia@gmail.com
 *
 */
@SuppressWarnings("serial")
public class OrganizerGUI extends JFrame implements ActionListener {
	
	final static int EAST_SIZE = 200;
	final static int SOUTH_SIZE = 300;
	
	OrganizerModel model;
	JLabel image;
	
	/**
	 * Constructs the GUI for the Wallpaper Organizer.
	 */
	public OrganizerGUI(OrganizerModel model){
		this.model = model;
		setTitle("Wallpaper Organizer");
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

	    // create the JList
	    // TODO: Make this prettier
	    DefaultListModel<String> listModel = new DefaultListModel<String>();
	    if(model.getCategoryList().isEmpty()){
	    	String[] initialList = {"This", "Is", "Where", "Categories", "Go"};
	    	for(String s : initialList){
	    		listModel.addElement(s);
	    	}
	    } else {
	    	for(String s : model.getCategoryList()){
	    		listModel.addElement(s);
	    	}
	    }
	    JList<String> listContainer = new JList<String>(listModel);
	    listContainer.setFixedCellWidth(EAST_SIZE);
	    listContainer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    east_p.add(listContainer, BorderLayout.CENTER);

		    //////////////////////////////////////////////////////////////////////
		    // Category Panel - EAST: SOUTH
		    //////////////////////////////////////////////////////////////////////

	    JButton new_button = new JButton("New");
	    new_button.addActionListener(this);
	    JButton delete_button = new JButton("Delete");
	    delete_button.addActionListener(this);

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
	    save_button.addActionListener(this);
	    JButton open_button = new JButton("Open Folder");
	    open_button.addActionListener(this);
	    JButton category_button = new JButton("Open Cat List");
	    category_button.addActionListener(this);
	    JButton apply_button = new JButton("Apply Changes");
	    apply_button.addActionListener(this);
	    JButton quit_button = new JButton("Quit");
	    quit_button.addActionListener(this);
	    JButton setFolder_button = new JButton("Set Folder");
	    setFolder_button.addActionListener(this);
	    
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
	    first.addActionListener(this);
	    JButton left = new JButton("<");
	    left.addActionListener(this);
	    JButton right = new JButton(">");
	    right.addActionListener(this);
	    JButton last = new JButton(">>");
	    last.addActionListener(this);
	    
	    navigation_p.add(first);
	    navigation_p.add(left);
	    navigation_p.add(right);
	    navigation_p.add(last);
	    
	    center_p.add(navigation_p, BorderLayout.NORTH);
	    
		    //////////////////////////////////////////////////////////////////////
		    // Image Display - CENTER: CENTER
		    //////////////////////////////////////////////////////////////////////
	    
	    //image.setIcon(new ImageIcon("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0005-1290393897735.jpg"));
	    //image.setIcon(new ImageIcon("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0006-1290394504600.png"));	    
	    //ImageIcon img = new ImageIcon(getResizedImage("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0005-1290393897735.jpg"));
	    this.image = new JLabel(new ImageIcon(getResizedImage("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0006-1290394504600.png")));
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonText = ((JButton) e.getSource()).getText();

		if (buttonText.equals("<")){
			System.out.println("<");
		} else if (buttonText.equals("<<")){
			System.out.println("<<");
		} else if (buttonText.equals(">>")){
			System.out.println(">>");
		} else if (buttonText.equals(">")){
			System.out.println(">");
		} else if (buttonText.equals("Open Folder")){
			System.out.println("Open Folder");
		} else if (buttonText.equals("Set Folder")){
			System.out.println("Set Folder");
		} else if (buttonText.equals("Open Cat List")){
			System.out.println("Open Cat");
		} else if (buttonText.equals("Save Cat List")){
			System.out.println("Save Cat");
		} else if (buttonText.equals("Apply Changes")){
			System.out.println("Apply Changes");
		} else if (buttonText.equals("New")){
			System.out.println("New");
		} else if (buttonText.equals("Delete")){
			System.out.println("Delete");
		} else if (buttonText.equals("Quit")){
			System.out.println("Quit");
		}
	}
	
	public static void main(String[] args) {
		/* Open the user's pictures folder the first image in the directory */
		OrganizerModel organizer = new OrganizerModel();
		OrganizerGUI orgGUI = new OrganizerGUI(organizer);
	}
}