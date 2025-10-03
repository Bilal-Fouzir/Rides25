package gui;

import java.text.DateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import configuration.UtilDate;
import domain.Alerta;
import domain.Driver;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import service.BLFacade;

public class CreateRideGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private DefaultComboBoxModel<String> defaultCars = new DefaultComboBoxModel<String>();
	private JComboBox comboBoxKotxeak = new JComboBox();
	private Driver driver;
	private JTextField fieldOrigin=new JTextField();
	private JTextField fieldDestination=new JTextField();
	private static String etik = "Etiquetas";
	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.GoingTo")); 
	private JLabel jLabelSeats = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.NumberOfSeats"));
	private JLabel jLabRideDate = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.RideDate"));
	private JLabel jLabelPrice = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.Price"));
	private JLabel jLabelKotxea = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.Kotxea"));

	
	
	private JTextField jTextFieldSeats = new JTextField();
	private JTextField jTextFieldPrice = new JTextField();

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle(etik).getString("CreateRideGUI.CreateRide"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	
	private List<Date> datesWithEventsCurrentMonth;


	public CreateRideGUI(Driver driver) {

		this.driver=driver;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle(etik).getString("CreateRideGUI.CreateRide"));

		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelSeats.setBounds(new Rectangle(6, 119, 173, 20));
		jTextFieldSeats.setBounds(new Rectangle(139, 119, 60, 20));
		
		jLabelPrice.setBounds(new Rectangle(6, 159, 173, 20));
		jTextFieldPrice.setBounds(new Rectangle(139, 159, 60, 20));

		jCalendar.setBounds(new Rectangle(300, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(133, 263, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonBack.setBounds(new Rectangle(308, 263, 130, 30));
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				DriverGUI dgui = new DriverGUI(driver);
				dgui.setVisible(true);
			}
		});
		jLabelMsg.setFont(new Font("Tahoma", Font.BOLD, 14));
		jLabelMsg.setHorizontalAlignment(SwingConstants.CENTER);

		jLabelMsg.setBounds(new Rectangle(133, 221, 305, 20));
		jLabelMsg.setForeground(new Color(0, 255, 0));

		jLabelError.setBounds(new Rectangle(6, 233, 320, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonBack, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldSeats, null);

		this.getContentPane().add(jLabelSeats, null);
		this.getContentPane().add(jLabelOrigin, null);
		

		

		this.getContentPane().add(jCalendar, null);
		
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(jTextFieldPrice, null);

		
		
		
		BLFacade facade = DriverGUI.getBusinessLogic();
		//datesWithEventsCurrentMonth=facade.getThisMonthDatesWithRides("a","b",jCalendar.getDate());		
		
		jLabRideDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabRideDate.setBounds(298, 16, 140, 25);
		getContentPane().add(jLabRideDate);
		
		jLabelDestination.setBounds(6, 81, 61, 16);
		getContentPane().add(jLabelDestination);
		
		
		fieldOrigin.setBounds(100, 53, 130, 26);
		getContentPane().add(fieldOrigin);
		fieldOrigin.setColumns(10);
		
		
		fieldDestination.setBounds(100, 81, 130, 26);
		getContentPane().add(fieldDestination);
		fieldDestination.setColumns(10);
		
		jLabelKotxea.setBounds(new Rectangle(6, 159, 173, 20));
		jLabelKotxea.setBounds(6, 189, 60, 20);
		getContentPane().add(jLabelKotxea);
		
		comboBoxKotxeak.setBounds(100, 189, 163, 21);
		getContentPane().add(comboBoxKotxeak);
		
		List<domain.Kotxea> cars=facade.getCars(driver);
		for (domain.Kotxea kotxe:cars){
			defaultCars.addElement(kotxe.toString());	
		}
		comboBoxKotxeak.setModel(defaultCars);
		
		 //Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//			
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);						
	
					}
					jCalendar.setCalendar(calendarAct);
					int offset = jCalendar.getCalendar().get(Calendar.DAY_OF_WEEK);
					
					
							offset += 5;
				Component o = (Component) jCalendar.getDayChooser().getDayPanel().getComponent(jCalendar.getCalendar().get(Calendar.DAY_OF_MONTH) + offset);
				}}});
		
	}	 
	private void jButtonCreate_actionPerformed(ActionEvent e) {
		jLabelMsg.setText("");
		String error=field_Errors();
		if (error!=null) 
			jLabelMsg.setText(error);
		else
			try {
				BLFacade facade = DriverGUI.getBusinessLogic();
				int inputSeats = Integer.parseInt(jTextFieldSeats.getText());
				float price = Float.parseFloat(jTextFieldPrice.getText());

				Ride r=facade.createRide(fieldOrigin.getText(), fieldDestination.getText(), UtilDate.trim(jCalendar.getDate()), inputSeats, price, driver.getEmail(), comboBoxKotxeak.getSelectedItem().toString());
				jLabelMsg.setText(ResourceBundle.getBundle(etik).getString("CreateRideGUI.RideCreated"));
				for(Alerta a : facade.getAllAlertak()) {
					if(!a.isAktibatua() && a.getJatorria().equals(r.getFrom()) && a.getHelburua().equals(r.getTo()) && (r.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(a.getNora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || r.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(a.getNora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) && (r.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(a.getNora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || r.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(a.getNora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) && (r.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(a.getNoiztik().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || r.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(a.getNoiztik().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())))){
						facade.aktibatuAlerta(a, r);
					}
				}
				
			} catch (RideMustBeLaterThanTodayException e1){
				// TODO Auto-generated catch block
				jLabelMsg.setText(e1.getMessage());
			} catch (RideAlreadyExistException e1) {
				// TODO Auto-generated catch block
				jLabelMsg.setText(e1.getMessage());
			}catch(Exception e2) {
				jLabelMsg.setText(e2.getMessage());
			}

		}
	

	private String field_Errors() {
		
		try {
			if ((fieldOrigin.getText().length()==0) || (fieldDestination.getText().length()==0) || (jTextFieldSeats.getText().length()==0) || (jTextFieldPrice.getText().length()==0))
				return ResourceBundle.getBundle(etik).getString("CreateRideGUI.ErrorQuery");
			else {

				// trigger an exception if the introduced string is not a number
				int inputSeats = Integer.parseInt(jTextFieldSeats.getText());

				if (inputSeats <= 0) {
					return ResourceBundle.getBundle(etik).getString("CreateRideGUI.SeatsMustBeGreaterThan0");
				}
				else {
					float price = Float.parseFloat(jTextFieldPrice.getText());
					if (price <= 0) 
						return ResourceBundle.getBundle(etik).getString("CreateRideGUI.PriceMustBeGreaterThan0");
					
					else 
						return null;
						
				}
			}
		} catch (java.lang.NumberFormatException e1) {

			return  ResourceBundle.getBundle(etik).getString("CreateRideGUI.ErrorNumber");		
		} catch (Exception e1) {

			
			return null;

		}
	}
}
