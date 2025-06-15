package hu.tundik.progenv.model;

public class DVD
{
    private int id;
    private String title;
    private String genre;
    private int movieLength;
    private double dailyPrice;
    private boolean available;

    public DVD() {}

    public DVD(int id, String title, String genre, int movieLength, double dailyPrice, boolean available) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.movieLength = movieLength;
        this.dailyPrice = dailyPrice;
        this.available = available;
    }

    // Getterek és setterek
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getMovieLength() { return movieLength; }
    public void setMovieLength(int movieLength) { this.movieLength = movieLength; }

    public double getDailyPrice() { return dailyPrice; }
    public void setDailyPrice(double dailyPrice) { this.dailyPrice = dailyPrice; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString()
    {
        return title + " (" + genre + ", " + movieLength + " min, " + dailyPrice + " €/day)";
    }
}
