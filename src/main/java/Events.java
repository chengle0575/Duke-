public class Events extends Task{

    private String at;

    public Events(String taskname,String at){
        super(taskname);
        this.at=at;
    }

    public String getTime(){
        return this.at;
    }
    public Tasktype getType(){
        return Tasktype.EVENT;
    }
    public String toString(){
        return this.getTaskname()+"(at:"+this.getTime()+")";
    }
}
