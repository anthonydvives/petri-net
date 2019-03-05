/* Author: Anthony Vives & Jimmy Duong
 * Date: 12-7-2018
 * Project: Petri Net Project
 */
package graphical_user_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import petri_net.State;
import petri_net.Transition;
import petri_net.Tree;

import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GUI {

	private JFrame frame;
	private JTextField textNumPlace;
	private JTextField textNumTransition;
	// Recorded Values
	private int tCount = 1;
	private int pCount = 1;
	private JTable tableMarking;
	private JTable tableInput;
	private JTable tableOutput;
	// Petri Net
	

	public GUI() {
		initialize();
	}
	public JFrame getFrame() {
		return frame;
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 465, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNumPlace = new JLabel("No. Place(s)");
		
		JLabel lblNumTransition = new JLabel("No. Transition(s)");
		
		JScrollPane scrollPaneMarking = new JScrollPane();
		JScrollPane scrollPaneInput = new JScrollPane();
		JScrollPane scrollPaneOutput = new JScrollPane();
		
		textNumPlace = new JTextField();
		textNumPlace.setColumns(10);
		
		textNumTransition = new JTextField();
		textNumTransition.setColumns(10);
		
		JButton btnGenerate = new JButton("Generate Table");
		JButton btnReset = new JButton("Reset");
		JButton btnWrite = new JButton("Write to File");
		
		// Generate Action Listener
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check if Text Field Values are Positive Integers
				if((textNumPlace.getText().matches("[0-9]+") == false || textNumPlace.getText().toString().contains(".")) || 
					(textNumTransition.getText().matches("[0-9]+") == false || textNumTransition.getText().toString().contains(".")) ) {
					JOptionPane.showMessageDialog(null, "Invalid Input, Only Positive Integers");
					return;
				}
				if(Integer.parseInt(textNumPlace.getText().toString()) < 0 || Integer.parseInt(textNumTransition.getText().toString()) < 0) {
					JOptionPane.showMessageDialog(null, "Invalid Input, Only Positive Integers");
					return;
				}
				// Check if Text Field Values are Empty
				else if(textNumPlace.getText().equals("") || textNumTransition.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Empty Place or Transition Input ");
					return;
				}

				// Text Fields
				pCount = Integer.parseInt(textNumPlace.getText());
				tCount = Integer.parseInt(textNumTransition.getText());
				// Scroll Panes
				scrollPaneMarking.setVisible(true);
				scrollPaneInput.setVisible(true);
				scrollPaneOutput.setVisible(true);
				tableOutput.setModel(getOutputModel());
				tableInput.setModel(getInputModel());
				tableMarking.setModel(getMarkingModel());
				// Buttons
				btnReset.setVisible(true);
				btnGenerate.setVisible(false);
				btnWrite.setVisible(true);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
		});
		// Reset Action Listener
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Text Fields
				textNumPlace.setText("");
				textNumTransition.setText("");
				// Scroll Pane
				scrollPaneMarking.setVisible(false);
				scrollPaneInput.setVisible(false);
				scrollPaneOutput.setVisible(false);
				// Buttons
				btnGenerate.setVisible(true);
				btnReset.setVisible(false);
				btnWrite.setVisible(false);
				// Variables
				pCount = 1;
				tCount = 1;
				
				tableOutput.setModel(getOutputModel());
				tableInput.setModel(getInputModel());
				tableMarking.setModel(getMarkingModel());
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
		});
		// Write Action Listener
		btnWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> input = new ArrayList<Integer>();
				ArrayList<Integer> output = new ArrayList<Integer>();
				ArrayList<Transition> transitions = new ArrayList<Transition>();
				State state;
				
				if (!checkTable(tableInput)) {
					return;
				}
				else if (!checkTable(tableOutput)) {
					return;
				}
				else if (!checkTable(tableMarking)) {
					return;
				}
				for(int i = 0; i < tableInput.getModel().getColumnCount(); i++) {
					Transition value;
					input = getTableIO(i,tableMarking.getModel().getColumnCount(), tableInput);
					output = getTableIO(i,tableMarking.getModel().getColumnCount(), tableOutput);
					value = new Transition(input, output);
					transitions.add(value);
				}
				
				ArrayList<String> markings = getTableMarking(tableMarking.getModel().getColumnCount(), tableMarking);
				state = new State(markings);
				Tree tree = new Tree(transitions, state);
				tree.run();
				try {
					PrintWriter printer = new PrintWriter("output.txt");
					ArrayList<String> results = tree.getResult();
					for(String result : results) {
						printer.println(result);
					}
					printer.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "File saved to program directory");
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneInput, 0, 0, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNumTransition, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(textNumTransition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNumPlace, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(textNumPlace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnGenerate, GroupLayout.PREFERRED_SIZE, 88, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnWrite, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
								.addComponent(btnReset, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)))
						.addComponent(scrollPaneOutput, 0, 0, Short.MAX_VALUE)
						.addComponent(scrollPaneMarking, 0, 0, Short.MAX_VALUE))
					.addGap(13))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumPlace, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(textNumPlace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnReset))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumTransition, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(textNumTransition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnGenerate)
						.addComponent(btnWrite))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneInput, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(scrollPaneOutput, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneMarking, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(40))
		);
		
		tableOutput = new JTable();
		tableOutput.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPaneOutput.setViewportView(tableOutput);
	
		tableInput = new JTable();
		tableInput.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPaneInput.setViewportView(tableInput);
		
		tableMarking = new JTable();
		tableMarking.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPaneMarking.setViewportView(tableMarking);
		
		// Visibility Defaults
		scrollPaneOutput.setVisible(false);
		scrollPaneInput.setVisible(false);
		scrollPaneMarking.setVisible(false);
		btnReset.setVisible(false);
		btnWrite.setVisible(false);
		
		frame.getContentPane().setLayout(groupLayout);
	}
	public void print(ArrayList<String> list) {
		for(int i = 0; i < tCount; i++) {
			System.out.println(list.get(i));
		}
	}
	public ArrayList<Integer> getTableIO(int column, int size, JTable table) {
		ArrayList<Integer> values = new ArrayList<Integer>();
		for(int i = 0; i < size; i++) {
			int input = Integer.parseInt(table.getModel().getValueAt(i, column).toString());
			values.add(input);
		}
		return values;
	}
	public ArrayList<String> getTableMarking(int size, JTable table) {
		ArrayList<String> values = new ArrayList<String>();
		for(int i = 0; i < size; i++) {
			String input = table.getModel().getValueAt(0, i).toString();
			values.add(input);
		}
		return values;
	}
	public DefaultTableModel getInputModel() {
		Object[][] cells = setTableDefault(pCount, tCount);
		String [] cHeader = new String [tCount];
		for(int i = 0; i < tCount; i++) {
			cHeader[i] = "I(t" + (i + 1) + ")";
		}
		DefaultTableModel model = new DefaultTableModel(cells, cHeader);
		return model;
	}
	public DefaultTableModel getOutputModel() {
		Object[][] cells = setTableDefault(pCount, tCount);
		for(int i = 0; i < pCount; i++) {
			for(int j = 0; j < tCount; j++) {
				cells[i][j] = "0";
			}
		}
		String [] cHeader = new String [tCount];
		for(int i = 0; i < tCount; i++) {
			cHeader[i] = "O(t" + (i + 1) + ")";
		}
		DefaultTableModel model = new DefaultTableModel(cells, cHeader);
		return model;
	}
	public DefaultTableModel getMarkingModel() {
		Object[][] cells = setTableDefault(1, pCount);
		for(int i = 0; i < pCount; i ++) {
			cells[0][i] = "0"; 
		}
		String [] cHeader = new String [pCount];
		for(int i = 0; i < pCount; i++) {
			cHeader[i] = "p" + (i + 1);
		}
		DefaultTableModel model = new DefaultTableModel(cells, cHeader);
		return model;
	}
	public Object [][] setTableDefault(int rowCount, int columnCount){
		Object [][] cells = new Object [rowCount][columnCount];
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0; j < columnCount; j++) {
				cells[i][j] = "0";
			}
		}
		return cells;
	}
	public boolean checkTable(JTable table) {
		for(int i = 0; i < table.getModel().getRowCount(); i++) {
			for(int j = 0; j < table.getModel().getColumnCount(); j++) {
				if(table.getModel().getValueAt(i, j).toString().matches("[0-9]+") == false) {
					JOptionPane.showMessageDialog(null, "Invalid Table Input, Only Positive Integers.");
					return false;
				}
			}
		}
		return true;
	}
	
}
