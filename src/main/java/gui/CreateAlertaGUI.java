package gui;

import java.text.DateFormat;
import java.time.LocalDate;
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
import domain.Traveler;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import service.BLFacade;

public class CreateAlertaGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private Traveler traveler;
	private JTextField fieldOrigin=new JTextField();
	private JTextField fieldDestination=new JTextField();
	
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.GoingTo"));
	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.LeavingFrom")); 
	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle(etik).getString("CreateAlertaGUI.Create"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
	private JLabel jLabelFrom = new JLabel(ResourceBundle.getBundle(etik).getString("CreateAlertaGUI.From"));
	private JLabel jLabelTo = new JLabel(ResourceBundle.getBundle(etik).getString("CreateAlertaGUI.To"));
	private JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle(etik).getString("CreateAlertaGUI.Title")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel jLabelMsg = new JLabel(ResourceBundle.getBundle(etik).getString("CreateAlertaGUI.AlertOK"));
	private JLabel jLabelError = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.ErrorQuery"));
	private final JLabel jLabelError2 = new JLabel(ResourceBundle.getBundle(etik).getString("CreateAlertaGUI.Error2"));
	
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;
	private JCalendar jCalendar2 = new JCalendar();
	private Calendar calendarAct2 = null;
	private Calendar calendarAnt2 = null;
	private static String etik = "Etiquetas";
	private JScrollPane scrollPaneEvents = new JScrollPane();

	
	
	private List<Date> datesWithEventsCurrentMonth;



	public CreateAlertaGUI(Traveler t) {

		this.traveler=t;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		
		this.setTitle(ResourceBundle.getBundle(etik).getString("CreateAlertaGUI.Title"));

		jLabelDestination.setBounds(new Rectangle(300, 62, 92, 20));

		jCalendar.setBounds(new Rectangle(50, 120, 225, 150));
		jCalendar2.setBounds(new Rectangle(300, 120, 225, 150));

		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(335, 300, 130, 30));
		
		jLabelMsg.setVisible(false);
		jLabelError.setVisible(false);
		jLabelError2.setVisible(false);
		

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date from = null;
				Date to = null;
				boolean error = false;
				jLabelMsg.setVisible(false);
				jLabelError.setVisible(false);
				jLabelError2.setVisible(false);
				if ((fieldOrigin.getText().length()==0) || (fieldDestination.getText().length()==0)) {
					error=true;
					jLabelError.setVisible(true);
				}
				if (!error) {
					try {
						 from = UtilDate.trim(jCalendar.getDate());
						 to = UtilDate.trim(jCalendar2.getDate());
					} catch (Exception ex){
						error=true;
						jLabelError.setVisible(true);
					}
				}
				if (!error) {
					if(from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(LocalDate.now()) || to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
						error=true;
						jLabelError2.setVisible(true);
					}
				}
					
				if(!error) {
					BLFacade facade = DriverGUI.getBusinessLogic();
					Alerta alerta = new Alerta(traveler.getEmail(), fieldOrigin.getText(), fieldDestination.getText(), from, to);
					facade.createAlerta(alerta);
					jLabelMsg.setVisible(true);
				}
			}
		});
		jButtonBack.setBounds(new Rectangle(112, 300, 130, 30));
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				TravelerGUI dgui = new TravelerGUI(traveler);
				dgui.setVisible(true);
			}
		});
		jLabelMsg.setFont(new Font("Tahoma", Font.BOLD, 20));
		jLabelMsg.setHorizontalAlignment(SwingConstants.CENTER);

		jLabelMsg.setBounds(new Rectangle(145, 274, 305, 20));
		jLabelMsg.setForeground(new Color(0, 255, 0));
		jLabelError.setHorizontalAlignment(SwingConstants.CENTER);

		jLabelError.setBounds(new Rectangle(145, 274, 320, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonBack, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jLabelDestination, null);
		

		

		this.getContentPane().add(jCalendar, null);
		this.getContentPane().add(jCalendar2, null);

		
		
		
		BLFacade facade = DriverGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getThisMonthDatesWithRides("a","b",jCalendar.getDate());	
		datesWithEventsCurrentMonth=facade.getThisMonthDatesWithRides("a","b",jCalendar2.getDate());
		
		jLabelOrigin.setBounds(50, 64, 92, 16);
		getContentPane().add(jLabelOrigin);
		
		
		fieldOrigin.setBounds(145, 60, 130, 26);
		getContentPane().add(fieldOrigin);
		fieldOrigin.setColumns(10);
		
		
		fieldDestination.setBounds(395, 60, 130, 26);
		getContentPane().add(fieldDestination);
		fieldDestination.setColumns(10);
		
		
		jLabelTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitle.setBounds(50, 10, 475, 30);
		getContentPane().add(jLabelTitle);
		
		
		jLabelFrom.setBounds(50, 94, 92, 16);
		getContentPane().add(jLabelFrom);
		
		
		jLabelTo.setBounds(300, 94, 92, 16);
		getContentPane().add(jLabelTo);
		jLabelError2.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelError2.setForeground(Color.RED);
		jLabelError2.setBounds(new Rectangle(145, 274, 320, 20));
		jLabelError2.setBounds(145, 274, 320, 20);
		
		getContentPane().add(jLabelError2);
		
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
		
		this.jCalendar2.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//			
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar2.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt2 = (Calendar) propertychangeevent.getOldValue();
					calendarAct2 = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar2.getLocale());
					
					int monthAnt = calendarAnt2.get(Calendar.MONTH);
					int monthAct = calendarAct2.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct2.set(Calendar.MONTH, monthAnt+1);
							calendarAct2.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar2.setCalendar(calendarAct2);						
	
					}
					jCalendar2.setCalendar(calendarAct2);
					int offset = jCalendar2.getCalendar().get(Calendar.DAY_OF_WEEK);
					
					
							offset += 5;
				Component o = (Component) jCalendar2.getDayChooser().getDayPanel().getComponent(jCalendar2.getCalendar().get(Calendar.DAY_OF_MONTH) + offset);
				}}});
		
	}	 
}
