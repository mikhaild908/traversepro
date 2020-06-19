import java.awt.*;
import java.util.*;
public class ChangeBackground{
	public static void changeBackground(Container c,Color newColor){		
		Component anotherComponent;		
		int counter=0;
		
		changeBackground((Component)c,newColor);
		
		while(c.getComponentCount()>counter){
		String className;
			int anotherCounter=0;			
			anotherComponent = c.getComponent(counter);
			className = anotherComponent.getClass().toString();
			
			if(!className.equals("class javax.swing.JTextField")){
				anotherComponent.setBackground(newColor);
			}
			counter++;			
		}		
		//System.out.println("container");
	}

	public static void changeBackground(Component c,Color newColor){
		c.setBackground(newColor);
		//System.out.println("component");		
	}

public static void changeForeground(Container c,Color newColor){		
		Component anotherComponent;		
		int counter=0;
		
		changeForeground((Component)c,newColor);
		
		while(c.getComponentCount()>counter){
		String className;
			int anotherCounter=0;			
			anotherComponent = c.getComponent(counter);
			className = anotherComponent.getClass().toString();
			if(!className.equals("class javax.swing.JTextField")){
				anotherComponent.setForeground(newColor);
			}
			counter++;			
		}		
		//System.out.println("container");
	}

	public static void changeForeground(Component c,Color newColor){
		c.setForeground(newColor);
		//System.out.println("component");		
	}


}
