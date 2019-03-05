/* Author: Michael Karolewicz
 * Date: 12-7-2018
 * Project: Petri Net Project
 */
package petri_net;

import java.util.ArrayList;

public class Transition 
{

	private ArrayList<Integer> inputs;
	private ArrayList<Integer> outputs;
	
	public Transition(ArrayList<Integer> inputs, ArrayList<Integer> outputs)
	{
		this.inputs = inputs;
		this.outputs = outputs;
	}
	public boolean isFireable(State state)
	{
		for(int i = 0; i < inputs.size(); i++)
		{
			if(state.getValue().get(i) == "w")
			{
				continue;
			}
			else if(Integer.parseInt(state.getValue().get(i)) < inputs.get(i))
			{
				return false;
			}
		}
		return true;
	}
	
	public State createNewStateFromFire(State state)
	{
		ArrayList<String> newStateList = new ArrayList<String>(); 
		for(int i = 0; i < inputs.size(); i++)
		{
			String placeVal;
			if(state.getValue().get(i) == "w")
			{
				placeVal = "w";
				newStateList.add(placeVal);
			}
			else
			{
				placeVal = Integer.toString(Integer.parseInt(state.getValue().get(i)) - inputs.get(i));
				newStateList.add(placeVal);
			}
		}
		State newState1 = new State(newStateList);
		ArrayList<String> newStateList2 = new ArrayList<String>(); 

		for(int i = 0; i < outputs.size(); i++)
		{
			String placeVal;
			if(newState1.getValue().get(i) == "w")
			{
				placeVal = "w";
				newStateList2.add(placeVal);
			}
			else
			{
				placeVal = Integer.toString(Integer.parseInt(newState1.getValue().get(i)) + outputs.get(i));
				newStateList2.add(placeVal);
			}
		}
		State newState2 = new State(newStateList2);
		return newState2;
	}
	/*
	public State fire(State state)
	{
		if(isFireable(state))
		{
			return createNewStateFromFire(state);
		}
	}

*/
}
