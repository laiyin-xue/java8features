package model;

public class TestA implements Test{

    public String message() {
        System.out.println("A begin...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("A end...");
        return "A";
    }
}
