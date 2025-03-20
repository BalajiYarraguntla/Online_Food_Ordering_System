package com.deloitte.notificationservice.entity;

	import jakarta.persistence.*;
	import lombok.*;
	 
	import java.time.LocalDateTime;
	 
	@Entity
	@Table(name = "notifications")
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class Notification {
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	    private String userId;
	    private String message;
	    private String type;  // EMAIL, SMS, PUSH
	    private LocalDateTime createdAt;
	}

}
