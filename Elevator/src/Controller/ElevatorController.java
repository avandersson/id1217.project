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
	ArrayList<Task> list = new ArrayList<Task>();
	int id;
	double destinationFloor = 0, requestedFloor, time;

	// boolean stopButtonPressed = false;

	public ElevatorController(Monitora monitor, int id) {
		this.monitor = monitor;
		this.id = id;
	}

	@Override
	public void run() {
		try {
			MakeAll.addInsideListener(id, this);
			MakeAll.addVelocityListener(this);
			elevator = MakeAll.getElevator(id);

			time = MakeAll.getVelocity() * 10000;

			while (true) {

				list = monitor.getTasks((int) destinationFloor);

				for (int i = 1; i < list.size(); i++) {
					if (monitor.getDirection() == 1
							&& list.get(i).getFloor() > elevator.whereIs()
							&& list.get(i).getDirection() >= 0) {

						destinationFloor = list.get(i).getFloor();
						list = monitor.addTaskFirst(list.get(i), i);

					} else if (monitor.getDirection() == -1
							&& list.get(i).getFloor() < elevator.whereIs()
							&& list.get(i).getDirection() <= 0) {
						destinationFloor = list.get(i).getFloor();
						list = monitor.addTaskFirst(list.get(i), i);

					}
				}

				destinationFloor = list.get(0).getFloor();

				if (destinationFloor == 31000.0) {
					elevator.open();
					Thread.sleep(2000);
					elevator.close();
					Thread.sleep(1500);
					monitor.removeTask(list.get(0));
				} else if (elevator.whereIs() < destinationFloor) {
					System.out.println("Elevator " + id + " moving up!");
					while (elevator.whereIs() < destinationFloor - 0.001
							&& !monitor.isStopButtonPressed()) {
						elevator.up();
						monitor.setDirection(1);
						/*
						 * updates the scale
						 */
						int where = (int) (elevator.whereIs() + 0.1);
						elevator.setScalePosition(where);
						Thread.sleep(10);
						list = monitor.getTasks((int) destinationFloor);

						for (int i = 1; i < list.size(); i++) {
							if (list.get(i).getFloor() > elevator.whereIs()
									&& list.get(i).getFloor() < destinationFloor
									&& list.get(i).getDirection() >= 0) {

								destinationFloor = list.get(i).getFloor();
								list = monitor.addTaskFirst(list.get(i), i);

							}

						}

					}
					int where = (int) (elevator.whereIs() + 0.1);
					elevator.setScalePosition(where);
					// monitor.setStoppedOnFloor((int)elevator.whereIs());
					if (!monitor.isStopButtonPressed()) {
						elevator.stop();
						elevator.open();
						Thread.sleep((long) (2000 / time));
						elevator.close();
						Thread.sleep((long) (1500 / time));
						print();
						if (!monitor.isStopButtonPressed()) {
							monitor.removeTask(list.get(0));
						}
					}
					monitor.setStopButtonPressed(false);

				} else {
					System.out.println("Elevator " + id + " moving down!");
					while (elevator.whereIs() > destinationFloor + 0.001
							&& !monitor.isStopButtonPressed()) {
						elevator.down();
						monitor.setDirection(-1);
						int where = (int) (elevator.whereIs() + 0.1);
						elevator.setScalePosition(where);
						Thread.sleep(10);

						list = monitor.getTasks((int) destinationFloor);

						for (int i = 1; i < list.size(); i++) {
							if (list.get(i).getFloor() < elevator.whereIs()
									&& list.get(i).getFloor() > destinationFloor
									&& list.get(i).getDirection() <= 0) {

								destinationFloor = list.get(i).getFloor();
								list = monitor.addTaskFirst(list.get(i), i);

							}

						}

					}
					int where = (int) (elevator.whereIs() + 0.1);
					elevator.setScalePosition(where);
					// monitor.setStoppedOnFloor((int)elevator.whereIs());
					if (!monitor.isStopButtonPressed()) {
						elevator.stop();
						elevator.open();
						Thread.sleep((long) (2000 / time));
						elevator.close();
						Thread.sleep((long) (1500 / time));
						print();
						if (!monitor.isStopButtonPressed()) {
							monitor.removeTask(list.get(0));
						}
					}
					monitor.setStopButtonPressed(false);
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
		String commandArray[] = new String[3];
		commandArray = command.split(" ");
		/*
		 * Parameter three sets the direction, panel buttons got no direction
		 */
		if (commandArray[0].equals("v")) {
			time = Double.valueOf(commandArray[1]) * 10000;
			System.out.println(commandArray[1]);
		} else {

			Task task = new Task(commandArray[0], Integer
					.parseInt(commandArray[2]), 0);

			if (Integer.parseInt(commandArray[1]) == id
					&& Double.parseDouble(commandArray[2]) == 32000) {
				/*
				 * stop command. stop and clear the task list.
				 */
				try {
					System.out.println("Elevator " + id + " stopped!");
					elevator.stop();
					/*
					 * not stopped on any floor
					 */
					monitor.setStopButtonPressed(true);
					list.clear();
					monitor.clearList();

				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} else {
				/*
				 * normal command, send to task list.
				 */
				requestedFloor = Double.parseDouble(commandArray[2]);
				monitor.setTask(task);
			}
		}

	}

	protected void print() {
		for (int j = 0; j < list.size(); j++) {
			System.out.println("Position in list:" + j + "\tFloor: "
					+ list.get(j).getFloor() + "\tDirection: "
					+ list.get(j).getDirection());
		}

	}
}
