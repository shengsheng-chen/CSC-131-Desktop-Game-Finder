/*Name: Group 3 - CSC 131-06 : Shivjyot Brar (302813914), Alan Her (302842878), Benjamin Church (302299231), Sheng-Chang Chen (303372394),
                               Mohammed Bader (303189770), Ikki Ee Herng Chua (303902677), Jonathan Herman (303050007), Ayush (303335591)
*Date: October 31, 2024
*Title: BOLD Game Library Prototype
*Description: User Menu Class
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class UserMenu{
   static ListUser allUsers = new ListUser();
   static Scanner input = new Scanner(System.in);   
   
   public static void menuUser(){
      importUsers();
      String control;
      System.out.println("Welcome to the User Menu! What would you like to do?");      
      do{
         System.out.println("\nCreate User (C), Sign In (S), Print Users(P), Exit (E)");
         control = input.nextLine();
            
            //input validation for one word only
            if(control.contains(" ")){ 
               System.out.println("Error: Please enter One Word/Character\n");
               control = "fail";
            } else {
               switch(control.toLowerCase()){              
                  case "c":
                        createUserMenu(); //create User
                        break;
                  case "s":
                        signInMenu(); //sign In
                        break;
                  case "p":
                        System.out.println(allUsers.printList(0)); //prints all usernames
                        break;
                  case "e":
                        break; //exit
                  default:
                        System.out.println("Invalid Input");
               }
            }
            
      } while (!control.equalsIgnoreCase("e"));
      allUsers.exportUsers();
   }
   
   //create user menu
   public static void createUserMenu(){
         String newUser, newPass;
         boolean userStatus;
         
         System.out.println("Let's Create a User!");
         
         //input validation for username to be one word
         do{
            System.out.print("Enter the Username: ");
            newUser = input.nextLine();
            if(newUser.contains(" ")){
               System.out.println("Error: Please enter One Word/Character\n");
            }
            
            if(newUser.equalsIgnoreCase("exit")){
               System.out.println("\nReserved Word: Exiting");
               return;
            }         
            
            if(allUsers.checkUser(newUser) == true){ //meaning username already exists
               System.out.println("\nError: Username Already Exists\n");
            }
            
         }while (newUser.contains (" ") || allUsers.checkUser(newUser) == true);
         
         //input validation for password to be one word
         do{
            System.out.print("Enter the Password: ");
            newPass = input.nextLine();
            
            if(newPass.contains(" ")){
               System.out.println("\nError: Please enter One Word/Character");
            }
            
            if(newPass.equalsIgnoreCase("exit")){
               System.out.println("\nReserved Word: Exiting");
               return;
            }
                     
         } while (newPass.contains (" "));
         
         //create user instance and add it to data base
         allUsers.addTail(newUser, newPass, 0);
         File file = new File(".\\Game Lists\\" + newUser + ".txt");
         try {
               // Attempt to create the file
               if (file.createNewFile()) {
                   System.out.println("File created: " + file.getPath());
               } else {
                   System.out.println("File already exists.");
               }
             } catch (IOException e) {
                  System.out.println("An error occurred while creating the file.");
                  e.printStackTrace();
             }         
      }
   
   //create user menu
   public static void signInMenu(){
      String newUser, newPass;
      User current = null;
      
      if(allUsers.isEmpty()){
         System.out.println("There are No Existing Users");
         return;
      }
      
      System.out.println("Please Sign In");
      
         //input validation for username to be one word
         do{
            System.out.print("Enter the Username: ");
            newUser = input.nextLine();
            if(newUser.contains(" ")){
               System.out.println("Error: Please enter One Word/Character\n");
            }
            
            if(newUser.equalsIgnoreCase("exit")){
               System.out.println("\nReserved Word: Exiting");
               return;
            }
            
            if(allUsers.checkUser(newUser) == true){ //meaning user was found
               current = allUsers.getUser(newUser);
            } else {
               System.out.println("Error: User Doesn't Exist\n");
            }
            
         }while (newUser.contains (" ") || allUsers.checkUser(newUser) == false);
         
         //input validation for password to be one word
         do{
            
            System.out.print("Enter the Password: ");
            newPass = input.nextLine();
            if(newPass.contains(" ")){
               System.out.println("Error: Please enter One Word/Character\n");
            }
            
            if(newPass.equalsIgnoreCase("exit")){
               System.out.println("\nReserved Word: Exiting");
               return;
            }
            
            if(!newPass.equals(current.getPass())){
               System.out.println("Error: Please enter the Correct Password\n");
            }
            
         } while (newPass.contains (" ") || !newPass.equals(current.getPass()));
         signedInMenu(current);
   }
   
   //signed in options
   public static void signedInMenu(User current){
      String control, newUser;
      User search = null;
      do{
         System.out.printf("\nSigned In As: %s %s", current.getUser(), ((current.getUserStatus() == 2) ? "Master" : ((current.getUserStatus() == 1) ? "Admin" : "User")));
         System.out.printf("\n%s%s%sBrowse (B), Exit (E)\n", ((current.getUserStatus() != 2) ? "Delete (D), " : ""),
                                                             ((current.getUserStatus() == 2) ? "Toggle Other Admins (T), " : ""),
                                                             ((current.getUserStatus() > 0) ? "Print Users (P), Delete Another User (A), " : ""));
         control = input.nextLine();
         
            //input validation for one word only
            if(control.contains(" ")){ 
               System.out.println("Error: Please enter One Word/Character\n");
               control = "fail";
            }
            
            if(control.equalsIgnoreCase("d") && current.getUserStatus() != 2){ //master account can't delete themselves
               allUsers.deleteUser(current);
               System.out.println("Success!"); 
               break;             
            }  
            
            if(control.equalsIgnoreCase("t") && current.getUserStatus() == 2){ //only master can set other admins
               //only execute if there is another user other than admin/master
               if(allUsers.getHead() == allUsers.getTail()){
                  System.out.println("Error: No Other Users Exits");
               } else {               
                  search = findOtherUser();
                  if(search != null){
                     if(search.getUserStatus() == 0){
                        search.setUserStatus(1);
                        System.out.println(search.getUser() + " is now an Admin!");
                     } else{
                        search.setUserStatus(0);
                        System.out.println(search.getUser() + " is now a User");
                     }
                  }
                  
                  //create buckets for bucket sort
                  ListUser statusUsers[] = {new ListUser(), new ListUser(), new ListUser()};
                  
                  //remove head until main list is empty and buckets are full
                  while(allUsers.isEmpty() == false){
                     statusUsers[allUsers.getHead().getUserStatus()].addTail(allUsers.removeHead());
                  }
                  
                  //empty buckets back into main user list
                  for(int i = statusUsers.length - 1; i >= 0; i--){
                     while(statusUsers[i].isEmpty() == false){
                        allUsers.addTail(statusUsers[i].removeHead());
                     }
                  }
                  
               }
            }
            
            if(control.equalsIgnoreCase("p") && current.getUserStatus() > 0){ //only admin/master account can see all users
               if(current.getUserStatus() == 2){ //master can see all usernames, passwords, and userStatus
                  System.out.println(allUsers.printList(2));
               } else {
                  System.out.println(allUsers.printList(1)); //for admins to only see usernames and userStatus
               }            
            }
                        
            if(control.equalsIgnoreCase("a") && current.getUserStatus() > 0){ //signed in user is admin/master
               //only execute if there is another user other than admin/master
               if(allUsers.getHead() == allUsers.getTail()){
                  System.out.println("Error: No Other Users Exits");
               } else {               
                  search = findOtherUser();
                  if(search != null){
                     if(current.getUserStatus() <= search.getUserStatus()){
                        System.out.println("Error: Don't Have Permission to Delete " + search.getUser() + " Account");
                     } else {
                        allUsers.deleteUser(search);
                        System.out.println("Success!"); 
                     }
                  }
               }
                           
            }
            
            if(control.equalsIgnoreCase("b")){ //signed in user can go browse
               BrowseMenu.menuBrowse(current); //browse games as user
            }
             
      } while (!control.equalsIgnoreCase("e"));
   }
      
   //import database to code for editing/operations
   public static void importUsers(){
      String newUser, newPass;
      int newUserStatus;
      try {
         File file = new File(".\\BOLD User List.txt");
         Scanner inputDB = new Scanner(file);
         
         while(inputDB.hasNextLine()){
               newUser = inputDB.nextLine();
               newPass = inputDB.nextLine();
               newUserStatus = Integer.parseInt(inputDB.nextLine());
               allUsers.addTail(newUser, newPass, newUserStatus);
            }
      } catch (FileNotFoundException error){
         System.out.println("Could not find file");
      }
   }
   
   //specifically used for admin/master functions of managing other users
   public static User findOtherUser(){
      String newUser;
      User search = null;      
      
      //find username that admin/master account wants
      do{
         System.out.print("Enter the Username: ");
         newUser = input.nextLine();
         
         //reserved exit
         if(newUser.equalsIgnoreCase("exit")){
            System.out.println("\nReserved Word: Exiting");
            return null;
         }
         
         //input validation for one word and if user exits
         if(newUser.contains(" ")){
            System.out.println("Error: Please enter One Word/Character\n");
         }  else 
            if(allUsers.checkUser(newUser) == true){ //meaning user was found
                  search = allUsers.getUser(newUser);
            } else {
               System.out.println("Error: User Doesn't Exist\n");
               }
                                    
      }while (newUser.contains (" ") || allUsers.checkUser(newUser) == false);
      return search;
   }      
}