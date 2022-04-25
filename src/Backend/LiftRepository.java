package Backend;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import javax.swing.JComboBox;

import org.jfree.data.category.CategoryDataset;

/**
 * 
 * @author Manuel
 *
 * Die Klasse ist für die Handhabung aller Informationen zuständig, hier werden diese Serialisiert und
 * gespeichert. Es werden von hieraus alle Klassen gehandhabt und alle Funktionen aufgerufen, welche im Front-End
 * enthalten sind. Ebenfalls ist diese Klasse dafür zuständig, dass das Front-End mit dem Backend verbunden wird. 
 *
 */


public class LiftRepository implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1174747622580018958L;

	ArrayList <Plan> plans = new ArrayList<Plan>();
	ArrayList <Measurements> measurements = new ArrayList<Measurements>();
	ArrayList <OneRMExercise> exercisesOneRM = new ArrayList<OneRMExercise>();
	ArrayList <Profile> profileList = new ArrayList<Profile>();
	
	
	static LiftRepository liftRep = new LiftRepository();
	
	
	//Instance
	public static LiftRepository getInstance() {
		return liftRep;
	}
	
	
	
	//Getter & Setter
	public  ArrayList <Plan> getPlansArray() {
		return plans;
	}
	
	public ArrayList <Measurements> getMeasurementsArray(){
		return measurements;
	}
	
	public ArrayList <OneRMExercise> getExerciseOneRMArray(){
		return exercisesOneRM;
	}
	
	public ArrayList <Profile> getProfileArray(){
		return profileList;
	}
	
	
	
	//Plan Functions
	public  void addPlan(String name) {
		Plan plan = new Plan();
		plan.setName(name);
		plans.add(plan);
		
		write();
	}
	
	public void editPlan(Plan p, String name) {
		p.setName(name);
		
		write();
	}
	
	public void deletePlan(Plan p) {
		plans.remove(p);
		
		write();
	}
	
	
	
	//Workout Functions
	public void safeAddWorkout(Plan plan, String name) {
		for(Plan p : plans) {
			if(plan.getId().equals(p.getId())) {
				p.addWorkout(name);
			}
		}
		
		write();
	}
	
	public void safeEditWorkout(Plan plan, Workout workout, String name) {
		for(Plan p : plans) {
			if(plan.getId().equals(p.getId())) {
				p.editWorkout(workout, name);
			}
		}
		
		write();
	}
	
	public void safeDeleteWorkout(Plan plan, Workout workout) {
		for(Plan p : plans) {
			if(plan.getId().equals(p.getId())) {
				p.deleteWorkout(workout);
			}
		}
		
		write();
	}
	
	
	
	//Exercise functions
	public void fillAllExercises(Plan plan, Workout workout) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				p.fillAllExerciseArray(workout);
			}
		}
	}
	
	public void safeAddAimValues(Plan plan, Workout workout, Exercise exercise, int sets, int reps, double weight) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				p.addAimValues(workout, exercise, sets, reps, weight);
			}
		}
		
		write();
	}
	
	public void safeEditExercise(Plan plan, Workout workout, Exercise exercise,  int sets, int reps, double weight) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				p.editExercise(workout, exercise, sets, reps, weight);
			}
		}
		
		write();
	}
	
	public void safeDeleteExercise(Plan plan, Workout workout, Exercise exercise) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				p.deleteExercise(workout, exercise);
			}
		}
		
		write();
	}
	
	public ArrayList<Exercise> getExerciseArray(Plan plan, Workout workout) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				return p.getExerciseArray(workout);
			}
		}
		return null;
	}
	
	public void safeSetFirstProgressionValue(Plan plan, Workout workout, Exercise exercise) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				p.setFirstProgressionValue(workout, exercise);
			}
		}
		
		write();
	}
	
	
	
	//Set functions
	public void safeAddSet(Plan plan, Workout workout, Exercise exercise, int reps, double weight) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				p.addSet(workout, exercise, reps, weight);
			}
		}
	
		for(OneRMExercise e : exercisesOneRM) {
			if(e.getName().equals(exercise.getName())){
				e.addSet(reps, weight);
			}
		}
		
		write();
	}
	
	public void safeEditSet(Plan plan, Workout workout, Exercise exercise, Set set, int reps, double weight) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				p.editSet(workout, exercise, set, reps, weight);
			}
		}
		
		for(OneRMExercise e : exercisesOneRM) {
			if(e.getName().equals(exercise.getName())){
				e.editSet(set, reps, weight);
			}
		}
	
		write();
	}
	
	public void safeDeleteSet(Plan plan, Workout workout, Exercise exercise, Set set) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				p.deleteSet(workout, exercise, set);
			}
		}	
		
		for(OneRMExercise e : exercisesOneRM) {
			if(e.getName().equals(exercise.getName())){
				e.deleteSet(set);
			}
		}
	
		write();
	}
	
	public void clearSets(Plan plan, Workout workout, Exercise exercise) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				p.clearSets(workout, exercise);
			}
		}
		
		for(OneRMExercise e : exercisesOneRM) {
			if(e.getName().equals(exercise.getName())){
				e.clearSets();
			}
		}
		
	}
	
	
	
	//Measurements functions
	public void addMeasurements(String name) {
		Measurements measurement = new Measurements();
		measurement.setName(name);
		measurements.add(measurement);
	}
	
	public void fillMeasurementsArray() {
		if(measurements.size() < 1) {
			addMeasurements("Weight");
			addMeasurements("Body Fat");
			addMeasurements("Chest");
			addMeasurements("Shoulders");
			addMeasurements("Left Biceps");
			addMeasurements("Right Biceps");
			addMeasurements("Waist");
			addMeasurements("Left Tight");
			addMeasurements("Right Tight");
		}
	}
		
	
	
	//OneRMExerciseProgression funct
	public void addOneRMExercise(String name) {
		OneRMExercise exerciseRM = new OneRMExercise();
		exerciseRM.setName(name);
		exercisesOneRM.add(exerciseRM);
	}
	
	public void fillOneRMExerciseArray() {
		addOneRMExercise("Benchpress");
		addOneRMExercise("Squat");
		addOneRMExercise("Deadlift");
		addOneRMExercise("Militarypress");
		addOneRMExercise("Bent-Over-Rows");
		addOneRMExercise("Lateral Raises");
		addOneRMExercise("Reverse-Flys");
		addOneRMExercise("Biceps-Curls");
		addOneRMExercise("Triceps-Pushdown");
	}
	
	public void setFirstProgressionValueOneRM(Exercise exercise, int reps, double weight) {
		for(OneRMExercise e : exercisesOneRM) {
			if(e.getName().equals(exercise.getName())) {
				e.addSet(reps, weight);
				e.calculateProgression();
				e.clearSets();
				System.out.println(e.getProgressionListArray());
			}
		}
		
		write();
		
	}
	
	
	
	//Profilefunctions
	public void addProfile(OneRMExercise exercise, Measurements measurement) {
		Profile p = new Profile(exercise, measurement);
		profileList.add(p);
	}
	
	public void fillProfileList() {
		OneRMExercise exercise = new OneRMExercise();
		Measurements measurement = new Measurements();
		if(profileList.size() < 1) {
			addProfile(exercise, measurement);
			addProfile(exercise, measurement);
			addProfile(exercise, measurement);
			addProfile(exercise, measurement);
			addProfile(exercise, measurement);
			addProfile(exercise, measurement);
		}
	}
	
	public void editProfile(Profile profile, OneRMExercise exercise, Measurements measurement) {
		for(Profile p : profileList) {
			if(p.getId().equals(profile.getId())) {
				p.setExercise(exercise);
				p.setMeasurement(measurement);
			}
		}
		
		write();
	}
	
	
	
	//Progression functions	
	public void calculateProgression(Plan plan, Workout workout, Exercise exercise) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				p.calculateProgression(workout, exercise);
			}
		}
		
		for(OneRMExercise e : exercisesOneRM) {
			if(e.getName().equals(exercise.getName())) {
				e.calculateProgression();
			}
		}
		
		write();
	}
	
	//Measurements
	public void safeAddProgressionData(Measurements measurement, double measurementValue) {
		for(Measurements m : measurements) {
			if(m.getId().equals(measurement.getId())) {
				m.addProgressionData(measurementValue);
			}
		}
		
		write();
	}
	
	
	
	//Dataset functions
	public CategoryDataset createDatasetExercise(Plan plan, Workout workout, Exercise exercise) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())) {
				return p.createDataset(workout, exercise);
			}
		}
		return null;
	}
	
	public CategoryDataset createDatasetOneRMExercise(OneRMExercise exercise) {
		for(OneRMExercise e : exercisesOneRM) {
			if(e.getName().equals(exercise.getName())) {
				return e.createDataset();
			}
		}
		return null;
	}	
	
	public CategoryDataset createDatasetMeasurements(Measurements measurement) {
		for(Measurements m : measurements) {
			if(m.getId().equals(measurement.getId())) {
				return m.createDataset();
			}
		}
		return null;
	}
	
	
	
	//GUI functions
	//Plan GUI func
	public DefaultListModel<Plan> plansListGUI(DefaultListModel<Plan> planList) {
		planList = new DefaultListModel<Plan>();
		for(Plan p : plans) {
			planList.addElement(p);
		}
		return planList;
	}
	
	public JComboBox <Plan> plansComboBoxGui(JComboBox <Plan> planComboBox) {
		planComboBox.removeAllItems();
		for(Plan p : plans) {
			planComboBox.addItem(p);
		}
		return planComboBox;
	}
	
	//Workout GUI func
	public DefaultListModel<Workout> workoutsListGUI(Plan plan, DefaultListModel<Workout> workoutList) {
		for(Plan p : plans) {
			if(plan.getId().equals(p.getId())) {
				return p.workoutsListGUI(workoutList);
			}
		}
		return workoutList;
	}
	
	public JComboBox<Workout> workoutsComboBoxGui(Plan plan, JComboBox<Workout> workoutsComboBox) {
		for(Plan p : plans) {
			if(plan.getId().equals(p.getId())) {
				return p.workoutsComboBoxGui(workoutsComboBox);
			}
		}
		
		return workoutsComboBox;
	}
	
	public JComboBox<Workout> cleanWorkoutsComboBoxGui(JComboBox<Workout> workoutsComboBox) {
		if(plans.isEmpty()) {
			workoutsComboBox.removeAllItems();
		}
		return workoutsComboBox;
	}	
	
	//Exercise GUI func
	public DefaultListModel<Exercise> exerciseListGUI(Plan plan, Workout workout, DefaultListModel<Exercise> exerciseList) {
		for(Plan p : plans) {
			if(plan.getId().equals(p.getId())) {
				return p.exerciseListGUI(workout, exerciseList);
			}
		}
		return exerciseList;
	}
	
	public JComboBox<Exercise> exerciseComboBoxGui(Plan plan, Workout workout, JComboBox<Exercise> exerciseComboBox) {
		for(Plan p : plans) {
			if(plan.getId().equals(p.getId())) {
				return p.exerciseComboBoxGui(workout, exerciseComboBox);
			}
		}
		
		return exerciseComboBox;
	}
	
	
	//Set GUI func
	public DefaultListModel<Set> newSetGUIList(Plan plan, Workout workout, Exercise exercise, DefaultListModel<Set> setList) {
		for(Plan p : plans) {
			if(p.getId().equals(plan.getId())){
				return p.newSetGUIList(workout, exercise, setList);
			}
		}
		return setList;
	}
	
	//measurements GUI func
	public DefaultListModel<Measurements> measurementsListGUI(DefaultListModel<Measurements> bodyList) {
		bodyList = new DefaultListModel<Measurements>();
		for(Measurements m : measurements) {
			bodyList.addElement(m);
		}
		return bodyList;
	}
	
	public JComboBox<Measurements> measurementsComboBoxGui(JComboBox<Measurements> measuremntComboBox) {
		measuremntComboBox.removeAllItems();
		for(Measurements m : measurements) {
			measuremntComboBox.addItem(m);
		}
		return measuremntComboBox;
	}
	
	//ExerciseOneRm GUI func
	public JComboBox<OneRMExercise> oneRMExerciseComboBoxGui(JComboBox<OneRMExercise> oneRMExerciseComboBox) {
		oneRMExerciseComboBox.removeAllItems();
		for(OneRMExercise e : exercisesOneRM) {
			oneRMExerciseComboBox.addItem(e);
		}
		return oneRMExerciseComboBox;
	}
	
	//check Values
	public String getRightValueAxisLabel(Measurements measurements) {
		String kg = "KG";
		String kfa = "BFP(%)";
		String cm = "Cm";
				
		if(measurements.getName() != null) {
			if(measurements.getName().equals("Weight")) {
				return kg;
			} else if (measurements.getName().equals("Body Fat")) {
				return kfa;
			}else {
				return cm;
			}
		} else {
			return "";
		}
	}
	
	
	
	//Serialisierung
	public void write(){
		try {
			FileOutputStream fos = new FileOutputStream("Data.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.plans);
			oos.writeObject(this.measurements);
			oos.writeObject(this.exercisesOneRM);
			oos.writeObject(this.profileList);
			oos.close();
		}catch(IOException e) {
			e.getStackTrace();
		}
	}
	

	@SuppressWarnings("unchecked")
	public static LiftRepository read() {
		LiftRepository liftRep = LiftRepository.getInstance();
		try {
			FileInputStream fis = new FileInputStream("Data.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			liftRep.plans = (ArrayList <Plan>) ois.readObject();
			liftRep.measurements = (ArrayList <Measurements>) ois.readObject();
			liftRep.exercisesOneRM = (ArrayList <OneRMExercise>) ois.readObject();
			liftRep.profileList = (ArrayList<Profile>) ois.readObject();
			ois.close();
			return liftRep;
		} catch (ClassNotFoundException | IOException  e){
			e.getStackTrace();
		}
		return liftRep;
	}
	
}
