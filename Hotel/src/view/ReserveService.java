package view;


import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import assets.DBConnectionMgr;
import assets.Setting;

public class ReserveService extends JFrame{
	private static final long serialVersionUID = 1L;
	private insertReserv insert;
	private StatusReserv status;
	//private 
	public ReserveService() {
		super("객실 예약");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btn  = new JButton("Show reserv");
		JButton btn2  = new JButton("Show status");

		//insert = new insertReserv(this, "정보 입력");
		//status = new StatusReserv(this, "객실 상태");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert.setVisible(true);
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status.setVisible(true);
			}
		});
		
		getContentPane().add(btn2);
		//getContentPane().add(btn2);
		setSize(250,200);
		setVisible(true);
	}
	public static void main(String[] ar) {
		new ReserveService();
	}
}


class insertReserv extends JDialog{
	private static final long serialVersionUID = 1L;
	PanSeat panSeat = new PanSeat();
	private int roomNum;
	private String typeString;
	private JTextField name = new JTextField(10);
	private JTextField age = new JTextField(15);
	private JTextField gender = new JTextField(15);
	private JTextField addr = new JTextField(15);
	private JTextField tel = new JTextField(15);
	private JButton insertButton = new JButton(new ImageIcon("img/reserv1.png"));
	
	private ButtonGroup group = new ButtonGroup();

	private JRadioButton onedayBox = new JRadioButton("1박");
	private JRadioButton twodayBox = new JRadioButton("2박");
	private JRadioButton threedayBox = new JRadioButton("3박");
	private JRadioButton fourdayBox = new JRadioButton("4박");
	
	private JLabel nameLabel = new JLabel("이름");
	private JLabel ageLabel = new JLabel("나이");
	private JLabel genderLabel = new JLabel("성별");
	private JLabel addrLabel = new JLabel("주소");
	private JLabel telLabel = new JLabel("전화번호");
	private JLabel dayLabel = new JLabel("<숙박일수>");
	private JLabel moneyLabel = new JLabel("금액:");
	private JLabel amountLabel = new JLabel();
	private JLabel roonumJLabel;
	
	BufferedImage img = null;
	boolean chk=false;
	int staydate;
	
    
	MyDocumentListener MyDocumentListener = new MyDocumentListener();
	public boolean visible() {
		this.setVisible(!chk);
		chk=!chk;
		return chk;
	}
	public insertReserv(JFrame frame, String title, int num) {
		super(frame,title);
		if(title.equals("일반 예약")) typeString="General";
		else typeString="Vip";
		
		setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 400, 300);
        layeredPane.setLayout(null);
        add(layeredPane);

        //배경
        MyPanel panel = new MyPanel();
        panel.setBounds(0, 0, 400, 300);
        
       /* try {
            img = ImageIO.read(new File("img/backgroundDB.png"));
        } catch (IOException e) {
            System.out.println("이미지 불러오기 실패");
            System.exit(0);
        }*/
        
        
        //개인정보 입력
        nameLabel.setBounds(50,20,50,20);
        name.setBounds(80,20,100,20);

        genderLabel.setBounds(200, 20, 50, 20);
        gender.setBounds(230, 20, 100, 20);
        
        ageLabel.setBounds(50, 50, 50, 20);
        age.setBounds(80, 50, 100, 20);
        
        addrLabel.setBounds(200, 50, 50, 20);
        addr.setBounds(230, 50, 100, 20);
        
        telLabel.setBounds(25, 80, 100, 20);
        tel.setBounds(80, 80, 100, 20);

        roonumJLabel=new JLabel(Integer.toString(num)+"호");
        roonumJLabel.setBounds(220, 80, 100, 20);
        
        layeredPane.add(name);layeredPane.add(nameLabel);
        layeredPane.add(age);layeredPane.add(ageLabel);
        layeredPane.add(gender);layeredPane.add(genderLabel);
        layeredPane.add(addr);layeredPane.add(addrLabel);
        layeredPane.add(tel);layeredPane.add(telLabel);
        layeredPane.add(roonumJLabel);

