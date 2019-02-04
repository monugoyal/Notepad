import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
public class Notepad extends JFrame implements ActionListener {
	JMenuBar mbar;
	JMenu mnuFile,mnuEdit,mnuFormat,mnuHelp;
	JMenuItem mitNew,mitOpen,mitSave,mitSaveAs,mitExit,mitCut,mitCopy,mitPaste,mitDelete,mitFind,mitFindNext,mitReplace,mitSelectAll,mitDateTime,mitFont,mitHelp,mitAbout;
	JCheckBoxMenuItem mitWordWrap;
	JTextArea jta;
	File currentFile;
	String currentFileName="";
	boolean saveFlag=true;
	JScrollPane jsp;
	Font defaultFnt;
	boolean first;
	String mainString,subString;
	boolean matchCase,direction;
	Notepad(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("images/notepad.jpg").getImage());
		mitNew=new JMenuItem("New");
		mitNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		mitOpen=new JMenuItem("Open");
		mitSave=new JMenuItem("Save");
		mitSaveAs=new JMenuItem("Save As");
		mitExit=new JMenuItem("Exit");
		mitCut=new JMenuItem("Cut");
		mitCopy=new JMenuItem("Copy");
		mitPaste=new JMenuItem("Paste");
		mitDelete=new JMenuItem("Delete");
		mitFind=new JMenuItem("Find");
		mitFindNext=new JMenuItem("Find Next");
		mitReplace=new JMenuItem("Replace");
		mitSelectAll=new JMenuItem("Select All");
		mitDateTime=new JMenuItem("Date/Time");
		mitWordWrap=new JCheckBoxMenuItem("Word Wrap",true);
		mitFont=new JMenuItem("Font");
		mitHelp=new JMenuItem("Help");
		mitAbout=new JMenuItem("About");
		
		mitNew.addActionListener(this);
		mitOpen.addActionListener(this);
		mitSave.addActionListener(this);
		mitSaveAs.addActionListener(this);
		mitExit.addActionListener(this);
		mitCut.addActionListener(this);
		mitCopy.addActionListener(this);
		mitPaste.addActionListener(this);
		mitDelete.addActionListener(this);
		mitFind.addActionListener(this);
		mitFindNext.addActionListener(this);
		mitReplace.addActionListener(this);
		mitSelectAll.addActionListener(this);
		mitDateTime.addActionListener(this);
		mitWordWrap.addActionListener(this);
		mitFont.addActionListener(this);
		mitHelp.addActionListener(this);
		mitAbout.addActionListener(this);
		
		mnuFile=new JMenu("File");
		mnuFile.setMnemonic(KeyEvent.VK_F);
		mnuFile.add(mitNew);
		mnuFile.add(mitOpen);
		mnuFile.add(mitSave);
		mnuFile.add(mitSaveAs);
		mnuFile.addSeparator();
		mnuFile.add(mitExit);
		
		mnuEdit=new JMenu("Edit");
		mnuEdit.add(mitCut);
		mnuEdit.add(mitCopy);
		mnuEdit.add(mitPaste);
		mnuEdit.add(mitDelete);
		mnuEdit.addSeparator();
		mnuEdit.add(mitFind);
		mnuEdit.add(mitFindNext);
		mnuEdit.add(mitReplace);
		mnuEdit.addSeparator();
		mnuEdit.add(mitSelectAll);
		mnuEdit.add(mitDateTime);
		
		mnuFormat=new JMenu("Format");
		mnuFormat.add(mitWordWrap);
		mnuFormat.add(mitFont);
		
		mnuHelp=new JMenu("Help");
		mnuHelp.add(mitHelp);
		mnuHelp.add(mitAbout);
		
		mbar=new JMenuBar();
		mbar.add(mnuFile);
		mbar.add(mnuEdit);
		mbar.add(mnuFormat);
		mbar.add(mnuHelp);
		
		setJMenuBar(mbar);
		setTitle("Untitled - Notepad");
		
