
package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.dto.MemberDto;
import test.util.DBConnect;

/*
 *  Dao => Data Access Object �� ����
 *  
 *  - ȸ�� ������ ���ؼ� SELECT , INSERT, UPDATE, DELETE 
 *    �۾��� ������ ��ü�� �����ϱ����� Ŭ���� �����ϱ�
 *  
 *  - Application �������� MemberDao ��ü�� ���� 1���� 
 *    �����ɼ� �ֵ��� �����Ѵ�.
 */
public class MemberDao {
	//1. �ڽ��� �������� ���� private static �ʵ� �����
	private static MemberDao dao;
	//2. �ܺο��� ��ü �����Ҽ� ������ �������� ���������ڸ�
	//   private �� ���� 
	private MemberDao() {}
	//3. �ڽ��� �������� �������ִ� static �ɹ� �޼ҵ� ����� 
	public static MemberDao getInstance() {
		if(dao==null) {
			dao=new MemberDao();
		}
		return dao;
	}
	
	// DB �� ȸ�������� �����ϴ� �޼ҵ�
	public boolean insert(MemberDto dto) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		//�۾��� �������θ� ���� ���� 
		boolean isSuccess=false;
		try {
			conn=new DBConnect().getConn();
			//������ sql ��
			String sql="INSERT INTO member (num,name,nickname) "
					+ "VALUES(member_seq.NEXTVAL,?,?)";
			pstmt=conn.prepareStatement(sql);
			// ? �� �� ���ε��ϱ� 
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getNickname());
			
			// sql �� �����ϰ� �߰��� row �� ���� ������
			int flag=pstmt.executeUpdate();
			if(flag>0) {//�۾� �����̸�
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
		//���� ���θ� ���� 
		return isSuccess;
	}
	// DB �� ȸ�������� �����ϴ� �޼ҵ�
	public boolean delete(int num) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		//�۾��� �������θ� ���� ���� 
		boolean isSuccess=false;
		try {
			conn=new DBConnect().getConn();
			//������ sql ��
			String sql="DELETE FROM member WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			// sql �� �����ϰ� �߰��� row �� ���� ������
			int flag=pstmt.executeUpdate();
			if(flag>0) {//�۾� �����̸�
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
		//���� ���θ� ���� 
		return isSuccess;
	}
	// DB �� ȸ�������� �����ϴ� �޼ҵ�
	public boolean update(MemberDto dto) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		//�۾��� �������θ� ���� ���� 
		boolean isSuccess=false;
		try {
			conn=new DBConnect().getConn();
			//������ sql ��
			String sql="UPDATE member SET name=?, nickname=? "
					+ "WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getNickname());
			pstmt.setInt(3, dto.getNum());
			// sql �� �����ϰ� �߰��� row �� ���� ������
			int flag=pstmt.executeUpdate();
			if(flag>0) {//�۾� �����̸�
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
		//���� ���θ� ���� 
		return isSuccess;
	}
	// DB �� ȸ�� ����� �������ִ� �޼ҵ�
	public List<MemberDto> getList(){
		Connection conn=null;
		//�ʿ��� ��ü�� ���� ���� ����� 
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//MemberDto ��ü�� ���� ArrayList ��ü����
		List<MemberDto> list=new ArrayList<>();
		try{
			conn=new DBConnect().getConn();
			//������ sql �� �غ� 
			String sql="SELECT num, name, addr FROM member";
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
				int  num=rs.getInt("num");
				String name=rs.getString("name");
				String addr=rs.getString("addr");
				//MemberDto ��ü�� ȸ������ ���
				MemberDto dto=new MemberDto(num, name, addr);
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
	//���ڷ� ���޵Ǵ� ��ȣ�� �ش��ϴ� ȸ�������� �������ִ� �޼ҵ� 
	public MemberDto getData(int num) {
		Connection conn=null;
		//�ʿ��� ��ü�� ���� ���� ����� 
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//MemberDto ��ü�� �������� ���� ���� ����� 
		MemberDto dto=null;
		try{
			conn=new DBConnect().getConn();
			//������ sql �� �غ� 
			String sql="SELECT name, nickname FROM member"+"WHERE num=?";
			//PreparedStatement ��ü�� ������ ������
			//(sql ���� ��� �������� ��ü)
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			//ResultSet ��ü�� ������ ������ 
			//(SELECT ���� ���� ��� ���� ������ �ִ� ��ü)
			rs=pstmt.executeQuery();
			//�ݺ��� ���鼭 cursor �� ��ĭ�� ������. 
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

