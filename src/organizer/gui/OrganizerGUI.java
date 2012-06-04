package organizer.gui;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The GUI for the organizer.
 * 
 * @author Ricardo Quintanilla - rpq5136@gmail.com
 * @author Josaphat Valdivia - josaphat.valdivia@gmail.com
 *
 */
@SuppressWarnings("serial")
public class OrganizerGUI extends JFrame implements ActionListener {
	
	final static int EAST_SIZE = 200;
	final static int SOUTH_SIZE = 300;
	
	private final static int FIRST = Integer.MIN_VALUE;
	private final static int PREVIOUS = -1;
	private final static int NEXT = 1;
	private final static int LAST = Integer.MAX_VALUE;
	
	OrganizerModel model;
	JLabel image;
	JLabel curDirLabel;
	JFileChooser fc;
	JList<String> listContainer;
	JButton delete_button,
	first_button,previous_button,
	next_button,last_button;
	DefaultListModel<String> listModel;
	
	/**
	 * Constructs the GUI for the Wallpaper Organizer.
	 */
	public OrganizerGUI(OrganizerModel m){
		this.model = m;
		this.fc = new JFileChooser(this.model.getCurrentDir());
		this.fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		curDirLabel = new JLabel(model.getCurrentDir().getName());
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
	    listModel = new DefaultListModel<String>();
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
	    listContainer = new JList<String>(listModel);
	    listContainer.setFixedCellWidth(EAST_SIZE);
	    listContainer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    listContainer.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				/* If there isn't anything selected in the list, don't let the user
				 * click the delete button.
				 */
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				if(lsm.isSelectionEmpty()){
					delete_button.setEnabled(false);
					model.setCurrentPair(null);
				} else {
					delete_button.setEnabled(true);
					model.setCurrentPair(listContainer.getSelectedValue());
				}
			}
	    });

	    east_p.add(listContainer, BorderLayout.CENTER);

		    //////////////////////////////////////////////////////////////////////
		    // Category Panel - EAST: SOUTH
		    //////////////////////////////////////////////////////////////////////

	    JButton new_button = new JButton("New");
	    new_button.addActionListener(this);
	    delete_button = new JButton("Delete");
	    delete_button.setEnabled(false);
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

	    JPanel navigation_p = new JPanel(new GridLayout(2,4));
	    //navigation buttons
	    first_button = new JButton("<<");
	    first_button.addActionListener(this);
	    first_button.setEnabled(false);
	    previous_button = new JButton("<");
	    previous_button.addActionListener(this);
	    previous_button.setEnabled(false);
	    next_button = new JButton(">");
	    next_button.addActionListener(this);
	    last_button = new JButton(">>");
	    last_button.addActionListener(this);
	    
	    navigation_p.add(this.curDirLabel); // Show the working directory
	    navigation_p.add(new JLabel("")); // add some blanks
	    navigation_p.add(new JLabel(""));
	    navigation_p.add(new JLabel(""));
	    navigation_p.add(first_button);
	    navigation_p.add(previous_button);
	    navigation_p.add(next_button);
	    navigation_p.add(last_button);
	    
	    center_p.add(navigation_p, BorderLayout.NORTH);
	    
		    //////////////////////////////////////////////////////////////////////
		    // Image Display - CENTER: CENTER
		    //////////////////////////////////////////////////////////////////////
	    
	    //image.setIcon(new ImageIcon("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0005-1290393897735.jpg"));
	    //image.setIcon(new ImageIcon("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0006-1290394504600.png"));	    
	    //ImageIcon img = new ImageIcon(getResizedImage("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0005-1290393897735.jpg"));
	    //this.image = new JLabel(new ImageIcon(getResizedImage("C:\\Users\\Dren Shcatten\\Pictures\\Backgrounds\\Other\\Zelda\\0006-1290394504600.png")));
	    this.image = new JLabel();
	    this.image.setIcon(scale(model.getFirstImageIcon()));
	    this.image.setAlignmentX(CENTER_ALIGNMENT);
	    //this.image = new JLabel(scale(new ImageIcon("C:\\Users\\panchovaldiv\\Pictures\\Scan.png")));
	    center_p.add(image, BorderLayout.CENTER);
	    
	    main_p.add(center_p, BorderLayout.CENTER);
	    
	    add(main_p);
	    setVisible(true);
	    
	}
	
	/**
	 * Scale the given image to fit on the screen.
	 * TODO: maintain the aspect ratio
	 * @param img
	 * @return
	 */
	private ImageIcon scale(ImageIcon img){
		int height, width;
		java.awt.Dimension d = this.getSize();
		if(d.height < d.width){
			height = d.height;
			width = -1;
		} else if(d.width < d.height) {
			height = -1;
			width = d.width;
		} else {
			width = -1;
			height = -1;
		}
		if(img == null){
			return null;
		} else {
			return new ImageIcon(img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		}
	}
	
	private void updateImage(int what){
		switch(what){
		case FIRST:
			this.image.setIcon(scale(model.getFirstImageIcon()));
			break;
		case PREVIOUS:
			this.image.setIcon(scale(model.getPreviousImageIcon()));
			break;
		case NEXT:
			this.image.setIcon(scale(model.getNextImageIcon()));
			break;
		case LAST:
			this.image.setIcon(scale(model.getLastImageIcon()));
			break;
		}
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
			updateImage(PREVIOUS);
			//TODO display categoryList entry for current
			if(model.getPairAt(model.getIndex()) != null){
				this.listContainer.setSelectedIndex(model.getCategoryList().indexOf(model.getPairAt(model.getIndex())));
			} else {
				this.listContainer.clearSelection();
			}
		} else if (buttonText.equals("<<")){
			updateImage(FIRST);
			// display categoryList entry for current
			if(model.getPairAt(model.getIndex()) != null){
				this.listContainer.setSelectedIndex(model.getCategoryList().indexOf(model.getPairAt(model.getIndex())));
			} else {
				this.listContainer.clearSelection();
			}
		} else if (buttonText.equals(">>")){
			updateImage(LAST);
			// display categoryList entry for current
			if(model.getPairAt(model.getIndex()) != null){
				this.listContainer.setSelectedIndex(model.getCategoryList().indexOf(model.getPairAt(model.getIndex())));
			} else {
				this.listContainer.clearSelection();
			}
		} else if (buttonText.equals(">")){
			updateImage(NEXT);
			// display categoryList entry for current
			if(model.getPairAt(model.getIndex()) != null){
				this.listContainer.setSelectedIndex(model.getCategoryList().indexOf(model.getPairAt(model.getIndex())));
			} else {
				this.listContainer.clearSelection();
			}
		} else if (buttonText.equals("Open Folder")){
			int retval = this.fc.showOpenDialog(this);
			if(retval == JFileChooser.APPROVE_OPTION){
				model.setCurrentDir(fc.getSelectedFile());
				this.curDirLabel.setText(model.getCurrentDir().toString());
				updateImage(FIRST);
			}
		} else if (buttonText.equals("Set Folder")){
			System.out.println("Set Folder");
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = chooser.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				model.setDirectoryOut(chooser.getSelectedFile());
			}
		} else if (buttonText.equals("Open Cat List")){
			System.out.println("Open Cat");
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = chooser.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				model.populateCategoryList(chooser.getSelectedFile().getAbsolutePath());
			}
			listModel.clear();
			for( int i = 0 ; i < model.getCategoryList().size() ; i++ ){
				listModel.addElement(model.getCategoryList().get(i));
			}
		} else if (buttonText.equals("Save Cat List")){
			System.out.println("Save Cat");
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = chooser.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				model.writeCategoryList(chooser.getSelectedFile().getAbsolutePath());
			}
		} else if (buttonText.equals("Apply Changes")){
			System.out.println("Apply Changes");
			model.applyChanges();
		} else if (buttonText.equals("New")){
			System.out.println("New");
		} else if (buttonText.equals("Delete")){
			System.out.println("Delete");
		}
		else if (buttonText.equals("Quit")){
			this.close();
		}
		
		if((model.index == model.LAST) && (model.index != 0)){
			first_button.setEnabled(true);
			previous_button.setEnabled(true);
			next_button.setEnabled(false);
			last_button.setEnabled(false);
		} else if ((model.index == model.FIRST) && (model.LAST != 0)) {
			first_button.setEnabled(false);
			previous_button.setEnabled(false);
			next_button.setEnabled(true);
			last_button.setEnabled(true);
		} else if (model.LAST == 0 ){
			first_button.setEnabled(false);
			previous_button.setEnabled(false);
			next_button.setEnabled(false);
			last_button.setEnabled(false);
		} else {
			first_button.setEnabled(true);
			previous_button.setEnabled(true);
			next_button.setEnabled(true);
			last_button.setEnabled(true);
		}
	}
	
	private void close(){
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		/* Open the user's pictures folder the first image in the directory */
		OrganizerModel organizer = new OrganizerModel();
		OrganizerGUI orgGUI = new OrganizerGUI(organizer);
	}
}