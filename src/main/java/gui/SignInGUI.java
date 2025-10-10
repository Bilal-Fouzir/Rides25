package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Traveler;
import exceptions.EmailAlreadyExistsException;
import service.BLFacade;
import service.BLFacadeImplementation;

import java.awt.Color;
import javax.swing.SwingConstants;

import configuration.ConfigXML;
import configuration.UnekoDatuak;

public class SignInGUI extends JFrame {

	private JPasswordField jPasswordField;
	private final ButtonGroup buttonGroupRola = new ButtonGroup();
	private final ButtonGroup buttonGroupLanguage = new ButtonGroup();
	private static String etik = "Etiquetas";
	private JLabel jLabelEmail = new JLabel(ResourceBundle.getBundle(etik).getString("SignInGUI.Email"));
	private JLabel jLabelPassword = new JLabel(ResourceBundle.getBundle(etik).getString("SignInGUI.Password"));
	private JRadioButton jRadioButtonTraveler = new JRadioButton(ResourceBundle.getBundle(etik).getString("SignInGUI.Traveler"));
	private JRadioButton jRadioButtonDriver = new JRadioButton(ResourceBundle.getBundle(etik).getString("SignInGUI.Driver"));
	private JButton jButtonSignIn = new JButton(ResourceBundle.getBundle(etik).getString("SignInGUI.SignIn"));
	protected JButton jButtonLoginWindow = new JButton(ResourceBundle.getBundle(etik).getString("SignInGUI.LoginWindow"));
	private JLabel jLabelEmailError = new JLabel(ResourceBundle.getBundle(etik).getString("SignInGUI.EmailError"));
	private JLabel jLabelEmailExistsError = new JLabel(ResourceBundle.getBundle(etik).getString("SignInGUI.EmailExistsError"));
	private JLabel jLabelPassError = new JLabel(ResourceBundle.getBundle(etik).getString("SignInGUI.PassError"));
	private JLabel jLabelRolError = new JLabel(ResourceBundle.getBundle(etik).getString("SignInGUI.RolError"));
	private JLabel jLabelRegistered = new JLabel(ResourceBundle.getBundle(etik).getString("SignInGUI.Registered"));
	private static String let = "Tahoma";
	private final JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("English");
	private final JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
	private final JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Euskara");
	private static BLFacade appFacadeInterface = new BLFacadeImplementation();
	
