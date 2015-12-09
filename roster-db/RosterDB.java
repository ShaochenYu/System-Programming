import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RosterDB{
	
		private static String userName = "root";
		private static String password = "";
		private static String serverName = "localhost";
	    private static String dbname = "Roster";
		private static Connection conn;
		//private List<Movie> list;
		List<Players> list_players;
		List<Teams>	list_teams;
		List<Leagues> list_leagues;
		
		
		RosterDB(){
			
			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", password);
			try{
				conn = DriverManager.getConnection("jdbc:" + "mysql" + "://"
							+ serverName + "/"+dbname, connectionProps);
				System.out.println("Connected to database" + dbname); 
			} catch( SQLException e ){
			    System.out.println(e.toString());
			}
			
			try{
				getPlayers();
				getTeams();
				getLeagues();
			}catch( SQLException e){
				System.out.println(e.toString());
			}
		}
		
		
		// set players list into list_players
		public void getPlayers() throws SQLException {
			
			Statement stmt = null;
			String query = "select * "
					+ "from Players ";

			list_players = new ArrayList<Players>();
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					int id = rs.getInt("player_id");
					String name = rs.getString("player_name");
					String height = rs.getString("height");
					int weight = rs.getInt("weight");
					int team_id = rs.getInt("team_id");
					Players p = new Players(id, name, height, weight, team_id);
					list_players.add(p);
				}
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
			return ;
			
		}
		
		public void getTeams() throws SQLException {
			
			
			Statement stmt = null;
			String query = "select * "
					+ "from Teams ";

			list_teams = new ArrayList<Teams>();
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					int pi = rs.getInt("team_id");
					String name = rs.getString("team_name");
					String hc = rs.getString("head_coach");
					int w = rs.getInt("number_of_players");
					int ti = rs.getInt("league_id");
					Teams t = new Teams(pi, name, hc, w, ti);
					list_teams.add(t);
				}
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
			return ;
			
		}
		
		public void getLeagues() throws SQLException {
			
			Statement stmt = null;
			String query = "select * "
					+ "from Leagues ";

			list_leagues = new ArrayList<Leagues>();
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					int li = rs.getInt("league_id");
					String name = rs.getString("league_name");
					String ds = rs.getString("description");
					int nt = rs.getInt("number_of_teams");
					Leagues l = new Leagues(li, name, ds, nt);
					list_leagues.add(l);
				}
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
			return ;
			
		}
		// search players
		public List<Players> getPlayers(int id) {
			List<Players> filterList = new ArrayList<Players>();
			/*
			try {
				getPlayers();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			*/
			for (Players p : list_players) {
				if ( p.player_id == id) {
					filterList.add(p);
				}
			}
			return filterList;
		}
		// search teams
		public List<Teams> getTeams(int id) {
			List<Teams> filterList = new ArrayList<Teams>();
			/*
			try {
				getTeams();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			*/
			for (Teams t : list_teams) {
				if ( t.team_id == id) {
					filterList.add(t);
				}
			}
			return filterList;
		}
		// search leagues
		public List<Leagues> getLeagues(int id) {
			List<Leagues> filterList = new ArrayList<Leagues>();
			/*
			try {
				getLeagues();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			*/
			for (Leagues l : list_leagues) {
				if ( l.league_id == id ) {
					filterList.add(l);
				}
			}
			return filterList;
		}
		
		// add Players
		
		public void addPlayers(Players player) {
			String sql = "insert into Players values " + "(?, ?, ?, ?, ?); ";

			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, player.player_id);
				preparedStatement.setString(2, player.player_name);
				preparedStatement.setString(3, player.height);
				preparedStatement.setInt(4, player.weight);
				preparedStatement.setInt(5, player.team_id);
				preparedStatement.executeUpdate();
				
				//update list
				getPlayers();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			
			
		}
		
		// add Teams
		
		public void addTeams(Teams team) {
			String sql = "insert into Teams values " + "(?, ?, ?, ?, ?); ";

			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, team.team_id);
				preparedStatement.setString(2, team.team_name);
				preparedStatement.setString(3, team.head_coach);
				preparedStatement.setInt(4, team.number_of_players);
				preparedStatement.setInt(5, team.league_id);
				preparedStatement.executeUpdate();
				
				// update list
				getTeams();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			
			
		}
		
		// add Leagues
		
		public void addLeagues(Leagues league) {
			String sql = "insert into Leagues values " + "(?, ?, ?, ?); ";

			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, league.league_id);
				preparedStatement.setString(2, league.league_name);
				preparedStatement.setString(3, league.description);
				preparedStatement.setInt(4, league.number_of_teams);
				preparedStatement.executeUpdate();
				
				// update list
			
				getLeagues();
				
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			} 
			
		}
		
		// update Players
		
		public void updatePlayers(int row, String columnName, Object data) {
		
			Players player = list_players.get(row);
			//String title = movie.getTitle();
			//int year = movie.getYear();
			
			String sql = "update Players set " + columnName + " = ?  where player_id = ? ;";
			System.out.println(sql);
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = conn.prepareStatement(sql);
				if (data instanceof String)
					preparedStatement.setString(1, (String) data);
				else if (data instanceof Integer)
					preparedStatement.setInt(1, (Integer) data);
				preparedStatement.setInt(2, player.player_id);
				
				preparedStatement.executeUpdate();
				
				// update list
				getPlayers();
				
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			} 
			
		}
		
		// update Teams
		public void updateTeams(int row, String columnName, Object data) {
		
			Teams team = list_teams.get(row);
			
			String sql = "update Teams set " + columnName + " = ?  where team_id = ? ;";
			System.out.println(sql);
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = conn.prepareStatement(sql);
				if (data instanceof String)
					preparedStatement.setString(1, (String) data);
				else if (data instanceof Integer)
					preparedStatement.setInt(1, (Integer) data);
				preparedStatement.setInt(2, team.team_id);		
				preparedStatement.executeUpdate();
				
				// update list
				getTeams();
				
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			} 
			
		}
		
		// update Leagues
		public void updateLeagues(int row, String columnName, Object data) {
		
			Leagues league = list_leagues.get(row);
			
			String sql = "update Leagues set " + columnName + " = ?  where league_id = ? ;";
			System.out.println(sql);
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = conn.prepareStatement(sql);
				if (data instanceof String)
					preparedStatement.setString(1, (String) data);
				else if (data instanceof Integer)
					preparedStatement.setInt(1, (Integer) data);
				preparedStatement.setInt(2, league.league_id);
				
				preparedStatement.executeUpdate();
				
				//update list
				getLeagues();
				
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			} 
			
		}
		
}