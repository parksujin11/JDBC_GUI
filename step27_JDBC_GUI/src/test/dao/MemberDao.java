
package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.dto.MemberDto;
import test.util.DBConnect;

/*
 *  Dao => Data Access Object 의 약자
 *  
 *  - 회원 정보에 대해서 SELECT , INSERT, UPDATE, DELETE 
 *    작업을 수행할 객체를 생성하기위한 클래스 설계하기
 *  
 *  - Application 전역에서 MemberDao 객체는 오직 1개만 
 *    생성될수 있도록 설계한다.
 */
public class MemberDao {
	//1. 자신의 참조값을 담을 private static 필드 만들기
	private static MemberDao dao;
	//2. 외부에서 객체 생성할수 없도록 생성자의 접근지정자를
	//   private 로 지정 
	private MemberDao() {}
	//3. 자신의 참조값을 리턴해주는 static 맴버 메소드 만들기 
	public static MemberDao getInstance() {
		if(dao==null) {
			dao=new MemberDao();
		}
		return dao;
	}
	
	// DB 에 회원정보를 저장하는 메소드
	public boolean insert(MemberDto dto) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		//작업의 성공여부를 담을 변수 
		boolean isSuccess=false;
		try {
			conn=new DBConnect().getConn();
			//실행할 sql 문
			String sql="INSERT INTO member (num,name,nickname) "
					+ "VALUES(member_seq.NEXTVAL,?,?)";
			pstmt=conn.prepareStatement(sql);
			// ? 에 값 바인딩하기 
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getNickname());
			
			// sql 문 수행하고 추가된 row 의 갯수 얻어오기
			int flag=pstmt.executeUpdate();
			if(flag>0) {//작업 성공이면
				isSuccess=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
			}catch(Exception e) {}
		}
		//성공 여부를 리턴 
		return isSuccess;
	}
	// DB 에 회원정보를 삭제하는 메소드
	public boolean delete(int num) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		//작업의 성공여부를 담을 변수 
		boolean isSuccess=false;
		try {
			conn=new DBConnect().getConn();
			//실행할 sql 문
			String sql="DELETE FROM member WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			// sql 문 수행하고 추가된 row 의 갯수 얻어오기
			int flag=pstmt.executeUpdate();
			if(flag>0) {//작업 성공이면
				isSuccess=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
			}catch(Exception e) {}
		}
		//성공 여부를 리턴 
		return isSuccess;
	}
	// DB 에 회원정보를 수정하는 메소드
	public boolean update(MemberDto dto) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		//작업의 성공여부를 담을 변수 
		boolean isSuccess=false;
		try {
			conn=new DBConnect().getConn();
			//실행할 sql 문
			String sql="UPDATE member SET name=?, nickname=? "
					+ "WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getNickname());
			pstmt.setInt(3, dto.getNum());
			// sql 문 수행하고 추가된 row 의 갯수 얻어오기
			int flag=pstmt.executeUpdate();
			if(flag>0) {//작업 성공이면
				isSuccess=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null)conn.close();
				if(pstmt!=null)pstmt.close();
			}catch(Exception e) {}
		}
		//성공 여부를 리턴 
		return isSuccess;
	}
	// DB 의 회원 목록을 리턴해주는 메소드
	public List<MemberDto> getList(){
		Connection conn=null;
		//필요한 객체를 담을 변수 만들기 
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//MemberDto 객체를 담을 ArrayList 객체생성
		List<MemberDto> list=new ArrayList<>();
		try{
			conn=new DBConnect().getConn();
			//실행할 sql 문 준비 
			String sql="SELECT num, name, addr FROM member";
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
				int  num=rs.getInt("num");
				String name=rs.getString("name");
				String addr=rs.getString("addr");
				//MemberDto 객체에 회원정보 담기
				MemberDto dto=new MemberDto(num, name, addr);
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
	//인자로 전달되는 번호에 해당하는 회원정보를 리턴해주는 메소드 
	public MemberDto getData(int num) {
		Connection conn=null;
		//필요한 객체를 담을 변수 만들기 
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//MemberDto 객체의 참조값을 담을 변수 만들기 
		MemberDto dto=null;
		try{
			conn=new DBConnect().getConn();
			//실행할 sql 문 준비 
			String sql="SELECT name, nickname FROM member"+"WHERE num=?";
			//PreparedStatement 객체의 참조값 얻어오기
			//(sql 문을 대신 수행해줄 객체)
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			//ResultSet 객체의 참조값 얻어오기 
			//(SELECT 문의 수행 결과 값을 가지고 있는 객체)
			rs=pstmt.executeQuery();
			//반복문 돌면서 cursor 를 한칸씩 내린다. 
			if(rs.next()){
				int num1=rs.getInt("num");
				String name=rs.getString("name");
				String addr=rs.getString("nickname");
				
				dto=new MemberDto();
				dto.setNum(num);
				dto.setName(name);
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
		return null;
	}
}

