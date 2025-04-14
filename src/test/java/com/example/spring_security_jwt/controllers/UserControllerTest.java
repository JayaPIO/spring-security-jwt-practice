package com.example.spring_security_jwt.controllers;

import com.example.spring_security_jwt.converters.EntityToDto;
import com.example.spring_security_jwt.dtos.UserDto;
import com.example.spring_security_jwt.models.User;
import com.example.spring_security_jwt.security.JwtAuthenticationFilter;
import com.example.spring_security_jwt.security.JwtHelper;
import com.example.spring_security_jwt.services.UserService;
import com.example.spring_security_jwt.services.impl.MemberServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtHelper jwtHelper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private MemberServiceImpl memberService;

    /**
     * performing controller level testing for saving a new user into database
     * @throws Exception
     */
    @Test
    void save() throws Exception {
        User user = new User(101L, "simran", "11", "MANAGER");
        UserDto userDto = EntityToDto.UserEntityToUserDto(user);
        when(userService.saveUser(any(UserDto.class))).thenReturn(ResponseEntity.status(HttpStatusCode.valueOf(201)).body(userDto));

        this.mockMvc.perform(post("/home/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.role").value(user.getRole()))
                .andExpect(jsonPath("$.password").value(user.getPassword()));
    }

    /**
     * performing controller level testing for deleting an user
     * @throws Exception
     */
    @Test
    void deleteTest() throws Exception {
        User user = new User(101L, "simran", "11", "MANAGER");
        doNothing().when(userService).deleteUser(anyLong());

        this.mockMvc.perform(delete("/home/delete/{id}", 101L))
                .andExpect(status().isNoContent());
    }

}
