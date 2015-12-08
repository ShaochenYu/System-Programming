
public class Players
{
	 int player_id, weight, team_id;
	 String player_name, height;
	
	
	public Players(int player_id, String player_name, String height, int weight,
			int team_id)
	{
		this.player_id = player_id;
		this.player_name = player_name;
		this.height = height;
		this.weight = weight;
		this.team_id = team_id;
	}
	
	
	public String toString()
	{
		return "Players: [player_id=" + player_id + ", player_name=" + player_name + ", height="
				+ height + ", weight=" + weight + ", team_id=" + team_id
				+ "]";
	}
}
