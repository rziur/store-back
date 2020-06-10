package com.store.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.store.domain.WineCustomer} entity.
 */
public class WineCustomerDTO extends UserDTO  implements Serializable {
    
    private String address;

    private String phone;

    private Long userId;
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WineCustomerDTO)) {
            return false;
        }

        return getId() != null && getId().equals(((WineCustomerDTO) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WineCustomerDTO{" +
            "id=" + getId() +
            ", login='" + getLogin() + '\'' +
            ", firstName='" + getFirstName() + '\'' +
            ", lastName='" + getLastName() + '\'' +
            ", email='" + getEmail() + '\'' +
            ", imageUrl='" + getImageUrl() + '\'' +
            ", activated=" + isActivated() +
            ", langKey='" + getLangKey() + '\'' +
            ", createdBy=" + getCreatedBy() +
            ", createdDate=" + getCreatedDate() +
            ", lastModifiedBy='" + getLastModifiedBy() + '\'' +
            ", lastModifiedDate=" + getLastModifiedDate() +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
