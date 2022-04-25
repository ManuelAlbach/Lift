package Backend;

import java.io.Serializable;
import java.util.ArrayList;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 
 * @author Manuel
 *
 * Die Klasse speichert die Progression des 1 Rep Max ab, handhabt diese und 
 * verwaltet ebenfalls die Sets welche zur dazugehörigen Übung getätigt wurden.
 *
 */

public class OneRMExercise implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3371120085962374439L;
	
	String name;
	ArrayList <Set> sets =  new ArrayList<>();
	ArrayList <ProgressionWeight> progressionList =  new ArrayList<>();
	double doneReps;
	double progressionWeight;
	
	//getter & setter
	public void setName (String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList <ProgressionWeight> getProgressionListArray(){
		return progressionList;
	}
	

	
	//Set functions
	public void addSet(int reps, double weight){
		Set set = new Set(reps, weight);	
		sets.add(set);
	}
	
	public void editSet(Set set, int reps, double weight) {
		set.setReps(reps);
		set.setWeight(weight);
	}
	
	public void deleteSet(Set set) {
		sets.remove(set);
	}
	
	public void clearSets() {
		sets =  new ArrayList<Set>(); 
	}
	
	
	
	//calculate Progression
	public void calculateProgression() {
		ProgressionWeight progression = new ProgressionWeight();
		progression.setDate();
		int i = 0;
		progressionWeight = 0;
		doneReps = 0;
		for(Set s : sets) {
			doneReps += s.getReps();
			progressionWeight += s.getWeight();
			i += 1;
		}
		
		doneReps = doneReps / i;
		progressionWeight = progressionWeight / i;
		
		if(doneReps > 0 && doneReps < 3) {
			progression.setWeight(progressionWeight);
		} else if(doneReps > 3 && doneReps < 5) {
			progressionWeight = progressionWeight / 0.9;
			progression.setWeight(progressionWeight);
		} else if(doneReps > 4 && doneReps < 7) {
			progressionWeight = progressionWeight / 0.85;
			progression.setWeight(progressionWeight);
		} else if(doneReps > 6 && doneReps < 9) {
			progressionWeight = progressionWeight / 0.8;
			progression.setWeight(progressionWeight);
		} else if(doneReps > 8 && doneReps < 13) {
			progressionWeight = progressionWeight / 0.75;
			progression.setWeight(progressionWeight);
		} else if(doneReps > 12 && doneReps < 16) {
			progressionWeight = progressionWeight / 0.7;
			progression.setWeight(progressionWeight);
		} else if(doneReps > 14 && doneReps < 21) {
			progressionWeight = progressionWeight / 0.65;
			progression.setWeight(progressionWeight);
		}  else if(doneReps > 20 && doneReps < 26) {
			progressionWeight = progressionWeight / 0.6;
			progression.setWeight(progressionWeight);
		}
		
		if(progressionList.size() > 9) {
			progressionList.remove(0);
		}
		progressionList.add(progression);
	}
	
	//Create DataSet
	public CategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		   
	    String series1 = "Progression";
	    
	    for(ProgressionWeight p : progressionList) {
	    	dataset.addValue(p.getWeight(), series1, p.getDate());
	    }
		 
	    return dataset;
    }
	
	
	
	//to String
	public String toString() {
		return name;
	}	
}
