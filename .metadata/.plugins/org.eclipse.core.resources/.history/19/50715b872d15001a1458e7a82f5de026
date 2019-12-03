package test;

import java.awt.Color;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpinnerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date now = new Date(0);
		SpinnerDateModel model = new SpinnerDateModel(now, null, now, Calendar.DAY_OF_WEEK);
		JSpinner spinner = new JSpinner(model);
		final DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner,"yyyy-mm-dd");
		JFormattedTextField ftf = editor.getTextField();
		ftf.setEditable(false);
		ftf.setHorizontalAlignment(JTextField.CENTER);
		
		ftf.setBackground(new Color(255,255,255));
		spinner.setEditor(editor);
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				Date value = (Date) model.getValue();
				Date next= (Date) model.getNextValue();
				if(value != null && next != null)
					System.out.println("value = "+df.format(value) + "\t"+ "next = "+df.format(next));
			}
		});
		JPanel panel=new JPanel();
		panel.add(spinner);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.setSize(250,100);
		frame.setLocation(200, 200);
		frame.setVisible(true);
 	}

}
