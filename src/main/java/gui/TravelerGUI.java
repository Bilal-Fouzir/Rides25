package gui;

import javax.swing.*;

import domain.Alerta;
import domain.Driver;
import domain.Ride;
import domain.Transaction;
import domain.Traveler;
import service.BLFacade;
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

public class TravelerGUI extends JFrame {

	private Traveler traveler;
	private Ride ride;
	private static final long serialVersionUID = 1L;
	private static String etik = "Etiquetas";
	private JPanel jContentPane = null;
	private DefaultListModel<String> alertaInfo = new DefaultListModel<String>();
	private JButton jButtonCreateErreserba = new JButton(
			ResourceBundle.getBundle(etik).getString("TravelerGUI.CreateErreserba"));;
	private JButton jButtonRemoveRide = new JButton(
			ResourceBundle.getBundle(etik).getString("TravelerGUI.RemoveErreserba"));
	private JButton jButtonSignOut = new JButton(ResourceBundle.getBundle(etik).getString("SignOut"));
	private JButton jButtonTransactions = new JButton(ResourceBundle.getBundle(etik).getString("Transactions"));
	private JLabel jLabelMoney = null;
	private JLabel jLabelEmail = null;
	private final ButtonGroup buttonGroupLanguage = new ButtonGroup();
	private final JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("English");
	private final JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
	private final JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Euskara");
	private JButton jButtonPutMoney = new JButton(
			ResourceBundle.getBundle(etik).getString("TravelerGUI.PutMoney"));
	private JButton jButtonErreserbaBukatuak = new JButton(
			ResourceBundle.getBundle(etik).getString("TravelerGUI.ErreserbaBukatuak"));
	private JButton jButtonGo = new JButton(ResourceBundle.getBundle(etik).getString("TravelerGUI.Go")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel jLabelAlert = new JLabel(ResourceBundle.getBundle(etik).getString("TravelerGUI.Alert")); //$NON-NLS-1$ //$NON-NLS-2$
	private static BLFacade appFacadeInterface;
	private final JButton jButtonDeleteAccount = new JButton(ResourceBundle.getBundle(etik).getString("TravelerGUI.DeleteAccount"));
	private static String let = "Tahoma";
	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private volatile boolean geldituta = false;
	private volatile boolean go = false;
	private static String loc = "Locale: ";

	/**
	 * This is the default constructor
	 */
	public TravelerGUI(Traveler t) {
		super(); 
		
		traveler = t;
		
		geldituta = false;
		go = false;

		UnekoDatuak u = UnekoDatuak.getInstance();
		if (u.getLanguage() == 1) {
			Locale.setDefault(new Locale("en"));
			rdbtnNewRadioButton_1.setSelected(true);
		} else if (u.getLanguage() == 2) {
			Locale.setDefault(new Locale("es"));
			rdbtnNewRadioButton_2.setSelected(true);
		} else {
			Locale.setDefault(new Locale("eus"));
			rdbtnNewRadioButton_3.setSelected(true);
		}

		this.setSize(450, 300);

		jContentPane = new JPanel();

		setContentPane(jContentPane);
		jContentPane.setLayout(null);
		jButtonCreateErreserba.setFont(new Font(let, Font.BOLD, 10));

		// JButton jButtonCreateQuery = new
		// JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.CreateRide"));
		jButtonCreateErreserba.setBounds(23, 110, 170, 50);
		jContentPane.add(jButtonCreateErreserba);
		jButtonRemoveRide.setFont(new Font(let, Font.BOLD, 10));

		// JButton jButtonQueryQueries = new
		// JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.QueryRides"));
		jButtonRemoveRide.setBounds(231, 110, 170, 50);
		jContentPane.add(jButtonRemoveRide);

		// JButton jButtonSignOut = new
		// JButton(ResourceBundle.getBundle(etik).getString("DriverGUI.SignOut"));
		jButtonSignOut.setBounds(299, 223, 127, 30);
		jContentPane.add(jButtonSignOut);

		JLabel jLabelMoney = new JLabel();
		jLabelMoney.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelMoney.setFont(new Font(let, Font.BOLD, 14));
		jLabelMoney.setBackground(new Color(255, 255, 255));
		String moneyBir = String.format("%.2f", traveler.getMoney());
		jLabelMoney.setText(moneyBir + "€");
		jLabelMoney.setBounds(10, 10, 146, 19);
		jLabelMoney.setAlignmentX(Component.CENTER_ALIGNMENT);
		jContentPane.add(jLabelMoney);

		JLabel jLabelEmail = new JLabel();
		jLabelEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelEmail.setText(traveler.getEmail());
		jLabelEmail.setFont(new Font(let, Font.BOLD, 14));
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
		setTitle(ResourceBundle.getBundle(etik).getString("TravelerGUI.MainTitle") + " - traveler :"
				+ traveler.getEmail());

		paintAgain();

		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println(loc + Locale.getDefault());
				u.setLanguage(1);
				paintAgain();
			}
		});
		buttonGroupLanguage.add(rdbtnNewRadioButton_1);

		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("es"));
				System.out.println(loc + Locale.getDefault());
				u.setLanguage(2);
				paintAgain();
			}
		});
		buttonGroupLanguage.add(rdbtnNewRadioButton_2);

		rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("eus"));
				System.out.println(loc + Locale.getDefault());
				u.setLanguage(3);
				paintAgain();
			}
		});
		buttonGroupLanguage.add(rdbtnNewRadioButton_3);

		jButtonPutMoney.setBounds(10, 29, 146, 23);
		jContentPane.add(jButtonPutMoney);

		jButtonTransactions.setBounds(10, 62, 146, 23);
		jContentPane.add(jButtonTransactions);

		jButtonErreserbaBukatuak.setBounds(280, 62, 146, 23);
		jContentPane.add(jButtonErreserbaBukatuak);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 170, 416, 50);
		jContentPane.add(scrollPane);

		JList alertList = new JList();
		alertList.setBackground(new Color(206, 255, 255));
		scrollPane.setViewportView(alertList);
		alertList.setModel(alertaInfo);
		jButtonGo.setFont(new Font(let, Font.BOLD, 10));
		
		
		scrollPane.setRowHeaderView(jButtonGo);
		
		
		jLabelAlert.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		scrollPane.setColumnHeaderView(jLabelAlert);
		jButtonDeleteAccount.setBounds(280, 30, 146, 23);
		
		jContentPane.add(jButtonDeleteAccount);
		scrollPane.setVisible(false);
		
		BLFacade facade = DriverGUI.getBusinessLogic();

		    Thread hilo = new Thread(() -> {
		    	
		        List<Alerta> alertaAktibatuak = facade.getAlertaAktibatuak(traveler.getEmail());
		        int i = 1;
		        int max = alertaAktibatuak.size();
		        
		        for (Alerta alert : alertaAktibatuak) {
		        	scrollPane.setVisible(true);
		        	ride=alert.getRide();
		            try {
		                for (int j = 6; j >= 0; j--) {
		                	if(geldituta) break;
		                    final int count = j;
		                    final int current = i;
		                    SwingUtilities.invokeLater(() -> {
		                        alertaInfo.clear();
		                        alertaInfo.addElement("(" + count + ") [" + current + "/" + max + "] " + alert.toString());
		                    });
		                    Thread.sleep(1000);
		                }
		                if(geldituta) {
		                	if(go) {
		                		facade.removeAlerta(alert.getId());
		                		scrollPane.setVisible(false);
		                		dispose();
		        				JFrame a = new CreateErreserbaGUI(traveler, ride);
		        				a.setVisible(true);
		                	}
		                	break;
		                }
		                facade.removeAlerta(alert.getId());
		                SwingUtilities.invokeLater(() -> alertaInfo.clear());
		            } catch (Exception  e) {
		                
		                Thread.currentThread().interrupt();
		            }
		            i++;
		        }
		        scrollPane.setVisible(false);
		    });
		    hilo.start();
		
		    
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});

		jButtonCreateErreserba.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				geldituta=true;
				dispose();
				JFrame a = new CreateErreserbaGUI(traveler, null);
				a.setVisible(true);
			}
		});
		
		jButtonGo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				geldituta=true;
				go=true;
				
				
			}
		});

		jButtonRemoveRide.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				geldituta=true;
				dispose();
				JFrame a = new RemoveErreserbaGUI(traveler);
				a.setVisible(true);
			}
		});

		jButtonSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				geldituta=true;
				dispose();
				LoginGUI l = new LoginGUI();
				l.setVisible(true);
			}
		});

		jButtonPutMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				geldituta=true;
				dispose();
				PutMoneyGUI j = new PutMoneyGUI(traveler);
				j.setVisible(true);
			}
		});

		jButtonTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				geldituta=true;
				dispose();
				TravelerTransactionsGUI j = new TravelerTransactionsGUI(traveler);
				j.setVisible(true);
			}
		});

		jButtonErreserbaBukatuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				geldituta=true;
				dispose();
				BaloratuErreklamatuGUI j = new BaloratuErreklamatuGUI(traveler);
				j.setVisible(true);
			}
		});
		
		jButtonDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				geldituta=true;
				dispose();
				DeleteAccountGUI j = new DeleteAccountGUI(traveler, null);
				j.setVisible(true);
			}
		});

	}
	

	private void paintAgain() {
		jButtonDeleteAccount.setText(ResourceBundle.getBundle(etik).getString("TravelerGUI.DeleteAccount"));
		jButtonGo.setText(ResourceBundle.getBundle(etik).getString("TravelerGUI.Go"));
		jLabelAlert.setText(ResourceBundle.getBundle(etik).getString("TravelerGUI.Alert")); //$NON-NLS-1$ //$NON-NLS-2$

		jButtonRemoveRide.setText(ResourceBundle.getBundle(etik).getString("TravelerGUI.RemoveErreserba"));
		jButtonCreateErreserba.setText(ResourceBundle.getBundle(etik).getString("TravelerGUI.CreateErreserba"));
		jButtonSignOut.setText(ResourceBundle.getBundle(etik).getString("SignOut"));
		jButtonTransactions.setText(ResourceBundle.getBundle(etik).getString("Transactions"));
		jButtonPutMoney.setText(ResourceBundle.getBundle(etik).getString("TravelerGUI.PutMoney"));
		jButtonErreserbaBukatuak
				.setText(ResourceBundle.getBundle(etik).getString("TravelerGUI.ErreserbaBukatuak"));
		this.setTitle(ResourceBundle.getBundle(etik).getString("TravelerGUI.MainTitle") + " - traveler :"
				+ traveler.getEmail());
	}
} // @jve:decl-index=0:visual-constraint="0,0"
