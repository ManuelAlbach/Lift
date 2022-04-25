package swingPopUps;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Manuel
 *
 * Von dieser Klasse aus wird eine Fehlermeldung geworfen, falls ungültige Aktionen vom User durchgeführt wurden
 *
 */

public class ErrorMessage {
	
	public JFrame errorText(String errorText) {
		JFrame frame = new JFrame();
		
		//Window
		frame.setTitle("ERROR");
		frame.setSize(500, 150);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		
		//Layout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		//Text
		JLabel errorMessage = new JLabel(errorText);
		
		
		//Add To MainPanel
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(errorMessage, gbc);
		
		
		//Design
		Color darkBlue = new Color(18, 25, 33);
		
		mainPanel.setBackground(Color.WHITE);
		
		errorMessage.setForeground(darkBlue);
		errorMessage.setFont(new Font("ARIAL", Font.PLAIN , 24));
		
		
		//Add to JFrame and set Visibility
		frame.add(mainPanel);
	
		frame.setVisible(true);
		
		return frame;
	}
}
