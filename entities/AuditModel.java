package org.munic.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import org.munic.service.FormatDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public abstract class AuditModel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    
    
    public Comparable<String> getCreatedAt() throws ParseException {
		 
		return FormatDate.ConvertDateTimeToString(createdAt); 
	}
   

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Comparable<String> getUpdatedAt() throws ParseException {
		 
		return FormatDate.ConvertDateTimeToString(updatedAt);
	}

   

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    
    public Date getCreatedAt_fd() {
		return createdAt;
	}
	public Date getUpdatedAt_fd() {
		return updatedAt;
	}
    
}