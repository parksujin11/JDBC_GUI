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
	
	
	//테이블 모델
		DefaultTableModel model;
	//테이블의 참조값을 저장할 필드
		
		JTable table;
	//생성자
	public DeptFrame() {
		initUI();
		
	}
	//UI 초기화 작업 메소드
	public void initUI(){
		//레이아웃 설정
				setLayout(new BorderLayout());
				//상단 페널 객체 생성
				JPanel topPanel=new JPanel();
			
				//프레임의 상단에 페널 배치하기
				add(topPanel, BorderLayout.NORTH);
				
				String[] colNames= {"사원번호", "이름", "부서명", "부서위치"};
				//기본 테이블 모델 객체 생성
				model=new DefaultTableModel(colNames, 0);
				//JTable 객체 생성
				table=new JTable();
				//테이블에 모델 연결
				table.setModel(model);
				//스크롤 가능한 패널 객체
				JScrollPane sPanel=new JScrollPane(table);
				//테이블을 가운데 배치
				add(sPanel, BorderLayout.CENTER);
				
	

		//프레임의 위치와 크기 설정
		setBounds(200, 200, 200, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//회원정보 출력
		displayMember();
		
	}
	//메인 메소드
	public static void main(String[] args) {
		new DeptFrame();
	}
	
	//DB 에 있는 회원 정보를 JTable 에 출력하는 메소드 
	public void displayMember() {
		//테이블의 내용을 지우고 
		model.setRowCount(0);
		//다시 출력
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			conn=new DBConnect().getConn();
			//실행할 sql 문 준비 
			String sql="SELECT empno,ename, dname, loc FROM EMP, DEPT WHERE EMP.DEPTNO=DEPT.DEPTNO"
					+ " ORDER BY EMP.DEPTNO ASC";
			//PreparedStatement 객체의 참조값 얻어오기
			//(sql 문을 대신 수행해줄 객체)
			pstmt=conn.prepareStatement(sql);
			//ResultSet 객체의 참조값 얻어오기 
			//(SELECT 문의 수행 결과 값을 가지고 있는 객체)
			rs=pstmt.executeQuery();
			//반복문 돌면서 cursor 를 한칸씩 내린다. 
			while(rs.next()){
				//현재 cursor 가 위치한 곳의 
				//row 에서 원하는 칼럼의 데이터를 얻어온다.
				int  empno=rs.getInt("empno");
				String ename=rs.getString("ename");
				String dname=rs.getString("dname");
				String loc=rs.getString("loc");
				Object[] rowData= {empno,ename,dname,loc};
				model.addRow(rowData);//row 추가
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