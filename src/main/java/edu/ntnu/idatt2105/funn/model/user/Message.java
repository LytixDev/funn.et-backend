package edu.ntnu.idatt2105.funn.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Class representing a message.
 * @author Callum G.
 * @version 1.0 - 23.03.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`chat_message`")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`chat`", referencedColumnName = "`chat_id`")
  private Chat chat;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`sender`", referencedColumnName = "`username`")
  private User sender;

  @Column(name = "`message`", nullable = false)
  @NonNull
  private String message;

  @Column(name = "`timestamp`", nullable = false)
  @NonNull
  private Timestamp timestamp;
}
