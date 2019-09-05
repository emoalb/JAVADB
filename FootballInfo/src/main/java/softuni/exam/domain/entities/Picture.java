package softuni.exam.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {
    private String url;
    private Set<Team> teams;
    private Set<Player> players;

    public Picture() {
    }

    @Column(name = "url", nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
@OneToMany(targetEntity = Team.class,mappedBy = "picture",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    @OneToMany(targetEntity = Player.class,mappedBy = "picture",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
