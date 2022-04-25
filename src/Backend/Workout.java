package Backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

import org.jfree.data.category.CategoryDataset;

/**
 * 
 * @author Manuel
 *
 * Die Klasse speichert die Übungen in Arrays ab und enthält ebenfalsl GUI funktionen für die Übungen
 *
 */

public class Workout implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7945013198442482647L;
	
	String id = UUID.randomUUID().toString();
	String name;
	ArrayList <Exercise> allExercises = new ArrayList<Exercise>();
	ArrayList <Exercise> exercisesWorkout = new ArrayList<Exercise>();

	
	//Getter & Setter
	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList <Exercise> getAllExercisesArray(){
		return allExercises;
	}
	
	public ArrayList <Exercise> getExercisesWorkoutArray(){
		return exercisesWorkout;
	}
	
	
	
	//Exercise functions
	public void addExercise(String name) {
		Exercise exercise = new Exercise();
		exercise.setName(name);
		allExercises.add(exercise);
	}
	
	public void fillAllExerciseArray() {
		if(allExercises.size() < 1) {
			addExercise("Benchpress");
			addExercise("Squat");
			addExercise("Deadlift");
			addExercise("Militarypress");
			addExercise("Bent-Over-Rows");
			addExercise("Lateral Raises");
			addExercise("Reverse-Flys");
			addExercise("Biceps-Curls");
			addExercise("Triceps-Pushdown");
		}
	}
	
	public void addAimValues(Exercise exercise, int sets, int reps, double weight) {
		exercise.setAimSetsNumber(sets);
		exercise.setAimRepsNumber(reps);
		exercise.setProgressionValue(weight);
		exercisesWorkout.add(exercise);
	}
	
	public void editExercise(Exercise exercise, int sets, int reps, double weight) {
		exercise.setAimSetsNumber(sets);
		exercise.setAimRepsNumber(reps);
		exercise.setProgressionValue(weight);
	}
	
	public void deleteExercise(Exercise exercise) {
		exercisesWorkout.remove(exercise);
	}
	
	
	
	//Set functions
	public void addSet(Exercise exercise, int reps, double weight) {
		for(Exercise e : exercisesWorkout) {
			if(e.getId().equals(exercise.getId())) {
				e.addSet(reps, weight);
			}
		}
	}
	
	public void editSet(Exercise exercise, Set set, int reps, double weight) {
		for(Exercise e : exercisesWorkout) {
			if(e.getId().equals(exercise.getId())) {
				e.editSet(set, reps, weight);
			}
		}
	}
	
	public void deleteSet(Exercise exercise, Set set) {
		for(Exercise e : exercisesWorkout) {
			if(e.getId().equals(exercise.getId())) {
				e.deleteSet(set);
			}
		}
	}
	
	public void clearSets(Exercise exercise) {
		for(Exercise e : exercisesWorkout) {
			if(e.getId().equals(exercise.getId())) {
				e.clearSets();
			}
		}
	}
	
	
	
	//Progression functions
	public void setFirstProgressionValue(Exercise exercise) {
		for(Exercise e : exercisesWorkout) {
			if(e.getId().equals(exercise.getId())) {
				e.setFirstProgressionValue();
			}
		}
	}
	
	public void calculateProgression(Exercise exercise) {
		for(Exercise e : exercisesWorkout) {
			if(e.getId().equals(exercise.getId())) {
				e.calculateProgression();
			}
		}
	}
	
	
	
	//Dataset functions
	public CategoryDataset createDataset(Exercise exercise) {
		for(Exercise e : exercisesWorkout) {
			if(e.getId().equals(exercise.getId())) {
				return e.createDataset();
			}
		}
		return null;
	}
	
	
	
	//GUI functions
	//exercise
	public DefaultListModel<Exercise> exerciseListGUI(DefaultListModel<Exercise> exerciseList) {
		exerciseList = new DefaultListModel<Exercise>();
		for(Exercise e : exercisesWorkout) {
			exerciseList.addElement(e);
		}
		return exerciseList;
	}
	
	public JComboBox<Exercise> exerciseComboBoxGui(JComboBox<Exercise> exerciseComboBox) {
		for(Exercise e : allExercises) {
			exerciseComboBox.addItem(e);
		}
		
		return exerciseComboBox;
	}
	
	//set
	public DefaultListModel<Set> newSetGUIList(Exercise exercise, DefaultListModel<Set> setList) {
		for(Exercise e : exercisesWorkout) {
			if(e.getId().equals(exercise.getId())){
				return e.newSetGUIList(setList);
			}
		}
		return setList;
	}
	
	
	
	//to String
	public String toString() {
		return name;
	}	
}
