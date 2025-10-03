package gui;

import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.Ride;
import domain.Transaction;
import domain.Traveler;
import service.BLFacade;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class CreateErreserbaGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> jComboBoxOrigin = new JComboBox<String>();
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();

	private JComboBox<String> jComboBoxDestination = new JComboBox<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();
	private static String etik = "Etiquetas";
	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.Origin"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.Destination"));
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.RideDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.Rides")); 
	private JLabel jLabelSeats = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.NumberOfSeats"));
	private JTextField jTextFieldSeats = new JTextField();
	private JButton jButtonErreserbatu = new JButton(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.Erreserbatu"));
	private JLabel jLabelPlWrongFormatError = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.PlWrongFormatError"));
	private final JLabel jLabelNotEnoughPlError = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.NotEnoughPlError"));
	private final JLabel jLabelNotEnoughMoneyError = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.NotEnoughMoneyError"));
	private final JLabel jLabelRideNotSelectedError = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.RideNotSelectedError"));
	private final JLabel jLabelReserveOK = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.ReserveOK"));
	private JLabel jLabelPrice2 = new JLabel((String) null);
	private final JButton jButtonCreateAlert = new JButton(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.CreateAlert")); //$NON-NLS-1$ //$NON-NLS-2$
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
	private static String let = "Tahoma";
	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;

	private List<Date> datesWithRidesCurrentMonth = new Vector<Date>();

	private DefaultTableModel tableModelRides;

	private DefaultListModel<String> rideInfo = new DefaultListModel<String>();
	private JList rideList = new JList<String>();

	private Traveler traveler;
	


	public CreateErreserbaGUI(Traveler t, Ride r)
	{
		this.traveler=t;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.CreateErreserba"));
		jLabelPrice2.setFont(new Font(let, Font.BOLD, 14));
		jLabelPrice2.setHorizontalAlignment(SwingConstants.LEFT);
		String moneyBir = String.format("%.2f", traveler.getMoney());
		jLabelPrice2.setText(moneyBir+"€");
		BLFacade facade = DriverGUI.getBusinessLogic();
		
		rideList.setFont(new Font(let, Font.PLAIN, 12));
		
		rideList.setModel(rideInfo);
		rideList.setBounds(130, 257, 445, 150);
		getContentPane().add(rideList);
		
		jLabelSeats.setBounds(new Rectangle(6, 119, 173, 20));
		jTextFieldSeats.setBounds(new Rectangle(130, 120, 60, 20));

		jButtonBack.setBounds(new Rectangle(194, 417, 130, 30));

		jButtonBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				TravelerGUI tgui = new TravelerGUI(traveler);
				tgui.setVisible(true);
			}
		});
		
		jButtonCreateAlert.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				CreateAlertaGUI tgui = new CreateAlertaGUI(traveler);
				tgui.setVisible(true);
			}
		});
		
		jButtonErreserbatu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jLabelPlWrongFormatError.setVisible(false);
				jLabelNotEnoughPlError.setVisible(false);
				jLabelNotEnoughMoneyError.setVisible(false);
				jLabelRideNotSelectedError.setVisible(false);
				jLabelReserveOK.setVisible(false);
				Object unekoRide = rideList.getSelectedValue();
				try {
					int places = Integer.parseInt(jTextFieldSeats.getText());
					if (unekoRide != null) {
						String aukeratua = (String) unekoRide;
						String[] zatiak = aukeratua.split(":");
						Ride r = facade.getRide(Integer.parseInt(zatiak[0]));
						double price = (r.getPrice() * places);
						if (places < 1) jLabelPlWrongFormatError.setVisible(true);
						else if (traveler.getMoney() < price) jLabelNotEnoughMoneyError.setVisible(true);
						else if (r.getnPlaces() >= places) {
							facade.erreserbaSortu(r, places, traveler);
							traveler = facade.badagoTraveler(traveler.getEmail());
							facade.addTransactionT(new Transaction(-r.getPrice()*places, traveler.getMoney(), "Payment/Pago/Ordainketa"), traveler);
							jLabelReserveOK.setVisible(true);
							traveler = facade.badagoTraveler(traveler.getEmail());
							String moneyBir = String.format("%.2f", traveler.getMoney());
							jLabelPrice2.setText(moneyBir+"€");
						} else  jLabelNotEnoughPlError.setVisible(true);
					} else jLabelRideNotSelectedError.setVisible(true);
				
				} catch(Exception ex) {
					ex.getMessage();
					jLabelPlWrongFormatError.setVisible(true); 
				}
			}
		});
		
		List<String> origins=facade.getDepartCities();
		
		for(String location:origins) originLocations.addElement(location);
		
		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelDestination.setBounds(6, 81, 61, 16);
		getContentPane().add(jLabelOrigin);
		this.getContentPane().add(jLabelSeats, null);
		this.getContentPane().add(jTextFieldSeats, null);
		getContentPane().add(jLabelDestination);

		jComboBoxOrigin.setModel(originLocations);
		jComboBoxOrigin.setBounds(new Rectangle(103, 50, 172, 20));
		
		
		List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
		for(String aciti:aCities) {
			destinationCities.addElement(aciti);
		}
		
		jComboBoxOrigin.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				destinationCities.removeAllElements();
				BLFacade facade = DriverGUI.getBusinessLogic();

				List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
				for(String aciti:aCities) {
					destinationCities.addElement(aciti);
				}
			}
		});


		jComboBoxDestination.setModel(destinationCities);
		jComboBoxDestination.setBounds(new Rectangle(103, 80, 172, 20));
		jComboBoxDestination.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,	new Color(210,228,238));

				BLFacade facade = DriverGUI.getBusinessLogic();

				datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);

			}
		});

		this.getContentPane().add(jButtonBack, null);
		this.getContentPane().add(jComboBoxOrigin, null);

		this.getContentPane().add(jComboBoxDestination, null);


		jCalendar1.setBounds(new Rectangle(300, 50, 225, 150));


		// Code for JCalendar
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					

					
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						

						jCalendar1.setCalendar(calendarAct);

					}
					
					try {
						

						BLFacade facade = DriverGUI.getBusinessLogic();
						List<domain.Ride> rides=facade.getRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),UtilDate.trim(jCalendar1.getDate()));

						if (rides.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle(etik).getString("FindRidesGUI.NoRides")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle(etik).getString("FindRidesGUI.Rides")+ ": "+dateformat1.format(calendarAct.getTime()));
						rideInfo.clear();
						for (domain.Ride ride:rides){
							rideInfo.addElement(ride.toString());	
						}
						datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
						paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);


					} catch (Exception e1) {

						
					}
				}
			} 
			
		});

		this.getContentPane().add(jCalendar1, null);
		datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
		paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
		
		jLabelPrice2.setBounds(new Rectangle(6, 159, 61, 20));
		jLabelPrice2.setBounds(6, 10, 112, 30);
		getContentPane().add(jLabelPrice2);
		
		jButtonErreserbatu.setBounds(new Rectangle(194, 417, 130, 30));
		jButtonErreserbatu.setBounds(372, 417, 130, 30);
		getContentPane().add(jButtonErreserbatu);
		jLabelPlWrongFormatError.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelPlWrongFormatError.setVisible(false);
		jLabelPlWrongFormatError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelPlWrongFormatError.setForeground(new Color(255, 0, 0));
		jLabelPlWrongFormatError.setBounds(new Rectangle(6, 159, 61, 20));
		jLabelPlWrongFormatError.setBounds(172, 235, 346, 20);
		getContentPane().add(jLabelPlWrongFormatError);
		jLabelNotEnoughPlError.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelNotEnoughPlError.setVisible(false);
		jLabelNotEnoughPlError.setForeground(Color.RED);
		jLabelNotEnoughPlError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelNotEnoughPlError.setBounds(new Rectangle(6, 159, 61, 20));
		jLabelNotEnoughPlError.setBounds(172, 235, 346, 20);
		getContentPane().add(jLabelNotEnoughPlError);
		jLabelNotEnoughMoneyError.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelNotEnoughMoneyError.setVisible(false);
		jLabelNotEnoughMoneyError.setForeground(Color.RED);
		jLabelNotEnoughMoneyError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelNotEnoughMoneyError.setBounds(new Rectangle(6, 159, 61, 20));
		jLabelNotEnoughMoneyError.setBounds(172, 235, 346, 20);
		getContentPane().add(jLabelNotEnoughMoneyError);
		jLabelRideNotSelectedError.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelRideNotSelectedError.setVisible(false);
		jLabelRideNotSelectedError.setForeground(Color.RED);
		jLabelRideNotSelectedError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelRideNotSelectedError.setBounds(new Rectangle(6, 159, 61, 20));
		jLabelRideNotSelectedError.setBounds(172, 235, 346, 20);
		getContentPane().add(jLabelRideNotSelectedError);
		jLabelReserveOK.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelReserveOK.setVisible(false);
		jLabelReserveOK.setForeground(new Color(0, 255, 0));
		jLabelReserveOK.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelReserveOK.setBounds(new Rectangle(6, 159, 61, 20));
		jLabelReserveOK.setBounds(172, 235, 346, 20);
		getContentPane().add(jLabelReserveOK);
		jButtonCreateAlert.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jButtonCreateAlert.setBounds(17, 165, 162, 21);
		
		getContentPane().add(jButtonCreateAlert);
		
		if(r!=null) {
			jComboBoxOrigin.setSelectedItem(r.getFrom());
			jComboBoxDestination.setSelectedItem(r.getTo());
			jCalendar1.setDate(r.getDate());

		}

	}
	public static void paintDaysWithEvents(JCalendar jCalendar,List<Date> datesWithEventsCurrentMonth, Color color) {
		//		// For each day with events in current month, the background color for that day is changed to cyan.


		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		//if (Locale.getDefault().equals(new Locale("es")))
			//offset += 4;
		//else
			offset += 5;


		for (Date d:datesWithEventsCurrentMonth){

			calendar.setTime(d);


			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(color);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);


	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
