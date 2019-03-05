/* Author: Michael Karolewicz
 * Date: 12-7-2018
 * Project: Petri Net Project
 */
package petri_net;

import java.util.ArrayList;

public class Tree 
{
	private ArrayList<Transition> transitions;
	private State initial;
	private ArrayList<State> states = new ArrayList<State>();
	
	
	
	private class Node
	{
		private State value;
		private boolean isRoot;
		private Node parent;
		private boolean old;

		public Node(State state)
		{
			value = state;
			old = false;
		}
		public State getValue()
		{
			return value;
		}
		public void setState(State state)
		{
			isRoot = false;
			value = state;
		}
		public void setRoot()
		{
			isRoot = true;
		}
		public void setParent(Node parent)
		{
			this.parent = parent;
		}
		public Node getParent()
		{
			return parent;
		}
		public void setValue(State state)
		{
			value = state;
		}
		public void changeToOld()
		{
			old = true;
		}
		public boolean getOld()
		{
			return old;
		}
	}

	private final Node root;
	private final Node buffer;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	
	public Tree(ArrayList<Transition> transitions, State initialState) 
	{	
		this.transitions = transitions;
		initial = initialState;
		Node node = new Node(initialState);
		node.setRoot();
		this.root = node;
		buffer = new Node(initialState);
		root.setParent(buffer);
		states.add(initialState);
		nodes.add(node);
		System.out.println("New");
	}
	
	public State getRoot()
	{
		return root.getValue();
	}
	
	
	public void run()
	{
		ArrayList<Node> nodesToAdd = new ArrayList<Node>();		
		for(Node node : nodes)
		{
			if(node.getOld() == false)
			{
				for(Transition transition : transitions)
				{
					if(transition.isFireable(node.value))
					{
						State testState = transition.createNewStateFromFire(node.value);
						if(contains(testState) == false)
						{
							Node testNode = new Node(testState);
							testNode.setParent(node);
							if(greaterThanOnPath(testNode) == true)
							{
								testState = greaterThanOnPathReplace(testNode);
								
							}
							if(contains(testState) == false)
							{
								testNode.setValue(testState);
								nodesToAdd.add(testNode);
								states.add(testState);
							}
						}
					}
				}
				node.changeToOld();
			}
			
		}
		if(nodesToAdd.size() != 0)
		{
			for(Node node : nodesToAdd)
			{
					nodes.add(node);
			}
			for(Node node : nodes)
			{
				node.value.printValue();
			}
			System.out.println("New pass");
			run();
		}
		else
		{
			System.out.print("Done");
			System.out.println("------------------------------------------------------------");
			for(Node node : nodes)
			{
				node.value.printValue();
			}
		}
	}
	
	private boolean greaterThanOnPath(Node inputNode)
	{
		if(inputNode.isRoot == false)
		{
			Node value = inputNode.getParent();
			while(value != buffer)
			{
				if(inputNode.value.isGreaterThan(value.value))
				{
					return true;
				}
				else
				{
					value = value.getParent();
				}
			}
			return false;
		}
		return false;
	}
	
	private State greaterThanOnPathReplace(Node inputNode)
	{
		if(inputNode.isRoot == false)
		{
			Node value = inputNode.getParent();
			while(value != buffer)
			{
				if(inputNode.value.isGreaterThan(value.value))
				{
					return replaceWithOmega(inputNode.value, value.value);
					
				}
				else
				{
					value = value.getParent();
				}
			}
			return buffer.value;
		}
		return buffer.value;
	}
	
	
	private State replaceWithOmega(State state1, State state2)
	{
		ArrayList<String> stateValues = new ArrayList<String>();
		for(int i = 0; i < state1.getValue().size(); i++)
		{
			
			if(state1.getValue().get(i).equals(state2.getValue().get(i)))
			{
				stateValues.add(state1.getValue().get(i));
			}				
			else
			{
				stateValues.add("w");
			}
		}
		return new State(stateValues);
	}
	
	
	public void printAll()
	{
		for(State state : states)
		{
			state.printValue();
		}
	}
	public boolean contains(State state)
	{
	
		for(State totalState : states)
		{
			if(State.isEqualTo(state, totalState) == true)
			{
				return true;
			}
		}
		return false;
	}
	public ArrayList<String> getResult()
    {
        ArrayList<String> result = new ArrayList<String>();
        for(State state : states)
        {
            result.add(state.returnValue());
        }
        return result;
    }
	
}
