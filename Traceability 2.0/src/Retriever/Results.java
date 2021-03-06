package Retriever;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Iterator;

//import javax.swing.text.html.HTMLDocument.Iterator;

import org.w3c.dom.stylesheets.LinkStyle;

public class Results {
    
//    private Link[] link;
	
	private HashMap<Integer,Token> tokenIndex = new HashMap<Integer,Token>();
	private HashMap<Integer, Document> docIndex = new HashMap<Integer, Document>();
	
	private HashMap<Integer, Vector<Link>> linkDocIndex = new HashMap<Integer, Vector<Link>>();
	private HashMap<Integer, Vector<Link>> linkTokenIndex = new HashMap<Integer, Vector<Link>>();
	
	
    private HashMap<Integer, Token> tokenIdTracker = new HashMap<Integer, Token>();
    private Vector<Document> docIdTracker = new Vector<Document>();
    private Vector<Link> links =  new Vector<Link>();
    int D;
 
    
    
    
    public Results()
    {
        
    }
    
    
    
    private Vector<Link> getTFIs(Document d){
    	Vector<Link> v = new Vector<Link>();
    	for(Link l:links){
    		if(l.getDocument == d){
    			v.add(l);
    		}
    	}
    	return v;
    }
    
    public void calculateD(){
    	D = docIdTracker.size();
    }
    
    
  
    
    
    
    private void tokenCalc(){
    	for(int i_token: tokenIdTracker.keySet()){
    		Token t = tokenIdTracker.get(i_token);
    		t.DFI = linkTokenIndex.get(t.id).size();
    		t.calculateIDF(docIndex.size());
    		for(Link l: linkTokenIndex.get(t.id)){
    			l.calculateWeight(t.IDF);
    		}
    	}
    }
    
    public void doAllCalculations(){
    	tokenCalc();
    	docCalc();
    	calculateThetas();
    	
    }
    
    private void docCalc(){
    	// Get all docs:
    	for(int i_doc: docIndex.keySet()){
    		Document d = docIndex.get(i_doc);
    		for(Link j_link: linkDocIndex.get(d.id)){
    			d.magnitude += Math.pow(j_link.getWeight(),2);
    		}
    		d.magnitude = Math.sqrt(d.magnitude);
    	}
    }
    
    private double dotProduct(Document a, Document b){
    	double value = 0;
    	Vector<Link> a_link = linkDocIndex.get(a.id);
    	Vector<Link> b_link = linkDocIndex.get(b.id);
    	
    	Vector<Link> token = new Vector<Link>();
    	
    	for(Link l_a: a_link){
    		
    		for(Link l_b: b_link){
    			if(l_a.getToken() == l_b.getToken()){
    				value += l_a.getWeight() * l_b.getWeight();
    				break;
    			}
    		}
    		return value;
    	}
    	
    	
    	
    	return 0;
    }
    
    private void calculateThetas(){
    	double q_mag = Math.sqrt(tokenIdTracker.size());
    	
    	int i = 0;
    	double c;
    	for(int docId: docIndex.keySet()){
    		c = 0;
    		for(Link l: linkDocIndex.get(docId)){
    			c+= l.getWeight();
    		}
    		
    		c = c/ (q_mag * docIndex.get(docId).getMagnitude());
    		c = Math.acos(c);
    		docIndex.get(docId).setTheta(c);
    	}
    }
    
 
    
//    public String[] getBestDocs(int max){
//    	String[] out = new String[max];
//    	
//    	
//    	
//    	return null;
//    }
//    
    public Document[] getBestDocs(double e){
    	Vector<Document> best = new Vector<Document>();
    	for(int docId: docIndex.keySet()){
    		Document d = docIndex.get(docId);
    		if(d.theta < e){
    			best.add(d);
    		}
    		
    	}
    	List<Document> docList = best;
    	Collections.sort(docList);
    	return (Document[]) docList.toArray();
    	
		
    	
    }
}


    