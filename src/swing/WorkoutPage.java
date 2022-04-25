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
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import Backend.Exercise;
import Backend.LiftRepository;
import Backend.Plan;
import Backend.Workout;
import swingPopUps.AddSet;
import swingPopUps.EditExercise;
import swingPopUps.EditPlan;
import swingPopUps.EditWorkouts;
import swingPopUps.ErrorMessage;

/**
 * 
 * @author Manuel
 *
 * Die Klasse gibt dem user ein überblick über dei Workouts, hier hat man die Optionen diese zu verwalten, und die Progression zu tracken.
 *
 */

public class WorkoutPage {
	LiftRepository liftRep = LiftRepository.read();
	ErrorMessage error = new ErrorMessage();
	Plan planValue = new Plan();
	Workout workoutValue = new Workout();
	Exercise exerciseValue = new Exercise();
	
	public JPanel workoutSite() {
		//Layout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		JPanel workoutPanel = new JPanel(new GridBagLayout());
		JPanel workoutButtonPanel = new JPanel(new GridBagLayout());
		JPanel workoutDropdownPanel = new JPanel(new GridBagLayout());
		JPanel exerciseListPanel = new JPanel(new GridBagLayout());
		JPanel workoutListButton = new JPanel(new GridBagLayout());
		
		JPanel progressionPanel = new JPanel(new GridBagLayout());
		JPanel chartPanel = new JPanel(new GridBagLayout());
		JPanel progressionDataPanel = new JPanel(new GridBagLayout());
		JPanel progressionDataButtonPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		//List
		JList <Exercise> excersiceList = new JList<>();
		DefaultListModel<Exercise> defExcersiceList = new DefaultListModel<>();
		excersiceList.setModel(liftRep.exerciseListGUI(planValue, workoutValue, defExcersiceList));
		excersiceList.setFixedCellWidth(700);
		excersiceList.setFixedCellHeight(60);
		JScrollPane jcp = new JScrollPane(excersiceList);
		excersiceList.getSelectionModel().addListSelectionListener(e -> {
			Exercise exerciseListValue = excersiceList.getSelectedValue();
			exerciseValue = exerciseListValue;
			if(exerciseValue != null) {
				chartPanel.removeAll();
				chartPanel.add(progressionChartPanel(planValue, workoutValue, exerciseValue));
				chartPanel.revalidate();
			}
		});
		
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 1;
		exerciseListPanel.add(jcp, gbc);
		
		
		//Combobox		
		JComboBox <Plan> cmbWorkoutPlans = new JComboBox <Plan>();
		liftRep.plansComboBoxGui(cmbWorkoutPlans);
		planValue = (Plan) cmbWorkoutPlans.getSelectedItem();
		cmbWorkoutPlans.setPreferredSize(new Dimension(250, 30));
		
		JComboBox <Workout> cmbWorkouts = new JComboBox <Workout>();
		liftRep.workoutsComboBoxGui(planValue, cmbWorkouts);
		workoutValue = (Workout) cmbWorkouts.getSelectedItem();
		excersiceList.setModel(liftRep.exerciseListGUI(planValue, workoutValue, defExcersiceList));
		cmbWorkouts.setPreferredSize(new Dimension(250, 30));
		
		cmbWorkoutPlans.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	planValue = (Plan) cmbWorkoutPlans.getSelectedItem();
		    	if(planValue != null) {
		    		liftRep.workoutsComboBoxGui(planValue, cmbWorkouts);
		    	}
		    }
		});
		
		cmbWorkouts.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	workoutValue = (Workout) cmbWorkouts.getSelectedItem();
		    	if(workoutValue != null) {
		    		excersiceList.setModel(liftRep.exerciseListGUI(planValue, workoutValue, defExcersiceList));
		    	} else {
		    		defExcersiceList.clear();
		    		excersiceList.setModel(defExcersiceList);
		    	}
		    }
		});		
		
		
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(0,0,0,15);
		gbc.gridx = 0;
		gbc.gridy = 0;
		workoutDropdownPanel.add(cmbWorkoutPlans, gbc);
		
		gbc.insets = new Insets(0,15,0,0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		workoutDropdownPanel.add(cmbWorkouts, gbc);
		
		
		//ComboBox Buttons
		JButton editPlan = new JButton("EDIT PLAN");
		editPlan.setPreferredSize(new Dimension(250, 50));
		editPlan.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				EditPlan plan = new EditPlan();
				plan.editPlanPopUp(cmbWorkoutPlans, cmbWorkouts, excersiceList, defExcersiceList);
			}
		});
		
		JButton editWorkout = new JButton("EDIT WORKOUT");
		editWorkout.setPreferredSize(new Dimension(250, 50));
		editWorkout.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(planValue == null) {
					error.errorText("Error Please Select a Plan!");
				} else {
					EditWorkouts workout = new EditWorkouts();
					workout.editWorkoutsPopUp(planValue, cmbWorkouts, excersiceList, defExcersiceList);
				}
			}
		});
		
		gbc.insets = new Insets(0,0,0,15);
		gbc.gridx = 0;
		gbc.gridy = 0;
		workoutButtonPanel.add(editPlan, gbc);
		
		gbc.insets = new Insets(0,15,0,0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		workoutButtonPanel.add(editWorkout, gbc);
			
		
		//exerciseList Buttons
		JButton editExcercise = new JButton("EDIT EXERCISES");
		editExcercise.setPreferredSize(new Dimension(300, 50));
		editExcercise.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(workoutValue == null) {
					error.errorText("Error Please Select a Workout!");
				} else {
					EditExercise edit = new EditExercise();
					edit.addExercisePopUp(planValue, workoutValue, defExcersiceList, excersiceList, chartPanel);
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		workoutListButton.add(editExcercise, gbc);
		
		
		//add to WorkoutPanel
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(0,0,15,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		workoutPanel.add(workoutButtonPanel, gbc);
		
		gbc.insets = new Insets(15,0,15,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		workoutPanel.add(workoutDropdownPanel, gbc);
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(15,0,15,0);
		gbc.gridx = 0;
		gbc.gridy = 2;
		workoutPanel.add(exerciseListPanel, gbc);
		
		gbc.insets = new Insets(15,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 3;
		workoutPanel.add(workoutListButton, gbc);
		
		
		//Progression chart
		chartPanel.add(progressionChartPanel(planValue, workoutValue, exerciseValue));
		
		
		//Add Progression Data		
		JButton addSet = new JButton("ADD SET");
		addSet.setPreferredSize(new Dimension(200, 50));
		addSet.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				if(exerciseValue.getName() == null) {
					error.errorText("Error Please Select a Exercise!");
				}else {
					AddSet addSet = new AddSet();
					addSet.addSetPopUp(planValue, workoutValue, exerciseValue, chartPanel);
				}
			}
		});
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		progressionDataButtonPanel.add(addSet, gbc);
		
		
		//add to progressionPanel
		gbc.insets = new Insets(0,0,20,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		progressionPanel.add(chartPanel, gbc);
		
		gbc.insets = new Insets(20,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		progressionPanel.add(progressionDataPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		progressionPanel.add(progressionDataButtonPanel, gbc);
		
		
		//add to mainPanel
		gbc.insets = new Insets(0,0,0,25);
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(workoutPanel, gbc);
		
		gbc.insets = new Insets(0,25,0,0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		mainPanel.add(progressionPanel, gbc);
		
		
		//Design
		Color darkBlue = new Color(18, 25, 33);
		Color yellow = new Color(250, 213, 125);
		Font text = new Font("ARIAL", Font.PLAIN, 24);
		
		mainPanel.setBackground(Color.WHITE);
		workoutPanel.setBackground(Color.WHITE);
		workoutButtonPanel.setBackground(Color.WHITE);
		workoutDropdownPanel.setBackground(Color.WHITE);
		exerciseListPanel.setBackground(Color.WHITE);
		workoutListButton.setBackground(Color.WHITE);
		progressionPanel.setBackground(Color.WHITE);
		chartPanel.setBackground(Color.WHITE);
		progressionDataPanel.setBackground(Color.WHITE);
		progressionDataButtonPanel.setBackground(Color.WHITE);
		
		editPlan.setFont(text);
		editPlan.setBackground(yellow);
		editPlan.setForeground(darkBlue);
		editPlan.setBorder(null);
		
		editWorkout.setFont(text);
		editWorkout.setBackground(yellow);
		editWorkout.setForeground(darkBlue);
		editWorkout.setBorder(null);
		
		editExcercise.setFont(text);
		editExcercise.setBackground(yellow);
		editExcercise.setForeground(darkBlue);
		editExcercise.setBorder(null);
		
		editWorkout.setFont(text);
		editWorkout.setBackground(yellow);
		editWorkout.setForeground(darkBlue);
		editWorkout.setBorder(null);
		
		cmbWorkoutPlans.setBorder(BorderFactory.createLineBorder(darkBlue));
		cmbWorkoutPlans.setBackground(Color.WHITE);
		cmbWorkoutPlans.setFont(text);
		
		cmbWorkouts.setBorder(BorderFactory.createLineBorder(darkBlue));
		cmbWorkouts.setBackground(Color.WHITE);
		cmbWorkouts.setFont(text);
		
		excersiceList.setBorder(BorderFactory.createLineBorder(darkBlue));
		excersiceList.setFont(text);
		
		addSet.setFont(text);
		addSet.setBackground(yellow);
		addSet.setForeground(darkBlue);
		addSet.setBorder(null);

		
		return mainPanel;
	}	
	
	
    public JPanel progressionChartPanel(Plan plan, Workout workout, Exercise exercise) {
    	String chartTitle = null;
    	if(exercise.getName() != null) {
    		chartTitle = exercise.getName() + " (" + exercise.getAimSetsNumber() + "x" + exercise.getAimRepsNumber() + ")";
    	}
        String categoryAxisLabel = "Date";
        String valueAxisLabel = "KG";
        
     
        CategoryDataset dataset = liftRep.createDatasetExercise(plan, workout, exercise);
     
        JFreeChart chart = ChartFactory.createLineChart(chartTitle,
                categoryAxisLabel, valueAxisLabel, dataset);
    	
      
        ChartPanel chartPanel = new ChartPanel(chart);
        
        return chartPanel;
    }         
}