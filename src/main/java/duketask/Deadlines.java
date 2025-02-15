package duketask;

public class Deadlines extends Task {
    private String by;

    public Deadlines(String taskname,String by){
        super(taskname);
        this.by=by;
    }

    public String getTime(){
        return this.by;
    }

    public Tasktype getType(){
        return Tasktype.DEADLINE;
    }

    public String toString(){
        return this.getTaskname()+"(by:"+this.getTime()+")";
    }
}
