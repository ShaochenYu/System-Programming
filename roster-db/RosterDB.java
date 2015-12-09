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
			
			for (Leagues l : list_leagues) {
				if ( l.league_id == id ) {
					filterList.add(l);
				}
			}
			return filterList;
		}
		
		// add Players
		
		public void addPlayers(Players player) throws SQLException {
			
			StringBuffer sb = new StringBuffer();
			sb.append("call addPlayer");
			sb.append("(");
			sb.append(player.player_id).append(",");
			sb.append("'"+player.player_name+"'").append(",");
			sb.append("'"+player.height+"'").append(",");
			sb.append(player.weight).append(",");
			sb.append(player.team_id);
			sb.append(");");
			
			PreparedStatement preparedStatement = null;
			preparedStatement = conn.prepareStatement(sb.toString());
			preparedStatement.executeUpdate();
				
			getPlayers();
			getTeams();
			
		}
		
		// add Teams
		
		public void addTeams(Teams team) throws SQLException {
			
			StringBuffer sb = new StringBuffer();
			sb.append("call addTeam");
			sb.append("(");
			sb.append(team.team_id).append(",");
			sb.append("'"+team.team_name+"'").append(",");
			sb.append("'"+team.head_coach+"'").append(",");
			sb.append(team.number_of_players).append(",");
			sb.append(team.league_id);
			sb.append(");");
			
			PreparedStatement preparedStatement = null;
			preparedStatement = conn.prepareStatement(sb.toString());
			preparedStatement.executeUpdate();
				
			getTeams();
			getLeagues();
		}
		
		// add Leagues
		
		public void addLeagues(Leagues league) throws SQLException {
			
			StringBuffer sb = new StringBuffer();
			sb.append("call addLeague");
			sb.append("(");
			sb.append(league.league_id).append(",");
			sb.append("'"+league.league_name+"'").append(",");
			sb.append("'"+league.description+"'").append(",");
			sb.append(league.number_of_teams);
			sb.append(");");
			
			PreparedStatement preparedStatement = null;
			preparedStatement = conn.prepareStatement(sb.toString());
			preparedStatement.executeUpdate();
			
			getLeagues(); 
			
		}
		
		// update Players
		
		public void updatePlayers(int row, String columnName, Object data) throws SQLException {
		
			Players player = list_players.get(row);
			
			String sql = "update Players set " + columnName + " = ?  where player_id = ? ;";
			System.out.println(sql);
			PreparedStatement preparedStatement = null;
			//try {
				preparedStatement = conn.prepareStatement(sql);
				if (data instanceof String)
					preparedStatement.setString(1, (String) data);
				else if (data instanceof Integer)
					preparedStatement.setInt(1, (Integer) data);
				preparedStatement.setInt(2, player.player_id);
				
				preparedStatement.executeUpdate();
				
				// update list
				getPlayers();
				getTeams();
				
			
		}
		
		// update Teams
		public void updateTeams(int row, String columnName, Object data) throws SQLException {
		
			Teams team = list_teams.get(row);
			
			String sql = "update Teams set " + columnName + " = ?  where team_id = ? ;";
			System.out.println(sql);
			PreparedStatement preparedStatement = null;
			//try {
				preparedStatement = conn.prepareStatement(sql);
				if (data instanceof String)
					preparedStatement.setString(1, (String) data);
				else if (data instanceof Integer)
					preparedStatement.setInt(1, (Integer) data);
				preparedStatement.setInt(2, team.team_id);		
				preparedStatement.executeUpdate();
				
				// update list
				getTeams();
				getLeagues();
			
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