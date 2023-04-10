package com.jose.lottery.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author jose
 */
@Entity
@Table(name = "TB_LotteryEvent")
public class LotteryEventModel implements Serializable {

    public enum Status {
        OPEN,
        CLOSED
    }
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private Status status;

    @OneToMany(mappedBy = "lotteryEvent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BallotModel> ballots = new HashSet<>();
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<BallotModel> getBallots() {
        return ballots;
    }

    public void setBallots(Set<BallotModel> ballots) {
        this.ballots = ballots;
    }
}
