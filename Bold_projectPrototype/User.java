/*Name: Group 3 - CSC 131-06 : Shivjyot Brar (302813914), Alan Her (302842878), Benjamin Church (302299231), Sheng-Chang Chen (303372394),
                               Mohammed Bader (303189770), Ikki Ee Herng Chua (303902677), Jonathan Herman (303050007), Ayush (303335591)
*Date: October 30, 2024
*Title: BOLD Game Library Prototype
*Description: User Class
*/

class User{
   private String username;
   private String password;
   private int userStatus;
   private User next;
   private User prev;
   
   //default constructor
   public User(){
      this.username = null;
      this.password = null;
      this.userStatus = 0;
      this.next = null;
      this.prev = null;
   }
   
   //custom constructor for user, pass
   public User(String user, String pass){
      this.username = user;
      this.password = pass;
      this.userStatus = 0;
      this.next = null;
      this.prev = null;
   }
   
   //custom constructor for user, pass, userStatus
   public User(String user, String pass, int status){
      this.username = user;
      this.password = pass;
      this.userStatus = status;
      this.next = null;
      this.prev = null;
   }
   
   //mutators
   public void setUserStatus(int status){
      this.userStatus = status;
   }
   
   public void setNext(User nextUser){
      this.next = nextUser;
   }
   
   public void setPrev(User prevUser){
      this.prev = prevUser;
   }

   
   //accessors
   public String getUser(){
      return this.username;
   }
   
   public String getPass(){
      return this.password;
   }
   
   public int getUserStatus(){
      return this.userStatus;
   }
   
   public User getNext(){
      return this.next;
   }
   
   public User getPrev(){
      return this.prev;
   }
}