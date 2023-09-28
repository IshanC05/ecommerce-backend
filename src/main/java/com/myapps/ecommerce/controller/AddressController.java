package com.myapps.ecommerce.controller;

import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.payload.AddressDto;
import com.myapps.ecommerce.security.JwtHelper;
import com.myapps.ecommerce.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private JwtHelper helper;

    @GetMapping(path = "/address")
    public List<AddressDto> getAllAddress(@RequestHeader HttpHeaders header) {
        String username = helper.getUsernameFromToken(header);
        return addressService.retrieveAllAddress(username);
    }

    @GetMapping(path = "/{userId}/address")
    public List<AddressDto> retrieveAllAddressByUserId(@RequestHeader HttpHeaders header,
                                                       @PathVariable Integer userId) {
        String username = helper.getUsernameFromToken(header);
        return addressService.retrieveAllAddressByUserId(username, userId);
    }

    @PostMapping(path = "/{userId}/address")
    public ResponseEntity<AddressDto> addNewAddress(@RequestHeader HttpHeaders header, @PathVariable Integer userId,
                                                    @RequestBody @Valid AddressDto addressDto) {
        String username = helper.getUsernameFromToken(header);
        return addressService.addNewAddressByUserId(username, userId, addressDto);
    }

    @DeleteMapping(path = "/{userId}/address/{addressId}")
    public ResponseEntity<ApiResponse> deleteAnAddress(@RequestHeader HttpHeaders header, @PathVariable Integer userId, @PathVariable Integer addressId) {
        String username = helper.getUsernameFromToken(header);
        return addressService.deleteAddressByUserId(username, userId, addressId);
    }

    @PutMapping(path = "/{userId}/address/{addressId}")
    public ResponseEntity<AddressDto> updateAnAddress(@RequestHeader HttpHeaders header,
                                                      @PathVariable Integer userId, @PathVariable Integer addressId,
                                                      @RequestBody @Valid AddressDto addressDto) {

        String username = helper.getUsernameFromToken(header);
        return addressService.updateAddressById(username, userId, addressId, addressDto);
    }

}