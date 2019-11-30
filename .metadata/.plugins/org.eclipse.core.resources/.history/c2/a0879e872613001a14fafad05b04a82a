package view;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
 //객실 그림
 //일반호실
public class VipSeat extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private insertLogin insert = new insertLogin(frame, "정보 입력");
	private BufferedImage img = null;
	private JButton seatButton = new JButton();
    private JLabel[] label = new JLabel[4];
    private int numSeat;
    
    public VipSeat() {}
    public VipSeat(int numSeat) {
        this.numSeat = numSeat;
        img("VipRoomOff");
        setLayout(null);
 
        JPanel panImg = new InnerPanel();
        panImg.setBounds(0, 0, 99, 99);
        
        
        seatButton.setBorderPainted(false);
        seatButton.setFocusPainted(false);
        seatButton.setContentAreaFilled(false);
        seatButton.setBounds(0, 0, 99, 99);
        
        this.add(seatButton);
        
        seatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(VipSeat.this.numSeat);
				insert.setNum(VipSeat.this.numSeat+1);
				insert.setVisible(true);
				img("VipRoomOn");
			}
		});
        /*seatButton[numSeat].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				img("roomOn");
			}
		});
        */
        //상태정보 패널
        JPanel panContent = new JPanel();
        panContent.setLayout(null);
        panContent.setBounds(0, 0, 99, 99);
        int posLabel = 10;
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
            	label[i] = new JLabel("300"+(numSeat + 1) + "호");
                if (numSeat+1==10)
                	label[i] = new JLabel("30"+(numSeat + 1) + "호");
                if(numSeat+1>10)
                	label[i]= new JLabel("40"+(numSeat+1)+"호");
                if(numSeat+1==20)
                	label[i]= new JLabel("40"+(numSeat+1)+"호");
            }
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
        frameTest.add(new VipSeat(1));
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

    
    
}