package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.ArrayList;
import elevator.rmi.*;

public class ElevatorController implements ActionListener, Runnable {
	Monitora monitor;
	Elevator elevator;
	ArrayList<Double> list = new ArrayList<Double>();
	int id;
	double destinationFloor = 0, requestedFloor;
	boolean stopButtonPressed = false;

	public ElevatorController(Monitora monitor, int id) {
		this.monitor = monitor;
		this.id = id;
	}
	@Override
	public void run() {
		try {
			elevator = MakeAll.getElevator(id);
			MakeAll.addInsideListener(id, this);

			while(true){
				
				list = monitor.getTasks(destinationFloor);

				destinationFloor = list.get(0);


				if(monitor.getStoppedOnFloor() == destinationFloor){}
				else if(elevator.whereIs() < list.get(0)){	
					System.out.println("Elevator " + id + " moving up!");
					elevator.up();
					do {
						elevator.up();
						Thread.sleep(100);
						list = monitor.getTasks(destinationFloor);

						for(int i = 1; i < list.size(); i++){
							if(list.get(i)  > elevator.whereIs() && list.get(i) < destinationFloor){
								destinationFloor = list.get(i);
								list = monitor.addTaskFirst(list.get(i), i);
							}
						}

					} while (elevator.whereIs() < destinationFloor - 0.001 && stopButtonPressed == false);
					if(!stopButtonPressed){
						monitor.removeTask(list.get(0));
						elevator.stop();
						elevator.open();
						Thread.sleep(2000);
						elevator.close();
						Thread.sleep(1500);
					}
					stopButtonPressed = false;

				}else{
					System.out.println("Elevator " + id + " moving down!");
					elevator.down();
					do {
						elevator.down();
						Thread.sleep(100);

						list = monitor.getTasks(destinationFloor);

						for(int i = 1; i < list.size(); i++){
							if(list.get(i)  < elevator.whereIs() && list.get(i) > destinationFloor){
								destinationFloor = list.get(i);
								list = monitor.addTaskFirst(list.get(i), i);
							}
						}

					} while (elevator.whereIs() > destinationFloor + 0.001 && stopButtonPressed == false);
					if(!stopButtonPressed){
						monitor.removeTask(list.get(0));
						elevator.stop();
						elevator.open();
						Thread.sleep(2000);
						elevator.close();
						Thread.sleep(1500);
					}
					stopButtonPressed = false;
				}
			}

		} catch (IllegalParamException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e2) {

		String command = e2.getActionCommand();
		String commandArray[]  = new String[3];
		commandArray = command.split(" ");

		

		if(Integer.parseInt(commandArray[1]) == id && Double.parseDouble(commandArray[2]) == 32000){
			try {
				System.out.println("Elevator " + id + " stopped!");
				elevator.stop();
				stopButtonPressed = true;
				list.clear();
				monitor.clearList();

			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}else{
			/*
			 * normal command eller?
			 */
			requestedFloor = Double.parseDouble(commandArray[2]);
			monitor.setTask(requestedFloor);	
		}

	}

}