        name.getDocument().addDocumentListener(MyDocumentListener);
        age.getDocument().addDocumentListener(MyDocumentListener);
        addr.getDocument().addDocumentListener(MyDocumentListener);
        gender.getDocument().addDocumentListener(MyDocumentListener);
        addr.getDocument().addDocumentListener(MyDocumentListener);
        tel.getDocument().addDocumentListener(MyDocumentListener);
        
        //숙박일수 선택
        dayLabel.setBounds(25, 117, 80, 20);
        onedayBox.setBounds(25, 135, 50, 40);
        twodayBox.setBounds(75, 135, 50, 40);
        threedayBox.setBounds(125, 135, 50, 40);
        fourdayBox.setBounds(175, 135, 50, 40);
        moneyLabel.setBounds(25, 140, 40, 90);
        amountLabel.setBounds(60, 145, 70, 80);
        
        insertButton.setBounds(140, 205, 100, 30);
        
        group.add(onedayBox);group.add(twodayBox);
        group.add(threedayBox);group.add(fourdayBox);

		
        layeredPane.add(dayLabel); 
        layeredPane.add(onedayBox); onedayBox.addItemListener(new myItemListener());
        layeredPane.add(twodayBox); twodayBox.addItemListener(new myItemListener());
        layeredPane.add(threedayBox); threedayBox.addItemListener(new myItemListener());
        layeredPane.add(fourdayBox); fourdayBox.addItemListener(new myItemListener());
        layeredPane.add(moneyLabel); layeredPane.add(amountLabel);
        
        
        layeredPane.add(insertButton);
        insertButton.setEnabled(false);

        
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//db에 데이터 넣는 부분
					int i=insertData(typeString,num, name.getText(), gender.getText(), 
							Integer.parseInt(age.getText()), tel.getText(), addr.getText(), Integer.parseInt(amountLabel.getText()));
					
					if(i>0) {
						JOptionPane.showMessageDialog(null, "입실이 되었습니다!", "입실 성공", JOptionPane.INFORMATION_MESSAGE);
						chk=true;
						System.out.println("입실 성공");
						if(num<2000)
							panSeat.setonoff("on", "roomOn");
						else panSeat.setonoff("on", "VipRoomOn");
						textclear();
						//여기다 넣으면 되지 않을까 싶다
					}
					else {
						System.out.println("입실 실패");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				insertButton.setBorderPainted(false);
				insertButton.setFocusPainted(false);
				insertButton.setContentAreaFilled(false);
				
				setVisible(false);
			}
		});
		
		layeredPane.add(panel);
        add(layeredPane);
        setLocation(Setting.locationX, Setting.locationY);
		setSize(400, 300);
	}

	//텍스트필드에 입력을 했는지 안했는지..하지만 한군데만 입력해도 통과되버린다 우짜냐
    class MyDocumentListener implements DocumentListener{
		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			check(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			check(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			check(e);
		}
    	public void check(DocumentEvent e) {
    		if(e.getLength() == 0) insertButton.setEnabled(false);
    		else insertButton.setEnabled(true);
    	}
    }
    
	public boolean check() {
		return chk;
	}
    
    public void textclear() {
    	name.setText("");
    	age.setText("");
    	addr.setText("");
    	gender.setText("");
    	tel.setText("");
    }
	public int insertData(String type, int num, String name, String gender, int age, String tel, String addr, int amount) throws Exception {

		DBConnectionMgr pool = DBConnectionMgr.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;
		
		String sql = null;
		ResultSet rs = null;
		
		con = pool.getConnection();
		sql = " select * from member where NUM='"+num+"' ";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		if(!rs.next()) {
		con = pool.getConnection();
		sql = "insert into member (ROOM_TYPE, INSERTDATE, NUM, NAME, GENDER, AGE, TEL, ADDR, TOTAL_AMOUNT) values ('"+type+"',CURRENT_TIMESTAMP,'"+num+"', '"+name+"', '"+gender+"', '"+age+"', '"+tel+"', '"+addr+"','"+amount+"');";
		pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
		}
		else {
			JOptionPane.showMessageDialog(null, "이미 입실이 되어있는 방입니다!", "입실 불가", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("이미 입실이 되어있는 방입니다!");
			return 0;
		}
	}
	
	public int getNum() {
		return roomNum;
	}

	public void setNum(int numSeat) {
		// TODO Auto-generated method stub
		roomNum=numSeat;
	}
	
	class MyPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public void paint(Graphics g) {
	        g.drawImage(img, 0, 0, null);
	    }
	}
	
	class myItemListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			String money = "";
			// TODO Auto-generated method stub
			if(e.getStateChange() == ItemEvent.DESELECTED)
				return; 
		if(typeString.equals("General")) {
			if(onedayBox.isSelected()) {
				money = "10000";
				staydate=1;
			}
			else if (twodayBox.isSelected()) {
				money = "15000";
				staydate=2;
			}
			else if (threedayBox.isSelected()) {
				money = "20000";
				staydate=3;
			}
			else {
				money = "25000";
				staydate=4;
			}
			amountLabel.setText(money);
			}
		else {
			if(onedayBox.isSelected()) {
				money = "40000";
				staydate=1;
			}
			else if (twodayBox.isSelected()) {
				money = "50000";
				staydate=2;
			}
			else if (threedayBox.isSelected()) {
				money = "60000";
				staydate=3;
			}
			else {
				money = "70000";
				staydate=4;
			}
			amountLabel.setText(money);
			}
		}
	}
}

