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

public class MainController extends Thread implements ActionListener{
    Motor motor;
    Door door;
    Scale scale;
    Monitora[] monitor;// = new Monitora[MakeAll.getNumberOfElevators()];
    Elevators elevators;
    String rmihost, action[] = new String[3];
    int elevatorNumber, requestedFloor;
    
    public MainController(Monitora monitor[]){
    	this.monitor = monitor;
    }

	public void run(){
		try {
			
			
			MakeAll.init("localhost");
			MakeAll.addFloorListener(this);
		    MakeAll.addInsideListener(this);
		    MakeAll.addPositionListener(this);
		    MakeAll.addVelocityListener(this);		    
//		    Elevator elevator = MakeAll.getElevator(1);
		    Elevators eles = MakeAll.getElevators();
/*		    motor = MakeAll.getMotor(1);
		    scale = MakeAll.getScale(1);
		    door = MakeAll.getDoor(1);
		    door.open();
		    sleep(2000);
		    door.close();
		    
		    double where = elevator.whereIs();
		    motor.up();
		    do{
		    	//elevator.setScalePosition((int) where);
		    	sleep(100);
		    }while((where = elevator.whereIs()) < 1.999);
		    elevator.stop();
			door.open();
			sleep(1000);
			door.close();
			motor.down();
*/
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
    	
    }

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		
		Monitora[] monitorList = new Monitora[MakeAll.getNumberOfElevators()+1];
		for (int i = 1; i < MakeAll.getNumberOfElevators() + 1; i++) {
			monitorList[i] = new Monitora();
			new Thread(new ElevatorController(monitorList[i], i)).start();
			
		}
		
		new Thread(new MainController(monitorList)).start();
		
		

	}


@Override
public void actionPerformed(ActionEvent e) {
	
	action = e.getActionCommand().split(" ");
	elevatorNumber = Integer.parseInt(action[1]);
	
	if(action[0].equals("p")){
		/*
		 * Elevator button pressed
		 */
		requestedFloor = Integer.parseInt(action[2]);
		monitor[elevatorNumber].setTask(action[0], requestedFloor);
		
	}
	if(action[0].equals("b")){
		/*
		 * Floor button pressed
		 */
	}
	
	if(e.getActionCommand().equals("p 1 5")){
		try {
			motor.up();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	System.out.println("hej");
	System.out.println("command=" + e.getActionCommand());
	return;
	
}
}


