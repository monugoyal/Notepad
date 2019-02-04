import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
class DlgReplace extends JDialog{
	JLabel lblFindWhat,lblReplaceWith;
	JTextField txtFindWhat,txtReplaceWith;
	JCheckBox chkMatchCase;
	JRadioButton rdoUp,rdoDown;
	ButtonGroup bg;
	JPanel panRadio;
	JButton btnFindNext,btnReplace,btnReplaceAll,btnCancel;
	boolean first=false;
	DlgReplace(Notepad frmNotepad){
		super(frmNotepad,"Replace",false);//true for Modal false for Modeless
		setLayout(null);
		lblFindWhat=new JLabel("Find What");
		lblFindWhat.setBounds(5,10,100,50);
		add(lblFindWhat);
		lblReplaceWith=new JLabel("Replace With");
		lblReplaceWith.setBounds(5,70,100,50);
		add(lblReplaceWith);
		txtFindWhat=new JTextField(20);
		txtFindWhat.setBounds(150,10,200,30);
		add(txtFindWhat);
		txtReplaceWith=new JTextField(20);
		txtReplaceWith.setBounds(150,70,200,30);
		add(txtReplaceWith);
		chkMatchCase=new JCheckBox("Match Case");
		chkMatchCase.setBounds(5,120,100,50);
		add(chkMatchCase);
		rdoUp=new JRadioButton("Up");
		rdoDown=new JRadioButton("Down",true);
		bg=new ButtonGroup();
		bg.add(rdoUp);
		bg.add(rdoDown);
		panRadio=new JPanel();
		panRadio.setBorder(BorderFactory.createTitledBorder("Direction"));
		panRadio.add(rdoUp);
		panRadio.add(rdoDown);	
		panRadio.setLayout(new FlowLayout(FlowLayout.LEFT));
		panRadio.setBounds(170, 120, 100, 80);
		add(panRadio);
		btnFindNext=new JButton("Find Next");
		btnFindNext.setBounds(370,10,100,40);
		btnFindNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				find(frmNotepad);
			}
		});
		add(btnFindNext);
		
		btnReplace=new JButton("Replace");
		btnReplace.setBounds(370,70,100,40);
		btnReplace.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int p=frmNotepad.jta.getSelectionEnd();
				frmNotepad.jta.setText(new StringBuffer(frmNotepad.jta.getText()).delete(frmNotepad.jta.getSelectionStart(), frmNotepad.jta.getSelectionEnd()).insert(frmNotepad.jta.getSelectionStart(), txtReplaceWith.getText()).toString());
				frmNotepad.jta.setCaretPosition(p);
				find(frmNotepad);
			}
		});
		add(btnReplace);
		
		btnReplaceAll=new JButton("ReplaceAll");
		btnReplaceAll.setBounds(370,120,100,40);
		btnReplaceAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				frmNotepad.jta.setText(frmNotepad.jta.getText().replaceAll(txtFindWhat.getText(), txtReplaceWith.getText()));
			}
		});
		add(btnReplaceAll);
		btnCancel=new JButton("Cancel");
		btnCancel.setBounds(370,170,100,40);
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				dispose();
			}
		});
		add(btnCancel);
		setSize(500,260);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	void find(Notepad frmNotepad) {
		String mainString=frmNotepad.jta.getText();
		String subString=txtFindWhat.getText();
		boolean matchCase=chkMatchCase.isSelected();
		String direction;
		int pos=frmNotepad.jta.getCaretPosition();
		if(matchCase==false) {
			mainString=mainString.toUpperCase();
			subString=subString.toUpperCase();
		}
		if(rdoUp.isSelected()) {
			if(first==true)
				pos-=(subString.length()+1);
			first=true;
			pos=mainString.lastIndexOf(subString,pos);
		}
		else {
			pos=mainString.indexOf(subString,pos);
		}
		if(pos!=-1) {
			frmNotepad.jta.setSelectionStart(pos);
			frmNotepad.jta.setSelectionEnd(pos+subString.length());
			frmNotepad.jta.requestFocus();
		}
		else {
			JOptionPane.showMessageDialog(frmNotepad, "String not found");
		}
	}
}