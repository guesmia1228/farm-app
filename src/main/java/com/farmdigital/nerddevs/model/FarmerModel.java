package com.farmdigital.nerddevs.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "farmers_registration")
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor

public class FarmerModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
@Column(name = "farmer_name")
    private String name;
@Column(name = "farmer_email")
    private String email;

    private String password;
    @Column(name = "farmer_id")
    private  String  farmerId;
    @Column(name = "registraion_time")
    private  String registrationTime;
    @Column(name = "phone_number")
    private  int phoneNumber;
    @Column(name = "verified_user")
    private  boolean verified=false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "farmer_roles", joinColumns = @JoinColumn(name = "farmer_id", referencedColumnName = "farmer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Roles> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapRolesToAuthority(roles);
    }

    @Override
    public String getUsername() {
        return this.email;
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

    //    ! return a list of granted authorities
    private Collection<GrantedAuthority> mapRolesToAuthority(List<Roles> roles) {
        return roles.stream().map(
                singleRole -> new SimpleGrantedAuthority(singleRole.getName())
        ).collect(Collectors.toList());

    }
}
