import java.util.Arrays;
import java.util.Scanner;


public class ChatFriend {
    private static final String CHATBOX="Judy:";
    private static Task[] userInputStorage=new Task[100]; //This is a fixed size array
    private static final String DECORATION="****--------------------------------------------------****";

    public static void keepReading(){
        Scanner in=new Scanner(System.in);
        String readin=in.nextLine();

        while(!readin.equals("bye")){
            doAccordingInput(readin);
            readin=in.nextLine();
        }
    }
    
    public static void doAccordingInput(String readin){
        if(readin.equals("list")){
            listall();
        }
        else if(readin.contains("done")){
            String input[]=readin.split(" ");
            int tasknumber=Integer.parseInt(input[1]);
            makeTaskDone(tasknumber);
        }
        else{
            addToList(readin);
        }

    }

    public static void makeTaskDone(int tasknumber){
        Task tasktochange=userInputStorage[tasknumber-1];

        tasktochange.setDoneToTrue();
        System.out.println("Nice! I've marked this task as done:");
        String taskformat=String.format("[%s] %s",tasktochange.getStatusIcon(),tasktochange.getTaskname());
        System.out.println(taskformat);
    }

    public static void addToList(String taskname){
        Task newtask=new Task(taskname);
        userInputStorage[newtask.getNumber()-1]=newtask;
        System.out.println(CHATBOX+taskname);

    }

    public static void listall(){
        for(int i=0;i<Task.getTotalnumber();i++){
            Task newtask=userInputStorage[i];
            String taskprint=String.format("%d.[%s] %s", newtask.getNumber(),newtask.getStatusIcon(),newtask.getTaskname());
            System.out.println(taskprint);
        }
    }

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
