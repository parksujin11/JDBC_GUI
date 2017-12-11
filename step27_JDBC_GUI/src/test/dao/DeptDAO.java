
package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.dto.DeptDTO;
import test.util.DBConnect;

/*
 *  Dao => Data Access Object �� ����
 *  
 *  - ȸ�� ������ ���ؼ� SELECT , INSERT, UPDATE, DELETE 
 *    �۾��� ������ ��ü�� �����ϱ����� Ŭ���� �����ϱ�
 *  
 *  - Application �������� MemberDao ��ü�� ����	 1���� 
 *    �����ɼ� �ֵ��� �����Ѵ�.
 */
public class DeptDAO {
	//1. �ڽ��� �������� ���� private static �ʵ� �����
	private static DeptDAO dao;
	//2. �ܺο��� ��ü �����Ҽ� ������ �������� ���������ڸ�
	//   private �� ���� 
	private DeptDAO() {}
	//3. �ڽ��� �������� �������ִ� static �ɹ� �޼ҵ� ����� 
	public static DeptDAO getInstance() {
		if(dao==null) {
			dao=new DeptDAO();
		}
		return dao;
	}
	
	
	// DB �� ȸ�� ����� �������ִ� �޼ҵ�
	public List<DeptDTO> getList(){
		Connection conn=null;
		//�ʿ��� ��ü�� ���� ���� ����� 
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//MemberDto ��ü�� ���� ArrayList ��ü����
		List<DeptDTO> list=new ArrayList<>();
		try{
			conn=new DBConnect().getConn();
			//������ sql �� �غ� 
			String sql="SELECT empno,ename, dname, loc FROM EMP, DEPT WHERE EMP.DEPTNO=DEPT.DEPTNO"
					+ "ORDER BY EMP.DEPTNO ASC";
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
				//MemberDto ��ü�� ȸ������ ���
				DeptDTO dto=new DeptDTO(empno, ename, dname, loc);
				//ArrayList ��ü�� MemberDto ��� 
				list.add(dto);
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
		
		return list;
	}
	
}