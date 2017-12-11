package test.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import test.dao.MemberDao;
import test.dto.MemberDto;

public class MemberFrame extends JFrame implements ActionListener{
	
	//�ɹ��ʵ� �����ϱ�
		JTextField inputNum, inputName, inputAddr;
		JButton saveBtn, deleteBtn, updateBtn;
	//���̺� ��
		DefaultTableModel model;
	//���̺��� �������� ������ �ʵ�
		
		JTable table;
	//������
	public MemberFrame() {
		initUI();
		
	}
	//UI �ʱ�ȭ �۾� �޼ҵ�
	public void initUI(){
		//���̾ƿ� ����
				setLayout(new BorderLayout());
				//��� ��� ��ü ����
				JPanel topPanel=new JPanel();
				
				//���̺� ��ü ����
				JLabel label1=new JLabel("��ȣ");
				JLabel label2=new JLabel("�̸�");
				JLabel label3=new JLabel("�ּ�");
				
				//�ؽ�Ʈ �ʵ� ��ü ����
				inputNum=new JTextField(10);
				inputName=new JTextField(10);
				inputAddr=new JTextField(10);
				
				saveBtn=new JButton("����");
				deleteBtn=new JButton("����");
				updateBtn=new JButton("����");
				
				//��ư�� ActionListener ���
				saveBtn.addActionListener(this);
				deleteBtn.addActionListener(this);
				updateBtn.addActionListener(this);
				
				//��Ʈ�� action command ���
				saveBtn.setActionCommand("save");
				deleteBtn.setActionCommand("delete");
				updateBtn.setActionCommand("update");
				
				//��ȣ�� �Է��̳� ������ �� �� ������ ����
				inputNum.setEditable(false);
				
				
				//��ο� ������Ʈ �߰� �ϱ� 
				topPanel.add(label1);
				topPanel.add(inputNum);
				topPanel.add(label2);
				topPanel.add(inputName);
				topPanel.add(label3);
				topPanel.add(inputAddr);
				topPanel.add(saveBtn);
				topPanel.add(deleteBtn);
				topPanel.add(updateBtn);
				
				//�������� ��ܿ� ��� ��ġ�ϱ�
				add(topPanel, BorderLayout.NORTH);
				
				String[] colNames= {"��ȣ", "�̸�", "�ּ�"};
				//�⺻ ���̺� �� ��ü ����
				model=new DefaultTableModel(colNames, 0);
				//JTable ��ü ����
				table=new JTable();
				//���̺� �� ����
				table.setModel(model);
				//��ũ�� ������ �г� ��ü
				JScrollPane sPanel=new JScrollPane(table);
				//���̺��� ��� ��ġ
				add(sPanel, BorderLayout.CENTER);
				
	

		//�������� ��ġ�� ũ�� ����
		setBounds(200, 200, 200, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//ȸ������ ���
		displayMember();
		
	}
	//���� �޼ҵ�
	public static void main(String[] args) {
		new MemberFrame();
	}
	//ActionListener �������̽� �������� ������ �޼ҵ� 
	@Override
	public void actionPerformed(ActionEvent ae) {
		//�̺�Ʈ�� �Ͼ ��ư�� action command�� �о�´�.
		String command=ae.getActionCommand();
		
		if(command.equals("save")) {
		//�Է��� �̸��� �ּ� �о����
			String name=inputName.getText();
			String Addr=inputAddr.getText();
			//MemberDto �� ��´�.
			MemberDto dto=new MemberDto();
			dto.setName(name);
			dto.setNickname(Addr);
			//MemberDao �� �̿��ؼ� ����
			MemberDao dao=MemberDao.getInstance();
			boolean isSuccess=dao.insert(dto);
			if(isSuccess) {
				JOptionPane.showInternalMessageDialog(this, "�����߽��ϴ�.");
			}else {
				JOptionPane.showInternalMessageDialog(this, "���� ���� ");
			}
			
		}else if(command.equals("delete")) {//���� ��ư�� �������� 
			//��, �ƴϿ�, ��� �߿� � ��ư�� �������� ������ int type ���� ���ϵȴ�.
			int result=JOptionPane.showConfirmDialog(this,"�����Ͻðڽ��ϰ�?");
			//�� ��ư�� ������ �ʾҴٸ�
			if(result !=JOptionPane.YES_OPTION) {
				return; //�޼ҵ� ���� 
			}
			//���õ� row �� �ε����� �о�´�.
			int selectedIndex=table.getSelectedRow();
			if(selectedIndex==-1) {
				JOptionPane.showConfirmDialog(this, "������ row�� �������ּ���");
				return;
			}
			//������ row �� �ִ� ȸ�� ��ȣ�� �о�´�.
			int num=(int)table.getValueAt(selectedIndex, 0);
			//DB���� �ش� ȸ�������� �����Ѵ�.
			MemberDao dao=MemberDao.getInstance();
			dao.delete(num);
			
		}else if(command.equals("update")) {
			int selectedIndex=table.getSelectedRow();
			if(selectedIndex==-1) {
				JOptionPane.showConfirmDialog(this, "������ row�� �������ּ���");
				return;
			}
			//������ ȸ�������� �о�ͼ� 
			int num=(int)table.getValueAt(selectedIndex, 0);
			String name=(String)table.getValueAt(selectedIndex, 1);
			String addr=(String)table.getValueAt(selectedIndex, 2);
			//MemberDto ��ü�� ���
			MemberDto dto=new MemberDto(num, name, addr);
			//DB�� ���� �ݿ�
			MemberDao.getInstance().update(dto);
			JOptionPane.showConfirmDialog(this,"�����Ͽ����ϴ�.");
			
		}
		//ȸ�� ���� �ٽ� ���
		displayMember();
		
	}// actionPerformed()
	
	//DB �� �ִ� ȸ�� ������ JTable �� ����ϴ� �޼ҵ� 
	public void displayMember() {
		//ȸ�� ������ �о�´�.
		MemberDao dao=MemberDao.getInstance();
		List<MemberDto> list=dao.getList();
		//���̺��� ������ ����� 
		model.setRowCount(0);
		//�ٽ� ���
		for(MemberDto tmp:list) {
			//row ������ ���� 
			Object[] rowData= {tmp.getNum(), tmp.getName(), tmp.getNickname()};
			model.addRow(rowData);//row �߰�
		}
		
		
		
	}
}
