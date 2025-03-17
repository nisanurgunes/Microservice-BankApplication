package com.bank.cards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseEntity {

  @CreatedDate
  @Column(updatable = false, insertable = true)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(updatable = true, insertable = true)
  private LocalDateTime updatedAt;

  @CreatedBy
  @Column(insertable = true, updatable = true)
  private String createdBy;

  @LastModifiedBy
  @Column(insertable = true, updatable = true)
  private String updatedBy;
}
