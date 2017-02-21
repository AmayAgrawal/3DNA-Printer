/* Author: Amay Agrawal, Birva Patel
 * Project: 3DNA Printer
 * Mentor: Prof. Manish K Gupta
 */

import java.util.ArrayList;

public class SaveFinalBoundarySequences {

    //static CoordinatesSeqMap c=new CoordinatesSeqMap();
    static ArrayList<DNASequence> FinalSeqList= CoordinatesSeqMap.SeqList;
    public static ArrayList<XYZCoordinates> FinalCoordinateList= CoordinatesSeqMap.CoordinateList;
    static ArrayList<VoxelToBrick> FinalData=new ArrayList<VoxelToBrick>();

    int[][][] indexval=CoordinatesSeqMap.ivalue;
    int x,y,z,indexfront, indexback;
    String Tsequence="TTTTTTTT";

    public SaveFinalBoundarySequences(){
        ConvertToT();
        RemoveTVoxels();
        SetProtectors();
        SetBrickList();
        attachBoundaryBricks();

        //	for(int i=0;i<FinalData.size();i++)
        //		System.out.println(FinalData.get(i).voxel1+","+FinalData.get(i).voxel2+""+
        //				"d1"+FinalData.get(i).Domain1+"d2"+FinalData.get(i).Domain2+"d3"+FinalData.get(i).Domain3+
        //				"d4"+FinalData.get(i).Domain4);
    }


    public void ConvertToT(){
        for(int i=0;i<FinalCoordinateList.size();i++){
            x=FinalCoordinateList.get(i).x;
            y=FinalCoordinateList.get(i).y;
            z=FinalCoordinateList.get(i).z;
            if(z<MainFrame.depth-1){
                indexfront=indexval[x][y][z+1];
                if(FinalCoordinateList.get(i).isRemoved==true){
                    System.out.println("removed in converttot:"+x+""+y+""+z);
                    if(FinalCoordinateList.get(i).domain==34)
                        FinalCoordinateList.get(indexfront).Domain2=FinalCoordinateList.get(indexfront).Domain2.replace
                                (FinalCoordinateList.get(indexfront).Domain2, Tsequence);

                    else
                        FinalCoordinateList.get(indexfront).Domain3=FinalCoordinateList.get(indexfront).Domain3.replace
                                (FinalCoordinateList.get(indexfront).Domain3, Tsequence);
                }
            }
            if(z>0){
                indexback=indexval[x][y][z-1];
                if(FinalCoordinateList.get(i).isRemoved==true){
                    System.out.println("removed in converttot:"+x+""+y+""+z);
                    if(FinalCoordinateList.get(i).domain==34)
                        FinalCoordinateList.get(indexback).Domain1=FinalCoordinateList.
                                get(indexback).Domain1.replace(FinalCoordinateList.get(indexback).Domain1, Tsequence);

                    else
                        FinalCoordinateList.get(indexback).Domain4=FinalCoordinateList.
                                get(indexback).Domain4.replace(FinalCoordinateList.get(indexback).Domain4, Tsequence);
                }
            }
        }
    }

    public void RemoveTVoxels(){
        //this function removes those voxels which have d3d4 OR d1&d2 as Tsequences
        for(int i=0;i<FinalCoordinateList.size();i++){

            x=FinalCoordinateList.get(i).x;
            y=FinalCoordinateList.get(i).y;
            z=FinalCoordinateList.get(i).z;

            if(FinalCoordinateList.get(i).isRemoved==false){
                if(FinalCoordinateList.get(i).domain==34){
                    if(FinalCoordinateList.get(i).Domain3.equals(Tsequence)&&
                            FinalCoordinateList.get(i).Domain4.equals(Tsequence)){
                        System.out.println("removed in removeTVoxels:"+x+""+y+""+z);
                        FinalCoordinateList.get(i).isRemoved=true;
                    }
                }
                else if(FinalCoordinateList.get(i).domain==12){
                    if(FinalCoordinateList.get(i).Domain1.equals(Tsequence)&&
                            FinalCoordinateList.get(i).Domain2.equals(Tsequence)){
                        System.out.println("removed in removeTVoxels:"+x+""+y+""+z);
                        FinalCoordinateList.get(i).isRemoved=true;
                    }
                }
            }
        }
    }

