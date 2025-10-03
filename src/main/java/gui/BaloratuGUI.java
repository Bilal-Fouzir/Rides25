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

public class BaloratuGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	
	private Traveler traveler;
	private Erreserba erreserba;
	private static String etik = "Etiquetas";
	private JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle(etik).getString("BaloratuGUI.Title"));
	private JButton jButtonSend = new JButton(ResourceBundle.getBundle(etik).getString("BaloratuGUI.Send"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
    private JLabel jLabelDescription = new JLabel(ResourceBundle.getBundle(etik).getString("BaloratuGUI.Description"));
	private JLabel jLabelReviewTitle = new JLabel(ResourceBundle.getBundle(etik).getString("BaloratuGUI.ReviewTitle"));
	private final JLabel jLabelReviewDone = new JLabel(ResourceBundle.getBundle(etik).getString("BaloratuGUI.ReviewDone"));
	private final JRadioButton jButton1Star = new JRadioButton("★☆☆☆☆");
	private final JRadioButton jButton2Stars = new JRadioButton("★★☆☆☆");
	private final JRadioButton jButton3Stars = new JRadioButton("★★★☆☆");
	private final JRadioButton jButton4Stars = new JRadioButton("★★★★☆");
	private final JRadioButton jButton5Stars = new JRadioButton("★★★★★");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JLabel jLabelStarError = new JLabel(ResourceBundle.getBundle(etik).getString("BaloratuGUI.StarError"));


	public BaloratuGUI(Traveler t, Erreserba e) {

		jLabelReviewDone.setVisible(false);

		this.traveler=t;
		this.erreserba=e;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));

		jButtonSend.setBounds(new Rectangle(329, 263, 130, 30));

	
		this.getContentPane().add(jButtonBack, null);
		this.getContentPane().add(jButtonSend, null);

		
		
		
		BLFacade facade = DriverGUI.getBusinessLogic();
		jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		jLabelTitle.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelTitle.setBounds(0, 10, 588, 25);
		getContentPane().add(jLabelTitle);
		
		jLabelDescription.setHorizontalAlignment(SwingConstants.LEFT);
		jLabelDescription.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelDescription.setBounds(57, 148, 154, 25);
		getContentPane().add(jLabelDescription);
		
		jLabelReviewTitle.setHorizontalAlignment(SwingConstants.LEFT);
		jLabelReviewTitle.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelReviewTitle.setBounds(57, 83, 286, 25);
		getContentPane().add(jLabelReviewTitle);
		
		JTextPane jTextPaneTitle = new JTextPane();
		jTextPaneTitle.setBounds(58, 118, 485, 20);
		getContentPane().add(jTextPaneTitle);
		String moneyBir = String.format("%.2f", traveler.getMoney());
		
		
		jButtonBack.setBounds(new Rectangle(133, 263, 130, 30));
		jLabelReviewDone.setForeground(new Color(0, 255, 0));
		jLabelReviewDone.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		jLabelReviewDone.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelReviewDone.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelReviewDone.setBounds(153, 298, 286, 25);
		
		getContentPane().add(jLabelReviewDone);
		
		JTextPane jTextPaneDescription = new JTextPane();
		jTextPaneDescription.setBounds(58, 179, 485, 74);
		getContentPane().add(jTextPaneDescription);
		buttonGroup.add(jButton1Star);
		jButton1Star.setBounds(60, 56, 98, 21);
		
		getContentPane().add(jButton1Star);
		buttonGroup.add(jButton2Stars);
		jButton2Stars.setBounds(160, 56, 98, 21);
		
		getContentPane().add(jButton2Stars);
		buttonGroup.add(jButton3Stars);
		jButton3Stars.setBounds(260, 56, 98, 21);
		
		getContentPane().add(jButton3Stars);
		buttonGroup.add(jButton4Stars);
		jButton4Stars.setBounds(360, 56, 99, 21);
		
		getContentPane().add(jButton4Stars);
		buttonGroup.add(jButton5Stars);
		jButton5Stars.setBounds(460, 56, 98, 21);
		
		getContentPane().add(jButton5Stars);
		jLabelStarError.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		jLabelStarError.setForeground(new Color(255, 0, 0));
		jLabelStarError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelStarError.setBounds(153, 298, 286, 25);
		
		getContentPane().add(jLabelStarError);
		
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				BaloratuErreklamatuGUI tgui = new BaloratuErreklamatuGUI(traveler);
				tgui.setVisible(true);
			}
		});
		
		jLabelStarError.setVisible(false);
		jButtonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean error = false;
				if (!jButton1Star.isSelected() && !jButton2Stars.isSelected() && !jButton3Stars.isSelected() && !jButton4Stars.isSelected()
						&& !jButton5Stars.isSelected()) {
					error = true;
					jLabelStarError.setVisible(true);
				}
				if(!error) {
					BLFacade facade = LoginGUI.getBusinessLogic();
					Balorazioa bal = new Balorazioa(traveler.getEmail(), 0, jTextPaneTitle.getText(), jTextPaneDescription.getText());
					bal.setErreserba(erreserba);
					if(jButton1Star.isSelected())
						bal.setBalorazioa(1);
					else if(jButton2Stars.isSelected())
						bal.setBalorazioa(2);
					else if(jButton3Stars.isSelected())
						bal.setBalorazioa(3);
					else if(jButton4Stars.isSelected())
						bal.setBalorazioa(4);
					else
						bal.setBalorazioa(5);
					jLabelReviewDone.setVisible(true);
					facade.createReview(bal, erreserba.getRide().getRideNumber());
				}
					
			}
		});
	}	 
}
