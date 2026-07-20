package com.example.FoodDeliveryApp.security;

import com.example.FoodDeliveryApp.entity.DeliveryPartner;
import com.example.FoodDeliveryApp.entity.User;
import com.example.FoodDeliveryApp.repository.DeliveryPartnerRepository;
import com.example.FoodDeliveryApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // First check Users
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            return new CustomUserDetails(user);
        }

        // Then check Delivery Partners
        DeliveryPartner deliveryPartner =
                deliveryPartnerRepository.findByEmail(email).orElse(null);

        if (deliveryPartner != null) {
            return new CustomUserDetails(deliveryPartner);
        }

        throw new UsernameNotFoundException("User not found");
    }
}