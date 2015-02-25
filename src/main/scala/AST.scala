package ca.hyperreal.human_tester


trait AST
case class StringAST( s: String ) extends AST
case class NumberAST( n: Int ) extends AST
case class VariableAST( v: String ) extends AST
case class OperationAST( f: String, args: AST* ) extends AST
case class ConjunctionAST( conjuncts: List[AST] ) extends AST