    public void SetProtectors(){
        //head protectors
        int z=0;
        for(int i=0;i<MainFrame.width;i++)
            for(int j=0;j<MainFrame.height;j++){
                while(FinalCoordinateList.get(indexval[i][j][z]).isRemoved==true&&z<MainFrame.depth-1){
                    z++;
                }
                if(FinalCoordinateList.get(indexval[i][j][MainFrame.depth-1]).isRemoved==false){
                    if(FinalCoordinateList.get(indexval[i][j][z]).domain==34)
                        FinalCoordinateList.get(indexval[i][j][z]).Domain3=FinalCoordinateList.
                                get(indexval[i][j][z]).Domain3.replace
                                (FinalCoordinateList.get(indexval[i][j][z]).Domain3, Tsequence);

                    else if(FinalCoordinateList.get(indexval[i][j][z]).domain==12)
                        FinalCoordinateList.get(indexval[i][j][z]).Domain2=FinalCoordinateList.
                                get(indexval[i][j][z]).Domain2.replace
                                (FinalCoordinateList.get(indexval[i][j][z]).Domain2, Tsequence);

                    FinalCoordinateList.get(indexval[i][j][z]).isHeadBrick=true;
                }
            }

        //tail protectors
        z=MainFrame.depth-1;
        for(int i=MainFrame.width-1;i>=0;i--)
            for(int j=MainFrame.height-1;j>=0;j--){
                while(FinalCoordinateList.get(indexval[i][j][z]).isRemoved==true&&z>0){
                    z--;
                }
                if(FinalCoordinateList.get(indexval[i][j][z]).isRemoved==false){
                    if(FinalCoordinateList.get(indexval[i][j][z]).domain==34)
                        FinalCoordinateList.get(indexval[i][j][z]).Domain4=FinalCoordinateList.
                                get(indexval[i][j][z]).Domain4.replace
                                (FinalCoordinateList.get(indexval[i][j][z]).Domain4, Tsequence);

                    else if(FinalCoordinateList.get(indexval[i][j][z]).domain==12)
                        FinalCoordinateList.get(indexval[i][j][z]).Domain1=FinalCoordinateList.
                                get(indexval[i][j][z]).Domain1.replace
                                (FinalCoordinateList.get(indexval[i][j][z]).Domain1, Tsequence);
                }
            }

    }


    //this function combines voxels to form bricks of size=2 voxels
    public void SetBrickList(){
        int x,y,z;
        for(int i=0;i<FinalCoordinateList.size();i++){

            if(FinalCoordinateList.get(i).isRemoved==false){

                x=FinalCoordinateList.get(i).x;
                y=FinalCoordinateList.get(i).y;
                z=FinalCoordinateList.get(i).z;

                if(FinalCoordinateList.get(i).isHalfBrick){
                    if(FinalCoordinateList.get(i).domain==34)
                        FinalData.add(new VoxelToBrick(-1,-1,-1,x,y,z,null, null,
                                FinalCoordinateList.get(i).Domain3, FinalCoordinateList.get(i).Domain4,false));
                    else
                        FinalData.add(new VoxelToBrick(x,y,z,-1,-1,-1,FinalCoordinateList.get(i).Domain1,
                                FinalCoordinateList.get(i).Domain2, null, null,false));
                }
                else{
                    //setting constraints for north-bricks
                    if(FinalCoordinateList.get(i).orientation=='n'){
                        if(FinalCoordinateList.get(i).domain==34){
                            if(FinalCoordinateList.get(indexval[x][y+1][z]).isRemoved==false){
                                FinalData.add(new VoxelToBrick(x,y+1,z,x,y,z,
                                        FinalCoordinateList.get(indexval[x][y+1][z]).Domain1,
                                        FinalCoordinateList.get(indexval[x][y+1][z]).Domain2,
                                        FinalCoordinateList.get(i).Domain3, FinalCoordinateList.get(i).Domain4,false));

                                FinalCoordinateList.get(indexval[x][y+1][z]).isRemoved=true;
                            }
                            else{
                                FinalData.add(new VoxelToBrick(-1,-1,-1,x,y,z,null, null,
                                        FinalCoordinateList.get(i).Domain3, FinalCoordinateList.get(i).Domain4,false));
                            }
                        }
                        else{
                            FinalData.add(new VoxelToBrick(x,y,z,-1,-1,-1,FinalCoordinateList.get(i).Domain1,
                                    FinalCoordinateList.get(i).Domain2, null, null,false));
                        }
                    }

                    //setting constraints for west-bricks
                    if(FinalCoordinateList.get(i).orientation=='w'){
                        if(FinalCoordinateList.get(i).domain==34){
                            if(FinalCoordinateList.get(indexval[x+1][y][z]).isRemoved==false){
                                FinalData.add(new VoxelToBrick(x+1,y,z,x,y,z,
                                        FinalCoordinateList.get(indexval[x+1][y][z]).Domain1,
                                        FinalCoordinateList.get(indexval[x+1][y][z]).Domain2,
                                        FinalCoordinateList.get(i).Domain3, FinalCoordinateList.get(i).Domain4,false));

                                FinalCoordinateList.get(indexval[x+1][y][z]).isRemoved=true;
                            }
                            else{
                                FinalData.add(new VoxelToBrick(-1,-1,-1,x,y,z,null, null,
                                        FinalCoordinateList.get(i).Domain3, FinalCoordinateList.get(i).Domain4,false));
                            }
                        }
                        else{
                            FinalData.add(new VoxelToBrick(x,y,z,-1,-1,-1,FinalCoordinateList.get(i).Domain1,
                                    FinalCoordinateList.get(i).Domain2, null, null,false));
                        }
                    }

                    //setting constraints for south-bricks
                    if(FinalCoordinateList.get(i).orientation=='s'){
                        if(FinalCoordinateList.get(i).domain==12){
                            if(FinalCoordinateList.get(indexval[x][y+1][z]).isRemoved==false){
                                FinalData.add(new VoxelToBrick(x,y,z,x,y+1,z,
                                        FinalCoordinateList.get(i).Domain1, FinalCoordinateList.get(i).Domain2,
                                        FinalCoordinateList.get(indexval[x][y+1][z]).Domain3,
                                        FinalCoordinateList.get(indexval[x][y+1][z]).Domain4,false
                                ));

                                FinalCoordinateList.get(indexval[x][y+1][z]).isRemoved=true;
                            }
                            else{
                                FinalData.add(new VoxelToBrick(-1,-1,-1,x,y,z,null, null,
                                        FinalCoordinateList.get(i).Domain1, FinalCoordinateList.get(i).Domain2,false));
                            }
                        }
                        else{
                            FinalData.add(new VoxelToBrick(x,y,z,-1,-1,-1, null, null,FinalCoordinateList.get(i).Domain3,
                                    FinalCoordinateList.get(i).Domain4,false));
                        }
                    }
                    //setting constraints for east-bricks
                    if(FinalCoordinateList.get(i).orientation=='e'){
                        if(FinalCoordinateList.get(i).domain==12){
                            if(FinalCoordinateList.get(indexval[x+1][y][z]).isRemoved==false){
                                FinalData.add(new VoxelToBrick(x,y,z,x+1,y,z,
                                        FinalCoordinateList.get(i).Domain1, FinalCoordinateList.get(i).Domain2,
                                        FinalCoordinateList.get(indexval[x+1][y][z]).Domain3,
                                        FinalCoordinateList.get(indexval[x+1][y][z]).Domain4,false
                                ));

                                FinalCoordinateList.get(indexval[x+1][y][z]).isRemoved=true;
                            }
                            else{
                                FinalData.add(new VoxelToBrick(x,y,z,-1,-1,-1,null, null,
                                        FinalCoordinateList.get(i).Domain1, FinalCoordinateList.get(i).Domain2,false));
                            }
                        }
                        else{
                            FinalData.add(new VoxelToBrick(-1,-1,-1,x,y,z,null, null,FinalCoordinateList.get(i).Domain3,
                                    FinalCoordinateList.get(i).Domain4,false));
                        }
                    }
                }
                FinalCoordinateList.get(i).isRemoved=true;
            }

        }
    }

