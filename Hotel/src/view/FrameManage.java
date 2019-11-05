package view;
 
import java.awt.Component;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import view.panel.PanImgload;
import assets.Setting;
 
@SuppressWarnings("serial")
public class FrameManage extends JFrame implements ActionListener{
    public JLayeredPane layeredPane = new JLayeredPane();
    private PanImgload backGround = new PanImgload("img/backgroundRoom.png");
    int posXpanSeat, posYpanSeat;
    PanSeat[] pan = new PanSeat[34];
    JPanel seat50 = new JPanel();
    JButton[] seatButton = new JButton[34];
    JButton memberButton = new JButton(new ImageIcon("img/member.png"));
    JButton moneyButton = new JButton(new ImageIcon("img/money.png"));
    JButton staffButton = new JButton(new ImageIcon("img/staff.png"));
    
    ImageIcon memberpress = new ImageIcon("img/memberclick.gif");
    ImageIcon moneypress = new ImageIcon("img/moneyclick.gif");
    ImageIcon staffpress = new ImageIcon("img/staffclick.gif");

    public FrameManage() {
        // Configure this Frame
        setLayout(null);
        setVisible(true);
        setTitle("객실 현황");
        setSize(Setting.bDimen);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(Setting.locationX, Setting.locationY);
        //버튼 투명처리
        
        memberButton.setBorderPainted(false);
        memberButton.setFocusPainted(false);
        memberButton.setContentAreaFilled(false);
        
        moneyButton.setBorderPainted(false);
        moneyButton.setFocusPainted(false);
        moneyButton.setContentAreaFilled(false);
        
        staffButton.setBorderPainted(false);
        staffButton.setFocusPainted(false);
        staffButton.setContentAreaFilled(false);
        
        
        // 좌석 패널 시작 시작점 165 129
 
        seat50.setLayout(null);
        seat50.setOpaque(false);
        seat50.setBounds(15, 129, 1368, 686);
        for ( int seat = 0; seat < 34; seat++) {
            pan[seat] = new PanSeat(seat);       
            if (seat == 10  && seat != 0 || seat == 17 ||seat == 27) {
                posXpanSeat = 0;
                posYpanSeat += 140;
            }
            
            pan[seat].setBounds(posXpanSeat, posYpanSeat, 99, 99);
            //seatButton[seat].setBounds(posXpanSeat, posYpanSeat, 99, 99);
            posXpanSeat += 135;
            seat50.add(pan[seat]);
            //seat50.add(seatButton[seat]);
            /*seatButton[seat].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println(numSeat);
				}
			});*/
        }
        System.out.println(pan[0].getX()+","+pan[0].getY());
        System.out.println(pan[27].getX()+","+pan[27].getY());
        //560
        add(setJLayered(backGround, seat50, memberButton, moneyButton, staffButton));
        add(layeredPane);
        
        memberButton.setPressedIcon(memberpress);
        moneyButton.setPressedIcon(moneypress);
        staffButton.setPressedIcon(staffpress);
        
        memberButton.addActionListener(this);
        moneyButton.addActionListener(this);
        staffButton.addActionListener(this);
        }

    public static void main(String[] args) throws Exception {
        FrameManage manageView = new FrameManage();
        manageView.setRectangles(FrameManage.class, manageView, Setting.class, Setting.getInstance());
    }
 
    // Setting inner Methods
    private JComponent setJLayered(Component... components) {
        int i = 0;
        for (Component component : components)
            layeredPane.add(component, new Integer(i++));
        return layeredPane;
    }
 
    // Reflection Practice
    public void setRectangles(Class<?> clazz, Object instance, Class<?> targetClass, Object target) throws Exception {
        Object tempObject = null;
        for (Field field : clazz.getDeclaredFields()) {
            if ((tempObject = field.get(instance)) instanceof JComponent) {
                ((JComponent) tempObject).setBounds((Rectangle) targetClass.getDeclaredField(field.getName()).get(target));
                ((JComponent) tempObject).setOpaque(false);
                ((JComponent) tempObject).setLayout(null);
            }
            if (tempObject instanceof Runnable)
                new Thread((Runnable) tempObject).start();
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if(obj == memberButton) {
			System.out.println("회원");
			ShowMember();
		}
		else if(obj == moneyButton) {
			System.out.println("매상");
		}
		else if(obj == staffButton) {
			System.out.println("직원");
			ShowStaff();
		}
	}
	public void ShowMember() {
		memberShow m = new memberShow();
	}
	
	public void ShowStaff() {
		StaffDAO s = new StaffDAO();
	}
}