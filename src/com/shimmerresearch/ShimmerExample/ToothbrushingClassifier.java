/**
 * 
 */
package com.shimmerresearch.ShimmerExample;

import java.util.Queue;

/**
 * Classifies Toothbrushing 
 */
public class ToothbrushingClassifier {
	
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
		return classified;
	}
	
	
}
