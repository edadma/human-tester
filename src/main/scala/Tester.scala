package ca.hyperreal.human_tester

import collection.mutable.{ArrayBuffer, HashMap}
import util.Random._


object Tester
{
	private val spaces = new HashMap[String, ArrayBuffer[(Template, String, String, String)]]
	private val templates = Map( "arithmetic" -> ArithmeticTemplate )
	
	spaces("default") = ArrayBuffer(
		(ArithmeticTemplate, "What is $a:pdigit plus $b:pdigit?", "a != b", "a + b"),
		(ArithmeticTemplate, "What is the sum of $a:pdigit, $b:pdigit and $c:pdigit?", "a < b < c", "a + b + c"),
		(ArithmeticTemplate, "What is $a:pdigit times $b:pdigit?", "a != b", "a*b"),
		(ArithmeticTemplate, "What number comes before $a:pdigit?", null, "a - 1"),
		(ArithmeticTemplate, "What number comes after $a:pdigit?", null, "a + 1"),
		(ArithmeticTemplate, "Does $a:pdigit come after $b:pdigit?", "a > b", "if( a = b + 1, 'yes', 'no' )"),
		(ArithmeticTemplate, "Does $a:pdigit come before $b:pdigit?", "a < b", "if( a = b - 1, 'yes', 'no' )"),
		(ArithmeticTemplate, "Is $a:pdigit less than $b:pdigit?", "a != b", "if( a < b, 'yes', 'no' )"),
		(ArithmeticTemplate, "Is $a:pdigit greater than $b:pdigit?", "a != b", "if( a > b, 'yes', 'no' )"),
		(ArithmeticTemplate, "Is $a:scomp divisible by $b:sprime?", "a > b", "if( a mod b = 0, 'yes', 'no' )")
		)
	
	def test( space: String ) =
	{
		require( spaces contains space, s"there is no question/answer space '$space'" )
		
	val (t, q, c, a) = spaces(space)( nextInt(spaces(space).length) )
	
		t.generate( q, c, a )
	}
	
	def add( space: String, template: String, question: String, constraints: String, answer: String )
	{
		require( templates contains template, "unknown template '$template'" )
		
	val buf =
		if (spaces contains space)
			spaces(space)
		else
		{
		val b = new ArrayBuffer[(Template, String, String, String)]
		
			spaces(space) = b
			b
		}
		
		buf.append( (templates(template), question, constraints, answer) )
	}
}