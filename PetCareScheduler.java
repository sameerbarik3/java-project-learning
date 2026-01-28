/*
                FOR THE BEGINNERS
                PLEASE CHECK THE PACKAGES SO THAT YOU DON'T RUN INTO AN ERROR
                ALSO CHECK THE IMPORTS I HAVE SEPERATED SOME IMPORT IN CASE YOU RUN INTO ANY PROBLEMS
                
*/
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


//IF NOT RUNNING PROPERLY Check Pet.java And Appointment.java

public class PetCareScheduler {

    private Map<String, Pet> petRegistry;
    private final String dataFile = "petData.sav"; //Save File Rename However You Like
    private final Scanner scanner;
    private static  final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static  final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public PetCareScheduler() {
        this.petRegistry = new HashMap<>();
        this.scanner = new Scanner(System.in);
        loadData(); // Load data on startup
    }

    private void loadData() {
        System.out.println("...Attempting to load data...");
        try (
                FileInputStream fileIn = new FileInputStream(dataFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn)
        ) {
            @SuppressWarnings("unchecked")
            Map<String, Pet> loadedData = (Map<String, Pet>) objectIn.readObject();
            this.petRegistry = loadedData;
            System.out.printf(" Data loaded successfully. %d pets registered.%n", petRegistry.size());
        } catch (FileNotFoundException e) {
            System.out.println("No existing data file found. Starting with an empty registry.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    public void storeData() {
        System.out.println("...Saving data to file...");
        try (
                FileOutputStream fileOut = new FileOutputStream(dataFile);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
        ) {
            objectOut.writeObject(petRegistry);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.err.println(" Error saving data: " + e.getMessage());
        }
    }
    public void registerPet() {
        System.out.println("\n---Pet Registration ---");
        String id;
        while (true) {
            System.out.print("Enter Unique Pet ID: ");
            id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println(" Pet ID cannot be empty. Please try again.");
            } else if (petRegistry.containsKey(id)) {
                System.out.println(" Error: Pet ID already exists. Please enter a unique ID.");
            } else {
                break;
            }
        }

        System.out.print("Enter Pet Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Species: ");
        String species = scanner.nextLine();

        int age = 0;
        while (true) {
            System.out.print("Enter Age (in years): ");
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age < 0) throw new IllegalArgumentException();
                break;
            } catch (NumberFormatException  e) {
                System.out.println(" Invalid input for age. Please enter a non-negative number.");
            }
        }

        System.out.print("Enter Owner Name: ");
        String ownerName = scanner.nextLine();
            System.out.print("Enter Contact Info: ");
            String contactInfo = scanner.nextLine();
            //"\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}"  this regex Allows To Match A Proper Mobile Number
        //Will Be Implemented In future



        LocalDate regDate = LocalDate.now();
        System.out.printf("Registration Date set to today: %s%n", regDate.format(dateFormat));

        Pet newPet = new Pet(id, name, species, age, ownerName, contactInfo, regDate);
        petRegistry.put(id, newPet);

