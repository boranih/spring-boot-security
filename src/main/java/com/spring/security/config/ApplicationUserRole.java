package com.spring.security.config;

import static com.spring.security.config.ApplicationUserPermission.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum ApplicationUserRole {

	STUDENT(Sets.newHashSet()), 
	ADMIN(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE, COURSE_READ, COURSE_WRITE)),
	ADMIN_TRAINEE(Sets.newHashSet(STUDENT_READ, COURSE_READ));

	private final Set<ApplicationUserPermission> permissions;

	private ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<ApplicationUserPermission> getPermissions() {
		return permissions;
	}

	public Set<SimpleGrantedAuthority> getGrantedAuthorites() {
		Set<SimpleGrantedAuthority> permisssions = getPermissions().stream()
				.map(persmission -> new SimpleGrantedAuthority(persmission.getPermission()))
				.collect(Collectors.toSet());
		permisssions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permisssions;

	}

}
