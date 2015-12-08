
public class Leagues
{
	 int league_id, number_of_teams;
	 String league_name, description;
	
	
	public Leagues(int league_id, String league_name, String description, int number_of_teams)
	{
		this.league_id = league_id;
		this.league_name = league_name;
		this.description = description;
		this.number_of_teams = number_of_teams;
	}
	
	
	public String toString()
	{
		return "Leagues: league_id=" + league_id;
	}
}
