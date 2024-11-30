/*Name: Group 3 - CSC 131-06 : Shivjyot Brar (302813914), Alan Her (302842878), Benjamin Church (302299231), Sheng-Chang Chen (303372394),
                               Mohammed Bader (303189770), Ikki Ee Herng Chua (303902677), Jonathan Herman (303050007), Ayush (303335591)
*Date: November 4, 2024
*Title: BOLD Game Library Prototype
*Description: Game List Class
*/

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

class ListGame{
   private Game head;
   private Game tail;
   private static final int LENGTH = 75;
   
   //blank constructor
   public ListGame(){
      this.head = null;
      this.tail = null;
   }   
   
   //accessors
   public Game getHead(){
      return this.head;
   }
   
   public Game getTail(){
      return this.tail;
   }
   
   //mutators
   public void setHead(Game head){
      this.head = head;
   }
   
   public void setTail(Game tail){
      this.tail = tail;
   }
   
   //returns if list is empty passing in head and tail nodes
   public boolean isEmpty(){
      return(this.head == null && this.tail == null);
      }   
   //removes head, returns Game that was head
   public Game removeHead(){
      if(this.head == null && this.tail == null){
         System.out.println("No Games in List");
         return null;
      }
      
      //if list is only one item, remove final item
      if(this.head == this.tail){
         Game current = this.head;
         this.head = null;
         this.tail = null;
         return current;
      }
      
      Game oldHead = this.head; //save reference to old head
      this.head = this.head.getNext(); //move head reference
      Game current = oldHead; //save current of head being deleted for output
      oldHead.setNext(null); //delete link
      this.head.setPrev(null); //delete link
      
      return current;
   }
   
   //adds to tail of linked list taking in title, description and price
   public void addTail(String title, double price, String des){
      Game game = new Game(title, price, des);
      
      //if list is empty, assign head and tail to user
      if(this.head == null && this.tail == null){
         this.head = game;
         this.tail = game;
      } else {
         this.tail.setNext(game);
         game.setPrev(this.tail);
         this.tail = game;
      }
   }      
   
   //prints the list starting from head and moving down
   //type will choose which kind of print will happen: 0 = Title : 1 = Title, Price : 2 = Title, Price, Description : 3 = Count, Title
   public String printList(int type){
      String list = "";
      int gameCount = 0;
      
      //if list is empty, return empty message
      if(this.head == null && this.tail == null){
         return "The Game list is empty";
      }
      
      //traverse list
      Game current = head;
      
      while(current != null){
         gameCount++;
         list += String.format("\n%s%s%s%s",
                              (type == 3) ? gameCount + ": ": "",
                              current.getTitle(),
                              (type == 1 || type == 2) ? " $" + String.format("%.2f",current.getPrice()) : "",
                              (type == 2) ? "\n" + breakLines(current.getDes(), LENGTH) + "\n" : "");
         current = current.getNext();
      } 
      return list;
   }
   
   //to print info of specific game
   public void printGame(Game current){
         System.out.println("\n" + current.getTitle() + " $" + String.format("%.2f",current.getPrice()) + "\n" + breakLines(current.getDes(), LENGTH) + "\n");
   }
   //used to return int of games
   public int countGames(){
      int gameCount = 0;
      
      //if list is empty, return 0
      if(this.head == null && this.tail == null){
         return gameCount;
      }
      
      //traverse list
      Game current = head;
      
      while(current != null){
         gameCount++;
         current = current.getNext();
      } 
      return gameCount;
   }   
   
   //check to see if game object exists
   public boolean checkGame(String title){
      
      //if list is empty, new game is good
      if(this.head == null && this.tail == null){
         return false;
      }
      
      //traverse list checking all games
      Game current = this.head;
      
      while(current != null){
         //exit if it finds one that already exists
         if(title.equals(current.getTitle())){
            return true;
         }
         current = current.getNext();
      } 
      return false;
   }
   
   //return Game with given title
   public Game getGame(String title){
      
      //traverse list checking all Usernames
      Game current = this.head;
      
      while(current != null){
         //exit if it finds one that already exists
         if(title.equals(current.getTitle())){
            return current;
         }
         current = current.getNext();
      } 
      return null;
   }
   
   //delete game and repairing linked list
   public void deleteGame(Game temp){
   
      //if game is only game
      if(temp == this.head && temp == this.tail){
         this.head = null;
         this.tail = null;
         }else if(this.head == temp && this.tail != temp){ //if user is head but not tail
               this.head = this.head.getNext();
               temp.setNext(null);
               this.head.setPrev(null);
            }  else if(this.head != temp && this.tail == temp){ //if user is tail but not head
                  this.tail = this.tail.getPrev();
                  temp.setPrev(null);
                  this.tail.setNext(null);
               }  else if(this.head != temp && this.tail != temp){ //if user is neither head or tail
                  temp.getPrev().setNext(temp.getNext());
                  temp.getNext().setPrev(temp.getPrev());
                  }
      
   }   
    
   //export Games to file for saving
   public void exportGames(String name){
      String output = "";
      
      Game current = this.head;
      
      //traverse list getting info of each user for export
      while(current != null){
         output += (current.getTitle() + "\n" + current.getPrice() + "\n" + current.getDes() + "\n");
         current = current.getNext();
         removeHead();
      }
      
      Path path = Paths.get(".\\Game Lists\\" + name + ".txt");
      
      //write the list to a file
      try{
         Files.writeString(path, output, StandardCharsets.UTF_8);
      } catch (IOException ex){
         System.out.println("Invalid Path");
      }   
   } 
   
   public static String breakLines(String input, int charLength) {

       if (input == null || charLength <= 0) {
           System.out.println("Error: Input is Empty or Length is < 0");
           return input;
       }
   
       String result = "";
       int lastSpace = -1;
          
       for (int i = 0; i < input.length(); i++) {
           
           // Check for spaces to determine where to break
           if (input.charAt(i) == ' ') {
               lastSpace = i; // Update lastSpace position
           }
   
           // If we reach the charLength, we break the line
           if (i >= charLength) {
               // If there was a space before charLength, break there
               if (lastSpace != -1) {
                   result += input.substring(0, lastSpace) + "\n";
                   // Remove the processed part from input
                   input = input.substring(lastSpace + 1);
                   i = -1; // Reset the index for the new input
                   lastSpace = -1; // Reset lastSpace for the new line
               } else {
                   // If no space was found, just break at charLength
                   result += input.substring(0, charLength) + "\n";
                   input = input.substring(charLength);
                   i = -1; // Reset the index for the new input
               }
           }
       }
   
       // Add any remaining text
       if (input.length() > 0) {
           result += input;
       }
   
       return result;
   }

                 
}