package Controller;

import java.util.ArrayList;
import java.util.TreeSet;

public class Monitora {
	ArrayList<Double> listOfTasks = new ArrayList<Double>();
	ArrayList<Task> list = new ArrayList<Task>();
	// String listOfTasks[] = new String[5];
	private int direction = 0;
	private int stoppedOnFloor = 0;
	private boolean stopButtonPressed = false;

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

	public synchronized Task getFirstTask() {
		// if (list.size() == 0) {
		// return -1;
		// } else {

		return list.get(0);
		// }
	}

	public synchronized int getSizeOfListOfTasks() {
		return list.size();
	}

	public synchronized ArrayList<Task> addTaskFirst(Task task, int position) {
		list.remove(position);
		list.add(0, task);
		return list;
	}

	/*
	 * public synchronized ArrayList<Double> addTaskFirst(Double d, int
	 * position) { listOfTasks.remove(position); listOfTasks.add(0, d); return
	 * listOfTasks; }
	 */
	public synchronized void removeTask(Task task) {
		list.remove(task);
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

		list.clear();

	}

	public void setStopButtonPressed(boolean stopButtonPressed) {
		this.stopButtonPressed = stopButtonPressed;
	}

	public boolean isStopButtonPressed() {
		return stopButtonPressed;
	}

}