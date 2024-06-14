package duketask;

public abstract class Task {

    private static int totalnumber=0; //class-level variable
    private boolean done;
    private String taskname;

    //constructor !donot have return type
    public Task(String taskname){
        this.done=false;
        this.taskname=taskname;

        totalnumber++;
    }

    public void setDoneToTrue(){
        this.done=true;
    }

    public static int getTotalnumber(){
        return totalnumber;
    }
    public static int decreaseTotalnumber(){
        totalnumber--;
        return totalnumber;
    }

    public boolean getDone(){
        return this.done;
    }

    public String getTaskname(){
        return this.taskname;
    }

    public String getStatusIcon(){
        return (this.getDone()?"\u2713":"\u2718");
    }


    @Override
    public String toString() {
        return this.taskname;
    }
    public Tasktype getType(){
        return null;
    }


    public abstract String getTime();
}
