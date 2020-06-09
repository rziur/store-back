package com.store.web.rest.vm;


/**
 * View Model extending the ManagedUserVM, which is meant to be used in the customer management UI.
 */
public class ManagedCustomerVM extends ManagedUserVM {

    private String address;

    private String phone;

    public ManagedCustomerVM() {
        // Empty constructor needed for Jackson.
    }

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

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagedCustomerVM{" + super.toString() + "} ";
    }
}
