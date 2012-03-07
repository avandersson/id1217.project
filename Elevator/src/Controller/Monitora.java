package Controller;

import java.util.ArrayList;

public class Monitora {
	ArrayList<String> listOfTasks = new ArrayList<String>();
	// String listOfTasks[] = new String[5];
	private int direction;
	private Object sync = new Object();

	public void setTask(String command, int number) {

		if (command.equals("p")) {
			/*
			 * Elevator button pressed
			 */
			synchronized (listOfTasks) {
				listOfTasks.add(new String("p " + number));
				notify();

			}

		}

	}

	public String getTask() {
		synchronized (listOfTasks) {
			if (listOfTasks.size() == 0) {
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
	}

	public int getDirection() {
		synchronized (sync) {
			return direction;		
		}
	
	}

	public void setDirection(int direction) {
		synchronized (sync) {
			this.direction = direction;	
		}
	}

	public int getSizeOfTaks() {
		synchronized (listOfTasks) {
			return listOfTasks.size();
		}
	}

}
