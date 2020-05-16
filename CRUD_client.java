import java.io.*;
import java.util.*;

public class CRUD_client {
    static final int SIZE = 5;
    static Scanner keyb = new Scanner(System.in);
    static Scanner keyboard = new Scanner(System.in);
    static final String CSV_FILE_NAME = "pets.csv";
    static final String MENU = "\n(N)ew pet, (S)how pet info, (M)odify pet, (D)elete pet, (Q)uit program";
    static String csvHeaders; //= "id,kind,name,birthYear,gender,spayed or neutered";

    public static void main(String[] args) {
        System.out.println("Welcome to the Pet Records Program");

        Map<Integer, Pet> petIDmap = readFileIntoMap(CSV_FILE_NAME);
        
        
        boolean again = false;
        do {
            char choice = getChoice();
            switch (choice) {
                case 'N':
                    addNewPetToMap(petIDmap);
                    break;
                case 'S':
                    showInfoForOnePet(petIDmap);
                    break;
                case 'M':
                    modifyOnePetsInfo(petIDmap);
                    break;
                case 'D':
                    deleteOnePet(petIDmap);
                    break;
                case 'Q':
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Unknown option");
            }
            again = choice != 'Q';
        } while (again);

        
       
        
        
        savePetInfoToCSVfile(CSV_FILE_NAME, petIDmap);
        
        System.out.println("Thank you for using the Pet Records Program");
        
       
    }

    /* TODO complete the function so that it returns a value
     * that is _NOT_ already in the IDset -- do this without
     * asking the user to enter an ID
     */
    static int generateUniqueID(Set<Integer> IDset) { // double check work
       Random r = new Random();
       int randomNumber = 0;    
        
        /*
         * the list below isn't used
         */
       ArrayList<Integer>tempList = new ArrayList<>();
        
        /*
         * the repetition here should be indefinite
         * 
         * generate a number
         * while the set contains the number
         *       generate a number
         * return the number
         * 
         * it only needs to be repeated while
         * the generated number is a repeated one
         * sometimes, the very first one is
         * unique, and the loop won't execute at all
         * 
         */
       for(int ID : IDset) {
          randomNumber = r.nextInt(100);
          if(!(IDset.contains(randomNumber))){
           return randomNumber; 
         }
       }
        
        
        
        return randomNumber;
    }

    /* TODO complete this function so that the user is asked
     * for a single ID, and the pet with that ID is displayed
     * just show a single pet (no loop needed here)
     */
    static void showInfoForOnePet(Map<Integer, Pet> petIDmap) {
        System.out.println("Enter the pet ID number: ");
        int ID_Number = keyb.nextInt();
        if(petIDmap.containsKey(ID_Number)){
        //System.out.println("show info for one pet");
        System.out.println(petIDmap.get(ID_Number));
        }
        else{
            System.out.println("No Pet with that ID found");
            //figure out error here in program
        }
       
    }

    /* TODO complete this function so that the user is asked
     * for a single ID, and the pet with that ID is displayed
     * then the user is asked for updated information, replace
     * the old object in the map with the updated object
     * (hint: this can be done without adding setters to the
     *  class, instead create a new pet object with the same ID but
     *  the updated information)
     */
    static void modifyOnePetsInfo(Map<Integer, Pet> petIDmap) {
        int ID_number = 0;
        System.out.println("Enter the ID number for the pet you'd like to modify: ");
        ID_number = keyb.nextInt();      
        if(petIDmap.containsKey(ID_number)){
        //System.out.println("show info for one pet");
        System.out.println(petIDmap.get(ID_number));
        }
        else{
            System.out.println("No Pet with that ID found");
            //figure out error here in program
        } 
        Pet updatedPet = getPetInfoFromUser();
        
        /*
         * 4/10/2020
         * the Pet object returned by getPetInfoFromUser
         * doesn't have the id set, that has to be done
         * as a separate step before replacing the Pet
         * in the map, or else it'll go in with zero as an id,
         * eventually that'll overwrite any previous
         * Pet with that Id
         */

        petIDmap.replace(ID_number,updatedPet);
        System.out.println("replaced the pet");

        
        
    }

