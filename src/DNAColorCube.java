/* Author: Amay Agrawal, Birva Patel
 * Project: 3DNA Printer
 * Mentor: Prof. Manish K Gupta
 */


import com.sun.j3d.utils.geometry.ColorCube;

public class DNAColorCube extends ColorCube{

    public int xCord;
    public int yCord;
    public int zCord;

    public DNAColorCube(float f) {
        super(f);
    }
    public void setCordinate(int x, int y, int z){
        xCord=x;
        yCord=y;
        zCord=z;
    }
    public int getX(){
        return xCord;
    }
    public int getY(){
        return yCord;
    }
    public int getZ(){
        return zCord;
    }
    public void printDetails(){
        System.out.println("New DNA Cube has been added with xCord "+xCord+" yCord"+yCord+" zCord"+zCord);
    }

}
