package model;

public class TestB implements Test{

    public String message() {
        System.out.println("B begin...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("B end...");
        return "B";
    }
}
