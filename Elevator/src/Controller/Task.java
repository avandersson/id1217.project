package Controller;

/**
 * Task object. Contains the necessary information about each task
 * 
 * @author Alfred Andersson, Ivan Pedersen
 */
public class Task {
	private String command;
	private int floor, direction;
	
	public Task(String command, int floor, int direction){
		this.command = command;
		this.floor = floor;
		this.direction = direction;
	}
	/**
	 * @return command The command of this task
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @return floor The floor requested by this task
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * @return direction The direction of this task
	 */
	public int getDirection() {
		return direction;
	}

}
