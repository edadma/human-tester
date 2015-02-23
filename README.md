Human Tester
============

This is a simple framework for generating question/answer pairs to test if an *entity* is human or not.  The framework is still in it's infancy - currently it can only deal with simple arithmetic questions.

Here is a code snippet to show how to use it.

	import ca.hyperreal.human_tester.Tester
	
	object Main extends App
	{
		Tester.add( "default", "arithmetic", "What is the product of $a:pdigit, $b:pdigit and $c:pdigit?", "a*b*c" )
		
		for (_ <- 1 to 10)
			println( Tester.test("default") )
	}
	
The above code snippet starts by adding a new type of test to the list of possible questions.  Actually, this is a question that you wouldn't really want to have (it's not one of the built-in questions) because the answers can easily be three digits.  The snippet then displays 10 question/answer pairs.  For example, you may get something like

	(What number comes after 8?,9)
	(What is 7 times 3?,21)
	(What number comes before 8?,7)
	(What is the product of 6, 5 and 1?,30)
	(What number comes after 3?,4)
	(What number comes before 7?,6)
	(What is 3 times 5?,15)
	(What is the sum of 5, 6 and 1?,12)
	(What is the product of 3, 5 and 2?,30)
	(What is 5 added to 1?,6)


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
		<version>0.1</version>
	</dependency>

Add the following to your `build.sbt` file to use Human Tester in your SBT project:

	resolvers += "Hyperreal Repository" at "http://hyperreal.ca/maven2"

	libraryDependencies += "ca.hyperreal" %% "human-tester" % "0.1"

