public class ToDos extends Task{

    public ToDos(String todoname){
        super(todoname);
    }

    public Tasktype getType(){
        return Tasktype.TODO;
    }
    @Override
    public String toString() {
        return this.getTaskname();
    }
}
