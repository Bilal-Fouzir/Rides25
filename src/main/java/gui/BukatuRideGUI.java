package gui;

import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Driver;
import domain.Ride;
import exceptions.RideMustBeLaterThanTodayException;
import service.BLFacade;

import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class BukatuRideGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Driver driver;
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();
	
	private DefaultListModel<String> rideInfo = new DefaultListModel<String>();
	private JList rideList = new JList<String>();
	private static String etik = "Etiquetas";
	private JLabel jLabelNotSelectedError = new JLabel(ResourceBundle.getBundle(etik).getString("RemoveRideGUI.NotSelectedError"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.Rides")); 
	private JButton jButtonBukatuRide = new JButton(ResourceBundle.getBundle(etik).getString("BukatuRideGUI.BukatuRide"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
	private static String let = "Tahoma";

	private JTable tableRides= new JTable();

	private DefaultTableModel tableModelRides;


	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.Driver"), 
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.NPlaces"), 
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.Price")
	};
	private final JLabel jLabelDateError = new JLabel(ResourceBundle.getBundle(etik).getString("BukatuRideGUI.DateError")); 
	private final JLabel jLabelMoney = new JLabel();
	private final JScrollPane scrollPane = new JScrollPane();
	


	public BukatuRideGUI(Driver d)
	{
		BLFacade facade = DriverGUI.getBusinessLogic();
		this.driver=d;
		
		String moneyBir = String.format("%.2f", driver.getMoney());
		jLabelMoney.setText(moneyBir+"€");
		
		rideInfo.clear();
		for (Ride ride : facade.getAllRides(driver)) {
			if(!ride.isBukatuta())
            rideInfo.addElement(ride.toString());
        }
		
		
		jLabelNotSelectedError.setVisible(false);
		jLabelDateError.setVisible(false);
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle(etik).getString("DriverGUI.Bukatu"));
		jLabelEvents.setFont(new Font(let, Font.BOLD, 20));
		jLabelEvents.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelEvents.setBounds(200, 37, 290, 30);
		this.getContentPane().add(jLabelEvents);

		jButtonBack.setBounds(new Rectangle(200, 417, 130, 30));

		jButtonBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				DriverGUI dgui = new DriverGUI(driver);
				dgui.setVisible(true);
			}
		});
	

		this.getContentPane().add(jButtonBack, null);

		//scrollPaneEvents.setViewportView(tableRides);
		tableModelRides = new DefaultTableModel(null, columnNamesRides);

		tableRides.setModel(tableModelRides);

		tableModelRides.setDataVector(null, columnNamesRides);
		tableModelRides.setColumnCount(4); // another column added to allocate ride objects

		tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);

		tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3));
		
		jButtonBukatuRide.setBounds(new Rectangle(200, 417, 130, 30));
		jButtonBukatuRide.setBounds(360, 417, 130, 30);
		getContentPane().add(jButtonBukatuRide);
		
		jLabelNotSelectedError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelNotSelectedError.setForeground(new Color(255, 0, 0));
		jLabelNotSelectedError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelNotSelectedError.setBounds(172, 387, 346, 13);
		getContentPane().add(jLabelNotSelectedError);
		jLabelDateError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelDateError.setForeground(new Color(255, 0, 0));
		jLabelDateError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDateError.setBounds(172, 387, 346, 13);
		
		getContentPane().add(jLabelDateError);
		jLabelMoney.setHorizontalAlignment(SwingConstants.LEFT);
		jLabelMoney.setFont(new Font(let, Font.BOLD, 14));
		jLabelMoney.setBackground(Color.WHITE);
		jLabelMoney.setAlignmentX(0.5f);
		jLabelMoney.setBounds(10, 10, 146, 19);
		
		getContentPane().add(jLabelMoney);
		scrollPane.setBounds(40, 98, 606, 268);
		
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(rideList);
		rideList.setFont(new Font(let, Font.PLAIN, 14));
		
		rideList.setModel(rideInfo);
		
		jButtonBukatuRide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelNotSelectedError.setVisible(false);
				jLabelDateError.setVisible(false);
				Object unekoRide = rideList.getSelectedValue();
				if (unekoRide != null) {
		            String aukeratua = (String) unekoRide;
					String[] zatiak = aukeratua.split(":");
					LocalDate rideDate = facade.getRide(Integer.parseInt(zatiak[0])).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (rideDate.isBefore(LocalDate.now())) {
						String email = facade.getRide(Integer.parseInt(zatiak[0])).getDriver().getEmail();
						facade.bukatuRide(zatiak[0]);
						driver = facade.badagoDriver(email);
						String moneyBir = String.format("%.2f", driver.getMoney());
						jLabelMoney.setText(moneyBir+"€");
						rideInfo.clear();
						for (Ride ride : facade.getAllRides(driver)) {
							if(!ride.isBukatuta())
				            rideInfo.addElement(ride.toString());
				        }
					}
					else {
						jLabelDateError.setVisible(true);				
						}
				}
				else jLabelNotSelectedError.setVisible(true);
			} 
		});
			
			
		}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
