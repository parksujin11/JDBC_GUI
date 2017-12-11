package test.main;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import test.dao.DeptDAO;
import test.dto.DeptDTO;
import test.util.DBConnect;

public class DeptFrame extends JFrame{
	
	
	//���̺� ��
		DefaultTableModel model;
	//���̺��� �������� ������ �ʵ�
		
		JTable table;
	//������
	public DeptFrame() {
		initUI();
		
	}
	//UI �ʱ�ȭ �۾� �޼ҵ�
	public void initUI(){
		//���̾ƿ� ����
				setLayout(new BorderLayout());
				//��� ��� ��ü ����
				JPanel topPanel=new JPanel();
			
				//�������� ��ܿ� ��� ��ġ�ϱ�
				add(topPanel, BorderLayout.NORTH);
				
				String[] colNames= {"�����ȣ", "�̸�", "�μ���", "�μ���ġ"};
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
		new DeptFrame();
	}
	
	//DB �� �ִ� ȸ�� ������ JTable �� ����ϴ� �޼ҵ� 
	public void displayMember() {
		//���̺��� ������ ����� 
		model.setRowCount(0);
		//�ٽ� ���
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			conn=new DBConnect().getConn();
			//������ sql �� �غ� 
			String sql="SELECT empno,ename, dname, loc FROM EMP, DEPT WHERE EMP.DEPTNO=DEPT.DEPTNO"
					+ " ORDER BY EMP.DEPTNO ASC";
			//PreparedStatement ��ü�� ������ ������
			//(sql ���� ��� �������� ��ü)
			pstmt=conn.prepareStatement(sql);
			//ResultSet ��ü�� ������ ������ 
			//(SELECT ���� ���� ��� ���� ������ �ִ� ��ü)
			rs=pstmt.executeQuery();
			//�ݺ��� ���鼭 cursor �� ��ĭ�� ������. 
			while(rs.next()){
				//���� cursor �� ��ġ�� ���� 
				//row ���� ���ϴ� Į���� �����͸� ���´�.
				int  empno=rs.getInt("empno");
				String ename=rs.getString("ename");
				String dname=rs.getString("dname");
				String loc=rs.getString("loc");
				Object[] rowData= {empno,ename,dname,loc};
				model.addRow(rowData);//row �߰�
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception e){}
		}//try

	}
}