import java.util.Arrays;
import java.util.Scanner;


public class ChatFriend {
    private static String chatbox="Judy:";
    private static Task[] userInputStorage=new Task[100]; //This is a fixed size array

    public static void echo(){
        Scanner in=new Scanner(System.in);
        String readin=in.nextLine();

        while(!readin.equals("bye")){

            if(readin.equals("list")){
                listall();
            }
            else if(readin.contains("done")){
                String input[]=readin.split(" ");
                int tasknumber=Integer.parseInt(input[1]);
                Task tasktochange=userInputStorage[tasknumber-1];

                tasktochange.setDoneToTrue();
                System.out.println("Nice! I've marked this task as done:");
                String status=tasktochange.getDone()?"y":"n";
                String taskformat=String.format("[%s] %s",tasktochange.getStatusIcon(),tasktochange.getTaskname());
                System.out.println(taskformat);
            }
            else{
                Task newtask=new Task(readin);
                addToList(newtask);
                System.out.println(chatbox+readin);

            }
            readin=in.nextLine();
        }
    }

    public static void addToList(Task newtask){
        userInputStorage[newtask.getNumber()-1]=newtask;

    }

    public static void listall(){
        for(int i=0;i<Task.getTotalnumber();i++){
            Task newtask=userInputStorage[i];
            String taskprint=String.format("%d.[%s] %s", newtask.getNumber(),newtask.getStatusIcon(),newtask.getTaskname());
            System.out.println(taskprint);
        }
    }



    public static void main(String[] args) {
        String decoration="****--------------------------------------------------****";

        String greetings=decoration+"\n"+
                         chatbox+
                        "Hello! I'm ChatFriend :)\n"+
                        "What can I do for you?";
        System.out.println(greetings);


        echo();

        String bye=decoration+"\n"
                    +chatbox+
                   "Bye. Hope to see you again soon!";
        System.out.println(bye);
    }


}
