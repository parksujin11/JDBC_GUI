
package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.dto.DeptDTO;
import test.util.DBConnect;

/*
 *  Dao => Data Access Object 의 약자
 *  
 *  - 회원 정보에 대해서 SELECT , INSERT, UPDATE, DELETE 
 *    작업을 수행할 객체를 생성하기위한 클래스 설계하기
 *  
 *  - Application 전역에서 MemberDao 객체는 오직	 1개만 
 *    생성될수 있도록 설계한다.
 */
public class DeptDAO {
	//1. 자신의 참조값을 담을 private static 필드 만들기
	private static DeptDAO dao;
	//2. 외부에서 객체 생성할수 없도록 생성자의 접근지정자를
	//   private 로 지정 
	private DeptDAO() {}
	//3. 자신의 참조값을 리턴해주는 static 맴버 메소드 만들기 
	public static DeptDAO getInstance() {
		if(dao==null) {
			dao=new DeptDAO();
		}
		return dao;
	}
	
	
	// DB 의 회원 목록을 리턴해주는 메소드
	public List<DeptDTO> getList(){
		Connection conn=null;
		//필요한 객체를 담을 변수 만들기 
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//MemberDto 객체를 담을 ArrayList 객체생성
		List<DeptDTO> list=new ArrayList<>();
		try{
			conn=new DBConnect().getConn();
			//실행할 sql 문 준비 
			String sql="SELECT empno,ename, dname, loc FROM EMP, DEPT WHERE EMP.DEPTNO=DEPT.DEPTNO"
					+ "ORDER BY EMP.DEPTNO ASC";
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
				//MemberDto 객체에 회원정보 담기
				DeptDTO dto=new DeptDTO(empno, ename, dname, loc);
				//ArrayList 객체에 MemberDto 담기 
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