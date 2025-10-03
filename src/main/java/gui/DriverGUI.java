package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Driver;
import domain.Ride;
import service.BLFacade;
import service.BLFacadeImplementation;
import configuration.UnekoDatuak;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;


public class DriverGUI extends JFrame {
	
    private Driver driver;
	private static final long serialVersionUID = 1L;
	private static String etik = "Etiquetas";
	private JPanel jContentPane = null;
	private JButton jButtonCreateRide = new JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.CreateRide"));;
	private JButton jButtonRemoveRide = new JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.RemoveRide"));
	private JButton jButtonSignOut = new JButton(ResourceBundle.getBundle(etik).getString("SignOut"));
	private JLabel jLabelMoney = null;
	private JLabel jLabelEmail = null;
	private final ButtonGroup buttonGroupLanguage = new ButtonGroup();
	private final JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("English");
	private final JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
	private final JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Euskara");
	private JButton jButtonTakeMoney = new JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.TakeMoney"));
	private JButton jButtonOnartuBaztertu = new JButton(ResourceBundle.getBundle(etik).getString("OnartuBaztertuErGUI.Onartu/Baztertu"));
	private JButton jButtonBukatu = new JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.Bukatu"));
	private JButton jButtonAddCar = new JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.AddCar"));
	private final JButton jButtonTransactions = new JButton(ResourceBundle.getBundle(etik).getString("Transactions"));
	private JButton jButtonIkusiBukatuak = new JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.IkusiBukatuak"));



    private static BLFacade appFacadeInterface = new BLFacadeImplementation();
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JButton jButtonDeleteAccount = new JButton((String) null);
	private static String loc = "Locale: ";
	
