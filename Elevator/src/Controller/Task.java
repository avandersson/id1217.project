package Controller;

import java.util.ArrayList;

/**
 * Task object. Contains the necessary information about each task
 * 
 * @author Alfred Andersson, Ivan Pedersen
 */

public class Task {
	private String command;
	private int floor, direction;

	public Task(String command, int floor, int direction) {
		this.command = command;
		this.floor = floor;
		this.direction = direction;
	}

	/**
	 * Gets the command of this task
	 * 
	 * @return String command
	 */

	public String getCommand() {
		return command;
	}

	/**
	 * Gets the floor requested by this task
	 * 
	 * @return int floor
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * The direction of this task
	 * 
	 * @return int Direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * True if the task already exists in the list given
	 * 
	 * @param list
	 *            The ArrayList to search through
	 * @return boolean
	 */
	public boolean existsIn(ArrayList<Task> list) {

		if (list.size() == 0) {
			return false;
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getFloor() == floor
						&& list.get(i).getDirection() == direction) {
					return true;
				}
			}
			return false;
		}

	}

}