class StatusReserv extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int Num;
	PanSeat panSeat = new PanSeat();
	private JLabel nameLabel;
	private JLabel userLabel = new JLabel();
	private JLabel daymoneyLabel = new JLabel("현재 금액");
	private JLabel roomServiceLabel =  new JLabel("룸서비스");
	//private ButtonGroup group = new ButtonGroup();
	
	//룸서비스 메뉴들
	private JCheckBox[] foodBoxs = new JCheckBox[5];
	private String [] names = {"피자","치킨","빵","와인","스파게티"};
	private JLabel sumPriceJLabel;
	
	private JButton savePriceButton = new JButton("저장");
	private JButton outRoomButton = new JButton("퇴실하기");
	/*private JCheckBox pizzaButton = new JCheckBox("피자");
	private JCheckBox chickenButton = new JCheckBox("치킨");
	private JCheckBox breadButton = new JCheckBox("빵");
	private JCheckBox wineButton = new JCheckBox("와인");
	private JCheckBox spaghettiButton = new JCheckBox("스파게티");
*/
	checkItemListener listener = new checkItemListener();	
	//DB 사용하기위해서,,,
	DBConnectionMgr pool;
	Connection con;
	PreparedStatement pstmt;
	String sql;
	ResultSet rs;
	
	private int sum;
	
	public StatusReserv(JFrame frame, String title, int num) {
		super(frame,title);
		setLayout(null);
		setNum(num);
		sum=Integer.parseInt(getAmount(num));
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 400, 300);
        layeredPane.setLayout(null);
        add(layeredPane);

        //배경
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 400, 300);
        
        nameLabel  = new JLabel(getNum()+"호 사용자 "+getUser(getNum())+" 님");
        nameLabel.setBounds(20,15,200,20);
        //userLabel.setBounds(70,15,80, 20);
        roomServiceLabel.setBounds(20, 40, 80, 20);
			/*try {
				userLabel.setText(getUser(num));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		int x=20,w = 60;
		for(int i=0;i<foodBoxs.length;i++) {
			foodBoxs[i]= new JCheckBox(names[i]);
			foodBoxs[i].setBounds(x, 60, w, 20);
			x+=60;
			if(i==3) w+=20;
			layeredPane.add(foodBoxs[i]);
			foodBoxs[i].addItemListener(listener);
		}
		daymoneyLabel.setBounds(20, 100, 80, 20);
		sumPriceJLabel= new JLabel(Integer.toString(sum));
		sumPriceJLabel.setBounds(90, 100, 130, 20);
		savePriceButton.setBounds(150, 100, 65, 20);
		outRoomButton.setBounds(130, 150, 100, 20);
		
		//pizzaButton.setBounds(20, 60, 60, 20);
		//chickenButton.setBounds(80, 60, 60, 20);
		//breadButton.setBounds(140, 60, 60, 20);
		//wineButton.setBounds(190, 60, 60, 20);
		//spaghettiButton.setBounds(250, 60, 80, 20);
		
		//layeredPane.add(pizzaButton); layeredPane.add(chickenButton);
		//layeredPane.add(breadButton); layeredPane.add(wineButton);
		//layeredPane.add(spaghettiButton);
		
		layeredPane.add(savePriceButton);savePriceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addAmount(getNum());
				JOptionPane.showMessageDialog(null, "금액이 추가되었습니다", "룸서비스 사용", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		layeredPane.add(outRoomButton);outRoomButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				outRoom(getNum());
			}
		});
		
		layeredPane.add(daymoneyLabel);
		layeredPane.add(sumPriceJLabel);
        layeredPane.add(roomServiceLabel);
        layeredPane.add(nameLabel);
        //layeredPane.add(userLabel);
        
        layeredPane.add(panel);
        add(layeredPane);
        setLocation(Setting.locationX, Setting.locationY);
		setSize(400, 300);
	}
	
	class checkItemListener implements ItemListener{
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange() == ItemEvent.SELECTED) {
				if(e.getItem() == foodBoxs[0])//피자
					sum+=26000;
				else if(e.getItem() == foodBoxs[1]) //치킨
					sum+=27000;
				else if(e.getItem() == foodBoxs[2])//빵
					sum+=21000;
				else if(e.getItem() == foodBoxs[3])	//와인
					sum+=55000;
				else sum+=34000;//스파게티
			}
			else {
				if(e.getItem() == foodBoxs[0]) 
					sum-=26000;
				else if(e.getItem() == foodBoxs[1])
					sum-=27000;
				else if(e.getItem() == foodBoxs[2])
					sum-=21000;
				else if(e.getItem() == foodBoxs[3])
					sum-=55000;
				else sum-=34000;
			}
			sumPriceJLabel.setText(Integer.toString(sum));
		}
		
	}
	
	public void addAmount(int num) {
		pool = DBConnectionMgr.getInstance();
		con = null;	pstmt = null;
		sql = null;	rs = null;
		try {
			con = pool.getConnection();
			sql = "UPDATE MEMBER SET TOTAL_AMOUNT = "+sum+" where NUM = "+num+"";
			pstmt = con.prepareStatement(sql);
			int cnt = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void outRoom(int num) {
		pool = DBConnectionMgr.getInstance();
		con = null;	pstmt = null;
		sql = null;	rs = null;
		try {
			con = pool.getConnection();
			sql = "delete from MEMBER where NUM = "+num+"";
			pstmt = con.prepareStatement(sql);
			int cnt = pstmt.executeUpdate();
			if (cnt > 0) {
				JOptionPane.showMessageDialog(null, "퇴실 처리 되었습니다", "퇴실 처리", JOptionPane.CLOSED_OPTION);
				if(num < 2000) {
					panSeat.setonoff("off", "roomOff1");
					panSeat.setName("off");
				}
				else panSeat.setonoff("off", "VipRoomOff");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getUser(int num){
		pool = DBConnectionMgr.getInstance();
		con = null;
		pstmt = null;
		
		sql = null;
		rs = null;
		
		try {
			con = pool.getConnection();
			sql = " select * from member where NUM="+num+" ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//System.out.println(rs.getString("name"));
				return rs.getString("name");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "x"; 
	}
	
	public String getAmount(int num) {
		pool = DBConnectionMgr.getInstance();
		con = null;
		pstmt = null;
		
		sql = null;
		rs = null;
		
		try {
			con = pool.getConnection();
			sql = " select * from member where NUM="+num+" ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//System.out.println(rs.getString("name"));
				return rs.getString("TOTAL_AMOUNT");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "xx"; 
	}
	
	public int getNum() {
		System.out.println(Num);
		return Num;
	}
	
	public void setNum(int num) {
		Num=num;
	}
}