	/**
	 * This is the default constructor
	 */
	public DriverGUI(Driver d) {
		super();

		driver=d;
		
		
		UnekoDatuak u=UnekoDatuak.getInstance();
		if(u.getLanguage()==1) {
			Locale.setDefault(new Locale("en"));
			rdbtnNewRadioButton_1.setSelected(true);
		}
		else if(u.getLanguage()==2) {
			Locale.setDefault(new Locale("es"));
			rdbtnNewRadioButton_2.setSelected(true);
		}
		else {
			Locale.setDefault(new Locale("eus"));
			rdbtnNewRadioButton_3.setSelected(true);
		}

		this.setSize(450, 300);
		
		
		jContentPane = new JPanel();
		
		
		setContentPane(jContentPane);
		jContentPane.setLayout(null);
		
		//JButton jButtonCreateQuery = new JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.CreateRide")); 
		jButtonCreateRide.setBounds(42, 85, 150, 50);
		jContentPane.add(jButtonCreateRide);
		
		//JButton jButtonQueryQueries = new JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.QueryRides"));
		jButtonRemoveRide.setBounds(243, 85, 150, 50);
		jContentPane.add(jButtonRemoveRide);
		
		//JButton jButtonSignOut = new JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.SignOut"));
		jButtonSignOut.setBounds(299, 223, 127, 30);
		jContentPane.add(jButtonSignOut);
		
		JLabel jLabelMoney = new JLabel();
		jLabelMoney.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelMoney.setFont(new Font("Tahoma", Font.BOLD, 14));
		jLabelMoney.setBackground(new Color(255, 255, 255));
		String moneyBir = String.format("%.2f", driver.getMoney());
		jLabelMoney.setText(moneyBir+"€");
		jLabelMoney.setBounds(10, 10, 146, 19);
		jLabelMoney.setAlignmentX(Component.CENTER_ALIGNMENT);
		jContentPane.add(jLabelMoney);
		
		JLabel jLabelEmail = new JLabel();
		jLabelEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelEmail.setText(driver.getEmail());
		jLabelEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		jLabelEmail.setBackground(Color.WHITE);
		jLabelEmail.setAlignmentX(0.5f);
		jLabelEmail.setBounds(206, 10, 220, 19);
		jContentPane.add(jLabelEmail);
		 
		rdbtnNewRadioButton_1.setBounds(5, 232, 69, 21);
		jContentPane.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2.setBounds(70, 232, 86, 21);
		jContentPane.add(rdbtnNewRadioButton_2);
		
		rdbtnNewRadioButton_3.setBounds(155, 232, 76, 21);
		jContentPane.add(rdbtnNewRadioButton_3);
		setTitle(ResourceBundle.getBundle(etik).getString("DriverGUI.MainTitle") + " - driver :" + driver.getEmail());
		
		paintAgain();
		
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println(loc+Locale.getDefault());
				u.setLanguage(1);
				paintAgain();				}
		});
		buttonGroupLanguage.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("es"));
				System.out.println(loc+Locale.getDefault());
				u.setLanguage(2);
				paintAgain();				}
		});
		buttonGroupLanguage.add(rdbtnNewRadioButton_2);
		
		rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("eus"));
				System.out.println(loc+Locale.getDefault());
				u.setLanguage(3);
				paintAgain();
			}
		});
		buttonGroupLanguage.add(rdbtnNewRadioButton_3);
		
		jButtonTakeMoney.setBounds(10, 29, 146, 23);
		jContentPane.add(jButtonTakeMoney);
		
		jButtonOnartuBaztertu.setBounds(42, 140, 150, 50);
		jContentPane.add(jButtonOnartuBaztertu);
		
		jButtonBukatu.setBounds(243, 140, 150, 50);
		jContentPane.add(jButtonBukatu);
		jButtonTransactions.setBounds(10, 55, 146, 23);
		
		jContentPane.add(jButtonTransactions);
		
		jButtonAddCar.setBounds(42, 200, 150, 23);
		jContentPane.add(jButtonAddCar);
		
		jButtonIkusiBukatuak.setBounds(280, 56, 146, 23);
		jContentPane.add(jButtonIkusiBukatuak);
		jButtonDeleteAccount.setBounds(280, 30, 146, 23);
		
		jContentPane.add(jButtonDeleteAccount);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
		
		jButtonCreateRide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	dispose();
                JFrame a = new CreateRideGUI(driver);
                a.setVisible(true);
            }
        });
		
		jButtonRemoveRide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	dispose();
                JFrame a = new RemoveRideGUI(driver);
                a.setVisible(true);
            }
        });
		
		jButtonSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginGUI l = new LoginGUI();
				l.setVisible(true);
								}
		});
		
		jButtonTakeMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				TakeMoneyGUI j = new TakeMoneyGUI(driver);
				j.setVisible(true);
								}
		});
		
		jButtonOnartuBaztertu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				OnartuBaztertuErGUI o = new OnartuBaztertuErGUI(driver);
				o.setVisible(true);
								}
		});
		
		jButtonBukatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				BukatuRideGUI o = new BukatuRideGUI(driver);
				o.setVisible(true);
								}
		});
		
		jButtonTransactions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				DriverTransactionsGUI dgui = new DriverTransactionsGUI(driver);
				dgui.setVisible(true);
			}
		});
		
		jButtonAddCar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				AddCarGUI dgui = new AddCarGUI(driver);
				dgui.setVisible(true);
			}
		});
		
		jButtonIkusiBukatuak.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				BukatutakoRidesGUI dgui = new BukatutakoRidesGUI(driver);
				dgui.setVisible(true);
			}
		});
		
		jButtonDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				DeleteAccountGUI j = new DeleteAccountGUI(null, driver);
				j.setVisible(true);
			}
		});
		
	}
	
	private void paintAgain() {
		jButtonDeleteAccount.setText(ResourceBundle.getBundle(etik).getString("TravelerGUI.DeleteAccount"));
		jButtonRemoveRide.setText(ResourceBundle.getBundle(etik).getString("DriverGUI.RemoveRide"));
		jButtonTransactions.setText(ResourceBundle.getBundle(etik).getString("Transactions"));
		jButtonIkusiBukatuak.setText(ResourceBundle.getBundle(etik).getString("DriverGUI.IkusiBukatuak"));
		jButtonCreateRide.setText(ResourceBundle.getBundle(etik).getString("DriverGUI.CreateRide"));
		jButtonSignOut.setText(ResourceBundle.getBundle(etik).getString("SignOut"));
		jButtonTakeMoney.setText(ResourceBundle.getBundle(etik).getString("DriverGUI.TakeMoney"));
		jButtonOnartuBaztertu.setText(ResourceBundle.getBundle(etik).getString("OnartuBaztertuErGUI.Onartu/Baztertu"));
		jButtonBukatu.setText(ResourceBundle.getBundle(etik).getString("DriverGUI.Bukatu"));
		jButtonAddCar.setText(ResourceBundle.getBundle(etik).getString("DriverGUI.AddCar"));
		this.setTitle(ResourceBundle.getBundle(etik).getString("DriverGUI.MainTitle")+ " - driver :" + driver.getEmail());
	}
} // @jve:decl-index=0:visual-constraint="0,0"

