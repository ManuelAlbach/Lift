package Backend;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Manuel
 *
 * Die Klasse speichert die Progressionsdaten für die Übungen
 *
 */

public class ProgressionWeight implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8441455608625765226L;
	
	double weight;
	String date;
	
	
	//Getter & Setter
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setDate() {
		String pattern = "dd.MM.yy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		this.date = simpleDateFormat.format(new Date());	
	}
	
	public String getDate() {
		return date;
	}
	
	
	
	//to String
	public String toString() {
		return String.valueOf(weight);
	}	
	
}
