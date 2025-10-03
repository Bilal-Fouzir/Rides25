package gui;

import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.*;
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


public class AddCarGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();
	private static String etik = "Etiquetas";
	private JLabel jLabelMarka = new JLabel(ResourceBundle.getBundle(etik).getString("AddCarGUI.Marka"));
	private JLabel jLabelModeloa = new JLabel(ResourceBundle.getBundle(etik).getString("AddCarGUI.Modeloa"));
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.RideDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.Rides")); 
	private JLabel jLabelMatrikula = new JLabel(ResourceBundle.getBundle(etik).getString("AddCarGUI.Matrikula"));
	private JTextField jTextFieldMatrikula = new JTextField();
	private JButton jButtonAddCar = new JButton(ResourceBundle.getBundle(etik).getString("AddCarGUI.AddCar"));
	private static String let = "Tahoma";
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;

	private List<Date> datesWithRidesCurrentMonth = new Vector<Date>();

	private DefaultTableModel tableModelRides;

	private DefaultListModel<String> carInfo = new DefaultListModel<String>();
	private JList carList = new JList<String>();

	private Driver driver;
	private JLabel jLabelPlWrongFormatError = new JLabel(ResourceBundle.getBundle(etik).getString("CreateRideGUI.ErrorQuery"));
	private final JLabel jLabelCarAlreadyExistsError = new JLabel(ResourceBundle.getBundle(etik).getString("AddCarGUI.CarAlreadyExistsError"));
	private final JLabel jLabelNotEnoughMoneyError = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.NotEnoughMoneyError"));
	private final JLabel jLabelRideNotSelectedError = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.RideNotSelectedError"));
	private final JLabel jLabelCarOK = new JLabel(ResourceBundle.getBundle(etik).getString("AddCarGUI.CarOK"));
	private JTextField textField;
	private JTextField textField_1;


	public AddCarGUI(Driver d)
	{
		this.driver=d;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle(etik).getString("AddCarGUI.AddCar"));
		String moneyBir = String.format("%.2f", driver.getMoney());
		BLFacade facade = DriverGUI.getBusinessLogic();
		
		carList.setFont(new Font(let, Font.PLAIN, 12));
		
		carList.setModel(carInfo);
		carList.setBounds(130, 257, 445, 150);
		getContentPane().add(carList);
		
		jLabelMatrikula.setBounds(new Rectangle(6, 119, 173, 20));
		jTextFieldMatrikula.setBounds(new Rectangle(130, 120, 145, 20));

		jButtonBack.setBounds(new Rectangle(194, 417, 130, 30));

		jButtonBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				DriverGUI tgui = new DriverGUI(driver);
				tgui.setVisible(true);
			}
		});
		
		jButtonAddCar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jLabelPlWrongFormatError.setVisible(false);
				jLabelCarAlreadyExistsError.setVisible(false);
				jLabelNotEnoughMoneyError.setVisible(false);
				jLabelRideNotSelectedError.setVisible(false);
				jLabelCarOK.setVisible(false);
				
		    	String matrikula = jTextFieldMatrikula.getText();
				String marka = textField.getText();
				String modeloa = textField_1.getText();
				try {
					if ((textField.getText().length()==0) || (textField_1.getText().length()==0) || (jTextFieldMatrikula.getText().length()==0)) {
						jLabelPlWrongFormatError.setVisible(true);
					}
					
				else {
					facade.addCar(marka, modeloa, matrikula, d);
					jLabelCarOK.setVisible(true);
					carInfo.clear();
					List<domain.Kotxea> cars=facade.getCars(driver);
					for (domain.Kotxea ride:cars){
						carInfo.addElement(ride.toString());	
					}
				}
				} catch (Exception ee) {
					jLabelCarAlreadyExistsError.setVisible(true);
					ee.getMessage();
				}
				
			}
		});
		
		List<domain.Kotxea> cars=facade.getCars(driver);
		for (domain.Kotxea ride:cars){
			carInfo.addElement(ride.toString());	
		}
		
		jLabelMarka.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelModeloa.setBounds(6, 86, 61, 16);
		getContentPane().add(jLabelMarka);
		this.getContentPane().add(jLabelMatrikula, null);
		this.getContentPane().add(jTextFieldMatrikula, null);
		getContentPane().add(jLabelModeloa);
		
		this.getContentPane().add(jButtonBack, null);
		
		jButtonAddCar.setBounds(new Rectangle(194, 417, 130, 30));
		jButtonAddCar.setBounds(372, 417, 130, 30);
		getContentPane().add(jButtonAddCar);
		jLabelPlWrongFormatError.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelPlWrongFormatError.setVisible(false);
		jLabelPlWrongFormatError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelPlWrongFormatError.setForeground(new Color(255, 0, 0));
		jLabelPlWrongFormatError.setBounds(new Rectangle(6, 159, 61, 20));
		jLabelPlWrongFormatError.setBounds(172, 235, 346, 20);
		getContentPane().add(jLabelPlWrongFormatError);
		jLabelCarAlreadyExistsError.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelCarAlreadyExistsError.setVisible(false);
		jLabelCarAlreadyExistsError.setForeground(Color.RED);
		jLabelCarAlreadyExistsError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelCarAlreadyExistsError.setBounds(new Rectangle(6, 159, 61, 20));
		jLabelCarAlreadyExistsError.setBounds(172, 235, 346, 20);
		getContentPane().add(jLabelCarAlreadyExistsError);
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
		jLabelCarOK.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelCarOK.setVisible(false);
		jLabelCarOK.setForeground(new Color(0, 255, 0));
		jLabelCarOK.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelCarOK.setBounds(new Rectangle(6, 159, 61, 20));
		jLabelCarOK.setBounds(172, 235, 346, 20);
		getContentPane().add(jLabelCarOK);
		
		textField = new JTextField();
		textField.setBounds(new Rectangle(130, 120, 60, 20));
		textField.setBounds(103, 57, 172, 20);
		getContentPane().add(textField);
		
		textField_1 = new JTextField();
		textField_1.setBounds(new Rectangle(130, 120, 60, 20));
		textField_1.setBounds(103, 89, 172, 20);
		getContentPane().add(textField_1);

	}
	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
