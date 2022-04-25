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

/**
 * 
 * @author Manuel
 *
 * In dieser Klasse können Workouts hinzugefügt/bearbeitet/gelöscht werden
 *
 */

public class EditWorkouts {
	LiftRepository liftRep = LiftRepository.read();
	ErrorMessage error = new ErrorMessage();
	Workout workoutValue;
	
	public JFrame editWorkoutsPopUp(Plan plan, JComboBox <Workout> Combobox, JList <Exercise> exerList, DefaultListModel<Exercise> defExcersiceList) {
		//set JFrame
		JFrame frame = new JFrame();
		
		
		//Window
		frame.setTitle("Edit Workouts");
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		
		//Layout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		//Plan name
		JLabel name = new JLabel("Name");
		
		JTextArea nameText = new JTextArea(1,20);
		
		
		//JList
		JList <Workout> workoutsList = new JList<>();
		DefaultListModel<Workout> defWorkoutList = new DefaultListModel<>();
		workoutsList.setModel(liftRep.workoutsListGUI(plan, defWorkoutList));
		workoutsList.setFixedCellWidth(400);
		workoutsList.setFixedCellHeight(30);
		JScrollPane jcp = new JScrollPane(workoutsList);
		workoutsList.getSelectionModel().addListSelectionListener(e -> {
			Workout workoutListValue = workoutsList.getSelectedValue();
			workoutValue = workoutListValue;
			if(workoutValue != null) {
				nameText.setText(workoutValue.getName());
			}
			else {
				nameText.setText("");
			}
		});

		
		//Buttons
		JButton add = new JButton("ADD");
		add.setPreferredSize(new Dimension(100, 50));
		add.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(nameText.getText().isEmpty()) {
					error.errorText("Error please Insert a Text!");
				}else {
					liftRep.safeAddWorkout(plan, nameText.getText());
					nameText.setText("");
				
					workoutsList.setModel(liftRep.workoutsListGUI(plan, defWorkoutList));
				
					jcp.repaint();
					liftRep.workoutsComboBoxGui(plan, Combobox);
				}
			}
		});
		
		JButton edit = new JButton("EDIT");
		edit.setPreferredSize(new Dimension(100, 50));
		edit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(nameText.getText().isEmpty()) {
					error.errorText("Error please Insert a Text!");
				}else {
					liftRep.safeEditWorkout(plan, workoutValue, nameText.getText());
					nameText.setText("");
					jcp.repaint();
					liftRep.workoutsComboBoxGui(plan, Combobox);
				}
			}
		});
		
		JButton delete = new JButton("DELETE");
		delete.setPreferredSize(new Dimension(100, 50));
		delete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				liftRep.safeDeleteWorkout(plan, workoutValue);
				workoutsList.setModel(liftRep.workoutsListGUI(plan, defWorkoutList));
				jcp.repaint();
				liftRep.workoutsComboBoxGui(plan, Combobox);
				if(plan.getWorkoutsList().isEmpty()) {
					defExcersiceList.clear();
					exerList.setModel(defExcersiceList);
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
		
		gbc.insets = new Insets(10,0,5,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(name, gbc);
		
		gbc.insets = new Insets(5,0,10,0);
		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(nameText, gbc);
		
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
		
		workoutsList.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		workoutsList.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		workoutsList.setFont(text);
		workoutsList.setBackground(darkBlue);
		workoutsList.setForeground(Color.WHITE);
		
		name.setForeground(Color.WHITE);
		name.setFont(new Font("ARIAL", Font.BOLD | Font.ITALIC, 28));
		
		nameText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		nameText.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		nameText.setFont(text);
		nameText.setBackground(darkBlue);
		nameText.setForeground(Color.WHITE);
		
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
