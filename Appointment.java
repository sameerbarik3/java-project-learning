import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String aType; // user must put any valid option 
    private LocalDateTime aDateTime;
    private String notes;
    //for Null Notes
    public Appointment(String aType, LocalDateTime aDateTime) {
        this.aType = aType;
        this.aDateTime = aDateTime;
        this.notes = "N/A , Have a Safe Day";
    }
    public Appointment(String aType, LocalDateTime aDateTime, String notes){
        this.aType = aType;
        this.aDateTime = aDateTime;
        this.notes =notes;
    }

    //Getters
    public String getAppointmentType() {
        return aType;
    }

    public LocalDateTime getDateTime() {
        return aDateTime;
    }

    public String getNotes() {
        return notes;
    }

    // Setters 
    public void setAppointmentType(String newType) {
        this.aType = newType;
    }

    public void setDateTime(LocalDateTime dteTme) {
        this.aDateTime = dteTme;
    }

    public void setNotes(String n) {
        this.notes = n;
    }

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    //Formatting To Make it Readable For User


    @Override
    public String toString() {
        String formattedDateTime = this.aDateTime.format(FORMATTER);

        return "Appointment Details:" +
                "\n  Type: " + aType +
                "\n  Date & Time: " + formattedDateTime +
                "\n  Notes: " + notes;
    }
}