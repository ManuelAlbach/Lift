package Backend;

import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * @author Manuel
 *
 * Die Klasse speichert die Informationen des Profils ab, damit diese nach dem Schließen des Programmes 
 * erhalten bleiben. Darunter fallen das One Rep Max, sowie auch die Körpermaße-
 *
 */

public class Profile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6074241280536782019L;
	
	String id = UUID.randomUUID().toString();
	String name;
	OneRMExercise exercise;
	Measurements measurement;
	
	public Profile(OneRMExercise exercise, Measurements measurement) {
		this.exercise = exercise;
		this.measurement = measurement;
	}
	
	
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
	
	public void setExercise(OneRMExercise exercise) {
		this.exercise = exercise;
	}
	
	public OneRMExercise getExercise() {
		return exercise;
	}
	
	public void setMeasurement(Measurements measurement) {
		this.measurement = measurement;
	}
	
	public Measurements getMeasurement() {
		return measurement;
	}
}
