/*Name: Group 3 - CSC 131-06 : Shivjyot Brar (302813914), Alan Her (302842878), Benjamin Church (302299231), Sheng-Chang Chen (303372394),
                               Mohammed Bader (303189770), Ikki Ee Herng Chua (303902677), Jonathan Herman (303050007), Ayush (303335591)
*Date: October 31, 2024
*Title: BOLD Game Library Prototype
*Description: User List Class
*/

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

import java.io.File;

class ListUser{
   private User head;
   private User tail;
   
   //blank constructor
   public ListUser(){
      this.head = null;
      this.tail = null;
   }   
   
   //accessors
   public User getHead(){
      return this.head;
   }
   
   public User getTail(){
      return this.tail;
   }
   
   //returns if list is empty passing in head and tail nodes
   public boolean isEmpty(){
      return(this.head == null && this.tail == null);
      }   
   
   //removes head, returns User that was head
   public User removeHead(){
      if(this.head == null && this.tail == null){
         System.out.println("No Users in List");
         return null;
      }
      
      //if list is only one item, remove final item
      if(this.head == this.tail){
         User current = this.head;
         this.head = null;
         this.tail = null;
         return current;
      }
      
      User oldHead = this.head; //save reference to old head
      this.head = this.head.getNext(); //move head reference
      User current = oldHead; //save current of head being deleted for output
      oldHead.setNext(null); //delete link
      this.head.setPrev(null); //delete link
      
      return current;
   }   
   
   //adds to tail of linked list User
   public void addTail(User user){
      
      //if list is empty, assign head and tail to user
      if(this.head == null && this.tail == null){
         this.head = user;
         this.tail = user;
      } else {
         this.tail.setNext(user);
         user.setPrev(this.tail);
         this.tail = user;
      }
   } 
   
   //adds to tail of linked list taking in username and pw
   public void addTail(String username, String password, int userStatus){
      User user = new User(username, password, userStatus);
      
      //if list is empty, assign head and tail to user
      if(this.head == null && this.tail == null){
         this.head = user;
         this.tail = user;
      } else {
         this.tail.setNext(user);
         user.setPrev(this.tail);
         this.tail = user;
      }
   }   
   
   //prints the list starting from head and moving down
   //type will choose which kind of print will happen: 0 = User : 1 = User, Status : 2 = User, Password, Status
   public String printList(int type){
      String list = "";
      int userCount = 0;
      
      //if list is empty, return empty message
      if(this.head == null && this.tail == null){
         return "The User list is empty";
      }
      
      //if list only has one item, just return item
      if(this.head == this.tail){
         userCount++;
         return String.format("\n%d: %s%s%s", userCount, this.head.getUser(),
                             (type > 1) ? "\n   " + this.head.getPass() : "",
                             (type > 0) ? "\n   " + this.head.getUserStatus() : "");
      }
      
      //traverse list
      User current = head;
      
      while(current != null){
         userCount++;
         list += String.format("\n%d: %s%s%s", userCount, current.getUser(),
                             (type > 1) ? "\n   " + current.getPass() : "",
                             (type > 0) ? "\n   " + current.getUserStatus() : "");
         current = current.getNext();
      } 
      return list;
   }
   
   //check to see if username already exists
   public boolean checkUser(String test){
      
      //if list is empty, new Username is good
      if(this.head == null && this.tail == null){
         return false;
      }
      
      //traverse list checking all Usernames
      User current = this.head;
      
      while(current != null){
         //exit if it finds one that already exists
         if(test.equals(current.getUser())){
            return true;
         }
         current = current.getNext();
      } 
      return false;
   }
   
   //check to see if username already exists
   public User getUser(String test){
      
      //traverse list checking all Usernames
      User current = this.head;
      
      while(current != null){
         //exit if it finds one that already exists
         if(test.equals(current.getUser())){
            return current;
         }
         current = current.getNext();
      } 
      return null;
   }
   
   //delete user and repairing linked list
   public void deleteUser(User temp){
   
      //if user is only user
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
         File file = new File(".\\Game Lists\\" + temp.getUser() + ".txt");
         file.delete();
         if (file.delete()){
            System.out.println("File Deleted!");
         }
         /*
         try {
               // Attempt to delete the file
               if (file.delete()) {
                   System.out.println("File Deleted!");
               } else {
                   System.out.println("File Not Found.");
               }
             } catch (IOException e) {
                  System.out.println("An error occurred while deleting the file.");
                  e.printStackTrace();
             }  
          */                
   }   
    
   //export database to file for saving
   public void exportUsers(){
      String output = "";
      
      User current = this.head;
      
      //traverse list getting info of each user for export
      while(current != null){
         output += (current.getUser() + "\n" + current.getPass() + "\n" + current.getUserStatus() + "\n");
         current = current.getNext();
         removeHead();
      }
      
      Path path = Paths.get(".\\BOLD User List.txt");
      
      //write the list to a file
      try{
         Files.writeString(path, output, StandardCharsets.UTF_8);
      } catch (IOException ex){
         System.out.println("Invalid Path");
      }   
   }  
                
}