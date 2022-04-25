package Backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.DefaultListModel;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 
 * @author Manuel
 *
 * Die Klasse ist für das Setzen der Ziel Sets und Wiederholungen zuständig und regelt ebenfalls,
 * die Handhabung der Sets, der Progressionsdaten so wie auch der Informationen, welche dem Chart übermittelt werden.
 *
 */

public class Exercise implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7913696737175558367L;
	
	String id = UUID.randomUUID().toString();
	String name;
	ArrayList <Set> sets = new ArrayList<>();
	ArrayList <ProgressionWeight> progressionList =  new ArrayList<>();
	int aimSetsNumber;
	int aimRepsNumber;
	int aimAllReps;
	double progressionValue;
	
	
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
	
	public 	ArrayList <Set> getSets() {
		return sets;
	}
	
	public 	ArrayList <ProgressionWeight> getProgressionList() {
		return progressionList;
	}
	
	public void setAimSetsNumber(int aimSetsNumber) {
		this.aimSetsNumber = aimSetsNumber;
	}
	
	public int getAimSetsNumber() {
		return aimSetsNumber;
	}
	
	public void setAimRepsNumber(int aimRepsNumber) {
		this.aimRepsNumber = aimRepsNumber;
	}
	
	public int getAimRepsNumber() {
		return aimRepsNumber;
	}
	
	public void setProgressionValue(double progressionValue) {
		this.progressionValue = progressionValue;
	}
	
	public double getProgressionValue() {
		return progressionValue;
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
	public void setFirstProgressionValue() {
		ProgressionWeight progression = new ProgressionWeight();
		progression.setDate();
		progressionValue = round(progressionValue, 2);
		progression.setWeight(progressionValue);
		progressionList.add(progression);
	}
	
	public void calculateProgression() {
		ProgressionWeight progression = new ProgressionWeight();
		progression.setDate();
		int i = 0;
		int doneRepsNumber = 0;
		progressionValue = 0;
		for(Set s : sets) {
			i += 1;
			doneRepsNumber += s.reps;
			progressionValue = progressionValue + s.weight;
		}
		
		progressionValue = progressionValue/i;
		progressionValue = round(progressionValue, 2);
		

		aimAllReps = aimRepsNumber * aimSetsNumber;
		
		double progressionWeight = progressionList.get(progressionList.size()-1).getWeight();
		
		if(aimAllReps <= doneRepsNumber && progressionWeight <= progressionValue) {
			progression.setWeight(progressionValue);
		} else if (aimAllReps > doneRepsNumber && progressionWeight > progressionValue) {
			progression.setWeight(progressionValue);
		} else if (aimAllReps <= doneRepsNumber && progressionWeight > progressionValue) {
			progression.setWeight(progressionValue);
		}  else if (aimAllReps > doneRepsNumber && progressionWeight < progressionValue) {
			progression.setWeight(progressionWeight);
		}
		
		if(progressionList.size() > 6) {
			progressionList.remove(0);
		}
		
		progressionList.add(progression);
	}
	
	
	
	//create Dataset
	public CategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		   
	    String series1 = "Progression";
	    
	    for(ProgressionWeight p : progressionList) {
	    	dataset.addValue(p.getWeight(), series1, p.getDate());
	    }
		 
	    return dataset;
    }
	
	
	
	//Round Values
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	
	
	//GUI functions
	public DefaultListModel<Set> newSetGUIList(DefaultListModel<Set> setList) {
		setList = new DefaultListModel<Set>();
		for(Set s : sets) {
			setList.addElement(s);
		}
		return setList;
	}

	
	
	//to String
	public String toString() {
		return name;
	}	
}
