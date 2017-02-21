/* Author: Amay Agrawal, Birva Patel
 * Project: 3DNA Printer
 * Mentor: Prof. Manish K Gupta
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class EstimatorActionListener implements ActionListener {
	static int totalbases;
	
	public EstimatorActionListener(int a){
		totalbases = a;
	}
	
	public EstimatorActionListener(){
		
	}
	
    public CoordinatesSeqMap c;
    static ArrayList<VoxelToBrick> DNASequenceData=SaveFinalSequences.FinalData;
    @Override
    public void actionPerformed(ActionEvent e) {
        c=new CoordinatesSeqMap();
        String fn=JOptionPane.showInputDialog(null, "Enter cost for a base (USD): ", "3DNA Printer Cost Estimator", JOptionPane.QUESTION_MESSAGE)  ;
        float n1=Float.parseFloat(fn);
        int domainCount=0;
        float totalcost=0;

        for(int i=0;i<DNASequenceData.size();i++){
            if(DNASequenceData.get(i).Domain1!=null)
                domainCount++;
            if(DNASequenceData.get(i).Domain2!=null)
                domainCount++;
            if(DNASequenceData.get(i).Domain3!=null)
                domainCount++;
            if(DNASequenceData.get(i).Domain4!=null)
                domainCount++;
            if(DNASequenceData.get(i).Domain5!=null)
                domainCount++;
            if(DNASequenceData.get(i).Domain6!=null)
                domainCount++;

        }
        totalcost=totalbases*n1;
        JOptionPane.showMessageDialog(null, "Total cost (USD) for "+
                totalbases+" bases :"+totalcost, "3DNA Printer Cost Estimator", JOptionPane.INFORMATION_MESSAGE );


    }

}