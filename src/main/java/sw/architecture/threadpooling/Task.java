package sw.architecture.threadpooling;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Task implements Runnable {
	
	private TaskRequest taskRequest;
	
	private TaskPriority taskPriority;
	
	public Task(TaskRequest taskRequest, TaskPriority taskPriority) {
		super();
		this.taskRequest = taskRequest;
		this.taskPriority = taskPriority;
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName()+"-->"+"Task "+getTaskRequest().getId()+" Processing "+getTaskPriority());
		callWebService();
		System.out.println(Thread.currentThread().getName()+"-->"+"Task "+getTaskRequest().getId()+" Finished "+getTaskPriority());
//		System.out.println(Thread.currentThread().getName()+"-->"+"Task "+getTaskRequest().getId()+" "+getTaskPriority());
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public String callWebService(){
		try{
			URL url = new URL("https://api.darksky.net/forecast/26203e0c6ba04da121478fb06334688b/"+taskRequest.getLattitude() + "," + taskRequest.getLongitude());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			StringBuilder response = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			conn.disconnect();
			return response.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public TaskRequest getTaskRequest() {
		return taskRequest;
	}

	public TaskPriority getTaskPriority() {
		return taskPriority;
	}

	public void setTaskRequest(TaskRequest taskRequest) {
		this.taskRequest = taskRequest;
	}

	public void setTaskPriority(TaskPriority taskPriority) {
		this.taskPriority = taskPriority;
	}
}