	private static String loc = "Locale: ";
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	public SignInGUI() {
	 
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
		
		getContentPane().setLayout(null);
		this.setSize(450, 300);
		
		JTextPane jTextPaneEmail = new JTextPane();
		jTextPaneEmail.setBounds(203, 30, 191, 19);
		getContentPane().add(jTextPaneEmail);
		
		jPasswordField = new JPasswordField();
		jPasswordField.setBounds(203, 71, 191, 22);
		getContentPane().add(jPasswordField);
		
		//JLabel jLabelEmail = new JLabel(ResourceBundle.getBundle(etik).getString("SignInGUI.Email"));
		jLabelEmail.setBounds(50, 30, 122, 19);
		getContentPane().add(jLabelEmail);
		
		//JLabel jLabelPassword = new JLabel(ResourceBundle.getBundle(etik).getString("SignInGUI.Password"));
		jLabelPassword.setBounds(50, 74, 122, 19);
		getContentPane().add(jLabelPassword);
		
		//JRadioButton jRadioButtonTraveler = new JRadioButton(ResourceBundle.getBundle(etik).getString("SignInGUI.Traveler"));
		jRadioButtonTraveler.setFont(new Font(let, Font.BOLD, 14));
		buttonGroupRola.add(jRadioButtonTraveler);
		jRadioButtonTraveler.setBounds(80, 119, 130, 22);
		getContentPane().add(jRadioButtonTraveler);
		
		//JRadioButton jRadioButtonDriver = new JRadioButton(ResourceBundle.getBundle(etik).getString("SignInGUI.Driver"));
		jRadioButtonDriver.setFont(new Font(let, Font.BOLD, 14));
		buttonGroupRola.add(jRadioButtonDriver);
		jRadioButtonDriver.setBounds(233, 120, 130, 21);
		getContentPane().add(jRadioButtonDriver);
		
		//JButton jButtonLogin = new JButton(ResourceBundle.getBundle(etik).getString("SignInGUI.Login"));
		jButtonSignIn.setBounds(170, 160, 104, 45);
		getContentPane().add(jButtonSignIn);
		
		//JButton jButtonSignInWindow = new JButton(ResourceBundle.getBundle(etik).getString("SignInGUI.SignInWindow"));
		jButtonLoginWindow.setBounds(235, 234, 191, 19);
		getContentPane().add(jButtonLoginWindow);
		rdbtnNewRadioButton_1.setBounds(5, 232, 69, 21);
		
		//JLabel jLabelPassError = new JLabel((String) null);
		jLabelPassError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 8));
		jLabelPassError.setForeground(Color.RED);
		jLabelPassError.setBounds(203, 94, 223, 19);
		getContentPane().add(jLabelPassError);
		
		//JLabel jLabelEmailError = new JLabel((String) null);
		jLabelEmailError.setForeground(Color.RED);
		jLabelEmailError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelEmailError.setBounds(203, 47, 223, 19);
		getContentPane().add(jLabelEmailError);
		
		//JLabel jLabelEmailExistsError = new JLabel((String) null);
		jLabelEmailExistsError.setForeground(Color.RED);
		jLabelEmailExistsError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelEmailExistsError.setBounds(203, 47, 223, 19);
		getContentPane().add(jLabelEmailExistsError);
		
		//JLabel jLabelRolError = new JLabel((String) null);
		jLabelRolError.setForeground(Color.RED);
		jLabelRolError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelRolError.setBounds(141, 141, 156, 19);
		getContentPane().add(jLabelRolError);
		jLabelRolError.setHorizontalAlignment(SwingConstants.CENTER);
		
		//JLabel jLabelRegisterd = new JLabel((String) null);
		jLabelRegistered.setForeground(Color.GREEN);
		jLabelRegistered.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelRegistered.setBounds(80, 141, 278, 19);
		getContentPane().add(jLabelRegistered);
		jLabelRegistered.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		getContentPane().add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_2.setBounds(70, 232, 86, 21);
		
		getContentPane().add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_3.setBounds(155, 232, 76, 21);
		
		getContentPane().add(rdbtnNewRadioButton_3);
				
		erroreakGarbitu();

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
		
		
		jButtonSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean error = false;
				erroreakGarbitu();
				String emailInput = jTextPaneEmail.getText();
				String passInput = new String (jPasswordField.getPassword());
				if (emailInput.length() == 0){  
					error = true;
					jLabelEmailError.setVisible(true);
				}
				if (passInput.length() < 8) {
					error = true;
					jLabelPassError.setVisible(true);
				}
				if (!jRadioButtonTraveler.isSelected() && !jRadioButtonDriver.isSelected()) {
					error = true;
					jLabelRolError.setVisible(true);
				}
				if (!error) {
					BLFacade facade = SignInGUI.getBusinessLogic();
					if (jRadioButtonTraveler.isSelected()) {
						Traveler t = new Traveler(emailInput, passInput);
						if (facade.badagoTraveler(emailInput) == null) {
							facade.createTraveler(t);
							jLabelRegistered.setVisible(true);
						}
						else jLabelEmailExistsError.setVisible(true);
					}
					else if (jRadioButtonDriver.isSelected()) {
						Driver d = new Driver(emailInput, passInput);
						if (facade.badagoDriver(emailInput) == null) {
							facade.createDriver(d);
							jLabelRegistered.setVisible(true);
						}
						else jLabelEmailExistsError.setVisible(true);
						
					} 
				}
				paintAgain();
			}
		});
		
		jButtonLoginWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginGUI l = new LoginGUI();
				l.setVisible(true);
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
		jLabelEmail.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.Email"));
		jLabelPassword.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.Password"));
		jRadioButtonTraveler.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.Traveler"));
		jRadioButtonDriver.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.Driver"));
		jButtonSignIn.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.SignIn"));
		jButtonLoginWindow.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.LoginWindow"));
		jLabelEmailError.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.EmailError"));
		jLabelEmailExistsError.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.EmailExistsError"));
		jLabelPassError.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.PassError"));
		jLabelRolError.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.RolError"));
		jLabelRegistered.setText(ResourceBundle.getBundle(etik).getString("SignInGUI.Registered"));

		this.setTitle(ResourceBundle.getBundle(etik).getString("SignInGUI.MainTitle"));
	}
	public void erroreakGarbitu() {
		jLabelEmailError.setVisible(false);
		jLabelEmailExistsError.setVisible(false);
		jLabelPassError.setVisible(false);
		jLabelRolError.setVisible(false);
		jLabelRegistered.setVisible(false);
	}
}
