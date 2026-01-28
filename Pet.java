import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
//import service.Appointment;

public class Pet implements Serializable {
    private static final long serialVersionUID = 1L;
    private String petId;
    private String name;
    private String species;
    private int age;
    private String ownerName;
    private String contactInfo;
    private LocalDate registrationDate;
    private List<Appointment> appointments;

    public Pet(String petId, String name, String species, int age, String ownerName, String contactInfo, LocalDate registrationDate) {
        this.petId = petId;
        this.name = name;
        this.species = species;
        this.age = age;
        this.ownerName = ownerName;
        this.contactInfo = contactInfo;
        this.registrationDate = registrationDate;
        this.appointments = new ArrayList<>();
    }

    // Getters
    public String getPetId() { return petId; }
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public int getAge() { return age; }
    public String getOwnerName() { return ownerName; }
    public String getContactInfo() { return contactInfo; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public List<Appointment> getAppointments() { return appointments; }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Override
    public String toString() {
        String formattedRegistrationDate = this.registrationDate.format(formatter);
        return String.format(
                "ID:" + petId + " Name: " +  name  + " Species: " + species +" Age: " + age +
                        "\n Owner: " + ownerName +" Owner Contact : " + contactInfo +  " Reg Date: " + formattedRegistrationDate
        );
    }
}