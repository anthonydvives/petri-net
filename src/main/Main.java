/* Author: Anthony Vives

 * Date: 12-7-2018
 * Project: Petri Net Project
 */
package main;

import java.awt.EventQueue;

import graphical_user_interface.GUI;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
