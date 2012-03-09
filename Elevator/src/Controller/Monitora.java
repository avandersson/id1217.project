package Controller;

import java.util.ArrayList;
import java.util.TreeSet;

public class Monitora {
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
						// listOfTasks.add(31000.0);
						notify();
					}
				} else {
					// listOfTasks.add(number);
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
					// listOfTasks.add(31000.0);
					notify();
				}
			} else {
				// listOfTasks.add(number);
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
	 * 
	 * @return The first task of this elevators ArrayList<Task>
	 */
	public synchronized Task getFirstTask() {
		return list.get(0);
	}

	/**
	 * 
	 * @return
	 */
	public synchronized int getSizeOfListOfTasks() {
		return list.size();
	}
	
	/**
	 * 
	 * @param task
	 * @param position
	 * @return
	 */
	public synchronized ArrayList<Task> addTaskFirst(Task task, int position) {
		list.remove(position);
		list.add(0, task);
		return list;
	}

	public synchronized void removeTask(Task task) {
		list.remove(task);
	}

	/**
	 * 
	 * @return
	 */
	public synchronized int getDirection() {
		return direction;
	}
	
	/**
	 * 
	 * @param direction
	 */
	public synchronized void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * 
	 * @return
	 */
	public int getStoppedOnFloor() {
		return stoppedOnFloor;
	}

	/**
	 * 
	 * @param stoppedOnFloor
	 */
	public void setStoppedOnFloor(int stoppedOnFloor) {
		this.stoppedOnFloor = stoppedOnFloor;
	}

	/**
	 * 
	 */
	public void clearList() {

		list.clear();

	}

	/**
	 * 
	 * @param stopButtonPressed
	 */
	public void setStopButtonPressed(boolean stopButtonPressed) {
		this.stopButtonPressed = stopButtonPressed;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isStopButtonPressed() {
		return stopButtonPressed;
	}

}