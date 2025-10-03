package gui;

import configuration.UtilDate;

import com.toedter.calendar.JCalendar;


import domain.*;
import service.BLFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class TravelerTransactionsGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Traveler traveler;
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();
	
	private DefaultListModel<String> transactionInfo = new DefaultListModel<String>();
	private static String etik = "Etiquetas";
	private JLabel jLabelNotSelectedError = new JLabel(ResourceBundle.getBundle(etik).getString("RemoveErreserbaGUI.NotSelectedError"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle(etik).getString("Transactions")); 
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


	public TravelerTransactionsGUI(Traveler t)
	{
		BLFacade facade = TravelerGUI.getBusinessLogic();
		this.traveler=t;
		
		transactionInfo.clear();
		
		if(traveler.getTransactions()!=null) {
			for (Transaction trans : traveler.getTransactions()) {
            transactionInfo.addElement(trans.toString());
			}
		} else transactionInfo.addElement(ResourceBundle.getBundle(etik).getString("EmptyTransactions"));
		
		
		
		jLabelNotSelectedError.setVisible(false);
		jLabelDefinitiveError.setVisible(false);	
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle(etik).getString("Transactions"));
		jLabelEvents.setFont(new Font(let, Font.BOLD, 20));
		jLabelEvents.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelEvents.setBounds(200, 37, 290, 30);
		this.getContentPane().add(jLabelEvents);

		jButtonBack.setBounds(new Rectangle(267, 410, 160, 30));

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
		
		JScrollPane transactionListFake = new JScrollPane();
		transactionListFake.setBounds(68, 103, 551, 246);
		getContentPane().add(transactionListFake);
		
		JList transactionList = new JList();
		transactionList.setFont(new Font(let, Font.PLAIN, 12));
		transactionListFake.setViewportView(transactionList);
		transactionList.setModel(transactionInfo);
		
			
			
		}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
