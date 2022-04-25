package swingPopUps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Backend.LiftRepository;
import Backend.Measurements;
import Backend.Profile;
import swing.ProfilePage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * 
 * @author Manuel
 *
 * Diese Klasse zeigt die Optionen, welche man im Profil, für die Körpermaße wählen kann an.
 *
 */

public class AddMeasurementChart {
	LiftRepository liftRep = LiftRepository.read();
	ProfilePage profilePage = new ProfilePage();
	Measurements measurementsValue;
	
	public JFrame addMeasurementChartPopUp(Profile profile, JPanel propgressionPanel, JPanel chartPanel) {
		//set JFrame
		JFrame frame = new JFrame();
		
		//Window
		frame.setTitle("Add Measurement");
		frame.setSize(400, 200);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		
		//Layout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		//JCombobox
		JComboBox<Measurements> cmbMeasurements = new JComboBox<Measurements>();
		liftRep.measurementsComboBoxGui(cmbMeasurements);
    	measurementsValue = (Measurements) cmbMeasurements.getSelectedItem();
		cmbMeasurements.setPreferredSize(new Dimension(250, 30));
		cmbMeasurements.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	measurementsValue = (Measurements) cmbMeasurements.getSelectedItem();
		    }
		});		
		
		
		//Add Button
		JButton edit = new JButton("Edit");
		edit.setPreferredSize(new Dimension(100, 50));
		edit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				propgressionPanel.removeAll();
				chartPanel.removeAll();
				liftRep.editProfile(profile, null, measurementsValue);
				chartPanel.add(profilePage.progressionPanel(profile, null, measurementsValue));
				propgressionPanel.add(chartPanel);
				propgressionPanel.revalidate();
				chartPanel.revalidate();
			}
		});
		
		
		//Add to MainPanel
		gbc.insets = new Insets(0,0,10,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(cmbMeasurements, gbc);
		
		gbc.insets = new Insets(10,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(edit, gbc);
		
		
		//Design
		Color darkBlue = new Color(18, 25, 33);
		Color yellow = new Color(250, 213, 125);
		Font text = new Font("ARIAL", Font.PLAIN, 20);
		
		mainPanel.setBackground(darkBlue);
		
		cmbMeasurements.setBorder(BorderFactory.createLineBorder(darkBlue));
		cmbMeasurements.setBackground(Color.WHITE);
		cmbMeasurements.setFont(text);
		
		edit.setFont(text);
		edit.setBackground(yellow);
		edit.setForeground(darkBlue);
		edit.setBorder(null);
		
		
		//frame
		frame.add(mainPanel);
		
		frame.setVisible(true);
		
		return frame;
	}
}
