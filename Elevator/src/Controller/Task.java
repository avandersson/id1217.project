package Controller;

import java.util.ArrayList;

public class Task {
	private String command;
	private int floor, direction;

	public Task(String command, int floor, int direction){
		this.command = command;
		this.floor = floor;
		this.direction = direction;
	}

	public String getCommand() {
		return command;
	}

	public int getFloor() {
		return floor;
	}

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
