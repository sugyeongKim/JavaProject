
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import assets.DBConnectionMgr;


public class memberView extends JFrame implements MouseListener,ActionListener{
	Vector v;  
    Vector cols;
    DefaultTableModel model;
    JTable jTable;
    JScrollPane pane;
    JPanel pbtn;
    JButton btnInsert;
    
    public memberView() {
    	super("text");

	     setSize(600,200);
	     setVisible(true);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     
			memberDao dao = new memberDao();
			v=dao.getMemberList();
			System.out.println("v="+v);
			cols=getColumn();
			
			model = new DefaultTableModel(v, cols);
			 jTable = new JTable(model);
		     pane = new JScrollPane(jTable);
		     add(pane);
		     
		    pbtn = new JPanel();
		    btnInsert = new JButton("회원가입");
		    pbtn.add(btnInsert);
		    add(pbtn,BorderLayout.NORTH);
		     
		     jTable.addMouseListener(this); //리스너 등록
		     
		}
    
    public Vector getColumn(){
        Vector<String> col = new Vector();
        col.add("아이디");
        col.add("비밀번호");
        col.add("이름");
        col.add("전화");
        col.add("주소");
        col.add("생일");
        col.add("직업");
        col.add("성별");
        col.add("이메일");
        col.add("자기소개");
       
        return col;
    }//getColumn
    
	public static void main() {
		new memberView();
	}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}



public class memberDao{

public Vector getMemberList() {
	Vector data = new Vector(); 
	
	DBConnectionMgr pool = DBConnectionMgr.getInstance();

	   Connection con = null;       //연결
       PreparedStatement ps = null; //명령
       ResultSet rs = null;         //결과
       
       String sql = null;

       
	try {
		
		con = pool.getConnection();
        sql = "select * from member order by num asc";
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while(rs.next()){
        	String num = rs.getString("num");
            String name = rs.getString("name");
            String tel = rs.getString("tel");
            String addr = rs.getString("addr");
            String age = rs.getString("age");
            String gender = rs.getString("gender");
        	String mileage = rs.getString("mileage");
        	
        	Vector row = new Vector();
            row.add(num);
            row.add(name);
            row.add(tel);
            row.add(addr);
            row.add(age);
            row.add(gender);
            row.add(mileage);
        }
	} catch (Exception e) {
		// TODO: handle exception
		 e.printStackTrace();
	}
	
	 return data;
		}
	}
}