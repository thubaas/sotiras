package com.thubas.sotiras.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thubas.sotiras.model.ERole;
import com.thubas.sotiras.model.Role;
import com.thubas.sotiras.repository.RoleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	private final RoleRepository roleRepository;

	@Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if(roleRepository.count() == 0) {
			roleRepository.save(new Role(null, ERole.ROLE_USER));
			roleRepository.save(new Role(null, ERole.ROLE_ADMIN));
		}
		
	}

}
