/* Author:Scott Sun

  Singleton in a nut shell 
 		while the program is running, there will be one and only one class object. 
 		Private constructor that restrict instantiation of the class from other classes.
 
	Understanding Singleton step by step
	Requirement:
		while the program is running, there will be only one class object in the whole program. 
 
 	Solution step 1:
 		when we use the program, we only call the constructor one time (new the class only once)
 		
 	Follow up question:
 		It is not a feasible solution. Users of the program does not know that they are limited to 
 		new the class one time.
 		
 	Solution step 2:
 		Use private property to limit the constructor
 			private ClassName(){}
 			
 	Follow up question:
 		When the constructor is private, how can we instantiate class object outside of the 
 		current class?
 		It also leads to the following problems：
 		class object cannot be instantiate outside of the class
 		class method cannot be used without class object
 		we can only use static method if there is any
 		
 	Solution step 3:
 		We can try use static property to get or create class object
 		
 		This method must be accessible to the outside class - [public]
 		This method can be called by Class name - [static]
 			in which, we do not need a class object to call the method
 		This method must return class Object as the return type - return [Class]
 		Method Name: [getInstance()]
 		parameter list must be the same as the constructor parameter list
 	
 	Follow up question:
 		The above method is feasible，however, it does not stop user from creating several class objects
 		
 	Solution step 4:
 		We can validate if there is a class object instantiated already
 		if no - instantiate the class object through public static method
 		if yes - method stop
 		
 		How to achieve the above method:
 		we need an instance variable to store the class object's address when it is instantiated
 		
 		Possible solution:
 			1. local variable
 			 		No - local variable only exist within the body of the method, not outside
 			2. non-static member variable
 			 		static method cannot use non-static member variable
 			3. static member variable  
 			
 		we can use static member variable to store class object once it is instantiated 
 	
 	Follow up question:
 		user can change the static member variable using class name
 	
 	Solution step 5:
 		private property to limit outside access
 */


class SingletonDemonstration{
	private int num;
	
	//private static member variable that is used to store class object once instantiate
	private static SingletonDemonstration s = null;
	
	private SingletonDemonstration(int num) {
		this.num = num;
	}
	
	public static SingletonDemonstration getInstance(int num) {
		
		//if class object does not exist, instantiate and store in member variable s
		if( s == null) {
			s = new SingletonDemonstration(num);
		}
		
		//return type - same as class object SingletonDemo
		return s;
	}

	public int getNum() {
		return num;
	}
	
}

public class SingletonConstructor {
	public static void main(String[] args) {
		
		/*
		 * SingletonDemo s1 = new SingletonDemo(60);
		 * 
		 * we cannot instantiate class object this way since the constructor is private
		 * we must use getInstance method
		 */
		
		SingletonDemonstration s1 = SingletonDemonstration.getInstance(60);
		System.out.println(s1 + "\t" + s1.getNum());
		//Singleton.SingletonDemonstration@96532d6	60
		
		SingletonDemonstration s2 = SingletonDemonstration.getInstance(60);
		System.out.println(s2 + "\t" + s2.getNum()); 
		//Singleton.SingletonDemonstration@96532d6	60
		//class object s2 is not instantiated 
		
		SingletonDemonstration s3 = SingletonDemonstration.getInstance(11);
		System.out.println(s3 + "\t" + s3.getNum());
		//Singleton.SingletonDemonstration@96532d6	60
		//s3 is not instantiated. parameter 11 is not passed to the class object
		
	}

}
