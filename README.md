Human Tester
============

This is a simple framework for generating question/answer pairs to test if an *entity* is human or not.  The framework is still in it's infancy - currently it can only deal with simple numerical type questions.

Here is a code snippet to show how to use it.

	import ca.hyperreal.human_tester.Tester
	
	object Main extends App
	{
		Tester.add( "default", "arithmetic", "What is the product of $a:pdigit, $b:pdigit and $c:pdigit?", "a < b < c, a*b*c < 50", "a*b*c" )
		
		for (_ <- 1 to 20)
			println( Tester.test("default") )
	}
	
The above code snippet starts by adding a new type of test to the list of possible questions.  Actually, this is a question that you wouldn't really want to have (it's not one of the built-in questions) because the answers can easily be three digits.  The snippet then displays 20 question/answer pairs.  For example, you may get something like

	(What is 3 times 9?,27)                                                                                                                                                                                                      
	(What is the product of 1, 4 and 5?,20)                                                                                                                                                                                      
	(Does 8 come after 7?,yes)                                                                                                                                                                                                   
	(What number comes after 4?,5)                                                                                                                                                                                               
	(What is the sum of 1, 5 and 6?,12)                                                                                                                                                                                          
	(What is 14 divided by 7?,2)                                                                                                                                                                                                 
	(What is 4 plus 3?,7)                                                                                                                                                                                                        
	(What number comes after 3?,4)                                                                                                                                                                                               
	(Is 7 less than 5?,no)                                                                                                                                                                                                       
	(What is the product of 1, 2 and 7?,14)                                                                                                                                                                                      
	(What is the sum of 1, 2 and 6?,9)                                                                                                                                                                                           
	(Is 12 divisible by 7?,no)                                                                                                                                                                                                   
	(Is 15 divisible by 5?,yes)                                                                                                                                                                                                  
	(What is the product of 1, 2 and 4?,8)
	(What is the sum of 3, 7 and 8?,18)
	(What is 3 times 8?,24)
	(What is 15 divided by 5?,3)
	(Is 12 divisible by 2?,yes)
	(What is 4 times 5?,20)
	(What number comes before 9?,8)


## License

Human Tester is distributed under the MIT License, meaning that you are free to use it in your free or proprietary software.


## Usage

Use the following elements to use Human Tester in your Maven project:

	<repository>
		<id>hyperreal</id>
		<url>http://hyperreal.ca/maven2</url>
	</repository>

	<dependency>
		<groupId>ca.hyperreal</groupId>
		<artifactId>human-tester</artifactId>
		<version>0.2</version>
	</dependency>

Add the following to your `build.sbt` file to use Human Tester in your SBT project:

	resolvers += "Hyperreal Repository" at "http://hyperreal.ca/maven2"

	libraryDependencies += "ca.hyperreal" %% "human-tester" % "0.2"
