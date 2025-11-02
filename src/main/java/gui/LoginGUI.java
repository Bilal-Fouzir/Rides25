package gui;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.GroupLayout.Alignment;

import java.awt.event.WindowAdapter;

import configuration.ConfigXML;
import configuration.UnekoDatuak;
import domain.Alerta;
import domain.Driver;
import domain.Traveler;
import service.BLFacade;
import service.BLFacadeImplementation;

public class LoginGUI extends JFrame {
	//APP
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPasswordField jPasswordField;
	private final ButtonGroup buttonGroupRola = new ButtonGroup();
	private final ButtonGroup buttonGroupLanguage = new ButtonGroup();
	private static String etik = "Etiquetas";
	private JLabel jLabelEmail = new JLabel(ResourceBundle.getBundle(etik).getString("LoginGUI.Email"));
	private JLabel jLabelPassword = new JLabel(ResourceBundle.getBundle(etik).getString("LoginGUI.Password"));
	private JLabel jLabelRolError = new JLabel(ResourceBundle.getBundle(etik).getString("LoginGUI.RolError"));
	private JLabel jLabelIncorrectDataError = new JLabel(ResourceBundle.getBundle(etik).getString("LoginGUI.IncorrectDataError"));
	private JRadioButton jRadioButtonTraveler = new JRadioButton(ResourceBundle.getBundle(etik).getString("LoginGUI.Traveler"));
	private JRadioButton jRadioButtonDriver = new JRadioButton(ResourceBundle.getBundle(etik).getString("LoginGUI.Driver"));
	private JButton jButtonLogin = new JButton(ResourceBundle.getBundle(etik).getString("LoginGUI.Login"));
	protected JButton jButtonSignInWindow = new JButton(ResourceBundle.getBundle(etik).getString("LoginGUI.SignInWindow")); //
	private final JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("English");
	private final JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
	private final JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Euskara");
	private static String let = "Tahoma";
	private static BLFacade appFacadeInterface= new BLFacadeImplementation();
	private final JButton jButtonQueryRides = new JButton(ResourceBundle.getBundle(etik).getString("LoginGUI.QueryRides"));
	
