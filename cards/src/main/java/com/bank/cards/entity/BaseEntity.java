package com.bank.cards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  // Getter and Setter for updatedAt
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  // Getter and Setter for createdBy
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  // Getter and Setter for updatedBy
  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  // Optional: ToString Method (similar to @ToString from Lombok)
  @Override
  public String toString() {
    return "BaseEntity{"
        + "createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + ", createdBy='"
        + createdBy
        + '\''
        + ", updatedBy='"
        + updatedBy
        + '\''
        + '}';
  }
}
