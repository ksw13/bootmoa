package com.example.demo.dto.security;

import com.example.demo.dto.UserAccountDto;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@Getter
public class BootPrincipal implements UserDetails {
    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public BootPrincipal(String username, String password) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);
        this.username = username;
        this.password = password;
        this.authorities = roleTypes.stream()
                .map(RoleType::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableSet());
    }

    public static BootPrincipal from(UserAccountDto userAccountDto){
        return new BootPrincipal(
                userAccountDto.getUserId(),
                userAccountDto.getUserPassword()
        );
    }

    public UserAccountDto toDto(){
        return new UserAccountDto(
                username,
                password
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public enum RoleType{
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
