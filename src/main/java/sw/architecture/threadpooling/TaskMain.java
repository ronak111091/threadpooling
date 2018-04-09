package sw.architecture.threadpooling;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class TaskMain {
	public static void main(String[] args) {
//		Task task1 = new Task(new TaskRequest(1, "0.584", "0.874"), TaskPriority.LOW);
//		Task task2 = new Task(new TaskRequest(2, "0.584", "0.874"), TaskPriority.MEDIUM);
//		Task task3 = new Task(new TaskRequest(3, "0.584", "0.874"), TaskPriority.MEDIUM);
//		Task task4 = new Task(new TaskRequest(4, "0.584", "0.874"), TaskPriority.HIGH);
//		Task task5 = new Task(new TaskRequest(5, "0.584", "0.874"), TaskPriority.LOW);
//		Task task6 = new Task(new TaskRequest(6, "0.584", "0.874"), TaskPriority.HIGH);
//		
//		TaskScheduler taskScheduler = new TaskScheduler(1, 10);
//		
//		taskScheduler.scheduleTask(task1);
//		taskScheduler.scheduleTask(task2);
//		taskScheduler.scheduleTask(task3);
//		taskScheduler.scheduleTask(task4);
//		taskScheduler.scheduleTask(task5);
//		taskScheduler.scheduleTask(task6);
		
		TaskMain taskMain = new TaskMain();
		taskMain.start();
		
	}
	
	public void start(){
		TaskScheduler taskScheduler = new TaskScheduler(10,100);
		List<Task> tasks = new ArrayList<>();
		CSVReader reader = null;
		try {
			InputStream in = this.getClass().getClassLoader()
	                .getResourceAsStream("aceleracaoLinear_terra.csv");
			reader = new CSVReader(new InputStreamReader(in));
			reader.readNext();
			String[] row = reader.readNext();
			int rowNo = 1;
			while(row!=null && rowNo<=100) {
				TaskPriority priority = null;
				if(rowNo>10 && Util.isPrimeNumber(rowNo)) {
					priority = TaskPriority.HIGH;
				}else {
					priority = TaskPriority.LOW;
				}
				Task task = new Task(new TaskRequest(rowNo, row[2].substring(0, 6), row[3].substring(0, 6)),priority);
//				System.out.println("Scheduling task "+rowNo);
				tasks.add(task);
				row = reader.readNext();
				rowNo++;
			}
			
			for(Task task:tasks){
				taskScheduler.scheduleTask(task);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
