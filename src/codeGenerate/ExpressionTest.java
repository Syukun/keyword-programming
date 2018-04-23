package codeGenerate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

public class ExpressionTest {
	// // array.add(src.readLine)
	public static MethodInv methodInv1 = new MethodInv(new Var("array", new Type("String")),
			new MName("add", new Type("boolean"), new Type[] { new Type("List<String>") }),
			new Expression[] { new MethodInv(new Var("src", new Type("BufferedReader")),
					new MName("readLine", new Type("String"), new Type[] { new Type("BufferedReader") }),
					new Expression[] {}) });
	//
	public static MethodInv methodInv3 = new MethodInv(new Var("src", new Type("BufferedReader")),
			new MName("readLine", new Type("String"), new Type[] { new Type("BufferedReader") }), new Expression[] {});
	// // lines.add(src.readLine)
	// public static MethodInv methodInv2 = new MethodInv(new Var("lines", new
	// Type("String")),
	// new MName("add", new Type("boolean"), new Type[] { new
	// Type("List<String>") }),
	// new Expression[] { new MethodInv(new Var("src", new
	// Type("BufferedReader")),
	// new MName("readLine", new Type("String"), new Type[] { new
	// Type("BufferedReader") }),
	// new Expression[] {}) });

	public static String keywords = "add line";
	//
	// @Test
	// public void testToString() {
	// // BinOp binOp = new BinOp(new Int(1), new Operator("+"), new Int(2));
	// // assertEquals("1+2", binOp.toString());
	// // BinOp binOp2 = new BinOp(new Int(1), new Operator("+"), new BinOp(new
	// // Int(2), new Operator("+"), new Int(2)));
	// // assertEquals("1+2+2", binOp2.toString());
	// // BinOp binOp3 = new BinOp(new BinOp(new Int(1), new Operator("+"), new
	// // Int(2)), new Operator("*"),
	// // new BinOp(new Int(2), new Operator("/"), new Int(1)));
	// // assertEquals("1+2*2/1", binOp3.toString());
	// //
	// // // MethodInv ToString() Test
	// // MethodInv methodInv = new MethodInv(new Var("lines"), new
	// // MName("add"),
	// // new Expression[] { new MethodInv(new Var("in"), new
	// // MName("readLine"), new Expression[] {}) });
	// // assertEquals("lines.add(in.readLine())", methodInv.toString());
	//
	// }
	//
	// @Test
	// public void testIsInKeywordListFlag() {
	//
	// List<KeywordFlag> keywordListFlag = new
	// KeywordFlag().toKeywordFlag(keywords);
	// // String word = "add";
	// // Expression exp = new Int(2);
	// // System.out.println("Whether the word is used : " +
	// // Expression.keywordListFlag.get(0).isFlag());
	// // System.out.println("Whether word " + word + " is in the keyword list
	// // : "+ exp.isInKeywordListFlag(word));
	// // System.out.println("Whether the word is used : " +
	// // Expression.keywordListFlag.get(0).isFlag());
	//
	// }
	//
	// @Test
	// public void testIntScore() {
	// assertEquals(0.95f, new Int(2).score("add 2 line"), 0);
	// }
	//
	// @Test
	// public void testVarScore() {
	// assertEquals(0.951f, new Var("line", new
	// Type("List<String>")).score(keywords), 0);
	// assertEquals(-0.059f, new Var("src", new
	// Type("List<String>")).score(keywords), 0);
	// assertEquals(0.941f, new Var("readLine", new
	// Type("List<String>")).score(keywords), 0);
	// }
	//
	// @Test
	// public void testMethodInvScore() {
	// assertEquals(1.772f, methodInv1.score(keywords), 0);
	// assertEquals(0.881f, methodInv3.score(keywords), 0);
	// }
	//
	// @Test
	// public void testGenerate() {
	// int i;
	// i = 3;
	// Expression.generate(i).stream().forEach(System.out::println);
	// int expCount = Expression.generate(i).size();
	// System.out.println("Count of all Expression which in length " + i + " is :" +
	// expCount);
	//
	// }

	@Test
	public void testMaxScoreExpression() {
		// int depth = 3;
		// System.out.println("Keywords are : " + keywords + "\nDepth is : "
		// + depth + "\nExpression is : "+ Expression.maxScoreExpression(depth,
		// keywords).toString());
	}

	@Test
	public void testMaxScoreExpressions() {

//		String keywords = "add line"; // test Type System.
//		int depth = 3;
//		System.out.println("Keywords are : " + keywords + "\nDepth is : " + depth + "\nExpressions are : ");
//		Expression.maxScoreExpressions(depth, keywords).stream().forEach(System.out::println);

	}

	// @Test
	// public void testBinGenerate() {
	// int depth = 4;
	// String keywords = "1 +";
	// System.out.println("Int generate size : " + Int.generate(1).size());
	// System.out.println("Operator size : " + Operator.allOperator().size());
	// System.out.println("(Size)Bin Operator class generate : " +
	// BinOp.generateDep(depth, keywords).size());
	// BinOp.generateDep(depth, keywords).stream().forEach(System.out::println);
	// }

	@Test
	public void testTypeFunction() {
		// MethodInv methodInv = new MethodInv(new Var("lines", new
		// Type("String")),
		// new MName("add", new Type("boolean"), new Type("List<String>")),
		// new Expression[] { new MethodInv(new Var("in", new
		// Type("BufferedReader")),
		// new MName("readLine", new Type("String"), new
		// Type("BufferedReader")), new Expression[] {}) });
		// MethodInv methodInv = new MethodInv(new Int(1), new MName("add", new
		// Type("boolean"), new Type[]{new Type("List<String>")}),
		// new Expression[] { new MethodInv(new Int(2),
		// new MName("readLine", new Type("String"), new Type[]{new
		// Type("BufferedReader")}), new Expression[] {}) });
		//
		// System.out.println("Type of " + methodInv.toString() + "is : " +
		// methodInv.type());
		// System.out.println("Type of methodInv return type is : " +
		// methodInv.getmName().getReturnType().toString());
		// System.out.println("Type of methodInv parameter type is : " +
		// methodInv.getmName().getParameterType()[0].toString());
		// System.out.println("Type of Expression on front is : " +
		// methodInv.getExpFront().type());
		// Int intEg = new Int(2);
		// System.out.println("Type of Int Class test is : " + intEg.type());
	}

	@Test
	public void testGenerateMethodInv() {
		 int i = 3;
		 int j = Expression.generate(i).size();
		 System.out.println("Count is : " + j);
		 Expression.generate(i).stream().forEach(System.out::println);
	}

	@Test
	public void testDepth() {
//		System.out.println("============================");
//		Expression exp1 = new Int (1);
//		Expression exp2 = new Var ("a",new Type ("String"));
//		Expression exp3 = new Var ("b",new Type ("bType"));
//		MName mt = new MName("add", new Type("boolean"), new Type[] { new Type("List<String>"),
//				 new Type("String") });
//		Expression exp4 = new MethodInv(exp1,mt,new Expression[] {exp1,exp2});
//		Expression exp5 = new MethodInv(exp4,mt,new Expression[] {exp4,exp4});
//		assertEquals(exp1.getDepth(),1);
//		assertEquals(exp2.getDepth(),1);
//		assertEquals(exp3.getDepth(),1);
//		assertEquals(exp4.getDepth(),2);
//		assertEquals(exp5.getDepth(),3);
	}
	
	@Test
	public void testDuplicateDeleteFunction() {
		// addForeach
		
	}
	

}