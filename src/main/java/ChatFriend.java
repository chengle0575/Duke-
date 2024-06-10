import java.util.Arrays;
import java.util.Scanner;


public class ChatFriend {
    private static String chatbox="Judy:";
    private static String[] userInputStorage=new String[0]; //This is a fixed size array

    public static void echo(){
        Scanner in=new Scanner(System.in);
        String readin=in.nextLine();

        while(!readin.equals("bye")){

            if(readin.equals("list")){
                listall();
            }
            else{
                addToList(readin);
                System.out.println(chatbox+readin);

            }
            readin=in.nextLine();
        }
    }

    public static void addToList(String userInput){
        //with list number
        int number= userInputStorage.length+1;
        userInputStorage= Arrays.copyOf(userInputStorage,number);
        String inputToStore=number+"."+userInput;
        userInputStorage[number-1]=inputToStore;
    }

    public static void listall(){
        for(int i=0;i< userInputStorage.length;i++){
            System.out.println(userInputStorage[i]);
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
