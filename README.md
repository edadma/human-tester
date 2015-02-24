Human Tester
============

This is a simple framework for generating question/answer pairs to test if an *entity* is human or not.  The framework is still in it's infancy - currently it can only deal with simple numerical type questions.

Here is a code snippet to show how to use it.

	import ca.hyperreal.human_tester.Tester
	
	object Main extends App
	{
		Tester.add( "default", "arithmetic", "What is the product of $a:pdigit, $b:pdigit and $c:pdigit?", "a < b < c, a*b*c < 50", "a*b*c" )
		
		for (_ <- 1 to 15)
			println( Tester.test("default") )
	}
	
The above code snippet starts by adding a new type of test to the list of possible questions.  Actually, this is a question that you wouldn't really want to have (it's not one of the built-in questions) because the answers can easily be three digits.  The snippet then displays 15 question/answer pairs.  For example, you may get something like

	(Is 9 less than 6?,no)
	(Does 8 come before 9?,yes)
	(Does 1 come before 5?,no)
	(What is 1 times 4?,4)
	(What is 1 plus 4?,5)
	(What is the sum of 5, 6 and 9?,20)
	(Is 10 divisible by 2?,yes)
	(What is 9 times 1?,9)
	(What is 9 plus 5?,14)
	(What number comes after 6?,7)
	(What is 8 times 5?,40)
	(What is the sum of 2, 5 and 6?,13)
	(Is 12 divisible by 7?,no)
	(What is 3 plus 4?,7)
	(What is 6 times 8?,48)


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

