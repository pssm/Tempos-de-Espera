package pedromendes.tempodeespera.data;


public class Hospital {
    long id;
    String name;
    String description;
    String address;
    String phone;
    String email;
    String district;
    String standbyTimesUrl;
    boolean hasEmergency;
    String institutionUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStandbyTimesUrl() {
        return standbyTimesUrl;
    }

    public void setStandbyTimesUrl(String standbyTimesUrl) {
        this.standbyTimesUrl = standbyTimesUrl;
    }

    public boolean isHasEmergency() {
        return hasEmergency;
    }

    public void setHasEmergency(boolean hasEmergency) {
        this.hasEmergency = hasEmergency;
    }

    public String getInstitutionUrl() {
        return institutionUrl;
    }

    public void setInstitutionUrl(String institutionUrl) {
        this.institutionUrl = institutionUrl;
    }

    public boolean isInDistrict(String district) {
        return this.district.equalsIgnoreCase(district);
    }

    @Override
    public String toString() {
        return getName();
    }

}
