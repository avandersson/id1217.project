package Controller;

import java.util.ArrayList;
import java.util.TreeSet;

public class Monitora {
	ArrayList<Double> listOfTasks = new ArrayList<Double>();
	//String listOfTasks[] = new String[5];
	private int direction = 0; 
	private double stoppedOnFloor = 0;

	public synchronized void setTask(double number){

		/*		if(command.equals("p")){
			/*
		 * Elevator button pressed
		 */
		if(!listOfTasks.contains((double) number)){
			listOfTasks.add(number);
		}

		notify();

		//}

	}


	public synchronized ArrayList<Double> getTasks(double stopedOnFloor){
		if(listOfTasks.size() == 0){
			try {
				this.setStoppedOnFloor(stopedOnFloor);
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
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


	public double getStoppedOnFloor() {
		return stoppedOnFloor;
	}


	public void setStoppedOnFloor(double stoppedOnFloor) {
		this.stoppedOnFloor = stoppedOnFloor;
	}


	public void clearList() {
		
		listOfTasks.clear();
		
	}

}