		jta=new JTextArea();
		jta.setLineWrap(true);
		jsp=new JScrollPane(jta);
		add(jsp);
		jta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				first=false;
			}
		});
		jta.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				saveFlag=false;
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				saveFlag=false;
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				saveFlag=false;	
			}
		});
		
		try{
			FileInputStream fis=new FileInputStream("documents/notepad.cfg");
			Properties p1=new Properties();
			p1.load(fis);
			defaultFnt=new Font(p1.getProperty("fntname"),Integer.parseInt(p1.getProperty("fntstyle")),Integer.parseInt(p1.getProperty("fntsize")));
		}
		catch(FileNotFoundException e){
			defaultFnt=new Font("Arial",Font.PLAIN,20);	
		}
		catch(IOException e){
			e.printStackTrace();
		}		
		jta.setFont(defaultFnt);

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		String s1=ae.getActionCommand();
		if(s1.equalsIgnoreCase("new")) {
			if(saveFlag!=true) {
				int ans=JOptionPane.showConfirmDialog(this, "Save Y/N?","Notepad",JOptionPane.YES_NO_OPTION);
				if(ans==JOptionPane.YES_OPTION) {
					save();
					return;
				}
			}
			setTitle("Untitled - Notepad");
			jta.setText("");
			currentFile=null;
			currentFileName="";
		}
		else if(s1.equalsIgnoreCase("open")) {
			JFileChooser jfc=new JFileChooser("c:/javaprog");
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Text Files", "txt");
			jfc.addChoosableFileFilter(filter1);
			jfc.setFileFilter(filter1);		
			int result=jfc.showOpenDialog(this);
			if(result==JFileChooser.APPROVE_OPTION) {
				try {
					currentFile=jfc.getSelectedFile();
					currentFileName=currentFile.getName();
					jta.setText("");
					FileInputStream fis=new FileInputStream(currentFile);
					byte b[]=new byte[10];
					int cnt=0;
					while((cnt=fis.read(b))!=-1) {
						jta.append(new String(b,0,cnt));
					}
					fis.close();
					setTitle(currentFileName.substring(0, currentFileName.lastIndexOf(".")) + " - Notepad");
				}
				catch(FileNotFoundException e) {
					e.printStackTrace();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(this,"No File Selected");
			}	
		}
		else if(s1.equalsIgnoreCase("save")) {
			save();
		}
		else if(s1.equalsIgnoreCase("save as")) {
			saveAs();
		}
		else if(s1.equalsIgnoreCase("exit")) {
			System.exit(0);
		}
		else if(s1.equalsIgnoreCase("cut")) {
			/*String s=jta.getSelectedText();
			StringSelection selection=new StringSelection(s);
			Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
			clip.setContents(selection,selection);
			jta.setText(new StringBuffer(jta.getText()).delete(jta.getSelectionStart(),jta.getSelectionEnd()).toString());*/
			jta.cut();
		}
		else if(s1.equalsIgnoreCase("copy")) {
			/*String s=jta.getSelectedText();
			StringSelection selection=new StringSelection(s);
			Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
			clip.setContents(selection,selection);*/
			jta.copy();
		}
		else if(s1.equalsIgnoreCase("paste")) {
			/*try{
				Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
				jta.setText(new StringBuffer(jta.getText()).insert(jta.getCaretPosition(),(String)clip.getContents(this).getTransferData(DataFlavor.stringFlavor)).toString());
			}
			catch(UnsupportedFlavorException e){
				e.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}*/
			jta.paste();
		}
		else if(s1.equalsIgnoreCase("delete")) {
			jta.setText(new StringBuffer(jta.getText()).delete(jta.getSelectionStart(),jta.getSelectionEnd()).toString());
		}
		else if(s1.equalsIgnoreCase("find")) {
			new DlgFind(this);
		}
		else if(s1.equalsIgnoreCase("find next")) {
			find(mainString,subString,matchCase,direction);
		}
		else if(s1.equalsIgnoreCase("replace")) {
			new DlgReplace(this);
		}
		else if(s1.equalsIgnoreCase("select all")) {
			//jta.setSelectionStart(0);
			//jta.setSelectionEnd(jta.getText().length());
			jta.selectAll();
		}
		else if(s1.equalsIgnoreCase("date/time")) {
			Date d1=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("hh:mm dd:MM:yyyy");
			jta.setText(new StringBuffer(jta.getText()).insert(jta.getCaretPosition(), sdf.format(d1)).toString());
		}
		else if(s1.equalsIgnoreCase("word wrap")) {
			jta.setLineWrap(!jta.getLineWrap());
		}
		else if(s1.equalsIgnoreCase("font")) {
			new DlgFont(this);
		}
		else if(s1.equalsIgnoreCase("help")) {
			new DlgHelp(this);
		}
		else if(s1.equalsIgnoreCase("about")) {
			new DlgAbout(this);
		}		
	}
	void save() {
		if(currentFileName.equals("")) {
			saveAs();
		}
		else {
			try {
				FileOutputStream fos=new FileOutputStream(currentFile);
				fos.write(jta.getText().getBytes());
				fos.close();
				saveFlag=true;
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	void saveAs(){
		JFileChooser jfc=new JFileChooser("c:/javaprog");
		FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Text Files", "txt");
		jfc.addChoosableFileFilter(filter1);
		jfc.setFileFilter(filter1);		
		int result=jfc.showSaveDialog(this);
		if(result==JFileChooser.APPROVE_OPTION) {
			try {
				currentFile=jfc.getSelectedFile();
				currentFileName=currentFile.getName();
				if(currentFileName.indexOf(".")==-1)
					currentFileName+=".txt";
				currentFile=new File(currentFile.getParent(),currentFileName);
				FileOutputStream fos=new FileOutputStream(currentFile);
				fos.write(jta.getText().getBytes());
				fos.close();
				setTitle(currentFileName.substring(0, currentFileName.lastIndexOf(".")) + " - Notepad");
				saveFlag=true;
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}		
		}
	}
	void find(String mainString,String subString,boolean matchCase,boolean direction) {
		this.mainString=mainString;
		this.subString=subString;
		this.matchCase=matchCase;
		this.direction=direction;
		int pos=jta.getCaretPosition();
		if(matchCase==false) {
			mainString=mainString.toUpperCase();
			subString=subString.toUpperCase();
		}
		if(direction) {
			if(first==true) {
				pos-=(subString.length()+1);
				jta.setCaretPosition(pos);
			}
			first=true;
			pos=mainString.lastIndexOf(subString,pos);
		}
		else {
			pos=mainString.indexOf(subString,pos);
		}
		if(pos!=-1) {
			jta.setSelectionStart(pos);
			jta.setSelectionEnd(pos+subString.length());
		}
		else {
			JOptionPane.showMessageDialog(this, "String not found");
		}
	}
	public static void main(String[] args) {
		new Notepad();
	}
}