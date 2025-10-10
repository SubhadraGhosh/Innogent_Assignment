//------------------------------------------deadlock problem in multithreading----------------------------------------

class Task1 implements Runnable{

    private Pen pen;
    private Paper paper;

    public Task1(Pen pen,Paper paper){
        this.pen = pen;
        this.paper = paper;
    }

    public void run(){
        System.out.println("Task - 1");
    }
}

class Task2 implements Runnable{

    private Pen pen;
    private Paper paper;

    public Task2(Pen pen,Paper paper){

        this.pen = pen;
        this.paper = paper;
    }

    public void run(){
        System.out.println("Task -2  ");
    }
}

class Pen {

    public synchronized void writeWithPaperAndPen(Paper paper){
        paper.finishWriting();
    }

    public synchronized void finishWriting(){
        System.out.println("finsih writing method in pen class");
    }
}

class Paper {

    public synchronized void writeWithPenAndPaper(Pen pen){
        pen.finishWriting();
    }

    public synchronized void finishWriting(){
        System.out.println("finish writing by paper");
    }
}

class TestThread{

    public static void main(String[] args) {

        Pen pen = new Pen();
        Paper paper = new Paper();

        Thread thread1 = new Thread(new Task1(pen,paper),"Thread-1");
        Thread thread2 = new Thread(new Task2(pen,paper),"Thread-2");

        thread1.start();     thread2.start();
    }
}