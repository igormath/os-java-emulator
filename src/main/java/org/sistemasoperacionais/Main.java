package org.sistemasoperacionais;

public class Main {
    public static void main(String[] args) {
        OperationalSystem os = new OperationalSystem();
        os.startSecondChanceAlgorithm();

//        String[] thread1OperationsSequence = {"4-R", "5-R", "0-R", "4-W-2"};
        String[] thread1OperationsSequence = {"0-W-24", "1-W-63", "1-R", "2-W-17", "2-R", "3-W-41", "4-W-8", "4-R", "7-W-9", "7-R"};
//        String[] thread2OperationsSequence = {"1-R", "5-W-4", "2-R", "2-W-6"};

        Process thread1 = new Process(thread1OperationsSequence, os, 1);
//        Process thread2 = new Process(thread2OperationsSequence, os, 2);

        thread1.start();
//        thread2.start();

        try{
            thread1.join();
//            thread2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
