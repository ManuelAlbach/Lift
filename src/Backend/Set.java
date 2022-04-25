package Backend;

import java.io.Serializable;

/**
 * 
 * @author Manuel
 *
 * Die Klasse speichert die Daten für die Sets
 *
 */

public class Set implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2394853873298807806L;
	
	int reps;
	double weight;
	
	
	//Getter & Setter
	public Set(int reps, double weight) {
		this.reps = reps;
		this.weight = weight;
	}
	
	public void setReps(int reps) {
		this.reps = reps;
	}
	
	public int getReps() {
		return reps;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}	
	
	public double getWeight() {
		return weight;
	}

	
	//to String
	public String toString() {
		return reps + "Reps" + " | " + weight + "KG";
	}
}
