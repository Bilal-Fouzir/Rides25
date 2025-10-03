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

public class DeleteAccountGUI extends JFrame {
	private  ApplicationLauncher app = new ApplicationLauncher(); //APP
	private static final long serialVersionUID = 1L;
	private Traveler traveler;
	private Driver driver;
	private JPanel jContentPane = null;
	private JPasswordField jPasswordField;
	private final ButtonGroup buttonGroupRola = new ButtonGroup();
	private final ButtonGroup buttonGroupLanguage = new ButtonGroup();
	private static String etik = "Etiquetas";
	private static String m = "TravelerGUI.DeleteAccount";
	private static String let = "Tahoma";
	private JLabel jLabelPassword = new JLabel(ResourceBundle.getBundle(etik).getString("LoginGUI.Password"));
	private JLabel jLabelDriverError = new JLabel(ResourceBundle.getBundle(etik).getString("DeleteAccountGUI.DriverError"));
	private JLabel jLabelIncorrectDataError = new JLabel(ResourceBundle.getBundle(etik).getString("LoginGUI.IncorrectDataError"));
	private JButton jButtonDelete = new JButton(ResourceBundle.getBundle(etik).getString(m));

	private static BLFacade appFacadeInterface = new BLFacadeImplementation();
	private final JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
	private final JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle(etik).getString(m)); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel jLabelEmail = new JLabel((String) null);
	private final JLabel jLabelWarning = new JLabel((String) null);
	
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}

	public DeleteAccountGUI(Traveler t, Driver d) {
		this.traveler=t;
		this.driver=d;
		this.setSize(450, 300);
		
		BLFacade facade = DriverGUI.getBusinessLogic();
		
		jPasswordField = new JPasswordField();
		jPasswordField.setBounds(203, 71, 191, 22);
		
		//JLabel jLabelRolError = new JLabel((String) null);
		jLabelDriverError.setForeground(Color.RED);
		jLabelDriverError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelDriverError.setBounds(80, 100, 278, 19);
	    getContentPane().add(jLabelDriverError);
		jLabelDriverError.setHorizontalAlignment(SwingConstants.CENTER);
						
		//JLabel jLabelIncorrectDataError = new JLabel((String) null);
		jLabelIncorrectDataError.setForeground(Color.RED);
		jLabelIncorrectDataError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelIncorrectDataError.setBounds(80, 100, 278, 19);
		getContentPane().add(jLabelIncorrectDataError);
		jLabelIncorrectDataError.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		getContentPane().setLayout(null);
		getContentPane().add(jPasswordField);
		jLabelPassword.setBounds(50, 74, 122, 19);
		getContentPane().add(jLabelPassword);
		jButtonDelete.setEnabled(false);
		jButtonDelete.setBounds(160, 186, 155, 45);
		getContentPane().add(jButtonDelete);
		jButtonBack.setFont(new Font(let, Font.BOLD, 10));
		jButtonBack.setBounds(10, 220, 140, 21);
		
		getContentPane().add(jButtonBack);
		jLabelTitle.setFont(new Font(let, Font.BOLD, 14));
		jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitle.setBounds(10, 10, 416, 19);
		
		getContentPane().add(jLabelTitle);
		jLabelEmail.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelEmail.setFont(new Font(let, Font.PLAIN, 11));
		jLabelEmail.setBounds(10, 42, 416, 19);
		
		getContentPane().add(jLabelEmail);
		jLabelWarning.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelWarning.setFont(new Font(let, Font.BOLD, 13));
		jLabelWarning.setBounds(10, 134, 416, 19);
		
		getContentPane().add(jLabelWarning);
		
		JRadioButton rdbtnAcceptWarning = new JRadioButton(ResourceBundle.getBundle(etik).getString("DeleteAccountGUI.AcceptWarning"));
		rdbtnAcceptWarning.setBounds(142, 159, 284, 21);
		getContentPane().add(rdbtnAcceptWarning);
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(4, 1, 0, 0));
		
		setTitle(ResourceBundle.getBundle(etik).getString(m));
		
		jLabelDriverError.setVisible(false);
		jLabelIncorrectDataError.setVisible(false);
		
		if(traveler!=null) {
			jLabelWarning.setText(ResourceBundle.getBundle(etik).getString("DeleteAccount.TWarning"));
			
		}
		else {
			jLabelWarning.setText(ResourceBundle.getBundle(etik).getString("DeleteAccount.DWarning"));
		}
		jButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelDriverError.setVisible(false);
				jLabelIncorrectDataError.setVisible(false);
				String pass = new String (jPasswordField.getPassword());
				if(traveler!=null && pass.equals(traveler.getPassword())) {
					try {
						facade.deleteAccountT(traveler);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
					LoginGUI j = new LoginGUI();
					j.setVisible(true);
				}
				else if(traveler!=null)
					jLabelIncorrectDataError.setVisible(true);
				else if(driver != null && facade.badagoErreserbaBehinBetiko(driver))
					jLabelDriverError.setVisible(true);
				else if(driver != null && pass.equals(driver.getPassword())) {
					try {
						facade.deleteAccountD(driver);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
					LoginGUI j = new LoginGUI();
					j.setVisible(true);
				}
				else 
					jLabelIncorrectDataError.setVisible(true);
			}
		});
		
		jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	dispose();
            	JFrame a;
            	if(driver==null)
            		a = new TravelerGUI(traveler);
            	else
            		a= new DriverGUI(driver);
                a.setVisible(true);
            }
        });
		
		rdbtnAcceptWarning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	jButtonDelete.setEnabled(true);
            }
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	
}
