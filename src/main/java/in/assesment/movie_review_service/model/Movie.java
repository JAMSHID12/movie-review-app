package in.assesment.movie_review_service.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserInfo userDetail;
	
	private String title;
	private String description;
	@Column(name = "release_date")
	private Date releaseDate;
	private int duration;
	
	@Column(name = "total_rating")
	private double totalRating;
	
	@Builder.Default
	@Column(name="total_rated_count")
	private int totalRatedCount = 1;
	
	@ManyToMany
	private Set<Genre> genres = new HashSet<>();
	
	@ManyToMany
	private Set<Language> languages = new HashSet<>();
	
	@Column(name = "movie_image_url")
	private String movieImageUrl;
	
	@Column(name = "ott_platform_url")
	private String ottPlatformUrl;
	
	@Builder.Default
	@Column(name = "is_active")
    private boolean status= false;
	
	@CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
