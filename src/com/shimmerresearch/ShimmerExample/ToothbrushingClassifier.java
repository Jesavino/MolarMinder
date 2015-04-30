/**
 * 
 */
package com.shimmerresearch.ShimmerExample;

import java.util.Queue;

/**
 * Classifies Toothbrushing 
 */
public class ToothbrushingClassifier {

	boolean[] toothArray = new boolean[30];
	int toothIndex = 0;

	

	/**
	 * Classifies a queue of size 50
	 * @param q : queue to be classified
	 * @return : boolean True if the user is brushing their teeth and false if not.
	 */
	public boolean betterClassifier(Queue q){
		boolean classified = false;
		int count = 0;
		int sum = 0;
		//Get peaks
		while(q.size() > 2){
			double previousVal = Math.abs(Double.parseDouble((String)q.poll()));
			double val = Math.abs(Double.parseDouble((String)q.poll()));
			double nextVal = Math.abs(Double.parseDouble((String)q.peek()));
			
			if(val < previousVal && val < nextVal){
				System.out.println("----------------------------THIS IS A MIN--------------------------");
				sum--;
				count++;
			}
			
			if(val > previousVal && val > nextVal){
				System.out.println("----------------------------THIS IS A MAX--------------------------");
				sum++;
				count++;
			}
			
		}
		if(count > 4  && Math.abs(sum)<2){
			classified = true;
		}

		buildToothArray(classified);
		return classified;
	}
	/**
	 * Builds tooth array
	 * @param b the boolean from the toothbrush classifiers
	 */
	public void buildToothArray(boolean b){
		//Add value to array
		if(toothIndex < 30){
			toothArray[toothIndex] = b;
		}
		//reset array
		else{
			classifyLongTerm();
			toothArray = new boolean[30];
		}
		toothIndex++;
	}

	
	public void classifyLongTerm(){
		int tcount = 0;
		int fcount = 0;
		for(int i = 0; i < toothArray.length; i++){
			if(toothArray[i] = true){
				tcount++;
			}
			else{
				fcount++;
			}
		}
		if(tcount / toothArray.length >= .75){
			System.out.println("LONG TOOTHBRUSHING CLASSIFIER---------------------------------------------------------------------------------------------------------------");
		}
		else{
			System.out.println("-------------------------------------------------------------------------------------------------------------------NOT CLASSIFIED LONG TERM");
		}
	}


	
	
}
