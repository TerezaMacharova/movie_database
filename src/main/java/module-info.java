
/**
 * @author Terézia Macharová
 *
 */
module movie_database {
    requires java.security.sasl;
    requires com.google.gson;

    opens movie_database to com.google.gson;
}

/* requires - dependencies on other modules
* exports - makes packages within the module available to other modules */