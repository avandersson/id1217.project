package Controller;

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

}
