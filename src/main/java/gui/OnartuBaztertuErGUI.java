package gui;

import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Driver;
import domain.Erreserba;
import domain.Ride;
import domain.Transaction;
import service.BLFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class OnartuBaztertuErGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Driver driver;
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();
	
	private DefaultListModel<String> erreserbaInfo = new DefaultListModel<String>();
	private JList erreserbaList = new JList<String>();
	private static String etik = "Etiquetas";
	private JLabel jLabelNotSelectedError = new JLabel(ResourceBundle.getBundle(etik).getString("OnartuBaztertuErGUI.NotSelectedError"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle(etik).getString("OnartuBaztertuErGUI.Onartu/Baztertu")); 
	private JButton jButtonCancelReserve = new JButton(ResourceBundle.getBundle(etik).getString("OnartuBaztertuErGUI.CancelReserve"));
	private JButton jButtonAcceptReserve = new JButton(ResourceBundle.getBundle(etik).getString("OnartuBaztertuErGUI.AcceptReserve"));
	private JLabel jLabelDefinitiveError = new JLabel(ResourceBundle.getBundle(etik).getString("RemoveErreserbaGUI.DefinitiveError"));
	private JLabel jLabelOnartuta = new JLabel(ResourceBundle.getBundle(etik).getString("OnartuBaztertuErGUI.Onartuta"));
	private JLabel jLabelBaztertuta = new JLabel(ResourceBundle.getBundle(etik).getString("OnartuBaztertuErGUI.Baztertuta"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
	private final JLabel jLabelNotEnoughPl = new JLabel(ResourceBundle.getBundle(etik).getString("CreateErreserbaGUI.NotEnoughPlError"));
	private static String let = "Tahoma";

	private JTable tableRides= new JTable();

	private DefaultTableModel tableModelRides;


	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.Driver"), 
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.NPlaces"), 
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.Price")
	};
	private final JScrollPane scrollPane = new JScrollPane();


	public OnartuBaztertuErGUI(Driver d)
	{
		BLFacade facade = DriverGUI.getBusinessLogic();
		this.driver=d;
		
		erreserbaInfo.clear();
		for (Erreserba erre : facade.getAllErreserbakD(driver)) {
			if(!erre.getRide().isBukatuta())
				erreserbaInfo.addElement(erre.toString2());
        } 
            
        
		
		jLabelNotEnoughPl.setVisible(false);
		jLabelNotSelectedError.setVisible(false);
		jLabelOnartuta.setVisible(false);
		jLabelBaztertuta.setVisible(false);
		jLabelDefinitiveError.setVisible(false);
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle(etik).getString("OnartuBaztertuErGUI.Onartu/Baztertu"));
		jLabelEvents.setFont(new Font(let, Font.BOLD, 20));
		jLabelEvents.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelEvents.setBounds(200, 37, 290, 30);
		this.getContentPane().add(jLabelEvents);

		jButtonBack.setBounds(new Rectangle(40, 417, 130, 30));

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
		
		jButtonCancelReserve.setBounds(new Rectangle(200, 417, 130, 30));
		jButtonCancelReserve.setBounds(215, 417, 160, 30);
		getContentPane().add(jButtonCancelReserve);
		
		jLabelNotSelectedError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelNotSelectedError.setForeground(new Color(255, 0, 0));
		jLabelNotSelectedError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelNotSelectedError.setBounds(172, 387, 346, 13);
		getContentPane().add(jLabelNotSelectedError);
		
		jButtonAcceptReserve.setBounds(new Rectangle(200, 417, 130, 30));
		jButtonAcceptReserve.setBounds(395, 417, 160, 30);
		getContentPane().add(jButtonAcceptReserve);
		
		jLabelOnartuta.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelOnartuta.setForeground(new Color(0, 255, 0));
		jLabelOnartuta.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelOnartuta.setBounds(172, 387, 346, 13);
		getContentPane().add(jLabelOnartuta);
		
		jLabelBaztertuta.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelBaztertuta.setForeground(Color.GREEN);
		jLabelBaztertuta.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelBaztertuta.setBounds(172, 388, 346, 13);
		getContentPane().add(jLabelBaztertuta);
		
		jLabelDefinitiveError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDefinitiveError.setForeground(new Color(255, 0, 0));
		jLabelDefinitiveError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelDefinitiveError.setBounds(172, 388, 346, 13);
		getContentPane().add(jLabelDefinitiveError);
		jLabelNotEnoughPl.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelNotEnoughPl.setForeground(new Color(255, 0, 0));
		jLabelNotEnoughPl.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelNotEnoughPl.setBounds(172, 388, 346, 13);
		
		getContentPane().add(jLabelNotEnoughPl);
		scrollPane.setBounds(40, 98, 606, 268);
		
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(erreserbaList);
		erreserbaList.setFont(new Font(let, Font.PLAIN, 14));
		
		erreserbaList.setModel(erreserbaInfo);
		
		jButtonCancelReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelNotSelectedError.setVisible(false);
				jLabelOnartuta.setVisible(false);
				jLabelBaztertuta.setVisible(false);
				jLabelDefinitiveError.setVisible(false);
				Object unekoErreserba = erreserbaList.getSelectedValue();
				if (unekoErreserba != null) {
		            String aukeratua = (String) unekoErreserba;
					String[] zatiak = aukeratua.split(":");
					Erreserba er = facade.getErreserba(Integer.parseInt(zatiak[0]));
					if (er.isOnartua()) jLabelDefinitiveError.setVisible(true);
					else {
						facade.erreserbaEzabatu(er, er.getTraveler());
						facade.addTransactionT(new Transaction(er.getRide().getPrice()*er.getPlaces(), er.getTraveler().getMoney(), "Cancelled/Cancelado/Baztertuta"), er.getTraveler());
						jLabelBaztertuta.setVisible(true);
						erreserbaInfo.clear();
						for (Erreserba erre : facade.getAllErreserbakD(driver)) {
							if(!erre.getRide().isBukatuta())
								erreserbaInfo.addElement(erre.toString2());
				        } 
					}
				}
				else jLabelNotSelectedError.setVisible(true);
			} 
		});
		
		jButtonAcceptReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelNotEnoughPl.setVisible(false);
				jLabelNotSelectedError.setVisible(false);
				jLabelOnartuta.setVisible(false);
				jLabelBaztertuta.setVisible(false);
				jLabelDefinitiveError.setVisible(false);
				Object unekoErreserba = erreserbaList.getSelectedValue();
				if (unekoErreserba != null) {
		            String aukeratua = (String) unekoErreserba;
					String[] zatiak = aukeratua.split(":");
					Erreserba er = facade.getErreserba(Integer.parseInt(zatiak[0]));
					if (er.isOnartua()) jLabelDefinitiveError.setVisible(true);
					else if (er.getPlaces() > er.getRide().getnPlaces()) jLabelNotEnoughPl.setVisible(true);
					else {
						facade.erreserbaOnartu(er);
						jLabelOnartuta.setVisible(true);
						erreserbaInfo.clear();
						for (Erreserba erre : facade.getAllErreserbakD(driver)) {
							if(!erre.getRide().isBukatuta())
								erreserbaInfo.addElement(erre.toString2());
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
