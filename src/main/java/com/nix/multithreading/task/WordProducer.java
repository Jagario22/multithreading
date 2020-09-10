package com.nix.multithreading.task;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class WordProducer extends Thread {
    private BlockingQueue<String> queue;
    private static final String EOF = "quit";

    public WordProducer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        String word = "";

        while (true) {
            System.out.print("word: ");
            word = in.next();
            if (!word.equals(EOF))
                queue.add(word);
            else
                break;
        }
    }
}
