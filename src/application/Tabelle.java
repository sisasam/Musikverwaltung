package application;

public class Tabelle {
	
	private int nr;
    private String titel;
    private String interpret;
    private String genre;
    private String path;

    public Tabelle(){
    	nr = 0;
        this.titel = "";
        this.interpret = "";
        this.genre = "";
    }

    public Tabelle(String titel, String interpret, String genre){
        this.titel = titel;
        this.interpret = interpret;
        this.genre = genre;
        nr++;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getInterpret() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
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

}
