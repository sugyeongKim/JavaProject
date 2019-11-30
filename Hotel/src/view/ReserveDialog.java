package view;


import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
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
import javax.swing.plaf.metal.MetalBorders.OptionDialogBorder;

import assets.DBConnectionMgr;
import assets.Setting;

public class ReserveDialog extends JFrame{
	private static final long serialVersionUID = 1L;
	private insertLogin insert;
	public ReserveDialog() {
		super("객실 예약");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btn  = new JButton("Show Dialog");

		insert = new insertLogin(this, "정보 입력");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert.setVisible(true);
			}
		});
		getContentPane().add(btn);
		setSize(250,200);
		setVisible(true);
	}
	public static void main(String[] ar) {
		new ReserveDialog();
	}
}


class insertLogin extends JDialog{
	private static final long serialVersionUID = 1L;
	private int roomNum;
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
	BufferedImage img = null;
	 
	public insertLogin(JFrame frame, String title) {
		super(frame,title);
		
		setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 400, 300);
        layeredPane.setLayout(null);
        setLocation(Setting.locationX, Setting.locationY);
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
        
       // backGround.setBounds(0, 0, 400, 200);
       // add(backGround, new Integer(0));
        
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

        layeredPane.add(name);layeredPane.add(nameLabel);
        layeredPane.add(age);layeredPane.add(ageLabel);
        layeredPane.add(gender);layeredPane.add(genderLabel);
        layeredPane.add(addr);layeredPane.add(addrLabel);
        layeredPane.add(tel);layeredPane.add(telLabel);
        
        
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
		

		insertButton.setBorderPainted(false);
		insertButton.setFocusPainted(false);
		insertButton.setContentAreaFilled(false);

		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int i=insertData(roomNum, name.getText(), gender.getText(), 
							Integer.parseInt(age.getText()), tel.getText(), addr.getText(), Integer.parseInt(amountLabel.getText()));
					if(i>0) {
						JOptionPane.showMessageDialog(null, "예약이 되었습니다!", "예약 성공", JOptionPane.INFORMATION_MESSAGE);
						System.out.println("예약 성공");
					}
					else {
						System.out.println("예약 실패");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
				
			}
		});
		layeredPane.add(panel);
        add(layeredPane);
        setLocation(Setting.locationX, Setting.locationY);
		setSize(400, 300);
	}
	public int insertData(int num, String name, String gender, int age, String tel, String addr, int amount) throws Exception {

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
		sql = "insert into member (NUM, NAME, GENDER, AGE, TEL, ADDR, TOTAL_AMOUNT) values ('"+num+"', '"+name+"', '"+gender+"', '"+age+"', '"+tel+"', '"+addr+"','"+amount+"');";
		pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
		}
		else {
			JOptionPane.showMessageDialog(null, "이미 예약이 되어있는 방입니다!", "예약 불가", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("이미 예약이 되어있는 방입니다!");
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
		if(onedayBox.isSelected()) 
			money = "10000";
		else if (twodayBox.isSelected())
			money = "15000";
		else if (threedayBox.isSelected())
			money = "20000";
		else
			money = "25000";
		amountLabel.setText(money);
	}
	
}

}

