

package main;

import java.io.*;

class DecisionTree {

    private class BinTree {
	
	private int     nodeID;
    	private String  questOrAns = null;
    	private BinTree yesBranch  = null;
    	private BinTree noBranch   = null;
	
	public BinTree(int newNodeID, String newQuestAns) {
	    nodeID     = newNodeID;
	    questOrAns = newQuestAns;
            }
	}

    static BufferedReader    keyboardInput = new
                           BufferedReader(new InputStreamReader(System.in));
    BinTree rootNode = null;

    public DecisionTree() {
	}

    public void createRoot(int newNodeID, String newQuestAns) {
	rootNode = new BinTree(newNodeID,newQuestAns);	
	System.out.println("Created root node " + newNodeID);	
	}

    public void addYesNode(int existingNodeID, int newNodeID, String newQuestAns) {
	
	if (rootNode == null) {
	    System.out.println("ERROR: No root node!");
	    return;
	    }
	
	if (searchTreeAndAddYesNode(rootNode,existingNodeID,newNodeID,newQuestAns)) {
	    System.out.println("Added node " + newNodeID +
	    		" onto \"yes\" branch of node " + existingNodeID);
	    }
	else System.out.println("Node " + existingNodeID + " not found");
	}

    private boolean searchTreeAndAddYesNode(BinTree currentNode,
    			int existingNodeID, int newNodeID, String newQuestAns) {
    	if (currentNode.nodeID == existingNodeID) {
    		
	    if (currentNode.yesBranch == null) currentNode.yesBranch = new
	    		BinTree(newNodeID,newQuestAns);
	    else {
	        System.out.println("WARNING: Overwriting previous node " +
			"(id = " + currentNode.yesBranch.nodeID +
			") linked to yes branch of node " +
			existingNodeID);
		currentNode.yesBranch = new BinTree(newNodeID,newQuestAns);
		}		
    	    return(true);
	    }
	else {
	    if (currentNode.yesBranch != null) { 	
	        if (searchTreeAndAddYesNode(currentNode.yesBranch,
		        	existingNodeID,newNodeID,newQuestAns)) {    	
	            return(true);
		    }	
		else {
	    	    if (currentNode.noBranch != null) {
    	    		return(searchTreeAndAddYesNode(currentNode.noBranch,
				existingNodeID,newNodeID,newQuestAns));
			}
		    else return(false);
		    }
    		}
	    return(false);		
	    }
   	} 	

    public void addNoNode(int existingNodeID, int newNodeID, String newQuestAns) {
	
	if (rootNode == null) {
	    System.out.println("ERROR: No root node!");
	    return;
	    }
	
	if (searchTreeAndAddNoNode(rootNode,existingNodeID,newNodeID,newQuestAns)) {
	    System.out.println("Added node " + newNodeID +
	    		" onto \"no\" branch of node " + existingNodeID);
	    }
	else System.out.println("Node " + existingNodeID + " not found");
	}

    private boolean searchTreeAndAddNoNode(BinTree currentNode,
    			int existingNodeID, int newNodeID, String newQuestAns) {
    	if (currentNode.nodeID == existingNodeID) {
    		
	    if (currentNode.noBranch == null) currentNode.noBranch = new
	    		BinTree(newNodeID,newQuestAns);
	    else {
	        System.out.println("WARNING: Overwriting previous node " +
			"(id = " + currentNode.noBranch.nodeID +
			") linked to yes branch of node " +
			existingNodeID);
		currentNode.noBranch = new BinTree(newNodeID,newQuestAns);
		}		
    	    return(true);
	    }
	else {

	    if (currentNode.yesBranch != null) { 	
	        if (searchTreeAndAddNoNode(currentNode.yesBranch,
		        	existingNodeID,newNodeID,newQuestAns)) {    	
	            return(true);
		    }	
		else {

	    	    if (currentNode.noBranch != null) {
    	    		return(searchTreeAndAddNoNode(currentNode.noBranch,
				existingNodeID,newNodeID,newQuestAns));
			}
		    else return(false);	
		    }
		 }
	    else return(false);	
	    }
   	} 	

    public void queryBinTree() throws IOException {
        queryBinTree(rootNode);
        }

    private void queryBinTree(BinTree currentNode) throws IOException {

        if (currentNode.yesBranch==null) {
            if (currentNode.noBranch==null) System.out.println(currentNode.questOrAns);
            else System.out.println("Error: Missing \"Yes\" branch at \"" +
            		currentNode.questOrAns + "\" question");
            return;
            }
        if (currentNode.noBranch==null) {
            System.out.println("Error: Missing \"No\" branch at \"" +
            		currentNode.questOrAns + "\" question");
            return;
            }

        askQuestion(currentNode);
        }

    private void askQuestion(BinTree currentNode) throws IOException {
        System.out.println(currentNode.questOrAns + " (enter \"Yes\" or \"No\")");
        String answer = keyboardInput.readLine();
        if (answer.equals("Yes")) queryBinTree(currentNode.yesBranch);
        else {
            if (answer.equals("No")) queryBinTree(currentNode.noBranch);
            else {
                System.out.println("ERROR: Must answer \"Yes\" or \"No\"");
                askQuestion(currentNode);
                }
            }
        }

    public void outputBinTree() {

        outputBinTree("1",rootNode);
        }

    private void outputBinTree(String tag, BinTree currentNode) {

        if (currentNode == null) return;

        System.out.println("[" + tag + "] nodeID = " + currentNode.nodeID +
        		", question/answer = " + currentNode.questOrAns);

        outputBinTree(tag + ".1",currentNode.yesBranch);

        outputBinTree(tag + ".2",currentNode.noBranch);
	}      		
    }