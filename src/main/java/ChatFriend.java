import java.util.Arrays;
import java.util.Scanner;


public class ChatFriend {
    private static final String CHATBOX="Judy:";
    private static Task[] userInputStorage=new Task[100]; //This is a fixed size array
    private static final String DECORATION="****--------------------------------------------------****";

    /*---------keep reading the input and do something accordingly-------------*/
    public static void keepReading(){
        Scanner in=new Scanner(System.in);
        String readin=in.nextLine();

        while(!readin.equals("bye")){
            doAccordingInput(readin);
            readin=in.nextLine();
        }
    }
    
    public static void doAccordingInput(String readin){
            if (readin.equals("list")) {
                listall();
            } else if (readin.contains("done")) {
                String input[] = readin.split(" ");
                int tasknumber = Integer.parseInt(input[1]);
                makeTaskDone(tasknumber);

            } else if (readin.contains("deadline") || readin.contains("todo") || readin.contains("event")) {
                Task addedtask = addToList(readin);
                System.out.println(CHATBOX + addedtask);
            } else {
                System.out.println(readin);
            }

    }
    /*---------parse input to get taskname and tasktime-------------*/
    public static String parseGetTaskName(String readin) throws DukeException{
        String[] inputAftSplit=readin.split("/");
        //String nameAft=inputAftSplit[0].replace(inputAftSplit[1],"");
        int firstspace=inputAftSplit[0].indexOf(" ");

        if(firstspace==-1){
                throw new DukeException();
        }

        String taskname = inputAftSplit[0].substring(firstspace);
        return taskname;
    }

    public static String parseGetTaskTime(String readin) throws DukeException{
        String[] inputAftSplit=readin.split("/");
        String tasktime=null;
        if(inputAftSplit.length>1){
            int firstcolon=inputAftSplit[1].indexOf(" ");
            tasktime=inputAftSplit[1].substring(firstcolon);
        }else{
            if(!readin.split(" ")[0].equals("todo")){
                throw new DukeException();
            }
        }

        return tasktime;
    }


    /*---------manipulate the task status-------------*/
    public static void makeTaskDone(int tasknumber){
        Task tasktochange=userInputStorage[tasknumber-1];

        tasktochange.setDoneToTrue();
        System.out.println("Nice! I've marked this task as done:");
        String taskformat=String.format("[%s] %s",tasktochange.getStatusIcon(),tasktochange);
        System.out.println(taskformat);
    }


    /*---------modify task list-------------*/
    public static Task addToList(String readin){
        String cmd=readin.split(" ")[0];
        String taskname=null;
        String tasktime=null;

        try{
            taskname=parseGetTaskName(readin);
            tasktime=parseGetTaskTime(readin);
        }catch (DukeException e){
            System.out.println("OOPS!!! The description of a "+cmd+" cannot be empty.");
            return null;
        }

        Task newtask = null;

        if(readin.contains("deadline")) {
            Deadlines deadline=new Deadlines(taskname,tasktime);
            newtask=(Task) deadline;
        } else if(readin.contains("event")){
            Events event=new Events(taskname,tasktime);
            newtask=(Task) event;
        } else if(readin.contains("todo")){
            ToDos todo=new ToDos(taskname);
            newtask=(Task) todo;
        } else{
            newtask=new Task(taskname);
        }

        userInputStorage[newtask.getNumber()-1]=newtask;
        return newtask;
    }

    public static void listall(){
        for(int i=0;i<Task.getTotalnumber();i++){
            Task newtask=userInputStorage[i];
            String taskprint=String.format("%d.[%s][%s]%s", newtask.getNumber(),newtask.getType(),newtask.getStatusIcon(),newtask);
            System.out.println(taskprint);
        }
    }

    /*---------greeting and bye-------------*/
    public static void printhello(){
        String greetings=DECORATION+"\n"+
                CHATBOX+
                "Hello! I'm ChatFriend :)\n"+
                "What can I do for you?";
        System.out.println(greetings);
    }

    public static void printbye(){
        String bye=DECORATION+"\n"
                +CHATBOX+
                "Bye. Hope to see you again soon!";
        System.out.println(bye);
    }


    public static void main(String[] args) {
        printhello();
        keepReading();
        printbye();
    }


}
