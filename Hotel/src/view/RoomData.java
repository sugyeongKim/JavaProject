package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import assets.DBConnectionMgr;

public class RoomData {
	public RoomData() {
		// TODO Auto-generated constructor stub
	}
	
	public void insertRoom(int num, String name) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		
		String sql = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
		
		sql = " select * from ROOM where NUM='"+num+"' ";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		if(!rs.next()) {
		con = pool.getConnection();
		///sql = "insert into room (NUM, CHK, USER_NAME, IN_DATE, USE_TERM, SERVICE_PRICE) values ('"+type+"','"+num+"', '"+name+"', '"+gender+"', '"+age+"', '"+tel+"', '"+addr+"','"+amount+"');";
		pstmt = con.prepareStatement(sql);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
