package com.joaobarbosadev.boletrix.api.user.dtos;

public class UserUpdate {
    private String name;
    private String email;
    private boolean isAlterPassword;
    private String password;
    private String newPassword;
    private String confirmNewPassword;


    public UserUpdate(){

    }

    public UserUpdate(String name,
                      String password,
                      String newPassword,
                      String confirmNewPassword,
                      boolean isAlterPassword,
                      String email) {
        this.name = name;
        this.password = password;
        this.confirmNewPassword = confirmNewPassword;
        this.newPassword = newPassword;
        this.isAlterPassword = isAlterPassword;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean isAlterPassword() {
        return isAlterPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public void setAlterPassword(boolean alterPassword) {
        isAlterPassword = alterPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "📄 Detalhes:\n" +
                "  • Nome: " + name + "\n" +
                "  • E-mail: " + email + "\n" +
                "  • Alterar Senha?: '" + isAlterPassword + "'\n" +
                "  • Nova Senha: '" + newPassword + "'\n" +
                "  • Confirmação Nova Senha: '" + confirmNewPassword + "'\n" +
                "  • Senha atual: " + password + "\n";
    }
}
