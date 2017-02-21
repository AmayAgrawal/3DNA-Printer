/* Author: Amay Agrawal, Birva Patel
 * Project: 3DNA Printer
 * Mentor: Prof. Manish K Gupta
 */
public class DNASequence {

	public String DNASequence;
	public boolean isUsed;
	
	public DNASequence(String seq){
		this.DNASequence=seq;
		this.isUsed=false;
	}
	public String getSequence(){
		this.isUsed=true;
		return(DNASequence);
		
	}
}
