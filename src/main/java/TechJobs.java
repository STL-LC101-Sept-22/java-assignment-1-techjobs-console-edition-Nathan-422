import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by (type 'x' to quit):", actionChoices);

            // quit program or continue interface
            if (actionChoice == null) {
                break;
            } else if (actionChoice.equals("list")) {

                // get user input
                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    // print all header options

                    printJobs(JobData.findAll());
                } else {
                    // print a single header worth of data

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }


        // loop for valid menu selection from the user
        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            // check for int input or check for quit command
            if (in.hasNextInt()) {
                choiceIdx = in.nextInt();
                in.nextLine();
            } else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");
                if (shouldQuit) {
                    return null;
                }
            }

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // TODO: implement me!
    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        /*  FORMAT:
        *****
        position type: Data Scientist / Business Intelligence
        name: Sr. IT Analyst (Data/BI)
        employer: Bull Moose Industries
        location: Saint Louis
        core competency: Statistical Analysis
        *****
        */

        // early return on empty array
        if (someJobs.size() == 0) {
            System.out.print("No Results");
            return;
        }


        int index = 0;  // control for line break printing

        System.out.println();   // extra line to match formatting
        for (HashMap<String, String> job : someJobs) {
            System.out.printf(
                    "*****\n" +
                            "position type: %s\n" +
                            "name: %s\n" +
                            "employer: %s\n" +
                            "location: %s\n" +
                            "core competency: %s\n" +
                            "*****\n",
                    job.get("position type"),
                    job.get("name"),
                    job.get("employer"),
                    job.get("location"),
                    job.get("core competency")
            );

            // print line break in between results
            if (index < someJobs.size() -1 ) {
                System.out.println();
            }
            index++;

        }
    }
}
