package ca.hyperreal.human_tester


object TestMain extends App
{
	// here's a question you wouldn't want to add: answers can easily be three digits
	Tester.add( "default", "arithmetic", "What is the product of $a:pdigit, $b:pdigit and $c:pdigit?", "a*b*c" )
	
	for (_ <- 1 to 10)
		println( Tester.test("default") )
}