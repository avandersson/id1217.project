package Controller;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import elevator.rmi.Elevator;
import elevator.rmi.IllegalParamException;
import elevator.rmi.MakeAll;

public class ElevatorController implements Runnable {
	Monitora monitor;
	String[] task = new String[2];
	int id;
	Elevator elevator;

	public ElevatorController(Monitora monitor, int id) {
		this.monitor = monitor;
		this.id = id;
	}

	@Override
	public void run() {
		try {
			elevator = MakeAll.getElevator(id);

			while(true){
			String temp = monitor.getTask();
			task = temp.split(" ");
			
				if(task[0].equals("p")){
					if(Integer.parseInt(task[1]) == 32000) elevator.stop();

					if(elevator.whereIs() < Double.parseDouble(task[1])){	
						elevator.up();
						do {
							Thread.sleep(100);
							monitor.getTask();
						} while (elevator.whereIs() < Double.parseDouble(task[1])-0.001);
						elevator.stop();
						elevator.open();
						Thread.sleep(2000);
						elevator.close();
						Thread.sleep(1500);
					}else{
						elevator.down();
						do {
							Thread.sleep(100);
						} while (elevator.whereIs() > Double.parseDouble(task[1])+0.001);
						elevator.stop();
						elevator.open();
						Thread.sleep(2000);
						elevator.close();
						Thread.sleep(1500);
					}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
