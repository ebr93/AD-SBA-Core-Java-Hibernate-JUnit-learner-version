package sba.sms.models;

import java.util.List;
import java.util.Objects;
import java.util.jar.Attributes.Name;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "student")
public class Student {
	@Id
	@NonNull
	@Column(name = "email", length = 50)
	String email;
	@NonNull
	@Column(name = "name", length = 50)
	String name;
	@NonNull
	@Column(name = "password", length = 50)
	String password;
	
	@JoinTable(name = "student_courses",
			joinColumns = @JoinColumn(
					name = "student_email",
					referencedColumnName = "email"
			), 
			inverseJoinColumns = @JoinColumn(
					name = "course_id",
					referencedColumnName = "id"
			)
	)
	@OneToMany(fetch = FetchType.EAGER,
	cascade = {CascadeType.DETACH, CascadeType.MERGE,
	CascadeType.PERSIST, CascadeType.REFRESH})
	@ToString.Exclude
	List<Course> courses;
	
	@Override
	public int hashCode() {
		return Objects.hash(courses, email, name, password);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(courses, other.courses) && Objects.equals(email, other.email)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password);
	}
	
	
	
}