        System.out.printf("Pet '%s' registered successfully with ID %s.%n", name, id);
    }

    public void scheduleAppointment() {
        System.out.println("\n---  Schedule Appointment ---");
        System.out.print("Enter Pet ID to schedule for: ");
        String id = scanner.nextLine().trim();

        Pet pet = petRegistry.get(id);
        if (pet == null) {
            System.out.println(" Error: Pet ID not found.");
            return;
        }

        System.out.printf("Scheduling appointment for: %s (%s)%n", pet.getName(), pet.getPetId());

        System.out.print("Enter Appointment Type: ");
        String type = scanner.nextLine();

        LocalDateTime dateTime = null;
        while (dateTime == null) {
            System.out.printf("Enter Date and Time (format: yyyy-MM-dd HH:mm): ");
            try {
                String input = scanner.nextLine();
                dateTime = LocalDateTime.parse(input, dateTimeFormat);
                if (dateTime.isBefore(LocalDateTime.now().minusMinutes(1))) {
                    System.out.println("Invalid Appointment Time , Please Choose Correct Date & Time ");
                    dateTime = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date/time format. Please use yyyy-MM-dd HH:mm (e.g., 2025-12-31 10:30).");
            }
        }

        System.out.print("Enter Notes (optional, press Enter to skip): ");
        String notes = scanner.nextLine();
        if (notes.isEmpty() ){
            Appointment newAppointment = new Appointment(type, dateTime);
            pet.addAppointment(newAppointment);
        }
        else{
            Appointment newAppointment = new Appointment(type, dateTime, notes);
            pet.addAppointment(newAppointment);
        }


        System.out.printf(" Appointment scheduled for %s on %s.%n", pet.getName(), dateTime.format(dateTimeFormat));
    }

    public void displayRecords() {
        System.out.println("\n---  Display model.Pet Records ---");
        System.out.println("1. All registered pets");
        System.out.println("2. All appointments for a specific pet");
        System.out.println("3. Upcoming appointments for all pets");
        System.out.println("4. Past appointment history for each pet");
        System.out.print("Enter option (1-4): ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                System.out.println("\n*** ALL REGISTERED PETS ***");
                if (petRegistry.isEmpty()) {
                    System.out.println("No pets are currently registered.");
                    break;
                }
                petRegistry.values().forEach(System.out::println);
                break;
            case "2":
                System.out.print("Enter Pet ID: ");
                String id = scanner.nextLine().trim();
                Pet pet = petRegistry.get(id);
                if (pet == null) {
                    System.out.println("Pet ID not found.");
                    break;
                }
                System.out.printf("\n*** APPOINTMENTS FOR %s (%s) ***%n", pet.getName(), pet.getPetId());
                if (pet.getAppointments().isEmpty()) {
                    System.out.println("No appointments found for this pet.");
                    break;
                }
                pet.getAppointments().forEach(System.out::println);
                break;
            case "3":
                displayUpcomingAppointments();
                break;
            case "4":
                displayPastAppointments();
                break;
            default:
                System.out.println(" Invalid option.");
        }
    }

    private void displayUpcomingAppointments() {
        System.out.println("\n*** UPCOMING APPOINTMENTS (All Pets) ***");
        LocalDateTime now = LocalDateTime.now();
        boolean found = false;

        for (Pet pet : petRegistry.values()) {
            List<Appointment> upcoming = pet.getAppointments().stream()
                    .filter(a -> a.getDateTime().isAfter(now))
                    .toList();

            if (!upcoming.isEmpty()) {
                System.out.printf("- Pet: %s (ID: %s)%n", pet.getName(), pet.getPetId());
                upcoming.forEach(System.out::println);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No upcoming appointments found.");
        }
    }

    private void displayPastAppointments() {
        System.out.println("\n*** PAST APPOINTMENT HISTORY (By Pet) ***");
        LocalDateTime now = LocalDateTime.now();
        boolean found = false;

        for (Pet pet : petRegistry.values()) {
            List<Appointment> past = pet.getAppointments().stream()
                    .filter(a -> a.getDateTime().isBefore(now))
                    .toList();

            if (!past.isEmpty()) {
                System.out.printf("- Pet: %s (ID: %s)%n", pet.getName(), pet.getPetId());
                past.forEach(System.out::println);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No past appointments found.");
        }
    }

    // --- Public Method: Generate Reports ---
    public void generateReports() {
        System.out.println("\n---  Generate Reports ---");
        System.out.println("1. Pets with upcoming appointments in the next week");
        System.out.println("2. Pets overdue for a vet visit (No vet visit in the last 6 months)");
        System.out.print("Enter option (1-2): ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                reportUpcomingNextWeek();
                break;
            case "2":
                reportOverdueVetVisit();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void reportUpcomingNextWeek() {
        System.out.println("\n*** PETS WITH UPCOMING APPOINTMENTS IN THE NEXT 7 DAYS ***");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusWeeks(1);
        boolean found = false;

        for (Pet pet : petRegistry.values()) {
            List<Appointment> appointmentsNextWeek = pet.getAppointments().stream()
                    .filter(a -> a.getDateTime().isAfter(now) && a.getDateTime().isBefore(nextWeek))
                    .toList();

            if (!appointmentsNextWeek.isEmpty()) {
                System.out.printf(" %s (ID: %s) - Owner: %s%n", pet.getName(), pet.getPetId(), pet.getOwnerName());
                appointmentsNextWeek.forEach(System.out::println);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No pets have appointments scheduled in the next week.");
        }
    }

    private void reportOverdueVetVisit() {
        System.out.println("\n*** PETS OVERDUE FOR A VET VISIT (Last 6 months) ***");
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        boolean found = false;

        for (Pet pet : petRegistry.values()) {
            Optional<LocalDateTime> lastVetVisit = pet.getAppointments().stream()
                    .filter(a -> a.getAppointmentType().toLowerCase().contains("vet"))
                    .map(Appointment::getDateTime)
                    .filter(dt -> dt.isBefore(LocalDateTime.now()))
                    .max(Comparator.naturalOrder());

            boolean overdue;
            if (lastVetVisit.isPresent()) {
                overdue = lastVetVisit.get().toLocalDate().isBefore(sixMonthsAgo);
            } else {
                // If never had a vet visit, check if registered more than 6 months ago
                overdue = pet.getRegistrationDate().isBefore(sixMonthsAgo);
            }

            if (overdue) {
                String lastVisitStr = lastVetVisit.map(dt -> dt.format(dateTimeFormat)).orElse("NEVER");
                System.out.printf(" %s (ID: %s) - Last Vet Visit: %s%n",
                        pet.getName(), pet.getPetId(), lastVisitStr);
                found = true;
            }
        }

        if (!found) {
            System.out.println("All registered pets have had a vet visit within the last 6 months or are newly registered.");
        }
    }

    
    /* Transparency As A New Learner
    As contactInfo.matches() function Works Alright but the main Problem is
    I cannot Make Program ask Again and Again Unless A Valid Number is Provided
    */

}public void run() {
        while (true) {
            //Could have Done Through Main But This Will Help Me Close Program Whenever I Like
            System.out.println("\n");
            System.out.println(" PetCare Scheduler Application ");
            System.out.println("\n");
            System.out.println("1. Register a Pet");
            System.out.println("2. Schedule an Appointment");
            System.out.println("3. Display Records");
            System.out.println("4. Generate Reports");
            System.out.println("5. Save & Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerPet();
                    break;
                case "2":
                    scheduleAppointment();
                    break;
                case "3":
                    displayRecords();
                    break;
                case "4":
                    generateReports();
                    break;
                case "5":
                    storeData();
                    System.out.println("Thank you for using PetCare Scheduler. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select an option between 1 and 5.");
            }
        }
    }

    // --- Main Method (Driver) ---
    public static void main(String[] args) {
        PetCareScheduler app = new PetCareScheduler();
        app.run();
    }