	private static String loc = "Locale: ";
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}

	public LoginGUI() {
		
		garbituAlertak();
		
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
		System.out.println(loc+Locale.getDefault());
		
		this.setSize(450, 300);
		
		JTextPane jTextPaneEmail = new JTextPane();
		jTextPaneEmail.setBounds(203, 30, 191, 19);
		
		jPasswordField = new JPasswordField();
		jPasswordField.setBounds(203, 71, 191, 22);
		jRadioButtonTraveler.setBounds(107, 119, 130, 22);
		
		//JRadioButton jRadioButtonTraveler = new JRadioButton(ResourceBundle.getBundle(etik).getString("LoginGUI.Traveler"));
		jRadioButtonTraveler.setFont(new Font(let, Font.BOLD, 14));
		buttonGroupRola.add(jRadioButtonTraveler);
		jRadioButtonDriver.setBounds(235, 120, 130, 21);
		
		//JRadioButton jRadioButtonDriver = new JRadioButton(ResourceBundle.getBundle(etik).getString("LoginGUI.Driver"));
		jRadioButtonDriver.setFont(new Font(let, Font.BOLD, 14));
		buttonGroupRola.add(jRadioButtonDriver);
		rdbtnNewRadioButton_1.setBounds(5, 232, 69, 21);
		
		//JLabel jLabelRolError = new JLabel((String) null);
		jLabelRolError.setForeground(Color.RED);
		jLabelRolError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelRolError.setBounds(141, 141, 156, 19);
	    getContentPane().add(jLabelRolError);
		jLabelRolError.setHorizontalAlignment(SwingConstants.CENTER);
						
		//JLabel jLabelIncorrectDataError = new JLabel((String) null);
		jLabelIncorrectDataError.setForeground(Color.RED);
		jLabelIncorrectDataError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelIncorrectDataError.setBounds(80, 100, 278, 19);
		getContentPane().add(jLabelIncorrectDataError);
		jLabelIncorrectDataError.setHorizontalAlignment(SwingConstants.CENTER);
		
		paintAgain();
		
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println(loc+Locale.getDefault());
				u.setLanguage(1);
				paintAgain();				}
		});
		buttonGroupLanguage.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_2.setBounds(70, 232, 86, 21);
		
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("es"));
				System.out.println(loc+Locale.getDefault());
				u.setLanguage(2);
				paintAgain();				}
		});
		buttonGroupLanguage.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_3.setBounds(155, 232, 76, 21);
		
		rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("eus"));
				System.out.println(loc+Locale.getDefault());
				u.setLanguage(3);
				paintAgain();
			}
		});
		buttonGroupLanguage.add(rdbtnNewRadioButton_3);
		getContentPane().setLayout(null);
		getContentPane().add(jTextPaneEmail);
		getContentPane().add(jPasswordField);
		jLabelEmail.setBounds(50, 30, 122, 19);
		getContentPane().add(jLabelEmail);
		jLabelPassword.setBounds(50, 74, 122, 19);
		getContentPane().add(jLabelPassword);
		getContentPane().add(jRadioButtonTraveler);
		getContentPane().add(jRadioButtonDriver);
		jButtonLogin.setBounds(160, 163, 122, 45);
		getContentPane().add(jButtonLogin);
		jButtonSignInWindow.setBounds(235, 234, 191, 19);
		getContentPane().add(jButtonSignInWindow);
		getContentPane().add(rdbtnNewRadioButton_1);
		getContentPane().add(rdbtnNewRadioButton_2);
		getContentPane().add(rdbtnNewRadioButton_3);
		jButtonQueryRides.setFont(new Font(let, Font.BOLD, 10));
		jButtonQueryRides.setBounds(10, 205, 140, 21);
		
		getContentPane().add(jButtonQueryRides);
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(4, 1, 0, 0));
		
		setTitle(ResourceBundle.getBundle(etik).getString("LoginGUI.MainTitle"));
		
		jLabelRolError.setVisible(false);
		jLabelIncorrectDataError.setVisible(false);
		
		jButtonSignInWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				SignInGUI s = new SignInGUI();
				s.setVisible(true);
								}
		});
		
		jButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean error = false;
				jLabelRolError.setVisible(false);
				jLabelIncorrectDataError.setVisible(false);
				String emailInput = jTextPaneEmail.getText();
				String passInput = new String (jPasswordField.getPassword()); 
				if (!jRadioButtonTraveler.isSelected() && !jRadioButtonDriver.isSelected()) {
					error = true;
					jLabelRolError.setVisible(true);
				}
				if (!error) {
					BLFacade facade = LoginGUI.getBusinessLogic();
					if (jRadioButtonTraveler.isSelected()) {
						Traveler t = facade.badagoTraveler(emailInput);
						if (t != null && t.getPassword().equals(passInput)) {
							u.setRol("Traveler");
							u.setTraveler(t);
							dispose();
							TravelerGUI tgui = new TravelerGUI(t);
							tgui.setVisible(true);
						}
						else jLabelIncorrectDataError.setVisible(true);
					}
					else if (jRadioButtonDriver.isSelected()) {
						Driver d = facade.badagoDriver(emailInput);
						if (d != null && d.getPassword().equals(passInput)) {
							u.setRol("Driver");
							u.setDriver(d);
							dispose();
							DriverGUI dgui = new DriverGUI(d);
							dgui.setVisible(true);
						}
						else jLabelIncorrectDataError.setVisible(true);
						
					} 
				}
				paintAgain();
			}
		});
		
		jButtonQueryRides.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	dispose();
                JFrame a = new FindRidesGUI();
                a.setVisible(true);
            }
        });
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}

	private void paintAgain() {
		jLabelEmail.setText(ResourceBundle.getBundle(etik).getString("LoginGUI.Email"));
		jLabelPassword.setText(ResourceBundle.getBundle(etik).getString("LoginGUI.Password"));
		jRadioButtonTraveler.setText(ResourceBundle.getBundle(etik).getString("LoginGUI.Traveler"));
		jRadioButtonDriver.setText(ResourceBundle.getBundle(etik).getString("LoginGUI.Driver"));
		jButtonLogin.setText(ResourceBundle.getBundle(etik).getString("LoginGUI.Login"));
		jButtonSignInWindow.setText(ResourceBundle.getBundle(etik).getString("LoginGUI.SignInWindow"));
		jLabelRolError.setText(ResourceBundle.getBundle(etik).getString("LoginGUI.RolError"));
		jLabelIncorrectDataError.setText(ResourceBundle.getBundle(etik).getString("LoginGUI.IncorrectDataError"));
		jButtonQueryRides.setText(ResourceBundle.getBundle(etik).getString("LoginGUI.QueryRides"));
		this.setTitle(ResourceBundle.getBundle(etik).getString("LoginGUI.MainTitle"));
	}
	
	private void garbituAlertak() {
		BLFacade facade = LoginGUI.getBusinessLogic();
		for (Alerta a : facade.getAllAlertak()) {
			if(a.getNora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(LocalDate.now())) {
				facade.removeAlerta(a.getId());
			}
		}
	}
	
}
