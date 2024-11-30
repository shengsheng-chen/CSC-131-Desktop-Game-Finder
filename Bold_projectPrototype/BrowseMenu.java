/*Name: Group 3 - CSC 131-06 : Shivjyot Brar (302813914), Alan Her (302842878), Benjamin Church (302299231), Sheng-Chang Chen (303372394),
                               Mohammed Bader (303189770), Ikki Ee Herng Chua (303902677), Jonathan Herman (303050007), Ayush (303335591)
*Date: October 31, 2024
*Title: BOLD Game Library Prototype
*Description: User Menu Class
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class BrowseMenu{
   static ListGame allGames = new ListGame(); 
   static ListGame searchGames = new ListGame();
   static ListGame userGames = new ListGame();
   static Scanner input = new Scanner(System.in);   
   static User current = null;
   
   public static void menuBrowse(User temp){
      importGames("Full Game Catalog", 0);
      if(temp != null){
         importGames(temp.getUser(), 1);
      }
      String control;
      
      current = temp;
      System.out.printf("\nWelcome to the Browse Menu! What would you like to do? %s\n", 
                        (current != null) ? "\nSigned In As: " + current.getUser() + 
                        ((current.getUserStatus() == 0) ? " User" : "") + 
                        ((current.getUserStatus() == 1) ? " Admin" : "") +
                        ((current.getUserStatus() == 2) ? " Master" : "")
                        : "Guest");
      do{
         System.out.printf("\nView Full Catalog (F), Search (S), %s%s%sExit (E)\n", 
                           (current != null) ? "My Games (G), " : "",                           
                           (current != null && current.getUserStatus() > 0) ? "Add Game (A), " : "",
                           (current != null && current.getUserStatus() > 1) ? "Delete Game (D), " : "");
         control = input.nextLine();
            
            //input validation for one word only
            if(control.contains(" ")){ 
               System.out.println("Error: Please enter One Word/Character\n");
               control = "fail";
            } else {
               
               if(control.equalsIgnoreCase("f")){
                  System.out.println(allGames.printList(0)); //print just game names
                  //System.out.println("Head: " + allGames.getHead().getTitle() + "\n" + "Tail: " + allGames.getTail().getTitle());                    
               }
               
               if(control.equalsIgnoreCase("s")){ //retrieve list accoring to input
                  searchGames();
               }
               
               if(control.equalsIgnoreCase("g") && current != null){ //print User's Games
                  myGames();
               }               
               
               if(control.equalsIgnoreCase("a") && current != null){ //have to be admin or master to add game
                  if(current.getUserStatus() > 0){
                     addGame();                  
                  }
               }
               
               if(control.equalsIgnoreCase("d") && current != null){ //have to be master to delete game
                  if(current.getUserStatus() > 1){
                     deleteGame();                
                  }
               }
            }
      } while (!control.equalsIgnoreCase("e"));
      
      allGames.exportGames("Full Game Catalog");
      if(temp != null){
         userGames.exportGames(temp.getUser());
      }      
   }
   
   //method to add game, only admins and master can do this
   public static void addGame(){
      String newTitle, newPrice, newDes;
      boolean isDouble = false;
      
      //get title to add while checking if it already exists
      do{
         System.out.print("Enter the Title: ");
         newTitle = input.nextLine();
         
         //reserved exit
         if(newTitle.equalsIgnoreCase("exit")){
            System.out.println("\nReserved Word: Exiting");
            return;
         }
         
         if(newTitle.charAt(newTitle.length() - 1) == ' '){
            System.out.println("\nError: Ends With a \"space\"");
         } else if(allGames.checkGame(newTitle) == true){ //meaning game already exists
            System.out.println("\nError: Game Title Already Exists");
         }  
                                            
      }while (allGames.checkGame(newTitle) == true || newTitle.charAt(newTitle.length() - 1) == ' ');
      
      //get price to add
      do{
         System.out.print("Enter the Price: ");
         newPrice = input.nextLine();
         
         //reserved exit
         if(newPrice.equalsIgnoreCase("exit")){
            System.out.println("\nReserved Word: Exiting");
            return;
         }
         
         if(newPrice.contains(" ")){
            System.out.println("Error: Please enter One Word/Character\n");
         } else {
               try{
                  Double.parseDouble(newPrice);
                  isDouble = true;
               
               }catch(NumberFormatException e){
                  System.out.println("Error: Not a Valid Number\n");
                  isDouble = false;
               }
         }
                                            
      }while (newPrice.contains(" ") || isDouble == false);
      
      //get description to add
      do{
         System.out.print("Enter the Description: ");
         newDes = input.nextLine();
         
         //reserved exit
         if(newDes.equalsIgnoreCase("exit")){
            System.out.println("\nReserved Word: Exiting");
            return;
         }
         
         if(newDes.charAt(newDes.length() - 1) == ' '){
            System.out.println("\nError: Ends With a \"space\"");
         } 
                                            
      }while (newDes.charAt(newTitle.length() - 1) == ' ');
      
      double price = Double.parseDouble(newPrice);
      allGames.addTail(newTitle, Math.round(price * 100.0) / 100.0, newDes);
      sort(allGames);
   }
   
   //method to delete game, only master can do this
   public static void deleteGame(){
      String deleteTitle;
      do{
         System.out.print("Enter the Title to Delete: ");
         deleteTitle = input.nextLine();
         
         //reserved exit
         if(deleteTitle.equalsIgnoreCase("exit")){
            System.out.println("\nReserved Word: Exiting");
            return;
         }
         
         if(deleteTitle.charAt(deleteTitle.length() - 1) == ' '){
            System.out.println("\nError: Ends With a \"space\"");
         } else if(allGames.checkGame(deleteTitle) == true){ //meaning game already exists
            System.out.println("\n" + deleteTitle + " Successfully Deleted!");
            allGames.deleteGame(allGames.getGame(deleteTitle));
            return;
         } else {
            System.out.println("\nError: " + deleteTitle + " Doesn't Exist");
         }
      }while(deleteTitle.charAt(deleteTitle.length() - 1) == ' ' || !deleteTitle.equalsIgnoreCase("exit"));
   }

   //search through catalog retrieving list of games
   public static void searchGames(){
      String newTitle, select;
      Game search = allGames.getHead();
      
      System.out.print("Enter the Title: ");
      newTitle = input.nextLine();
      
      //reserved exit
      if(newTitle.equalsIgnoreCase("exit")){
         System.out.println("\nReserved Word: Exiting");
         return;
      }
      
      //travese catalog adding any matches
      while(search != null){
         if(search.getTitle().toLowerCase().contains(newTitle.toLowerCase()) == true){
            searchGames.addTail(search.getTitle(), search.getPrice(), search.getDes());
         }
         search = search.getNext();
      }
      
      do{
         selectSearch(0);
         
         if(searchGames.isEmpty()){
            break;
         }
         
         System.out.println("Select Another (S), Exit(E)");
         select = input.nextLine();

         //reserved exit
         if(select.equalsIgnoreCase("exit")){
            System.out.println("\nReserved Word: Exiting");
            break;
         }
         
         if(select.contains(" ")){
            System.out.println("Error: Please enter One Word/Character");
         }
         
         if(!select.equalsIgnoreCase("s") && !select.equalsIgnoreCase("e")){
            System.out.println("Error: Invalid Input");
         }        
        } while (!select.equalsIgnoreCase("e"));
            
      //travese to remove everything
      search = searchGames.getHead();
      while(search != null){
         search = search.getNext();
         searchGames.removeHead();
      } 
      
   }
   
   public static void selectSearch(int type){
      String select;
      boolean isInt = false;
      int selectInt = 0;       
      
      //print search results unless there is none
      if(((type == 0) ? searchGames : userGames).getHead() == null && ((type == 0) ? searchGames : userGames).getTail() == null){
         System.out.println("No Results Found");
         return;
      } else {
         System.out.println(((type == 0) ? searchGames : userGames).printList(3));
      }
            
      //allow user to select a game for more options/info
      do{
         System.out.println("\nSelect Game Number");
         select = input.nextLine();
         
         //reserved exit
         if(select.equalsIgnoreCase("exit")){
            System.out.println("\nReserved Word: Exiting");
            return;
         }        
                  
         if(select.contains(" ")){
            System.out.println("Error: Please Enter A Valid Number");
            isInt = false;
         } else {
            try{
               selectInt = Integer.parseInt(select);
               isInt = true;
               if(selectInt < 1 || selectInt > ((type == 0) ? searchGames : userGames).countGames()){
                  System.out.println("Error: Please Enter A Valid Number");
                  isInt = false;
               }
            
            } catch (NumberFormatException e) {
               System.out.println("Error: Not a Number");
               isInt = false;
            }
         }
         
      } while(isInt == false);
      
      //travese to find game
      Game search = ((type == 0) ? searchGames : userGames).getHead();
      for(int i = 1; i < selectInt; i++){
         search = search.getNext();
      }
      
      ((type == 0) ? searchGames : userGames).printGame(search);
      
      if(current != null && userGames.checkGame(search.getTitle()) == false){
         do{
               System.out.println("Do You Want to Add This to Your Library?\nYes (Y), No (N)");
               select = input.nextLine();
               
               //reserved exit
               if(select.equalsIgnoreCase("exit")){
                  System.out.println("\nReserved Word: Exiting");
                  break;
               } 
                          
               if(select.contains(" ")){
                  System.out.println("Error: Please enter One Word/Character");
               } else {
                  if(select.equalsIgnoreCase("y")){
                     userGames.addTail(search.getTitle(), search.getPrice(), search.getDes());
                     break;
                  }            
               }
            } while (select.contains(" ") || !select.equalsIgnoreCase("n"));
      } else if(current != null && userGames.checkGame(search.getTitle()) == true){
         if(type == 0){
         System.out.println("This Game is in Your Library");
         } else {
               do {
                  System.out.println("Do You Want to Delete This Game From Your Library?\nYes (Y), No (N)");
                  select = input.nextLine();
                  
                  //reserved exit
                  if(select.equalsIgnoreCase("exit")){
                     System.out.println("\nReserved Word: Exiting");
                     break;
                  } 
                             
                  if(select.contains(" ")){
                     System.out.println("Error: Please enter One Word/Character");
                  } else {
                     if(select.equalsIgnoreCase("y")){
                        userGames.deleteGame(search);
                        break;
                     }            
                  }
               } while (select.contains(" ") || !select.equalsIgnoreCase("n"));
         }
      }   
   }
   
   public static void myGames(){
      String select;
      do{
         selectSearch(1);
         
         if(userGames.isEmpty()){
            break;
         }
         
         System.out.println("Select Another (S), Exit(E)");
         select = input.nextLine();

         //reserved exit
         if(select.equalsIgnoreCase("exit")){
            System.out.println("\nReserved Word: Exiting");
            break;
         }
         
         if(select.contains(" ")){
            System.out.println("Error: Please enter One Word/Character");
         }
         
         if(!select.equalsIgnoreCase("s") && !select.equalsIgnoreCase("e")){
            System.out.println("Error: Invalid Input");
         }        
        } while (!select.equalsIgnoreCase("e"));     
      }
     
   //import all games to code for editing/operations
   //types: 0: All Games : 1: User's Games
   public static void importGames(String name, int type){
      String newTitle, newDes;
      double newPrice;
      try {
         File file = new File(".\\Game Lists\\" + name + ".txt");
         
         Scanner inputGL = new Scanner(file);
         
         while(inputGL.hasNextLine()){
               newTitle = inputGL.nextLine();
               newPrice = Double.parseDouble(inputGL.nextLine());               
               newDes = inputGL.nextLine();
               if(type == 0){
                  allGames.addTail(newTitle, newPrice, newDes);
               } else {
                  if(allGames.checkGame(newTitle)){
                     userGames.addTail(newTitle, newPrice, newDes);
                  }
               }
            }
      } catch (FileNotFoundException error){
         System.out.println("Could not find file");
      }
      if(type == 0){
         sort(allGames);
      } else {
         sort(userGames);
      }
   }

    public static void sort(ListGame allGames) {
        if (allGames.isEmpty()) {
            return; // No need to sort if the list is empty
        }
        allGames.setHead(mergeSort(allGames.getHead()));
        Game newTail = allGames.getHead();
           
        while(newTail.getNext() != null){
            newTail = newTail.getNext();
        }
           
        allGames.setTail(newTail);

    }

    private static Game mergeSort(Game head) {
        if (head == null || head.getNext() == null) {
            return head;
        }

        Game middle = getMiddle(head);
        Game nextOfMiddle = middle.getNext();
        middle.setNext(null); // Split the list

        Game left = mergeSort(head);
        Game right = mergeSort(nextOfMiddle);

        return sortedMerge(left, right);
    }

    private static Game getMiddle(Game head) {
        if (head == null) {
            return head;
        }

        Game slow = head;
        Game fast = head.getNext();

        while (fast != null) {
            fast = fast.getNext();
            if (fast != null) {
                slow = slow.getNext();
                fast = fast.getNext();
            }
        }
        return slow; // The middle node
    }

    private static Game sortedMerge(Game left, Game right) {
        Game result;

        if (left == null) return right;
        if (right == null) return left;

        if (left.getTitle().compareTo(right.getTitle()) <= 0) {
            result = left;
            result.setNext(sortedMerge(left.getNext(), right));
            result.getNext().setPrev(result); // Set previous node
            result.setPrev(null); // Set prev of the head
        } else {
            result = right;
            result.setNext(sortedMerge(left, right.getNext()));
            result.getNext().setPrev(result); // Set previous node
            result.setPrev(null); // Set prev of the head
        }

        return result;
    }
}