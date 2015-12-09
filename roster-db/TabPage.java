import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;

import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.util.*;
import java.sql.SQLException;

public class TabPage implements ActionListener, TableModelListener {
	
	
	private int type = 0;
	private JPanel parentPanel;
	
	private JButton btnList, btnSearch, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private RosterDB rdb;
	//private List<Players> list;
	private ArrayList<String> columnNames = new ArrayList<String>() ;
	// list panel
	private Object[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	//search panel
    private JPanel pnlSearch;
	private JLabel lblTitle;;
	private JTextField txfTitle;
	private JButton btnTitleSearch;
	// add panel
	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[5];
	private JTextField[] txfField = new JTextField[5];
	private JButton btnAddItem;
	
	public TabPage(JPanel card, RosterDB rdb, int type ){
		parentPanel = card;
		this.rdb = rdb;
		this.type = type;
		if( type == 1 )	{
			//list = rdb.list_players;
			columnNames.add("player_id");
			columnNames.add("player_name");
			columnNames.add("height");
			columnNames.add("weight");
			columnNames.add("team_id");
		}else{ 
			if (type == 2) {
				
				columnNames.add("team_id");
				columnNames.add("team_name");
				columnNames.add("head_coach");
				columnNames.add("number_of_players");
				columnNames.add("leagu_id");
			}else{
					
					columnNames.add("league_id");
					columnNames.add("league_name");
					columnNames.add("description");
					columnNames.add("number_of_teams");
			}
		}
		
		setData();
		createComponents();
	}
	
	
	private void setData(){
		if( type == 1 ){
			List<Players> list = rdb.list_players;
			
			data = new Object[list.size()][columnNames.size()];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).player_id;
				data[i][1] = list.get(i).player_name;
				data[i][2] = list.get(i).height;
				data[i][3] = list.get(i).weight;
				data[i][4] = list.get(i).team_id;
				
			}
			
		}else
			if( type == 2 ){
				
				List<Teams> list = rdb.list_teams;
				data = new Object[list.size()][columnNames.size()];
				for (int i=0; i<list.size(); i++) {
					data[i][0] = list.get(i).team_id;
					data[i][1] = list.get(i).team_name;
					data[i][2] = list.get(i).head_coach;
					data[i][3] = list.get(i).number_of_players;
					data[i][4] = list.get(i).league_id;
				}
			
			}else{
				
				List<Leagues> list = rdb.list_leagues;
				data = new Object[list.size()][columnNames.size()];
				for (int i=0; i<list.size(); i++) {
					data[i][0] = list.get(i).league_id;
					data[i][1] = list.get(i).league_name;
					data[i][2] = list.get(i).description;
					data[i][3] = list.get(i).number_of_teams;
					
				}
			
			}
		
	}
	
	private void setData(int id){
		if( type == 1 ){
			List<Players> list = rdb.getPlayers(id);
			
			data = new Object[list.size()][columnNames.size()];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).player_id;
				data[i][1] = list.get(i).player_name;
				data[i][2] = list.get(i).height;
				data[i][3] = list.get(i).weight;
				data[i][4] = list.get(i).team_id;
				
			}
			
		}else
			if( type == 2 ){
				
				List<Teams> list = rdb.getTeams(id);
				data = new Object[list.size()][columnNames.size()];
				for (int i=0; i<list.size(); i++) {
					data[i][0] = list.get(i).team_id;
					data[i][1] = list.get(i).team_name;
					data[i][2] = list.get(i).head_coach;
					data[i][3] = list.get(i).number_of_players;
					data[i][4] = list.get(i).league_id;
				}
			
			}else{
				
				List<Leagues> list = rdb.getLeagues(id);
				data = new Object[list.size()][columnNames.size()];
				for (int i=0; i<list.size(); i++) {
					data[i][0] = list.get(i).league_id;
					data[i][1] = list.get(i).league_name;
					data[i][2] = list.get(i).description;
					data[i][3] = list.get(i).number_of_teams;
					//data[i][4] = list.get(i).league_id;
				}
			
			}
		
	}


	private void createComponents()
	{       
        // add buttons area
		pnlButtons = new JPanel();
		
		String name ;
		if( type == 1 )	name = "Player";
		else
			if( type == 2 )	name = "Team";
			else			name = "League";
		
		btnList = new JButton("List " + name);
		btnList.addActionListener(this);
		
		btnSearch = new JButton("Search " + name);
		btnSearch.addActionListener(this);
		
		btnAdd = new JButton("Add " + name);
		btnAdd.addActionListener(this);
		
		pnlButtons.add(btnList);
		pnlButtons.add(btnSearch);
		pnlButtons.add(btnAdd);
		//parentPanel.setLayout();
		parentPanel.setLayout(new BoxLayout(parentPanel,BoxLayout.X_AXIS));
		parentPanel.add(pnlButtons);
		
		//List Panel
		pnlContent = new JPanel();
		table = new JTable(data, columnNames.toArray());
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		table.getModel().addTableModelListener(this);
		
		//Search Panel
		pnlSearch = new JPanel();
		lblTitle = new JLabel("Enter id: ");
		txfTitle = new JTextField(25);
		btnTitleSearch = new JButton("Search");
		btnTitleSearch.addActionListener(this);
		pnlSearch.add(lblTitle);
		pnlSearch.add(txfTitle);
		pnlSearch.add(btnTitleSearch);
		
		//Add Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(6, 0));
		ArrayList<String> labelNames = new ArrayList<String>();
		labelNames.add("Enter id");
		labelNames.add("Enter name");
		
		if( type == 1 ){
				labelNames.add("Enter height");
				labelNames.add("Enter weight");
				labelNames.add("Enter team id");
		}else{
			if( type == 2 ){
				labelNames.add("Enter head coach");
				labelNames.add("Enter # of players");
				labelNames.add("Enter league id");
			}else{
				labelNames.add("Enter description");
				labelNames.add("Enter # of teams");
			}
		}	
		
		for (int i=0; i<labelNames.size(); i++) {
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(labelNames.get(i));
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddItem = new JButton("Add");
		btnAddItem.addActionListener(this);
		panel.add(btnAddItem);
		pnlAdd.add(panel);
		
		parentPanel.add(pnlContent);
		
	}
	// click event
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnList) {
			
			setData();
			
			pnlContent.removeAll();
			table = new JTable(data, columnNames.toArray());
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			//System.out.println("repaint");
			pnlContent.repaint();
			
		} else if (e.getSource() == btnSearch) {
			pnlContent.removeAll();
			pnlContent.add(pnlSearch);
			pnlContent.revalidate();
			pnlContent.repaint();
			
		} else if (e.getSource() == btnAdd) {
			pnlContent.removeAll();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			pnlContent.repaint();
			
		} else if (e.getSource() == btnTitleSearch) {
			String title = txfTitle.getText();
			if (title.length() > 0) {
				
				setData(Integer.parseInt(title));
				
				pnlContent.removeAll();
				table = new JTable(data, columnNames.toArray());
				table.getModel().addTableModelListener(this);
				scrollPane = new JScrollPane(table);
				pnlContent.add(scrollPane);
				pnlContent.revalidate();
				pnlContent.repaint();
				txfTitle.setText("");
			}
		} else if (e.getSource() == btnAddItem) {
			
			try{
			if( type == 1 ){
				Players p = new Players( Integer.parseInt( txfField[0].getText() ), txfField[1].getText(), txfField[2].getText(), Integer.parseInt( txfField[3].getText() ), Integer.parseInt( txfField[4].getText() ));
				rdb.addPlayers(p);
			}
			
			if( type == 2 ){
				Teams p = new Teams( Integer.parseInt( txfField[0].getText() ), txfField[1].getText(), txfField[2].getText(), Integer.parseInt( txfField[3].getText() ), Integer.parseInt( txfField[4].getText() ));
				rdb.addTeams(p);
			}
			
			if( type == 3 ){
				Leagues l = new Leagues( Integer.parseInt( txfField[0].getText() ), txfField[1].getText(), txfField[2].getText(), Integer.parseInt( txfField[3].getText() ) );
				rdb.addLeagues(l);
			}
			
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			} catch( SQLException se){
				se.printStackTrace();
				JOptionPane.showMessageDialog(null, "Operation is against DB constraints!");
			}
			
			for (int i=0; i<txfField.length; i++) {
				txfField[i].setText("");
			}
		}
		
	}
	
	public void tableChanged(TableModelEvent e) {
		
		int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
		try{
		if( type == 1 ){
				rdb.updatePlayers(row, columnName, data);
				rdb.getPlayers();
		}
		if( type == 2 ){ 
				rdb.updateTeams(row, columnName, data);
				rdb.getTeams();
		}
		if( type == 3 ){
				rdb.updateLeagues(row, columnName, data);
				rdb.getLeagues();
		}
		JOptionPane.showMessageDialog(null, "Modified sucessfully!");
		
		} catch ( SQLException se ){
			se.printStackTrace();
			JOptionPane.showMessageDialog(null, "Operation is against DB constraints!");
		}
		
	}
	
}