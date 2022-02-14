package de.neo.test;

public class Test {

    private static Test instance;

    public static Test getInstance() {
        if (instance == null) {
            instance = new Test();
        }
        return instance;
    }

    private String name;

    public Test() {
        instance = this;
        name = "Neo8";
    }

    private static String flag = "CTF{r3m0t3_d3bu991n9_ftw}";

    public static String getFlag(Test test) {
        return test.getClass().getName();
    }

    public String getRealFlag(Test test) {
        return flag;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("Moin, " + getInstance().name);
        try {
            Thread.sleep(15000);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Test.getFlag(new Test()));
        try {
            Thread.sleep(15000);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Flag: " + new Test().getRealFlag(new Test()));
        try {
            Thread.sleep(15000);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Goodbye World!");
        System.out.println("Bye, " + getInstance().name);
        while(true) {
            System.out.println(getInstance().name);
        }
    }

}
