package entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "game_results")
public class GameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_results_seq_gen")
    @SequenceGenerator(
            name = "game_results_seq_gen",
            sequenceName = "game_results_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "player_name", nullable = false)
    private String playerName;

    @Column(name = "score")
    private int score;

    @Column(name = "total_response_time")
    private long totalResponseTime;

    @Column(name = "played_at")
    private LocalDateTime playedAt;

    public GameResult(String playerName, int score, long totalResponseTime) {
        this.playerName = playerName;
        this.score = score;
        this.totalResponseTime = totalResponseTime;
        this.playedAt = LocalDateTime.now();
    }
}