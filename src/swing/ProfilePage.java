package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import Backend.LiftRepository;
import Backend.Measurements;
import Backend.OneRMExercise;
import Backend.Profile;
import swingPopUps.AddExerciseChart;
import swingPopUps.AddMeasurementChart;

/**
 * 
 * @author Manuel
 *
 * Die Klasse ist für das Profil zuständig und zeigt dem nutzer mehrere Charts an, in denen er seine bevorzugten Werte tracken kann
 *
 */

public class ProfilePage {
	LiftRepository liftRep = LiftRepository.read();
	OneRMExercise exerciseValue = new OneRMExercise();
	Measurements measurementValue = new Measurements();
	
	public JPanel profilePage() {
		//Layout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		JPanel chartGrid = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		//Add to chartGrid
		gbc.insets = new Insets(0,0,10,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		chartGrid.add(progressionPanel(liftRep.getProfileArray().get(0), liftRep.getProfileArray().get(0).getExercise(), liftRep.getProfileArray().get(0).getMeasurement()), gbc);
		
		gbc.insets = new Insets(0,0,10,0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		chartGrid.add(progressionPanel(liftRep.getProfileArray().get(1), liftRep.getProfileArray().get(1).getExercise(), liftRep.getProfileArray().get(1).getMeasurement()), gbc);
		
		gbc.insets = new Insets(0,0,10,0);
		gbc.gridx = 2;
		gbc.gridy = 0;
		chartGrid.add(progressionPanel(liftRep.getProfileArray().get(2), liftRep.getProfileArray().get(2).getExercise(), liftRep.getProfileArray().get(2).getMeasurement()), gbc);
		
		gbc.insets = new Insets(10,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		chartGrid.add(progressionPanel(liftRep.getProfileArray().get(3), liftRep.getProfileArray().get(3).getExercise(), liftRep.getProfileArray().get(3).getMeasurement()), gbc);
		
		gbc.insets = new Insets(10,0,0,0);
		gbc.gridx = 1;
		gbc.gridy = 1;
		chartGrid.add(progressionPanel(liftRep.getProfileArray().get(4), liftRep.getProfileArray().get(4).getExercise(), liftRep.getProfileArray().get(4).getMeasurement()), gbc);
		
		gbc.insets = new Insets(10,0,0,0);
		gbc.gridx = 2;
		gbc.gridy = 1;
		chartGrid.add(progressionPanel(liftRep.getProfileArray().get(5), liftRep.getProfileArray().get(5).getExercise(), liftRep.getProfileArray().get(5).getMeasurement()), gbc);

		
		//Add to MainPanel
		gbc.insets = new Insets(0,0,10,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(chartGrid, gbc);
		
		
		//Design
		mainPanel.setBackground(Color.WHITE);
		chartGrid.setBackground(Color.WHITE);
		
		
		return mainPanel;
	}
	
	 public JPanel progressionChart(OneRMExercise exercise, Measurements measurements) {
		 	String chartTitle = "";
	        String categoryAxisLabel = "Date";
		 	String valueAxisLabel = "";
		 	
        	CategoryDataset dataset = null;
		 	
	        if(exercise == null && measurements != null) {
		        chartTitle = measurements.getName();
		        valueAxisLabel = liftRep.getRightValueAxisLabel(measurements);
		        
		        dataset = liftRep.createDatasetMeasurements(measurements);
		        
	        } else if (exercise != null && measurements == null){
		        chartTitle = exercise.getName() + " (1RM)";
		        valueAxisLabel = "KG";
		        
		        dataset = liftRep.createDatasetOneRMExercise(exercise);
	        }
	     
	        JFreeChart chart = ChartFactory.createLineChart(chartTitle,
	                categoryAxisLabel, valueAxisLabel, dataset);
	       
	        ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new Dimension(480, 300));
	        
			return chartPanel;
	 } 
	 
	 public JPanel progressionPanel(Profile profile, OneRMExercise exercise, Measurements measurements) {
		 JPanel progressionPanel = new JPanel(new GridBagLayout());
		 
		 JPanel buttonPanel = new JPanel(new GridBagLayout());
		 
		 JPanel chartPanel = new JPanel(new GridBagLayout());
		 
		 GridBagConstraints gbc = new GridBagConstraints();
		 
		 chartPanel.add(progressionChart(exercise, measurements));
		 
		 
		//icons
		//Refresh
		ImageIcon iRefresh = new ImageIcon(getClass().getResource("../Images/Refresh.png"));
		Image refreshImage = iRefresh.getImage();
		Image scaleRefresh = refreshImage.getScaledInstance(45, 40,  java.awt.Image.SCALE_SMOOTH);
		iRefresh = new ImageIcon(scaleRefresh);
		 
		 
		 //Buttons
		 JButton exerciseButton = new JButton("EXERCISE");
		 exerciseButton.setPreferredSize(new Dimension(120, 40));
		 exerciseButton.addActionListener(new ActionListener() {
				
			 public void actionPerformed(ActionEvent e) {
				 AddExerciseChart exerciseChartValues = new AddExerciseChart();
				 exerciseChartValues.addExerciseChartPopUp(profile, progressionPanel, chartPanel);
			 }
		 });
			
		 JButton bodyMeasurements = new JButton("BODY MEASUREMENTS");
		 bodyMeasurements.setPreferredSize(new Dimension(190, 40));
		 bodyMeasurements.addActionListener(new ActionListener() {
			
			 public void actionPerformed(ActionEvent e) {
				 AddMeasurementChart measurementChartValues = new AddMeasurementChart();
				 measurementChartValues.addMeasurementChartPopUp(profile, progressionPanel, chartPanel);
			 }
		 });
		
		JLabel refresh = new JLabel(iRefresh);
		refresh.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				chartPanel.removeAll();
				chartPanel.add(progressionChart(exercise, measurements));
				chartPanel.revalidate();
		    }
		});
			
		 gbc.insets = new Insets(0,0,0,10);
		 gbc.gridx = 0;
		 gbc.gridy = 0;
		 buttonPanel.add(exerciseButton, gbc);
		
		 gbc.insets = new Insets(0,10,0,10);
		 gbc.gridx = 1;
		 gbc.gridy = 0;
		 buttonPanel.add(bodyMeasurements, gbc);
		 
		 gbc.insets = new Insets(0,0,0,0);
		 gbc.gridx = 2;
		 gbc.gridy = 0;
		 buttonPanel.add(refresh, gbc);
		
		 
		 //Add to progressionPanel
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		progressionPanel.add(chartPanel, gbc);
		
		gbc.insets = new Insets(10,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		progressionPanel.add(buttonPanel, gbc);
		
		
		//Design
		Color darkBlue = new Color(18, 25, 33);
		Color yellow = new Color(250, 213, 125);
		Font text = new Font("ARIAL", Font.PLAIN, 16);
		
		progressionPanel.setBackground(Color.WHITE);
		buttonPanel.setBackground(Color.WHITE);
		chartPanel.setBackground(Color.WHITE);
		
		exerciseButton.setFont(text);
		exerciseButton.setBackground(yellow);
		exerciseButton.setForeground(darkBlue);
		exerciseButton.setBorder(null);
		
		bodyMeasurements.setFont(text);
		bodyMeasurements.setBackground(yellow);
		bodyMeasurements.setForeground(darkBlue);
		bodyMeasurements.setBorder(null);

		refresh.setBackground(Color.WHITE);
		refresh.setForeground(Color.WHITE);
		refresh.setBorder(null);	
		
		return progressionPanel;
	 }
}
