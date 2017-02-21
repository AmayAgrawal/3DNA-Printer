/* Author: Amay Agrawal, Birva Patel
 * Project: 3DNA Printer
 * Mentor: Prof. Manish K Gupta
 */

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Stack;

import javax.media.j3d.*;
import javax.swing.JFileChooser;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class fullActionListener extends MouseAdapter implements ActionListener {
	public static File f = null;
	public static int count1=0;
	int counter5 = 0;
	static int counter20=0;
	public fullActionListener(File g,int a,int ad){
		f=g;
		count1 =a;
		counter20=ad;
	}
	public fullActionListener(){}
	
    static int pressed=0;
    public static int lastx;
    public static int lasty;
    public static int lastz;
    private static final float[] colors = {
            // front face (yellow)
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            // back face (green)
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            // right face (blue)
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            // left face (red)
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            // top face (magenta)
            1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 1.0f,
            // bottom face (cyan)
            0.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f,
    };
    ImportActionListener import1 = new ImportActionListener();
    public static PickCanvas pickCanvas;
    public static BranchGroup objRoot;
    public static Transform3D transform;
    public static TransformGroup tg;
    public static SimpleUniverse simpleU;
    public static MouseRotate rotateBehaviour;
    public static TransformGroup masterTrans;
    public static BranchGroup scene;
    public static float canvasx;
    public static float canvasy;
    public static float canvasz;

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    	BufferedReader br = null;
    	try {
            br = new BufferedReader(new FileReader(f));
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
            Double[] xCordinate = new Double[100];
            Double[] yCordinate = new Double[100];
            Double[] zCordinate = new Double[100];
            int face[][] = new int [100][100];
            for(int i=0;i<count1;i++){
           	 for(int j=0;j<count1;j++){
           		 face[i][j]=0;
           	 }
            }
       	 int m=0;

       	 String strLine;
       	 String parts[];
       	 int i=0;

       	 //Read File Line By Line
       	 try {
			while ((strLine = br.readLine()) != null)   {
			   // Print the content on the console
				// System.out.println(strLine);
			   if(strLine.length()<=0){
				   continue;
			   }
			   parts = strLine.split("  ");
			   if(parts[0].equals("v")){
			   double x = Double.parseDouble(parts[1]);
			   double y = Double.parseDouble(parts[2]);
			   double z = Double.parseDouble(parts[3]);
			   xCordinate[m] = x/2;
			   yCordinate[m] = y/2;
			   zCordinate[m++] = z/2;
			   //System.out.println();
			   }
			   if(parts[0].equals("f")){
				   
				   if(parts[1].equals("1") || parts[2].equals("1") || parts[3].equals("1")){
						 counter5++;
					 }
				   
				   int x1 = Integer.parseInt(parts[1]);
				   int x2 = Integer.parseInt(parts[2]);
				   int x3 = Integer.parseInt(parts[3]);
				   System.out.println(x1+" "+x2+" "+x3+"\n");
				   face[x1-1][x2-1]=1;
				   face[x2-1][x1-1]=1;
				   face[x1-1][x3-1]=1;
				   face[x3-1][x1-1]=1;
				   face[x3-1][x2-1]=1;
				   face[x2-1][x3-1]=1;
				   
			   }
			   
			 }
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

       	Prims p =new Prims(count1);
       	int teri[][]=p.check(face);
   	
       	
   	 //Close the input stream
   	 		//br.close();
       	 
       	 
       	 
          try {
			createCanvas(xCordinate,yCordinate,zCordinate,face,count1,teri,counter5);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
       	 //Close the input stream
       	 try {
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        }

                
    public void createCanvas(Double a1[],Double b1[],Double c1[],int d1[][],int e1,int h1[][],int counter45) throws IOException{
    	
    	
    	Double[] xCordinate = new Double[100];
        Double[] yCordinate = new Double[100];
        Double[] zCordinate = new Double[100];
        int faces[][] = new int[100][100];
        int faces1[][] = new int[100][100];
        int faces2[][] = new int[100][100];
        double faces3[][][] = new double [100][100][14];
        int s=2;
        Point3d coords6[] = new Point3d[2];
        Point3d coords7[] = new Point3d[2];
        double faces4[][] = new double [1000][7];
        int counter = 0;
        int j=0;
        
        for(int rt=0;rt<1000;rt++){
   		 for(int rf=0;rf<7;rf++){
   			 faces4[rt][rf]=-20;
   		 }
   	 }
        
        
    	xCordinate = a1;
    	yCordinate = b1;
    	zCordinate = c1;
    	faces = d1;
    	faces2 = h1;
    
    	
    	BranchGroup group = new BranchGroup();
   	 
   	 Point3d coords[] = new Point3d[count1];
   	 Point3d coords1[] = new Point3d[2];
   	 Point3d coords2[] = new Point3d[2];
   	 Point3d coords3[] = new Point3d[2];
   	 Point3d coords4[] = new Point3d[2];
   	 
   	 Shape3D shape;
        LineStripArray lines;
        
        Appearance app = new Appearance();
        
        Appearance appearanceGreen = new Appearance();
        ColoringAttributes coloringAttributesGreen = new ColoringAttributes();
        coloringAttributesGreen.setColor(new Color3f(Color.green));
        appearanceGreen.setColoringAttributes(coloringAttributesGreen);
        
        Appearance appearancered = new Appearance();
        ColoringAttributes coloringAttributesred = new ColoringAttributes();
        coloringAttributesred.setColor(new Color3f(Color.red));
        appearancered.setColoringAttributes(coloringAttributesred);
        
        Appearance appearanceblue = new Appearance();
        ColoringAttributes coloringAttributesblue = new ColoringAttributes();
        coloringAttributesblue.setColor(new Color3f(Color.blue));
        appearanceblue.setColoringAttributes(coloringAttributesblue);
        
        BufferedReader br1 = null;
    	try {
            br1 = new BufferedReader(new FileReader(f));
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        
        
        String strLine1;
        String parts[];
        
        for(int i=0;i<count1;i++){
       	 double x = xCordinate[i];
       	 double y = yCordinate[i];
       	 double z = zCordinate[i];
       	 coords[i] = new Point3d(x,y,z);
        }
        for(int i=0;i<100;i++)
        {	for(int j1=0;j1<100;j1++)
       	 for(int k=0;k<14;k++)
       
       		 faces3[i][j1][k]=-1000;
        }
      	
       		 for(int m=0;m<count1;m++){
       			 for(int n=0;n<count1;n++){
       				 if(faces[m][n]== 1){
       					 try {
							while((strLine1 = br1.readLine()) != null){
								 
								 if(strLine1.length()<=0){
									 continue;
								 }
								 
								 parts = strLine1.split("  ");
								 
								 if(parts[0].equals("f")){
									 int x1 = Integer.parseInt(parts[1]);
									 int x2 = Integer.parseInt(parts[2]);
									 int x3 = Integer.parseInt(parts[3]);
									 
									 System.out.println(x1+" "+x2+" "+x3);
									 
									 if(faces2[x1-1][x2-1]==1){
										 double x = coords[x1-1].x;
										 double y = coords[x1-1].y;
										 double z = coords[x1-1].z;
										 
										 double a = coords[x2-1].x;
										 double b = coords[x2-1].y;
										 double c = coords[x2-1].z;
										 
										 double ratio = 0.1;
										 double ratio1 = 0.9;
										 
										 double xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);

										 x = coords[x1-1].x;
										 y = coords[x1-1].y;
										 z = coords[x1-1].z;
										 
										 a = coords[x3-1].x;
										 b = coords[x3-1].y;
										 c = coords[x3-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 double xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 double xcoor4 = (xcoor+xcoor2)/2;
										 double ycoor4 = (ycoor+ycoor2)/2;
										 double zcoor4 = (zcoor+zcoor2)/2;
										 
										 coords3[0] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 x = coords[x2-1].x;
										 y = coords[x2-1].y;
										 z = coords[x2-1].z;
										 
										 a = coords[x3-1].x;
										 b = coords[x3-1].y;
										 c = coords[x3-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x2-1].x;
										 y = coords[x2-1].y;
										 z = coords[x2-1].z;
										 
										 a = coords[x1-1].x;
										 b = coords[x1-1].y;
										 c = coords[x1-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 xcoor4 = (xcoor + xcoor2)/2;
										 ycoor4 = (ycoor + ycoor2)/2;
										 zcoor4 = (zcoor + zcoor2)/2;
										 
										 coords3[1] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 faces4[counter][0] = coords3[0].x;
	    	        					 faces4[counter][1] = coords3[0].y;
	    	        					 faces4[counter][2] = coords3[0].z;
	    	        					 faces4[counter][3] = coords3[1].x;
	    	        					 faces4[counter][4] = coords3[1].y;
	    	        					 faces4[counter][5] = coords3[1].z;
	    	        					 faces4[counter++][6] = 0;
	    	        					 
										 
										 int vertexCounts1[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts1);
										 
										 //lines.setCoordinates(0, coords3);
										 
										 shape = new Shape3D(lines,app);
										 
										 TransformGroup objRotate = new TransformGroup();
										 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
										 objRotate.addChild(shape);
										 
										 MouseRotate f1 = new MouseRotate();
										 f1.setSchedulingBounds(new BoundingSphere());
										 f1.setTransformGroup(objRotate);
										 group.addChild(f1);
										 
										 group.addChild(objRotate);
										
										 
									 }
									 if(faces2[x2-1][x3-1]==1){
										 double x = coords[x2-1].x;
										 double y = coords[x2-1].y;
										 double z = coords[x2-1].z;
										 
										 double a = coords[x3-1].x;
										 double b = coords[x3-1].y;
										 double c = coords[x3-1].z;
										 
										 double ratio = 0.1;
										 double ratio1 = 0.9;
										 
										 double xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x2-1].x;
										 y = coords[x2-1].y;
										 z = coords[x2-1].z;
										 
										 a = coords[x1-1].x;
										 b = coords[x1-1].y;
										 c = coords[x1-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 double xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 double xcoor4 = (xcoor+xcoor2)/2;
										 double ycoor4 = (ycoor+ycoor2)/2;
										 double zcoor4 = (zcoor+zcoor2)/2;
										 
										 coords3[0] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 
										 x = coords[x3-1].x;
										 y = coords[x3-1].y;
										 z = coords[x3-1].z;
										 
										 a = coords[x2-1].x;
										 b = coords[x2-1].y;
										 c = coords[x2-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x3-1].x;
										 y = coords[x3-1].y;
										 z = coords[x3-1].z;
										 
										 a = coords[x1-1].x;
										 b = coords[x1-1].y;
										 c = coords[x1-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 xcoor4 = (xcoor + xcoor2)/2;
										 ycoor4 = (ycoor + ycoor2)/2;
										 zcoor4 = (zcoor + zcoor2)/2;
										 
										 coords3[1] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 faces4[counter][0] = coords3[0].x;
	    	        					 faces4[counter][1] = coords3[0].y;
	    	        					 faces4[counter][2] = coords3[0].z;
	    	        					 faces4[counter][3] = coords3[1].x;
	    	        					 faces4[counter][4] = coords3[1].y;
	    	        					 faces4[counter][5] = coords3[1].z;
	    	        					 faces4[counter++][6] = 0;
	    	        					 
										 
										 
										 int vertexCounts1[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts1);
										 
										// lines.setCoordinates(0, coords3);
										 
										 shape = new Shape3D(lines,app);
										 
										 TransformGroup objRotate = new TransformGroup();
										 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
										 objRotate.addChild(shape);
										 
										 MouseRotate f1 = new MouseRotate();
										 f1.setSchedulingBounds(new BoundingSphere());
										 f1.setTransformGroup(objRotate);
										 group.addChild(f1);
										 
										 group.addChild(objRotate);
										 }
									 
									 if(faces2[x1-1][x3-1]==1){
										 double x = coords[x1-1].x;
										 double y = coords[x1-1].y;
										 double z = coords[x1-1].z;
										 
										 double a = coords[x2-1].x;
										 double b = coords[x2-1].y;
										 double c = coords[x2-1].z;
										 
										 double ratio = 0.1;
										 double ratio1 = 0.9;
										 
										 double xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x1-1].x;
										 y = coords[x1-1].y;
										 z = coords[x1-1].z;
										 
										 a = coords[x3-1].x;
										 b = coords[x3-1].y;
										 c = coords[x3-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 double xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 double xcoor4 = (xcoor+xcoor2)/2;
										 double ycoor4 = (ycoor+ycoor2)/2;
										 double zcoor4 = (zcoor+zcoor2)/2;
										 
										 coords3[0] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 
										 x = coords[x3-1].x;
										 y = coords[x3-1].y;
										 z = coords[x3-1].z;
										 
										 a = coords[x2-1].x;
										 b = coords[x2-1].y;
										 c = coords[x2-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x3-1].x;
										 y = coords[x3-1].y;
										 z = coords[x3-1].z;
										 
										 a = coords[x1-1].x;
										 b = coords[x1-1].y;
										 c = coords[x1-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 xcoor4 = (xcoor + xcoor2)/2;
										 ycoor4 = (ycoor + ycoor2)/2;
										 zcoor4 = (zcoor + zcoor2)/2;
										 
										 coords3[1] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 faces4[counter][0] = coords3[0].x;
	    	        					 faces4[counter][1] = coords3[0].y;
	    	        					 faces4[counter][2] = coords3[0].z;
	    	        					 faces4[counter][3] = coords3[1].x;
	    	        					 faces4[counter][4] = coords3[1].y;
	    	        					 faces4[counter][5] = coords3[1].z;
	    	        					 faces4[counter++][6] = 0;
	    	        					 
										 
										 int vertexCounts1[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts1);
										 
										// lines.setCoordinates(0, coords3);
										 
										 shape = new Shape3D(lines,app);
										 
										 TransformGroup objRotate = new TransformGroup();
										 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
										 objRotate.addChild(shape);
										 
										 MouseRotate f1 = new MouseRotate();
										 f1.setSchedulingBounds(new BoundingSphere());
										 f1.setTransformGroup(objRotate);
										 group.addChild(f1);
										 
										 group.addChild(objRotate);
										 }
									 
									 if(faces2[x1-1][x2-1]==0){
										 double x = coords[x1-1].x;
										 double y = coords[x1-1].y;
										 double z = coords[x1-1].z;
										 
										 double a = coords[x2-1].x;
										 double b = coords[x2-1].y;
										 double c = coords[x2-1].z;
										 
										 double ratio = 0.1;
										 double ratio1 = 0.9;
										 
										 double xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x1-1].x;
										 y = coords[x1-1].y;
										 z = coords[x1-1].z;
										 
										 a = coords[x3-1].x;
										 b = coords[x3-1].y;
										 c = coords[x3-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 double xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 double xcoor4 = (xcoor+xcoor2)/2;
										 double ycoor4 = (ycoor+ycoor2)/2;
										 double zcoor4 = (zcoor+zcoor2)/2;
										 
										 coords3[0] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 
										 x = coords[x2-1].x;
										 y = coords[x2-1].y;
										 z = coords[x2-1].z;
										 
										 a = coords[x3-1].x;
										 b = coords[x3-1].y;
										 c = coords[x3-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x2-1].x;
										 y = coords[x2-1].y;
										 z = coords[x2-1].z;
										 
										 a = coords[x1-1].x;
										 b = coords[x1-1].y;
										 c = coords[x1-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 xcoor4 = (xcoor + xcoor2)/2;
										 ycoor4 = (ycoor + ycoor2)/2;
										 zcoor4 = (zcoor + zcoor2)/2;
										 
										 coords3[1] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 x = coords3[0].x;
										 y = coords3[0].y;
										 z = coords3[0].z;
										 
										 a = coords3[1].x;
										 b = coords3[1].y;
										 c = coords3[1].z;
										 
										 ratio = 0.4;
										 ratio1 = 0.6;
										 
										 double xcoor5 = (ratio*a) + (ratio1*x) / (ratio+ratio1);
										 double ycoor5 = (ratio*b) + (ratio1*y) / (ratio+ratio1);
										 double zcoor5 = (ratio*c) + (ratio1*z) / (ratio+ratio1);
										 
										 coords4[0] = coords3[0];
										 coords4[1] = new Point3d(xcoor5,ycoor5,zcoor5);
										 
										 faces4[counter][0] = coords4[0].x;
	    	        					 faces4[counter][1] = coords4[0].y;
	    	        					 faces4[counter][2] = coords4[0].z;
	    	        					 faces4[counter][3] = coords4[1].x;
	    	        					 faces4[counter][4] = coords4[1].y;
	    	        					 faces4[counter][5] = coords4[1].z;
	    	        					 faces4[counter++][6] = 0;
										 
										 if(faces3[x1-1][x2-1][s]!=-1000 || faces3[x2-1][x1-1][s]!=-1000)
										 {
											 s+=6;
										 }
										 faces3[x1-1][x2-1][s++]=xcoor5;
										 faces3[x1-1][x2-1][s++]=ycoor5;
										 faces3[x1-1][x2-1][s++]=zcoor5;
										 s-=3;
										 faces3[x2-1][x1-1][s++]=xcoor5;
										 faces3[x2-1][x1-1][s++]=ycoor5;
										 faces3[x2-1][x1-1][s++]=zcoor5;
										 
										 int vertexCounts2[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts2);
										 
										// lines.setCoordinates(0, coords4);
										 
										 shape = new Shape3D(lines,app);
										 
										 TransformGroup objRotate = new TransformGroup();
										 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
										 objRotate.addChild(shape);
										 
										 MouseRotate f1 = new MouseRotate();
										 f1.setSchedulingBounds(new BoundingSphere());
										 f1.setTransformGroup(objRotate);
										 group.addChild(f1);
										 
										 group.addChild(objRotate);
										 
										 ratio = 0.6;
										 ratio1 = 0.4;
										 
										 double xcoor6 = (ratio*a) + (ratio1*x) / (ratio+ratio1);
										 double ycoor6 = (ratio*b) + (ratio1*y) / (ratio+ratio1);
										 double zcoor6 = (ratio*c) + (ratio1*z) / (ratio+ratio1);
										 
										 coords4[0] = new Point3d(xcoor6,ycoor6,zcoor6);
										 coords4[1] = coords3[1];
										 
										 faces4[counter][0] = coords4[0].x;
	    	        					 faces4[counter][1] = coords4[0].y;
	    	        					 faces4[counter][2] = coords4[0].z;
	    	        					 faces4[counter][3] = coords4[1].x;
	    	        					 faces4[counter][4] = coords4[1].y;
	    	        					 faces4[counter][5] = coords4[1].z;
	    	        					 faces4[counter++][6] = 0;
										 
										 faces3[x1-1][x2-1][s++]=xcoor6;
										 faces3[x1-1][x2-1][s++]=ycoor6;
										 faces3[x1-1][x2-1][s++]=zcoor6;
										 s-=3;
										 faces3[x2-1][x1-1][s++]=xcoor6;
										 faces3[x2-1][x1-1][s++]=ycoor6;
										 faces3[x2-1][x1-1][s++]=zcoor6;
										 
										 s=2;
										 int vertexCounts3[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts3);
										 
										 //lines.setCoordinates(0, coords4);
										 
										 shape = new Shape3D(lines,app);
										 
										 objRotate = new TransformGroup();
										 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
										 objRotate.addChild(shape);
										 
										 f1 = new MouseRotate();
										 f1.setSchedulingBounds(new BoundingSphere());
										 f1.setTransformGroup(objRotate);
										 group.addChild(f1);
										 
										 group.addChild(objRotate);
									 }
									 
									 if(faces2[x2-1][x3-1]==0){
										 double x = coords[x2-1].x;
										 double y = coords[x2-1].y;
										 double z = coords[x2-1].z;
										 
										 double a = coords[x3-1].x;
										 double b = coords[x3-1].y;
										 double c = coords[x3-1].z;
										 
										 double ratio = 0.1;
										 double ratio1 = 0.9;
										 
										 double xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x2-1].x;
										 y = coords[x2-1].y;
										 z = coords[x2-1].z;
										 
										 a = coords[x1-1].x;
										 b = coords[x1-1].y;
										 c = coords[x1-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 double xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 double xcoor4 = (xcoor+xcoor2)/2;
										 double ycoor4 = (ycoor+ycoor2)/2;
										 double zcoor4 = (zcoor+zcoor2)/2;
										 
										 coords3[0] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										
										 x = coords[x3-1].x;
										 y = coords[x3-1].y;
										 z = coords[x3-1].z;
										 
										 a = coords[x2-1].x;
										 b = coords[x2-1].y;
										 c = coords[x2-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x3-1].x;
										 y = coords[x3-1].y;
										 z = coords[x3-1].z;
										 
										 a = coords[x1-1].x;
										 b = coords[x1-1].y;
										 c = coords[x1-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 xcoor4 = (xcoor + xcoor2)/2;
										 ycoor4 = (ycoor + ycoor2)/2;
										 zcoor4 = (zcoor + zcoor2)/2;
										 
										 coords3[1] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 x = coords3[0].x;
										 y = coords3[0].y;
										 z = coords3[0].z;
										 
										 a = coords3[1].x;
										 b = coords3[1].y;
										 c = coords3[1].z;
										 
										 ratio = 0.4;
										 ratio1 = 0.6;
										 
										 double xcoor5 = (ratio*a) + (ratio1*x) / (ratio+ratio1);
										 double ycoor5 = (ratio*b) + (ratio1*y) / (ratio+ratio1);
										 double zcoor5 = (ratio*c) + (ratio1*z) / (ratio+ratio1);
										 
										 coords4[0] = coords3[0];
										 coords4[1] = new Point3d(xcoor5,ycoor5,zcoor5);
										 
										 faces4[counter][0] = coords4[0].x;
	    	        					 faces4[counter][1] = coords4[0].y;
	    	        					 faces4[counter][2] = coords4[0].z;
	    	        					 faces4[counter][3] = coords4[1].x;
	    	        					 faces4[counter][4] = coords4[1].y;
	    	        					 faces4[counter][5] = coords4[1].z;
	    	        					 faces4[counter++][6] = 0;
										
										 if(faces3[x2-1][x3-1][s]!=-1000 || faces3[x3-1][x2-1][s]!=-1000)
										 {
											 s+=6;
										 }
										 faces3[x2-1][x3-1][s++]=xcoor5;
										 faces3[x2-1][x3-1][s++]=ycoor5;
										 faces3[x2-1][x3-1][s++]=zcoor5;
										 
										 s-=3;
										 
										 faces3[x3-1][x2-1][s++]=xcoor5;
										 faces3[x3-1][x2-1][s++]=ycoor5;
										 faces3[x3-1][x2-1][s++]=zcoor5;
									
										 int vertexCounts2[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts2);
										 
										// lines.setCoordinates(0, coords4);
										 
										 shape = new Shape3D(lines,app);
										 
										 TransformGroup objRotate = new TransformGroup();
										 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
										 objRotate.addChild(shape);
										 
										 MouseRotate f1 = new MouseRotate();
										 f1.setSchedulingBounds(new BoundingSphere());
										 f1.setTransformGroup(objRotate);
										 group.addChild(f1);
										 
										 group.addChild(objRotate);
										 
										 ratio = 0.6;
										 ratio1 = 0.4;
										 
										 double xcoor6 = (ratio*a) + (ratio1*x) / (ratio+ratio1);
										 double ycoor6 = (ratio*b) + (ratio1*y) / (ratio+ratio1);
										 double zcoor6 = (ratio*c) + (ratio1*z) / (ratio+ratio1);
										 
										 coords4[0] = new Point3d(xcoor6,ycoor6,zcoor6);
										 coords4[1] = coords3[1];
										 
										 faces4[counter][0] = coords4[0].x;
	    	        					 faces4[counter][1] = coords4[0].y;
	    	        					 faces4[counter][2] = coords4[0].z;
	    	        					 faces4[counter][3] = coords4[1].x;
	    	        					 faces4[counter][4] = coords4[1].y;
	    	        					 faces4[counter][5] = coords4[1].z;
	    	        					 faces4[counter++][6] = 0;
										 
										 faces3[x2-1][x3-1][s++]=xcoor6;
										 faces3[x2-1][x3-1][s++]=ycoor6;
										 faces3[x2-1][x3-1][s++]=zcoor6;
										 s-=3;
										 faces3[x3-1][x2-1][s++]=xcoor6;
										 faces3[x3-1][x2-1][s++]=ycoor6;
										 faces3[x3-1][x2-1][s++]=zcoor6;
										 
										 s=2;
										 
										 int vertexCounts3[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts3);
										 
										// lines.setCoordinates(0, coords4);
										 
										 shape = new Shape3D(lines,app);
										 
										 objRotate = new TransformGroup();
										 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
										 objRotate.addChild(shape);
										 
										 f1 = new MouseRotate();
										 f1.setSchedulingBounds(new BoundingSphere());
										 f1.setTransformGroup(objRotate);
										 group.addChild(f1);
										 
										 group.addChild(objRotate);
									 }
									 
									 if(faces2[x1-1][x3-1]==0){
										 double x = coords[x1-1].x;
										 double y = coords[x1-1].y;
										 double z = coords[x1-1].z;
										 
										 double a = coords[x2-1].x;
										 double b = coords[x2-1].y;
										 double c = coords[x2-1].z;
										 
										 double ratio = 0.1;
										 double ratio1 = 0.9;
										 
										 double xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x1-1].x;
										 y = coords[x1-1].y;
										 z = coords[x1-1].z;
										 
										 a = coords[x3-1].x;
										 b = coords[x3-1].y;
										 c = coords[x3-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 double xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 double xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 double ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 double zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 double xcoor4 = (xcoor+xcoor2)/2;
										 double ycoor4 = (ycoor+ycoor2)/2;
										 double zcoor4 = (zcoor+zcoor2)/2;
										 
										 coords3[0] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 
										 x = coords[x3-1].x;
										 y = coords[x3-1].y;
										 z = coords[x3-1].z;
										 
										 a = coords[x2-1].x;
										 b = coords[x2-1].y;
										 c = coords[x2-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor1 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor1 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor1 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 x = coords[x3-1].x;
										 y = coords[x3-1].y;
										 z = coords[x3-1].z;
										 
										 a = coords[x1-1].x;
										 b = coords[x1-1].y;
										 c = coords[x1-1].z;
										 
										 ratio = 0.1;
										 ratio1 = 0.9;
										 
										 xcoor2 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor2 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor2 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 ratio = 0.9;
										 ratio1 = 0.1;
										 
										 xcoor3 = ( (ratio*a) + (ratio1*x) ) / (ratio + ratio1);
										 ycoor3 = ( (ratio*b) + (ratio1*y) ) / (ratio + ratio1);
										 zcoor3 = ( (ratio*c) + (ratio1*z) ) / (ratio + ratio1);
										 
										 xcoor4 = (xcoor + xcoor2)/2;
										 ycoor4 = (ycoor + ycoor2)/2;
										 zcoor4 = (zcoor + zcoor2)/2;
										 
										 coords3[1] = new Point3d(xcoor4,ycoor4,zcoor4);
										 
										 x = coords3[0].x;
										 y = coords3[0].y;
										 z = coords3[0].z;
										 
										 a = coords3[1].x;
										 b = coords3[1].y;
										 c = coords3[1].z;
										 
										 ratio = 0.4;
										 ratio1 = 0.6;
										 
										 double xcoor5 = (ratio*a) + (ratio1*x) / (ratio+ratio1);
										 double ycoor5 = (ratio*b) + (ratio1*y) / (ratio+ratio1);
										 double zcoor5 = (ratio*c) + (ratio1*z) / (ratio+ratio1);
										 
										 coords4[0] = coords3[0];
										 coords4[1] = new Point3d(xcoor5,ycoor5,zcoor5);
										 
										 faces4[counter][0] = coords4[0].x;
	    	        					 faces4[counter][1] = coords4[0].y;
	    	        					 faces4[counter][2] = coords4[0].z;
	    	        					 faces4[counter][3] = coords4[1].x;
	    	        					 faces4[counter][4] = coords4[1].y;
	    	        					 faces4[counter][5] = coords4[1].z;
	    	        					 faces4[counter++][6] = 0;
										 
										 if(faces3[x1-1][x3-1][s]!=-1000 || faces3[x3-1][x1-1][s]!=-1000)
										 {
											 s+=6;
										 }
										 faces3[x1-1][x3-1][s++]=xcoor5;
										 faces3[x1-1][x3-1][s++]=ycoor5;
										 faces3[x1-1][x3-1][s++]=zcoor5;
										 
										 s-=3;
										 
										 faces3[x3-1][x1-1][s++]=xcoor5;
										 faces3[x3-1][x1-1][s++]=ycoor5;
										 faces3[x3-1][x1-1][s++]=zcoor5;
										 
										 int vertexCounts2[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts2);
										 
										 //lines.setCoordinates(0, coords4);
										 
										 shape = new Shape3D(lines,app);
										 
										 TransformGroup objRotate = new TransformGroup();
										 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
										 objRotate.addChild(shape);
										 
										 MouseRotate f1 = new MouseRotate();
										 f1.setSchedulingBounds(new BoundingSphere());
										 f1.setTransformGroup(objRotate);
										 group.addChild(f1);
										 
										 group.addChild(objRotate);
										 
										 ratio = 0.6;
										 ratio1 = 0.4;
										 
										 double xcoor6 = (ratio*a) + (ratio1*x) / (ratio+ratio1);
										 double ycoor6 = (ratio*b) + (ratio1*y) / (ratio+ratio1);
										 double zcoor6 = (ratio*c) + (ratio1*z) / (ratio+ratio1);
										 
										 coords4[0] = new Point3d(xcoor6,ycoor6,zcoor6);
										 coords4[1] = coords3[1];
										 
										 faces4[counter][0] = coords4[0].x;
	    	        					 faces4[counter][1] = coords4[0].y;
	    	        					 faces4[counter][2] = coords4[0].z;
	    	        					 faces4[counter][3] = coords4[1].x;
	    	        					 faces4[counter][4] = coords4[1].y;
	    	        					 faces4[counter][5] = coords4[1].z;
	    	        					 faces4[counter++][6] = 0;
										 
										 faces3[x1-1][x3-1][s++]=xcoor6;
										 faces3[x1-1][x3-1][s++]=ycoor6;
										 faces3[x1-1][x3-1][s++]=zcoor6;
										 s-=3;
										 faces3[x3-1][x1-1][s++]=xcoor6;
										 faces3[x3-1][x1-1][s++]=ycoor6;
										 faces3[x3-1][x1-1][s++]=zcoor6;
										 
										 s=2;
										 
										 int vertexCounts3[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts3);
										 
										 //lines.setCoordinates(0, coords4);
										 
										 shape = new Shape3D(lines,app);
										 
										 objRotate = new TransformGroup();
										 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
										 objRotate.addChild(shape);
										 
										 f1 = new MouseRotate();
										 f1.setSchedulingBounds(new BoundingSphere());
										 f1.setTransformGroup(objRotate);
										 group.addChild(f1);
										 
										 group.addChild(objRotate);
									 }
									 
									 
									 
									 
									 
									 
									 
									 for(int ua=0;ua<100;ua++){
										 for(int ub=0;ub<100;ub++){
											 if(faces3[ua][ub][2]!=-1000 && faces3[ua][ub][8]!=-1000){
												 
												 double dist = (((faces3[ua][ub][2]-faces3[ua][ub][8])*(faces3[ua][ub][2]-faces3[ua][ub][8])) + ((faces3[ua][ub][3]-faces3[ua][ub][9])*(faces3[ua][ub][3]-faces3[ua][ub][9])) + ((faces3[ua][ub][4]-faces3[ua][ub][10])*(faces3[ua][ub][4]-faces3[ua][ub][10])));
												 
												 double dist1 = (((faces3[ua][ub][2]-faces3[ua][ub][11])*(faces3[ua][ub][2]-faces3[ua][ub][11])) + ((faces3[ua][ub][3]-faces3[ua][ub][12])*(faces3[ua][ub][3]-faces3[ua][ub][12])) + ((faces3[ua][ub][4]-faces3[ua][ub][13])*(faces3[ua][ub][4]-faces3[ua][ub][13])));
												 if(dist < dist1){
												    coords6[0] = new Point3d(faces3[ua][ub][2],faces3[ua][ub][3],faces3[ua][ub][4]);
							    	    			coords6[1]=new Point3d(faces3[ua][ub][8],faces3[ua][ub][9],faces3[ua][ub][10]);
							    	    			
							    	    			 faces4[counter][0] = coords6[0].x;
		    	    	        					 faces4[counter][1] = coords6[0].y;
		    	    	        					 faces4[counter][2] = coords6[0].z;
		    	    	        					 faces4[counter][3] = coords6[1].x;
		    	    	        					 faces4[counter][4] = coords6[1].y;
		    	    	        					 faces4[counter][5] = coords6[1].z;
		    	    	        					 faces4[counter++][6] = 0;
		    	    	        					 
							    	    			
												 }
												 else{
													    coords6[0] = new Point3d(faces3[ua][ub][2],faces3[ua][ub][3],faces3[ua][ub][4]);
								    	    			coords6[1]=new Point3d(faces3[ua][ub][11],faces3[ua][ub][12],faces3[ua][ub][13]);
								    	    			
								    	    			 faces4[counter][0] = coords6[0].x;
			    	    	        					 faces4[counter][1] = coords6[0].y;
			    	    	        					 faces4[counter][2] = coords6[0].z;
			    	    	        					 faces4[counter][3] = coords6[1].x;
			    	    	        					 faces4[counter][4] = coords6[1].y;
			    	    	        					 faces4[counter][5] = coords6[1].z;
			    	    	        					 faces4[counter++][6] = 0;
			    	    	        					 
													 
												 }
							    	    			int vertexCounts[] = {2};
							    	    		     lines = new LineStripArray(2,
							    	    		     GeometryArray.COORDINATES, vertexCounts);

							    	    		     //lines.setCoordinates(0, coords6);

							    	    			
							    	    		    shape=new Shape3D(lines , app);
							    	    			
							    	    			

							    	    		    //group.addChild(shape);
							    	    		    
							    	    		    TransformGroup objRotate = new TransformGroup();
							    	    		    objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
							    	    		    objRotate.addChild(shape);
							    	    		    
							    	    		    MouseRotate f1=new MouseRotate();
							    	    		    f1.setSchedulingBounds(new BoundingSphere());
							    	    		    f1.setTransformGroup(objRotate);
							    	    		    group.addChild(f1);
							    	    		 
							    	    		    group.addChild(objRotate);
							    	    		    
							    	    		    
							    	    		    if(dist < dist1){
							    	    		    coords6[0] = new Point3d(faces3[ua][ub][5],faces3[ua][ub][6],faces3[ua][ub][7]);
							    	    			coords6[1]=new Point3d(faces3[ua][ub][11],faces3[ua][ub][12],faces3[ua][ub][13]);
							    	    			
							    	    			 faces4[counter][0] = coords6[0].x;
		    	    	        					 faces4[counter][1] = coords6[0].y;
		    	    	        					 faces4[counter][2] = coords6[0].z;
		    	    	        					 faces4[counter][3] = coords6[1].x;
		    	    	        					 faces4[counter][4] = coords6[1].y;
		    	    	        					 faces4[counter][5] = coords6[1].z;
		    	    	        					 faces4[counter++][6] = 0;
		    	    	        					 
							    	    			
							    	    		    }
							    	    		    else{
							    	    		    	coords6[0] = new Point3d(faces3[ua][ub][5],faces3[ua][ub][6],faces3[ua][ub][7]);
								    	    			coords6[1]=new Point3d(faces3[ua][ub][8],faces3[ua][ub][9],faces3[ua][ub][10]);
								    	    		    
								    	    			 faces4[counter][0] = coords6[0].x;
			    	    	        					 faces4[counter][1] = coords6[0].y;
			    	    	        					 faces4[counter][2] = coords6[0].z;
			    	    	        					 faces4[counter][3] = coords6[1].x;
			    	    	        					 faces4[counter][4] = coords6[1].y;
			    	    	        					 faces4[counter][5] = coords6[1].z;
			    	    	        					 faces4[counter++][6] = 0;
			    	    	        					 
								    	    			
							    	    		    }
							    	    			
							    	    			int vertexCounts1[] = {2};
							    	    		     lines = new LineStripArray(2,
							    	    		     GeometryArray.COORDINATES, vertexCounts1);

							    	    		     //lines.setCoordinates(0, coords6);

							    	    			
							    	    		    shape=new Shape3D(lines , app);
							    	    			
							    	    			

							    	    		    //group.addChild(shape);
							    	    		    
							    	    		    objRotate = new TransformGroup();
							    	    		    objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
							    	    		    objRotate.addChild(shape);
							    	    		    
							    	    		    f1=new MouseRotate();
							    	    		    f1.setSchedulingBounds(new BoundingSphere());
							    	    		    f1.setTransformGroup(objRotate);
							    	    		    group.addChild(f1);
							    	    		 
							    	    		    group.addChild(objRotate);

							    	    		    

											 }
										 }
									 }
  	        	
									
								 }
							 }
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
       				 }
       			 }
       		 
       		 }
       		 
       		
   		 
       		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
   		 
   		 PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
   		 
   		 out.flush();
   		 
   		 
   		 //FileInputStream fstream1 = new FileInputStream("output.txt");
   		 //FileInputStream fstream10 = new FileInputStream("output6.txt");
   		 //FileInputStream fstream2 = new FileInputStream("output.txt");
   		 //FileInputStream fstream3 = new FileInputStream("output.txt");
   		 File file = new File("M_13.txt");
   		 FileInputStream fis = new FileInputStream(file);
   		 int counter30=0;
   		 //BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
   		 //BufferedReader br3 = new BufferedReader(new InputStreamReader(fstream2));
   		 //BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream3));
   		 //BufferedReader br10 = new BufferedReader(new InputStreamReader(fstream10));
   		 Stack<String> stack= new Stack<String>();
   		 String strline1;
   		 String strline2="";
   		 String strline4="";
   		 int counter1=0;
   		 int counter3=0;
   		 int counter4=0;
   		 double xcoor7 = faces4[0][0];
   		 double ycoor7 = faces4[0][1];
   		 double zcoor7 = faces4[0][2];
   		 double xcoor8=0;
   		 double ycoor8=0;
   		 double zcoor8=0;
   		 double llen=-1000;
   		 double mlen=-1000;
   		 double slen=-1000;
   		 boolean small = false;
   		 boolean medium = true;
   		 boolean large = false;
   		 int strindex = 0;
   		 int counter5=0;
   		 int counter6=0;
   		 int[] lineno = new int[50];
   		 boolean reverse = false;
   		 BufferedWriter out1 = new BufferedWriter(new FileWriter( "output7.txt"));
   		 out1.flush();
   		 while(true)
   		 {
   			 if(((faces4[counter1][0]==xcoor7 && faces4[counter1][1]==ycoor7 && faces4[counter1][2]==zcoor7) || (faces4[counter1][3]==xcoor7 && faces4[counter1][4]==ycoor7 && faces4[counter1][5]==zcoor7 )) && faces4[counter1][6]==0)
   			 {
   				 if(faces4[counter1][0]==xcoor7 && faces4[counter1][1]==ycoor7 && faces4[counter1][2]==zcoor7)
   				 {
   					xcoor8 = faces4[counter1][3];
   					ycoor8 = faces4[counter1][4];
   					zcoor8 = faces4[counter1][5];
   					reverse = false;
   					
   				 }
   				 else if(faces4[counter1][3]==xcoor7 && faces4[counter1][4]==ycoor7 && faces4[counter1][5]==zcoor7)
   				 {
   					xcoor8 = faces4[counter1][0];
    					ycoor8 = faces4[counter1][1];
    					zcoor8 = faces4[counter1][2];
    					reverse = true;
   				 }
   				 faces4[counter1][6]=1;
   				 
   				 coords7[0] = new Point3d(xcoor7,ycoor7,zcoor7);
   				 coords7[1] = new Point3d(xcoor8,ycoor8,zcoor8);
   				 
   				 out.write(counter5++ +" "+xcoor7+" "+ycoor7+" "+zcoor7+" "+xcoor8+" "+ycoor8+" "+zcoor8);
   				 out.flush();
   				 out.write(" ");
					 out.flush();
					 double len = (((coords7[0].x - coords7[1].x) * (coords7[0].x - coords7[1].x)) + ((coords7[0].y - coords7[1].y) * (coords7[0].y - coords7[1].y)) + ((coords7[0].z - coords7[1].z) * (coords7[0].z - coords7[1].z)));
       			 //System.out.println("length "+len);
       			 

       			 
       			 if(large == true )
       			 {
       				 if((llen/3)>len)
       				 {
       				 medium = true;
       				 large = false;
       				 mlen = len;
       				// System.out.println(dna.substring(strindex,strindex+18));
       				 
       				 
       				 
       				 if(reverse == true){
       					 String s20="";
       					 while(counter30<=20){
       						s20+=(char)fis.read();
       						counter30++;
       					 }
       					 counter30=0;
       					 //s = new String(dna.substring(strindex, strindex+21));
       					 String srev = new StringBuilder(s20).reverse().toString();
       					 out.write(srev);
       					 out.flush();
       					 out1.write(srev);
       					 out1.flush();
       					 }
       				 else{
       					 String s20="";
       					 while(counter30<=20){
       						s20+=(char)fis.read();
       						counter30++;
       					 }
       					 counter30=0;
       					 //out.write(dna.substring(strindex,strindex+21));
       					 out.write(s20);
       					 out.flush();
       					 out1.write(s20);
       					 out1.flush();
       				 }
       				 out.write("\r\n");
       				 out.flush();
       				 out1.write("\r\n");
       				 out1.flush();
       				 strindex += 21;
       				 
       				 }
       				 else
       				 {
       					 llen = len;
           				// System.out.println(dna.substring(strindex,strindex+42));
       					 if(reverse == true){
       						 String s20="";
           					 while(counter30<=41){
           						s20+=(char)fis.read();
           						counter30++;
           					 }
           					 counter30=0;
           					 //String s = new String(dna.substring(strindex, strindex+42));
           					 String srev = new StringBuilder(s20).reverse().toString();
           					 out.write(srev);
           					 out.flush();
           					 out1.write(srev);
           					 out1.flush();
           					 }
           				 else{
           					 String s20="";
           					 while(counter30<=41){
           						s20+=(char)fis.read();
           						counter30++;
           					 }
           					 counter30=0;
           					 //out.write(dna.substring(strindex,strindex+42));
           					 out.write(s20);
           					 out.flush();
           					 out1.write(s20);
           					 out1.flush();
           				 }
           				 out.write("\r\n");
           				 out.flush();
           				 out1.write("\r\n");
           				 out1.flush();
           				 strindex += 42; 
       				 }
       			 }
       			 else if(medium == true)
       			 {
       				 if((3*mlen)<len)
       				 {
       					 large = true;
       					 medium = false;
       					 llen = len; 
       					// System.out.println(dna.substring(strindex,strindex+42));
       					 if(reverse == true){
       						 String s20="";
           					 while(counter30<=41){
           						s20+=(char)fis.read();
           						counter30++;
           					 }
           					 counter30=0;
           					// String s = new String(dna.substring(strindex, strindex+42));
           					 String srev = new StringBuilder(s20).reverse().toString();
           					 out.write(srev);
           					 out.flush();
           					 out1.write(srev);
           					 out1.flush();
           					 }
           				 else{
           					 String s20="";
           					 while(counter30<=41){
           						s20+=(char)fis.read();
           						counter30++;
           					 }
           					 counter30=0;
           					 //out.write(dna.substring(strindex,strindex+42));
           					 out.write(s20);
           					 out.flush();
           					 out1.write(s20);
           					 out1.flush();
           				 }
           				 out.write("\r\n");
           				 out.flush();
           				 out1.write("\r\n");
           				 out1.flush();
           				 strindex += 42;
       				 }
       				 else if((mlen/3)>len)
       				 {
       					 small = true;
       					 medium = false;
       					 slen = len;
       					 //System.out.println(dna.substring(strindex,strindex+3));
       					 if(reverse == true){
       						 String s20="";
           					 while(counter30<=2){
           						s20+=(char)fis.read();
           						counter30++;
           					 }
           					 counter30=0;
           					 //String s = new String(dna.substring(strindex, strindex+3));
           					 String srev = new StringBuilder(s20).reverse().toString();
           					 out.write(srev);
           					 out.flush();
           					 out1.write(srev);
           					 out1.flush();
           					 }
           				 else{
           					 String s20="";
           					 while(counter30<=2){
           						s20+=(char)fis.read();
           						counter30++;
           					 }
           					 counter30=0;
           					 //out.write(dna.substring(strindex,strindex+3));
           					 out.write(s20);
           					 out.flush();
           					 out1.write(s20);
           					 out1.flush();
           				 }
           				 out.write("\r\n");
           				 out.flush();
           				 out1.write("\r\n");
           				 out1.flush();
           				 strindex += 3;
       				 }
       				 else
       				 {
       					 mlen = len;
       					 //System.out.println(dna.substring(strindex,strindex+18));
       					 if(reverse == true){
       						 String s20="";
           					 while(counter30<=20){
           						s20+=(char)fis.read();
           						counter30++;
           					 }
           					 counter30=0;
           					 //String s = new String(dna.substring(strindex, strindex+21));
           					 String srev = new StringBuilder(s20).reverse().toString();
           					 out.write(srev);
           					 out.flush();
           					 out1.write(srev);
           					 out1.flush();
           					 }
           				 else{
           					 String s20="";
           					 while(counter30<=20){
           						s20+=(char)fis.read();
           						counter30++;
           					 }
           					 counter30=0;
           					 //out.write(dna.substring(strindex,strindex+21));
           					 out.write(s20);
           					 out.flush();
           					 out1.write(s20);
           					 out1.flush();
           				 }
           				 out.write("\r\n");
           				 out.flush();
           				 out1.write("\r\n");
           				 out1.flush();
           				 strindex += 21;
       				 }
       				 
       			 }
       			 else if(small == true)
       			 {
       				 medium = true;
       				 small = false;
       				 slen = len;
       				 //System.out.println(dna.substring(strindex,strindex+18));
       				 if(reverse == true){
       					 String s20="";
       					 while(counter30<=20){
       						s20+=(char)fis.read();
       						counter30++;
       					 }
       					 counter30=0;
       					 //String s = new String(dna.substring(strindex, strindex+21));
       					 String srev = new StringBuilder(s20).reverse().toString();
       					 out.write(srev);
       					 out.flush();
       					 out1.write(srev);
       					 out1.flush();
       				 }
       				 else{
       					 String s20="";
       					 while(counter30<=20){
       						s20+=(char)fis.read();
       						counter30++;
       					 }
       					 counter30=0;
       					 //out.write(dna.substring(strindex,strindex+21));
       					 out.write(s20);
       					 out.flush();
       					 out1.write(s20);
       					 out1.flush();
       				 }
       				 out.write("\r\n");
       				 out.flush();
       				 out1.write("\r\n");
       				 out1.flush();
       				 strindex += 21;
       			 }
   				 
   				 while(counter1<=counter)
   				 {
   					 if(faces4[counter1][0]==xcoor7 && faces4[counter1][1]==ycoor7 && faces4[counter1][2]==zcoor7 && faces4[counter1][3]==xcoor8 && faces4[counter1][4]==ycoor8 && faces4[counter1][5]==zcoor8  && faces4[counter1][6]==0)
   						 faces4[counter1][6]=1;
   					 if(faces4[counter1][0]==xcoor8 && faces4[counter1][1]==ycoor8 && faces4[counter1][2]==zcoor8 && faces4[counter1][3]==xcoor7 && faces4[counter1][4]==ycoor7 && faces4[counter1][5]==zcoor7  && faces4[counter1][6]==0)
   						 faces4[counter1][6]=1;
   					 counter1++;
   				 
   				 }
   				 counter1=0;
   				 xcoor7 = xcoor8;
   				 ycoor7 = ycoor8;
   				 zcoor7 = zcoor8;
   				 counter3++;
   			 }
   			 counter1++;
   			 
   			// counter3++;
   			// System.out.print(dna.charAt(counter2++)); 
   			 int vertexCounts2[] = {2};
				 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts2);
				 
				 lines.setCoordinates(0, coords7);

				 if(counter3==1){
						shape = new Shape3D(lines,appearancered);
					}
					else if(counter3==2){
						shape = new Shape3D(lines,appearanceblue);
					}
					else{
						shape = new Shape3D(lines,appearanceGreen);
					}
				
				 
				 TransformGroup objRotate = new TransformGroup();
				 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
				 objRotate.addChild(shape);
				 
				 MouseRotate f1 = new MouseRotate();
				 f1.setSchedulingBounds(new BoundingSphere());
				 f1.setTransformGroup(objRotate);
				 group.addChild(f1);
				 
				 group.addChild(objRotate);
				  
				
				 if(counter1>counter)
   				 break;
   			
				 
   		 }
   		 out.close();
   		 
   		String str[];
  		 String strline7="";
  		 int n = counter20;
  		 System.out.println("iudhjhdjijskokokok"+" "+n);
  		 Stack<String> stk = new Stack<String>();
  		 Stack<String> stk1 = new Stack<String>();
  		 String str1="";
  		 String str2="";
  		 String edgestaple1="";
  		 String edgestaple2="";
  		 int counter10=0;
  		 FileInputStream fstream4 = new FileInputStream("output.txt");
  		 BufferedReader br6 = new BufferedReader(new InputStreamReader(fstream4));
  		 FileInputStream fstream5 = new FileInputStream("output.txt");
  		 BufferedReader br7 = new BufferedReader(new InputStreamReader(fstream5));

  		             
  		             while ((strline7 = br6.readLine()) != null){
  		                str= strline7.split(" ");
  		                if(str[7].length()==3)
  		                    continue;
  		                else
  		                {	
  		                	counter10++;
  		                    stk.push(str[7]);
  		                    if((stk.peek().length() == 42) && (stk.size()>=(2*n)))
  		                    {
  		                    	System.out.println(counter10);
  		                        for(int dt = 0 ; dt<((2*n)-1) ; dt++)
  		                        {	
  		                            stk1.push(stk.pop());
  		                        }
  		                        String vertexStaple="";
  		                        if(stk.peek().length() == 42)
  		                        {	
  		                        	str1+=stk.peek();
  		                            vertexStaple+= stk.peek().substring(34,42);
  		                            for( int dt = 0 ; dt < (2*n - 1) ; dt++ )
  		                            {
  		                                if(dt == (2*n-2)){
  		                                	str2+=stk1.peek();
  		                                    stk.push(stk1.pop());
  		                                }
  		                                else if((dt % 2) == 0)
  		                                {
  		                                    vertexStaple+= stk1.pop().substring(0,8);
  		                                }
  		                                else
  		                                {	
  		                                	
  		                                    if(stk1.peek().length() == 42)
  		                                        vertexStaple+= stk1.pop().substring(34,42);
  		                                    else
  		                                        vertexStaple+= stk1.pop().substring(13,21);
  		                                }
  		                            }
  		                            String s15 = new String(str1);
  		                            String sReverse = new StringBuilder(s15.substring(16,25)).reverse().toString();
  		                            edgestaple1+=sReverse;
  		                            
  		                            String s1 = new String(str2);
  		                            String sReverse1 = new StringBuilder(s1.substring(8,26)).reverse().toString();
  		                            edgestaple1+=sReverse1;
  		                            
  		                            String s2 = new String(str1);
  		                            String sReverse2 = new StringBuilder(s2.substring(25,34)).reverse().toString();
  		                            edgestaple1+=sReverse2;
  		                            
  		                            String s3 = new String(str1);
  		                            String sReverse3 = new StringBuilder(s3.substring(8,16)).reverse().toString();
  		                            edgestaple2+=sReverse3;
  		                            
  		                            String s4 = new String(str2);
  		                            String sReverse4 = new StringBuilder(s2.substring(26,34)).reverse().toString();
  		                            edgestaple2+=sReverse4;
  		                            
  		                            vertexStaple+= stk.peek().substring(34,42);
  		                            //System.out.println(stk.size());
  		                            String s12 = new String(vertexStaple);
  		                            String sReverse12 = new StringBuilder(s12).reverse().toString();
  		                            vertexStaple=sReverse12;
  		                            vertexStaple=complement(vertexStaple);
  		                            edgestaple1=complement(edgestaple1);
  		                            edgestaple2=complement(edgestaple2);
  		                            System.out.println("vertexstaple is "+vertexStaple);
  		                            System.out.println("edgestaple1 is "+edgestaple1);
  		                            System.out.println("edgestaple2 is "+edgestaple2);
  		                            out1.write("VertexStaple" +"  " +vertexStaple);
  		                            out1.flush();
  		                            out1.write("\r\n");
  		                            out1.flush();
  		                            out1.write("Edgestaple"+"  "+edgestaple1);
  		                            out1.flush();
  		                            out1.write("\r\n");
  		                            out1.flush();
  		                            out1.write("Edgestaple"+"  "+edgestaple2);
  		                            out1.flush();
  		                            out1.write("\r\n");
  		                            out1.flush();
  		                            edgestaple1="";
  		                            edgestaple2="";
  		                            str1="";
  		                            str2="";
  		                        }
  		                    }
  		                }
  		            }
  		             String vertexstaple1 = "";
  		             counter10=0;
  		            //System.out.println(stk.size());
  		             while(!stk.isEmpty()){
  		            	 stk1.push(stk.pop());
  		             }
  		             while(!stk1.isEmpty()){
  		            	 if(counter10 % 2 ==0){
  		            		 vertexstaple1+=stk1.pop().substring(0, 8);
  		            	 }
  		            	 else{
  		            		 if(stk1.peek().length() == 42)
                                   vertexstaple1+= stk1.pop().substring(34,42);
                               else
                                   vertexstaple1+= stk1.pop().substring(10,18);
              
  		            	 }
  		             }
  		             vertexstaple1=complement(vertexstaple1);
  		             System.out.println(vertexstaple1);
  		             out1.write("Vertexstaple"+"  "+vertexstaple1);
  		             out1.flush();
  		             out1.write("\r\n");
  		             out1.flush();
  		             
  		             stk = new Stack<String>();
  		             stk1 = new Stack<String>();
  		             while ((strline7 = br7.readLine()) != null){
   		                str= strline7.split(" ");
   		                stk.push(str[7]);
   		               // System.out.println(stk);
   		                if(stk.size()>= 6 && stk.peek().length() == 3)
   		                {	
   		                	for(int dt = 0 ; dt < 6 ; dt++ )
   		                	{
   		                		stk1.push(stk.pop());
   		                	}
   		                	if(stk1.peek().length() == 3)
   		                	{	
   		                		String[] s1 = new String[4];
   		                		int g=1;
   		                		s1[0]= stk.peek();
   		                		for(int dt = 0; dt < 6 ; dt++)
   		                		{
   		                			if(dt == 1 || dt == 4)
   		                				s1[g++] = stk1.pop();
   		                			else
   		                				stk1.pop();
   		                		}
   		                		if((strline7 = br7.readLine()) != null)
   		                		{
   		                			str= strline7.split(" ");
   		                			s1[g++] = str[7];
   		                		}
   		                		//System.out.println(s1[0]);
   		                		g=1;
   		                		
   		                		String cutedgestaple1="";
       		                	String cutedgestaple2="";
       		                	
       		                	String s5 = new String(s1[0]);
  	                            String sReverse5 = new StringBuilder(s5.substring(8,14)).reverse().toString();
  	                            cutedgestaple1+=sReverse5;
  	                           
  	                            String s6 = new String(s1[1]);
  	                            String sReverse6 = new StringBuilder(s6.substring(0,13)).reverse().toString();
  	                            cutedgestaple1+=sReverse6;
  	                           
  	                            String s7 = new String(s1[2]);
  	                            String sReverse7 = new StringBuilder(s7.substring(14,21)).reverse().toString();
  	                            cutedgestaple1+=sReverse7;
  	                           
  	                            String s8 = new String(s1[2]);
  	                            String sReverse8 = new StringBuilder(s8.substring(8,14)).reverse().toString();
  	                            cutedgestaple2+=sReverse8;
  	                            
  	                            String s9 = new String(s1[3]);
  	                            String sReverse9 = new StringBuilder(s9.substring(0,13)).reverse().toString();
  	                            cutedgestaple2+=sReverse9;
  	                            
  	                            String s10 = new String(s1[0]);
  	                            String sReverse10 = new StringBuilder(s10.substring(14,21)).reverse().toString();
  	                            cutedgestaple2+=sReverse10;
  	                            cutedgestaple1=complement(cutedgestaple1);
  	                            cutedgestaple2=complement(cutedgestaple2);
  	                            System.out.println("cutedgestaple1 is "+cutedgestaple1);
  	                            System.out.println("cutedgestaple2 is "+cutedgestaple2);
  	                            out1.write("Cutedgestaple"+"  "+cutedgestaple1);
  	                            out1.flush();
  	                            out1.write("\r\n");
  	                            out1.flush();
  	                            out1.write("Cutedgestaple"+"  "+cutedgestaple2);
  	                            out1.flush();
  	                            out1.write("\r\n");
  	                            out1.flush();
  	                            cutedgestaple1="";
  	                            cutedgestaple2="";
   		                	}
   		                	else
   		                	{
   		                		for(int dt = 0 ; dt < 6 ; dt++ )
       		                	{
       		                		stk.push(stk1.pop());
       		                	}
   		                	}
   		                	
   		                	
	                           
   		                	
   		                }
   		                
   		                
   		             }
  		           BufferedReader bufferedReader = new BufferedReader(new FileReader("output.txt"));
    		          FileOutputStream out5 = new FileOutputStream("workbook1.xls");
    		          HSSFWorkbook wb = new HSSFWorkbook();
    		          HSSFSheet sheet1 = wb.createSheet("new sheet");
    		          HSSFRow row = sheet1.createRow(3);
    		          HSSFCell cell = row.createCell(1);
    		          cell.setCellValue("3DNA Printer - DNA Sequences generated using the default M_13 Scaffold");
    		          row = sheet1.createRow(8);
 		          cell = row.createCell(1);
 		          cell.setCellValue("Start Vertices (first 3)   "+"End Vertices (next 3)   "+ " DNA Scaffold Sequences");
    		          
    		          int i=10;
    		          int totalbases=0;
    			      String line = null;
    			   while ((line = bufferedReader.readLine()) != null) {
    	        	// given string will be split by the argument delimiter provided. 
    	        	String str10[]=line.split(" ");
    	        	totalbases+=str10[7].length();
    	        	String line5 = str10[1]+"   "+str10[2]+"   "+str10[3]+"        "+str10[4]+"   "+str10[5]+"   "+str10[6]+"         "+str10[7];
    	        	row = sheet1.createRow(i);
    	        	cell = row.createCell(1);
    	        	System.out.println(line);
    	    		cell.setCellValue(line5);
    	        	i++;
    	        	 System.out.println();
    	        }
    			wb.write(out5);
    	 		out5.close();
    	 		int counter50=i;
    	 		int counter100=10;
    	      BufferedReader bufferedReader1 = new BufferedReader(new FileReader("output7.txt"));
 	          FileOutputStream out6 = new FileOutputStream("Output_Sequences.xls");
 	          
 		      String line1 = null;
 		   while ((line1 = bufferedReader1.readLine()) != null) {
        	// given string will be split by the argument delimiter provided. 
 			   	if(counter100<counter50){
 			   		counter100++;
 			   		continue;
 			   	}
 			   	String str11[]=line1.split("  ");
 			   	totalbases+=str11[1].length();
 			   	row = sheet1.createRow(i);
 			   	cell = row.createCell(1);
 			   	System.out.println(line1);
 			   	cell.setCellValue(line1);
    		
 			   	i++;
 			   	System.out.println();
 		   }
 		   row = sheet1.createRow(5);
 		   cell = row.createCell(1);
 		   cell.setCellValue("Number of nucleotides = "+totalbases);
 		   wb.write(out6);
 		   out6.close();
    	 		
    	 		EstimatorActionListener estimate = new EstimatorActionListener(totalbases);
  	 		
  	 		
       MainFrame.TrueEnableContent();
       // Canvas3D is where all the action will be taking place, don't worry, after adding it
       // to your layout, you don't have to touch it.
       Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
       // add Canvas3D

       MainFrame.vPanel.setTopComponent(canvas);

       simpleU = new SimpleUniverse(canvas); // setup the SimpleUniverse, attach the Canvas3D

       //This is very important, the SceneGraph (where all the action takes place) is created
       //by calling a function which here is called 'createSceneGraph'.
       //The function is not necessary, you can put all your code here, but it is a
       //standard in Java3D to create your SceneGraph contents in the function 'createSceneGraph'

       scene = createSceneGraph();
       //set the ViewingPlatform by setting the canvasx, canvasy, canvasz values as 0.0,0.0,2.0
       canvasx = 0.0f;
       canvasy = 0.0f;
       canvasz = 2.0f;

       ViewingPlatform vp = simpleU.getViewingPlatform(); // get the ViewingPlatform of the SimpleUniverse

       TransformGroup View_TransformGroup = vp.getMultiTransformGroup().getTransformGroup(0); // get the TransformGroup associated

       Transform3D View_Transform3D = new Transform3D();    // create a Transform3D for the ViewingPlatform
       View_TransformGroup.getTransform(View_Transform3D); // get the current 3d from the ViewingPlatform

       View_Transform3D.setTranslation(new Vector3f(canvasx, canvasy, canvasz)); // set 3d to  x=0, y=-1, z= 5
       View_TransformGroup.setTransform(View_Transform3D);  // assign Transform3D to ViewPlatform

       // this will optimize your SceneGraph, not necessary, but it will allow your program to run faster.
       scene.compile();
       simpleU.addBranchGraph(scene); //add your SceneGraph to the SimpleUniverse

       simpleU.addBranchGraph(group);
   	
       simpleU.getViewingPlatform().setNominalViewingTransform();
   	
       
       //We now add a pick canvas so that we can get
       pickCanvas = new PickCanvas(canvas, scene);
       pickCanvas.setMode(PickCanvas.GEOMETRY);
       canvas.addMouseListener(this);
   }
   
   public String complement(String f){
  	 String complementreverse="";
  	 for(int yi=0;yi<f.length();yi++){
  		 if(f.charAt(yi) == 'A'){
  			 complementreverse+='T';
  		 }
  		 else if(f.charAt(yi) == 'T'){
  			 complementreverse+='A';
  		 }
  		 if(f.charAt(yi) == 'C'){
  			 complementreverse+='G';
  		 }
  		 if(f.charAt(yi) == 'G'){
  			 complementreverse+='C';
  		 }
  	 }
  	 return complementreverse;
   }
  

   public static BranchGroup createSceneGraph() {

       // This BranchGroup is the root of the SceneGraph, 'objRoot' is the name I use,
       // and it is typically the standard name for it, but can be named anything.
       objRoot = new BranchGroup();
       objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
       objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
       objRoot.setCapability(BranchGroup.ALLOW_PICKABLE_WRITE);
       objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
       objRoot.setCapability(BranchGroup.ALLOW_DETACH);
       objRoot.setCapability(Node.ENABLE_PICK_REPORTING);
       getNewBoxPlane();//creates a plane of cubes, planes can be combined to form the full fledged canvas.
       return objRoot;
   }
   public static void getNewBoxPlane(){
       masterTrans=new TransformGroup();
       masterTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
       masterTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
       masterTrans.setCapability(Node.ENABLE_PICK_REPORTING);
       masterTrans.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
       masterTrans.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
       masterTrans.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);

       float vposition=0.105f;
       float hposition=0.105f;
       float zposition=0.105f;

       for(int i=0;i<MainFrame.width;i++){
           for(int j=0;j<MainFrame.height;j++){
               for (int k=0;k<MainFrame.depth;k++){
                   if(MainFrame.StoreCoordinates[i][j][k]==true){
                       DNAColorCube c1 = new DNAColorCube(0.05f);
                       c1.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
                       c1.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
                       c1.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
                       c1.setCapability(Node.ENABLE_PICK_REPORTING);
                       c1.setCordinate(i, j-MainFrame.height+1, k-MainFrame.depth+1);
                       tg = new TransformGroup();
                       tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                       tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
                       transform = new Transform3D();
                       Vector3f vector = new Vector3f(0f+i*hposition,(0f+j*vposition)*-1, (0f+k*zposition)*-1);
                       transform.setTranslation(vector);
                       tg.addChild(c1);
                       //c1.printDetails();
                       tg.setTransform(transform);
                       masterTrans.addChild(tg);
                   }
               }
           }
       }
       objRoot.addChild(masterTrans);
       rotateBehaviour=new MouseRotate();
       rotateBehaviour.setTransformGroup(masterTrans);
       rotateBehaviour.setSchedulingBounds(new BoundingSphere());
       masterTrans.addChild(rotateBehaviour);

       //}
       //  if(pressed==1)
       //  importingFunction();
   }
   public void destroy() { // this function will allow Java3D to clean up upon quiting
       simpleU.removeAllLocales();
   }
   public void importingFunction(){
       pressed=0;
   }
   public void mouseClicked(MouseEvent e)
   {
       MainFrame.isPDFSaved=false;
       MainFrame.isCSVSaved=false;
       pickCanvas.setShapeLocation(e);
       PickResult result = pickCanvas.pickClosest();
       if (result == null) {
           System.out.println("Nothing picked");
           int xCord=e.getX();
           int yCord=e.getY();
           System.out.println("Nothing selected at xCord "+xCord+" yCord"+yCord);

       }
       else {
           DNAColorCube s = (DNAColorCube)result.getNode(PickResult.SHAPE3D);
           if (s != null) {
               MainFrame.StoreCoordinates[s.getX()][(s.getY()*-1)][(s.getZ()*-1)]=true;
               lastx=s.getX();
               lasty=s.getY();
               lastz=s.getZ();
               System.out.println(s.getClass().getName());
               System.out.println("X cordinate"+s.getX());
               System.out.println("Y cordinate"+s.getY());
               System.out.println("Z cordinate"+s.getZ());
               QuadArray cube = new QuadArray(24, QuadArray.COORDINATES|QuadArray.COLOR_3);
               cube.setColors(0, colors);
               s.setGeometry(cube);

           }
           else{
               System.out.println("null");
           }
       }
   }

}