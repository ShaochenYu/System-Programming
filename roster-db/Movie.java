
public class Movie
{
	private String title;
	private int year;
	private int length;
	private String genre, studioName;
	
	
	public Movie(String title, int year, int length, String genre,
			String studioName)
	{
		this.title = title;
		this.year = year;
		this.length = length;
		this.genre = genre;
		this.studioName = studioName;
	}
	
	@Override
	public String toString()
	{
		return "Movie [title=" + title + ", year=" + year + ", length="
				+ length + ", genre=" + genre + ", studioName=" + studioName
				+ "]";
	}

	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public int getYear()
	{
		return year;
	}
	public void setYear(int year)
	{
		this.year = year;
	}
	public int getLength()
	{
		return length;
	}
	public void setLength(int length)
	{
		this.length = length;
	}
	public String getGenre()
	{
		return genre;
	}
	public void setGenre(String genre)
	{
		this.genre = genre;
	}
	public String getStudioName()
	{
		return studioName;
	}
	public void setStudioName(String studioName)
	{
		this.studioName = studioName;
	}
	
	
}
