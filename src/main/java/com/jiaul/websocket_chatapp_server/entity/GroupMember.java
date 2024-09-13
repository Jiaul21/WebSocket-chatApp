package com.jiaul.websocket_chatapp_server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"profile"})
@EqualsAndHashCode(exclude = {"profile"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickName;
    private boolean isAdmin;
    private boolean isActive;
    private boolean isNotificationActive;
    private Long lastSeenMessageId;

    @ManyToOne
    private Profile profile;

    @OneToOne
    private GroupMember addedBy;
}
