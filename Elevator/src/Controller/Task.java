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

	public boolean existsIn(ArrayList<Task> list) {

		if(list.size() == 0){
			return false;
		}else{
			for(int i = 0; i < list.size(); i++){
				if(list.get(i).getFloor() == floor && list.get(i).getDirection() == direction){
					return true;
				}
			}
			return false;
		}

	}

}
