package com.myapps.ecommerce.service;

import com.myapps.ecommerce.entity.Address;
import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.exception.ResourceNotFoundException;
import com.myapps.ecommerce.exception.UserMismatchException;
import com.myapps.ecommerce.exception.UserNotFoundException;
import com.myapps.ecommerce.payload.AddressDto;
import com.myapps.ecommerce.repository.AddressRepository;
import com.myapps.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<AddressDto> retrieveAllAddress(String username) {
//        return addressRepository.findAll();
        List<Address> allAddress = addressRepository.findAll();
        return allAddress.stream().map(this::addressToDto).collect(Collectors.toList());
    }

    public List<AddressDto> retrieveAllAddressByUserId(String username, Integer userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No matching user found"));
        if (!user.getUsername().equals(username)) {
            throw new UserMismatchException("Logged In user and requesting user is different");
        }
//        return addressRepository.findByUserId(user_id);
        List<Address> allAddress = addressRepository.findAll();
        return allAddress.stream().map(this::addressToDto).collect(Collectors.toList());
    }

    public ResponseEntity<AddressDto> addNewAddressByUserId(String username, Integer userId, AddressDto addressDto) {
        Users foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No matching user found"));
        if (!foundUser.getUsername().equals(username))
            throw new UserMismatchException("Logged In user and requesting user is different");
        Address address = this.addressDtoToAddress(addressDto);
        address.setUser(foundUser);
        foundUser.getAddresses().add(address);
        Users savedUser = userRepository.save(foundUser);
        Address newAddress = savedUser.getAddresses().get(savedUser.getAddresses().size() - 1);
        AddressDto newAddressDto = this.addressToDto(newAddress);
//        return ResponseEntity.ok(address);
        return ResponseEntity.ok(newAddressDto);
    }

    public ResponseEntity<ApiResponse> deleteAddressByUserId(String username, Integer userId, Integer addressId) {
        Users foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No matching user found"));
        if (!foundUser.getUsername().equals(username))
            throw new UserMismatchException("Logged In user and requesting user is different");
        Address addressToBeDeleted = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
        addressRepository.delete(addressToBeDeleted);
        ApiResponse apiResponse = new ApiResponse(String.format("Address with Id %s deleted successfully", addressId),
                true);
        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<AddressDto> updateAddressById(String username, Integer userId, Integer addressId,
                                                        AddressDto newAddressDto) {
        Users foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No matching user found"));
        if (!foundUser.getUsername().equals(username))
            throw new UserMismatchException("Logged In user and requesting user is different");
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
        Address newAddress = this.addressDtoToAddress(newAddressDto);
        if (newAddress.getStreet() != null)
            existingAddress.setStreet(newAddress.getStreet());
        if (newAddress.getCity() != null)
            existingAddress.setCity(newAddress.getCity());
        if (newAddress.getCountry() != null)
            existingAddress.setCountry(newAddress.getCountry());
        Address updatedAddress = addressRepository.save(existingAddress);
        AddressDto updatedAddressDto = this.addressToDto(updatedAddress);
        return ResponseEntity.ok(updatedAddressDto);
    }

    private AddressDto addressToDto(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }

    private Address addressDtoToAddress(AddressDto addressDto) {
        return modelMapper.map(addressDto, Address.class);
    }
}