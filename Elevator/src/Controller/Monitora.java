package Controller;

import java.util.ArrayList;

public class Monitora {
	ArrayList<String> listOfTasks = new ArrayList<String>();
	//String listOfTasks[] = new String[5];
	private int direction;
	
	public synchronized void setTask(String command, int number){
		
		if(command.equals("p")){
			/*
			 * Elevator button pressed
			 */
			listOfTasks.add(new String("p "+ number));
			notify();
			
		}
		
	}
	
	
	public synchronized String getTask(){
		if(listOfTasks.size() == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String ret = listOfTasks.get(0);
		listOfTasks.remove(0);
		return ret;
	}


	public synchronized int getDirection() {
		return direction;
	}


	public synchronized void setDirection(int direction) {
		this.direction = direction;
	}

}
