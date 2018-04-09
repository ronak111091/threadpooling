package sw.architecture.threadpooling;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {

    private ExecutorService priorityTaskPoolExecutor;
    private ExecutorService priorityTaskScheduler = 
      Executors.newSingleThreadExecutor();
    private PriorityBlockingQueue<Task> priorityQueue;

    public TaskScheduler(Integer poolSize, Integer queueSize) {
        priorityTaskPoolExecutor = Executors.newFixedThreadPool(poolSize);
        priorityQueue = new PriorityBlockingQueue<Task>(queueSize, 
          Comparator.comparing(Task::getTaskPriority));

        priorityTaskScheduler.execute(()->{
            while (true) {
                try {
                    priorityTaskPoolExecutor.execute(priorityQueue.take());
                } catch (InterruptedException e) {
                    // exception needs special handling
                    break;
                }
            }
        });
    }

    public void scheduleTask(Task job) {
        priorityQueue.add(job);
    }

    public int getQueuedTaskCount() {
        return priorityQueue.size();
    }

    protected void close(ExecutorService scheduler) {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }

    public void closeScheduler() {
        close(priorityTaskPoolExecutor);
        close(priorityTaskScheduler);
    }

}	
