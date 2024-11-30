/*Name: Group 3 - CSC 131-06 : Shivjyot Brar (302813914), Alan Her (302842878), Benjamin Church (302299231), Sheng-Chang Chen (303372394),
                               Mohammed Bader (303189770), Ikki Ee Herng Chua (303902677), Jonathan Herman (303050007), Ayush (303335591)
*Date: November 3, 2024
*Title: BOLD Game Library Prototype
*Description: Game Class
*/

class Game{
   private String title;
   private String des; //short for description
   private double price;
   private Game next;
   private Game prev;
   
   //default constructor
   public Game(){
      this.title = "";
      this.price = 0;      
      this.des = "";
      this.next = null;
      this.prev = null;
   }
   
   //custom constructor for title, description, price
   public Game(String newTitle, double newPrice, String newDes){
      this.title = newTitle;
      this.price = newPrice;
      this.des = newDes;      
      this.next = null;
      this.prev = null;
   }
   
   //mutators
   public void setTitle(String newTitle){
      this.title = newTitle;
   } 
   
   public void getDes(String newDes){
      this.des = newDes;
   }
   
   public void getPrice(double newPrice){
      this.price = newPrice;
   }
   
   public void setNext(Game nextGame){
      this.next = nextGame;
   }
   
   public void setPrev(Game prevGame){
      this.prev = prevGame;
   }     
   
   //accessors
   public String getTitle(){
      return this.title;
   } 
   
   public String getDes(){
      return this.des;
   }
   
   public double getPrice(){
      return this.price;
   } 
   
   public Game getNext(){
      return this.next;
   }
   
   public Game getPrev(){
      return this.prev;
   }  
}