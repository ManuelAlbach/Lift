package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import Backend.LiftRepository;
import Backend.Measurements;
import swingPopUps.ErrorMessage;

/**
 * 
 * @author Manuel
 *
 * Diese Klasse ist für die GUI der Körpermaße zuständig, hier kann man die verschiedenen Werte eintragen, 
 * welche im Backend verarbeitet werden
 *
 */

public class MeasurementsPage {
	LiftRepository liftRep = LiftRepository.read();
	ErrorMessage error = new ErrorMessage();
	Measurements measurementValue = new Measurements();

	public JPanel measurementsSite() {	
		//Layout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		JPanel partsPanel = new JPanel(new GridBagLayout());
		
		JPanel progressionPanel = new JPanel(new GridBagLayout());
		JPanel cmPanel = new JPanel(new GridBagLayout());
		JPanel kgPanel = new JPanel(new GridBagLayout());
		JPanel bfpPanel = new JPanel(new GridBagLayout());
		
		JPanel chartPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		//List
		JList <Measurements> bodyParts = new JList <Measurements>();
		DefaultListModel<Measurements> bodyPartsList = new DefaultListModel <Measurements>();
		bodyParts.setModel(liftRep.measurementsListGUI(bodyPartsList));
		bodyParts.setFixedCellWidth(700);
		bodyParts.setFixedCellHeight(80);
		JScrollPane jcp = new JScrollPane(bodyParts);
		bodyParts.getSelectionModel().addListSelectionListener(e -> {
			Measurements measurementListValue = bodyParts.getSelectedValue();
			measurementValue = measurementListValue;
			if(measurementValue != null) {
				chartPanel.removeAll();
				chartPanel.add(progressionChartPanel(measurementValue));
				chartPanel.revalidate();
			}
			
			if(measurementValue.getName().equals("Weight")) {
				kgPanel.setVisible(true);
				bfpPanel.setVisible(false);
				cmPanel.setVisible(false);
			} else if(measurementValue.getName().equals("Body Fat")) {
				bfpPanel.setVisible(true);
				cmPanel.setVisible(false);
				kgPanel.setVisible(false);
			} else {
				cmPanel.setVisible(true);
				kgPanel.setVisible(false);
				bfpPanel.setVisible(false);
			}
		});
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		partsPanel.add(jcp, gbc);
		
		
		//progression chart
		chartPanel.add(progressionChartPanel(measurementValue));
		
		
		//Add Progression Data
		JLabel cm = new JLabel("Cm: ");
		JLabel kg = new JLabel("KG: ");
		JLabel bfp = new JLabel("BFP: ");
		
		JTextArea cmText = new JTextArea();
		cmText.setPreferredSize(new Dimension(55, 30));
		JTextArea kgText = new JTextArea();
		kgText.setPreferredSize(new Dimension(55, 30));
		JTextArea bfpText = new JTextArea();
		bfpText.setPreferredSize(new Dimension(55, 30));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		cmPanel.add(cm, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		cmPanel.add(cmText, gbc);
		cmPanel.setVisible(false);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		kgPanel.add(kg, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		kgPanel.add(kgText, gbc);
		kgPanel.setVisible(false);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		bfpPanel.add(bfp, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		bfpPanel.add(bfpText, gbc);
		bfpPanel.setVisible(false);
		
		
		//Add Button
		JButton add = new JButton("ADD");
		add.setPreferredSize(new Dimension(100, 50));
		add.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(cmText.getText().isEmpty() && kgText.getText().isEmpty() && bfpText.getText().isEmpty()) {
					error.errorText("ERROR Please Insert a Valid Number!");
				} else {
					try {
						double progressionValue = checkProgressionValues(cmText.getText(), kgText.getText(), bfpText.getText());
						liftRep.safeAddProgressionData(measurementValue, progressionValue);
				
						chartPanel.removeAll();
						chartPanel.add(progressionChartPanel(measurementValue));
						chartPanel.revalidate();
					} catch (NumberFormatException ex) {
						error.errorText("ERROR Please Insert a Valid Format!");
					}
				}
				cmText.setText("");
				kgText.setText("");
				bfpText.setText("");
				
			}
		});
		
		
		//add to Progressionpanel
		gbc.insets = new Insets(0,0,20,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		progressionPanel.add(chartPanel, gbc);
		
		gbc.insets = new Insets(20,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		progressionPanel.add(cmPanel, gbc);	
		
		gbc.insets = new Insets(20,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		progressionPanel.add(kgPanel, gbc);	
		
		gbc.insets = new Insets(20,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		progressionPanel.add(bfpPanel, gbc);	
			
		gbc.gridx = 0;
		gbc.gridy = 2;
		progressionPanel.add(add, gbc);
		
		
		//add to MainPanel
		gbc.insets = new Insets(0,0,0,25);
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(partsPanel, gbc);
		
		gbc.insets = new Insets(0,25,0,0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		mainPanel.add(progressionPanel, gbc);
		
		
		//Design
		Color darkBlue = new Color(18, 25, 33);
		Color yellow = new Color(250, 213, 125);
		Font text = new Font("ARIAL", Font.PLAIN, 24);
		
		mainPanel.setBackground(Color.WHITE);
		partsPanel.setBackground(Color.WHITE);
		progressionPanel.setBackground(Color.WHITE);
		cmPanel.setBackground(Color.WHITE);
		kgPanel.setBackground(Color.WHITE);
		bfpPanel.setBackground(Color.WHITE);
		
		bodyParts.setFont(text);
		bodyParts.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		cm.setFont(text);
		kg.setFont(text);
		bfp.setFont(text);
		
		cmText.setFont(text);
		cmText.setBorder(BorderFactory.createLineBorder(darkBlue));
		
		kgText.setFont(text);
		kgText.setBorder(BorderFactory.createLineBorder(darkBlue));
		
		bfpText.setFont(text);
		bfpText.setBorder(BorderFactory.createLineBorder(darkBlue));
		
		add.setFont(text);
		add.setBackground(yellow);
		add.setForeground(darkBlue);
		add.setBorder(null);
		
		return mainPanel;
	}
	
    private JPanel progressionChartPanel(Measurements measurements) {
        String chartTitle = measurements.getName();
        String categoryAxisLabel = "Date";
        String valueAxisLabel = liftRep.getRightValueAxisLabel(measurements);
        
     
        CategoryDataset dataset = liftRep.createDatasetMeasurements(measurements);
     
        JFreeChart chart = ChartFactory.createLineChart(chartTitle,
                categoryAxisLabel, valueAxisLabel, dataset);
    	
        
        ChartPanel chartPanel = new ChartPanel(chart);

        
        return chartPanel;
    }
	
	private double checkProgressionValues(String cmText, String kgText, String bfpText) {
		if(!cmText.isEmpty()) {
			double cm = Double.parseDouble(cmText);
			return cm;
		} else if (!kgText.isEmpty()) {
			double kg = Double.parseDouble(kgText);
			return kg;
		} else if(!bfpText.isEmpty()) {
			double kfa = Double.parseDouble(bfpText);
			return kfa;
		}
		return 0;
	}
}
