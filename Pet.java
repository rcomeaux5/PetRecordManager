import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

public class Pet {
    private int uniqID;
    private String kind;
    private String name;
    private int birthYear;
    private char gender; // 'm' == male, 'f' == female
    private boolean spayedOrNeutered = false;

    static private Random rand = new Random();

    public Pet() {
        kind = "dog";
        name = "Augie";
        birthYear = 2008;
        gender = 'm';
        spayedOrNeutered = true;
    }

    public Pet(String kind_In, String name_In, int year_In, char gender_In, boolean spayNeut_In) {
        kind = kind_In.toLowerCase(); // make sure all objects use the same capitalization
        name = name_In;
        birthYear = year_In;
        gender = gender_In;
        spayedOrNeutered = spayNeut_In;
    }

    public Pet(String data) {
        String[] pieces = data.split(",");
        uniqID = Integer.parseInt(pieces[0]);
        kind = pieces[1];
        name = pieces[2];
        birthYear = Integer.parseInt(pieces[3]);
        gender = pieces[4].charAt(0);
        spayedOrNeutered = Boolean.parseBoolean(pieces[5]);
    }

    public int getUniqID() {
        return uniqID;
    }

    public void setUniqID(int id_In) {
        uniqID = id_In;
    }

    @Override
    public String toString() {
        String kStr = kind;
        return String.format("%s (%s), Age: %d, Sex: %s, Spayed/Neutered: %s",
                name, kStr, getAge(), gender, spayedOrNeutered);
    }

    public String toCSVstring() {
        return String.format("%d,%s,%s,%d,%s,%s",
                uniqID, kind, name, birthYear, gender, spayedOrNeutered);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return birthYear == pet.birthYear &&
                gender == pet.gender &&
                spayedOrNeutered == pet.spayedOrNeutered &&
                kind.equals(pet.kind) &&
                name.equals(pet.name);
    }

    public int hashCode() {
        return Objects.hash(kind, name, birthYear, gender, spayedOrNeutered);
    }

    public int getAge() {
        LocalDate today = LocalDate.now();
        int thisYear = today.getYear();
        return thisYear - birthYear;
    }

    public String getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public char getGender() {
        return gender;
    }

    public boolean isSpayedOrNeutered() {
        return spayedOrNeutered;
    }

    static final double SPAY_NEUT_PCT = 0.2; // 20% of generated pets will be intact (not spayed or neutered)

    public static Pet randomPet_0() {
        String[] kinds = {"dog", "cat", "parrot", "canary", "ferret", "hamster"};
        String kind_In = kinds[rand.nextInt(kinds.length)];

        final int MAX_AGE = 20;

        String[] names = {
                "Bowser", "Fido", "Caesar", "Chester", "Andy", "Danae", "Perseus", "Donald", "Tweety", "Sylvester",
                "Bugs", "Daffy", "Mickey", "Pluto", "Nola", "Ginger", "Tobie", "Peanut", "Snoopy", "Toto",
                "Molly", "Zuma", "Charlie", "Boomer", "Foxy", "Snickers", "Petey", "Spot", "Daisy", "Max"};
        String name_In = names[rand.nextInt(names.length)];
        int thisYear = LocalDate.now().getYear();
        int year_of_birth = thisYear - rand.nextInt(MAX_AGE);
        char gender_In = rand.nextBoolean() ? 'm' : 'f';
        boolean spayed_In = rand.nextDouble() > SPAY_NEUT_PCT;
        boolean old_enough = thisYear - year_of_birth > 1;

        return new Pet(kind_In, name_In, year_of_birth, gender_In, spayed_In && old_enough);
    }

    public static final String[] KINDS = {"cat", "canary", "dog", "ferret", "hamster", "parrot"};

    public static Pet randomPet() {
        String kind_In = KINDS[rand.nextInt(KINDS.length)];
        int maxAge = 10;
        switch (kind_In) {
            case "cat":
                maxAge = 16;
                break;
            case "canary":
                maxAge = 10;
                break;
            case "dog":
                maxAge = 16;
                break;
            case "ferret":
                maxAge = 10;
                break;
            case "hamster":
                maxAge = 3;
                break;
            case "parrot":
                maxAge = 30;
                break;
        }
        String[] names = {
                "Bowser", "Fido", "Caesar", "Chester", "Andy", "Danae", "Perseus", "Donald", "Tweety", "Sylvester",
                "Bugs", "Daffy", "Mickey", "Pluto", "Nola", "Ginger", "Tobie", "Peanut", "Snoopy", "Toto",
                "Molly", "Zuma", "Charlie", "Boomer", "Foxy", "Snickers", "Petey", "Spot", "Daisy", "Max"};
        String name_In = names[rand.nextInt(names.length)];
        int thisYear = LocalDate.now().getYear();
        int year_of_birth = thisYear - rand.nextInt(maxAge);
        char gender_In = rand.nextBoolean() ? 'm' : 'f';
        boolean spayed_In = rand.nextDouble() > SPAY_NEUT_PCT;
        boolean old_enough = thisYear - year_of_birth > 1;

        return new Pet(kind_In, name_In, year_of_birth, gender_In, spayed_In && old_enough);
    }
}
