package com.luxoft.bankapp.networking;

/**
 * Created by Sergey Popov on 15.04.14.
 */
public class BankServerMonitor implements Runnable {
    private boolean shouldStop = false;

    @Override
    public void run() {
        while (!shouldStop) {
            try {
                Thread.sleep(10000);
                System.out.println("\nClients in the queue: " +
                        BankServerThreaded.connectQueueCount.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        shouldStop = true;
    }
}
