/*Name: Group 3 - CSC 131-06 : Shivjyot Brar (302813914), Alan Her (302842878), Benjamin Church (302299231), Sheng-Chang Chen (303372394),
                               Mohammed Bader (303189770), Ikki Ee Herng Chua (303902677), Jonathan Herman (303050007), Ayush (303335591)
*Date: October 30, 2024
*Title: BOLD Game Library Prototype
*Description: Home Class
*/

import java.util.Scanner;

class BoldHome{
   public static void main(String[] args){
      Scanner input = new Scanner(System.in);
      String controlMain;
      System.out.println("Welcome to the BOLD Game Library! What would you like to do?");
      do{
         System.out.println("\nBrowse Library as Guest (B), User Menu(U), Exit (E)");
         controlMain = input.nextLine();
         
            //input validation for one word only
            if(controlMain.contains(" ")){ 
               System.out.println("Error: Please enter One Word/Character\n");
               controlMain = "fail";
            } else {
               switch(controlMain.toLowerCase()){              
                  case "b":
                        BrowseMenu.menuBrowse(null); //browse games as guest
                        break;
                  case "u":
                        UserMenu.menuUser(); //go to user menu to sign in/create account
                        break;
                  case "e":
                        break; //exit
                  default:
                        System.out.println("Invalid Input");
               }
            }
      } while (!controlMain.equalsIgnoreCase("e"));
   }
}