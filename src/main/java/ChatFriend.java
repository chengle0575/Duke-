import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        try {
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
                throw new DukeException();
                //System.out.println(readin);
            }
        }catch (DukeException e){
            System.out.println(CHATBOX+"OOPS!!! I'm sorry, but I don't know what that means :-(");
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

        String taskname = inputAftSplit[0].substring(firstspace).trim();
        return taskname;
    }

    public static String parseGetTaskTime(String readin) throws DukeException{
        String[] inputAftSplit=readin.split("/");
        String tasktime=null;
        if(inputAftSplit.length>1){
            int spaceindex=inputAftSplit[1].indexOf(" ");
            tasktime=inputAftSplit[1].substring(spaceindex).trim();
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

        writetoDisk();
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
            System.out.println(CHATBOX+"OOPS!!! The description of a "+cmd+" cannot be empty.");
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
        } /*else{
            newtask=new Task(taskname);
        }*/

        userInputStorage[newtask.getNumber()-1]=newtask;

        writetoDisk();
        return newtask;
    }

    public static void listall(){
        for(int i=0;i<Task.getTotalnumber();i++){
            Task newtask=userInputStorage[i];
            String taskprint=String.format("%d.[%s][%s]%s", newtask.getNumber(),newtask.getType(),newtask.getStatusIcon(),newtask);
            System.out.println(taskprint);
        }
    }



    /*---------disk interaction operation-------------*/

    public static File initfile() throws IOException{
        File f=new File("../disksaving/duketask.txt");
        if(!f.exists()){
                System.out.println("file not exist. create now");
                String newdirectory = "../disksaving";
                File newdir = new File(newdirectory);
                newdir.mkdirs();

                String newfile = "../disksaving/duketask.txt";
                File newf = new File(newfile);
                newf.createNewFile();
        }
        return f;
    }
    public static void writetoDisk(){
        try {
            File f = initfile();
            FileWriter filewriter = new FileWriter(f);
            for(int i=0;i<Task.getTotalnumber();i++) {
                Task tosave=userInputStorage[i];
                filewriter.write(tosave.getType()+"|"+tosave.getDone()+"|"+tosave.getTaskname()+"|"+tosave.getTime());
                filewriter.write("/r");
            }
            filewriter.close();
        }catch (IOException ioe){
            System.out.println("IO exception happened");
        }
    }

    public static String loadfromDisk(){
        String readfromdisk = "";
        try {
            File f = initfile();
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                readfromdisk += s.nextLine();
            }
        }catch (IOException ioe){
            System.out.println("IO exception happened");
        }
        if(!readfromdisk.equals("")) {
            refilluserInputStorage(readfromdisk);
        }
        return readfromdisk;
    }

    public static void refilluserInputStorage(String s){
        String[] taskstringArray=s.split("/r");

        for(int i=0;i<taskstringArray.length;i++){
            Task taskback=parsetoTask(taskstringArray[i]);
            userInputStorage[i]=taskback;
        }
    }

    public static Task parsetoTask(String s){
        String[] aftsplit=s.split("\\|");
        String tasktype=aftsplit[0];
        String isdone=aftsplit[1];
        String taskname=aftsplit[2];
        String tasktime=aftsplit[3];
        Task tasktoreturn=null;
        if(tasktype.equals("TODO")){
            tasktoreturn=new ToDos(taskname);
        }else if(tasktype.equals("DEADLINE")){
            tasktoreturn=new Deadlines(taskname,tasktime);
        }else if(tasktype.equals("EVENT")){
            tasktoreturn=new Events(taskname,tasktime);
        }
        if(isdone.equals("true")){
            tasktoreturn.setDoneToTrue();
        }
        return tasktoreturn;
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
        loadfromDisk();
        printhello();
        keepReading();
        printbye();
    }


}
