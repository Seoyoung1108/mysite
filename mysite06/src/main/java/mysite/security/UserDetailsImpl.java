package mysite.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mysite.vo.UserVo;

@SuppressWarnings("serial")
public class UserDetailsImpl extends UserVo implements UserDetails {
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + getRole()));
		// List.of(~)
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}
}
