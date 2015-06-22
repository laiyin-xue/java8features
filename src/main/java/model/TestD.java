package model;


public class TestD implements Test{

    public String message() {
        System.out.println("D begin...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("D end...");
        return "D";
    }
}
