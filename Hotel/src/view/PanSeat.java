package view;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import assets.DBConnectionMgr;
 //객실 그림
 //일반호실
public class PanSeat extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private insertReserv insert;
	private StatusReserv status;
	private BufferedImage img = null;
	private static String bname="off";
	public  JButton seatButton = new JButton(bname);
    private JLabel[] label = new JLabel[4];
    private int numSeat;
    
    DBConnectionMgr pool = DBConnectionMgr.getInstance();

	Connection con = null;
	PreparedStatement pstmt = null;
	
	String sql = null;
	ResultSet rs = null;
	
    public PanSeat() {}
    public PanSeat(int num) {
        this.numSeat = num;
        img("roomOff1");
        setLayout(null);
 
        JPanel panImg = new InnerPanel();
        panImg.setBounds(0, 0, 99, 99);
        
        seatButton.setForeground((Color.WHITE));
        seatButton.setBorderPainted(false);
        seatButton.setFocusPainted(false);
        seatButton.setContentAreaFilled(false);
        seatButton.setBounds(0, 0, 99, 99);
        
        this.add(seatButton);

		
        seatButton.addActionListener(new ActionListener() {
			@Override
			//이 부분을 room db를 체크해서 chk가 0이면 미사용, 1이면 사용중. 2는 탈퇴직후
			//room db는 초기화를 시켜놓고 프레임창을 닫을때 delete하도록 설계하자
			public void actionPerformed(ActionEvent e) {
				System.out.println("click: "+seatButton.getText());
				System.out.println("chk : "+getChk(num));
				// TODO Auto-generated method stub
				if(getChk(num) == 0) {
					insert = new insertReserv(frame, "일반 예약", numSeat);
					insert.visible();
					setonoff("on", "roomOn");
				    seatButton.setText(getName());
				}else if (getChk(num) == 2) {
					status.setChk(num,0);
					setonoff("off", "roomOff1");
				}else {
					seatButton.setForeground(new Color(128,128,128));
					status = new StatusReserv(frame, "일반 객실 상태", numSeat);
					status.visible();
				    seatButton.setText(getName());
				}
			}
		});
        
        //상태정보 패널
        JPanel panContent = new JPanel();
        panContent.setLayout(null);
        panContent.setBounds(0, 0, 99, 99);
        int posLabel = 10;
        for (int i = 0; i < 4; i++) {
            if (i == 0)
                label[i] = new JLabel(numSeat + "호");
            else
                label[i] = new JLabel("");
 
            label[i].setBounds(24, posLabel, 80, 15);
            posLabel += 16;
            label[i].setForeground(new Color(255,255,255));
            label[i].setFont(new Font("배달의민족 주아체", 1, 16));
            panContent.add(label[i]);
        }
        panContent.setOpaque(false);
         
        //제이레이어패널
        JLayeredPane panLayered = new JLayeredPane();
        panLayered.setBounds(0, 0, 1600, 900);
        panLayered.setLayout(null);
        panLayered.setOpaque(false);
        panLayered.add(panImg, new Integer(0), 0);
        panLayered.add(panContent, new Integer(1), 0);
      
        add(panLayered);
         
        setVisible(true);
        setOpaque(false);
        setFocusable(true);
    }
 
    public static void main(String[] args) {
        JFrame frameTest = new JFrame();
        frameTest.setTitle("시트패널");
        frameTest.add(new PanSeat(1));
        frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameTest.setSize(99, 144);
        frameTest.setVisible(true);
    }
    
    class InnerPanel extends JPanel {
        private static final long serialVersionUID = 1547128190348749556L;
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(img, 0, 0, null);
        }
    }
    
    public BufferedImage img(String filename) {
        // 이미지 받아오기 - gameOn, gameOff (로그인, 로그오프)
        try {
            img = ImageIO.read(new File("img/" + filename + ".png"));
        } catch (IOException e) {
            System.out.println("이미지 불러오기 실패!");
            System.exit(0);
        }
        repaint();
		return img;
    }
    //이름가져오기?
    
    public int getChk(int num) {
    	pool = DBConnectionMgr.getInstance();
    	con = null;	pstmt = null;
    	sql = null;	rs = null;
    	
	try {
		con = pool.getConnection();
		sql = " select * from room where NUM="+num+" ";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			return Integer.parseInt(rs.getString("chk"));
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return 0;
}
    
    public void setonoff(String status, String fname) {
    	System.out.println(status+","+fname);
        bname=status;
        System.out.println(status+","+fname+"@@");
    	img(fname);
    }    
}