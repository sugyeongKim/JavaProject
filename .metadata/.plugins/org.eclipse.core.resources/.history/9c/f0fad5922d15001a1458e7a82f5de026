package control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import assets.DBConnectionMgr;

public class DaoLogin {
	public static void main(String[] args) {
		boolean test = loginCheck("test", "1234");
		System.out.println("로그인 결과 : "+test);
	}
	
	public  int insertUser() throws Exception {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;

		con = pool.getConnection();
		sql = "select * from member_test where id='test'";
		pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(!rs.next()){
			System.out.println("비어있는 값");
			sql = "insert into member_test (id, password) values ('test', '1234');";
			pstmt = con.prepareStatement(sql);
			return pstmt.executeUpdate();
		}
		return 0;
	}
	
	public static boolean loginCheck(String id, String passwrord) {
		boolean flag = false;
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String sql = null;
		String getPass = null;
		
		try {
			con = pool.getConnection();
			
			//문장생성
			sql = "select password from MEMBER_TEST where id=?";
			
			//문장연결, 열차준비
			pstmt = con.prepareStatement(sql);
			
			//빈칸 채워주기
			pstmt.setString(1, id);
			
			//실행, 열차 출발
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//패스워드를 읽어온다.
				getPass = rs.getString("password");
				
				//DB에서 읽어온 pw와 입력 pw가 같냐?
				if(getPass.equals(passwrord)) {
					System.out.println("받아온 비밀번호 : "+getPass);
					flag = true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원 반납
			pool.freeConnection(con, pstmt, rs);
		}
		
//		결과 반납
		return flag;
		
	}
}
