package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import assets.DBConnectionMgr;
//얘 수정해야댐 얘 잘 모르겠다
public class memberShow extends JPanel{
	 	private JTable table;   
	 	private JScrollPane scrollPane;  
	 	private JButton jBtnAddRow = null; 
	 	private JButton jBtnSaveRow = null;
	 	private JButton jBtnDelRow = null;
	 	
	 	
	 	private String colNames[] = {"호실","이름","성별","나이","전화번호","주소","마일리지"};
	 	private DefaultTableModel model = new DefaultTableModel(colNames, 0);
	 	
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;       //연결
	    PreparedStatement ps = null; //명령
	    ResultSet rs = null;         //결과
	    String sql = null;
	    
	   public memberShow() {
		   setLayout(null);  
		   // 레이아웃 배치관리자 삭제
		   JFrame win = new JFrame();
		   win.setTitle("회원 목록");
		   win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		   win.add(this);
		   win.setSize(510,400);
		   win.setVisible(true);

		   table = new JTable(model); 
		   table.addMouseListener(new JTableMouseListener());     
		   scrollPane = new JScrollPane(table);  
		   scrollPane.setSize(500, 200);
		   add(scrollPane);  
		   initialize(); 
		   select();

	   }
	   
	   private class JTableMouseListener implements MouseListener{    // 마우스로 눌려진값확인하기
           public void mouseClicked(java.awt.event.MouseEvent e) {    // 선택된 위치의 값을 출력
        	JTable jtable = (JTable)e.getSource();
        	int row = jtable.getSelectedRow();                // 선택된 테이블의 행값
        	int col = jtable.getSelectedColumn();         // 선택된 테이블의 열값
        	
        	System.out.println(model.getValueAt(row, col)); 
        	select();
           }// 선택된 위치의 값을 얻어내서 출력
        	@Override
        	public void mouseEntered(MouseEvent arg0) {
        	}
        	@Override
        	public void mouseExited(MouseEvent arg0) {
        		// TODO Auto-generated method stu
        	}
        	@Override
        	public void mousePressed(MouseEvent arg0) {

        	}
        	@Override
        	public void mouseReleased(MouseEvent arg0) {
        	}
 }

	   
	   public static void main(String[] args) {
		  new memberShow();
	}
	   private void select() {
		// TODO Auto-generated method stub
		 String query = "select * from member order by num asc;";    
		 
		 try {
			 con = pool.getConnection();
			 ps = con.prepareStatement(query);
		     rs = ps.executeQuery();
			 
		     while(rs.next()) {
		    	 model.addRow(new Object[] {rs.getString("num"),
		    		 rs.getString("name"),rs.getString("gender"),rs.getString("age"),
		    		 rs.getString("tel"),rs.getString("addr"),rs.getString("mileage")});
		     }
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				rs.close();
				ps.close();
				con.close();
			}catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	private void initialize() {            // 액션이벤트와 버튼 컴포넌트 설
		        // 테이블 새로 한줄 추가하는 부분
		            jBtnAddRow = new JButton();
		            jBtnAddRow.addActionListener(new ActionListener() {   
		                public void actionPerformed(ActionEvent e) {
		                    System.out.println(e.getActionCommand());        // 선택된 버튼의 텍스트값 출력
		                    DefaultTableModel model2 = (DefaultTableModel)table.getModel();
		                    model2.addRow(new String[]{"","","","",""});  // 새테이블의 초기값
		                }
		            });
		            jBtnAddRow.setBounds(180,222,110,30);
		            jBtnAddRow.setText("조회");
		            add(jBtnAddRow);
		            
		            jBtnDelRow = new JButton();
		            jBtnDelRow.addActionListener(new ActionListener() {
		                public void actionPerformed(java.awt.event.ActionEvent e) {
		                    System.out.println(e.getActionCommand());        // 선택된 버튼의 텍스트값 출력
		                    DefaultTableModel model2 = (DefaultTableModel)table.getModel();
		                    int row = table.getSelectedRow();
		                   // if(row<0) return; // 선택이 안된 상태면 -1리턴
		                    String query = "delete from member";

		                    try{
		                       // Class.forName(driver);  // 드라이버 로딩
		                    	con = pool.getConnection(); // DB 연결
		                    	ps = con.prepareStatement(query);  
		                        // 물음표가 1개 이므로 4개 각각 입력해줘야한다.
		               

		                    	int cnt = ps.executeUpdate();
		                        //pstmt.executeUpdate(); create insert update delete
		                        //pstmt.executeQuery(); select
		                    }catch(Exception eeee){
		                        System.out.println(eeee.getMessage());
		                    }finally{
		                        try {
		                        	ps.close();con.close();
		                        } catch (Exception e2) {}
		                    }
		                    model2.removeRow(row);    // 테이블 상의 한줄 삭제
		                }
		            });
		            jBtnDelRow.setBounds(320, 222, 110,30);
		            jBtnDelRow.setText("삭제");
		            add(jBtnDelRow);

	}
}