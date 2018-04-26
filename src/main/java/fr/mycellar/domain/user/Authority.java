package fr.mycellar.domain.user;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.mycellar.domain.shared.AbstractAuditingEntity;

@Entity
@Table(name = "authority")
public class Authority extends AbstractAuditingEntity {

	private static final long serialVersionUID = 201804261319L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 50, unique = true)
	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Authority authority = (Authority) o;
		if (authority.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), authority.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Authority{id=" + getId() + ", name='" + getName() + "'}";
	}
}
