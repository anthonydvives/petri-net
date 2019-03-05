/* Author: Michael Karolewicz
 * Date: 12-7-2018
 * Project: Petri Net Project
 */
package petri_net;

import java.util.ArrayList;

public class State 
{

	private ArrayList<String> value;

	public State(ArrayList<String> state) 
	{
		value = state;
	}
	public ArrayList<String> getValue()
	{
		return value;
	}
	public boolean isGreaterThan(State otherState)
	{
		for(int i = 0; i < otherState.getValue().size(); i++)
		{
			if(otherState.getValue().get(i) == "w" && this.getValue().get(i) != "w")
			{
				return false;
			}
			else if (this.getValue().get(i) == "w" && otherState.getValue().get(i) == "w")
			{
				continue;
			}
			else if (this.getValue().get(i) == "w" && otherState.getValue().get(i) != "w")
			{
				continue;
			}
			else if(Integer.parseInt(otherState.getValue().get(i)) > Integer.parseInt(this.getValue().get(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean isEqualTo(State thisState, State otherState)
	{
	
		for(int i = 0; i < thisState.getValue().size(); i++)
		{
			if((thisState.getValue().get(i).equals((otherState.getValue().get(i)))))
			{
				if(i == thisState.getValue().size()-1)
				{
					return true;
				}
			}
			else
			{
				break;
			}
		}
		return false;
	
	
		/*
		for(int i = 0; i < otherState.getValue().size(); i++)
		{
			if(!(thisState.getValue().get(i) == (otherState.getValue().get(i))))
			{
				return false;
			}
		}
		return true;
		*/
	}
	
	public void printValue()
	{
		String s = "{";
		for(String val : value)
		{
			s += " " + val + " ";
		}
		s += "}";
		System.out.println(s);
	}
	
	public String returnValue()
    {
        String s = "{";
        for(String val : value)
        {
            s += " " + val + " ";
        }
        s += "}";
        return(s);
    }
}
