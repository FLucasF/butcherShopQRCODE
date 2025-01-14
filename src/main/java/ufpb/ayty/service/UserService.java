package ufpb.ayty.service;

import ufpb.ayty.dao.UserDAO;
import ufpb.ayty.model.User;
import ufpb.ayty.util.PasswordUtil;

import java.util.Optional;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public String authenticate(String email, String password) {
        Optional<User> userOpt = userDAO.findByEmail(email);
        if (userOpt.isEmpty() || !PasswordUtil.verifyPassword(password, userOpt.get().getPassword())) {
            throw new RuntimeException("Email ou senha inválidos");
        }
        User user = userOpt.get();
        return JwtService.generateToken(user.getEmail(), "USER"); // Padrão USER, mas pode ser dinâmico
    }

    public void register(User user) {
        Optional<User> existingUser = userDAO.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Usuário já existe com esse email");
        }
        user.setPassword(PasswordUtil.hashPassword(user.getPassword())); // Salvar senha como hash
        userDAO.save(user);
    }
}
