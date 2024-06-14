package Operateurs;

import Types.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class FormuleParser
{
	private String formule;
	private TableauDynamiqueND grille;
	private int[] index;

	// SI( EQ(COM P T ER(G0), 1),SI(SU P EQ(COM P T ER(G8), 4), 0, 1),SI(EQ(COMPTER(G8), 2), 1, 0) )
	public FormuleParser(String formule, TableauDynamiqueND grille, int ... index)
	{
		this.formule = formule.replaceAll("\\s+", "");
		this.grille = grille;
		this.index = index;
	}

	public Operateur parse()
	{
		Stack<Object> stack = new Stack<>();
		StringBuilder sb = new StringBuilder(); //token

		for (int i = 0; i < formule.length(); i++)
		{
			char c = formule.charAt(i);

			if (c == '(')
			{
				if (sb.length() != 0)
				{
					String operateur = sb.toString();
					sb = new StringBuilder();
					stack.push(operateur);
				}
				stack.push("(");
			}
			else if (c == ')')
			{
				if (sb.length() != 0)
				{
					String argument = sb.toString();
					sb = new StringBuilder();
					stack.push(argument);
				}
				ArrayList<Object> arguments = new ArrayList<>();

				while (!stack.isEmpty() && !stack.peek().equals("("))
				{
					if (stack.peek().equals(","))
					{
						stack.pop();
						continue;
					}
					arguments.addFirst(stack.pop());
				}
				stack.pop();
				Operateur op, arg1, arg2, arg3 = null;
				switch((String) stack.pop())
				{
					case "ADD":
						if (arguments.size() != 2)
						{
							throw new IllegalArgumentException("ADD doit avoir 2 arguments");
						}
						arg1 = arguments.get(0) instanceof Operateur ? (Operateur) arguments.get(0) : new CONST(Integer.parseInt((String) arguments.get(0)));
						arg2 = arguments.get(1) instanceof Operateur ? (Operateur) arguments.get(1) : new CONST(Integer.parseInt((String) arguments.get(1)));
						op = new ADD(arg1, arg2);
						stack.push(op);
						break;
					case "SUB":
						if (arguments.size() != 2)
						{
							throw new IllegalArgumentException("SUB doit avoir 2 arguments");
						}
						arg1 = arguments.get(0) instanceof Operateur ? (Operateur) arguments.get(0) : new CONST(Integer.parseInt((String) arguments.get(0)));
						arg2 = arguments.get(1) instanceof Operateur ? (Operateur) arguments.get(1) : new CONST(Integer.parseInt((String) arguments.get(1)));
						op = new SUB(new CONST(Integer.parseInt((String) arguments.get(0))), new CONST(Integer.parseInt((String) arguments.get(1))));
						stack.push(op);
						break;
					case "MUL":
						if (arguments.size() != 2)
						{
							throw new IllegalArgumentException("MUL doit avoir 2 arguments");
						}
						arg1 = arguments.get(0) instanceof Operateur ? (Operateur) arguments.get(0) : new CONST(Integer.parseInt((String) arguments.get(0)));
						arg2 = arguments.get(1) instanceof Operateur ? (Operateur) arguments.get(1) : new CONST(Integer.parseInt((String) arguments.get(1)));
						op = new MUL(arg1, arg2);
						stack.push(op);
						break;
					case "EQ":
						if (arguments.size() != 2)
						{
							throw new IllegalArgumentException("EQ doit avoir 2 arguments");
						}
						arg1 = arguments.get(0) instanceof Operateur ? (Operateur) arguments.get(0) : new CONST(Integer.parseInt((String) arguments.get(0)));
						arg2 = arguments.get(1) instanceof Operateur ? (Operateur) arguments.get(1) : new CONST(Integer.parseInt((String) arguments.get(1)));
						op = new EQ(arg1, arg2);
						stack.push(op);
						break;
					case "ET":
						if (arguments.size() != 2)
						{
							throw new IllegalArgumentException("ET doit avoir 2 arguments");
						}
						arg1 = arguments.get(0) instanceof Operateur ? (Operateur) arguments.get(0) : new CONST(Integer.parseInt((String) arguments.get(0)));
						arg2 = arguments.get(1) instanceof Operateur ? (Operateur) arguments.get(1) : new CONST(Integer.parseInt((String) arguments.get(1)));
						op = new ET(arg1, arg2);
						stack.push(op);
						break;
					case "NON":
						if (arguments.size() != 1)
						{
							throw new IllegalArgumentException("NON doit avoir 1 argument");
						}
						arg1 = arguments.get(0) instanceof Operateur ? (Operateur) arguments.get(0) : new CONST(Integer.parseInt((String) arguments.get(0)));
						op = new NON(arg1);
						stack.push(op);
						break;
					case "OU":
						if (arguments.size() != 2)
						{
							throw new IllegalArgumentException("OU doit avoir 2 arguments");
						}
						arg1 = arguments.get(0) instanceof Operateur ? (Operateur) arguments.get(0) : new CONST(Integer.parseInt((String) arguments.get(0)));
						arg2 = arguments.get(1) instanceof Operateur ? (Operateur) arguments.get(1) : new CONST(Integer.parseInt((String) arguments.get(1)));
						op = new OU(arg1, arg2);
						stack.push(op);
						break;
					case "SI":
						if (arguments.size() != 3)
						{
							throw new IllegalArgumentException("SI doit avoir 3 arguments");
						}
						arg1 = arguments.get(0) instanceof Operateur ? (Operateur) arguments.get(0) : new CONST(Integer.parseInt((String) arguments.get(0)));
						arg2 = arguments.get(1) instanceof Operateur ? (Operateur) arguments.get(1) : new CONST(Integer.parseInt((String) arguments.get(1)));
						arg3 = arguments.get(2) instanceof Operateur ? (Operateur) arguments.get(2) : new CONST(Integer.parseInt((String) arguments.get(2)));
						op = new SI(arg1, arg2, arg3);
						stack.push(op);
						break;
					case "SUP":
						if (arguments.size() != 2)
						{
							throw new IllegalArgumentException("SUP doit avoir 2 arguments");
						}
						arg1 = arguments.get(0) instanceof Operateur ? (Operateur) arguments.get(0) : new CONST(Integer.parseInt((String) arguments.get(0)));
						arg2 = arguments.get(1) instanceof Operateur ? (Operateur) arguments.get(1) : new CONST(Integer.parseInt((String) arguments.get(1)));
						op = new SUP(arg1, arg2);
						stack.push(op);
						break;
					case "SUPEQ":
						if (arguments.size() != 2)
						{
							throw new IllegalArgumentException("SUPEQ doit avoir 2 arguments");
						}
						arg1 = arguments.get(0) instanceof Operateur ? (Operateur) arguments.get(0) : new CONST(Integer.parseInt((String) arguments.get(0)));
						arg2 = arguments.get(1) instanceof Operateur ? (Operateur) arguments.get(1) : new CONST(Integer.parseInt((String) arguments.get(1)));
						op = new SUPEQ(arg1, arg2);
						stack.push(op);
						break;
					case "COMPTER":
						if (arguments.size() != 1)
						{
							throw new IllegalArgumentException("COMPTER doit avoir 1 argument");
						}
						Object argCOMPTER = arguments.get(0);
						if (!(argCOMPTER instanceof String))
						{
							throw new IllegalArgumentException("COMPTER doit avoir une chaine de caractere en argument");
						}

						switch((String) argCOMPTER)
						{
							case "G0":
								op = new COMPTER(new G0(grille, index));
								stack.push(op);
								break;
							case "G2":
								op = new COMPTER(new G2(grille, index));
								stack.push(op);
								break;
							case "G4":
								op = new COMPTER(new G4(grille, index));
								stack.push(op);
								break;
							case "G6":
								op = new COMPTER(new G6(grille, index));
								stack.push(op);
								break;
							case "G8":
								op = new COMPTER(new G8(grille, index));
								stack.push(op);
								break;
							case "G26":
								op = new COMPTER(new G26(grille, index));
								stack.push(op);
								break;
							case "G2*":
								op = new COMPTER(new G2e(grille, index));
								stack.push(op);
								break;
							case "G4*":
								op = new COMPTER(new G4e(grille, index));
								stack.push(op);
								break;
							case "G6*":
								op = new COMPTER(new G6e(grille, index));
								stack.push(op);
								break;
							case "G8*":
								op = new COMPTER(new G8e(grille, index));
								stack.push(op);
								break;
							case "G26*":
								op = new COMPTER(new G26e(grille, index));
								stack.push(op);
								break;
							default:
								throw new IllegalArgumentException("Argument de COMPTER invalide");
						}
						break;

				}
			}
			else if (c == ',')
			{
				if(sb.length() != 0)
				{
					String argument = sb.toString();
					stack.push(argument);
					sb = new StringBuilder();
				}
				stack.push(",");
			}
			else
			{
				sb.append(c);
			}
		}
		if(stack.size() == 1)
			return (Operateur) stack.pop();
		else
		{
			throw new IllegalArgumentException("Erreur de syntaxe dans la formule");
		}
	}
}