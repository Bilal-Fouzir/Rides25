package gui;

import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Driver;
import domain.Ride;
import service.BLFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class RemoveRideGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Driver driver;
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();
	
	private DefaultListModel<String> rideInfo = new DefaultListModel<String>();
	private JList rideList = new JList<String>();
	private static String etik = "Etiquetas";
	private JLabel jLabelNotSelectedError = new JLabel(ResourceBundle.getBundle(etik).getString("RemoveRideGUI.NotSelectedError"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.Rides")); 
	private JButton jButtonRemoveRide = new JButton(ResourceBundle.getBundle(etik).getString("RemoveRideGUI.RemoveRide"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));


	private JTable tableRides= new JTable();

	private DefaultTableModel tableModelRides;

	private static String let = "Tahoma";
	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.Driver"), 
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.NPlaces"), 
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.Price")
	};
	private final JLabel jLabelErreserbaDagoError = new JLabel(ResourceBundle.getBundle(etik).getString("RemoveRideGUI.ErreserbaDagoError")); 
	private final JScrollPane scrollPane = new JScrollPane();


	public RemoveRideGUI(Driver d)
	{
		BLFacade facade = DriverGUI.getBusinessLogic();
		this.driver=d;
		
		rideInfo.clear();
		for (Ride ride : facade.getAllRides(driver)) {
            rideInfo.addElement(ride.toString());
        }
		
		
		jLabelNotSelectedError.setVisible(false);
		jLabelErreserbaDagoError.setVisible(false);
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle(etik).getString("RemoveRideGUI.RemoveRide"));
		jLabelEvents.setFont(new Font("Tahoma", Font.BOLD, 20));
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
		
		jButtonRemoveRide.setBounds(new Rectangle(200, 417, 130, 30));
		jButtonRemoveRide.setBounds(360, 417, 130, 30);
		getContentPane().add(jButtonRemoveRide);
		
		jLabelNotSelectedError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelNotSelectedError.setForeground(new Color(255, 0, 0));
		jLabelNotSelectedError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelNotSelectedError.setBounds(172, 387, 346, 13);
		getContentPane().add(jLabelNotSelectedError);
		jLabelErreserbaDagoError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelErreserbaDagoError.setForeground(new Color(255, 0, 0));
		jLabelErreserbaDagoError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelErreserbaDagoError.setBounds(172, 387, 346, 13);
		
		getContentPane().add(jLabelErreserbaDagoError);
		scrollPane.setBounds(40, 98, 606, 268);
		
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(rideList);
		rideList.setFont(new Font(let, Font.PLAIN, 14));
		
		rideList.setModel(rideInfo);
		
		jButtonRemoveRide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelNotSelectedError.setVisible(false);
				jLabelErreserbaDagoError.setVisible(false);
				Object unekoRide = rideList.getSelectedValue();
				if (unekoRide != null) {
		            String aukeratua = (String) unekoRide;
					String[] zatiak = aukeratua.split(":");
					Ride ride = facade.getRide(Integer.parseInt(zatiak[0]));
					if (facade.erreserbaDauka(ride) && !ride.isBukatuta()) jLabelErreserbaDagoError.setVisible(true);
					else {
						facade.removeRide(zatiak[0]);
						rideInfo.clear();
						for (Ride r : facade.getAllRides(driver)) {
							rideInfo.addElement(r.toString());
						}
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
