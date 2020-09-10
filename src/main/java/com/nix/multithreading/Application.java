package com.nix.multithreading;

import java.io.File;
import java.util.concurrent.*;

public class Application {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String path = "words.txt";
        File file = new File(path);

        LastWordWriter wordWriter = new LastWordWriter(file, executor, scheduler);
        wordWriter.write();
        wordWriter.shutdown();
        System.out.println("File writing is over");
    }
}