    /* TODO ask the user for an ID. Show the pet info to the user
     * confirm that's the pet to delete -- if the user confirms
     * the deletion, remove the pet from the map
     */
    static void deleteOnePet(Map<Integer, Pet> petIDmap) {
        int ID_number = 0;
        boolean confirm = false;
        System.out.println("Enter the ID number for the pet you'd like to modify: ");
        ID_number = Integer.parseInt(keyboard.next());      
        if(petIDmap.containsKey(ID_number)){
            System.out.println(petIDmap.get(ID_number));
            System.out.println("");
            System.out.println("Do you confirm to delete this Pet? y/n:");
            confirm = keyboard.next().toLowerCase().charAt(0) == 'y';
            if(confirm){  
                petIDmap.remove(ID_number);
                System.out.println("Pet removed "); 
            }   
        }
        else{
            System.out.println("No Pet with that ID found");  
        } 
    }

    static void addNewPetToMap(Map<Integer, Pet> petIDmap) {
        Pet p = getPetInfoFromUser();
        int uniq_ID = generateUniqueID(petIDmap.keySet());
        p.setUniqID(uniq_ID);
        System.out.printf("%s is assigned the id [%d]\n", p.getName(), uniq_ID);
        System.out.printf("Adding %s to the idMap\n", p.getName());
        petIDmap.put(p.getUniqID(), p);
    }

    static Pet getPetInfoFromUser() {
        /* TODO restrict kind_In to known types as defined by the Pet class */
        List<String> KIND = Arrays.asList("cat", "canary", "dog", "ferret", "hamster", "parrot");
        System.out.println("The types of animal are:");
        for (int ndx = 0; ndx < Pet.KINDS.length; ndx++) {
            System.out.printf("%d) %s\t", ndx, Pet.KINDS[ndx]);
        }
        System.out.print("\nWhat kind of animal? ");
        String kind_In = keyb.nextLine();
        while(!(KIND.contains(kind_In))){
             System.out.print("\nWhat kind of animal? ");
             kind_In = keyb.nextLine();
        }
        
        System.out.print("What's it's name? ");
        String name_In = keyb.nextLine();

        System.out.print("Year of birth? ");
        int year_In = Integer.parseInt(keyb.nextLine());// dont leabe any trailing new lines

        /* this input must be validated, so there's no mistake */
        System.out.print("(m)ale or (f)emale? ");
        char gender_In = keyb.nextLine().toLowerCase().charAt(0);
        while (gender_In != 'm' && gender_In != 'f') {
            System.out.print("(m)ale or (f)emale? ");
            gender_In = keyb.nextLine().toLowerCase().charAt(0);
        }

        /* any response other than 'Y' or 'y' will yield spayed=false */
        System.out.printf("Is %s spayed or neutered? (Y/N) ", name_In);
        boolean spayed_In = keyb.nextLine().toLowerCase().charAt(0) == 'y';

        Pet p = new Pet(kind_In, name_In, year_In, gender_In, spayed_In);
        System.out.printf("Pet created: %s\n", p);
        return p;
    }

    static char getChoice() {
        /* no need for input validation in this function,
         * since the return value is being used in a switch statement,
         * the default switch clause will handle any invalid input
         * and the loop in main will repeat
         */
        System.out.println(MENU);
        System.out.print("Enter your choice: ");
        char choice = keyb.next().toUpperCase().charAt(0);
        return choice;
    }

    static void savePetInfoToCSVfile(String csvFileName, Map<Integer, Pet> petIDmap) {
        try {
            PrintWriter pw = new PrintWriter(csvFileName);
            System.out.println("Saving records");
            pw.println(csvHeaders); // must write the headers before anything else
            for (Pet pet : petIDmap.values()) {//why would you lose the record?
                pw.println(pet.toCSVstring());
            }
            pw.close();// must close PrintWriter or will be empty? why?
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static Map<Integer, Pet> readFileIntoMap(String csvFileName) {
        Map<Integer, Pet> map = new HashMap<>();
        File petFile = new File(csvFileName);
        try {
            Scanner petScan = new Scanner(petFile);
            csvHeaders = petScan.nextLine(); // 1st line of .csv file is column headers
            while (petScan.hasNext()) {// why not stored in data structure
                String data = petScan.nextLine();
                Pet p = new Pet(data);
                map.put(p.getUniqID(), p);
            }
            petScan.close();
        } catch (FileNotFoundException e) {
            System.err.printf("Cannot open file: [%s].\nExiting program.", csvFileName);
            System.exit(0);
        }
        return map;
    }
}
