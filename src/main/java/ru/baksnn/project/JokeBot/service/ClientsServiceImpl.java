package ru.baksnn.project.JokeBot.service;

// ... other imports ...

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.baksnn.project.JokeBot.exceptions.UsernameAlreadyExistsException;
import ru.baksnn.project.JokeBot.model.Clients;
import ru.baksnn.project.JokeBot.model.ClientsAuthority;
import ru.baksnn.project.JokeBot.model.ClientsRole;
import ru.baksnn.project.JokeBot.repository.ClientsRepository;
import ru.baksnn.project.JokeBot.repository.ClientsRolesRepository;

@RequiredArgsConstructor
@Service
public class ClientsServiceImpl implements ClientsService, UserDetailsService {

    private final ClientsRolesRepository clientsRolesRepository;
    private final ClientsRepository clientsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registration(String username, String password) {
        if (!clientsRepository.findByUsername(username).isPresent()) {
            Clients user = clientsRepository.save(
                    new Clients()
                            .setId(null)
                            .setUsername(username)
                            .setPassword(passwordEncoder.encode(password))
                            .setLocked(false)
                            .setExpired(false)
                            .setEnabled(true)
            );
            clientsRolesRepository.save(new ClientsRole(null, ClientsAuthority.PLACE_JOKES, user));
        }
        else {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientsRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}