package Backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 
 * @author Manuel
 *
 * Die Klasse speichert die Daten für die Körpermaße ab und handhabt die
 * Progressionsdaten in diesem Bereich. 
 *
 */

public class Measurements implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5951619324145974815L;
	
	String id = UUID.randomUUID().toString();
	String name;
	ArrayList <ProgressionMeasurements> measurements = new ArrayList<>();
	
	
	//Gette & Setter
	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList <ProgressionMeasurements> getMeasurementsArray(){
		return measurements;
	}
	
	
	
	//ProgressionMeasurements funct
	public void addProgressionData(double measurement) {
		ProgressionMeasurements progression = new ProgressionMeasurements();
		progression.setMeasurements(measurement);
		progression.setDate();
		if(measurements.size() > 6) {
			measurements.remove(0);
		}
		measurements.add(progression);
	}
	
	//create Dataset
	public CategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		   
	    String series1 = "Progression";
	    
	    for(ProgressionMeasurements p : measurements) {
	    	dataset.addValue(p.getMeasurements(), series1, p.getDate());
	    }
		 
	    return dataset;
    }
	
	
	
	//to String
	public String toString() {
		return name;
	}	
}
