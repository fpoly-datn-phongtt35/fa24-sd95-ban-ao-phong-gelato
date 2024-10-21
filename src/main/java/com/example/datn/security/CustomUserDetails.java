package com.example.datn.security;


import com.example.datn.entities.Account;
import com.example.datn.entities.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class CustomUserDetails implements UserDetails, Serializable {
    private Account account;

    public CustomUserDetails(Account account) {
        this.account = account;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = account.getRole();
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

            authorityList.add(new SimpleGrantedAuthority(role.getName().toString()));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.isNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    public String getFullName() {
//        return this.account.getFullName();
//    }
//    public String getPhoto() {
//        return this.account.getPhoto();
//    }
//    public Integer getId() {
//        return this.account.getId();
//    }

}
