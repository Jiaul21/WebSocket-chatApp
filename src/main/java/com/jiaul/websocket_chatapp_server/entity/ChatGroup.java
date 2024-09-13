package com.jiaul.websocket_chatapp_server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"chatMessages","groupMembers"})
@EqualsAndHashCode(exclude = {"chatMessages","groupMembers"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ChatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;
    private String photo;
    private Date createTime;
    private boolean isActive;
    private String channel;
    private boolean isPersonal;  // one to one chat

    @OneToOne(cascade = CascadeType.ALL)
    private WebChannel webChannel;

    @OneToMany(mappedBy = "chatGroup", cascade = CascadeType.ALL)
    private Set<ChatMessage> chatMessages;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<GroupMember> groupMembers;

}
