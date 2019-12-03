package view;
import java.awt.Rectangle; 
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import assets.DBConnectionMgr;

public class StaffDAO extends JPanel{
    
    // DB에서 스윙 화면으로 테이블 값 가져오기(select) , 저장하기(insert), 수정하기(update), 삭제하기(delete)
        private static final long serialVersionUID = 1L;
        private JButton jBtnAddRow = null;    // 테이블 한줄 추가 버튼
        private JButton jBtnShowRow = null;
        private JButton jBtnSaveRow = null;    // 테이블 한줄 저장 버튼
        private JButton jBtnEditRow = null;    // 테이블 한줄 저장 버튼
        private JButton jBtnDelRow = null;        // 테이블 한줄 삭제 벅튼
        private JTable table;    
        private JScrollPane scrollPane;        // 테이블 스크롤바 자동으로 생성되게 하기

        //private String driver = "oracle.jdbc.driver.OracleDriver";        
              // @호스트 IP : 포트 : SID
        private String colNames[] = {"사원번호","이름","성별","나이","연락처","주소","역할"};  // 테이블 컬럼 값들
        private DefaultTableModel model = new DefaultTableModel(colNames, 0); //  테이블 데이터 모델 객체 생성
                
        DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;       //연결
	    PreparedStatement pstmt = null; //명령
	    ResultSet rs = null;         //결과
	    String sql = null;   // 리턴받아 사용할 객체 생성 ( select에서 보여줄 때 필요 )

        public StaffDAO() {
        	setLayout(null);  
 		   // 레이아웃 배치관리자 삭제
 		   JFrame win = new JFrame();
 		   win.setTitle("회원 목록");
 		   win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 		   win.add(this);
 		   win.setSize(915,400);
 		   win.setVisible(true);
 		   
            table = new JTable(model);  // 테이블에 모델객체 삽입
            table.addMouseListener(new JTableMouseListener());        // 테이블에 마우스리스너 연결
            scrollPane = new JScrollPane(table);            // 테이블에 스크롤 생기게 하기
            scrollPane.setSize(900, 200);
            add(scrollPane);        
            initialize();    
            select();
        }
        
        private class JTableMouseListener implements MouseListener{    // 마우스로 눌려진값확인하기
            public void mouseClicked(java.awt.event.MouseEvent e) {    // 선택된 위치의 값을 출력
                
                JTable jtable = (JTable)e.getSource();
                int row = jtable.getSelectedRow();                // 선택된 테이블의 행값
                int col = jtable.getSelectedColumn();         // 선택된 테이블의 열값
            
                System.out.println(model.getValueAt(row, col));   // 선택된 위치의 값을 얻어내서 출력
                    
            }
            public void mouseEntered(java.awt.event.MouseEvent e) {}
            public void mouseExited(java.awt.event.MouseEvent e) {}
            public void mousePressed(java.awt.event.MouseEvent e) {}
            public void mouseReleased(java.awt.event.MouseEvent e) {}
        }
                