    public void attachBoundaryBricks(){
        System.out.println("calling boundary brick");
        int x,y,z;
        String d1,d2;
        for(int i=0;i<FinalData.size();i++){
            if((FinalData.get(i).voxel2[0]==-1||FinalData.get(i).voxel1[2]!=0)&&
                    (FinalData.get(i).isHeadProtector==false)){

                System.out.println("found boundary brick");
                if(FinalData.get(i).voxel2[0]==-1){
                    x=FinalData.get(i).voxel1[0];
                    y=FinalData.get(i).voxel1[1];
                    z=FinalData.get(i).voxel1[2];
                    d1=new String(FinalData.get(i).Domain1);
                    d2=new String(FinalData.get(i).Domain2);
                }

                else {
                    x=FinalData.get(i).voxel2[0];
                    y=FinalData.get(i).voxel2[1];
                    z=FinalData.get(i).voxel2[2];
                    d1=new String(FinalData.get(i).Domain3);
                    d2=new String(FinalData.get(i).Domain4);
                }

                for(int j=0;j<FinalData.size();j++){
                    if(FinalData.get(j).voxel1[0]==x&&FinalData.get(j).voxel1[1]==y&&FinalData.get(j).voxel1[2]==z-1) {
                        FinalData.get(j).addDomains(x, y, z, d1, d2);
                        FinalData.get(j).isBoundary=true;
                        FinalData.remove(i);
                        i--;
                    }

                    else if(FinalData.get(j).voxel2[0]==x&&FinalData.get(j).voxel2[1]==y&&FinalData.get(j).voxel2[2]==z-1) {
                        FinalData.get(j).addDomains(x, y, z, d1, d2);
                        FinalData.get(j).isBoundary=true;
                        FinalData.remove(i);
                        i--;
                    }
                }

            }
        }
    }

}
