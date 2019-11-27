package softuni.residentevil.domain.model.binding;

import org.springframework.format.annotation.DateTimeFormat;
import softuni.residentevil.domain.enums.Magnitude;
import softuni.residentevil.domain.enums.Mutation;
import softuni.residentevil.validation.BeforeToday;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

public class VirusRegisterBindingModel {
    private String name;
    private String description;
    private String sideEffects;
    private String creator;
    private boolean isDeadly;
    private boolean isCurable;
    private Mutation mutation;
    private Integer turnoverRate;
    private Integer hoursUntilTurn;
    private Magnitude magnitude;
    private Date releasedOn;
    private List<String> capitals;

    public VirusRegisterBindingModel() {
    }

    @Size(min = 3, max = 10, message = "Invalid name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = 5, max = 100, message = "Invalid description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Size(max = 50, message = "Invalid side effects")
    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @Pattern(regexp = "[Cc]orp", message = "Invalid creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isDeadly() {
        return isDeadly;
    }

    public void setDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    public boolean isCurable() {
        return isCurable;
    }

    public void setCurable(boolean curable) {
        isCurable = curable;
    }

    @NotNull(message = "Mutation cannot be null")
    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    @Min(value = 0, message = "Invalid turnover rate")
    @Max(value = 100, message = "Invalid turnover rate")
    public Integer getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Integer turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @Min(value = 1, message = "Invalid hours until turn")
    @Max(value = 12, message = "Invalid hours until turn")
    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull
    public Magnitude getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    @BeforeToday
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(Date releasedOn) {
        this.releasedOn = releasedOn;
    }

    @NotEmpty(message = "You must select capitals")
    public List<String> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<String> capitals) {
        this.capitals = capitals;
    }
}
