package swingPopUps;

import java.awt.BasicStroke;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Backend.Exercise;
import Backend.LiftRepository;
import Backend.Plan;
import Backend.Workout;
import swing.WorkoutPage;

/**
 * 
 * @author Manuel
 *
 * In dieser Klasse können Übungen hinzugefügt/bearbeitet/gelöscht werden.
 *
 */

public class EditExercise {
	LiftRepository liftRep = LiftRepository.read();
	WorkoutPage wp = new WorkoutPage();
	ErrorMessage error = new ErrorMessage();
	Exercise exerciseValueCombo;
	Exercise exerciseValueList;
	Exercise defaultExerciseValue = new Exercise();
	
	public JFrame addExercisePopUp(Plan plan, Workout workout, DefaultListModel<Exercise> defExerList, JList <Exercise> exerList, JPanel chartPanel) {
		//set JFrame
		JFrame frame = new JFrame();
		
		//Window
		frame.setTitle("Edit Exercise");
		frame.setSize(500, 650);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		
		//Layout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		JPanel inputTextPanel = new JPanel(new GridBagLayout());
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		//JList
		JList <Exercise> exerciseList = new JList<>();
		DefaultListModel<Exercise> defExerciseList = new DefaultListModel<>();
		exerciseList.setModel(liftRep.exerciseListGUI(plan, workout, defExerciseList));
		exerciseList.setFixedCellWidth(400);
		exerciseList.setFixedCellHeight(30);
		JScrollPane jcp = new JScrollPane(exerciseList);
		exerciseList.getSelectionModel().addListSelectionListener(e -> {
			Exercise exerciseListValue = exerciseList.getSelectedValue();
			exerciseValueList = exerciseListValue;
		});
		
		
		//Combobox
		liftRep.fillAllExercises(plan, workout);
		JComboBox<Exercise> cmbExercise = new JComboBox<Exercise>();
		liftRep.exerciseComboBoxGui(plan, workout, cmbExercise);
    	exerciseValueCombo = (Exercise) cmbExercise.getSelectedItem();
		cmbExercise.setPreferredSize(new Dimension(250, 30));
		cmbExercise.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	exerciseValueCombo = (Exercise) cmbExercise.getSelectedItem();
		    }
		});	
		
		
		//set current Strengthlevel
		JLabel set = new JLabel("Aim Sets");
		JLabel reps = new JLabel("Aim Reps");
		JLabel kg = new JLabel("Current Weight");
		JTextArea setText = new JTextArea(1,3);
		JTextArea repsText = new JTextArea(1,3);
		JTextArea kgText = new JTextArea(1,3);
		
		gbc.insets = new Insets(0,0,3,5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		inputTextPanel.add(set, gbc);
		
		gbc.insets = new Insets(0,5,3,0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		inputTextPanel.add(setText, gbc);
		
		gbc.insets = new Insets(3,0,3,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		inputTextPanel.add(reps, gbc);
		
		gbc.insets = new Insets(3,5,3,0);
		gbc.gridx = 1;
		gbc.gridy = 1;
		inputTextPanel.add(repsText, gbc);
		
		gbc.insets = new Insets(3,0,0,5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		inputTextPanel.add(kg, gbc);
		
		gbc.insets = new Insets(3,5,0,0);
		gbc.gridx = 1;
		gbc.gridy = 2;
		inputTextPanel.add(kgText, gbc);
		
		
		//Buttons
		JButton add = new JButton("ADD");
		add.setPreferredSize(new Dimension(100, 50));
		add.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				boolean check = true;
				for(int i = 0; i < exerciseList.getModel().getSize(); i++) {
					if(exerciseList.getModel().getElementAt(i).getName().equals(exerciseValueCombo.getName())) {
						error.errorText("Error You Already Added This Exercise!");
						check = false;
					}
				}	
				
				if(check == true || exerciseList.getModel().getSize() < 1) {
					try {
						liftRep.safeAddAimValues(plan, workout, exerciseValueCombo, Integer.parseInt(setText.getText()), Integer.parseInt(repsText.getText()), Double.parseDouble(kgText.getText()));
					
						exerciseList.setModel(liftRep.exerciseListGUI(plan, workout, defExerciseList));
						jcp.repaint();
							
						exerList.setModel(liftRep.exerciseListGUI(plan, workout, defExerList));
					
						liftRep.safeSetFirstProgressionValue(plan, workout, exerciseValueCombo);
						liftRep.setFirstProgressionValueOneRM(exerciseValueCombo, Integer.parseInt(repsText.getText()), Double.parseDouble(kgText.getText()));
					} catch (NumberFormatException ex) {
						error.errorText("Error Please Insert a Valid Format!");
					}
						
					setText.setText("");
					repsText.setText("");
					kgText.setText("");
				}
			}
		});
		
		JButton edit = new JButton("EDIT");
		edit.setPreferredSize(new Dimension(100, 50));
		edit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(exerciseValueList == null) {
					error.errorText("ERROR Please Select a Value!");
				}else {
					try {
						liftRep.safeEditExercise(plan, workout, exerciseValueList, Integer.parseInt(setText.getText()), Integer.parseInt(repsText.getText()), Double.parseDouble(kgText.getText()));
						jcp.repaint();
						exerList.setModel(liftRep.exerciseListGUI(plan, workout, defExerList));
						
						liftRep.safeSetFirstProgressionValue(plan, workout, exerciseValueList);
						liftRep.setFirstProgressionValueOneRM(exerciseValueList, Integer.parseInt(repsText.getText()), Double.parseDouble(kgText.getText()));
						
						chartPanel.removeAll();
						chartPanel.add(wp.progressionChartPanel(plan, workout, exerciseValueCombo));
						chartPanel.revalidate();
					} catch (NumberFormatException ex) {
						error.errorText("Error Please Insert a Valid Format!");
					}
					setText.setText("");
					repsText.setText("");
					kgText.setText("");
				}
				
			}
		});
		
		JButton delete = new JButton("DELETE");
		delete.setPreferredSize(new Dimension(100, 50));
		delete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(exerList.getSelectedValue() != null) {
					for(int i = 0; i < exerList.getModel().getSize(); i++) {
						if(exerList.getSelectedValue().getName().equals(exerciseValueList.getName())) {
							chartPanel.removeAll();
							chartPanel.add(wp.progressionChartPanel(plan, workout, defaultExerciseValue));
							chartPanel.revalidate();
						}
					}
				}
				
				liftRep.safeDeleteExercise(plan, workout, exerciseValueList);
				exerciseList.setModel(liftRep.exerciseListGUI(plan, workout, defExerList));
				jcp.repaint();
				exerList.setModel(liftRep.exerciseListGUI(plan, workout, defExerList));
				
				if(exerList.getModel().getSize() < 1) {
					chartPanel.removeAll();
					chartPanel.add(wp.progressionChartPanel(plan, workout, defaultExerciseValue));
					chartPanel.revalidate();
				}
			}
		});

		gbc.insets = new Insets(0,0,0,10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		buttonPanel.add(add, gbc);
		
		gbc.insets = new Insets(0,10,0,10);
		gbc.gridx = 1;
		gbc.gridy = 0;
		buttonPanel.add(edit, gbc);
		
		gbc.insets = new Insets(0,10,0,0);
		gbc.gridx = 2;
		gbc.gridy = 0;
		buttonPanel.add(delete, gbc);
		
		
		//add to main
		gbc.insets = new Insets(0,0,10,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(jcp, gbc);
		
		gbc.insets = new Insets(10,0,10,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(cmbExercise, gbc);
		
		gbc.insets = new Insets(5,0,10,0);
		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(inputTextPanel, gbc);
		
		gbc.insets = new Insets(10,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 3; 
		mainPanel.add(buttonPanel, gbc);
		
		
		//Design
		Color darkBlue = new Color(18, 25, 33);
		Color yellow = new Color(250, 213, 125);
		Font text = new Font("ARIAL", Font.PLAIN, 20);
		
		mainPanel.setBackground(darkBlue);
		buttonPanel.setBackground(darkBlue);
		inputTextPanel.setBackground(darkBlue);
		
		cmbExercise.setBorder(BorderFactory.createLineBorder(darkBlue));
		cmbExercise.setBackground(Color.WHITE);
		cmbExercise.setFont(text);
		
		jcp.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		jcp.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		jcp.setFont(text);
		jcp.setBackground(darkBlue);
		jcp.setForeground(Color.WHITE);
		
		exerciseList.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		exerciseList.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		exerciseList.setFont(text);
		exerciseList.setBackground(darkBlue);
		exerciseList.setForeground(Color.WHITE);
		
		reps.setForeground(Color.WHITE);
		reps.setFont(text);
		
		kg.setForeground(Color.WHITE);
		kg.setFont(text);
		
		set.setForeground(Color.WHITE);
		set.setFont(text);
		
		repsText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		repsText.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		repsText.setFont(text);
		repsText.setBackground(darkBlue);
		repsText.setForeground(Color.WHITE);
		
		kgText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		kgText.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		kgText.setFont(text);
		kgText.setBackground(darkBlue);
		kgText.setForeground(Color.WHITE);
		
		setText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		setText.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		setText.setFont(text);
		setText.setBackground(darkBlue);
		setText.setForeground(Color.WHITE);
		
		add.setFont(text);
		add.setBackground(yellow);
		add.setForeground(darkBlue);
		add.setBorder(null);
		
		edit.setFont(text);
		edit.setBackground(yellow);
		edit.setForeground(darkBlue);
		edit.setBorder(null);
		
		delete.setFont(text);
		delete.setBackground(yellow);
		delete.setForeground(darkBlue);
		delete.setBorder(null);
		
		
		//frame
		frame.add(mainPanel);
		
		frame.setVisible(true);
		
		return frame;
	}
	
	
}
