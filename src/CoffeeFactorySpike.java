/*
 * Playing with an idea for a simplified Factory Method
 * design pattern assignment.
 *
 * In Decorator (Star Buzz coffee drinks) the "Conversation
 * with a Decorator" mentioned that a difficulty was how
 * to nest Decorators when creating things. We said that a
 * factory pattern was a potential solution. So this is that.
 *
 * String base = "HouseBlend"; // other future options: Pine, Spruce
 * String[] addOns = {"Mocha", "Whip"};  ///incorrect spelling for addOns.
 * Beverage drink = factory.makeDrink(base, addIns); ///if .equal then do house blends or if .equal darkroast etc.
 *													///reflection or something to do
 *													///choose defult base if missed spelled. houseblend should be basecase
 * So, can we turn a String into an object of that name? Let's find out...
 * Turns, out (see below) that we CAN!! 
 */

public class CoffeeFactorySpike {
    public static void main(String[] args) {
	Beverage b = null;
	String[] items = {"HouseBlend", "Whip", "Whip"}; 
	for (String s : items) {
	    System.out.print("String to convert: ");
	    System.out.println(s);
	    Class c = null;  ///java has a Class.. 12min 10/20/21
	    try {
		c = Class.forName(s);  ///java has a class //i think this is a basecase
	    } catch (ClassNotFoundException e) {
		System.err.println("Class not found.");
		e.printStackTrace();
	    }
	    System.out.println("Just converted a string into a class object!");
	    System.out.println(c);
	    System.out.println(); //don't have a object yet
	    Object o = null;  //making an object base case i think
	    try {
		o = c.newInstance(); //something...
	    } catch (InstantiationException e) { //catch something if something goes wrong
		System.err.println("Not able to instantiate.");
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		System.err.println("Not able to access.");
		e.printStackTrace();
	    }
	    System.out.println("Just instantiated an object of that class");
	    System.out.println(o);
	    System.out.println();

	    if (b == null) {  //to see if we are in the base case or wraping stage or something
		// first item so base tree
		b = (Beverage) o;
	    }
	    else {
		// not base so wrap
		((Decoration) o).wrap(b); ///wrap method, have to cast it becuse its an object or something. you'll get an error or something if you don't 19min
		b = (Beverage) o;
	    }
	    System.out.println("Just casted that object as a Beverage and possibly did some decorator stuf");
	    System.out.println(b);
	    System.out.println();
	}
	System.out.println();
	System.out.println("Final drink: " + b.name() 
			   + " costs $" + b.cost());
	System.out.println();
    }
}

abstract class Beverage {
    private String description;
    private float cost;

    public void setDescription(String d) { description = d; }
    public String getDescription() { return description; }
    public abstract float cost();
    public abstract String name();

    public String toString() {
	return "["+ getDescription() 
	    + "," + cost()
	    + "]";
    }
}

class HouseBlend extends Beverage {
    private static float HouseBlendCost = 1.99f;
    public HouseBlend() {
	super();
	setDescription("HouseBlend");
    }
    public float cost() { return HouseBlendCost; }
    public String name() { return " HouseBlend"; }
}

abstract class Decoration extends Beverage {
    protected Beverage component;
    public void wrap(Beverage t) { component = t; }
    public String toString() {
	return "["+ (component==null ? "null" : component.toString())
	    + ", " + getDescription() 
	    + "," + (cost()-(component==null ? 0 : component.cost()))
	    + "]";
    }
    public String name() {
	return (component==null) ? "" : component.name();
    }
}
class Whip extends Decoration {
    private static float WhipCost = .29f;
    public Whip() {
	super();
	setDescription("Whip");
    }
    public float cost() {
	return WhipCost + (component==null ? 0 : component.cost());
    }
    public String name() { return " Whip" + super.name(); }
}