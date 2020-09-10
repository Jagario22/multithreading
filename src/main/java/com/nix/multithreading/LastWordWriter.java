package com.nix.multithreading;

import com.nix.multithreading.task.WordConsumer;
import com.nix.multithreading.task.WordProducer;

import java.io.File;
import java.util.concurrent.*;

class LastWordWriter implements FileWriter {
    private BlockingQueue<String> queue = new LinkedBlockingDeque<>();
    private Future<?> executorFuture;
    private ExecutorService executor;
    private ScheduledExecutorService scheduler;
    private File file;

    LastWordWriter(File file, ExecutorService executor, ScheduledExecutorService scheduler) {
        this.executor = executor;
        this.scheduler = scheduler;
        this.file = file;
    }

    public void write() {
        setExecutor();
        setSchedule();
    }

    private void setExecutor() {
        WordProducer wordProducer = new WordProducer(queue);
        executorFuture = executor.submit(wordProducer);
    }

    private void setSchedule() {
        WordConsumer wordConsumer = new WordConsumer(file, queue);
        scheduler.scheduleWithFixedDelay(wordConsumer, 0, 1, TimeUnit.SECONDS);
    }

    public void shutdown() {
        checkConfiguration();

        executor.shutdown();
        try {
            executorFuture.get();
            while (true) {
                if (queue.size() == 0) {
                    scheduler.shutdown();
                    break;
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void checkConfiguration() {
        if (executor == null)
            throw new NullPointerException("The executor was not configured");

        if (scheduler == null)
            throw new NullPointerException("Scheduler was not configured");
    }
}
