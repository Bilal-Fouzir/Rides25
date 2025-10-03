package gui;

import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Driver;
import domain.Erreserba;
import domain.Ride;
import domain.Transaction;
import domain.Traveler;
import service.BLFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class RemoveErreserbaGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Traveler traveler;
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();
	
	private DefaultListModel<String> erreserbaInfo = new DefaultListModel<String>();
	private JList erreserbaList = new JList<String>();
	private static String etik = "Etiquetas";
	private JLabel jLabelNotSelectedError = new JLabel(ResourceBundle.getBundle(etik).getString("RemoveErreserbaGUI.NotSelectedError"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle(etik).getString("RemoveErreserbaGUI.Reserves")); 
	private JButton jButtonRemoveErreserba = new JButton(ResourceBundle.getBundle(etik).getString("RemoveErreserbaGUI.RemoveErreserba"));
	private JLabel jLabelDefinitiveError = new JLabel(ResourceBundle.getBundle(etik).getString("RemoveErreserbaGUI.DefinitiveError")); //$NON-NLS-1$ //$NON-NLS-2$
	private static String let = "Tahoma";
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));


	private JTable tableRides= new JTable();

	private DefaultTableModel tableModelRides;


	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.Driver"), 
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.NPlaces"), 
			ResourceBundle.getBundle(etik).getString("FindRidesGUI.Price")
	};
	private final JLabel jLabelPrice2 = new JLabel((String) null);
	private final JScrollPane scrollPane = new JScrollPane();


	public RemoveErreserbaGUI(Traveler t)
	{
		BLFacade facade = DriverGUI.getBusinessLogic();
		this.traveler=t;
		
		erreserbaInfo.clear();
		for (Erreserba erre : facade.getAllErreserbakT(traveler)) {
            erreserbaInfo.addElement(erre.toString());
        }
		
		
		jLabelNotSelectedError.setVisible(false);
		jLabelDefinitiveError.setVisible(false);	
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle(etik).getString("TravelerGUI.RemoveErreserba"));
		jLabelEvents.setFont(new Font(let, Font.BOLD, 20));
		jLabelEvents.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelEvents.setBounds(200, 37, 290, 30);
		this.getContentPane().add(jLabelEvents);

		jButtonBack.setBounds(new Rectangle(170, 417, 160, 30));

		jButtonBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				TravelerGUI tgui = new TravelerGUI(traveler);
				tgui.setVisible(true);
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
		
		jButtonRemoveErreserba.setBounds(new Rectangle(200, 417, 130, 30));
		jButtonRemoveErreserba.setBounds(360, 417, 160, 30);
		getContentPane().add(jButtonRemoveErreserba);
		
		jLabelNotSelectedError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelNotSelectedError.setForeground(new Color(255, 0, 0));
		jLabelNotSelectedError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelNotSelectedError.setBounds(172, 387, 346, 13);
		getContentPane().add(jLabelNotSelectedError);
		jLabelPrice2.setHorizontalAlignment(SwingConstants.LEFT);
		jLabelPrice2.setFont(new Font(let, Font.BOLD, 14));
		jLabelPrice2.setBounds(new Rectangle(6, 159, 61, 20));
		jLabelPrice2.setBounds(6, 10, 112, 30);
		String moneyBir = String.format("%.2f", traveler.getMoney());
		jLabelPrice2.setText(moneyBir+"€");
		
		getContentPane().add(jLabelPrice2);
		
		jLabelDefinitiveError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDefinitiveError.setForeground(new Color(255, 0, 0));
		jLabelDefinitiveError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 12));
		jLabelDefinitiveError.setBounds(172, 387, 346, 13);
		getContentPane().add(jLabelDefinitiveError);
		scrollPane.setBounds(40, 98, 609, 268);
		
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(erreserbaList);
		erreserbaList.setFont(new Font(let, Font.PLAIN, 14));
		
		erreserbaList.setModel(erreserbaInfo);
		
		jButtonRemoveErreserba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelNotSelectedError.setVisible(false);
				jLabelDefinitiveError.setVisible(false);	
				Object unekoErreserba = erreserbaList.getSelectedValue();
				if (unekoErreserba != null) {
		            String aukeratua = (String) unekoErreserba;
					String[] zatiak = aukeratua.split(":");
					Erreserba er =facade.getErreserba(Integer.parseInt(zatiak[0]));
					if (er.isOnartua()) jLabelDefinitiveError.setVisible(true);	
					else {
						facade.erreserbaEzabatu(er, traveler);
						traveler = facade.badagoTraveler(traveler.getEmail());
						String moneyBir = String.format("%.2f", traveler.getMoney());
						facade.addTransactionT(new Transaction(er.getRide().getPrice()*er.getPlaces(), traveler.getMoney(), "Refund/Devolución/Itzulketa"), traveler);
						jLabelPrice2.setText(moneyBir+"€");
						traveler = facade.badagoTraveler(traveler.getEmail());
						erreserbaInfo.clear();
						for (Erreserba erre : facade.getAllErreserbakT(traveler)) {
							erreserbaInfo.addElement(erre.toString());
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
