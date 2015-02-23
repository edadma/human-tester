package ca.hyperreal.human_tester

import collection.mutable.{ArrayBuffer, HashMap}
import util.Random._


object Tester
{
	private val spaces = new HashMap[String, ArrayBuffer[(Template, String, String)]]
	private val templates = Map( "arithmetic" -> ArithmeticTemplate )
	
	spaces("default") = ArrayBuffer(
		(ArithmeticTemplate, "What is $a:pdigit added to $b:pdigit?", "a + b"),
		(ArithmeticTemplate, "What is the sum of $a:pdigit, $b:pdigit and $c:pdigit?", "a + b + c"),
		(ArithmeticTemplate, "What is $a:pdigit times $b:pdigit?", "a*b"),
		(ArithmeticTemplate, "What number comes before $a:pdigit?", "a - 1"),
		(ArithmeticTemplate, "What number comes after $a:pdigit?", "a + 1")
		)
	
	def test( space: String ) =
	{
		require( spaces contains space, s"there is no question/answer space '$space'" )
		
	val (t, q, a) = spaces(space)( nextInt(spaces(space).length) )
	
		t.generate( q, a )
	}
	
	def add( space: String, template: String, question: String, answer: String )
	{
		require( templates contains template, "unknown template '$template'" )
		
	val buf =
		if (spaces contains space)
			spaces(space)
		else
			new ArrayBuffer[(Template, String, String)]
			
		buf.append( (templates(template), question, answer) )
	}
}