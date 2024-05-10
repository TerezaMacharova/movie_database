package movie_database;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.SQLException;

public class Play {



    public static int pouzeCelaCisla(Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine(); // clear the invalid input
                System.out.println("Zadaj prosim cele cislo");
            }
        }
    }


    public static float pouzeCisla(Scanner sc)
    {
        float cislo = 0;
        try
        {
            cislo = sc.nextFloat();
        }
        catch(Exception e)
        {
            System.out.println("Nastala vynimka typu "+e.toString());
            System.out.println("zadaj prosim cislo ");
            sc.nextLine();
            cislo = pouzeCisla(sc);
        }
        return cislo;
    }



    public static void main(String[] args)  {
        AnimatedMovies movies = new AnimatedMovies();
        Scanner input = new Scanner(System.in);
        Scanner output= new Scanner(System.in);



        while (true) {
            System.out.println("\nVyberte operaci:");
            System.out.println("1 - pridať film");
            System.out.println("2 - zobraziť film");
            System.out.println("3 - nastaviť hodnotenie");
            System.out.println("4 - vymazať film");
            System.out.println("5 - výpis filmov");
            System.out.println("6 - uložit do súboru");
            System.out.println("7 - načítať zo súboru");
            System.out.println("8 - upraviť film");
            System.out.println("9 -  Zoznam hercov,animátorov, ktorý sa podielali na viacerých filmoch: ");
            System.out.println("10 - Výpis filmov jednoho herca");
            System.out.println("11 - Pripojenie k SQL databázi a uloženie všetkých údajov do SQL databáze");
            System.out.println("12 - Načítanie všetkých údajov z SQL databáze");
            System.out.println("0 - ukončit program");

            int volba = input.nextInt();
            input.nextLine();


            switch (volba) {
                case 1:
                    movies.addFilm();

                    break;
                case 2:
                    System.out.println("Zadajte meno filmu:");
                    String meno = input.nextLine();
                    Movie movie = movies.getFilm(meno);
                    if (movie == null) {
                        System.out.println("Film nebol nájdený.");
                    } else {
                        System.out.println(movie);
                    }
                    break;
                case 3:
                    System.out.println("Zadajte meno filmu:");
                    meno = input.nextLine();
                    System.out.println("Upraviť hodnotenie filmu:");
                    int hodnotenie = input.nextInt();
                    boolean result = movies.setHodnotenie(meno, (int) hodnotenie);
                    if (result) {
                        System.out.println("Hodnotenie bolo nastavené.");
                    } else {
                        System.out.println("Film nebol nájdený.");
                    }
                    break;
                case 4:
                    System.out.println("Zadajte meno filmu:");
                    meno = input.nextLine();
                    result = movies.vymazFilm(meno);
                    if (result) {
                        System.out.println("Film bol úspešne vymazaný.");
                    } else {
                        System.out.println("Film nebol nájdený.");
                    }
                    break;
                case 5:
                    movies.vypisFilmov();
                    break;
                case 6:
                    System.out.println("Zadajte meno filmu:");
                    String soubor = input.nextLine();
                    movies.ulozDoSuboru(soubor);
                    break;
                case 7: System.out.println("Zadejte názov súboru:");
                    String nacistSoubor = input.nextLine();
                    AnimatedMovies.nacitajZoSuboru(nacistSoubor);
                    System.out.println("Film bol načítaný zo súboru " + nacistSoubor);
                    break;
                case 8:

                    movies.editFilm();
                    break;
                case 9: AnimatedMovies.HerecVoViacejFilmochMetoda();
                    break;
                case 10:
                    System.out.println("Zadajte meno herca alebo animátora, ktorého filmy chcete vypísať: ");
                    String jmeno = input.next();
                    AnimatedMovies.HerecvJednomFilme(jmeno);
                    break;
                case 11:
                    try {
                        Database database = new Database();
                        Connection connection = Database.connect();

                        // Connection connection = database.getConnection();
                        if (connection == null) {
                            System.out.println("Nedokážem sa pripojiť");
                            return;
                        }
                        try {
                            database.createTable();

                            AnimatedMovies.insertFilm(connection);
                            System.out.println("Pridaný film úspešne");
                        } catch (SQLException e) {
                            System.out.println("Zlyhanie pridania filmu: " + e.getMessage());
                        } finally {
                            database.disconnect();
                        }
                    } catch (Exception e) {

                    }
                    break;
                case 12:
                    Database database = new Database();

                    Connection connection = Database.connect();
                    try {
                        database.createTable();
                        AnimatedMovies.loadMovie(connection);
                        System.out.println("Pridaný film úspešne.");
                    } catch (SQLException e) {

                        e.printStackTrace();
                    }



                    break;
                case 0:
                    System.exit(0);

                default:
                    System.out.println("Neplatná volba. Zvolte prosím číslo od 0 do 12.");
                    break;
            }}}}

