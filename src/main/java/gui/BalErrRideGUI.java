package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import configuration.UtilDate;
import domain.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import service.BLFacade;

public class BalErrRideGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	
	private Driver driver;
	private Ride ride;
	
	private DefaultListModel<String> rideInfo = new DefaultListModel<String>();
	private static String etik = "Etiquetas";
	private JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle(etik).getString("BukatutakoRidesGUI.SelectRide"));
	private JButton jButtonAccept = new JButton(ResourceBundle.getBundle(etik).getString("BalErrRideGUI.Accept"));
	private JButton jButtonReject = new JButton(ResourceBundle.getBundle(etik).getString("BalErrRideGUI.Reject"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
	private final JLabel jLabelNotSelected = new JLabel(ResourceBundle.getBundle(etik).getString("TakeMoneyGUI.WrongFormatError"));
	private final JLabel jLabelNotAClaim = new JLabel(ResourceBundle.getBundle(etik).getString("BalErrRideGUI.NotAClaim"));
	private JLabel jLabelAccepted = new JLabel(ResourceBundle.getBundle(etik).getString("BalErrRideGUI.Accepted")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel jLabelRejected = new JLabel(ResourceBundle.getBundle(etik).getString("BalErrRideGUI.Rejected")); //
	private JLabel jLabelNotEnoughMoney = new JLabel(ResourceBundle.getBundle(etik).getString("TakeMoneyGUI.LessMoneyError")); //
	private final JScrollPane scrollPane = new JScrollPane();
	private static String let = "Tahoma";


	public BalErrRideGUI(Driver d, Ride r) {

		setTitle(ResourceBundle.getBundle(etik).getString("BukatutakoRidesGUI.SelectRide"));
		jLabelNotAClaim.setVisible(false);
		jLabelNotSelected.setVisible(false);
		jLabelRejected.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelRejected.setVisible(false);
		jLabelAccepted.setVisible(false);
		jLabelNotEnoughMoney.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelNotEnoughMoney.setVisible(false);
		
		BLFacade facade = DriverGUI.getBusinessLogic();

		this.driver=d;
		this.ride=r;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));

		
		rideInfo.clear();
		for (Balorazioa b : ride.getBalorazioList()) {
			rideInfo.addElement(b.toString());
        }
		for (Erreklamazioa e : ride.getErreklamazioList()) {
			if (!e.isEginda()) rideInfo.addElement(e.toString());
        }
		
		jButtonAccept.setBounds(new Rectangle(276, 270, 130, 30));

	
		this.getContentPane().add(jButtonBack, null);
		this.getContentPane().add(jButtonAccept, null);
		jLabelTitle.setFont(new Font(let, Font.BOLD, 15));

		
		
		jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelTitle.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelTitle.setBounds(0, 22, 588, 25);
		getContentPane().add(jLabelTitle);
		String moneyBir = String.format("%.2f", driver.getMoney());
		
		
		jButtonBack.setBounds(new Rectangle(45, 270, 130, 30));
		jLabelNotSelected.setForeground(Color.RED);
		jLabelNotSelected.setFont(new Font(let, Font.BOLD | Font.ITALIC, 11));
		jLabelNotSelected.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelNotSelected.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelNotSelected.setBounds(133, 240, 326, 25);
		
		getContentPane().add(jLabelNotSelected);
		jLabelNotAClaim.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelNotAClaim.setForeground(Color.RED);
		jLabelNotAClaim.setFont(new Font(let, Font.BOLD | Font.ITALIC, 11));
		jLabelNotAClaim.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelNotAClaim.setBounds(133, 240, 326, 25);
		
		getContentPane().add(jLabelNotAClaim);
		scrollPane.setBounds(23, 58, 545, 171);
		
		getContentPane().add(scrollPane);
		
		JList balErrList = new JList();
		scrollPane.setViewportView(balErrList);
		balErrList.setModel(rideInfo);
		
		
		jButtonReject.setBounds(new Rectangle(329, 270, 130, 30));
		jButtonReject.setBounds(438, 270, 130, 30);
		getContentPane().add(jButtonReject);
		
		jLabelAccepted.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelAccepted.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelAccepted.setForeground(new Color(0, 255, 0));
		jLabelAccepted.setBounds(133, 240, 326, 25);
		getContentPane().add(jLabelAccepted);
		
		jLabelRejected.setForeground(Color.GREEN);
		jLabelRejected.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelRejected.setBounds(133, 240, 326, 25);
		getContentPane().add(jLabelRejected);
		
		jLabelNotEnoughMoney.setForeground(new Color(255, 0, 0));
		jLabelNotEnoughMoney.setFont(new Font(let, Font.BOLD | Font.ITALIC, 10));
		jLabelNotEnoughMoney.setBounds(133, 240, 326, 25);
		getContentPane().add(jLabelNotEnoughMoney);
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				BukatutakoRidesGUI dgui = new BukatutakoRidesGUI(driver);
				dgui.setVisible(true);
			}
		});
		
		jButtonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelNotAClaim.setVisible(false);
				jLabelNotSelected.setVisible(false);
				jLabelRejected.setVisible(false);
				jLabelAccepted.setVisible(false);
				jLabelNotEnoughMoney.setVisible(false);
				if (balErrList.getSelectedValue() != null) {
					String unekoa = (String) balErrList.getSelectedValue();
					String[] zatiak = unekoa.split("\\|");
					if (zatiak[0].equals(ResourceBundle.getBundle(etik).getString("Err") + " ")) {
						String prezioa = zatiak[3].substring(0, zatiak[3].length() - 1);
						if (Double.parseDouble(prezioa) <= driver.getMoney()) {
							facade.acceptClaim(zatiak[1], driver);
							driver = facade.badagoDriver(d.getEmail());
							ride= facade.getRide(ride.getRideNumber());
							rideInfo.clear();
							for (Balorazioa b : ride.getBalorazioList()) {
								rideInfo.addElement(b.toString());
							}
							for (Erreklamazioa err : ride.getErreklamazioList()) {
								if (!err.isEginda()) rideInfo.addElement(err.toString());
							}
							jLabelAccepted.setVisible(true);
						} else {
							jLabelNotEnoughMoney.setVisible(true);
						}

					} else {
						jLabelNotAClaim.setVisible(true);
					}
				} else {
					jLabelNotSelected.setVisible(true);
				}

			}
		});

		jButtonReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelNotAClaim.setVisible(false);
				jLabelNotSelected.setVisible(false);
				jLabelRejected.setVisible(false);
				jLabelAccepted.setVisible(false);
				jLabelNotEnoughMoney.setVisible(false);
				if (balErrList.getSelectedValue() != null) {
					String unekoa = (String) balErrList.getSelectedValue();
					String[] zatiak = unekoa.split("\\|");
					if (zatiak[0].equals(ResourceBundle.getBundle(etik).getString("Err") + " ")) {
						balErEdukiaBete(facade, d, zatiak);
					} else {
						jLabelNotAClaim.setVisible(true);
					}
				} else {
					jLabelNotSelected.setVisible(true);
				}

			}
		});

		
	}	
	public void balErEdukiaBete(BLFacade facade,Driver d, String[] zatiak) {
		facade.acceptClaim(zatiak[1], driver);
		driver = facade.badagoDriver(d.getEmail());
		ride= facade.getRide(ride.getRideNumber());
		rideInfo.clear();
		for (Balorazioa b : ride.getBalorazioList()) {
			rideInfo.addElement(b.toString());
		}
		for (Erreklamazioa err : ride.getErreklamazioList()) {
			if (!err.isEginda()) rideInfo.addElement(err.toString());
		}
		jLabelAccepted.setVisible(true);
	}

}
