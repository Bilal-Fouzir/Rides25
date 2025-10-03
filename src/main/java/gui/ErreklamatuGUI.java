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

public class ErreklamatuGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private static String etik = "Etiquetas";
	private Traveler traveler;
	private Erreserba erreserba;
	private JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle(etik).getString("ErreklamatuGUI.Title"));
	private JButton jButtonSend = new JButton(ResourceBundle.getBundle(etik).getString("BaloratuGUI.Send"));
	private JButton jButtonBack = new JButton(ResourceBundle.getBundle(etik).getString("Back"));
    private JLabel jLabelDescription = new JLabel(ResourceBundle.getBundle(etik).getString("BaloratuGUI.Description"));
	private JLabel jLabelReviewTitle = new JLabel(ResourceBundle.getBundle(etik).getString("BaloratuGUI.ReviewTitle"));
	private final JLabel jLabelReviewDone = new JLabel(ResourceBundle.getBundle(etik).getString("BaloratuGUI.ReviewDone"));
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JLabel jLabelDescriptionError = new JLabel(ResourceBundle.getBundle(etik).getString("ErreklamatuGUI.DescriptionError"));
	private JLabel jLabelTitleError = new JLabel(ResourceBundle.getBundle(etik).getString("ErreklamatuGUI.TitleError"));
	private static String let = "Tahoma";

	public ErreklamatuGUI(Traveler t, Erreserba er) {

		jLabelReviewDone.setVisible(false);
		jLabelDescriptionError.setVisible(false);
		jLabelTitleError.setVisible(false);

		this.traveler=t;
		this.erreserba = er;
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
		jLabelDescription.setBounds(57, 118, 154, 25);
		getContentPane().add(jLabelDescription);
		
		jLabelReviewTitle.setHorizontalAlignment(SwingConstants.LEFT);
		jLabelReviewTitle.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelReviewTitle.setBounds(57, 45, 286, 25);
		getContentPane().add(jLabelReviewTitle);
		
		JTextPane jTextPaneTitle = new JTextPane();
		jTextPaneTitle.setBounds(57, 80, 485, 20);
		getContentPane().add(jTextPaneTitle);
		String moneyBir = String.format("%.2f", traveler.getMoney());
		
		
		jButtonBack.setBounds(new Rectangle(133, 263, 130, 30));
		jLabelReviewDone.setForeground(new Color(0, 255, 0));
		jLabelReviewDone.setFont(new Font(let, Font.BOLD | Font.ITALIC, 11));
		jLabelReviewDone.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelReviewDone.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelReviewDone.setBounds(153, 298, 286, 25);
		
		getContentPane().add(jLabelReviewDone);
		
		JTextPane jTextPaneDescription = new JTextPane();
		jTextPaneDescription.setBounds(57, 153, 485, 100);
		getContentPane().add(jTextPaneDescription);
		jLabelDescriptionError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 11));
		jLabelDescriptionError.setForeground(new Color(255, 0, 0));
		jLabelDescriptionError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDescriptionError.setBounds(153, 298, 286, 25);
		
		getContentPane().add(jLabelDescriptionError);
		
		jLabelTitleError.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitleError.setFont(new Font(let, Font.BOLD | Font.ITALIC, 11));
		jLabelTitleError.setForeground(new Color(255, 0, 0));
		jLabelTitleError.setBounds(153, 298, 286, 25);
		getContentPane().add(jLabelTitleError);
		
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				BaloratuErreklamatuGUI tgui = new BaloratuErreklamatuGUI(traveler);
				tgui.setVisible(true);
			}
		});
		
		jLabelDescriptionError.setVisible(false);
		jButtonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelDescriptionError.setVisible(false);
				jLabelTitleError.setVisible(false);
				jLabelReviewDone.setVisible(false);
				boolean error = false;
				if (jTextPaneTitle.getText().equals("")) {
					error = true;
					jLabelTitleError.setVisible(true);
				}
				else if (jTextPaneDescription.getText().equals("")) {
					error = true;
					jLabelDescriptionError.setVisible(true);
				}
				if(!error) {
					BLFacade facade = LoginGUI.getBusinessLogic();
					Erreklamazioa err = new Erreklamazioa(traveler.getEmail(), jTextPaneTitle.getText(), jTextPaneDescription.getText(), er);
					jLabelReviewDone.setVisible(true);
					facade.createErreklamazioa(err, erreserba.getRide().getRideNumber());
				}
					
			}
		});
	}	 
}
