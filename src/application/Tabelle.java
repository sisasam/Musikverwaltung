package application;

public class Tabelle 
{
	
	private int nr;
    private String titel;
    private String interpret;
    private String genre;
    private String path;

    public Tabelle()
    {
    	nr = 0;
        this.titel = "";
        this.interpret = "";
        this.genre = "";
    }

    public Tabelle(String titel, String interpret, String genre)
    {
        this.titel = titel;
        this.interpret = interpret;
        this.genre = genre;
        this.nr = nr++;
    }
    
    public Tabelle(String[] infos)
    {
    	this.titel = infos[0];
      this.interpret = infos[1];
      this.genre = infos[2];
      this.setPath(infos[3]);
      this.nr = nr++;
    }

    public String getTitel() 
    {
        return titel;
    }

    public void setTitel(String titel) 
    {
        this.titel = titel;
    }

    public String getInterpret() 
    {
        return interpret;
    }

    public void setInterpret(String interpret) 
    {
        this.interpret = interpret;
    }

    public String getGenre() 
    {
        return genre;
    }

    public void setGenre(String genre) 
    {
        this.genre = genre;
    }

	public int getNr()
	{
		return nr;
	}

	public void setNr(int nr)
	{
		this.nr = nr;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

}
