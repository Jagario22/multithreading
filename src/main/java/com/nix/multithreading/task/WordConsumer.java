package com.nix.multithreading.task;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class WordConsumer implements Runnable {
    private BlockingQueue<String> words;
    private File file;

    public WordConsumer(File file, BlockingQueue<String> words) {
        this.file = file;
        this.words = words;
    }

    @Override
    public void run() {
        if (words.size() != 0) {
            try (PrintWriter writer = new PrintWriter(file)) {
                String word = words.poll();
                writer.println(word);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
