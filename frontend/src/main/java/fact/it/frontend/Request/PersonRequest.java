package fact.it.frontend.Request;

public class PersonRequest {

    private String name;
    private boolean visitor;
    private String phone;
    private String email;

    public PersonRequest(String name, boolean visitor, String phone, String email) {
        this.name = name;
        this.visitor = visitor;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisitor() {
        return visitor;
    }

    public void setVisitor(boolean visitor) {
        this.visitor = visitor;
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
}

