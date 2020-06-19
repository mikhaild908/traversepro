import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


public class Plotter extends Panel{
	private java.util.List newList;
	private JTextField newTextField;
	private java.util.List originalPoints = new ArrayList();
	private java.util.List tempPoints = new ArrayList();
	private int panelHeight;
	private int panelWidth;
	private int maxDistance;
	private int centerX, centerY;
	private int yOffset,xOffset;
	private double scale=1;
	private int smallerDimension=0;
	private int maxX,maxY;
	private int minX,minY;
	
	
	public Plotter(java.util.List list){	
		newList = list;				
		this.setSize(750,550);
		this.setBackground(Color.black);		
		getPoints();
		this.setVisible(true);						
	}

	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		int counter = 0;
		int x,y;
		Point2D.Double point1;
		Point2D.Double point2;
		String position;
	
		g2.setPaint(Color.yellow);

		while(counter<originalPoints.size()-1){
			double newX,newY;			
			point1 = (Point2D.Double)tempPoints.get(counter);
			newX = point1.getX();			
			newY = point1.getY() ;
			point1.setLocation(newX,newY);
			
			point2 = (Point2D.Double)tempPoints.get(++counter);
			newX = point2.getX();
			newY = point2.getY() ;
			point2.setLocation(newX,newY);
			
			g2.draw(new Line2D.Double(point1,point2));
			x = (int)(point1.getX());
			y = (int)(point1.getY());
			position = "Sta " + String.valueOf(counter);
			g2.drawString(position,x,y);
		}//end while
		//g2.drawString("*",centerX,centerY);				
	}//end paint
	
	private void getPoints(){
		getCenter();
		int counter=6;
		Point2D.Double newPoint;	
		
		while(counter<newList.size()){
			double x,y;
			newTextField =(JTextField)newList.get(counter);
			y = Double.parseDouble(newTextField.getText());
			newTextField =(JTextField)newList.get(++counter);
			x = Double.parseDouble(newTextField.getText());
			newPoint = new Point2D.Double(x,-y);
			originalPoints.add(newPoint);					
			counter +=7;		
		}//end while
		
		getMaxMin();
	}//end getPoints

	private void getCenter(){
		panelHeight = this.getHeight();
		panelWidth = this.getWidth();
		centerY = panelHeight/2;		
		centerX = panelWidth/2;		
	}//end setPlottingArea
		
	private void getMaxMin(){		
		Point2D newPoint;		

		newPoint = (Point2D)originalPoints.get(0);
		maxX = (int)newPoint.getX();
		minX = (int)newPoint.getX();
		maxY = (int)newPoint.getY();
		minY = (int)newPoint.getY();
	
		for(int counter=1;counter<originalPoints.size();counter++){
			newPoint = (Point2D)originalPoints.get(counter);
			if((int)newPoint.getX()>maxX){
				maxX = (int)newPoint.getX();
			}
			if((int)newPoint.getX()<minX){
				minX = (int)newPoint.getX();
			}
			if((int)newPoint.getY()>maxY){
				maxY = (int)newPoint.getY();
			}			
			if((int)newPoint.getY()<minY){
				minY = (int)newPoint.getY();
			}
			
			if ((maxX - minX)>(maxY - minY)){
				maxDistance = maxX - minX;
			}
			else{
				maxDistance = maxY - minY;
			}			
		}//end for		
		
		getOffsets();
		setScale();	
		bringToUpperLeftEdge();
	}//end getMaxMin
	
	private void getOffsets(){
		xOffset = minX;
		yOffset = 1*minY;		
	}//end getOffsets

	private void setScale(){
		if(panelHeight<panelWidth) smallerDimension = panelHeight;
		else smallerDimension = panelWidth;
		scale = (double)smallerDimension/(double)maxDistance;		
	}//end setScale()
	
	private void bringToUpperLeftEdge(){
		//set points to upper left edge of panel
		//then put points in tempPoints
		
		Point2D newPoint;
		double x,y;
		int counter=0;
		//System.out.println("Scale:" + scale);
		while(counter<originalPoints.size()){
			newPoint = (Point2D)(originalPoints.get(counter));
			
			//(scale/1.25) yields 80% of the total size
			y =(newPoint.getY()-yOffset)*scale/1.25+(panelHeight/2)-((maxDistance/2)*scale/1.25);
			x =(newPoint.getX()-xOffset)*scale/1.25+(panelWidth/2)-((maxDistance/2)*scale/1.25);
			
			newPoint.setLocation(x,y);
			tempPoints.add(newPoint);
			counter++;
		}//end while			
	}//end bringToLowerLeftEdge()	

}//end Plotter
