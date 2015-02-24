package ca.hyperreal.human_tester


object TestMain extends App
{
	// here's a question you wouldn't want to add: answers can easily be too large and if you add a size limiting constraint then all the questions have a
	// one in them
 	Tester.add( "default", "arithmetic", "What is the product of $a:pdigit, $b:pdigit and $c:pdigit?", "a < b < c, a*b*c < 50", "a*b*c" )
	
	for (_ <- 1 to 20)
		println( Tester.test("default") )

// 	val eval = new Evaluator( Map[String, Any]() )
// 	
// 	println( eval("'asdf' = 'asdf'") )
}