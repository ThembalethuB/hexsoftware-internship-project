class Movie {

    String title;
    String genre;
    String description;
    float rating;
    double duration;
    int year;

    public Movie(String title, String genre, String description, float rating, double duration, int year) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.rating = rating;
        this.duration = duration;
        this.year = year;
    }

    public String toFileString() {
        return title + "," + genre + "," + description + "," + rating + "," + duration + "," + year;
    }

    public static Movie fromFileString(String line) {

        String[] data = line.split(",");

        return new Movie(
                data[0],
                data[1],
                data[2],
                Float.parseFloat(data[3]),
                Double.parseDouble(data[4]),
                Integer.parseInt(data[5])
        );
    }

    public void display() {
        System.out.println("\nTitle: " + title);
        System.out.println("Genre: " + genre);
        System.out.println("Description: " + description);
        System.out.println("Rating: " + rating);
        System.out.println("Duration: " + duration + " hours");
        System.out.println("Year: " + year);
        System.out.println("--------------------------------");
    }
}