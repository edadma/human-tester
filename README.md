Human Tester
============

This is a simple framework for generating question/answer pairs to test if an *entity* is human or not.  The framework is still in it's infancy - currently it can only deal with simple numerical type questions.

Here is a code snippet to show how to use it.

	import ca.hyperreal.human_tester.Tester
	
	object Main extends App
	{
		Tester.question( "default", "default", "What is the product of a:tint, b:tint and c:tint?", "a < b < c, a*b*c < 50", "a*b*c" )
		
		for (_ <- 1 to 20)
			println( Tester.test("default") )
	}
	
The above code snippet starts by adding a new type of test to the list of possible questions.  This is just to show that new questions can be added.  You probably won't need to do that.  Actually, this is a question that you wouldn't really want to have (it's not one of the built-in questions) because the answers can easily be too large without the size limiting constraint `a*b*c < 50`, but with the constraint most of the questions will have a one as the first number, as can be seen in the sample output below.  The snippet then displays 20 question/answer pairs.  For example, you may get something like

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


## Question/Answer Templates

The framework uses string templating to make it convenient to have it be able to generate a large number of appropriate question/answer pairs without having to individually add every particular question/answer pair.  The framework comes with one built-in test question template class, `ArithmeticTemplate`.  We will use the above example code and output to discuss question/answer templates.  A question that the tester can ask has three components: the question itself, a list of constraints (boolean expressions), and the answer to the question.

In the example above, a question is added where the question itself is `What is the product of a:tint, b:tint and c:tint?`, the constraints to be satisfied are `a < b < c, a*b*c < 50`, and the answer is `a*b*c`.  In the question, there are "slots" for randomly generated variables.  Each slot is a pair of the form `<name>:<set>` where `name` is the name of the variable and `set` is the name of a set of possible choices for that slot.  In this example, `tint` means "tiny integer" which is an integer from 1 to 9, inclusive.  Next, we have the constraints which say that the three variables should be in ascending order and their product should be less than 50.  And finally, the answer is the product of the three variables.

The sets for question variables that are included are:

- `tint` (tiny integer) integers from 1 to 9, inclusive
- `tprime` (tiny prime) prime numbers from 2 to 9, inclusive
- `tcomp` (tiny composite) composite numbers from from 4 to 15, inclusive
- `sint` (small integer) integers from 1 to 30, inclusive
- `sprime` (small prime) prime numbers from 2 to 29, inclusive
- `scomp` (small composite) composite numbers from 4 to 40, inclusive


## Adding Template Classes

A new template class can be added to `Tester` using the `template` method.  Let's say you wanted to have a template class based on `ArithmeticTemplate` that had a new number set called `ssquare`, the set of small square numbers up to 100.  You could use the following:

	object ExampleArithmeticTemplate extends ArithmeticTemplate
	{
		import math._

		types("ssquare") = (1 to 100) filter (n => pow(floor(sqrt(n)), 2 ) == n)
	}

The following snippet shows how to add the above template object to `Tester` along with a question to demonstrate the `ssquare` set:

	Tester.template( "example", ExampleArithmeticTemplate )
	Tester.question( "example", "example", "What is the square root of a:ssquare?", null, "isqrt( a )" )
	
	for (_ <- 1 to 5)
		println( Tester.test("example") )

Of course, the above question probably isn't one you would use to test if someone/something is a human because not everyone will know what "square root" means.  A better question might be

	Tester.question( "example", "example", "Is a:sint a perfect square number?", null, "if( isqrt(a)*isqrt(a) = a, 'yes', 'no' )" )


## License

Human Tester is distributed under the MIT License, meaning that you are free to use it in your free or proprietary software.


## Usage

Use the following elements to use Human Tester in your Maven project:

	<repository>
		<id>hyperreal</id>
		<url>https://dl.bintray.com/edadma/maven</url>
	</repository>

	<dependency>
		<groupId>ca.hyperreal</groupId>
		<artifactId>human-tester</artifactId>
		<version>0.5</version>
	</dependency>

Add the following to your `build.sbt` file to use Human Tester in your SBT project:

	resolvers += "Hyperreal Repository" at "https://dl.bintray.com/edadma/maven"

	libraryDependencies += "ca.hyperreal" %% "human-tester" % "0.5"

