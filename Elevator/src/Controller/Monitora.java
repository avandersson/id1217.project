package Controller;

import java.util.ArrayList;
import java.util.TreeSet;

public class Monitora {
	ArrayList<Double> listOfTasks = new ArrayList<Double>();
	//String listOfTasks[] = new String[5];
	private int direction = 0; 
	private int stoppedOnFloor = 0;
	private boolean stopButtonPressed = false;
	

	public synchronized void setTask(double number){

		/*
		 * Elevator button pressed
		 */
		if(!listOfTasks.contains((double) number)){
			if (stoppedOnFloor == (int)number) {
				/*
				 * already at this floor, do nothing
				 */
			}else{
				listOfTasks.add(number);	
				notify();

			}
			
		}



	}


	public synchronized ArrayList<Double> getTasks(int stopedOnFloor){
		if(listOfTasks.size() == 0){
			try {
				if (!stopButtonPressed) {
					this.setStoppedOnFloor(stopedOnFloor);
					direction = 0;
				}
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			stopButtonPressed = false;
			this.setStoppedOnFloor(-1);
		}
		return listOfTasks;
	}
	public synchronized ArrayList<Double> addTaskFirst(Double d, int position){
		listOfTasks.remove(position);
		listOfTasks.add(0, d);
		return listOfTasks;
	}

	public synchronized void removeTask(double d){
		listOfTasks.remove((double) d);
	}


	public synchronized int getDirection() {
		return direction;
	}


	public synchronized void setDirection(int direction) {
		this.direction = direction;
	}


	public int getStoppedOnFloor() {
		return stoppedOnFloor;
	}


	public void setStoppedOnFloor(int stoppedOnFloor) {
		this.stoppedOnFloor = stoppedOnFloor;
	}


	public void clearList() {
		
		listOfTasks.clear();
		
	}


	public void setStopButtonPressed(boolean stopButtonPressed) {
		this.stopButtonPressed = stopButtonPressed;
	}


	public boolean isStopButtonPressed() {
		return stopButtonPressed;
	}

}