package com.jiaul.websocket_chatapp_server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userCredential","webChannels"})
@EqualsAndHashCode(exclude = {"userCredential","webChannels"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String photo;
    private String gender;
    private String language;
    private String country;
    private boolean isActive;
    private boolean isVisible;  // visible to search by others

    @OneToOne(cascade = CascadeType.ALL)
    private UserCredential userCredential;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "profile_channel",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id"))
    private Set<WebChannel> webChannels;
}
