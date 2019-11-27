package softuni.residentevil.domain.entity;

import org.springframework.format.annotation.DateTimeFormat;
import softuni.residentevil.domain.enums.Magnitude;
import softuni.residentevil.domain.enums.Mutation;
import softuni.residentevil.validation.BeforeToday;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "viruses")
public class Virus extends BaseEntity {
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
    private List<Capital> capitals;

    public Virus() {
    }

    @NotEmpty
    @Size(min = 3, max = 10)
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty
    @Size(min = 5, max = 100)
    @Column(name = "description", columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Size(max = 50)
    @Column(name = "side_effects")
    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @Pattern(regexp = "[Cc]orp")
    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "is_deadly")
    public boolean isDeadly() {
        return isDeadly;
    }

    public void setDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    @Column(name = "is_curable")
    public boolean isCurable() {
        return isCurable;
    }

    public void setCurable(boolean curable) {
        isCurable = curable;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mutation")
    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    @Min(0)
    @Max(100)
    @Column(name = "turnover_rate")
    public Integer getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Integer turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @Min(1)
    @Max(12)
    @Column(name = "hours_until_turn")
    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "magnitude")
    public Magnitude getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    @NotNull
    @BeforeToday
    @Column(name = "released_on")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(Date releasedOn) {
        this.releasedOn = releasedOn;
    }

    @OneToMany(targetEntity = Capital.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "viruses_capitals",
            joinColumns = @JoinColumn(name = "virus_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "capital_id", referencedColumnName = "id")
    )
    public List<Capital> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }
}
