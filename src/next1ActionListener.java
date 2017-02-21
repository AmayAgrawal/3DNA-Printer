/* Author: Amay Agrawal, Birva Patel
 * Project: 3DNA Printer
 * Mentor: Prof. Manish K Gupta
 */

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.media.j3d.*;
import javax.swing.JFileChooser;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class next1ActionListener extends MouseAdapter implements ActionListener {
	public static File f = null;
	public static int count1=0;
	public next1ActionListener(File g,int a){
		f=g;
		count1 =a;
	}
	public next1ActionListener(){}
	
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
       	 
       	 
       	 
          createCanvas(xCordinate,yCordinate,zCordinate,face,count1,teri);
       	 //Close the input stream
       	 try {
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        }

                
    public void createCanvas(Double a1[],Double b1[],Double c1[],int d1[][],int e1,int h1[][]){
    	
    	
    	Double[] xCordinate = new Double[100];
        Double[] yCordinate = new Double[100];
        Double[] zCordinate = new Double[100];
        int faces[][] = new int[100][100];
        int faces1[][] = new int[100][100];
        int faces2[][] = new int[100][100];
        double faces3[][][] = new double [100][100][14];
        int s=2;
        Point3d coords6[] = new Point3d[2];
        int j=0;
        
        
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
										 
										 int vertexCounts1[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts1);
										 
										 lines.setCoordinates(0, coords3);
										 
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
										 
										 
										 int vertexCounts1[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts1);
										 
										 lines.setCoordinates(0, coords3);
										 
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
										 
										 int vertexCounts1[] = {2};
										 lines = new LineStripArray(2,GeometryArray.COORDINATES,vertexCounts1);
										 
										 lines.setCoordinates(0, coords3);
										 
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
										 
										 lines.setCoordinates(0, coords4);
										 
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
										 
										 lines.setCoordinates(0, coords4);
										 
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
										 
										 lines.setCoordinates(0, coords4);
										 
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
										 
										 lines.setCoordinates(0, coords4);
										 
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
										 
										 lines.setCoordinates(0, coords4);
										 
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
										 
										 lines.setCoordinates(0, coords4);
										 
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
									 
									 for(int ua=0;ua<20;ua++){
										 for(int ub=0;ub<20;ub++){
											 for(int uc=0;uc<14;uc++){
												 System.out.print(faces3[ua][ub][uc]+" ");
											 }
											 System.out.println();
										 }
										 System.out.println();
									 }
									 
									 
									 
									 
									 
									 for(int ua=0;ua<100;ua++){
										 for(int ub=0;ub<100;ub++){
											 if(faces3[ua][ub][2]!=-1000 && faces3[ua][ub][8]!=-1000){
												 
												 double dist = (((faces3[ua][ub][2]-faces3[ua][ub][8])*(faces3[ua][ub][2]-faces3[ua][ub][8])) + ((faces3[ua][ub][3]-faces3[ua][ub][9])*(faces3[ua][ub][3]-faces3[ua][ub][9])) + ((faces3[ua][ub][4]-faces3[ua][ub][10])*(faces3[ua][ub][4]-faces3[ua][ub][10])));
												 
												 double dist1 = (((faces3[ua][ub][2]-faces3[ua][ub][11])*(faces3[ua][ub][2]-faces3[ua][ub][11])) + ((faces3[ua][ub][3]-faces3[ua][ub][12])*(faces3[ua][ub][3]-faces3[ua][ub][12])) + ((faces3[ua][ub][4]-faces3[ua][ub][13])*(faces3[ua][ub][4]-faces3[ua][ub][13])));
												 if(dist < dist1){
												 coords6[0] = new Point3d(faces3[ua][ub][2],faces3[ua][ub][3],faces3[ua][ub][4]);
							    	    			coords6[1]=new Point3d(faces3[ua][ub][8],faces3[ua][ub][9],faces3[ua][ub][10]);
												 }
												 else{
													 coords6[0] = new Point3d(faces3[ua][ub][2],faces3[ua][ub][3],faces3[ua][ub][4]);
								    	    			coords6[1]=new Point3d(faces3[ua][ub][11],faces3[ua][ub][12],faces3[ua][ub][13]);
													 
												 }
							    	    			int vertexCounts[] = {2};
							    	    		     lines = new LineStripArray(2,
							    	    		     GeometryArray.COORDINATES, vertexCounts);

							    	    		     lines.setCoordinates(0, coords6);

							    	    			
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
							    	    		    }
							    	    		    else{
							    	    		    	coords6[0] = new Point3d(faces3[ua][ub][5],faces3[ua][ub][6],faces3[ua][ub][7]);
								    	    			coords6[1]=new Point3d(faces3[ua][ub][8],faces3[ua][ub][9],faces3[ua][ub][10]);
								    	    		    
							    	    		    }
							    	    			
							    	    			int vertexCounts1[] = {2};
							    	    		     lines = new LineStripArray(2,
							    	    		     GeometryArray.COORDINATES, vertexCounts1);

							    	    		     lines.setCoordinates(0, coords6);

							    	    			
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