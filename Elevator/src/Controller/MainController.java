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
	Monitora[] monitor;
	Elevators elevators;
	String rmihost, action[] = new String[3];
	int elevatorNumber;
	double requestedFloor;

	public MainController(Monitora monitor[]) {
		this.monitor = monitor;
	}

	public void run() {
		try {

			MakeAll.init("localhost");
			MakeAll.addFloorListener(this);
			MakeAll.addVelocityListener(this);

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws RemoteException,
			MalformedURLException, NotBoundException {

		Monitora[] monitorList = new Monitora[MakeAll.getNumberOfElevators() + 1];
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

		System.out.println("M command=" + e.getActionCommand());
		return;

	}
}
