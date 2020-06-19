import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.text.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class ComputationFrame{
	private JFrame f;
	private Frame plottingFrame;
	private JMenuBar mb;
	private JMenu mFile;//file
	private JMenu mEdit;//Edit
	private JMenu mTools;//Tools
	private JMenu mHelp;//help
	private JMenuItem miExit;//exit
	private JMenuItem miNew;//new
	private JMenuItem miOpen;//open
	private JMenuItem miSave;//save
	private JMenuItem miContents;//Contents
	private JMenuItem miAbout;//About
	private JMenuItem miPlot;//Plot
	private JMenuItem miBackground;//Background
	private JMenuItem miForeground;//Foreground
	private JMenuItem miExportAsHTML;//export as HTML							
	private JPanel northPanel;	
	private JPanel centerPanelInNorthPanel;
	private JPanel southPanelInNorthPanel;
	private JPanel centerPanel;
	private JPanel northPanelInNorthPanel;
	private JPanel centerContainer; //set its LayoutManager to null
	private JTextField newTextField;	
	private int numStations=0;	
	private JOptionPane op;
	private JButton btnCompute;
	private JButton btnAdjust;
	private double prevNorthing=0;
	private double prevEasting=0;
	private JTextField initNorthing;
	private JTextField initEasting;
	private DecimalFormat myFormatter = new DecimalFormat("0.###");
	private boolean notFreshlyLoaded = false;
	private JFileChooser newFileChooser = new JFileChooser();
	private JScrollPane scrollPane;	
	private TextFileFilter filter = new TextFileFilter();
	private HTMLFileFilter HTMLfilter = new HTMLFileFilter();
	private JPGFileFilter JPGfilter = new JPGFileFilter();
	private JTextField JTextLength;
	private JTextField JTextEe;
	private JTextField JTextEn;
	private JTextField JTextRE;
	private JTextField JTextLEC;
	private java.util.List list;
	private Plotter newPlotter;
	
	private ComputationFrame(){			
		f = new JFrame("TraversePro 2004 (Professional Edition)");			
		f.setDefaultLookAndFeelDecorated(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		f.setResizable(false);
		f.getContentPane().setLayout(new BorderLayout());		
		//panels
		northPanel = new JPanel();
		centerPanelInNorthPanel = new JPanel();
		centerPanel = new JPanel();		
		centerPanel.setLayout(new GridLayout(0,8));
		f.getContentPane().add(centerPanel,BorderLayout.CENTER);					
		northPanelInNorthPanel = new JPanel();		
		f.getContentPane().add(northPanel,BorderLayout.NORTH);				
		
		//********scrollPane************
		centerContainer = new JPanel();
		centerContainer.setLayout(null);
		centerContainer.setSize(500,500);
		f.getContentPane().add(centerContainer,BorderLayout.CENTER);
		scrollPane = new JScrollPane(centerPanel);
		centerContainer.add(scrollPane);
		scrollPane.setSize(780,400);		
		centerPanel.setSize(500,500);		
		centerContainer.setSize(800,600);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);		
		//********scrollPane************

		//menu				
		String path = "/home/java01/TraversePro/m4.gif";		
     	ImageIcon icon = new ImageIcon(path);
		mb = new JMenuBar();		
		mFile = new JMenu("File");		
		mFile.setMnemonic(KeyEvent.VK_F);		
		mb.add(mFile);
		miNew = new JMenuItem("New");		
      miNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		miNew.setMnemonic(KeyEvent.VK_N);
		miOpen = new JMenuItem("Open...");
		miOpen.setMnemonic(KeyEvent.VK_O);
		miOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		miSave = new JMenuItem("Save");
		miSave.setMnemonic(KeyEvent.VK_S);
		miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		miSave.setEnabled(false);
		miExportAsHTML = new JMenuItem("Export as *.html");
		miExportAsHTML.setMnemonic(KeyEvent.VK_H);
		miExportAsHTML.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		miExportAsHTML.setEnabled(false);
		miExit = new JMenuItem("Quit");
		miExit.setMnemonic(KeyEvent.VK_Q);
		miExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		miContents = new JMenuItem("Contents     ");
		//miContents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		miContents.setMnemonic(KeyEvent.VK_C);
		miAbout = new JMenuItem("About",icon);		
		miAbout.setMnemonic(KeyEvent.VK_A);
		//miAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));	
		mEdit = new JMenu("Edit");
		mb.add(mEdit);
		miBackground = new JMenuItem("Background");
		miBackground.setMnemonic(KeyEvent.VK_B);
		miBackground.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		miForeground = new JMenuItem("Foreground");
		miForeground.setMnemonic(KeyEvent.VK_F);
		miForeground.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		mEdit.add(miBackground);
		mEdit.add(miForeground);		
		mEdit.setMnemonic(KeyEvent.VK_E);
		mTools = new JMenu("Tools");
		mTools.setMnemonic(KeyEvent.VK_T);	
		mb.add(mTools);	
		miPlot = new JMenuItem("Plot");
		miPlot.setEnabled(false);
		miPlot.setMnemonic(KeyEvent.VK_P);
		miPlot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		mHelp = new JMenu("Help");
		mHelp.setMnemonic(KeyEvent.VK_H);
		mb.add(mHelp);
		miExit.addActionListener(new MenuItemHandler());
		miNew.addActionListener(new MenuItemHandler());
		miOpen.addActionListener(new MenuItemHandler());
		miSave.addActionListener(new MenuItemHandler());		
		miExportAsHTML.addActionListener(new MenuItemHandler());		
		miBackground.addActionListener(new MenuItemHandler());
		miForeground.addActionListener(new MenuItemHandler());
		miPlot.addActionListener(new MenuItemHandler());
		miContents.addActionListener(new ContentHandler());
		miAbout.addActionListener(new ContentHandler());
		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.addSeparator();		
		mFile.add(miSave);
		mFile.add(miExportAsHTML);
		mFile.addSeparator();
		mFile.add(miExit);		
		mTools.add(miPlot);
		mHelp.add(miContents);
		mHelp.add(miAbout);		
		
		btnCompute = new JButton("Compute");
		btnCompute.setMnemonic(KeyEvent.VK_C);
		btnAdjust = new JButton("Adjust");		
		btnAdjust.setMnemonic(KeyEvent.VK_A);
		btnAdjust.setEnabled(false);		
		btnCompute.addActionListener(new ButtonHandler());
		btnCompute.setEnabled(false);
		initNorthing = new JTextField("0",10);
		initNorthing.setHorizontalAlignment(JTextField.RIGHT);
		initEasting = new JTextField("0",10);
		initEasting.setHorizontalAlignment(JTextField.RIGHT);
		initNorthing.setBackground(Color.white);
		initEasting.setBackground(Color.white);
		northPanel.setLayout(new GridLayout(3,1));
		northPanel.add(northPanelInNorthPanel);		
		JLabel newLabel = new JLabel("Initial Northing");
		northPanelInNorthPanel.add(newLabel);		
		northPanelInNorthPanel.add(initNorthing);
		newLabel = new JLabel("Initial Easting");
		northPanelInNorthPanel.add(newLabel);
		northPanelInNorthPanel.add(initEasting);		
		northPanelInNorthPanel.add(btnCompute);
		//northPanelInNorthPanel.add(btnAdjust);
		northPanel.add(centerPanelInNorthPanel);
		southPanelInNorthPanel = new JPanel();
		northPanel.add(southPanelInNorthPanel);
		//centerPanelInNorthPanel.setLayout();
		southPanelInNorthPanel.setLayout(new GridLayout(1,8));
		f.setJMenuBar(mb);
		f.setSize(790,550);
		createOtherTextFieldsAndLabels();
		createLabels();		
		f.setVisible(true);		
		op = new JOptionPane();
		//changeBackground(new Color(127,127,150));
	}//end ComputationFrame Constructor

	public void changeBackground(Color newColor){
		ChangeBackground.changeBackground(centerPanel,newColor);		
		ChangeBackground.changeBackground(centerContainer,newColor);	
		ChangeBackground.changeBackground(centerPanelInNorthPanel,newColor);
   	ChangeBackground.changeBackground(northPanelInNorthPanel,newColor);
		ChangeBackground.changeBackground(southPanelInNorthPanel,newColor);
		ChangeBackground.changeBackground(mb,newColor);
		ChangeBackground.changeBackground(mFile,newColor);
		ChangeBackground.changeBackground(mEdit,newColor);
		ChangeBackground.changeBackground(mHelp,newColor);
			
	}

	public void changeForeground(Color newColor){
		ChangeBackground.changeForeground(centerPanel,newColor);		
		ChangeBackground.changeForeground(centerContainer,newColor);	
		ChangeBackground.changeForeground(centerPanelInNorthPanel,newColor);
   	ChangeBackground.changeForeground(northPanelInNorthPanel,newColor);
		ChangeBackground.changeForeground(southPanelInNorthPanel,newColor);
		ChangeBackground.changeForeground(mb,newColor);
		ChangeBackground.changeForeground(mFile,newColor);
		ChangeBackground.changeForeground(mEdit,newColor);
		ChangeBackground.changeForeground(mHelp,newColor);
	}

	public static void main(String [] args){		
		ComputationFrame cf = new ComputationFrame();				
	}
	
	private void createOtherTextFieldsAndLabels(){
		JLabel l = new JLabel("Length");		
		centerPanelInNorthPanel.add(l);
		JTextLength = new JTextField("",8);
		JTextLength.setHorizontalAlignment(JTextField.CENTER);
		centerPanelInNorthPanel.add(JTextLength);
		l = new JLabel("En");		
		centerPanelInNorthPanel.add(l);
		JTextEn = new JTextField("",8);
		JTextEn.setHorizontalAlignment(JTextField.CENTER);
		centerPanelInNorthPanel.add(JTextEn);
		l = new JLabel("Ee");		
		centerPanelInNorthPanel.add(l);
		JTextEe = new JTextField("",8);
		JTextEe.setHorizontalAlignment(JTextField.CENTER);
		centerPanelInNorthPanel.add(JTextEe);		
		l = new JLabel("LEC");		
		centerPanelInNorthPanel.add(l);
		JTextLEC = new JTextField("",8);
		JTextLEC.setHorizontalAlignment(JTextField.CENTER);
		centerPanelInNorthPanel.add(JTextLEC);
		l = new JLabel("Relative Error");		
		centerPanelInNorthPanel.add(l);
		JTextRE = new JTextField("",8);
		JTextRE.setHorizontalAlignment(JTextField.CENTER);
		centerPanelInNorthPanel.add(JTextRE);
		JTextLength.setEditable(false);
		JTextEe.setEditable(false);
 		JTextEn.setEditable(false);
		JTextRE.setEditable(false);
		JTextLEC.setEditable(false);
	}
	
	private void createLabels(){		
		JLabel l = new JLabel("        Sta Occ");		
		southPanelInNorthPanel.add(l);
		l = new JLabel("       Sta Obs");
		southPanelInNorthPanel.add(l);
		l = new JLabel("       Distance");
		southPanelInNorthPanel.add(l);
		l = new JLabel("       Azimuth");
		southPanelInNorthPanel.add(l);
		l = new JLabel("      Latitude");
		southPanelInNorthPanel.add(l);
		l = new JLabel("     Departure");
		southPanelInNorthPanel.add(l);
		l = new JLabel("     Northing");
		southPanelInNorthPanel.add(l); 
		l = new JLabel("     Easting");
		southPanelInNorthPanel.add(l);
	}
	private void resetText(){
		initNorthing.setText("0"); initEasting.setText("0");
		JTextLength.setText("");
		JTextEe.setText("");
		JTextEn.setText("");
		JTextRE.setText("");
		JTextLEC.setText("");
	}

	private void createTextFields(int numStations){				
		//if there is a previous list, the old list becomes a candidate for garbage collection
		list = new ArrayList();		
		int height=5;
		for(int i=0;i<numStations;i++){	
			height += 20;	
			scrollPane.setSize(780,height);			
			for(int j=0;j<8;j++){				
				newTextField = new JTextField("",8);				
				if (j>3){					
					newTextField.setEditable(false);				
				}
				if (j<2){
					newTextField.setHorizontalAlignment(JTextField.CENTER);
				}
				else{
					newTextField.setHorizontalAlignment(JTextField.RIGHT);
				}
				centerPanel.add(newTextField);
				list.add(newTextField);				
			}//end inner for
		}//end outer for
		if (numStations>20){
			scrollPane.setSize(780,400);
		}
		f.setVisible(true);	
	}//end createTextFields
	
	class MenuItemHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource()==miExit){//exit
				System.exit(0);
			}//end exit
			else if(e.getSource()==miNew){//new			
				try{
					int oldNumStations = numStations;
					numStations=Integer.parseInt(op.showInputDialog(f,"Input Number of Stations:",String.valueOf(3)));
					if (numStations<3){
						op.showMessageDialog(f,"Minimum number of stations should be 3.","TraversePro 2004 (Professional Edition)",JOptionPane.ERROR_MESSAGE);
						numStations = 3;
					}
					if (notFreshlyLoaded){
						removeItemsInList(oldNumStations);
					}
					miSave.setEnabled(true);
					miPlot.setEnabled(false);
					resetText();								
					miExportAsHTML.setEnabled(false);						
					btnCompute.setEnabled(true);
					createTextFields(++numStations);
					notFreshlyLoaded = true;
					f.setTitle("TraversePro 2004 (Professional Edition) - New File");
				}catch(NumberFormatException numFormatEx){
					op.showMessageDialog(f,"Please input a valid number of stations.","TraversePro 2004 (Professional Edition)",JOptionPane.ERROR_MESSAGE);
				}//end catch(NumberFormatException numFormatEx)
			}//end new
			else if(e.getSource()==miOpen){//open							
				String newString,fileName;
				newFileChooser.setSelectedFile(new File("untitled"));									
				newFileChooser.addChoosableFileFilter(filter);
				newFileChooser.setFileFilter(filter);	
				int returnVal = newFileChooser.showOpenDialog(f);
				if(returnVal == JFileChooser.APPROVE_OPTION) {	
					resetText();
					miPlot.setEnabled(false);
					int oldNumStations = numStations;					
					if (notFreshlyLoaded){
						removeItemsInList(oldNumStations);
					}						
					File newFile = newFileChooser.getCurrentDirectory();
					//System.out.println(newFile);
					fileName = newFile.getAbsolutePath() + "/";
					fileName += newFileChooser.getSelectedFile().getName();
					TextFileReader newFileReader = new TextFileReader();
					newString = newFileReader.ReadFile(fileName);
					f.setTitle("TraversePro 2004 (Professional Edition) - "+newFileChooser.getSelectedFile().getName());

					//count number of commas					
					int end=0,start=0,elementCounter=0;
					while(end>=0){						
						end = newString.indexOf(",",start);
						if (end<0){ 
							elementCounter++;		
							break;						
						}
						else{
							elementCounter++;
							start = ++end;
						}
					}//end while
					
					numStations = elementCounter/8;
					createTextFields(numStations);
					
					//put strings in TextFields
					String printThis;
					end=0;start=0;elementCounter=0; //reinitialize variables;
					while(end>=0){						
						end = newString.indexOf(",",start);
						if (end<0){ 
							printThis = newString.substring(start,newString.length()-4);
							//System.out.println(printThis);								
							newTextField = (JTextField)list.get(elementCounter++);
							newTextField.setText(printThis);
							break;
						}
						else{
							printThis = newString.substring(start,end);							
							newTextField = (JTextField)list.get(elementCounter);
							start = ++end;
							//System.out.println(printThis);
							newTextField = (JTextField)list.get(elementCounter++);
							newTextField.setText(printThis);
						}
					}//end while
					miSave.setEnabled(true);													
					btnCompute.setEnabled(true);
					notFreshlyLoaded = true;					
					miExportAsHTML.setEnabled(false);
				}//end if
			}//end open
			else if(e.getSource()==miSave){//save				
				String fileName;
				String dataToBePassed;
				newFileChooser.addChoosableFileFilter(filter);
				newFileChooser.setFileFilter(filter);
				newFileChooser.setSelectedFile(new File("untitled"));
				dataToBePassed = getDataFromTextFields();
				newFileChooser.setDialogTitle("Save");				
				int returnVal = newFileChooser.showSaveDialog(f);		
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File newFile = newFileChooser.getCurrentDirectory();				
					fileName = newFile.getAbsolutePath() + "/";					
					fileName += newFileChooser.getSelectedFile().getName();
					if (!fileName.substring(fileName.length()-4).equals(".tra")){
						fileName +=".tra";						
					}					
					TextFileWriter newTextFileWriter = new TextFileWriter();					
					newTextFileWriter.writeToFile(fileName,dataToBePassed);
					f.setTitle("TraversePro 2004 (Professional Edition) - " + newFileChooser.getSelectedFile().getName());
				}//end if
			}//end save 
			else if(e.getSource()==miBackground){ //background
				Color backgroundColor;
				JColorChooser colorChooser = new JColorChooser();
				try{				
					backgroundColor = colorChooser.showDialog(f,"Set Background Color",new Color(127,127,160));
					if (!backgroundColor.toString().equals("null")){
						changeBackground(backgroundColor);
					}
				}catch(NullPointerException nullEx){
					//do nothing
				}//end catch(NullPointerException nullEx)	
			}//end background
			else if(e.getSource()==miForeground){ //foreground
				Color foregroundColor;
				JColorChooser colorChooser = new JColorChooser();
				try{				
					foregroundColor = colorChooser.showDialog(f,"Set Foreground Color",new Color(0,0,0));
					if (!foregroundColor.toString().equals("null")){
						changeForeground(foregroundColor);
					}
				}catch(NullPointerException nullEx2){
					//do nothing
				}//end catch(NullPointerException nullEx2)	
			}//end foreground
			else if(e.getSource()==miExportAsHTML){ //export as html				
				String fileName;
				String dataToBePassed;
				newFileChooser.addChoosableFileFilter(HTMLfilter);
				newFileChooser.setFileFilter(HTMLfilter);
				newFileChooser.setSelectedFile(new File("untitled"));
				dataToBePassed = getOtherData() + getDataFromTextFields(); 
				dataToBePassed = ExportToHTML.createHTML(dataToBePassed);				

				newFileChooser.setDialogTitle("Export as HTML");				
				int returnVal = newFileChooser.showSaveDialog(f);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File newFile = newFileChooser.getCurrentDirectory();				
					fileName = newFile.getAbsolutePath() + "/";
					fileName += newFileChooser.getSelectedFile().getName();
					if (!fileName.substring(fileName.length()-5).equals(".html")){
						fileName +=".html";						
					}									
					TextFileWriter newTextFileWriter = new TextFileWriter();					
					newTextFileWriter.writeToFile(fileName,dataToBePassed);
				}//end if(returnVal...)
			}//end export as html
			else if(e.getSource()==miPlot){ //Plot						
				newPlotter = new Plotter(list);
				plottingFrame = new Frame("TraversePro 2004");
				plottingFrame.addWindowListener(new WindowAdapter() {
      			public void windowClosing(WindowEvent e){					
        			plottingFrame.dispose();
      		}});				
				plottingFrame.setResizable(false);
				MenuBar pfMB = new MenuBar();
				Menu pfFile = new Menu("File");
				MenuItem pfSave = new MenuItem("Save      ");
				plottingFrame.setMenuBar(pfMB);
				pfMB.add(pfFile);
				pfFile.add(pfSave);
				pfSave.addActionListener(new SaveImageActionListener());
				plottingFrame.add(newPlotter);
				plottingFrame.setSize(750,550);
				plottingFrame.setVisible(true);
			}
		}//end actionPerformed
	}//end MenuItemHandler
	
	private void removeItemsInList(int oldNumStations){
		//dereferencing list makes the old elements of list to be garbage collected
		//see createTextFields (list = new Arraylist())
		for (int i=0;i<oldNumStations*8;i++){
			newTextField = (JTextField)list.get(i);			
			centerPanel.remove(newTextField);
		}				
	}
	
	private String getDataFromTextFields(){
		String returnThis = "";
		int counter=0;
		
		//newTextField = (JTextField)list.get(0);
		//returnThis = newTextField.getText() ;

		for(int i=0;i<numStations;i++){
			for(int j=0;j<8;j++){				
				newTextField = (JTextField)list.get(counter++);
				if (i==numStations-1 && j==7)	returnThis += newTextField.getText();
				else returnThis += newTextField.getText() + ","; 
				if (j==7) returnThis += "\n";				
			}
		}		
		return returnThis;
	}//end getDataFromtextFields()

	private String getOtherData(){
		String returnThis;
		returnThis = initNorthing.getText() + ",";
		returnThis += initEasting.getText() + ",";
		returnThis += JTextLength.getText() + ",";
		returnThis += JTextEn.getText() + ",";
		returnThis += JTextEe.getText() + ",";
		returnThis += JTextLEC.getText() + ",";
		returnThis += JTextRE.getText() + ",";

		return returnThis;
	}
	
	class SaveImageActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String fileName = "";	
			newFileChooser.addChoosableFileFilter(JPGfilter);
			newFileChooser.setFileFilter(JPGfilter);
			newFileChooser.setDialogTitle("Save Image");
			newFileChooser.setSelectedFile(new File("untitled"));
			int returnVal = newFileChooser.showSaveDialog(f);		
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File newFile = newFileChooser.getCurrentDirectory();				
				fileName = newFile.getAbsolutePath() + "/";					
				fileName += newFileChooser.getSelectedFile().getName();
				
				if (!fileName.substring(fileName.length()-4).equals(".jpg")){
					fileName +=".jpg";						
				}//end if
				
				try{				
		      BufferedImage image = new BufferedImage(750,550,BufferedImage.TYPE_INT_RGB);
   		   Graphics2D g2 = image.createGraphics();
				newPlotter.paint(g2);				
				ImageIO.write(image, "JPG", new File(fileName));
				}catch(IOException e1){
					System.out.println("IOException");
				}//end catch(IOException e1)					
			}//end if			
		}//end actionPerformed
	}//end SaveImageActionListener 

	class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			double dep=0,lat=0;
			double azimuth=0,distance=0;
			
			double northing=0,easting=0;
			double totalLength=0;			
			int counter = 0;
			if (e.getSource()==btnCompute){
				try{
					prevNorthing = Double.parseDouble(initNorthing.getText());
					prevEasting = Double.parseDouble(initEasting.getText());
					while(counter<=(numStations*8)-1){
						counter++;
						newTextField = (JTextField)list.get(++counter);
						//get distances and azimuths
						distance = Double.parseDouble(newTextField.getText());
						totalLength += distance;
						newTextField = (JTextField)list.get(++counter);						
						azimuth = Azimuth.parseAzimuth(newTextField.getText());
						//azimuth = Double.parseDouble(newTextField.getText());

						dep = Double.parseDouble(myFormatter.format(ComputeDepLat.computeDep(azimuth,distance)));
						lat = Double.parseDouble(myFormatter.format(ComputeDepLat.computeLat(azimuth,distance)));					
						newTextField = (JTextField)list.get(++counter);
						//set dep and lat fields
						newTextField.setText(Double.toString(lat));
						if (newTextField.getText().equals("-0.0")) newTextField.setText("0.0");
						newTextField = (JTextField)list.get(++counter);
						newTextField.setText(Double.toString(dep));
						if (newTextField.getText().equals("-0.0")) newTextField.setText("0.0");
						//set northing and easting fields
						newTextField = (JTextField)list.get(++counter);
						northing = Double.parseDouble(myFormatter.format(lat + prevNorthing));
						newTextField.setText(Double.toString(northing));
						prevNorthing = northing;
						newTextField = (JTextField)list.get(++counter);
						easting = Double.parseDouble(myFormatter.format(dep + prevEasting));
						newTextField.setText(Double.toString(easting));
						prevEasting = easting;					
						if (counter<(numStations*8)-1){
							newTextField = (JTextField)list.get(++counter);
						}
						else{
							break;						
						}						
					}//end while
					//compute total Length
					newTextField=(JTextField)list.get(2);						
					double initLength = Double.parseDouble(newTextField.getText());				
					totalLength=totalLength-initLength;
					JTextLength.setText(String.valueOf(myFormatter.format(totalLength)));
					//compute En
					newTextField=(JTextField)list.get(6);						
					double NorthingOfFirstStation=Double.parseDouble(newTextField.getText());		
					JTextEn.setText(String.valueOf(myFormatter.format(northing-NorthingOfFirstStation)));
					//compute Ee
					newTextField=(JTextField)list.get(7);						
					double EastingOfFirstStation=Double.parseDouble(newTextField.getText());		
					JTextEe.setText(String.valueOf(myFormatter.format(easting-EastingOfFirstStation)));
					//compute LEC
					double LEC;
					double Ee = Double.parseDouble(JTextEe.getText());
					double En = Double.parseDouble(JTextEn.getText());					
					LEC = Math.pow(Ee,2) + Math.pow(En,2);
					LEC = Math.pow(LEC,0.5);
					JTextLEC.setText(String.valueOf(myFormatter.format(LEC)));
					//compute RE
					double RE;					
					RE = (totalLength)/Double.parseDouble(JTextLEC.getText());
					JTextRE.setText("1:"+String.valueOf(myFormatter.format(RE)));					
				}catch(NumberFormatException numFormatEx){
					//do nothing just catch the NumberFormatException					
				}//end catch(NumberFormatException numFormatEx)
			miExportAsHTML.setEnabled(true);
			miPlot.setEnabled(true);
			}//end if (e.getSource()==btnCompute)
		}//end actionPerformed
	}//end ButtonHandler

	class ContentHandler implements ActionListener{
		private JOptionPane instructions;
		public void actionPerformed(ActionEvent e){
			String myInst;
			instructions = new JOptionPane();		
			if(e.getSource()==miContents){//contents
				myInst="Closed Loop Traverse\n\nA traverse that originates and terminates on a single";
				myInst+=" point \nof known horizontal position.";
				instructions.showMessageDialog(f,myInst,"TraversePro 2004 (Professional Edition)",JOptionPane.INFORMATION_MESSAGE);
			}//end contents
			else if(e.getSource()==miAbout){//about
				myInst = "       Created by Mikhail C. Dumlao       ";				
				instructions.showMessageDialog(f,myInst,"TraversePro 2004 (Professional Edition)",JOptionPane.INFORMATION_MESSAGE);
			}//end about
		}//end actionPerformed
	}//end ContentHandler

}//end ComputationFrame
