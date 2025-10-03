package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import configuration.UtilDate;
import domain.Driver;
import domain.Ride;
import domain.Transaction;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import service.BLFacade;

public class TakeMoneyGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private static String etik = "Etiquetas";
	private Driver driver;
	private JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle(etik).getString("TakeMoneyGUI.Title"));
	private JButton jButtonTake = new JButton(ResourceBundle.getBundle(etik).getString("TakeMoneyGUI.Take"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
    private JLabel jLabelMoney = new JLabel(ResourceBundle.getBundle(etik).getString("TakeMoneyGUI.Money"));
	private JLabel jLabelInsertMoney = new JLabel(ResourceBundle.getBundle(etik).getString("TakeMoneyGUI.InsertMoney"));
	private final JLabel jLabelMoney2 = new JLabel((String) null);
	private final JLabel jLabelWrongFormatError = new JLabel(ResourceBundle.getBundle(etik).getString("TakeMoneyGUI.WrongFormatError"));
	private final JLabel jLabelLessMoneyError = new JLabel(ResourceBundle.getBundle(etik).getString("TakeMoneyGUI.LessMoneyError"));



	public TakeMoneyGUI(Driver d) {
		
		this.setTitle(ResourceBundle.getBundle(etik).getString("TakeMoneyGUI.Title"));

		jLabelWrongFormatError.setVisible(false);
		jLabelLessMoneyError.setVisible(false);

		this.driver=d;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));

		jButtonTake.setBounds(new Rectangle(329, 263, 130, 30));

	
		this.getContentPane().add(jButtonBack, null);
		this.getContentPane().add(jButtonTake, null);

		
		
		
		BLFacade facade = DriverGUI.getBusinessLogic();
		jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelTitle.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelTitle.setBounds(0, 22, 588, 25);
		getContentPane().add(jLabelTitle);
		
		jLabelMoney.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelMoney.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelMoney.setBounds(143, 209, 154, 25);
		getContentPane().add(jLabelMoney);
		
		jLabelInsertMoney.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelInsertMoney.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelInsertMoney.setBounds(153, 100, 286, 25);
		getContentPane().add(jLabelInsertMoney);
		
		JTextPane jTextPaneMoney = new JTextPane();
		jTextPaneMoney.setBounds(193, 132, 206, 20);
		getContentPane().add(jTextPaneMoney);
		jLabelMoney2.setHorizontalAlignment(SwingConstants.LEFT);
		jLabelMoney2.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelMoney2.setBounds(307, 209, 154, 25);
		String moneyBir = String.format("%.2f", driver.getMoney());
		jLabelMoney2.setText(moneyBir+"€");
		
		getContentPane().add(jLabelMoney2);
		
		jButtonTake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelWrongFormatError.setVisible(false);
				jLabelLessMoneyError.setVisible(false);
				boolean error=false;
				double money=0.0;
				try {
					 money = Double.parseDouble(jTextPaneMoney.getText());
				
					if(money<=0) {
						jLabelWrongFormatError.setVisible(true);
						error=true;
					}
					if(!error && money>driver.getMoney()) {
						jLabelLessMoneyError.setVisible(true);
						error=true;
					}
					if(!error) {
						facade.sartuDiruaD(-money, driver);
						driver=facade.badagoDriver(driver.getEmail());
						String moneyBir = String.format("%.2f", driver.getMoney());
						jLabelMoney2.setText(moneyBir+"€");

					}
				} catch(Exception ee) {
					jLabelWrongFormatError.setVisible(true);
					error=true;
					ee.getMessage();
				}
				if(!error) {
					facade.addTransactionD(new Transaction(-money, driver.getMoney(), "Withdrawal/Dinero retirado/Dirua aterata"), driver);
					driver=facade.badagoDriver(driver.getEmail());
				}
				}
		});
		jButtonBack.setBounds(new Rectangle(133, 263, 130, 30));
		jLabelWrongFormatError.setForeground(Color.RED);
		jLabelWrongFormatError.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		jLabelWrongFormatError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelWrongFormatError.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelWrongFormatError.setBounds(153, 154, 286, 25);
		
		getContentPane().add(jLabelWrongFormatError);
		jLabelLessMoneyError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelLessMoneyError.setForeground(Color.RED);
		jLabelLessMoneyError.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		jLabelLessMoneyError.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelLessMoneyError.setBounds(153, 154, 286, 25);
		
		getContentPane().add(jLabelLessMoneyError);
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				DriverGUI dgui = new DriverGUI(driver);
				dgui.setVisible(true);
			}
		});

		
	}	 
}
