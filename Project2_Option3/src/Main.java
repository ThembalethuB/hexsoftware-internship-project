import java.util.*;
import java.io.*;

public class Main {

    static ArrayList<Movie> movies = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final String FILE_NAME = "movies.txt";

    public static void main(String[] args) {

        loadMovies();

        while (true) {

            System.out.println("\n=========== Movie System ===========");
            System.out.println("1. Add Movie");
            System.out.println("2. Search Movie");
            System.out.println("3. View Movies");
            System.out.println("4. Delete Movie");
            System.out.println("5. Exit");

            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addMovie();
                    break;

                case 2:
                    searchMovie();
                    break;

                case 3:
                    viewMovies();
                    break;

                case 4:
                    deleteMovie();
                    break;

                case 5:
                    saveMovies();
                    System.out.println("Movies saved. Goodbye.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ADD MOVIE
    static void addMovie() {

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Rating: ");
        float rating = scanner.nextFloat();

        System.out.print("Duration: ");
        double duration = scanner.nextDouble();

        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        movies.add(new Movie(title, genre, description, rating, duration, year));

        System.out.println("Movie added successfully.");
    }

    // SEARCH MOVIE
    static void searchMovie() {

        System.out.print("Enter movie title: ");
        String search = scanner.nextLine();

        boolean found = false;

        for (Movie movie : movies) {

            if (movie.title.equalsIgnoreCase(search)) {
                movie.display();
                found = true;
            }
        }

        if (!found) {

            System.out.println("Movie not found.");
            suggestMovies(search);
        }
    }

    // SUGGEST RELATED MOVIES
    static void suggestMovies(String search) {

        System.out.println("\nRelated movies you may like:");

        String prefix = search.substring(0, Math.min(2, search.length())).toLowerCase();

        boolean suggestionFound = false;

        for (Movie movie : movies) {

            if (movie.title.toLowerCase().startsWith(prefix)) {

                System.out.println(movie.title);
                suggestionFound = true;
            }
        }

        if (!suggestionFound) {
            System.out.println("No similar movies found.");
        }
    }

    // VIEW MOVIES
    static void viewMovies() {

        if (movies.isEmpty()) {
            System.out.println("No movies stored.");
            return;
        }

        for (Movie movie : movies) {
            movie.display();
        }
    }

    // DELETE MOVIE
    static void deleteMovie() {

        System.out.print("Enter title to delete: ");
        String title = scanner.nextLine();

        Iterator<Movie> iterator = movies.iterator();

        boolean removed = false;

        while (iterator.hasNext()) {

            Movie movie = iterator.next();

            if (movie.title.equalsIgnoreCase(title)) {

                iterator.remove();
                removed = true;
            }
        }

        if (removed)
            System.out.println("Movie deleted.");
        else
            System.out.println("Movie not found.");
    }

    // SAVE MOVIES
    static void saveMovies() {

        try {

            PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME));

            for (Movie movie : movies) {
                writer.println(movie.toFileString());
            }

            writer.close();

        } catch (Exception e) {
            System.out.println("Error saving movies.");
        }
    }

    // LOAD MOVIES
    static void loadMovies() {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists())
                return;

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                movies.add(Movie.fromFileString(line));
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error loading movies.");
        }
    }
}