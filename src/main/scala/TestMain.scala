package ca.hyperreal.human_tester


object TestMain extends App
{
	// here's a question you wouldn't want to add: answers can easily be too large and if you add a size limiting constraint then all the questions have a
	// one in them
	Tester.question( "default", "default", "What is the product of a:tint, b:tint and c:tint?", "a < b < c, a*b*c < 50", "a*b*c" )
// 	Tester.template( "example", NewArithmeticTemplate )
// 	Tester.question( "example", "example", "Is a:sint a perfect square?", null, "if( sqrt(a)*sqrt(a) = a, 'yes', 'no' )" )
	
	for (_ <- 1 to 5)
// 		println( Tester.test("example") )
		println( Tester.test("default") )

//	println( Evaluator(Expression("1 < 2 < 3"), Map()) )
}

object NewArithmeticTemplate extends ArithmeticTemplate
{
	import math._

	types("ssquare") = (1 until 100) filter (n => pow(floor(sqrt(n)), 2 ) == n)
}
