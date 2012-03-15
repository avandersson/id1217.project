package Controller;

import java.util.ArrayList;
import java.util.TreeSet;

public class Monitor {
	ArrayList<Task> list = new ArrayList<Task>();
	private int direction = 0;
	private int stoppedOnFloor = 0;
	private boolean stopButtonPressed = false;

	/**
	 * Adds a task to the elevators task list
	 * 
	 * @param task
	 */
	public synchronized void setTask(Task task) {

		if (task.getCommand().equals("p")) {
			/*
			 * Elevator button pressed
			 */
			if (!task.existsIn(list)) {
				if (stoppedOnFloor == task.getFloor()) {
					if (task.existsIn(list)) {
					} else {
						/*
						 * On the correct floor, open the doors.
						 */
						list.add(task);
						notify();
					}
				} else {
					list.add(task);
					notify();

				}
			}

		} else if (task.getCommand().equals("b")) {
			if (!task.existsIn(list)) {
				if (task.existsIn(list)) {
				} else {
					/*
					 * On the correct floor, open the doors.
					 */
					list.add(task);
					notify();
				}
			} else {
				list.add(task);
				notify();

			}
		}

	}
	
	/**
	 * Gets a list of tasks that this elevator has
	 * 
	 * @param stoppedOnFloor
	 * @return list - The ArrayList of tasks this elevator has
	 */
	public synchronized ArrayList<Task> getTasks(int stoppedOnFloor) {
		if (list.size() == 0) {
			try {
				if (!stopButtonPressed) {
					this.setStoppedOnFloor(stoppedOnFloor);
					direction = 0;
				}
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			stopButtonPressed = false;
			this.setStoppedOnFloor(-1);
		}
		return list;
	}

	/**
	 * Gets the first task of this elevators
	 * 
	 * @return task
	 */
	public synchronized Task getFirstTask() {
		return list.get(0);
	}

	/**
	 * Gets the size of the list
	 * 
	 * @return size
	 */
	public synchronized int getSizeOfListOfTasks() {
		return list.size();
	}
	
	/**
	 * Adds the task to the front of the list
	 * 
	 * @param task The task that gets added first in the list
	 * @param position The position of the task
	 * @return list The new list
	 */
	public synchronized ArrayList<Task> addTaskFirst(Task task, int position) {
		list.remove(position);
		list.add(0, task);
		return list;
	}
	
	/**
	 * Removes task form the list
	 * 
	 * @param task The task to be removed
	 */
	public synchronized void removeTask(Task task) {
		list.remove(task);
	}

	/**
	 * Gets the direction of the elevator
	 * -1 = down
	 * 0 = no direction
	 * 1 = up
	 *  
	 * @return direction
	 */
	public synchronized int getDirection() {
		return direction;
	}
	
	/**
	 * Sets the direction of this elevator
	 * -1 = down
	 * 0 = no direction
	 * 1 = up
	 * 
	 * @param int direction
	 */
	public synchronized void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * Gets the floor where the elevator is stopped
	 * 
	 * @return -1 if the elevator is moving
	 */
	public int getStoppedOnFloor() {
		return stoppedOnFloor;
	}

	/**
	 * Sets the stopped on floor variable
	 * 
	 * @param stoppedOnFloor
	 */
	public void setStoppedOnFloor(int stoppedOnFloor) {
		this.stoppedOnFloor = stoppedOnFloor;
	}

	/**
	 * Clears the list 
	 */
	public void clearList() {
		list.clear();
	}

	/**
	 * Sets the stopped button variable
	 * 
	 * @param stopButtonPressed
	 */
	public void setStopButtonPressed(boolean stopButtonPressed) {
		this.stopButtonPressed = stopButtonPressed;
	}

	/**
	 * Checks if the stopped button is pressed
	 * 
	 * @return boolean True is the stop button has been pressed
	 */
	public boolean isStopButtonPressed() {
		return stopButtonPressed;
	}

}