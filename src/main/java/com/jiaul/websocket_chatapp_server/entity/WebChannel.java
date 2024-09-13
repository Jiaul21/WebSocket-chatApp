package com.jiaul.websocket_chatapp_server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jiaul.websocket_chatapp_server.enums.ChannelEnumType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"profiles","chatGroup"})
@EqualsAndHashCode(exclude = {"profiles","chatGroup"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WebChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String channelName;
    private String messageChannel;      // group message
    private String notifyChannel;   // group announcement or user notification
    private String activityChannel;     // active status
    private String infoChannel;     // user account info
    private ChannelEnumType channelType;

    @ManyToMany(mappedBy = "webChannels")
    private Set<Profile> profiles;

    @OneToOne
    private ChatGroup chatGroup;
}
