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
 * Diese Klasse ist für die Handhabung der Workouts zuständig und beinhaltet ebenfalls GUI functionen für
 * diesen Bereich
 *
 */

public class Plan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2301404506335767525L;
	
	String id = UUID.randomUUID().toString();
	String name;
	ArrayList <Workout> workouts = new ArrayList<>();

	
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
	
	public  ArrayList <Workout> getWorkoutsList() {
		return workouts;
	}
	
	
	
	//Workout functions
	public void addWorkout(String name) {
		Workout workout = new Workout();
		workout.setName(name);
		workouts.add(workout);
	}
	
	public void editWorkout(Workout workout, String name) {
		workout.setName(name);
	}
	
	public void deleteWorkout(Workout workout) {
		workouts.remove(workout);
	}
	
	
	
	//Exercise functions
	public void fillAllExerciseArray(Workout workout) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				w.fillAllExerciseArray();
			}
		}
	}
	
	public void addAimValues(Workout workout, Exercise exercise, int sets, int reps, double weight) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				w.addAimValues(exercise, sets, reps, weight);
			}
		}
	}
	
	public void editExercise(Workout workout, Exercise exercise, int sets, int reps, double weight) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				w.editExercise(exercise, sets, reps, weight);
			}
		}
	}
	
	public void deleteExercise(Workout workout, Exercise exercise) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				w.deleteExercise(exercise);
			}
		}
	}
	
	public ArrayList<Exercise> getExerciseArray(Workout workout) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				return w.getAllExercisesArray();
			}
		}
		return null;
	}
	
	
	
	//Set functions
	public void addSet(Workout workout, Exercise exercise, int reps, double weight) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				w.addSet(exercise, reps, weight);
			}
		}
	}
	
	public void editSet(Workout workout, Exercise exercise, Set set, int reps, double weight) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				w.editSet(exercise, set, reps, weight);
			}
		}
	}
	
	public void deleteSet(Workout workout, Exercise exercise, Set set) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				w.deleteSet(exercise, set);
			}
		}
	}
	
	public void clearSets(Workout workout, Exercise exercise) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				w.clearSets(exercise);
			}
		}
	}
	
	
	
	//Progression functions
	public void setFirstProgressionValue(Workout workout, Exercise exercise) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				w.setFirstProgressionValue(exercise);
			}
		}
	}
	
	public void calculateProgression(Workout workout, Exercise exercise) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				w.calculateProgression(exercise);
			}
		}
	}
	
	
	
	//Dataset functions
	public CategoryDataset createDataset(Workout workout, Exercise exercise) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())) {
				return w.createDataset(exercise);
			}
		}
		return null;
	}
	
	
	
	//Gui Functions
	//Workout GUI func
	public DefaultListModel<Workout> workoutsListGUI(DefaultListModel<Workout> workoutsList) {
		workoutsList = new DefaultListModel<Workout>();
		for(Workout w : workouts) {
			workoutsList.addElement(w);
		}
		return workoutsList;
	}
	
	public JComboBox<Workout> workoutsComboBoxGui(JComboBox<Workout> workoutsComboBox) {
		workoutsComboBox.removeAllItems();
		for(Workout w : workouts) {
			workoutsComboBox.addItem(w);
		}
		
		return workoutsComboBox;
	}
	
	//Exercise GUI func
	public DefaultListModel<Exercise> exerciseListGUI(Workout workout, DefaultListModel<Exercise> exerciseList) {
		exerciseList = new DefaultListModel<Exercise>();
		for(Workout w : workouts) {
			if(workout.getId().equals(w.getId())) {
				return w.exerciseListGUI(exerciseList);
			}
		}
		return exerciseList;
	}
	
	public  DefaultListModel<Exercise> cleanExerciseListGUI(DefaultListModel<Exercise> exerciseList){
		if(workouts.size() < 1) {
			System.out.println("in workout");
			exerciseList.removeAllElements();
		}
		return exerciseList;
	}
	
	public JComboBox<Exercise> exerciseComboBoxGui(Workout workout, JComboBox<Exercise> exerciseComboBox) {
		for(Workout w : workouts) {
			if(workout.getId().equals(w.getId())) {
				return w.exerciseComboBoxGui(exerciseComboBox);
			}
		}
		
		return exerciseComboBox;
	}
	
	//Set
	public DefaultListModel<Set> newSetGUIList(Workout workout, Exercise exercise, DefaultListModel<Set> setList) {
		for(Workout w : workouts) {
			if(w.getId().equals(workout.getId())){
				return w.newSetGUIList(exercise, setList);
			}
		}
		return setList;
	}
	
	
	
	//to String
	public String toString() {
		return name;
	}	
	
}
