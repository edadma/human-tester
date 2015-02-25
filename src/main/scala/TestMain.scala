package ca.hyperreal.human_tester


object TestMain extends App
{
	// here's a question you wouldn't want to add: answers can easily be too large and if you add a size limiting constraint then all the questions have a
	// one in them
 	Tester.question( "default", "arithmetic", "What is the product of a:tint, b:tint and c:tint?", "a < b < c, a*b*c < 50", "a*b*c" )
	
	for (_ <- 1 to 20)
		println( Tester.test("default") )

//	println( Evaluator(Expression("1 < 2 < 3"), Map()) )
}