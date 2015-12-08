
public class Teams
{
	 int team_id, number_of_players, league_id;
	 String team_name, head_coach;
	
	
	public Teams(int team_id, String team_name, String head_coach, int number_of_players,
			int league_id)
	{
		this.team_id = team_id;
		this.team_name = team_name;
		this.head_coach = head_coach;
		this.number_of_players = number_of_players;
		this.league_id = league_id;
	}
	
	
	public String toString()
	{
		return "Teams: team_id=" + team_id;
	}
}