        private void select(){        // 테이블에 보이기 위해 검색
            
            String query = "select * from staff order by number asc;";     
		     
            try{
                //Class.forName(driver);
                con = pool.getConnection();
                pstmt = con.prepareStatement(query);
                rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
                
                while(rs.next()){            // 각각 값을 가져와서 테이블값들을 추가
                    model.addRow(new Object[]{rs.getString("NUMBER"),rs.getString("NAME"),
                                                            rs.getString("GENDER"),rs.getString("AGE"),
                                                            rs.getString("TEL"),rs.getString("ADDR"),
                                                            rs.getString("POSITION")});
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }finally{
                try {
                    rs.close(); 
                    pstmt.close(); 
                    con.close();   // 객체 생성한 반대 순으로 사용한 객체는 닫아준다.
                } catch (Exception e2) {}
            }
        }
    
        private void initialize() {            // 액션이벤트와 버튼 컴포넌트 설정
        	
            jBtnShowRow = new JButton();
            jBtnShowRow.addActionListener(new ActionListener() {   
                public void actionPerformed(ActionEvent e) {
                    System.out.println(e.getActionCommand());        // 선택된 버튼의 텍스트값 출력
                    DefaultTableModel model2 = (DefaultTableModel)table.getModel();
                    model2.setRowCount(0);         // 전체 테이블 화면을 지워줌
                    select();  
                }
            });
            jBtnShowRow.setBounds(30,222,110,30);
            jBtnShowRow.setText("조회");
            add(jBtnShowRow);

        // 테이블 새로 한줄 추가하는 부분
        	jBtnAddRow = new JButton();
            jBtnAddRow.addActionListener(new ActionListener() {   
                public void actionPerformed(ActionEvent e) {
                    System.out.println(e.getActionCommand());        // 선택된 버튼의 텍스트값 출력
                    DefaultTableModel model2 = (DefaultTableModel)table.getModel();
                    model2.addRow(new String[]{"","","","","","",""});  // 새테이블의 초기값
                }
            });
            jBtnAddRow.setBounds(150,222,110,30);
            jBtnAddRow.setText("추가");
            add(jBtnAddRow);
            
        // 테이블 새로 저장하는 부분
            jBtnSaveRow = new JButton();
            jBtnSaveRow.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println(e.getActionCommand());        // 선택된 버튼의 텍스트값 출력
                    DefaultTableModel model2 = (DefaultTableModel)table.getModel();
                    int row = table.getSelectedRow();
                    if(row<0) return;     // 선택이 안된 상태면 -1리턴
                    String query = "insert into STAFF (NUMBER, NAME, GENDER, AGE, TEL, ADDR, POSITION)" 
                          + "values (?,?,?,?,?,?,?)"; 

                    try{
                        //Class.forName(driver);  // 드라이버 로딩
                        con = pool.getConnection();
                        pstmt = con.prepareStatement(query);
                        //rs = pstmt.executeQuery(); 
                        
                        // 물음표가 4개 이므로 4개 각각 입력해줘야한다.
                        pstmt.setInt(1, Integer.valueOf((String) model2.getValueAt(row, 0)));
                        pstmt.setString(2, String.valueOf(model2.getValueAt(row, 1)));
                        pstmt.setString(3, String.valueOf (model2.getValueAt(row, 2)));
                        pstmt.setInt(4,  Integer.valueOf((String)model2.getValueAt(row, 3)));
                        pstmt.setString(5, String.valueOf (model2.getValueAt(row, 4)));
                        pstmt.setString(6, String.valueOf (model2.getValueAt(row, 5)));
                        pstmt.setString(7, String.valueOf (model2.getValueAt(row, 6)));
                        //pstmt.setString(8, String.valueOf (model2.getValueAt(row, 7)));
                        	//뭐야 마지막 왜 안들어가
                        int cnt = pstmt.executeUpdate();
                        //pstmt.executeUpdate(); create insert update delete 
                        //pstmt.executeQuery(); select 
                    }catch(Exception eeee){
                        System.out.println(eeee.getMessage());
                    }finally{
                        try {
                            pstmt.close();
                            con.close();
                        } catch (Exception e2) {}
                    }
                    model2.setRowCount(0);         // 전체 테이블 화면을 지워줌
                    select();          // 저장 후 다시 전체 값들을 받아옴.
                }
            });
            jBtnSaveRow.setBounds(270,222,110, 30);
            jBtnSaveRow.setText("저장");
            add(jBtnSaveRow);
            
            
            // 선택된 테이블 한줄 수정하는 부분
            jBtnEditRow = new JButton();
            jBtnEditRow.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {        
                            
                    System.out.println(e.getActionCommand());// 선택된 버튼의 텍스트값 출력
                    DefaultTableModel model2 = (DefaultTableModel)table.getModel();
                    int row = table.getSelectedRow();
                    if(row<0) return;     // 선택이 안된 상태면 -1리턴
 
                    String query = "UPDATE STAFF SET NUMBER=?, NAME=?, GENDER=?, AGE=?, "
                    		+ "TEL=?, ADDR=?, POSITION=? where NUMBER=?";
                    
                    try{
                        con = pool.getConnection();
                        pstmt = con.prepareStatement(query);
                        
                        pstmt.setInt(1, Integer.valueOf((String) model2.getValueAt(row, 0)));
                        pstmt.setString(2, String.valueOf(model2.getValueAt(row, 1)));
                        pstmt.setString(3, String.valueOf (model2.getValueAt(row, 2)));
                        pstmt.setInt(4,  Integer.valueOf((String)model2.getValueAt(row, 3)));
                        pstmt.setString(5, String.valueOf (model2.getValueAt(row, 4)));
                        pstmt.setString(6, String.valueOf (model2.getValueAt(row, 5)));
                        pstmt.setString(7, String.valueOf (model2.getValueAt(row, 6)));
                        //pstmt.setString(8, String.valueOf (model2.getValueAt(row, 7)));

                        int cnt = pstmt.executeUpdate();
                        //pstmt.executeUpdate(); create insert update delete 
                        //pstmt.executeQuery(); select 
                    }catch(Exception eeee){
                        System.out.println(eeee.getMessage());
                    }finally{
                        try {
                            pstmt.close();
                            con.close();
                        } catch (Exception e2) {}
                    }
                    model2.setRowCount(0);         // 전체 테이블 화면을 지워줌
                    select();          // 수정 후다시 전체 값들을 받아옴.
                }
            });
            jBtnEditRow.setBounds(30,270,110, 30);
            jBtnEditRow.setText("수정");
            add(jBtnEditRow);

            
        // 선택된 테이블 한줄 삭제하는 부분
            jBtnDelRow = new JButton();
            jBtnDelRow.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.out.println(e.getActionCommand());        // 선택된 버튼의 텍스트값 출력
                    DefaultTableModel model2 = (DefaultTableModel)table.getModel();
                    int row = table.getSelectedRow();
                    if(row<0) return; // 선택이 안된 상태면 -1리턴
                    String query = "delete from STAFF where NUMBER= ?";
                   
                    try{
                        //Class.forName(driver);  // 드라이버 로딩
                        con = pool.getConnection();
                        pstmt = con.prepareStatement(query);
                        
                        // 물음표가 1개 이므로 4개 각각 입력해줘야한다.
                        pstmt.setString(1, (String) model2.getValueAt(row, 0));
                        int cnt = pstmt.executeUpdate();
                        //pstmt.executeUpdate(); create insert update delete 
                        //pstmt.executeQuery(); select 
                    }catch(Exception eeee){
                        System.out.println(eeee.getMessage());
                    }finally{
                        try {
                            pstmt.close();con.close();
                        } catch (Exception e2) {}
                    }
                    model2.removeRow(row);    // 테이블 상의 한줄 삭제
                }
            });
            jBtnDelRow.setBounds(new Rectangle(150, 270, 110, 30));
            jBtnDelRow.setText("삭제");
            add(jBtnDelRow);
        }
        
    public static void main(String[] args) {
        new StaffDAO();
    }
    
    
    public int insertData(int number, String name, String gender, int age, String tel, String addr, String position, String department) throws Exception {

		DBConnectionMgr pool = DBConnectionMgr.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		
		con = pool.getConnection();
		sql = " select * from member where number='"+number+"' ";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		if(!rs.next()) {
		con = pool.getConnection();
		sql = "insert into STAFF (NUMBER, NAME, GENDER, AGE, TEL, ADDR, POSITION)"
				+ " values ('"+number+"', '"+name+"', '"+gender+"', '"+age+"', '"+tel+"', '"+addr+"','"+position+"');";
		pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
		}
		else {
			JOptionPane.showMessageDialog(null, "이미 사원이 존재합니다", "채용 불가", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("이미 사원이 존재합니다");
			return 0;
		}
    }
}