package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import elevator.rmi.Door;
import elevator.rmi.Elevator;
import elevator.rmi.Elevators;
import elevator.rmi.MakeAll;
import elevator.rmi.Motor;
import elevator.rmi.Scale;

public class MainController extends Thread implements ActionListener {
	Motor motor;
	Door door;
	Scale scale;
	Monitor[] monitor;
	Elevators elevators;
	String rmihost, action[] = new String[3];
	int elevatorNumber;
	int numOfElevators;
	double requestedFloor;

	public MainController(Monitor monitor[]) {
		this.monitor = monitor;
	}

	public void run() {
		try {

			MakeAll.init("localhost");
			MakeAll.addFloorListener(this);
			numOfElevators = MakeAll.getNumberOfElevators();
			elevators = MakeAll.getElevators();

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws RemoteException,
			MalformedURLException, NotBoundException, InterruptedException {

		Monitor[] monitorList = new Monitor[MakeAll.getNumberOfElevators() + 1];
		for (int i = 1; i < MakeAll.getNumberOfElevators() + 1; i++) {
			monitorList[i] = new Monitor();
			new Thread(new ElevatorController(monitorList[i], i)).start();
			Thread.sleep(10);
		}

		new Thread(new MainController(monitorList)).start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		action = e.getActionCommand().split(" ");
		elevatorNumber = Integer.parseInt(action[1]);
		int direction = Integer.parseInt(action[2]);
		int floor = Integer.parseInt(action[1]);
		int closeUpFloor = floor + 1;
		int closeDownFloor = floor - 1;
		boolean done = false;
		Task task = new Task(action[0], floor, direction);

		System.out.println("M command=" + e.getActionCommand());
		try {
			
			for (int i = 1; i < numOfElevators +1; i++) {
				
				if(task.existsIn(monitor[i].list)){
					done = true;
				}
				
			}
			
			if (direction == 1) {
				/*
				 * up action requested
				 */

				
				for (int i = 1; i < numOfElevators + 1 && !done; i++) {
					if (monitor[i].getStoppedOnFloor() == floor) {
						/*
						 * this elevator is stationary on this floor, choose
						 * this one
						 */
						monitor[i].setTask(task);
						done = true;
					}
				}
				for (int i = 1; i < numOfElevators + 1 && !done; i++) {
					/*
					 * find a elevator on close floor that's going in the right direction
					 */
					if (monitor[i].getDirection() == 1
							&& elevators.whereIs(i) < floor
							&& elevators.whereIs(i) > closeDownFloor
							&& monitor[i].list.get(0).getDirection() != -1) {
						monitor[i].setTask(task);
						done = true;
					}
				}
				for (int i = 1; i < numOfElevators + 1 && !done; i++) {
					/*
					 * finds stationary elevator on above or below floor
					 */
					if ((task.getFloor() != 0 && monitor[i].getStoppedOnFloor() == floor - 1) || 
							(task.getFloor() != MakeAll.getNumberOfFloors() && monitor[i].getStoppedOnFloor() == floor + 1)) {
						monitor[i].setTask(task);
						done = true;
					}
					
				}
				for (int i = 1; i < numOfElevators + 1 && !done; i++) {
					/*
					 * find a elevator floor that's going in the right direction
					 */
					if (monitor[i].getDirection() == 1
							&& elevators.whereIs(i) < floor
							&& monitor[i].list.get(0).getDirection() != -1) {
						monitor[i].setTask(task);
						done = true;
					}
				}
				if (!done) {
					int smallestListSize = monitor[1].getSizeOfListOfTasks();
					int index = 1;
					for (int i = 2; i < numOfElevators + 1 && !done; i++) {
						if (monitor[i].getSizeOfListOfTasks() < smallestListSize) {
							smallestListSize = monitor[i].getSizeOfListOfTasks();
							index = i;
						}
					}
					monitor[index].setTask(task);
					done = true;
				}
			
			} else {
				/*
				 * down action requested
				 */
				for (int i = 1; i < numOfElevators + 1 && !done; i++) {
					if (monitor[i].getStoppedOnFloor() == floor) {
						/*
						 * this elevator is stationary on this floor, choose
						 * this one
						 */
						monitor[i].setTask(task);
						done = true;
					}
				}
				for (int i = 1; i < numOfElevators + 1 && !done; i++) {
					/*
					 * find a elevator on close floor that's going in the right direction
					 */
					if (monitor[i].getDirection() == -1
							&& elevators.whereIs(i) > floor
							&& elevators.whereIs(i) < closeUpFloor
							&& monitor[i].list.get(0).getDirection() != 1) {
						monitor[i].setTask(task);
						done = true;
					}
				}
				for (int i = 1; i < numOfElevators + 1 && !done; i++) {
					/*
					 * finds stationary elevator on above or below floor
					 */
					if ((task.getFloor() != 0 &&  monitor[i].getStoppedOnFloor() == floor - 1) || 
							(task.getFloor() != MakeAll.getNumberOfFloors() && monitor[i].getStoppedOnFloor() == floor + 1)) {
						monitor[i].setTask(task);
						done = true;
					}
					
				}
				for (int i = 1; i < numOfElevators + 1 && !done; i++) {
					/*
					 * find a elevator floor that's going in the right direction
					 */
					if (monitor[i].getDirection() == -1
							&& elevators.whereIs(i) > floor
							&& monitor[i].list.get(0).getDirection() != 1) {
						monitor[i].setTask(task);
						done = true;
					}
				}
				if (!done) {
					int smallestListSize = monitor[1].getSizeOfListOfTasks();
					int index = 1;
					for (int i = 2; i < numOfElevators + 1 && !done; i++) {
						if (monitor[i].getSizeOfListOfTasks() < smallestListSize) {
							smallestListSize = monitor[i].getSizeOfListOfTasks();
							index = i;
						}
					}
					monitor[index].setTask(task);
					done = true;
				}

			}
		} catch (RemoteException e2) {
			e2.printStackTrace();

		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NotBoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		return;

	}
}
