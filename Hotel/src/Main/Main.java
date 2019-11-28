package Main;

import assets.Setting;
import control.DaoLogin;
import view.FrameLogin;
import view.FrameManage;

public class Main {
	private FrameManage frameManage;
	private FrameLogin frameLogin;
	
	private DaoLogin daoLogin;
	
	
	public static void main(String[] ar) throws Exception {
		Main main = new Main();
		FrameLogin frameLogin = new FrameLogin();
		frameLogin.setMain(main);
		
		DaoLogin daoLogin = new DaoLogin();
		daoLogin.insertUser();
		main.frameLogin.setDaoLogin(daoLogin);
	}
	
	public void showFrameManage(FrameLogin frameLogin){
		frameLogin.dispose();
		FrameManage manageView = new FrameManage();

		manageView.requestFocusInWindow();
		manageView.setFocusable(true);
		
		try {
			manageView.setRectangles(FrameManage.class, manageView, Setting.class, Setting.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
